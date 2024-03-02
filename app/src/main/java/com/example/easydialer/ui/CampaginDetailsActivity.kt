package com.example.easydialer.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.easydialer.databinding.ActivityCampaginDetailsBinding
import com.example.easydialer.models.CampaignResponseItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaginDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCampaginDetailsBinding.inflate(layoutInflater)
    }

    companion object {
        lateinit var campaign: CampaignResponseItem
        fun getIntent(context: Context, campaign: CampaignResponseItem): Intent {
            this.campaign = campaign
            return Intent(context, CampaginDetailsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        with(binding) {
            toolbar.title.text = "Campaign Summary"
            heading.text = campaign.name
            campaignDesc.text = if (campaign.description == null) {
                "N/A"
            } else {
                campaign.description
            }
        }


        binding.calling.setOnClickListener {
            startActivity(Intent(this@CampaginDetailsActivity, FollowupActivity::class.java))
        }


    }
}