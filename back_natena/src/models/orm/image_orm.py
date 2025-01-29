
from sqlalchemy import ForeignKey, Boolean, String, Integer
from requests import Session
from sqlalchemy import ForeignKey, String
from sqlalchemy.orm import Mapped, mapped_column
from sqlalchemy.orm import relationship

from src.models.orm.base_orm import Base


class Image(Base):
    __tablename__ = 'image'
    id: Mapped[int] = mapped_column(primary_key=True, autoincrement=True)
    spot_id: Mapped[int] = mapped_column(ForeignKey("spot.id"))
    url: Mapped[str] = mapped_column(String)
    main: Mapped[int] = mapped_column(Integer, nullable=True)
    # relation ajoutée pour faire fonctionner l'orm avec sql aclchemy
    spot = relationship("Spot", back_populates="images")

    # Méthode appelée au sein de Spot.insertSurfDataFromJson() dans une boucle for pour insérer les photos dans la db dans la table image
    # Permet d'éviter de refaire un appel API.
    @staticmethod
    def insertImageDataFromJson(record, photo, main, session: Session):
        imageToAdd = Image(
            spot_id = record.id,
            url = photo['url'],
            main = main
        )

        session.add(imageToAdd)



