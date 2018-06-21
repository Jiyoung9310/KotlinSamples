package jiyoung.example.kotlin.com.kotlinsamples

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.lapism.searchview.SearchView
import kotlinx.android.synthetic.main.act_lapism.*

class ActLapism : AppCompatActivity() {
    lateinit var mActivity: Activity
    //lateinit var mHistoryDatabase: SearchKeyTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_lapism)
        mActivity = svLapism.context as Activity

        init(true)
    }


    private fun init(isSearchMode: Boolean) {
        // SearchView initialize
        if (isSearchMode) {
            svLapism.version = SearchView.VERSION_TOOLBAR
            svLapism.setArrowOnly(false)
            svLapism.setOnMenuClickListener(SearchView.OnMenuClickListener { mActivity.finish() })
        } else {
            svLapism.version = SearchView.VERSION_MENU_ITEM
        }

        svLapism.setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM)
        svLapism.setTheme(SearchView.THEME_LIGHT, true)
        svLapism.setHint("검색어를 입력하세요.")
        svLapism.setVoice(false)
        svLapism.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!TextUtils.isEmpty(query) && query.length > 2) {
                    //searchData(query)
                    svLapism.close(false)
                } else {
                    Toast.makeText(mActivity, "검색어는 3자 이상 입력이 가능합니다.", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
}
