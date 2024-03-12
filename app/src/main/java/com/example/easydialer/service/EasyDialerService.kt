package com.example.easydialer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.getActivity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.os.IBinder
import androidx.core.app.NotificationCompat.Builder
import com.example.easydialer.R.drawable.voice_call
import com.example.easydialer.ui.LoginActivity
import com.example.easydialer.utils.Constants.Companion.NOTIFICATION_CHANNEL_ID
import com.example.easydialer.utils.Constants.Companion.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EasyDialerService : Service() {
    private lateinit var outgoingReceiver: OutgoingReceiver
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification: Notification = buildNotification(this)
        startForeground(NOTIFICATION_ID, notification)
        outgoingReceiver = OutgoingReceiver()
        outgoingReceiver.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        outgoingReceiver.stop()
    }

    private fun buildNotification(context: Context): Notification {
        createNotificationChannel(context)
        val notificationIntent = Intent(context, LoginActivity::class.java)
        val pendingIntent = getActivity(
            context,
            0,
            notificationIntent,
            FLAG_IMMUTABLE
        )

        // Build the notification
        return Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Easy Dialer")
            .setContentText("Easy Dialer is running")
            .setSmallIcon(voice_call)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel(context: Context) {
        if (SDK_INT >= O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_NONE
            )
            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
