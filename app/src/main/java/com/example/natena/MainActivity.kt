package com.example.natena

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.AbsListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.natena.models.SingleSpotActivity
import com.example.natena.models.Spot
import com.example.natena.models.SpotAdapter
import com.example.natena.models.SpotDto
import com.example.natena.models.toSpot
import com.example.natena.network.fetchAllItems
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: SpotAdapter
    private val spots = mutableListOf<Spot>()

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
        adapter = SpotAdapter(this, spots)
        listView.adapter = adapter

        listView.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                                  visibleItemCount: Int, totalItemCount: Int) {
                // Gérer le scroll si nécessaire
            }

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                // Gérer les changements d'état du scroll si nécessaire
            }
        })

        loadSpotsFromApi()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedSpot = spots[position]
            val intent = Intent(this, SingleSpotActivity::class.java).apply {
                putExtra("spotImage", selectedSpot.url)
                putExtra("Location", selectedSpot.type)
                putExtra("Address", selectedSpot.address)
            }
            startActivity(intent)
        }
    }

    private fun loadSpotsFromApi() {
        lifecycleScope.launch {
            try {
                val apiResponse = fetchAllItems()
                val newSpots = convertApiResponseToSpots(apiResponse)
                spots.clear()
                spots.addAll(newSpots)
                adapter.notifyDataSetChanged()
                Log.d("MainActivity", "Spots loaded from API: ${spots.size}")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error loading spots from API", e)
            }
        }
    }

    private fun convertApiResponseToSpots(apiResponse: List<SpotDto>): List<Spot> {
        return try {
            apiResponse.map { spotDto -> spotDto.toSpot() }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error converting API response", e)
            emptyList()
        }
    }
}
