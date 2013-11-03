package com.github.fedyura.dbandroid;

import com.github.fedyura.technopark_db.TechnoparkContract.TPStudent;

import android.content.Context;
import android.support.v4.widget.SimpleCursorAdapter;

public class StudentListAdapter extends SimpleCursorAdapter {
	
	public StudentListAdapter(Context context) {
		super(context, R.layout.student_view, null, 
			  new String[] { TPStudent.COLUMN_NAME_INITIAL }, 
			  new int[] { R.id.studInit }, 0);
	}
}
