import requests
import os
from dotenv import load_dotenv

class FetchDataFromAirtable():
    # @Todo : planquer les IDs
    def fetchDataFromAirtable(self):
        load_dotenv()
        # url = "https://api.airtable.com/v0/appEksYm9WhIjEtus/tblRuaa61gtDvzAt2"
        #
        # headers = {
        #     'Authorization': "Bearer patrDqepDCk7RDxlp.6c71e97b83c63734c11ca9d5047bdabb66e7c0f166d58ef7b807d7f2404425fe"
        # }
        SURF_SPOT_API_KEY = os.getenv("SURF_SPOT_API_KEY")
        BASE_ID = os.getenv("BASE_ID")
        TABLE_ID = os.getenv("TABLE_ID")

        print(SURF_SPOT_API_KEY)
        print(TABLE_ID)
        print(BASE_ID)

        if not SURF_SPOT_API_KEY or not BASE_ID or not TABLE_ID:
            raise ValueError("Les variables d'environnement ne sont pas correctement définies.")


        apikey= "patrDqepDCk7RDxlp.6c71e97b83c63734c11ca9d5047bdabb66e7c0f166d58ef7b807d7f2404425fe"
        baseid = "appEksYm9WhIjEtus"
        tableid = "tblRuaa61gtDvzAt2"

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



