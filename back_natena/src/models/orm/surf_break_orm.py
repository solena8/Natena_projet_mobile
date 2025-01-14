from sqlalchemy import String
from sqlalchemy.orm import Mapped, mapped_column

from src.models.orm.spot_orm import Base


class Image(Base):
    id: Mapped[int] = mapped_column(primary_key=True)
    type: Mapped[str] = mapped_column(String(30))
