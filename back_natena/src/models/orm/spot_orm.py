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
    __tablename__ = "spot"
    # mapped_column() permet de caractériser plus fortement les champs
    id: Mapped[int] = mapped_column(primary_key=True)
    surf_break_id: Mapped[int] = mapped_column(ForeignKey("surf_break.id"))
    address: Mapped[str] = mapped_column(String(100))
    latitude: Mapped[float]
    longitude: Mapped[float]
    difficulty: Mapped[int]  # @Todo A limiter entre 1 et 5
    link: Mapped[str]
    season_begins: Mapped[date]
    season_ends: Mapped[date]
    created_at: Mapped[datetime]
