package com.example.easydialer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Start your service here
            val serviceIntent = Intent(context, EasyDialerService::class.java)
            context.startService(serviceIntent)
        }
    }
}