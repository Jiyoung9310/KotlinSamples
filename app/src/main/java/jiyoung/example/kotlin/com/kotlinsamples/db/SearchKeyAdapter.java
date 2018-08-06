package jiyoung.example.kotlin.com.kotlinsamples.db;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.lapism.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jiyoung.example.kotlin.com.kotlinsamples.R;

/**
 * SearchView library 의 DB 대신 Search History 관리 DB를 별도 만들었으며, 해당 DB 를 이용하기 위해 <br>
 * Adapter 클래스를 분리함 <br><br>
 *
 * Created by jongsic.kim on 2017-06-27.
 */
public class SearchKeyAdapter extends RecyclerView.Adapter<SearchKeyAdapter.ResultViewHolder>
    implements Filterable {

    private static final int MAX_HISTORY_SIZE = 5;

    final private SearchKeyTable mDatabase;
    private Context mContext;
    private int mSearchKey;                 // DataBase Key
    protected String key = "";              // Search Keyword (for highlighting)

    protected List<OnItemClickListener> mItemClickListeners;
    private List<SearchKeyItem> mResultList = new ArrayList<>();            // 검색 결과 List
    private List<SearchKeyItem> mSuggestionsList = new ArrayList<>();       // 검색 결과 이 외에 제안키워드 리스트

    public SearchKeyAdapter(Context context) {
        this.mContext = context;
        mDatabase = new SearchKeyTable(context);
        mDatabase.setHistorySize(MAX_HISTORY_SIZE);
        mSearchKey = SearchKeyTable.SEARCH_KEY_VOD;

        getFilter().filter("");
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // SearchView library 그대로 사용
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.search_item, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder viewHolder, int position) {
        SearchKeyItem item = mResultList.get(position);

        viewHolder.icon_left.setImageResource(com.lapism.searchview.R.drawable.ic_search_black_24dp);
        viewHolder.icon_left.setColorFilter(SearchView.getIconColor(), PorterDuff.Mode.SRC_IN);
        viewHolder.text.setTypeface((Typeface.create(SearchView.getTextFont(), SearchView.getTextStyle())));
        viewHolder.text.setTextColor(SearchView.getTextColor());

        String itemText = item.getText().toString();
        String itemTextLower = itemText.toLowerCase(Locale.getDefault());

        if (itemTextLower.contains(key) && !key.isEmpty()) {
            SpannableString s = new SpannableString(itemText);
            s.setSpan(new ForegroundColorSpan(SearchView.getTextHighlightColor()), itemTextLower.indexOf(key), itemTextLower.indexOf(key) + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.text.setText(s, TextView.BufferType.SPANNABLE);
        } else {
            viewHolder.text.setText(item.getText().toString());
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (!TextUtils.isEmpty(constraint)) {
                    key = constraint.toString().toLowerCase(Locale.getDefault());

                    List<SearchKeyItem> results = new ArrayList<>(); // 검색 결과
                    List<SearchKeyItem> history = new ArrayList<>(); //
                    List<SearchKeyItem> databaseAllItems = mDatabase.getAllItems(mSearchKey);

                    if (!databaseAllItems.isEmpty()) {
                        history.addAll(databaseAllItems);
                    }
                    history.addAll(mSuggestionsList);

                    for (SearchKeyItem item : history) {
                        String string = item.getText().toString().toLowerCase(Locale.getDefault());
                        if (string.contains(key)) {
                            results.add(item);
                        }
                    }

                    if (results.size() > 0) {
                        filterResults.values = results;
                        filterResults.count = results.size();
                    }
                } else {
                    key = "";
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<SearchKeyItem> dataSet = new ArrayList<>();

                if (results.count > 0) {
                    List<?> result = (ArrayList<?>) results.values;
                    for (Object object : result) {
                        if (object instanceof SearchKeyItem) {
                            dataSet.add((SearchKeyItem) object);
                        }
                    }
                } else {
                    if (key.isEmpty()) {
                        List<SearchKeyItem> allItems = mDatabase.getAllItems(mSearchKey);
                        if (!allItems.isEmpty()) {
                            dataSet = allItems;
                        }
                    }
                }

                setData(dataSet);
            }
        };
    }

    public void setData(List<SearchKeyItem> data) {
        if (mResultList.size() == 0) {
            mResultList = data;
            notifyDataSetChanged();
        } else {
            int previousSize = mResultList.size();
            int nextSize = data.size();
            mResultList = data;
            if (previousSize == nextSize && nextSize != 0)
                notifyItemRangeChanged(0, previousSize);
            else if (previousSize > nextSize) {
                if (nextSize == 0)
                    notifyItemRangeRemoved(0, previousSize);
                else {
                    notifyItemRangeChanged(0, nextSize);
                    notifyItemRangeRemoved(nextSize - 1, previousSize);
                }
            } else {
                notifyItemRangeChanged(0, previousSize);
                notifyItemRangeInserted(previousSize, nextSize - previousSize);
            }
        }
    }

    public void addOnItemClickListener(OnItemClickListener listener) {
        addOnItemClickListener(listener, null);
    }

    protected void addOnItemClickListener(OnItemClickListener listener, Integer position) {
        if (mItemClickListeners == null)
            mItemClickListeners = new ArrayList<>();

        if (position == null)
            mItemClickListeners.add(listener);
        else
            mItemClickListeners.add(position, listener);
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

		ImageView icon_left;
		TextView text;

        public ResultViewHolder(View view) {
            super(view);
            icon_left = view.findViewById(R.id.imageView_item_icon_left);
			text = view.findViewById(R.id.textView_item_text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListeners != null) {
                        for (OnItemClickListener listener : mItemClickListeners)
                            listener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
