package jiyoung.example.kotlin.com.kotlinsamples.filter

import android.content.Context
import jiyoung.example.kotlin.com.kotlinsamples.R.id.avatar
import com.facebook.drawee.view.SimpleDraweeView
import jiyoung.example.kotlin.com.kotlinsamples.R.id.filter_second
import android.widget.TextView
import jiyoung.example.kotlin.com.kotlinsamples.R.id.filter_first
import jiyoung.example.kotlin.com.kotlinsamples.R.id.text_question
import jiyoung.example.kotlin.com.kotlinsamples.R.id.text_date
import jiyoung.example.kotlin.com.kotlinsamples.R.id.text_job_title
import jiyoung.example.kotlin.com.kotlinsamples.R.id.text_name
import android.support.v7.widget.RecyclerView
import java.nio.file.Files.size
import android.support.v4.content.ContextCompat
import android.graphics.drawable.GradientDrawable
import jiyoung.example.kotlin.com.kotlinsamples.R.layout.item_list
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jiyoung.example.kotlin.com.kotlinsamples.R


class QuestionsAdapter(private val mContext: Context, var questions: List<Question>?) : RecyclerView.Adapter<QuestionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, null, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions!![position]

        holder.avatar.setImageURI(question.authorAvatar)
        holder.textAuthorName.text = question.authorName
        holder.textJobTitle.text = question.authorJobTitle
        holder.textDate.text = question.date
        holder.textQuestion.text = question.text
        val firstTag = question.tags?.get(0)
        holder.firstFilter.text = firstTag?.getText()
        val secondTag = question.tags?.get(1)
        holder.secondFilter.text = secondTag?.getText()

        val drawable = GradientDrawable()
        drawable.cornerRadius = 1000f
        drawable.setColor(firstTag!!.color)
        holder.firstFilter.setBackgroundDrawable(drawable)
        val drawable1 = GradientDrawable()
        drawable1.cornerRadius = 1000f
        drawable1.setColor(secondTag!!.color)
        holder.secondFilter.setBackgroundDrawable(drawable1)
    }

    private fun getColor(color: Int): Int {
        return ContextCompat.getColor(mContext, color)
    }

    override fun getItemCount(): Int {
        return questions!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textAuthorName: TextView
        var textJobTitle: TextView
        var textDate: TextView
        var textQuestion: TextView
        var firstFilter: TextView
        var secondFilter: TextView
        var avatar: SimpleDraweeView

        init {

            textAuthorName = itemView.findViewById(R.id.text_name)
            textJobTitle = itemView.findViewById(R.id.text_job_title)
            textDate = itemView.findViewById(R.id.text_date)
            textQuestion = itemView.findViewById(R.id.text_question)
            firstFilter = itemView.findViewById(R.id.filter_first)
            secondFilter = itemView.findViewById(R.id.filter_second)
            avatar = itemView.findViewById(R.id.avatar)
        }
    }
}