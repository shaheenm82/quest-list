package com.davies.questlist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserHelper {
	// Logcat tag
    private static final String LOG = "UserHelper";
    
	DatabaseHelper dbHelper;
	
	public UserHelper(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public long createUser(User user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COL_USER_NAME, user.getName());
	    values.put(DatabaseHelper.COL_USER_XP, user.getXp());
	    values.put(DatabaseHelper.COL_USER_LEVEL, user.getLevel());
	 
	    Log.i(LOG,"Inserting User record " + values.toString());
	    // insert row
	    long id = db.insert(DatabaseHelper.TABLE_USER, null, values);
	    
	    db.close();
	    return id;
	}
	
	public User getUserData(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		User user;
		
		Cursor c = db.query(DatabaseHelper.TABLE_USER, null, null, null, null, null, null);
		
		if (c.moveToFirst()){
			user = new User(c.getString(1), c.getInt(2), c.getInt(8));
			user.setId(c.getLong(0));
			user.setFitness_xp(c.getInt(3));
			user.setLearning_xp(c.getInt(4));
			user.setCulture_xp(c.getInt(5));
			user.setSocial_xp(c.getInt(6));
			user.setPersonal_xp(c.getInt(7));
			
			Log.i(LOG,"Reading User record " + user);
			
			db.close();
			
			return user;
		}
		
		db.close();
		
		user = new User("NewUser");
		Log.e(LOG,"getUserData: No User Record Found!!!");
		return user;
	}
	
	public int updateUserData(User user){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		String where = "_id = ?";
		String arg[] = {Long.toString(user.getId())};
		
		ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COL_USER_NAME, user.getName());
	    values.put(DatabaseHelper.COL_USER_XP, user.getXp());
	    values.put(DatabaseHelper.COL_USER_FIT_XP, user.getFitness_xp());
	    values.put(DatabaseHelper.COL_USER_INT_XP, user.getLearning_xp());
	    values.put(DatabaseHelper.COL_USER_ART_XP, user.getCulture_xp());
	    values.put(DatabaseHelper.COL_USER_CHR_XP, user.getSocial_xp());
	    values.put(DatabaseHelper.COL_USER_PER_XP, user.getPersonal_xp());
	    values.put(DatabaseHelper.COL_USER_LEVEL, user.getLevel());
	    
	    Log.i(LOG,"Updating User record " + user);
	    
	    int rows = db.update(DatabaseHelper.TABLE_USER, values, where, arg);
	    
	    db.close();
	    
		return rows;
	}
}
