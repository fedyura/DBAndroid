package com.github.fedyura.TechnoparkDB;

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
	
	public static abstract class TPDiscipline implements BaseColumns {
		
		public static final String TABLE_NAME = "Discipline";
		
		public static final String COLUMN_NAME_DISCNAME = "name";
		public static final String COLUMN_NAME_LECTURER_INITIALS = "lectInitials";
		public static final String COLUMN_NAME_NUM_SEMESTER = "numSemestr";
		public static final String COLUMN_NAME_LECTURER_TELEPHONE = "lectTelephone";
		public static final String COLUMN_NAME_LECTURER_EMAIL = "lectEmail";
		
		public static final String COLUMN_NAME_NULLABLE = COLUMN_NAME_LECTURER_EMAIL;
		
		private static final String SQL_CREATE_DISCIPLINES =
				"CREATE TABLE " + TPDiscipline.TABLE_NAME + " (" +
				TPDiscipline._ID + " INTEGER PRIMARY KEY," +
				TPDiscipline.COLUMN_NAME_DISCNAME + TEXT_TYPE + COMMA_SEP +
				TPDiscipline.COLUMN_NAME_LECTURER_INITIALS + TEXT_TYPE + COMMA_SEP +
				TPDiscipline.COLUMN_NAME_NUM_SEMESTER + INTEGER_TYPE + COMMA_SEP +
				TPDiscipline.COLUMN_NAME_LECTURER_TELEPHONE + TEXT_TYPE + COMMA_SEP +
				TPDiscipline.COLUMN_NAME_LECTURER_EMAIL + TEXT_TYPE + COMMA_SEP + 
				" )";
		
		private static final String SQL_DELETE_DISCIPLINES = 
				"DROP TABLE IF EXISTS " + TPDiscipline.TABLE_NAME;
		
		public static void onCreate(SQLiteDatabase db) {
			
			db.execSQL(SQL_CREATE_DISCIPLINES);
		}
		
		public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL(SQL_DELETE_DISCIPLINES);
		}
	}
	
	public static abstract class Journal implements BaseColumns {
		
		public static final String TABLE_NAME = "Journal";
		
		public static final String COLUMN_NAME_STUDENT_ID = "studentId";
		public static final String COLUMN_NAME_DISCIPLINE_ID = "discId";
		public static final String COLUMN_NAME_MARK = "exam_mark";
		
		public static final String COLUMN_NAME_NULLABLE = COLUMN_NAME_MARK;
		
		private static final String SQL_CREATE_JOURNAL =
				"CREATE TABLE " + Journal.TABLE_NAME + " (" +
				Journal._ID + " INTEGER PRIMARY KEY," +
				Journal.COLUMN_NAME_STUDENT_ID + INTEGER_TYPE + COMMA_SEP +
				Journal.COLUMN_NAME_DISCIPLINE_ID + INTEGER_TYPE + COMMA_SEP +
				Journal.COLUMN_NAME_MARK + INTEGER_TYPE + COMMA_SEP +
				"FOREIGN KEY (" + Journal.COLUMN_NAME_STUDENT_ID + ") REFERENCES" +  
				TPStudent.TABLE_NAME + "(" + TPStudent._ID + ")" +  
				"FOREIGN KEY (" + Journal.COLUMN_NAME_DISCIPLINE_ID + ") REFERENCES" +  
				TPDiscipline.TABLE_NAME + "(" + TPDiscipline._ID + ")" +
				")";
		
		private static final String SQL_DELETE_JOURNAL = 
				"DROP TABLE IF EXISTS " + Journal.TABLE_NAME;
		
		public static void onCreate(SQLiteDatabase db) {
			
			db.execSQL(SQL_CREATE_JOURNAL);
		}
		
		public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL(SQL_DELETE_JOURNAL);
		}
	}
	
	public static void InsertData(SQLiteDatabase db) {
		
		InsertDataStudents(db, "Иванов И.И.", 1, "8-916-234-7896", "ivanov_i@mail.ru");
		InsertDataStudents(db, "Петров А.П.", 1, "8-915-278-4578", "petrov1_i@gmail.com");
		InsertDataStudents(db, "Сидоров Р.И.", 2, "8-903-321-5738", "sidorov_r@mail.ru");
		InsertDataStudents(db, "Варанов С.Р.", 2, "8-926-565-4339", "varanov@yandex.ru");
		InsertDataStudents(db, "Нахимов А.Р.", 3, "8-916-433-3845", "naximov_ar@mail.ru");
		
		InsertDataDiscipline(db, "Web технологии", "Д. Смаль", 1, "8-915-897-4849", "smal.mail.ru");
		InsertDataDiscipline(db, "C++", "А. Петров", 1, "8-926-685-2940", "petrov.mail.ru");
		InsertDataDiscipline(db, "Программирование на Java", "В. Чибриков", 2, "8-903-324-0947", "chibrikov.mail.ru");
	
		InsertDataJournal(db, 1, 1, 4);
		InsertDataJournal(db, 1, 2, 5);
		InsertDataJournal(db, 2, 1, 4);
		InsertDataJournal(db, 2, 2, 3);
		InsertDataJournal(db, 3, 1, 4);
		InsertDataJournal(db, 3, 2, 4);
		InsertDataJournal(db, 3, 3, 5);
		InsertDataJournal(db, 4, 1, 3);
		InsertDataJournal(db, 4, 2, 3);
		InsertDataJournal(db, 4, 3, 4);
		InsertDataJournal(db, 5, 1, 5);
		InsertDataJournal(db, 5, 2, 5);
		InsertDataJournal(db, 5, 3, 4);
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
	
	public static void InsertDataDiscipline(SQLiteDatabase db, String name,
		String lectInitials, int numSemester, String lectTelephone, String lectEmail) {
			
		ContentValues values = new ContentValues();
		values.put(TPDiscipline.COLUMN_NAME_DISCNAME, name);
		values.put(TPDiscipline.COLUMN_NAME_LECTURER_INITIALS, lectInitials);
		values.put(TPDiscipline.COLUMN_NAME_NUM_SEMESTER, numSemester);
		values.put(TPDiscipline.COLUMN_NAME_LECTURER_TELEPHONE, lectTelephone);
		values.put(TPDiscipline.COLUMN_NAME_LECTURER_EMAIL, lectEmail);
			
		long newRowId;
		newRowId = db.insert(
			TPDiscipline.TABLE_NAME,
			TPDiscipline.COLUMN_NAME_NULLABLE,
		    values);
	}
	
	public static void InsertDataJournal(SQLiteDatabase db,
		int studentId, int disciplineId, int mark) {
			
		ContentValues values = new ContentValues();
		values.put(Journal.COLUMN_NAME_STUDENT_ID, studentId);
		values.put(Journal.COLUMN_NAME_DISCIPLINE_ID, disciplineId);
		values.put(Journal.COLUMN_NAME_MARK, mark);
			
		long newRowId;
		newRowId = db.insert(
			Journal.TABLE_NAME,
			Journal.COLUMN_NAME_NULLABLE,
		    values);
	}
}
