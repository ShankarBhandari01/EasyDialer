package com.example.easydialer.service

import android.content.Context
import android.os.SystemClock
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import timber.log.Timber
import java.util.concurrent.TimeUnit


class PhoneStateChangeListener(private val context: Context) : PhoneStateListener() {
    private var callStartTime: Long = 0

    override fun onCallStateChanged(state: Int, phoneNumber: String?) {
        super.onCallStateChanged(state, phoneNumber)
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                // Call ended or idle
                if (callStartTime != 0L) {
                    val callDurationMillis = SystemClock.elapsedRealtime() - callStartTime
                    val callDuration = formatDuration(callDurationMillis)
                    Timber.tag("test").e("Call duration $callDuration")


                    // Handle call duration here (callDuration is formatted as HH:MM:SS)
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
    }

    private fun formatDuration(durationMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}