package com.ayan.seedsfinal.history.seedDetails

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ayan.seedsfinal.R
import com.ayan.seedsfinal.history.FLOWER_ID

class SeedDetailsActivity : AppCompatActivity() {

    private val flowerDetailViewModel by viewModels<SeedDetailsViewModel> {
        FlowerDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seed_details)

        var currentFlowerId: Long? = null

        /* Connect variables to UI elements. */
        val flowerName: TextView = findViewById(R.id.flower_detail_name)
        val flowerImage: ImageView = findViewById(R.id.flower_detail_image)
        val flowerDescription: TextView = findViewById(R.id.flower_detail_description)
        val removeFlowerButton: Button = findViewById(R.id.remove_button)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentFlowerId = bundle.getLong(FLOWER_ID)
        }

        /* If currentFlowerId is not null, get corresponding flower and set name, image and
        description */
        currentFlowerId?.let {
            val currentFlower = flowerDetailViewModel.getFlowerForId(it)
            flowerName.text = currentFlower?.name
            if (currentFlower?.image == null) {
                flowerImage.setImageResource(R.drawable.rose)
            } else {
                flowerImage.setImageResource(currentFlower.image)
            }
            flowerDescription.text = currentFlower?.description

            removeFlowerButton.setOnClickListener {
                if (currentFlower != null) {
                    flowerDetailViewModel.removeFlower(currentFlower)
                }
                finish()
            }
        }

    }
}