package jiyoung.example.kotlin.com.kotlinsamples.filter

import android.content.res.TypedArray
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.yalantis.filter.listener.FilterListener
import com.yalantis.filter.widget.Filter
import jiyoung.example.kotlin.com.kotlinsamples.R
import kotlinx.android.synthetic.main.act_filter.*
import com.yalantis.filter.animator.FiltersListItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import java.nio.file.Files.size
import android.support.v7.util.DiffUtil
import android.support.v4.content.ContextCompat
import com.yalantis.filter.widget.FilterItem
import com.yalantis.filter.adapter.FilterAdapter
import jiyoung.example.kotlin.com.kotlinsamples.R.id.mRecyclerView


class ActFilter : AppCompatActivity(), FilterListener<Tag> {

    lateinit var mAdapter: QuestionsAdapter
    lateinit var mAllQuestions: List<Question>
    lateinit var mFilter: Filter<Tag>
    lateinit var mColors: TypedArray
    lateinit var mTitles: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_filter)

        val config = ImagePipelineConfig
                .newBuilder(this)
                .setDownsampleEnabled(true)
                .build()
        Fresco.initialize(this, config)

        mColors = resources.obtainTypedArray(R.array.colors)
        mTitles = resources.getStringArray(R.array.job_titles)

        mFilter = findViewById<Filter<Tag>>(R.id.myFilter)
        mFilter.adapter = myAdapter(getTags())
        mFilter.listener = this
        //the text to show when there's no selected items
        mFilter.noSelectedItemText = getString(R.string.str_all_selected)
        mFilter.build()

        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mAllQuestions = getQuestions()
        mAdapter = QuestionsAdapter(this, mAllQuestions)

        mRecyclerView.adapter = mAdapter
        mRecyclerView.itemAnimator = FiltersListItemAnimator()


    }

    private fun getTags(): List<Tag> {
        val tags = ArrayList<Tag>()

        for (i in 0 until mTitles.size) {
            tags.add(Tag(mTitles[i], mColors.getColor(i, 0)))
        }

        return tags
    }

    private fun getQuestions(): List<Question> {
        return object : ArrayList<Question>() {
            init {
                add(Question("Carol Bell", "Graphic Designer",
                        "http://kingofwallpapers.com/girl/girl-011.jpg", "Nov 20, 6:12 PM",
                        "What is the first step to transform an idea into an actual project?", object : ArrayList<Tag>() {
                    init {
                        val tag1 = Tag(mTitles[2], mColors.getColor(2,0))
                        add(tag1)
                        add(Tag(mTitles[4], mColors.getColor(4,0)))
                    }
                }))
                add(Question("Melissa Morales", "Project Manager",
                        "http://weknowyourdreams.com/images/girl/girl-03.jpg", "Nov 20, 3:48 AM",
                        "What is your biggest frustration with taking your business/career (in a corporate) to the next level?", object : ArrayList<Tag>() {
                    init {
                        add(Tag(mTitles[1], mColors.getColor(1,0)))
                        add(Tag(mTitles[5], mColors.getColor(5,0)))
                    }
                }))
                add(Question("Rochelle Yingst", "iOS Developer",
                        "http://www.viraldoza.com/wp-content/uploads/2014/03/8876509-lily-pretty-girl.jpg", "Nov 20, 6:12 PM",
                        "What is the first step to transform an idea into an actual project?", object : ArrayList<Tag>() {
                    init {
                        add(Tag(mTitles[7], mColors.getColor(7,0)))
                        add(Tag(mTitles[8], mColors.getColor(8,0)))
                    }
                }))
                add(Question("Lacey Barbara", "QA Engineer",
                        "http://kingofwallpapers.com/girl/girl-019.jpg", "Nov 20, 6:12 PM",
                        "What is the first step to transform an idea into an actual project?", object : ArrayList<Tag>() {
                    init {
                        add(Tag(mTitles[3], mColors.getColor(3,0)))
                        add(Tag(mTitles[9], mColors.getColor(9,0)))
                    }
                }))
                add(Question("Teena Allain", "Android Developer",
                        "http://tribzap2it.files.wordpress.com/2014/09/hannah-simone-new-girl-season-4-cece.jpg", "Nov 20, 6:12 PM",
                        "What is the first step to transform an idea into an actual project?", object : ArrayList<Tag>() {
                    init {
                        add(Tag(mTitles[1], mColors.getColor(1,0)))
                        add(Tag(mTitles[6], mColors.getColor(6,0)))
                    }
                }))
            }
        }
    }

    override fun onFilterDeselected(item: Tag) {

    }

    override fun onFilterSelected(item: Tag) {
        if (item.getText() == mTitles[0]) {
            mFilter.deselectAll()
            mFilter.collapse()
        }
    }

    override fun onFiltersSelected(filters: java.util.ArrayList<Tag>) {
        val newQuestions = findByTags(filters)
        val oldQuestions = mAdapter.questions!!
        mAdapter.questions = newQuestions
        calculateDiff(oldQuestions, newQuestions)
    }

    override fun onNothingSelected() {
        if (mRecyclerView != null) {
            mAdapter.questions = mAllQuestions
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun findByTags(tags: List<Tag>): List<Question> {
        val questions = mutableListOf<Question>()

        for (question in mAllQuestions) {
            for (tag in tags) {
                if (question.hasTag(tag.getText()) && !questions.contains(question)) {
                    questions.add(question)
                }
            }
        }

        return questions
    }

    private fun calculateDiff(oldList: List<Question>, newList: List<Question>) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == newList[newItemPosition]
            }
        }).dispatchUpdatesTo(mAdapter)
    }

    inner class myAdapter(items: List<Tag>) : FilterAdapter<Tag>(items) {

        override fun createView(position: Int, item: Tag): FilterItem {
            val filterItem = FilterItem(this@ActFilter)

            filterItem.strokeColor = mColors.getColor(0, 0)
            filterItem.textColor = mColors.getColor(0, 0)
            filterItem.cornerRadius = 14F
            filterItem.checkedTextColor = ContextCompat.getColor(this@ActFilter, android.R.color.white)
            filterItem.color = ContextCompat.getColor(this@ActFilter, android.R.color.white)
            filterItem.checkedColor = mColors.getColor(position, 0)
            filterItem.text = item.getText()
            filterItem.deselect()

            return filterItem
        }
    }
}
