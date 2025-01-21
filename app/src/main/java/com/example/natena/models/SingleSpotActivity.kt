package com.example.natena.models

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


class SingleSpotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_spot)

        val spotId = intent.getStringExtra("id") ?: return

        lifecycleScope.launch {
            try {
                val spot = fetchSpotById(spotId)

                val imageView = findViewById<ImageView>(R.id.spotImage)
                Glide.with(this@SingleSpotActivity)
                    .load(spot.url)
                    .into(imageView)

                findViewById<TextView>(R.id.spotName).text = spot.type
                findViewById<TextView>(R.id.spotLocation).text = spot.address
                findViewById<TextView>(R.id.spotLatitude).text = "Latitude: ${spot.latitude}"
                findViewById<TextView>(R.id.spotLongitude).text = "Longitude: ${spot.longitude}"

            } catch (e: Exception) {
                // Handle error (e.g., show an error message)
                e.printStackTrace()
            }
        }

        val home = findViewById<Button>(R.id.home)
        home.setOnClickListener {
            val intent = Intent(this@SingleSpotActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}


