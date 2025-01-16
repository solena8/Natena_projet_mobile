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
    difficulty: Mapped[int]
    link: Mapped[str] = mapped_column(String(), nullable=True)
    season_begins: Mapped[date] = mapped_column(String(), nullable=True)
    season_ends: Mapped[date] = mapped_column(String(), nullable=True)
    created_at: Mapped[datetime] = mapped_column(String())

    @validates('difficulty')
    def validate_difficulty(self, key, value):
        if not(1 <= value <= 5):
            raise ValueError("La difficulté doit être comprise entre 1 et 5.")
        return value




    def insertSurfDataFromJson(self, session: Session):
        json_data = FetchDataFromAirtable.fetchDataFromAirtable(self)
        for record in json_data['records']:
            fields = record['fields']

            peak_surf_season_begins = datetime.strptime(fields['Peak Surf Season Begins'], '%Y-%m-%d') if fields[
                'Peak Surf Season Begins'] else None
            peak_surf_season_ends = datetime.strptime(fields['Peak Surf Season Ends'], '%Y-%m-%d') if fields[
                'Peak Surf Season Ends'] else None

            # utilisation de la méthode de surf break orm pour déterminer l'id de surf break
            surf_break_id = SurfBreak.determineSurfBreakId(session, fields['Surf Break'][0])
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

            # @Todo ajout d'une boucle for pour initialiser les photos en appelant la méthode de image orm (a faire)



            session.add(surf_destination)

            # Commit des changements dans la base de données
        session.commit()