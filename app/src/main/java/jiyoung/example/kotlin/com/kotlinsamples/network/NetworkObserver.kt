package jiyoung.example.kotlin.com.kotlinsamples.network

import android.content.Context
import android.widget.Toast

/**
 * Created by user on 2018-05-24.
 */
class NetworkObserver(private val context: Context): Observer {
    override fun update(networkChecker: NetworkChecker) {
        try{
            Thread.sleep(1000)

        } catch (e: Exception) {

        }
        val result = networkChecker.getStatus()
        when(result) {
            true -> Toast.makeText(context,"인터넷 연결 됨", Toast.LENGTH_SHORT).show()
            false -> Toast.makeText(context,"인터넷 연결 안됨", Toast.LENGTH_SHORT).show()
        }
    }
}