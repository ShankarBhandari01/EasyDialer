package com.example.easydialer.utils

class Constants {

    companion object {
        /**
         * Notification channel details
         */
        const val NOTIFICATION_CHANNEL_ID = "MyForegroundServiceChannel"
        const val NOTIFICATION_ID = 1
        const val REQUEST_CODE = 0x01;


        /**
         *
         * Error Messages
         */
        const val API_INTERNET_MESSAGE = "No Internet Connection"
        const val API_SOMETHING_WENT_WRONG_MESSAGE = "Something went wrong"
        const val API_FAILED_CODE = "2222"
        const val API_SUCCESS_CODE = "9999"
        const val API_FAILURE_CODE = "5555"
        const val API_INTERNET_CODE = "1111"


        /**
         * Offline Values Lists
         * */
        const val USER_PREFERENCES = "USER_LOGIN"
        const val OFFLINE_DATABASE = "app_database"
        const val NOTE_TABLE = "NOTE_TABLE"

        /**
         * API Lists
         */
        const val BASE_URL = "https://simplydialer.in/public/api/"
        const val API_CAMPAIGN = "campaign"
        const val API_KEY = ""
        const val API_AGENT = "agent"

    }
}