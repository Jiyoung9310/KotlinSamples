package jiyoung.example.kotlin.com.kotlinsamples.db;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lapism.searchview.SearchView;

import jiyoung.example.kotlin.com.kotlinsamples.ActLapism;
import jiyoung.example.kotlin.com.kotlinsamples.R;

import static jiyoung.example.kotlin.com.kotlinsamples.ActLapismKt.EXTRA_KEY_OPTION;

/**
 * SearchView 관리를 위한 클래스 <br><br>
 *
 * Created by jongsic.kim on 2017-04-17.
 */
public class SearchViewMgr {

    private static final int MAX_SEARCH_HISTORY_SIZE = 5;

    SearchKeyTable mHistoryDatabase;

    Activity mActivity;
    SearchView mSearchView;
    SearchOptionsUpdater mUpdater;

    public SearchViewMgr(SearchView searchView, boolean isSearchMode) {
        mSearchView = searchView;
        mActivity = (Activity) mSearchView.getContext();

        mHistoryDatabase = new SearchKeyTable(mActivity);
        mHistoryDatabase.setHistorySize(MAX_SEARCH_HISTORY_SIZE);

        init(isSearchMode);
    }

    private void init(boolean isSearchMode) {

        // SearchView initialize
        if( isSearchMode ) {
            mSearchView.setVersion(SearchView.VERSION_TOOLBAR);
            mSearchView.setArrowOnly(false);
            mSearchView.setOnMenuClickListener(new SearchView.OnMenuClickListener() {
                @Override
                public void onMenuClick() {
                    mActivity.finish();
                }
            });
        } else {
            mSearchView.setVersion(SearchView.VERSION_MENU_ITEM);
        }

        mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_MENU_ITEM);
        mSearchView.setTheme(SearchView.THEME_LIGHT, true);
        mSearchView.setHint(R.string.search_key_hint);
        mSearchView.setVoice(false);

        final SearchKeyAdapter searchAdapter = new SearchKeyAdapter(mActivity);
        searchAdapter.addOnItemClickListener(new SearchKeyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
                String query = textView.getText().toString();
                searchData(query);
                mSearchView.setQuery("", false);
                mSearchView.close(false);
            }
        });
        searchAdapter.setHasStableIds(true);

        mSearchView.setAdapter(searchAdapter);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if( !TextUtils.isEmpty(query) && query.length() > 2) {
                    searchData(query);
                    mSearchView.close(false);
                } else {
                    Toast.makeText(mActivity, mActivity.getString(R.string.search_fail_limit_character), Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void setSearchOptionsUpdater(SearchOptionsUpdater updater) {
        this.mUpdater = updater;
    }

    public void setQuery(String query, boolean execute) {
        mSearchView.setQuery(query, execute);
    }

    public void open(boolean animate, MenuItem item) {
        mSearchView.open(animate, item);
    }

    public void searchData(String query) {
        // history add
        mHistoryDatabase.addItem(new SearchKeyItem(SearchKeyTable.SEARCH_KEY_VOD, query));

        Intent intent = new Intent(mActivity, ActLapism.class);
        intent.putExtra(EXTRA_KEY_OPTION, createSearchOptions(query));
        mActivity.startActivity(intent);
    }

    private SearchOptions createSearchOptions(String query) {
        SearchOptions options = new SearchOptions();
        options.searchKey = query;
        if( mUpdater != null ) {
            mUpdater.updateSearchOptions(options);
        }

        return options;
    }

    /**
     * 검색 조건 updater interface
     */
    public interface SearchOptionsUpdater {
        public void updateSearchOptions(SearchOptions options);
    }

}
