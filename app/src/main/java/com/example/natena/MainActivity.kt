package com.example.natena

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.natena.models.Spot
import com.example.natena.models.SpotAdapter
import com.example.natena.network.fetchAllItems
import kotlinx.coroutines.launch

// Classe principale de l'application
class MainActivity : AppCompatActivity() {
    // Déclaration d'un adaptateur pour la liste des spots
    private lateinit var adapter: SpotAdapter

    // Liste mutable qui stocke les spots récupérés
    private val spots = mutableListOf<Spot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Active la configuration Edge-to-Edge pour une meilleure gestion des insets système
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Gère les marges de l'interface utilisateur pour éviter les chevauchements avec les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialisation de la ListView et configuration de l'adaptateur
        val listView = findViewById<ListView>(R.id.spotlist)
        adapter = SpotAdapter(this, spots)
        listView.adapter = adapter

        // Charge les données depuis l'API
        loadSpotsFromApi()

        // Gestion des clics sur les éléments de la liste
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedSpot = spots[position]
            val intent = Intent(this, SingleSpotActivity::class.java).apply {
                putExtra("spotImage", selectedSpot.spotImage) // Image du spot
                putExtra("Location", selectedSpot.spotName)   // Nom du spot
                putExtra("Address", selectedSpot.spotLocation) // Adresse du spot
            }
            // Démarre une nouvelle activité pour afficher les détails du spot sélectionné
            startActivity(intent)
        }
    }

    // Fonction pour charger les spots depuis l'API
    private fun loadSpotsFromApi() {
        lifecycleScope.launch {
            try {
                // Appelle la fonction qui récupère les données de l'API
                val allItems = fetchAllItems()

                // Convertit les données brutes en objets Spot
                val newSpots = convertApiResponseToSpots(allItems)

                // Met à jour la liste des spots et notifie l'adaptateur
                spots.clear()
                spots.addAll(newSpots)
                adapter.notifyDataSetChanged()
                Log.d("MainActivity", "Spots loaded from API: ${spots.size}")
            } catch (e: Exception) {
                // Gère les erreurs éventuelles lors du chargement des données
                Log.e("MainActivity", "Error loading spots from API", e)
            }
        }
    }

    // Fonction pour convertir la réponse brute de l'API en une liste d'objets Spot
    private fun convertApiResponseToSpots(apiResponse: Map<String, Any>): List<Spot> {
        return try {
            // Récupère les enregistrements depuis la clé "records"
            val records = (apiResponse["records"] as? List<*>) ?: emptyList<Any>()

            // Transforme chaque enregistrement en un objet Spot
            records.mapNotNull { record ->
                (record as? Map<*, *>)?.let { recordMap ->
                    val fields = recordMap["fields"] as? Map<*, *>
                    fields?.let {
                        Spot(
                            spotName = (it["Destination"] as? String) ?: "", // Nom du spot
                            spotLocation = (it["Address"] as? String) ?: "", // Adresse
                            spotImage = getSafeLargeThumbnailUrl(it["Photos"]), // URL de l'image
                        )
                    }
                }
            }
        } catch (e: Exception) {
            // Gère les erreurs de conversion
            Log.e("MainActivity", "Error converting API response", e)
            emptyList()
        }
    }

    // Fonction pour obtenir en toute sécurité l'URL de la miniature (taille large) d'une photo
    private fun getSafeLargeThumbnailUrl(photos: Any?): String {
        return try {
            // Parcourt les données pour extraire l'URL
            val photosList = photos as? List<*>
            val firstPhoto = photosList?.firstOrNull() as? Map<*, *>
            val thumbnails = firstPhoto?.get("thumbnails") as? Map<*, *>
            val large = thumbnails?.get("large") as? Map<*, *>
            (large?.get("url") as? String) ?: "" // Renvoie l'URL ou une chaîne vide
        } catch (e: Exception) {
            // Retourne une chaîne vide en cas d'erreur
            ""
        }
    }
}
