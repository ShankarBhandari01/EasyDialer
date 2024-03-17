package com.example.easydialer.ui


import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.easydialer.R
import com.example.easydialer.databinding.ActivityTeleDialerBinding
import com.example.easydialer.models.CampaignResponse
import com.example.easydialer.models.CampaignResponseItem
import com.example.easydialer.ui.adaptor.AppAdaptor
import com.example.easydialer.utils.ApiResultHandler
import com.example.easydialer.viewmodels.CampaignViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TelDialerActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityTeleDialerBinding.inflate(layoutInflater)
    }
    private val campaignViewModel by viewModels<CampaignViewModel>()


    private lateinit var appAdaptor: AppAdaptor<CampaignResponseItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            toolbar.back.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            toolbar.title.text = "Select Campaign"
        }

        init()
        getCampaign()
        observer()
    }


    private fun init() {
        try {
            appAdaptor = AppAdaptor {
                startActivity(CampaignDetailsActivity.getIntent(this, it))
            }
            binding.list.apply { adapter = appAdaptor }
            val animation: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(
                this@TelDialerActivity,
                R.anim.layout_animation_fall_down
            )
            binding.list.layoutAnimation = animation
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun observer() {
        try {
            campaignViewModel.responseCampaign.observe(this) { response ->
                val apiResultHandler = ApiResultHandler<CampaignResponse>(this@TelDialerActivity,
                    onLoading = {
                        binding.progress.visibility = View.VISIBLE
                    },
                    onSuccess = { data ->
                        binding.progress.visibility = View.GONE
                        data?.let { appAdaptor.setData(it) }
//                        binding.swipeRefreshLayout.isRefreshing = false
                    },
                    onFailure = {
                        binding.progress.visibility = View.GONE
                    })
                apiResultHandler.handleApiResult(response)
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private fun getCampaign() {
        try {
            campaignViewModel.getCampaign()
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}