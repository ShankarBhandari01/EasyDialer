package com.example.easydialer.service

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.example.easydialer.ui.dialogbox.OverlayDialog
import com.example.easydialer.utils.Utils.dialog


class PhoneStateChangeListener(
    var context: Context,
    var intent: Intent,
    var onCallStatusChanged: (Long) -> Unit
) :
    PhoneStateListener() {
    private var callStartTime: Long = 0
    override fun onCallStateChanged(state: Int, phoneNumber: String) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                // Call ended or idle
                if (callStartTime != 0L) {
                    val callDurationMillis = SystemClock.elapsedRealtime() - callStartTime
                    onCallStatusChanged(callDurationMillis)
                    callStartTime = 0
                }
            }

            TelephonyManager.CALL_STATE_RINGING -> {
                // Incoming call
            }

            TelephonyManager.CALL_STATE_OFFHOOK -> {
                // Call in progress
                callStartTime = SystemClock.elapsedRealtime()
            }
        }
        super.onCallStateChanged(state, phoneNumber)

    }
}