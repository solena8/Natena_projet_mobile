import requests
import os
from dotenv import load_dotenv

class FetchDataFromAirtable():
    def fetchDataFromAirtable(self):
        load_dotenv()

        SURF_SPOT_API_KEY = os.getenv("SURF_SPOT_API_KEY")
        BASE_ID = os.getenv("BASE_ID")
        TABLE_ID = os.getenv("TABLE_ID")

        if not SURF_SPOT_API_KEY or not BASE_ID or not TABLE_ID:
            raise ValueError("Les variables d'environnement ne sont pas correctement définies.")

        url = f"https://api.airtable.com/v0/{BASE_ID}/{TABLE_ID}"

        headers = {
            'Authorization': f"Bearer {SURF_SPOT_API_KEY}"
        }

        response = requests.get(url, headers=headers)

        if response.status_code == 200:
            print(response.json())
            return response.json()
        else:
            print(f"Erreur {response.status_code}: Impossible de récupérer les données.")



