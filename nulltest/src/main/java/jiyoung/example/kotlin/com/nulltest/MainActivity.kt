package jiyoung.example.kotlin.com.nulltest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun set(a: String, b: String?) {

    }

    fun Test(): Int {
        var temp: String? =  null
        var size = -1
        if(temp != null) {
            size = temp.length
        }

        return size
    }
}
