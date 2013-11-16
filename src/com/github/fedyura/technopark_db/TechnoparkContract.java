package com.github.fedyura.technopark_db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class TechnoparkContract {
	
	public TechnoparkContract() { }
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	
	private static final String COMMA_SEP = ",";
	
	public static abstract class TPStudent implements BaseColumns {
		
		public static final String TABLE_NAME = "Student";
		
		public static final String COLUMN_NAME_INITIAL = "initials";
		public static final String COLUMN_NAME_NUM_SEMESTER = "numSemestr";
		public static final String COLUMN_NAME_TELEPHONE = "telephone";
		public static final String COLUMN_NAME_EMAIL = "email";
		
		public static final String COLUMN_NAME_NULLABLE = COLUMN_NAME_EMAIL;
		
		private static final String SQL_CREATE_STUDENTS =
				"CREATE TABLE " + TPStudent.TABLE_NAME + " (" +
				TPStudent._ID + " INTEGER PRIMARY KEY," +
				TPStudent.COLUMN_NAME_INITIAL + TEXT_TYPE + COMMA_SEP +
				TPStudent.COLUMN_NAME_NUM_SEMESTER + INTEGER_TYPE + COMMA_SEP +
				TPStudent.COLUMN_NAME_TELEPHONE + TEXT_TYPE + COMMA_SEP +
				TPStudent.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
				"FOREIGN KEY (" + TPStudent.COLUMN_NAME_NUM_SEMESTER + ") REFERENCES " +  
				TPGroup.TABLE_NAME + "(" + TPGroup._ID + ")" +
				" )";
		
		private static final String SQL_DELETE_STUDENTS = 
				"DROP TABLE IF EXISTS " + TPStudent.TABLE_NAME;	
		
		public static void onCreate(SQLiteDatabase db) {
			
			db.execSQL(SQL_CREATE_STUDENTS);
		}
		
		public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL(SQL_DELETE_STUDENTS);
		}
	}
	
	public static abstract class TPGroup implements BaseColumns {
		
		public static final String TABLE_NAME = "StudGroup";
		
		public static final String COLUMN_NAME_GROUPNAME = "name";
		public static final String COLUMN_NAME_KURATOR_INITIALS = "kurInitials";
		public static final String COLUMN_NAME_DISCIPLINES = "disciplines";
		public static final String COLUMN_NAME_KURATOR_TELEPHONE = "kurTelephone";
		public static final String COLUMN_NAME_KURATOR_EMAIL = "kurEmail";
		
		public static final String COLUMN_NAME_NULLABLE = COLUMN_NAME_KURATOR_EMAIL;
		
		private static final String SQL_CREATE_GROUPS =
				"CREATE TABLE " + TPGroup.TABLE_NAME + " (" +
				TPGroup._ID + " INTEGER PRIMARY KEY," +
				TPGroup.COLUMN_NAME_GROUPNAME + TEXT_TYPE + COMMA_SEP +
				TPGroup.COLUMN_NAME_KURATOR_INITIALS + TEXT_TYPE + COMMA_SEP +
				TPGroup.COLUMN_NAME_DISCIPLINES + TEXT_TYPE + COMMA_SEP +
				TPGroup.COLUMN_NAME_KURATOR_TELEPHONE + TEXT_TYPE + COMMA_SEP +
				TPGroup.COLUMN_NAME_KURATOR_EMAIL + TEXT_TYPE + 
				" )";
		
		private static final String SQL_DELETE_GROUPS = 
				"DROP TABLE IF EXISTS " + TPGroup.TABLE_NAME;
		
		public static void onCreate(SQLiteDatabase db) {
			
			db.execSQL(SQL_CREATE_GROUPS);
		}
		
		public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL(SQL_DELETE_GROUPS);
		}
	}
	
	public static void InsertData(SQLiteDatabase db) {
		
		InsertDataGroups(db, "АПО-11", "Кузовлев А.И.", "Web; C++; Algorithms",
						 "8-925-345-8392", "kuzovlev@mail.ru");
		InsertDataGroups(db, "АПО-21", "Вендов Р.П.", "Java; DB; Frontend",
				 		 "8-916-844-8204", "r.vendov@gmail.com");
		InsertDataGroups(db, "АПО-31", "Кисова А.Ю.", "Testing; GUI; Security; Android",
				 		 "8-903-194-5739", "anna_kisova@yandex.ru");
		InsertDataGroups(db, "АПО-41", "Купаскин М.Л.", "Management; Web development; Soft Skills",
				 		 "8-916-294-2495", "ml_kupaskin@mail.ru");
		
		InsertDataStudents(db, "Иванов И.И.", 1, "8-916-234-7896", "ivanov_i@mail.ru");
		InsertDataStudents(db, "Петров А.П.", 4, "8-915-278-4578", "petrov1_i@gmail.com");
		InsertDataStudents(db, "Сидоров Р.И.", 2, "8-903-321-5738", "sidorov_r@mail.ru");
		InsertDataStudents(db, "Варанов С.Р.", 2, "8-926-565-4339", "varanov@yandex.ru");
		InsertDataStudents(db, "Нахимов А.Р.", 3, "8-916-433-3845", "naximov_ar@mail.ru");
		
		InsertDataStudents(db, "Горбунов С.И.", 4, "8-916-473-4839", "gorb_s2@gmail.com");
		InsertDataStudents(db, "Мякинин Р.П.", 1, "8-915-704-3955", "myakinin34@gmail.com");
		InsertDataStudents(db, "Крутов Р.И.", 2, "8-904-384-4930", "kruto_ri@mail.ru");
		InsertDataStudents(db, "Гаранина А.С.", 3, "8-925-394-0245", "garanina@yandex.ru");
		InsertDataStudents(db, "Кусов П.Ю.", 3, "8-916-530-5937", "kusov_p@mail.ru");
	}
	
	public static void InsertDataStudents(SQLiteDatabase db, 
		String initials, int numSemester, String telephone, String email) {
		
		ContentValues values = new ContentValues();
		values.put(TPStudent.COLUMN_NAME_INITIAL, initials);
		values.put(TPStudent.COLUMN_NAME_NUM_SEMESTER, numSemester);
		values.put(TPStudent.COLUMN_NAME_TELEPHONE, telephone);
		values.put(TPStudent.COLUMN_NAME_EMAIL, email);
		
		long newRowId;
		newRowId = db.insert(
			TPStudent.TABLE_NAME,
			TPStudent.COLUMN_NAME_NULLABLE,
		    values);
	}
	
	public static void InsertDataGroups(SQLiteDatabase db, String name,
		String kurInitials, String disciplines, String kurTelephone, String kurEmail) {
			
		ContentValues values = new ContentValues();
		values.put(TPGroup.COLUMN_NAME_GROUPNAME, name);
		values.put(TPGroup.COLUMN_NAME_KURATOR_INITIALS, kurInitials);
		values.put(TPGroup.COLUMN_NAME_DISCIPLINES, disciplines);
		values.put(TPGroup.COLUMN_NAME_KURATOR_TELEPHONE, kurTelephone);
		values.put(TPGroup.COLUMN_NAME_KURATOR_EMAIL, kurEmail);
			
		long newRowId;
		newRowId = db.insert(
			TPGroup.TABLE_NAME,
			TPGroup.COLUMN_NAME_NULLABLE,
		    values);
	}
}
