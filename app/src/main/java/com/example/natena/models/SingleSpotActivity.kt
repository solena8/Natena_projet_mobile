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

class SingleSpotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_spot)

        val spotUrl = intent.getStringExtra("url")        // Changé de spotImage à url
        val spotType = intent.getStringExtra("type")      // Changé de Location à type
        val spotAddress = intent.getStringExtra("address") // Changé de Address à address

        val imageView = findViewById<ImageView>(R.id.spotImage)

        spotUrl?.let {
            Glide.with(this)
                .load(it)
                .into(imageView)
        }

        findViewById<TextView>(R.id.spotName).text = spotType
        findViewById<TextView>(R.id.spotLocation).text = spotAddress

        val home = findViewById<Button>(R.id.home)

        home.setOnClickListener {
            val intent = Intent(this@SingleSpotActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}



