from datetime import date, datetime
from sqlalchemy import ForeignKey
from sqlalchemy import String
from sqlalchemy.orm import DeclarativeBase
from sqlalchemy.orm import Mapped
from sqlalchemy.orm import mapped_column

class Base(DeclarativeBase):
    pass

# Vérifier si on a besoin de rajouter des relationships dans notre orm
class Spot(Base):
    __tablename__ = 'spot'
    # mapped_column() permet de caractériser plus fortement les champs
    id: Mapped[int] = mapped_column(primary_key=True)
    # surf_break_id: Mapped[int] = mapped_column(ForeignKey("surf_break.id"))
    address: Mapped[str] = mapped_column(String(100))
    #@Todo : gérer le géocode pour générer la latitude et la longitude quand on aura le DTO
    geocode: Mapped[str]
    difficulty: Mapped[int]  # @Todo A limiter entre 1 et 5
    link: Mapped[str]
    season_begins: Mapped[date]
    season_ends: Mapped[date]
    created_at: Mapped[datetime]

#     def determineSurfBreakId(self, string):
#
#         #remplace les surfs breaks par leur id respectif
#         #return le json mappé
#
#     def insertSurfDataFromJson(self):
#         json_data = self.determineSurfBreakId()
#         spot_id = 1
#         for record in json_data['records']:
#             fields = record[fields]
#
#             peak_surf_season_begins = datetime.strptime(fields['Peak Surf Season Begins'], '%Y-%m-%d') if fields[
#                 'Peak Surf Season Begins'] else None
#             peak_surf_season_ends = datetime.strptime(fields['Peak Surf Season Ends'], '%Y-%m-%d') if fields[
#                 'Peak Surf Season Ends'] else None
#
#             # Créer un objet de la classe SurfDestination
#             surf_destination = Spot(
#                 id=spot_id,
#                 # surf_break=self.determineSurfBreakId(fields['Surf Break'][0]),
#                 address=fields.get('Address', ''),
#                 peak_surf_season_begins=peak_surf_season_begins,
#                 peak_surf_season_ends=peak_surf_season_ends,
#                 difficulty_level=fields['Difficulty Level'],
#                 influencers=fields['Influencers'],
#                 photos=[photo['url'] for photo in fields['Photos']],  # Récupère les URLs des photos
#                 magic_seaweed_link=fields.get('Magic Seaweed Link', ''),
#                 geocode=fields.get('Geocode', '')
#             )
#
#             spot_id += 1
#
#             # Ajouter l'objet à la session
#             session.add(surf_destination)
#
#             # Commit des changements dans la base de données
#         session.commit()
#
#     # Charger le JSON (en supposant que le JSON soit dans un fichier)
#     with open('data.json', 'r') as file:
#         json_data = json.load(file)
#
#     # Insérer les données dans la base
#     insert_surf_data(json_data)
#
#
# FetchDataFromAirtable.fetchDataFromAirtable(self)

