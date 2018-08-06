package jiyoung.example.kotlin.com.kotlinsamples

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.act_mancj.*
import android.widget.Toast
import android.view.Gravity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log


class ActMancj : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener, PopupMenu.OnMenuItemClickListener {


    lateinit var lastSearches: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_mancj)

        init()
    }

    private fun init() {
        svMancj.setHint("힌트")
        svMancj.setSpeechMode(true)
        svMancj.setOnSearchActionListener(this)
        //lastSearches = loadSearchSuggestionFromDisk()
        //svMancj.lastSuggestions = list
        svMancj.inflateMenu(R.menu.menu_main)
        svMancj.menu.setOnMenuItemClickListener(this@ActMancj)
        svMancj.setCardViewElevation(10)
        svMancj.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                Log.d("LOG_TAG", javaClass.simpleName + " text changed " + svMancj.text)
            }

            override fun afterTextChanged(editable: Editable) {

            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        //saveSearchSuggestionToDisk(svMancj.lastSuggestions)
    }

    override fun onButtonClicked(buttonCode: Int) {
        /*when (buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> drawer.openDrawer(Gravity.LEFT)
            MaterialSearchBar.BUTTON_SPEECH -> openVoiceRecognizer()
        }*/
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        val s = if (enabled) "enabled" else "disabled"
        Toast.makeText(this, "Search $s", Toast.LENGTH_SHORT).show()
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        startSearch(text.toString(), true, null, true);
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        return true
    }
}
