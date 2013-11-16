package com.github.fedyura.dbandroid;

import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;

import com.github.fedyura.dbandroid.StudentsDialog.StudentsDialogListener;
import com.github.fedyura.technopark_db.CustomContentProvider;
import com.github.fedyura.technopark_db.TechnoparkContract.TPGroup;
import com.github.fedyura.technopark_db.TechnoparkContract.TPStudent;

public class MainActivity extends FragmentActivity 
	implements StudentsDialogListener, LoaderCallbacks<Cursor> {

	public final static int ID_GROUPS = -1;
	public final static String STUDENT_INFO = "student_info";
	public final static String STUDENT_UPDATE = "student_update";
	private StudentListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ExpandableListView explistView = (ExpandableListView)findViewById(R.id.stListView);
		
		mAdapter = new StudentListAdapter(this);
	    explistView.setAdapter(mAdapter);
		
	    explistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
	    	
	    	public boolean onChildClick(ExpandableListView parent, View v,
	    			                    int groupPosition, int childPosition, long id) {
	    		
	    		Cursor cursor = (Cursor) ((StudentListAdapter)parent.getExpandableListAdapter()).getChild(groupPosition, childPosition);
	    		//parent.getC
	    		
	    		//parent.getChildAt(groupPosition).get
	    		
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
	    		
	    		return true;
	    	}
	    	
	    });
	    
	    /*listView.setOnItemClickListener(new OnItemClickListener() {
	        
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
	    });*/
	}
	
	@Override
	public void onStart() {
		
		super.onStart();
		getSupportLoaderManager().initLoader(ID_GROUPS, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {
		// TODO Auto-generated method stub
		String dbPath = "content://" + CustomContentProvider.AUTHORITY + "/";
		String[] projectionStudent = { TPStudent._ID, TPStudent.COLUMN_NAME_INITIAL, 
								TPStudent.COLUMN_NAME_NUM_SEMESTER, 
								TPStudent.COLUMN_NAME_TELEPHONE,
								TPStudent.COLUMN_NAME_EMAIL };
	    String selection = "((" + TPStudent.COLUMN_NAME_NUM_SEMESTER + " = ? ))";
	    String[] selectionArgs = { String.valueOf(id) };
		
	    String[] projectionGroup = { TPGroup._ID, TPGroup.COLUMN_NAME_GROUPNAME, 
	    		TPGroup.COLUMN_NAME_KURATOR_INITIALS,
	    		TPGroup.COLUMN_NAME_DISCIPLINES, 
				TPGroup.COLUMN_NAME_KURATOR_TELEPHONE,
				TPGroup.COLUMN_NAME_KURATOR_EMAIL };
	    
	    if (id != -1) {
			
			//child view
			return new CursorLoader(this, Uri.parse(dbPath + TPStudent.TABLE_NAME), 
					   				projectionStudent, selection, selectionArgs, null);
		}
		else {
			
			//group view
			return new CursorLoader(this, Uri.parse(dbPath + TPGroup.TABLE_NAME),
									projectionGroup, null, null, null);
			//return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// TODO Auto-generated method stub
		int id = loader.getId();
		if (id != -1) {
			
			if (!data.isClosed()) {
				
				HashMap<Integer,Integer> groupMap = mAdapter.getGroupMap();
				try {
					int groupPos = groupMap.get(id);
					mAdapter.setChildrenCursor(groupPos, data);
				}
				catch(NullPointerException e) {
					
					Log.w("TAG", "Adapter expired, try again on the next query: "
                          + e.getMessage());
				}
			}
		}
		else {
			mAdapter.setGroupCursor(data);
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		int id = loader.getId();
		if (id != -1) {
			
			try {
				mAdapter.setChildrenCursor(id, null);
			}
			catch (NullPointerException e) {
				Log.w("TAG", "Adapter expired, try again on the next query: "
                        + e.getMessage());
			}
		}
		else {
			
			mAdapter.setGroupCursor(null);
		}
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
