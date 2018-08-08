package jiyoung.example.kotlin.com.kotlinsamples

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import kotlinx.android.synthetic.main.act_getnumber.*


class ActGetNumber : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_getnumber)

        btn_getnumber.setOnClickListener {
            val telManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var phoneNum = telManager.line1Number
            if (phoneNum.startsWith("+82")) {
                tv_number.text = phoneNum.replace("+82", "0")
            } else if (phoneNum.isEmpty()) {
                tv_number.text = "번호없음"
            } else {
                tv_number.text = "내 번호는 "+ phoneNum
            }
        }


    }
}
