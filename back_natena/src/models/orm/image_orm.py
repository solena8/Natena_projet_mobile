
from requests import Session
from sqlalchemy import ForeignKey, Boolean, String
from sqlalchemy.orm import Mapped, mapped_column

from src.models.orm.base_orm import Base


class Image(Base):
    __tablename__ = 'image'
    id: Mapped[int] = mapped_column(primary_key=True, autoincrement=True)
    spot_id: Mapped[int] = mapped_column(ForeignKey("spot.id"))
    url: Mapped[str] = mapped_column(String)
    main: Mapped[bool] = mapped_column(Boolean, nullable=True)

    # Méthode appelée au sein de Spot.insertSurfDataFromJson() dans une boucle for pour insérer les photos dans la db dans la table image
    # Permet d'éviter de refaire un appel API.
    @staticmethod
    def insertImageDataFromJson(self, record, photo, main, session: Session):
        imageToAdd = Image(
            spot_id = record.id,
            url = photo['url'],
            main = main
        )

        session.add(imageToAdd)
