package com.example.easydialer.service

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.example.easydialer.ui.dialogbox.OverlayDialog


class PhoneStateChangeListener(
    var context: Context,
    var intent: Intent,
    var onCallStatusChanged: (Long) -> Unit
) :
    PhoneStateListener() {
    lateinit var dialog: OverlayDialog
    private var callStartTime: Long = 0
    override fun onCallStateChanged(state: Int, phoneNumber: String) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                // Call ended or idle
                if (callStartTime != 0L) {
                    val callDurationMillis = SystemClock.elapsedRealtime() - callStartTime
                    onCallStatusChanged(callDurationMillis)
                    callStartTime = 0
                    if (::dialog.isInitialized)
                        dialog.hide()

                }
            }

            TelephonyManager.CALL_STATE_RINGING -> {
                // Incoming call
            }

            TelephonyManager.CALL_STATE_OFFHOOK -> {
                // Call in progress
                callStartTime = SystemClock.elapsedRealtime()
                val extras = intent.extras
                if (extras != null) {
                    val incomingNumber = extras.getString("incoming_number")
                    dialog = OverlayDialog(context.applicationContext, incomingNumber)
                    dialog.show()

                }


            }
        }
        super.onCallStateChanged(state, phoneNumber)

    }
}