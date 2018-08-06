package jiyoung.example.kotlin.com.kotlinsamples

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lapism.searchview.SearchView
import jiyoung.example.kotlin.com.kotlinsamples.R.id.svLapism
import jiyoung.example.kotlin.com.kotlinsamples.db.SearchKeyAdapter
import jiyoung.example.kotlin.com.kotlinsamples.db.SearchKeyItem
import jiyoung.example.kotlin.com.kotlinsamples.db.SearchKeyTable
import kotlinx.android.synthetic.main.act_lapism.*
const val EXTRA_KEY_OPTION = "EXTRA_KEY_OPTION"
class ActLapism : AppCompatActivity() {
    lateinit var mActivity: Activity
    lateinit var mHistoryDatabase: SearchKeyTable
    private val MAX_SEARCH_HISTORY_SIZE = 5



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_lapism)
        mActivity = svLapism.context as Activity
        mHistoryDatabase = SearchKeyTable(mActivity)
        mHistoryDatabase.setHistorySize(MAX_SEARCH_HISTORY_SIZE)

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
                    searchData(query)
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

        val searchAdapter = SearchKeyAdapter(mActivity)
        searchAdapter.addOnItemClickListener(object : SearchKeyAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val textView = view.findViewById<TextView>(R.id.textView_item_text)
                val query = textView.text.toString()
                searchData(query)
                svLapism.setQuery("", false)
                svLapism.close(false)
            }
        })
        searchAdapter.setHasStableIds(true)
    }

    fun searchData(query: String) {
        // history add
        mHistoryDatabase.addItem(SearchKeyItem(SearchKeyTable.SEARCH_KEY_VOD, query))

        val intent = Intent(mActivity, ActSearch::class.java)
        //intent.putExtra(ActSearch.EXTRA_KEY_OPTION, createSearchOptions(query))
        mActivity.startActivity(intent)
    }
}
