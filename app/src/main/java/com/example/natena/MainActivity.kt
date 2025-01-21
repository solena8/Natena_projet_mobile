package com.example.natena

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private lateinit var adapter: SpotAdapter
    private val spots = mutableListOf<Spot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.spotlist)
        adapter = SpotAdapter(this, spots)
        listView.adapter = adapter

        loadSpotsFromApi()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedSpot = spots[position]
            val intent = Intent(this, SingleSpotActivity::class.java).apply {
                putExtra("id", selectedSpot.id.toString())
            }
            startActivity(intent)
        }
    }

    private fun loadSpotsFromApi() {
        lifecycleScope.launch {
            try {
                val apiResponse = fetchAllSpots()
                spots.clear()
                spots.addAll(apiResponse.spots.map { it.toSpot() })
                adapter.notifyDataSetChanged()
                Log.d("MainActivity", "Spots loaded from API: ${spots.size}")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error loading spots from API", e)
            }
        }
    }
}
