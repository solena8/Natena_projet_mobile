from fastapi import FastAPI
import json  # Module pour manipuler les fichiers JSON
from pathlib import Path  # Gestion des chemins de fichier

app = FastAPI()

# Chemin vers le fichier JSON
json_file_path = Path("complex_data.json")

# Charger les données JSON au démarrage de l'application
def load_json_data(file_path: Path):
    try:
        with file_path.open("r", encoding="utf-8") as file:
            return json.load(file)  # Charger le JSON sous forme de dictionnaire Python
    except FileNotFoundError:
        return {"error": "Fichier JSON introuvable"}
    except json.JSONDecodeError:
        return {"error": "Erreur lors du parsing du fichier JSON"}

# Charger les données
data = load_json_data(json_file_path)

# Route pour vérifier que l'API fonctionne
@app.get("/")
def get_all_data():
    if isinstance(data, dict) and "error" in data:
        # Si une erreur s'est produite lors du chargement des données
        return {"error": data["error"]}
    return data  # Retourner toutes les données JSON


