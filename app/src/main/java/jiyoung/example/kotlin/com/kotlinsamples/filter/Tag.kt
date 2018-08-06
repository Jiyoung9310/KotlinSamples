package jiyoung.example.kotlin.com.kotlinsamples.filter

import android.support.annotation.NonNull
import com.yalantis.filter.model.FilterModel


class Tag(private var text: String, var color: Int) : FilterModel {

    override fun getText(): String {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Tag) return false

        val tag = o as Tag?

        return if (color != tag!!.color) false else getText() == tag.getText()

    }

    override fun hashCode(): Int {
        var result = getText().hashCode()
        result = 31 * result + color
        return result
    }

}