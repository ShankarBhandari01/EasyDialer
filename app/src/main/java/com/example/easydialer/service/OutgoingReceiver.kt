package com.example.easydialer.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.CallLog
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import java.util.Date


class OutgoingReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "PhoneStateReceiver"
    }

    var context: Context? = null

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        val pscl = PhoneStateChangeListener(context)
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        tm.listen(pscl, PhoneStateListener.LISTEN_CALL_STATE)
    }

    fun start() {
        val intentFilter = IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL)
        context?.registerReceiver(this, intentFilter)
    }

    fun stop() {
        context?.unregisterReceiver(this)
    }

}

