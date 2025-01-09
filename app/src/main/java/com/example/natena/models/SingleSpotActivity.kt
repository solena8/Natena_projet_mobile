package com.example.natena
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class SingleSpotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_spot)

        // Récupérer les données transmises via l'intent
        val spotImage = intent.getStringExtra("spotImage")
        val spotName = intent.getStringExtra("spotName")
        val spotLocation = intent.getStringExtra("spotLocation")

        // Associer les données aux vues
        val imageView = findViewById<ImageView>(R.id.spotImage)

        //Chargement de l'image
        spotImage?.let {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.placeholder)
                .into(imageView)
        }

        findViewById<TextView>(R.id.spotName).text = spotName
        findViewById<TextView>(R.id.spotLocation).text = spotLocation

        val home = findViewById<Button>(R.id.home)

        home.setOnClickListener {
            val intent = Intent(this@SingleSpotActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

