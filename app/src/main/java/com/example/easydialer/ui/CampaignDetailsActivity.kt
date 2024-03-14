package com.example.easydialer.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.easydialer.databinding.ActivityCampaginDetailsBinding
import com.example.easydialer.models.AgentList
import com.example.easydialer.models.CampaignResponse
import com.example.easydialer.models.CampaignResponseItem
import com.example.easydialer.models.DispositionItem
import com.example.easydialer.models.DispositionList
import com.example.easydialer.models.MobileList
import com.example.easydialer.utils.ApiResultHandler
import com.example.easydialer.utils.Utils
import com.example.easydialer.viewmodels.CampaignViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCampaginDetailsBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<CampaignViewModel>()

    companion object {
        lateinit var dispositionList: DispositionList
        lateinit var campaign: CampaignResponseItem
        fun getIntent(context: Context, campaign: CampaignResponseItem): Intent {
            this.campaign = campaign
            return Intent(context, CampaignDetailsActivity::class.java)
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

        getCampaignDetails()
        binding.calling.setOnClickListener {
            viewModel.getCampaignMobile(campaign.id)
        }
        observers()

    }

    private fun getCampaignDetails() {
        viewModel.getCampaignAgent(campaign.id)
        viewModel.getCampaignDisposition(campaign.id)
    }

    private fun observers() {
        try {
            viewModel.responseCampaignAgent.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<AgentList>(this,
                    onLoading = {
                        Utils.showProgressDialog("Loading Campaign Agent", this)
                    },
                    onSuccess = {
                        Utils.dismissProgressDialog()
                        binding.toolbar.title.text = it?.get(0)?.name

                    },
                    onFailure = {
                        Utils.dismissProgressDialog()
                    })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }

        try {
            viewModel.responseCampaignMobile.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<MobileList>(this,
                    onLoading = {
                        Utils.showProgressDialog("Loading Campaign Mobile Numbers", this)
                    },
                    onSuccess = {
                        Utils.dismissProgressDialog()
                        startActivity(
                            it?.let { it1 -> FollowupActivity.getIntent(this, it1) }
                        )
                    },
                    onFailure = {
                        Utils.dismissProgressDialog()
                    }
                )
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }


        try {
            viewModel.responseCampaignDisposition.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<DispositionList>(this,
                    onLoading = {
                        // Utils.showProgressDialog("Loading Campaign Mobile Numbers", this)
                    },
                    onSuccess = {
                        // Utils.dismissProgressDialog()
                        if (it != null) {
                            dispositionList = it
                        }
                    },
                    onFailure = {
                        // Utils.dismissProgressDialog()
                    }
                )
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}