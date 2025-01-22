package com.example.natena.models

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.natena.MainActivity
import com.example.natena.R
import com.example.natena.network.fetchSpotById
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

// This activity displays details of a single spot
class SingleSpotActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for this activity
        setContentView(R.layout.activity_single_spot)

        // Retrieve the spot ID from the intent extras
        val spotId = intent.getStringExtra("id") ?: return

        // Use lifecycleScope to launch a coroutine
        lifecycleScope.launch {
            try {
                // Fetch the spot details using the ID
                val spot = fetchSpotById(spotId)

                // Find the ImageView in the layout
                val imageView = findViewById<ImageView>(R.id.spotImage)
                // Use Glide to load and display the spot image
                Glide.with(this@SingleSpotActivity)
                    .load(spot.url)
                    .into(imageView)

                // Set the text for various TextViews with spot details
                findViewById<TextView>(R.id.spotName).text = spot.type
                findViewById<TextView>(R.id.spotLocation).text = spot.address
                findViewById<TextView>(R.id.spotDifficulty).text = "Difficulty: ${spot.difficulty}"
                findViewById<TextView>(R.id.spotLatitude).text = "Latitude: ${spot.latitude}"
                findViewById<TextView>(R.id.spotLongitude).text = "Longitude: ${spot.longitude}"

            } catch (e: Exception) {
                // Handle any errors that occur during data fetching
                // TODO: Implement proper error handling (e.g., show an error message to the user)
                e.printStackTrace()
            }
        }

        // Find the home button in the layout
        val home = findViewById<Button>(R.id.home)
        // Set a click listener for the home button
        home.setOnClickListener {
            // Create an intent to navigate back to the MainActivity
            val intent = Intent(this@SingleSpotActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
