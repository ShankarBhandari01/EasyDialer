package com.example.easydialer.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import com.example.easydialer.models.MobileListItem
import com.google.gson.Gson
import timber.log.Timber


class OutgoingReceiver : BroadcastReceiver() {
    var context: Context? = null

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val pscl = PhoneStateChangeListener(context, intent) { callDurationMillis ->
            val broadcastIntent =
                Intent("${context.applicationContext.packageName}.CUSTOM_ACTION").apply {
                    putExtra("call_duration_millis", callDurationMillis)
                }
            context.sendBroadcast(broadcastIntent)
        }
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
