package com.github.fedyura.dbandroid;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.widget.SimpleCursorTreeAdapter;

import com.github.fedyura.technopark_db.TechnoparkContract.TPGroup;
import com.github.fedyura.technopark_db.TechnoparkContract.TPStudent;

public class StudentListAdapter extends SimpleCursorTreeAdapter {
	
	//Activity mActivity;
	MainActivity mActivity;
	
	protected final HashMap<Integer, Integer> mGroupMap;
	
	public StudentListAdapter(MainActivity activity) {
		super((Context) activity, null, R.layout.group_view,  
		new String[] { TPGroup.COLUMN_NAME_GROUPNAME }, 
		new int[] { R.id.groupName }, R.layout.student_view,
		new String[] { TPStudent.COLUMN_NAME_INITIAL }, new int[] { R.id.studInit });
		mActivity = activity;
		mGroupMap = new HashMap<Integer, Integer>();
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		// TODO Auto-generated method stub
		
		//int groupId = groupCursor.getInt(groupCursor.getColumnIndex(TPGroup._ID));
		
		int groupPos = groupCursor.getPosition();
	    int groupId = groupCursor.getInt(groupCursor.getColumnIndex(TPGroup._ID));
		
		//mActivity.getLoaderManager().initLoader(groupId, null, mActivity);
		//Loader loader = mActivity.getSupportLoaderManager().getLoader(groupId); 
	    //if (loader != null && !loader.isReset()) {  
		
	    mGroupMap.put(groupId, groupPos);
	    
	    Loader loader = mActivity.getSupportLoaderManager().getLoader(groupId); 
		if (loader != null && !loader.isReset())
		{
			mActivity.getSupportLoaderManager().restartLoader(groupId, null, mActivity);
		}
		else {
			mActivity.getSupportLoaderManager().initLoader(groupId, null, mActivity);
		}
		return null;
	}
	
	//Accessor method
	public HashMap<Integer, Integer> getGroupMap() {
	    
		return mGroupMap;
	}
}
