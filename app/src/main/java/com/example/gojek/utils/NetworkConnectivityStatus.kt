package com.example.gojek.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnectivityStatus private constructor() {
    companion object {
        val instance = NetworkConnectivityStatus()
        var context: Context? = null
        fun getInstance(activityContext: Context): NetworkConnectivityStatus {
            context = activityContext.applicationContext
            return instance
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}