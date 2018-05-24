package jiyoung.example.kotlin.com.kotlinsamples.network

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.text.TextUtils

/**
 * Created by user on 2018-05-23.
 */
object NetworkModel: NetworkChecker() {
    private var wifiState: Boolean = false
    private var mobileState: Boolean = false
    private var networkStatusListener = NetworkCheckReceiver.onChangeNetworkStatusListener { status -> onChangedNetwork(status) }

    private lateinit var mNetworkCheckReceiver: NetworkCheckReceiver
    private var mListener: NetworkCheckReceiver.onChangeNetworkStatusListener? = null


    fun registNetworkReceiver(context: Context) {
        mNetworkCheckReceiver = NetworkCheckReceiver(context)
        context.registerReceiver(mNetworkCheckReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        context.registerReceiver(mNetworkCheckReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }

    fun setNetworkCheckListener(listener: NetworkCheckReceiver.onChangeNetworkStatusListener?) {
        mListener = listener ?: networkStatusListener
        mNetworkCheckReceiver.setOnChangeNetworkStatusListener(mListener)
    }

    private fun onChangedNetwork(status: Int): Boolean {
        when (status) {
            NetworkCheckReceiver.WIFI_STATE_ENABLED -> wifiState = true
            NetworkCheckReceiver.WIFI_STATE_ENABLING, NetworkCheckReceiver.WIFI_STATE_UNKNOWN, NetworkCheckReceiver.WIFI_STATE_DISABLING, NetworkCheckReceiver.WIFI_STATE_DISABLED -> wifiState = false

            NetworkCheckReceiver.NETWORK_STATE_CONNECTED -> mobileState = true
            NetworkCheckReceiver.NETWORK_STATE_CONNECTING, NetworkCheckReceiver.NETWORK_STATE_UNKNOWN, NetworkCheckReceiver.NETWORK_STATE_SUSPENDED, NetworkCheckReceiver.NETWORK_STATE_DISCONNECTING, NetworkCheckReceiver.NETWORK_STATE_DISCONNECTED -> mobileState = false
        }
        return wifiState || mobileState
    }

    fun setStatus(type:String, status: Boolean) {
        if(TextUtils.equals(type, "wifi")) {
            wifiState = status
        } else if(TextUtils.equals(type, "mobile")) {
            mobileState = status
        }
    }

    override fun getStatus(): Boolean {
        return wifiState || mobileState
    }

    override fun execute() {
        notifyObservers()
    }

}