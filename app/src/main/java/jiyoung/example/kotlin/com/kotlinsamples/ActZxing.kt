package jiyoung.example.kotlin.com.kotlinsamples

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.journeyapps.barcodescanner.CaptureActivity
import android.graphics.Color.parseColor
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.view.ViewGroup



class ActZxing : CaptureActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val title_view = TextView(this)
        title_view.layoutParams = LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        title_view.setBackgroundColor(Color.parseColor("#00FFFFFF"))
        title_view.setPadding(150, 100, 100, 100)
        title_view.setTextColor(Color.parseColor("#FF7200"))
        title_view.textSize = 30f
        title_view.text = "바코드 / QR 코드 입력화면"

        this.addContentView(title_view, layoutParams)

    }
}
