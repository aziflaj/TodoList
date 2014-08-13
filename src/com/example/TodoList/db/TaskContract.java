package com.example.TodoList.db;


import android.provider.BaseColumns;

public class TaskContract {
	public static final String DB_NAME = "com.example.TodoList.tasks.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "tasks";

	public static final String DEFAULT_SORT = Column.CREATED_AT + " DESCR";


	public class Column {
		public static final String ID = BaseColumns._ID;
		public static final String TASK = "task";
		public static final String CREATED_AT = "created_at";
	}
}
