package com.github.fedyura.technopark_db;

import com.github.fedyura.technopark_db.TechnoparkContract.Journal;
import com.github.fedyura.technopark_db.TechnoparkContract.TPDiscipline;
import com.github.fedyura.technopark_db.TechnoparkContract.TPStudent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TechnoparkDBHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Technopark.db";
    
    public TechnoparkDBHelper(Context context) {
        
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		TPStudent.onCreate(db);
		//TPDiscipline.onCreate(db);
		//Journal.onCreate(db);
		TechnoparkContract.InsertData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		TPStudent.onUpgrade(db, oldVersion, newVersion);
		//TPDiscipline.onUpgrade(db, oldVersion, newVersion);
		//Journal.onUpgrade(db, oldVersion, newVersion);
		onCreate(db);
		TechnoparkContract.InsertData(db);
	}
    
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
		onUpgrade(db, oldVersion, newVersion);
    }
	
	@Override
    public void close(){
        
		getReadableDatabase().close();
        getWritableDatabase().close();
        super.close();
    }
}
