from requests import Session
from sqlalchemy import String, select
from sqlalchemy.orm import Mapped, mapped_column, relationship

from src.models.orm.base_orm import Base


class SurfBreak(Base):
    __tablename__ = 'surf_break'
    id: Mapped[int] = mapped_column(primary_key=True)
    type: Mapped[str] = mapped_column(String(30))

    # spots = relationship("Spot", back_populates="surf_break")

    @staticmethod
    # On pourrait mettre les surfs break dans un Json à part
    def insertSurfBreak(session:Session):

        existing = session.execute(select(SurfBreak.id)).scalars().all()
        if existing:
            print("Les SurfBreaks existent déjà")
            return

        surf_breaks = [
            SurfBreak(id=1, type="Reef Break"),
            SurfBreak(id=2, type="Point Break"),
            SurfBreak(id=3, type="Beach Break"),
            SurfBreak(id=4, type="River Break"),
            SurfBreak(id=5, type="Artificial Wave Break"),
        ]

        session.bulk_save_objects(surf_breaks)
        session.commit()
        print("Bien joé")
        return surf_breaks

