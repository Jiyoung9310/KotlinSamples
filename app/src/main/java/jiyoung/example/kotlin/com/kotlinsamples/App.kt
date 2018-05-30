package jiyoung.example.kotlin.com.kotlinsamples

import android.app.Application
import com.google.android.gms.ads.MobileAds

/**
 * Created by user on 2018-05-30.
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111")
    }
}