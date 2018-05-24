package jiyoung.example.kotlin.com.kotlinsamples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jiyoung.example.kotlin.com.kotlinsamples.network.NetworkCheckReceiver
import jiyoung.example.kotlin.com.kotlinsamples.network.NetworkModel
import jiyoung.example.kotlin.com.kotlinsamples.network.NetworkModel.setStatus
import jiyoung.example.kotlin.com.kotlinsamples.network.NetworkObserver

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val networkModel = NetworkModel
        val observer = NetworkObserver(this)
        networkModel.registNetworkReceiver(this)
        networkModel.addObserver(observer)
        networkModel.setNetworkCheckListener(NetworkCheckReceiver.onChangeNetworkStatusListener{ status ->
            when (status) {
                NetworkCheckReceiver.WIFI_STATE_ENABLED -> setStatus("wifi", true)

                NetworkCheckReceiver.WIFI_STATE_ENABLING
                    , NetworkCheckReceiver.WIFI_STATE_UNKNOWN
                    , NetworkCheckReceiver.WIFI_STATE_DISABLING
                    , NetworkCheckReceiver.WIFI_STATE_DISABLED -> setStatus("wifi", false)

                NetworkCheckReceiver.NETWORK_STATE_CONNECTED -> setStatus("mobile", true)

                NetworkCheckReceiver.NETWORK_STATE_CONNECTING
                    , NetworkCheckReceiver.NETWORK_STATE_UNKNOWN
                    , NetworkCheckReceiver.NETWORK_STATE_SUSPENDED
                    , NetworkCheckReceiver.NETWORK_STATE_DISCONNECTING
                    , NetworkCheckReceiver.NETWORK_STATE_DISCONNECTED -> setStatus("mobile", false)
            }
            networkModel.execute()
        })

        networkModel.execute()

    }


}
