from sqlalchemy import String
from sqlalchemy.orm import Mapped, mapped_column

from src.models.orm.base_orm import Base


class SurfBreak(Base):
    __tablename__ = 'surf_break'
    id: Mapped[int] = mapped_column(primary_key=True)
    type: Mapped[str] = mapped_column(String(30))

    @staticmethod
    def insertSurfBreak(session):
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

