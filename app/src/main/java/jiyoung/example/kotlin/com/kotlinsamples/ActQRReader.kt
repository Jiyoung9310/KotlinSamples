package jiyoung.example.kotlin.com.kotlinsamples

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_qrreader.*

import com.google.zxing.integration.android.IntentIntegrator

class ActQRReader : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_qrreader)


        btn_qr.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.captureActivity = ActZxing::class.java
            integrator.setOrientationLocked(false)
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        tv_result.text = result.contents
        if(result.contents.startsWith("http")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tv_result.text as String?))
            startActivity(intent)
        }
    }
}
