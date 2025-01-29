# src/routes/routes_api.py
from fastapi import FastAPI, HTTPException
from sqlalchemy.exc import SQLAlchemyError

from src.config.db_instance import db
from src.models.orm.spot_orm import Spot
from src.models.orm.surf_break_orm import SurfBreak
from src.models.orm.image_orm import Image
from src.models.dto.spot_dto import SpotListDto, SpotDetailDto

app = FastAPI()

@app.get("/all")
def get_spot_list_data():
    try:
        with db.get_session() as session:
            spots = session.query(Spot) \
                .join(SurfBreak) \
                .join(Image) \
                .filter(Image.main == 1) \
                .all()

            return {
                "spots": [SpotListDto.from_orm(spot) for spot in spots]
            }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/spot/{id}")
def get_details_for_a_spot(id: int):
    try:
        with db.get_session() as session:
            spot = session.query(Spot) \
                .join(SurfBreak) \
                .join(Image) \
                .filter(Spot.id == id) \
                .filter(Image.main == 1) \
                .first()

            if not spot:
                raise HTTPException(status_code=404, detail="Spot not found")

            return {
                "spots": [SpotDetailDto.from_orm(spot)]
            }
    except SQLAlchemyError as e:
        raise HTTPException(status_code=500, detail=f"Failed to fetch data: {str(e)}")
