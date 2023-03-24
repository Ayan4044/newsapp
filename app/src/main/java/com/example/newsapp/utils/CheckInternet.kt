package com.example.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


class CheckInternet {

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netinfo = cm.activeNetworkInfo
        return if (netinfo != null && netinfo.isConnectedOrConnecting) {
            val wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            mobile != null && mobile.isConnectedOrConnecting || wifi != null && wifi.isConnectedOrConnecting
        } else false
    }


    fun checkConnection(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw      = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}