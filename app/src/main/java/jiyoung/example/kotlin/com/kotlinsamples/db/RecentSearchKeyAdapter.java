package jiyoung.example.kotlin.com.kotlinsamples.db;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import jiyoung.example.kotlin.com.kotlinsamples.R;


/**
 * Created by jiyoung on 2017-06-08.
 */

public class RecentSearchKeyAdapter extends RecyclerView.Adapter<RecentSearchKeyAdapter.ViewHolder> {

    Activity mActivity;
    public List<SearchKeyItem> mData;
    private RecentSearchKeyAdapterListener mListener;

    public static final String LIST_TYPE_HEADER           = "LIST_TYPE_HEADER";
    public static final String LIST_TYPE_CONTENT          = "LIST_TYPE_CONTENT";

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CONTENT = 1;

    public RecentSearchKeyAdapter(Activity activity, List<SearchKeyItem> data) {
        this.mActivity=activity;
        this.mData=data;
    }

    public void setLectureSearchKeyAdapterListener(RecentSearchKeyAdapterListener listener) {
        mListener = listener;
    }

    public void setData(ArrayList<SearchKeyItem> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (int) mData.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_HEADER) {
            holder = new HeaderViewHolder(inflater.inflate(R.layout.section_header_lecture_search, parent, false));
        }else if (viewType == TYPE_CONTENT) {
            holder = new ContentsViewHolder(inflater.inflate(R.layout.search_item_lecture, parent, false));
        }

        if( holder != null ) {
            holder.createHolder();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindHolder(mData.get(position));
        //mPosition = position;
    }

    public class HeaderViewHolder extends ViewHolder {
		TextView mTvTitle;
		LinearLayout mLlDeleteAll;
        public HeaderViewHolder(View view) {
            super(view);
            mTvTitle = view.findViewById(R.id.tvText);
            mLlDeleteAll = view.findViewById(R.id.llDeleteAll);
            mTvTitle.setText(mActivity.getString(R.string.search_key_recent));
            mLlDeleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null) {
                        mListener.onClickAllDelete();
                    }
                }
            });
        }

        @Override
        public void bindHolder(SearchKeyItem item) {
            super.bindHolder(item);
        }
    }

    public class ContentsViewHolder extends ViewHolder {
		RelativeLayout mRlContent;
		TextView mTvText;
		//ImageButton mIbDelete;
        public ContentsViewHolder(View view) {
            super(view);
            mRlContent = view.findViewById(R.id.search_view_item);
            mTvText = view.findViewById(R.id.textView_item_text);
            //mIbDelete = view.findViewById(R.id.btnDelete);
        }

        @Override
        public void createHolder() {
            super.createHolder();
            mRlContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null) {
                        //mListener.onClickViewDetail(mData.get(mPosition));
                        mListener.onClickViewDetail(mSearchKeyItem);
                    }
                }
            });
            /*mIbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null) {
                        mListener.onClickDelete(mSearchKeyItem);
                    }
                }
            });*/
        }

        @Override
        public void bindHolder(SearchKeyItem item) {
            super.bindHolder(item);
            String content = (String) item.getText();
            mTvText.setText(content);
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public SearchKeyItem mSearchKeyItem;

        public ViewHolder(View view) {
            super(view);
        }

        public void createHolder() {
            // Event handle
        }

        public void bindHolder(SearchKeyItem item) {
            // Bind data
            mSearchKeyItem = item;
        }
    }

    public interface RecentSearchKeyAdapterListener {
        public void onClickDelete(SearchKeyItem i);
        public void onClickViewDetail(SearchKeyItem i);
        public void onClickAllDelete();
    }
}
