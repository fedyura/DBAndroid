package com.github.fedyura.dbandroid;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ListView;

import com.github.fedyura.technopark_db.CustomContentProvider;
import com.github.fedyura.technopark_db.TechnoparkContract.TPStudent;

public class MainActivity extends FragmentActivity 
	implements LoaderCallbacks<Cursor> {

	public final static int ID_STUDENTS = 0;
	private SimpleCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView)findViewById(R.id.stListView);
		
		adapter = new StudentListAdapter(this);
	    listView.setAdapter(adapter);
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		getSupportLoaderManager().initLoader(ID_STUDENTS, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		String dbPath = "content://" + CustomContentProvider.AUTHORITY + "/";
		String[] projection = { TPStudent._ID, TPStudent.COLUMN_NAME_INITIAL };
	    
		CursorLoader cursorLoader = new CursorLoader(this,
				Uri.parse(dbPath + TPStudent.TABLE_NAME), projection, null, null, null);
	    return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
		// TODO Auto-generated method stub
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		adapter.swapCursor(null);
	}

}
