package com.github.fedyura.dbandroid;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.github.fedyura.dbandroid.StudentsDialog.StudentsDialogListener;
import com.github.fedyura.technopark_db.CustomContentProvider;
import com.github.fedyura.technopark_db.TechnoparkContract.TPStudent;

public class MainActivity extends FragmentActivity 
	implements StudentsDialogListener, LoaderCallbacks<Cursor> {

	public final static int ID_STUDENTS = 0;
	public final static String STUDENT_INFO = "student_info";
	public final static String STUDENT_UPDATE = "student_update";
	private SimpleCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView)findViewById(R.id.stListView);
		
		adapter = new StudentListAdapter(this);
	    listView.setAdapter(adapter);
	    
	    listView.setOnItemClickListener(new OnItemClickListener() {
	        
	    	public void onItemClick(AdapterView<?> parent, View view,
	        						int position, long id) {
	          
	    		Cursor cursor = (Cursor) ((StudentListAdapter)parent.getAdapter()).getItem(position);
                
	    		int _id = cursor.getInt(cursor.getColumnIndex(TPStudent._ID));
                String initials = cursor.getString(cursor.getColumnIndex(TPStudent.COLUMN_NAME_INITIAL));
                int num_semester = cursor.getInt(cursor.getColumnIndex(TPStudent.COLUMN_NAME_NUM_SEMESTER));
                String telephone = cursor.getString(cursor.getColumnIndex(TPStudent.COLUMN_NAME_TELEPHONE));
                String email = cursor.getString(cursor.getColumnIndex(TPStudent.COLUMN_NAME_EMAIL));
                
	    		Bundle bundle = new Bundle();
	    		bundle.putParcelable(STUDENT_INFO, new Student(_id, initials, num_semester, telephone, email));
	    		
	    		DialogFragment dialog = new StudentsDialog();
	    		
	    		dialog.setArguments(bundle);
	            dialog.show(getSupportFragmentManager(), "SettingsDialog");
	    	}
	    });
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
		String[] projection = { TPStudent._ID, TPStudent.COLUMN_NAME_INITIAL, 
								TPStudent.COLUMN_NAME_NUM_SEMESTER, 
								TPStudent.COLUMN_NAME_TELEPHONE,
								TPStudent.COLUMN_NAME_EMAIL };
	    
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

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, Student student) {
		// TODO Auto-generated method stub
		Student stud = student;
		
		 Intent intent = new Intent(MainActivity.this, StudentUpdateService.class);
		 intent.putExtra(STUDENT_UPDATE, stud);
		 startService(intent);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
}
