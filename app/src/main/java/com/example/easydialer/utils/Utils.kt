package com.example.easydialer.utils

import android.R.drawable.ic_dialog_alert
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.text.TextUtils
import android.util.Patterns
import com.example.easydialer.R.string.app_name
import org.json.JSONException

import org.json.JSONObject





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
}