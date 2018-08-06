package jiyoung.example.kotlin.com.kotlinsamples.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SearchKeyDatabase extends SQLiteOpenHelper {
	static final String SEARCH_HISTORY_TABLE = "search_key_table";
	static final String SEARCH_HISTORY_COLUMN_ID = "_id";
	static final String SEARCH_HISTORY_COLUMN_TEXT = "_text";
	static final String SEARCH_HISTORY_COLUMN_GROUP = "_group";
	static final String SEARCH_HISTORY_COLUMN_USERID = "_userId";

	private static final String DATABASE_NAME = "search_key_db.db";
	private static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE_SEARCH_HISTORY = "CREATE TABLE IF NOT EXISTS "
			+ SEARCH_HISTORY_TABLE + " ( "
			+ SEARCH_HISTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ SEARCH_HISTORY_COLUMN_TEXT + " TEXT, "
			+ SEARCH_HISTORY_COLUMN_GROUP + " INTEGER, "
			+ SEARCH_HISTORY_COLUMN_USERID + " TEXT "
			+ ");";

	SearchKeyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SEARCH_HISTORY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropAllTables(db);
		onCreate(db);
	}

	private void dropAllTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + SearchKeyDatabase.SEARCH_HISTORY_TABLE);
	}
}
