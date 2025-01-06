package com.example.natena

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.natena.models.Spot

class SpotAdapter(context: Context, private val spots: List<Spot>) :
    ArrayAdapter<Spot>(context, 0, spots) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Récupère le spot actuel
        val spot = getItem(position)

        // Réutilise une vue existante ou en crée une nouvelle
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.spot_layout, parent, false
        )

        //@Todo : remplacer ces lignes par la fonction associateDataToRightSpot du fichier FirstDataJson.kt
        // Associe les données du spot aux vues
        val imageView = view.findViewById<ImageView>(R.id.spotImage)
        val nameTextView = view.findViewById<TextView>(R.id.spotName)
        val locationTextView = view.findViewById<TextView>(R.id.spotLocation)

        // Remplit les données
        spot?.let {
            imageView.setImageResource(it.spotImage)
            nameTextView.text = it.spotName
            locationTextView.text = it.spotLocation
        }

        return view
    }
}
