package com.example.easydialer.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.easydialer.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton


@AndroidEntryPoint
class OutgoingReceiver @Inject constructor(): BroadcastReceiver() {

    @Inject
    lateinit var telephonyManagerHandler: TelephonyManagerHandler

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(ctx: Context?, intent: Intent?) {
        if (ctx == null || intent == null) return
        telephonyManagerHandler.onCallEvent = { duration, _ ->
            sendBroadcastMessage(duration, ctx)
        }
    }

    private fun sendBroadcastMessage(callDurationMillis: Long, ctx: Context) {
        val broadcastIntent =
            Intent("${ctx.applicationContext.packageName}.CUSTOM_ACTION").apply {
                putExtra("call_duration_millis", callDurationMillis)
            }
        ctx.sendBroadcast(broadcastIntent)
    }
}
