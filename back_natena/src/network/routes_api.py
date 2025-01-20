from fastapi import FastAPI
import sqlite3


app = FastAPI()

def get_db():
    conn = sqlite3.connect("../config/surfdatabase.db")
    # permet que row ait le format adéquat pour transformer en dict plus tard
    conn.row_factory = sqlite3.Row
    # cursos object : permet d'exécuter des commandes SQL
    cur = conn.cursor()
    return conn, cur


@app.get("/")
def get_spot_list_data():
    conn, cur = get_db()
    fetch_data = "select s.id, sb.type, s.address, i.url, i.main from spot s join surf_break sb on sb.id = s.surf_break_id join image i on i.spot_id = s.id where i.main = 1"
    cur.execute(fetch_data)
    rows = cur.fetchall()
    data = [dict(row) for row in rows]
    conn.close()
    if isinstance(data, dict) and "error" in data:
        # Si une erreur s'est produite lors du chargement des données
        return {"error": data["error"]}
    return data  # Retourner toutes les données JSON