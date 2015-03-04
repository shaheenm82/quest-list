package com.davies.questlist.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	// Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "questList";
    
 // Table Names
    public static final String TABLE_USER = "user";
    public static final String TABLE_QUEST = "quest";
    public static final String TABLE_TASK = "task";
 
    // Common column names
    public static final String KEY_ID = "_id";
    
    // User Table - column names
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_XP = "xp";
    public static final String COL_USER_LEVEL = "level";
 
    // Quest Table - column names
    public static final String COL_QUEST_NAME = "name";
    public static final String COL_QUEST_CREATED = "created_date";
    public static final String COL_QUEST_TYPE = "type";
    public static final String COL_QUEST_XP = "xp";
    public static final String COL_QUEST_COMPLETED = "completed";
 
    // Task Table - column names
    public static final String COL_TASK_NAME = "name";
    public static final String COL_TASK_DESC = "description";
    public static final String COL_TASK_CREATED = "created_date";
    public static final String COL_TASK_FIT_XP = "fit_xp";
    public static final String COL_TASK_INT_XP = "int_xp";
    public static final String COL_TASK_ART_XP = "art_xp";
    public static final String COL_TASK_CHR_XP = "chr_xp";
    public static final String COL_TASK_PER_XP = "per_xp";    
    public static final String COL_TASK_COMPLETED = "completed";
    public static final String COL_TASK_QUEST = "quest_id";
    public static final String COL_TASK_PARENT = "parent_task";
 
    // Table Create Statements
    // User table create statement
    private static final String CREATE_TABLE_USER = 
    	"CREATE TABLE " + TABLE_USER
    	+ " (" + KEY_ID + " INTEGER PRIMARY KEY,"
    	+ COL_USER_NAME + " TEXT NOT NULL,"
    	+ COL_USER_XP + " INTEGER DEFAULT 0,"
    	+ COL_USER_LEVEL + " INTEGER )";
 
    // Quest table create statement
    private static final String CREATE_TABLE_QUEST = 
    	"CREATE TABLE " + TABLE_QUEST
        + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
        + COL_QUEST_NAME + " TEXT NOT NULL,"
        + COL_QUEST_TYPE + " TEXT NOT NULL,"
        + COL_QUEST_XP + " INTEGER DEFAULT 0,"
        + COL_QUEST_CREATED + " TEXT NOT NULL,"
        + COL_QUEST_COMPLETED + " TEXT "
        + ")";
 
    // todo_tag table create statement
    private static final String CREATE_TABLE_TASK = 
    	"CREATE TABLE " + TABLE_TASK
        + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
        + COL_TASK_NAME + " TEXT NOT NULL,"
        + COL_TASK_DESC + " TEXT,"
        + COL_TASK_FIT_XP + " INTEGER DEFAULT 0,"
        + COL_TASK_INT_XP + " INTEGER DEFAULT 0,"
        + COL_TASK_ART_XP + " INTEGER DEFAULT 0,"
        + COL_TASK_CHR_XP + " INTEGER DEFAULT 0,"
        + COL_TASK_PER_XP + " INTEGER DEFAULT 0,"
        + COL_TASK_CREATED + " TEXT NOT NULL,"
        + COL_TASK_COMPLETED + " TEXT,"
        + COL_TASK_QUEST + " INTEGER,"
        + COL_TASK_PARENT + " INTEGER "
        + ")";
    
    public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create required tables
		db.execSQL(CREATE_TABLE_USER);
		db.execSQL(CREATE_TABLE_QUEST);
		db.execSQL(CREATE_TABLE_TASK);	
		
		Log.i(LOG, "Database Tables Created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
 
        Log.i(LOG, "Database Tables Dropped/Updated");
        // create new tables
        onCreate(db);
	}

	public boolean isNewDb(){
		String selectQuery = "SELECT  * FROM " + TABLE_USER;
		 
	    Log.i(LOG, "Checking for User Record");
	 
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor c = db.rawQuery(selectQuery, null);
	    	  
		return (c.moveToFirst() == false);
	}
}
