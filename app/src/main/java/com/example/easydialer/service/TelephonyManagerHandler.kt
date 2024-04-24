package com.example.easydialer.service

import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.telephony.PhoneStateListener
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("DEPRECATION")
class TelephonyManagerHandler @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private var callStartTime: Long = 0
    private val telephonyManager: TelephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    private var phoneStateListener: PhoneStateListener? = null
    private var telephonyCallback: TelephonyCallback? = null

    var onCallEvent: ((Long, TelephonyManagerHandler) -> Unit)? = null

    init {
        startListening()
    }

    private fun startListening() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            telephonyCallback = object : TelephonyCallback(), TelephonyCallback.CallStateListener {
                override fun onCallStateChanged(state: Int) {
                    handleCallStateChange(state)
                }
            }
            telephonyManager.registerTelephonyCallback(context.mainExecutor, telephonyCallback!!)
        } else {
            phoneStateListener = object : PhoneStateListener() {
                @Deprecated("Deprecated in Java")
                override fun onCallStateChanged(state: Int, incomingNumber: String?) {
                    handleCallStateChange(state)
                }
            }
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }
    }

    fun stopListening() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            telephonyCallback?.let { telephonyManager.unregisterTelephonyCallback(it) }
        } else {
            phoneStateListener?.let {
                telephonyManager.listen(
                    it,
                    PhoneStateListener.LISTEN_NONE
                )
            }
        }
    }

    private fun handleCallStateChange(state: Int) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                // Call ended or idle
                if (callStartTime != 0L) {
                    val callDurationMillis = SystemClock.elapsedRealtime() - callStartTime
                    onCallEvent?.invoke(callDurationMillis, this)
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

}

