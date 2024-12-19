package com.example.natena
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView

class SingleSpotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_spot)

        // Récupérer les données transmises via l'intent
        val spotImage = intent.getIntExtra("spotImage", 0)
        val spotName = intent.getStringExtra("spotName")
        val spotLocation = intent.getStringExtra("spotLocation")

        // Associer les données aux vues
        findViewById<ImageView>(R.id.spotImage).setImageResource(spotImage)
        findViewById<TextView>(R.id.spotName).text = spotName
        findViewById<TextView>(R.id.spotLocation).text = spotLocation

        val home = findViewById<Button>(R.id.home)

        home.setOnClickListener {
            val intent = Intent(this@SingleSpotActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

