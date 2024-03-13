package com.example.easydialer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.SystemClock
import android.telephony.TelephonyManager
import org.joda.time.DateTime
import org.joda.time.Duration
import timber.log.Timber


class OutgoingReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "PhoneStateReceiver"
    }

    var context: Context? = null
    private var callStartTime: Long = 0
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (intent.action == "android.intent.action.NEW_OUTGOING_CALL") {
            // Outgoing call
            val savedNumber = intent.getStringExtra("android.intent.extra.PHONE_NUMBER")
            Timber.tag(TAG).d("Outgoing call: $savedNumber")
            callStartTime = System.currentTimeMillis() // Save the start time of the call
        } else {
            // Incoming call or call state changed
            val stateStr = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            var state = 0

            when (stateStr) {
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    Timber.tag(TAG).d("Incoming call ended. Number: $number")
                    state = TelephonyManager.CALL_STATE_IDLE

                    // If this was an outgoing call, calculate the call duration
                    if (callStartTime > 0) {
                        val durationMillis = System.currentTimeMillis() - callStartTime
                        val durationSeconds = durationMillis / 1000
                        Timber.tag(TAG).d("Outgoing call duration: $durationSeconds seconds")
                        callStartTime = 0 // Reset start time
                    }
                }

                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    Timber.tag(TAG).d("Call answered. Number: $number")
                    state = TelephonyManager.CALL_STATE_OFFHOOK
                }
            }

            if (!number.isNullOrEmpty() && number != "null") {
                // Handle call state changes
                onCallStateChanged(context, state, number)
                Timber.tag(TAG).d("Number: $number")
            }
        }
    }

    fun start() {
        val intentFilter = IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL)
        context?.registerReceiver(this, intentFilter)
    }

    fun stop() {
        context?.unregisterReceiver(this)
    }

    private fun onCallStateChanged(context: Context?, state: Int, number: String) {
        when (state) {
            TelephonyManager.CALL_STATE_OFFHOOK -> {
                // Call answered
                Timber.tag("TEST :").d("Call answered. Number: $number")
                callStartTime = SystemClock.elapsedRealtime()
            }

            TelephonyManager.CALL_STATE_IDLE -> {
                // Call ended
                val callDuration = calculateCallDuration()
                Timber.tag("TEST :")
                    .d("Call ended. Duration: $callDuration. Number: $number")
            }
        }
    }


    private fun calculateCallDuration(): Long {
        val callEndTime = DateTime(System.currentTimeMillis())
        val callStartTimeDateTime = DateTime(callStartTime)
        val duration = Duration(callStartTimeDateTime, callEndTime)
        return duration.standardSeconds
    }



}

