from sqlite3 import Row

from fastapi import FastAPI, HTTPException
import sqlite3

from src.models.dto.spot_dto import SpotDto

app = FastAPI()

def get_db():
    try:
        conn = sqlite3.connect("../config/surfdatabase.db")
        conn.row_factory = sqlite3.Row  # Permet de transformer les résultats en dictionnaires
        return conn
    except sqlite3.Error as e:
        raise HTTPException(status_code=500, detail=f"Database connection failed: {e}")

# Route pour obtenir la liste des spots
@app.get("/all")
def get_spot_list_data():
    query = """
        SELECT 
            s.id, sb.type, s.address, i.url, i.main
        FROM 
            spot s
        JOIN 
            surf_break sb ON sb.id = s.surf_break_id
        JOIN 
            image i ON i.spot_id = s.id
        WHERE 
            i.main = 1
    """
    try:
        with get_db() as conn:  # Gestion automatique de la fermeture de la connexion
            cur = conn.cursor()
            cur.execute(query)
            rows = cur.fetchall()

            # Transformer les résultats en liste de dictionnaires
            data = [dict(row) for row in rows]
            return {"spots": data}
    except sqlite3.Error as e:
        raise HTTPException(status_code=500, detail=f"Failed to fetch data: {e}")


@app.get("/spot/{id}")
def get_details_for_a_spot(id: int):
    query = """
        SELECT 
            s.id, sb.type, s.address, i.url, i.main, s.geocode
        FROM 
            spot s
        JOIN 
            surf_break sb ON sb.id = s.surf_break_id
        JOIN 
            image i ON i.spot_id = s.id
        WHERE 
            s.id = ?
    """
    try:
        with get_db() as conn:
            conn.row_factory = Row
            cur = conn.cursor()
            cur.execute(query, (id,))  # Utilisation de paramètres liés pour éviter l'injection SQL
            rows = cur.fetchall()

            # Transformer les résultats en liste de dictionnaires
            data = []
            for row in rows:
                row_dict = dict(row)
                try:
                    latitude, longitude = SpotDto.convertGeocode(row_dict.pop("geocode"))
                    row_dict["latitude"] = latitude
                    row_dict["longitude"] = longitude
                except ValueError as e:
                    raise HTTPException(status_code=500, detail=f"Erreur avec le geocode: {e}")
                data.append(row_dict)
            return {"spots": data}
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Failed to fetch data: {e}")
