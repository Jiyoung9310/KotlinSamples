package jiyoung.example.kotlin.com.kotlinsamples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import jiyoung.example.kotlin.com.kotlinsamples.network.NetworkCheckReceiver
import jiyoung.example.kotlin.com.kotlinsamples.network.NetworkModel
import jiyoung.example.kotlin.com.kotlinsamples.network.NetworkModel.setStatus
import jiyoung.example.kotlin.com.kotlinsamples.network.NetworkObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adRequest = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()
        adView.loadAd(adRequest)

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
            //networkModel.execute()
        })

        networkModel.execute()

        button1.setOnClickListener(View.OnClickListener { view -> nullTest(this.setViewText(null)) })

    }

    private fun setViewText(text: String?) : String?{
        return text
    }

    private fun nullTest(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
