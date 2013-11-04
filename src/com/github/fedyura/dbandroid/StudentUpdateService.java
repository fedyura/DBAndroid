package com.github.fedyura.dbandroid;

import com.github.fedyura.technopark_db.CustomContentProvider;
import com.github.fedyura.technopark_db.TechnoparkContract.TPStudent;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;

public class StudentUpdateService extends IntentService {

	public StudentUpdateService() {
		super("StudentUpdateService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		if (intent == null) {
			
			return;
		}
		
		Student stud = intent.getParcelableExtra(MainActivity.STUDENT_UPDATE);
		
		ContentValues values = new ContentValues();
		values.put(TPStudent.COLUMN_NAME_INITIAL, stud.getInitials());
		values.put(TPStudent.COLUMN_NAME_NUM_SEMESTER, stud.getNumSemester());
		values.put(TPStudent.COLUMN_NAME_TELEPHONE, stud.getTelephone());
		values.put(TPStudent.COLUMN_NAME_EMAIL, stud.getEmail());
		
		String where = TPStudent._ID + " = " + String.valueOf(stud.getId());
		String dbPath = "content://" + CustomContentProvider.AUTHORITY + "/";
		
		getContentResolver().update(Uri.parse(dbPath + TPStudent.TABLE_NAME), values, where, null);
	}
}
