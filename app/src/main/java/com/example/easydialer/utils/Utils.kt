package com.example.easydialer.utils

import android.Manifest
import android.R.drawable.ic_dialog_alert
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import com.example.easydialer.R.string.app_name
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import org.json.JSONException

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit


object Utils {

    fun hasInternetConnection(context: Context?): Boolean {
        try {
            if (context == null)
                return false
            val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                networkCapabilities.hasTransport(TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } catch (e: Exception) {
            return false
        }
    }

    fun showAlertDialog(context: Context, message: String?) {
        try {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(app_name)
            builder.setMessage(message)
            builder.setIcon(ic_dialog_alert)
            builder.setPositiveButton("OK") { dialogInterface, _ ->
                dialogInterface.dismiss()

            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    private var progressDialog: ProgressDialog? = null
    fun showProgressDialog(message: String?, context: Context?) {
        if (context != null && context !is Activity) {
            return
        }
        if (context != null) {
            val activity = context as Activity
            if (activity.isFinishing || activity.isDestroyed) {
                return
            }
        }
        if (progressDialog != null && progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
            progressDialog = null
        }
        progressDialog = ProgressDialog(context)
        progressDialog?.setMessage(message)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    fun isActivityValid(activity: Activity): Boolean {
        return !activity.isFinishing && !activity.isDestroyed
    }

    fun dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
        }
    }

    fun setDialogMessage(message: String?) {
        if (progressDialog != null) {
            progressDialog!!.setMessage(message)
        }
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun getApiErrorMessage(error: String?): String? {
        var jsonObject: JSONObject? = null
        var errorMessage: String? = null
        return try {
            jsonObject = JSONObject(error)
            errorMessage = jsonObject.getString("message")
            errorMessage
        } catch (e: JSONException) {
            e.localizedMessage
        }
    }


    fun getCurrentDateTimeWithAMPM(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }


    fun showDateTimePickerDialog(context: Context, onDateTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                showTimePickerDialog(context, calendar, onDateTimeSelected)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog(
        context: Context,
        calendar: Calendar,
        onDateTimeSelected: (String) -> Unit
    ) {
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                val selectedDateTime = formatDateTime(calendar)
                onDateTimeSelected(selectedDateTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false // 24-hour format
        )
        timePickerDialog.show()
    }

    fun formatDateTime(calendar: Calendar = Calendar.getInstance()): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun startCall(number: String, context: Context) {
        context.runWithPermissions(Manifest.permission.CALL_PHONE) {
            val intent = Intent(
                Intent.ACTION_CALL,
                Uri.parse(
                    "tel:" + number.trim()
                )
            )
            context.startActivity(intent)
        }
    }

    fun formatDuration(durationMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(durationMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}