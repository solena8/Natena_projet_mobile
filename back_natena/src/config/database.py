from self import self
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy_utils import database_exists, create_database

from src.models.orm.spot_orm import Base
from src.models.orm.image_orm import Image
from src.models.orm.spot_orm import Spot
from src.models.orm.surf_break_orm import SurfBreak

engine = create_engine("sqlite:///surfdatabase.db", echo=True)
if not database_exists(engine.url):
    create_database(engine.url)

# Il semblerait que c'est une meilleure pratique de faire juste engine, à voir (ça fonctionne en tout cas).
# Base.metadata.create_all(engine)

Base.metadata.create_all(engine, tables=[SurfBreak.__table__])
Base.metadata.create_all(engine, tables=[Spot.__table__])
Base.metadata.create_all(engine, tables=[Image.__table__])

# Création d'une session
Session = sessionmaker(bind=engine)
session = Session()

# Insertion des données
SurfBreak.insertSurfBreak(session)
Spot.insertSurfDataFromJson(self, session)
