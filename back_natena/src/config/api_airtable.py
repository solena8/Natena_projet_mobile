import requests

url = "https://api.airtable.com/v0/appEksYm9WhIjEtus/tblRuaa61gtDvzAt2"

headers = {
    'Authorization': "Bearer patrDqepDCk7RDxlp.6c71e97b83c63734c11ca9d5047bdabb66e7c0f166d58ef7b807d7f2404425fe"
}

response = requests.get(url, headers=headers)

if response.status_code == 200:
    print(response.json())
else:
    print(f"Erreur {response.status_code}: Impossible de récupérer les données.")

#
# url = "https://api.airtable.com/v0/appzBKIwgW3EG2YXJ/tblRuaa61gtDvzAt2"
#
# headers = {
#     'Authorization': "Bearer path1VPzzec6fJZ31.7f3c62e9c08cde8372b1261886550658e987da2e120f9f5fcd231fce7cb4db75"
# }
#
# response = requests.get(url, headers=headers)
#
# if response.status_code == 200:
#     print(response.json())
# else:
#     print(f"Erreur {response.status_code}: Impossible de récupérer les données.")
