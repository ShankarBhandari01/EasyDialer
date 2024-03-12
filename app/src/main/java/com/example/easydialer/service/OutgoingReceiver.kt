package com.example.easydialer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.TelephonyManager
import timber.log.Timber


class OutgoingReceiver : BroadcastReceiver() {
    var context: Context? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        this.context = context

        if (intent?.action == "android.intent.action.NEW_OUTGOING_CALL") {
            var savedNumber = intent.extras?.getString("android.intent.extra.PHONE_NUMBER")
        } else {
            val stateStr = intent?.extras?.getString(TelephonyManager.EXTRA_STATE)
            val number = intent?.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            var state = 0
            when (stateStr) {
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    state = TelephonyManager.CALL_STATE_IDLE
                }

                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    state = TelephonyManager.CALL_STATE_OFFHOOK
                }

                TelephonyManager.EXTRA_STATE_RINGING -> {
                    state = TelephonyManager.CALL_STATE_RINGING
                }
            }


            if (!number.isNullOrEmpty() && number != "null") {
                onCallStateChanged(context, state, number)
                Timber.tag("TEST :").d("NUMBER =>$number");
                return
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

    }
}

