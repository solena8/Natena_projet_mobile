from src.models.orm.spot_orm import Base
from src.models.orm.image_orm import Image
from src.models.orm.spot_orm import Spot
from src.models.orm.surf_break_orm import SurfBreak
from src.config.db_instance import db

# Création des tables
Base.metadata.create_all(db._engine, tables=[SurfBreak.__table__])
Base.metadata.create_all(db._engine, tables=[Spot.__table__])
Base.metadata.create_all(db._engine, tables=[Image.__table__])

# Initialisation des données
with db.get_session() as session:
    SurfBreak.insertSurfBreak()
    Spot.insertSurfDataFromJson()
