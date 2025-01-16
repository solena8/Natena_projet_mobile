from requests import Session
from sqlalchemy import ForeignKey
from sqlalchemy.orm import Mapped, mapped_column

from src.models.orm.base_orm import Base


class Image(Base):
    __tablename__ = 'image'
    id: Mapped[int] = mapped_column(primary_key=True, autoincrement=True)
    spot_id: Mapped[int] = mapped_column(ForeignKey("spot.id"))
    url: Mapped[str]
    main: Mapped[bool]

    # @todo: Méthode à appeler au sein de Spot.insertSurfDataFromJson() dans une boucle for pour insérer les photos dans la db dans la table photos
    # Permet d'éviter de refaire un appel API. Se baser sur l'id du spot qui vient d'être créé.
    @staticmethod
    def insertPhoto(self, spotId, session: Session):
        # @todo
        return

