# src/models/orm/spot_orm.py
from datetime import datetime, timezone
from sqlalchemy import String, ForeignKey, DateTime
from sqlalchemy.orm import Mapped, mapped_column, relationship, validates
from src.models.orm.base_orm import Base
from src.config.fetch_data_from_airtable import FetchDataFromAirtable
from src.models.orm.surf_break_orm import SurfBreak
from src.models.orm.image_orm import Image
from src.config.db_instance import db


class Spot(Base):
    __tablename__ = 'spot'
    id: Mapped[int] = mapped_column(primary_key=True, autoincrement=True)
    surf_break_id: Mapped[int | None] = mapped_column(ForeignKey("surf_break.id"), nullable=True)
    address: Mapped[str] = mapped_column(String(100))
    geocode: Mapped[str] = mapped_column(String(), nullable=True)
    difficulty: Mapped[int]
    link: Mapped[str] = mapped_column(String(), nullable=True)
    season_begins: Mapped[datetime] = mapped_column(DateTime(), nullable=True)
    season_ends: Mapped[datetime] = mapped_column(DateTime(), nullable=True)
    created_at: Mapped[datetime] = mapped_column(
        DateTime(timezone=True),  # Add timezone support
        nullable=False)
    surf_break = relationship("SurfBreak", back_populates="spots")
    images = relationship("Image", back_populates="spot") \

    @ validates('difficulty')

    def validate_difficulty(self, key, value):
        if not (1 <= value <= 5):
            raise ValueError("Difficulty must be between 1 and 5")
        return value

    @classmethod
    def insertSurfDataFromJson(cls):
        with db.get_session() as session:
            json_data = FetchDataFromAirtable.fetchDataFromAirtable(cls)

            # Récupérer ou créer un surf break par défaut
            default_surf_break = session.query(SurfBreak).filter_by(type="No Surf Break").first()
            if not default_surf_break:
                default_surf_break = SurfBreak(type="No Surf Break")
                session.add(default_surf_break)
                session.commit()

            default_surf_break_id = default_surf_break.id

            for record in json_data['records']:
                fields = record['fields']

                # Parsing des dates
                peak_surf_season_begins = datetime.strptime(
                    fields['Peak Surf Season Begins'],
                    '%Y-%m-%d'
                ) if fields.get('Peak Surf Season Begins') else None

                peak_surf_season_ends = datetime.strptime(
                    fields['Peak Surf Season Ends'],
                    '%Y-%m-%d'
                ) if fields.get('Peak Surf Season Ends') else None

                # Déterminer le surf_break_id
                surf_break_id = default_surf_break_id
                if 'Surf Break' in fields and fields['Surf Break']:
                    surf_type = fields['Surf Break'][0]
                    if surf_type in ['Reef Break', 'Point Break', 'Beach Break', 'River Break', 'Artificial Wave Break']:
                        surf_break_id = SurfBreak.determineSurfBreakId(surf_type) or default_surf_break_id

            # Créer une instance Spot
            spot = cls(
                surf_break_id=surf_break_id,
                address=fields.get('Address', ''),
                season_begins=peak_surf_season_begins,
                season_ends=peak_surf_season_ends,
                difficulty=fields['Difficulty Level'],
                link=fields.get('Magic Seaweed Link', ''),
                geocode=fields.get('Geocode', ''),
                created_at=datetime.now(timezone.utc)
            )
            session.add(spot)
            session.commit()

            for i in range(len(fields['Photos'])):
                photo = fields['Photos'][i]
                main = 1 if i == 0 else 0
                Image.insertImageDataFromJson(spot, photo, main, session)

            session.commit()
