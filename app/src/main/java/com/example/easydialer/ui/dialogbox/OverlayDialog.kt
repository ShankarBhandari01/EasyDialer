package com.example.easydialer.ui.dialogbox


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDialog
import com.example.easydialer.R
import com.example.easydialer.databinding.ActivityOverlayBinding
import com.example.easydialer.ui.FollowupActivity
import com.google.gson.Gson
import timber.log.Timber


class OverlayDialog(context: Context, private var phoneNumber: String?) :
    AppCompatDialog(context, R.style.Theme_EasyDialer) {
    private val binding by lazy { ActivityOverlayBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        var mobile = FollowupActivity.selectedMobileListItem
        Timber.tag("NumberSelected").e(Gson().toJson(mobile))
        binding.phone.text = phoneNumber
        val window = window
        window?.setBackgroundDrawableResource(R.drawable.rounded_corner) // Set the custom background drawable
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        )
        window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
    }


}