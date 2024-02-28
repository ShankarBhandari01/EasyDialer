package com.example.easydialer.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.easydialer.databinding.ActivityCampaginDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaginDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCampaginDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.back.setOnClickListener {
          onBackPressedDispatcher.onBackPressed()
        }

        binding.toolbar.title.text = "Campaign Summary"

        binding.calling.setOnClickListener {
            startActivity(Intent(this@CampaginDetailsActivity, FollowupActivity::class.java))
        }


    }
}