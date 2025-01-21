import base64
import json
from dataclasses import dataclass

from sqlalchemy.dialects.sqlite import DATETIME


@dataclass
class SpotDto:
    id: int
    surf_break_id: int
    address: str
    season_begins: DATETIME
    season_ends: DATETIME
    difficulty: int
    link: str
    latitude: float
    longitude: float
    created_at: DATETIME

    @classmethod
    def extractSpotFromDB(cls, spot):

        latitude, longitude = cls.convertGeocode(spot.geocode)

        return cls(
            id = spot.id,
            surf_break_id=spot.surf_break_id,
            address=spot.address,
            season_begins=spot.season_begins,
            season_ends=spot.season_ends,
            difficulty=spot.difficulty,
            link=spot.link,
            latitude= latitude,
            longitude= longitude,
            created_at=spot.created_at
        )

    @classmethod
    def convertGeocode(cls, geocode):
        geocodeJson = base64.b64decode(geocode)
        print("Json geocode", geocodeJson)
        geocodeDict = json.loads(geocodeJson)
        print("Dictionnaire geocode :", geocodeDict)
        lat = geocodeDict["o"]["lat"]
        lng = geocodeDict["o"]["lng"]
        return lat, lng