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

# Version avec plusieurs images, qui ne matche pas avec le front
@app.get("/spot/{id}")
def get_details_for_a_spot(id: int):
    spotQuery = """
        SELECT
            s.id, sb.type, s.address, s.geocode, s.difficulty
        FROM
            spot s
        JOIN
            surf_break sb ON sb.id = s.surf_break_id
        WHERE
            s.id = ?
    """
    imageQuery = """
        SELECT * FROM image i
        WHERE i.spot_id = ?
    """
    try:
        with get_db() as conn:
            conn.row_factory = Row
            cur = conn.cursor()
            cur.execute(spotQuery, (id,))
            # id en parametre permet de définir le ? de s.id = ?
            # Utilisation de paramètres liés pour éviter l'injection SQL
            spotRows = cur.fetchall()

            # Transformer les résultats de la requête spot en liste de dictionnaires
            spotData = []
            for row in spotRows:
                row_dict = dict(row)
                try:
                    latitude, longitude = SpotDto.convertGeocode(row_dict.pop("geocode"))
                    row_dict["latitude"] = latitude
                    row_dict["longitude"] = longitude
                except ValueError as e:
                    raise HTTPException(status_code=500, detail=f"Erreur avec le geocode: {e}")
                spotData.append(row_dict)

            # Transformer les résultats de la requête image en liste de dictionnaires
            cur.execute(imageQuery, (id,))
            imageRows = cur.fetchall()
            imageData = []
            for row in imageRows:
                row_dict = dict(row)
                imageData.append(row_dict)

            return {
                "spot": spotData,
                "images": imageData
                    }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Failed to fetch data: {e}")


# Version avec une seule image, qui match avec le front
# @app.get("/spot/{id}")
# def get_details_for_a_spot(id: int):
#     query = """
#         SELECT
#             s.id, sb.type, s.address, i.url, i.main, s.geocode, s.difficulty
#         FROM
#             spot s
#         JOIN
#             surf_break sb ON sb.id = s.surf_break_id
#         JOIN
#             image i ON i.spot_id = s.id
#         WHERE
#             s.id = ?
#     """
#     try:
#         with get_db() as conn:
#             conn.row_factory = Row
#             cur = conn.cursor()
#             cur.execute(query, (id,))  # Utilisation de paramètres liés pour éviter l'injection SQL
#             rows = cur.fetchall()
#             # Transformer les résultats en liste de dictionnaires
#             data = []
#             for row in rows:
#                 row_dict = dict(row)
#                 try:
#                     latitude, longitude = SpotDto.convertGeocode(row_dict.pop("geocode"))
#                     row_dict["latitude"] = latitude
#                     row_dict["longitude"] = longitude
#                 except ValueError as e:
#                     raise HTTPException(status_code=500, detail=f"Erreur avec le geocode: {e}")
#                 data.append(row_dict)
#             return {"spots": data}
#     except Exception as e:
#         raise HTTPException(status_code=500, detail=f"Failed to fetch data: {e}")

@app.post("/spots")
def insert_new_spot():
    query = """
        
    """
    # try:
    #     with get_db() as conn:  # Gestion automatique de la fermeture de la connexion
    #         cur = conn.cursor()
    #         cur.execute(query)
    #         rows = cur.fetchall()
    #
    #         # Transformer les résultats en liste de dictionnaires
    #         data = [dict(row) for row in rows]
    #         return {"spots": data}
    # except sqlite3.Error as e:
    #     raise HTTPException(status_code=500, detail=f"Failed to fetch data: {e}")

