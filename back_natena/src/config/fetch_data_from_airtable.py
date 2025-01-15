from ctypes import SetPointerType
from datetime import datetime
from random import random

import requests
import self
from sqlalchemy.orm import session

from src.models.orm.spot_orm import Spot
from src.models.orm.surf_break_orm import SurfBreak


class FetchDataFromAirtable():
    # @Todo : planquer les IDs
    def fetchDataFromAirtable(self):
        url = "https://api.airtable.com/v0/appEksYm9WhIjEtus/tblRuaa61gtDvzAt2"

        headers = {
            'Authorization': "Bearer patrDqepDCk7RDxlp.6c71e97b83c63734c11ca9d5047bdabb66e7c0f166d58ef7b807d7f2404425fe"
        }

        response = requests.get(url, headers=headers)

        if response.status_code == 200:
            print(response.json())
            return response.json()
        else:
            print(f"Erreur {response.status_code}: Impossible de récupérer les données.")



