package com.example.natena.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.natena.R


//This SpotAdapter class is responsible for creating and populating views for each item in
// the list of spots. It uses the Glide library to efficiently load images, and populates
// TextViews with the spot's type and address information

class SpotAdapter(context: Context, private val spots: List<Spot>) :
    ArrayAdapter<Spot>(context, 0, spots) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the current spot
        val spot = getItem(position)

        // Reuse an existing view or create a new one
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.spot_layout, parent, false
        )

        // Associate spot data with views
        val imageView = view.findViewById<ImageView>(R.id.spotImage)
        val nameTextView = view.findViewById<TextView>(R.id.spotName)
        val locationTextView = view.findViewById<TextView>(R.id.spotLocation)

        // Fill in the data
        spot?.let {
            // Load the image
            Glide.with(context)
                .load(it.url)
                .placeholder(R.drawable.placeholder)
                .into(imageView)
            nameTextView.text = it.type
            locationTextView.text = it.address
        }

        return view
    }
}
