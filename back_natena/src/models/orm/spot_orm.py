from datetime import date, datetime

from sqlalchemy.orm import Session, relationship, validates
from sqlalchemy import String, select, ForeignKey
from sqlalchemy.exc import NoResultFound
from sqlalchemy.orm import Mapped, session
from sqlalchemy.orm import mapped_column

from src.config.fetch_data_from_airtable import FetchDataFromAirtable
from src.models.orm.base_orm import Base
from src.models.orm.surf_break_orm import SurfBreak


# Vérifier si on a besoin de rajouter des relationships dans notre orm
class Spot(Base):
    __tablename__ = 'spot'
    # mapped_column() permet de caractériser plus fortement les champs
    id: Mapped[int] = mapped_column(primary_key=True, autoincrement=True)
    surf_break_id: Mapped[int] = mapped_column(ForeignKey("surf_break.id"))
    address: Mapped[str] = mapped_column(String(100))
    # @Todo : gérer le géocode pour générer la latitude et la longitude quand on aura le DTO
    geocode: Mapped[str] = mapped_column(String(), nullable=True)
    difficulty: Mapped[int]  # @Todo A limiter entre 1 et 5
    link: Mapped[str] = mapped_column(String(), nullable=True)
    season_begins: Mapped[date] = mapped_column(String(), nullable=True)
    season_ends: Mapped[date] = mapped_column(String(), nullable=True)
    created_at: Mapped[datetime] = mapped_column(String())

    # surf_break = relationship("Surf Break", back_populates="spots")

    @validates('difficulty')
    def validate_difficulty(self, key, value):
        if not(1 <= value <= 5):
            raise ValueError("La difficulté doit être comprise entre 1 et 5.")
        return value

    @staticmethod
    def determineSurfBreakId(session: Session, surf_type) -> int | None:
        try:
            surfBreakId = session.execute(select(SurfBreak.id).where(SurfBreak.type == surf_type)).scalar_one()
            print(f"L'id du surf Break est : {surfBreakId} ")
            return surfBreakId
        except NoResultFound:
            print(f"Aucun SurfBreak trouvé pour le type : {surf_type}")
            return None


    def insertSurfDataFromJson(self, session: Session):
        json_data = FetchDataFromAirtable.fetchDataFromAirtable(self)
        for record in json_data['records']:
            fields = record['fields']

            peak_surf_season_begins = datetime.strptime(fields['Peak Surf Season Begins'], '%Y-%m-%d') if fields[
                'Peak Surf Season Begins'] else None
            peak_surf_season_ends = datetime.strptime(fields['Peak Surf Season Ends'], '%Y-%m-%d') if fields[
                'Peak Surf Season Ends'] else None

            surf_break_id = Spot.determineSurfBreakId(session, fields['Surf Break'][0])
            if surf_break_id is None: continue

            # Créer un objet de la classe SurfDestination
            surf_destination = Spot(
                surf_break_id=surf_break_id,
                address=fields.get('Address', ''),
                season_begins=peak_surf_season_begins,
                season_ends=peak_surf_season_ends,
                difficulty=fields['Difficulty Level'],
                link=fields.get('Magic Seaweed Link', ''),
                geocode=fields.get('Geocode', ''),
                created_at=date.today()
            )

            # Boucle for concernant les méthodes

            session.add(surf_destination)

            # Commit des changements dans la base de données
        session.commit()