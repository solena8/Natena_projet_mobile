# src/models/dto/spot_dto.py
from dataclasses import dataclass
from datetime import date
import base64
import json

from src.models.dto.image_dto import ImageDto


@dataclass
class SpotListDto:
    id: int
    type: str
    address: str
    url: str | None
    main: int

    @classmethod
    def from_orm(cls, spot) -> "SpotListDto":
        image_dto = ImageDto.from_orm(spot.images[0]) if spot.images else None
        return cls(
            id=spot.id,
            type=spot.surf_break.type,
            address=spot.address,
            url=image_dto.url if image_dto else None,
            main=image_dto.main if image_dto else 0
        )

@dataclass
class SpotDetailDto:
    id: int
    type: str
    address: str
    url: str
    main: int
    latitude: float
    longitude: float
    difficulty: int
    season_begins: str | None
    season_ends: str | None

    @classmethod
    def from_orm(cls, spot) -> "SpotDetailDto":
        lat, lng = cls._decode_geocode(spot.geocode)
        image_dto = ImageDto.from_orm(spot.images[0])
        return cls(
            id=spot.id,
            type=spot.surf_break.type,
            address=spot.address,
            url=spot.images[0].url,
            main=image_dto.main,
            latitude=lat,
            longitude=lng,
            difficulty=spot.difficulty,
            season_begins=spot.season_begins.isoformat().split('T')[0] if spot.season_begins else None,
            season_ends=spot.season_ends.isoformat().split('T')[0] if spot.season_ends else None
        )


    @staticmethod
    def _decode_geocode(geocode: str) -> tuple[float, float]:
        geocode_json = base64.b64decode(geocode)
        geocode_dict = json.loads(geocode_json)
        return geocode_dict["o"]["lat"], geocode_dict["o"]["lng"]
