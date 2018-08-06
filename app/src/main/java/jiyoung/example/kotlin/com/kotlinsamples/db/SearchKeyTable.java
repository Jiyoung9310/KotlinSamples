package jiyoung.example.kotlin.com.kotlinsamples.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiyoung on 2017-06-22.
 */

public class SearchKeyTable {

    public static final int SEARCH_KEY_VOD = 1;
    public static final int SEARCH_KEY_LECTURE = 2;

    private final SearchKeyDatabase dbHelper;
    private static int mHistorySize = 100;
    private static Integer mCurrentDatabaseKey = null;
    private static int mConnectionCount = 0;
    private SQLiteDatabase db;
    private String userID;

    Context mContext;

    public SearchKeyTable(Context mContext) {
        this.mContext = mContext;
        dbHelper = new SearchKeyDatabase(mContext);
        /*if (AccountManager.instance(mContext).isLoggedIn()) {
            userID = SettingMgr.instance(mContext).getMemberId();
        }else {
            userID = "$n%u@l*l$";
        }*/
		userID = "$n%u@l*l$";
    }
    public void open() throws SQLException {
        if (mConnectionCount == 0) {
            db = dbHelper.getWritableDatabase();
        }
        mConnectionCount++;
    }

    public void close() {
        mConnectionCount--;
        if (mConnectionCount == 0) {
            dbHelper.close();
        }
    }


    public void addItem(SearchKeyItem item) {
        ContentValues values = new ContentValues();
        if (!checkText(item.getText().toString())) {
            putDB(values, item);
        } else {
            deleteItem(item);
            putDB(values, item);
        }
    }

    private void putDB(ContentValues values, SearchKeyItem item) {
        values.put(SearchKeyDatabase.SEARCH_HISTORY_COLUMN_TEXT, item.getText().toString());
        values.put(SearchKeyDatabase.SEARCH_HISTORY_COLUMN_GROUP, item.getKey());
        values.put(SearchKeyDatabase.SEARCH_HISTORY_COLUMN_USERID, userID);

        open();
        db.insert(SearchKeyDatabase.SEARCH_HISTORY_TABLE, null, values);
        close();
    }

    public void deleteItem(SearchKeyItem item) {
        open();
        db.delete(SearchKeyDatabase.SEARCH_HISTORY_TABLE, SearchKeyDatabase.SEARCH_HISTORY_COLUMN_ID + " = ? ", new String[]{Integer.toString(getItemId(item))});
        close();
    }

    private int getItemId(SearchKeyItem item) {
        open();
        String query = "SELECT " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_ID +
                " FROM " + SearchKeyDatabase.SEARCH_HISTORY_TABLE +
                " WHERE " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_USERID + " = " + "'" + userID + "'" +
                " AND " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_GROUP + " = " +item.getKey() +
                " AND " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_TEXT + " = ?";
        Cursor res = db.rawQuery(query, new String[]{item.getText().toString()});
        int id = 0;
        if(res.getCount()>0) {
            res.moveToFirst();
            id = res.getInt(0);
        }
        res.close();
        close();
        return id;
    }


    public int getLastItemId(Integer databaseKey) {
        open();
        String sql = "SELECT " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_ID +
                " FROM " + SearchKeyDatabase.SEARCH_HISTORY_TABLE +
                " WHERE " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_USERID + " = " + "'" + userID + "'";
        if (databaseKey != null)
            sql += " AND " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_GROUP + " = " + databaseKey;
        Cursor res = db.rawQuery(sql, null);
        int count = 0;
        if(res.getCount()>0) {
            res.moveToLast();
            count = res.getInt(0);
        }

        res.close();
        close();
        return count;
    }

    private boolean checkText(String text) {
        open();

        String query = "SELECT * FROM " + SearchKeyDatabase.SEARCH_HISTORY_TABLE +
                " WHERE " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_USERID + " = " + "'" + userID + "'" +
                " AND " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_TEXT + " =?";
        Cursor cursor = db.rawQuery(query, new String[]{text});

        boolean hasObject = false;

        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                hasObject = true;
            }
        }

        cursor.close();
        close();
        return hasObject;
    }

    public List<SearchKeyItem> getAllItems(Integer databaseKey) {
        //mCurrentDatabaseKey = databaseKey;
        List<SearchKeyItem> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + SearchKeyDatabase.SEARCH_HISTORY_TABLE +
                " WHERE " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_USERID + " = " + "'" + userID + "'";
        if (databaseKey != null) {
            selectQuery += " AND " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_GROUP + " = " + databaseKey;
        }
        selectQuery += " ORDER BY " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_ID + " DESC";
        if (mHistorySize != 100) {
            selectQuery += " LIMIT " + mHistorySize;
        }


        open();
        Cursor cursor = db.rawQuery(selectQuery, null);// TODO BUG
        if(cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    SearchKeyItem item = new SearchKeyItem();
                    item.setKey(databaseKey);
                    item.setText(cursor.getString(1));
                    item.setViewType(RecentSearchKeyAdapter.TYPE_CONTENT);
                    list.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        close();
        return list;
    }

    public void clearDatabase(Integer databaseKey) {
        open();
        String sql = "DELETE FROM " + SearchKeyDatabase.SEARCH_HISTORY_TABLE +
                " WHERE " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_USERID + " = " + "'" + userID + "'";
        if (databaseKey != null)
            sql += " AND " + SearchKeyDatabase.SEARCH_HISTORY_COLUMN_GROUP + " = " + databaseKey;
        db.execSQL(sql);
        close();
    }

    public void setHistorySize(int historySize) {
        mHistorySize = historySize;
    }

}
