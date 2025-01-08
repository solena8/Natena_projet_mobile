package com.example.natena

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.natena.models.createSpotsFromComplexJson
import com.example.natena.models.spots
import com.example.natena.models.readComplexJsonFromRaw

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.spotlist)
        val adapter = SpotAdapter(this, spots)
        listView.adapter = adapter

        //Vérification que le json est ok
        val jsonString = readComplexJsonFromRaw(this, R.raw.complex_datas)
        println("Json debug : `$jsonString`")

        //Condition pour empêcher que la fonction se lance si les spots sont déjà initialisés.
        if (spots.isEmpty()) {
            createSpotsFromComplexJson(this)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            // Récupérer le spot correspondant
            val selectedSpot = spots[position]

            // Créer un Intent pour démarrer l'activité de détail
            val intent = Intent(this, SingleSpotActivity::class.java).apply {
                putExtra("spotImage", selectedSpot.spotImage)
                putExtra("spotName", selectedSpot.spotName)
                putExtra("spotLocation", selectedSpot.spotLocation)
            }

            // Lancer l'activité
            startActivity(intent)
        }
    }
}