from src.models.orm.image_orm import Image
from src.models.orm.surf_break_orm import SurfBreak
from src.models.orm.spot_orm import Spot
from src.models.orm.spot_orm import Base

from sqlalchemy import create_engine
from sqlalchemy_utils import database_exists, create_database

engine = create_engine("sqlite:///surfdatabase.db", echo=True)
if not database_exists(engine.url):
    create_database(engine.url)


Base.metadata.create_all(engine, tables=[SurfBreak.__table__])
Base.metadata.create_all(engine, tables=[Spot.__table__])
Base.metadata.create_all(engine, tables=[Image.__table__])