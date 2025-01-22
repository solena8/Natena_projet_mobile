package com.example.natena

import com.example.natena.models.AddSpotActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.natena.models.SingleSpotActivity
import com.example.natena.models.Spot
import com.example.natena.models.SpotAdapter
import com.example.natena.models.toSpot
import com.example.natena.network.fetchAllSpots
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    // Adapter for the ListView to display spots
    private lateinit var adapter: SpotAdapter

    // Mutable list to hold the spots fetched from the API
    private val spots = mutableListOf<Spot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for this activity
        setContentView(R.layout.activity_main)

        // Find the ListView in the layout by its ID
        val listView = findViewById<ListView>(R.id.spotlist)

        // Initialize the adapter with the spots list and set it to the ListView
        adapter = SpotAdapter(this, spots)
        listView.adapter = adapter

        // Load spots from the API when the activity is created
        loadSpotsFromApi()

        // Set up an item click listener for the ListView to handle spot selection
        listView.setOnItemClickListener { _, _, position, _ ->
            // Get the selected spot from the list using its position
            val selectedSpot = spots[position]

            // Create an intent to open SingleSpotActivity and pass the selected spot's ID as an extra
            val intent = Intent(this, SingleSpotActivity::class.java).apply {
                putExtra("id", selectedSpot.id.toString())
            }

            // Start SingleSpotActivity to display details of the selected spot
            startActivity(intent)
        }

        // Find the FloatingActionButton for adding a new spot by its ID
        val fabAddSpot = findViewById<Button>(R.id.fabAddSpot)


        // Set a click listener on the FloatingActionButton to navigate to AddSpotActivity
        fabAddSpot.setOnClickListener {
            val intent = Intent(this, AddSpotActivity::class.java)
            startActivity(intent)  // Start AddSpotActivity when button is clicked
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload spots from API when returning to this activity
        loadSpotsFromApi()
    }

    // Function to load spots from the API using a coroutine
    private fun loadSpotsFromApi() {
        lifecycleScope.launch {
            try {
                // Fetch spots from the API asynchronously
                val apiResponse = fetchAllSpots()

                // Clear existing spots in the list before adding new ones
                spots.clear()

                // Add new spots to the list, converting from DTO to Spot model using extension function
                spots.addAll(apiResponse.spots.map { it.toSpot() })

                // Notify the adapter that the data has changed so it can refresh the UI
                adapter.notifyDataSetChanged()

                // Log success message with the number of spots loaded from API
                Log.d("MainActivity", "Spots loaded from API: ${spots.size}")
            } catch (e: Exception) {
                // Log error message if loading fails due to an exception
                Log.e("MainActivity", "Error loading spots from API", e)
            }
        }
    }
}
