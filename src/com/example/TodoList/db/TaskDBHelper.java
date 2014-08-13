package com.example.TodoList.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TaskDBHelper extends SQLiteOpenHelper {

	public TaskDBHelper(Context context) {
		super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqlDB) {
		String sqlQuery =
				String.format("CREATE TABLE %s (" +
						"%s int primary key autoincrement, " +
						"%s text ) ", TaskContract.TABLE,
								      TaskContract.Columns.ID,
								      TaskContract.Columns.TASK);

		Log.d("TaskDBHelper","Qyery to form table: "+sqlQuery);
		sqlDB.execSQL(sqlQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
		sqlDB.execSQL("DROP TABLE IF EXISTS "+TaskContract.TABLE);
		onCreate(sqlDB);
	}
}
