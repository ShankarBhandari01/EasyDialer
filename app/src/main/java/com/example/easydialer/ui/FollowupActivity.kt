package com.example.easydialer.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.easydialer.R
import com.example.easydialer.databinding.ActivityFollowupBinding
import com.example.easydialer.models.CampaignResponseItem
import com.example.easydialer.models.FollowUPStatus
import com.example.easydialer.models.FollowUpStatusList
import com.example.easydialer.models.MobileList
import com.example.easydialer.models.MobileListItem
import com.example.easydialer.ui.adaptor.AppAdaptor
import com.example.easydialer.utils.Utils
import com.example.easydialer.utils.Utils.formatDateTime
import com.example.easydialer.utils.Utils.getCurrentDateTimeWithAMPM
import com.example.easydialer.utils.Utils.showDateTimePickerDialog
import com.example.easydialer.utils.Utils.startCall
import com.example.easydialer.viewmodels.CampaignViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class FollowupActivity : AppCompatActivity() {
    private var currentIndex = 0
    private val binding by lazy { ActivityFollowupBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<CampaignViewModel>()
    private lateinit var appAdaptor: AppAdaptor<FollowUPStatus>

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val callDurationMillis = intent?.getLongExtra("call_duration_millis", 0L)
            selectedMobileListItem.status = 1
            selectedMobileListItem.dialed = 1
            selectedMobileListItem.duration = Utils.formatDuration(callDurationMillis ?: 0)
            selectedMobileListItem.dialed_at = formatDateTime()
            Timber.tag("Calling ${selectedMobileListItem.mobile}")
                .e("Selected Mobile data $selectedMobileListItem")
        }
    }

    private lateinit var selectedMobileListItem: MobileListItem

    companion object {
        private lateinit var mobileList: MobileList
        fun getIntent(context: Context, mobileList: MobileList): Intent {
            this.mobileList = mobileList
            return Intent(context, FollowupActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.toolbar.back.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        loadFollowUpStatus()
        checkAutoCall()
        with(binding) {
            toolbar.title.text = "Call FollowUp"
            selectedMobileListItem = mobileList[currentIndex] // current selected mobile
            init()
            binding.phone.setText(selectedMobileListItem.mobile)
            checkCallType()

            datetime.editText?.setText(getCurrentDateTimeWithAMPM())
            datetime.setEndIconOnClickListener {
                showDateTimePickerDialog(this@FollowupActivity) { selectedDateTime ->
                    binding.datetime.editText?.setText(selectedDateTime)
                }
            }

            phoneLayout.setEndIconOnClickListener {
                callnow.performClick()
            }

            callnow.setOnClickListener {
                startCall(binding.phone.text.toString(), this@FollowupActivity)
            }

            stopnext.setOnClickListener {
                handleNextNumber()
            }
        }
        setUpCallListener()

        observes()
    }

    private fun observes() {
        var data = ArrayList<FollowUPStatus>()
        data.add(FollowUPStatus("RNR"))
        data.add(FollowUPStatus("BUSY"))
        data.add(FollowUPStatus("Not Answer"))
        data.add(FollowUPStatus("Not Available"))

        appAdaptor.setData(data)
    }

    private fun init() {
        try {
            appAdaptor = AppAdaptor(context = this@FollowupActivity) {

            }
            binding.list.apply { adapter = appAdaptor }
            val animation: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(
                this,
                R.anim.layout_animation_fall_down
            )
            binding.list.layoutAnimation = animation
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun setUpCallListener() {
        val filter = IntentFilter("${applicationContext.packageName}.CUSTOM_ACTION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(receiver, filter)
        }

    }

    private fun loadFollowUpStatus() {
        viewModel.getCallFollowUpStatus()
    }

    private fun checkCallType() {
        val dispositionList = CampaignDetailsActivity.dispositionList
        if (dispositionList[0].type.equals("Scheduled", true)) {
            binding.tvFollowup.visibility = View.VISIBLE
            binding.mcvFollowupLayout.visibility = View.VISIBLE
        }
    }


    private fun checkAutoCall() {
        if (CampaignDetailsActivity.campaign.mode.equals("AUTO", true)) {
            Toast.makeText(this, "Auto Call detected ", Toast.LENGTH_SHORT).show()
            selectedMobileListItem = mobileList.random()
            startCall(selectedMobileListItem.mobile, this)
        }
    }

    private fun handleNextNumber() {
        if (currentIndex < mobileList.size - 1) {
            currentIndex++
        } else {
            Toast.makeText(this, "Number is not available ", Toast.LENGTH_SHORT).show()
        }
        selectedMobileListItem = mobileList[currentIndex]
        binding.phone.setText(selectedMobileListItem.mobile)
    }

}