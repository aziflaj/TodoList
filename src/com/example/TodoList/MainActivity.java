package com.example.TodoList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import com.example.TodoList.db.TaskContract;
import com.example.TodoList.db.TaskDBHelper;

import static com.example.TodoList.db.TaskContract.TABLE;

public class MainActivity extends ListActivity {
	private ListAdapter listAdapter;
	private TaskDBHelper helper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d("MainActivity","UpdateUI");
		updateUI();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add_task:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Add a task");
				builder.setMessage("What do you want to do?");
				final EditText inputField = new EditText(this);
				builder.setView(inputField);
				builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						String task = inputField.getText().toString();
						Log.d("MainActivity",task);

						helper = new TaskDBHelper(MainActivity.this);
						SQLiteDatabase db = helper.getWritableDatabase();
						ContentValues values = new ContentValues();

						values.clear();
						values.put(TaskContract.Columns.TASK,task);

						db.insertWithOnConflict(TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE);
						updateUI();
					}
				});

				builder.setNegativeButton("Cancel",null);

				builder.create().show();
				return true;

			default:
				return false;
		}
	}
	private void updateUI() {
		Log.d("MainActivity","UpdateUI method");
		helper = new TaskDBHelper(MainActivity.this);
		SQLiteDatabase sqlDB = helper.getReadableDatabase();
		Cursor cursor = sqlDB.query(TaskContract.TABLE,
				new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
				null,null,null,null,null);


		cursor.moveToFirst();
		while(cursor.moveToNext()) {
			Log.d("MainActivity cursor",cursor.getString(cursor.getColumnIndexOrThrow(TaskContract.Columns.TASK)));
		}

		listAdapter = new SimpleCursorAdapter(
				this,
				R.layout.task_view,
				cursor,
				new String[] { TaskContract.Columns.TASK},
				new int[] { R.id.taskTextView},
				0
		);
		Log.d("MainActivity","setting list adapter");
		this.setListAdapter(listAdapter);
	}
}
