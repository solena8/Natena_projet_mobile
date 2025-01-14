from sqlalchemy import ForeignKey
from sqlalchemy.orm import Mapped, mapped_column

from src.models.orm.spot_orm import Base


class Image(Base):
    id: Mapped[int] = mapped_column(primary_key=True)
    spot_id: Mapped[int] = mapped_column(ForeignKey("spot.id"))
    url: Mapped[str]
    main: Mapped[bool]
