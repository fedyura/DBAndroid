package com.github.fedyura.technopark_db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CustomContentProvider extends ContentProvider {

	//database
	private TechnoparkDBHelper TPDBHelper;
	
	//user for uri
	public static final String AUTHORITY = "com.github.fedyura.technopark_db.CustomContentProvider";
	private static final int STUDENTS = 0, GROUPS = 1;
	
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
	    uriMatcher.addURI(AUTHORITY, TechnoparkContract.TPStudent.TABLE_NAME, STUDENTS);
	    uriMatcher.addURI(AUTHORITY, TechnoparkContract.TPGroup.TABLE_NAME, GROUPS);
	  }
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		TPDBHelper = new TechnoparkDBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODO Auto-generated method stub
		String tableName;

        int uriType = uriMatcher.match(uri);
		switch (uriType) {    
        case GROUPS:
                tableName = TechnoparkContract.TPGroup.TABLE_NAME;
                break;
        case STUDENTS:
                tableName = TechnoparkContract.TPStudent.TABLE_NAME;
                break;
        default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = TPDBHelper.getReadableDatabase();
        Cursor c = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
        }

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = TPDBHelper.getWritableDatabase();
        int count= 0;
        switch (uriMatcher.match(uri)) {
        case STUDENTS:
                count = db.update(TechnoparkContract.TPStudent.TABLE_NAME, values, selection, selectionArgs);
                break;
        case GROUPS:
                count = db.update(TechnoparkContract.TPGroup.TABLE_NAME, values, selection, selectionArgs);
                break;
        default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
	}
}
