package com.example.natena.models

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.natena.MainActivity
import com.example.natena.R
import com.example.natena.network.SurfSpotApi
import kotlinx.coroutines.launch




class AddSpotActivity : AppCompatActivity() {
    private lateinit var etType: EditText
    private lateinit var etAddress: EditText
    private lateinit var etImageUrl: EditText
    private lateinit var etDifficulty: EditText
    private lateinit var etLatitude: EditText
    private lateinit var etLongitude: EditText
    private lateinit var btnSubmit: Button
    private lateinit var home: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_spot)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        etType = findViewById(R.id.etType)
        etAddress = findViewById(R.id.etAddress)
        etImageUrl = findViewById(R.id.etImageUrl)
        etDifficulty = findViewById(R.id.etDifficulty)
        etLatitude = findViewById(R.id.etLatitude)
        etLongitude = findViewById(R.id.etLongitude)
        btnSubmit = findViewById(R.id.btnSubmit)
        home = findViewById<Button>(R.id.home)

    }

    private fun setupListeners() {
        btnSubmit.setOnClickListener {
            if (validateInput()) {
                submitSpot()
            }
        }
        // Set a click listener for the home button
        home.setOnClickListener {
            // Create an intent to navigate back to the MainActivity
            val intent = Intent(this@AddSpotActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(): Boolean {
        // Add validation logic here
        return true
    }

    private fun submitSpot() {
        val newSpot = SpotDto(
            id = 0, // The API will assign an ID
            type = etType.text.toString(),
            address = etAddress.text.toString(),
            url = etImageUrl.text.toString(),
            main = 1,
            latitude = etLatitude.text.toString().toDoubleOrNull() ?: 0.0,
            longitude = etLongitude.text.toString().toDoubleOrNull() ?: 0.0,
            difficulty = etDifficulty.text.toString().toIntOrNull() ?: 1
        )

        lifecycleScope.launch {
            try {
                val response = SurfSpotApi.retrofitService.addSpot(newSpot)
                if (response.isSuccessful) {
                    Toast.makeText(this@AddSpotActivity, "Spot added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddSpotActivity, "Failed to add spot", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AddSpotActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }


        }

    }
}
