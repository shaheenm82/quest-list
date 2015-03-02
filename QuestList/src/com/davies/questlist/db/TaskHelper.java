package com.davies.questlist.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TaskHelper {
	// Logcat tag
    private static final String LOG = "TaskHelper";
    
    private String allTaskColumns[] = {DatabaseHelper.KEY_ID,
    		DatabaseHelper.COL_TASK_NAME, 
    		DatabaseHelper.COL_TASK_DESC, 
    		DatabaseHelper.COL_TASK_FIT_XP, 
    		DatabaseHelper.COL_TASK_INT_XP,
    		DatabaseHelper.COL_TASK_ART_XP,
    		DatabaseHelper.COL_TASK_CHR_XP,
    		DatabaseHelper.COL_TASK_PER_XP,
    		DatabaseHelper.COL_TASK_CREATED,
    		DatabaseHelper.COL_TASK_COMPLETED,
    		DatabaseHelper.COL_TASK_QUEST}; 
	
	DatabaseHelper dbHelper;
	
	public TaskHelper(Context context){
		dbHelper = new DatabaseHelper(context);
	}
	
	public long createTask(Task task, long quest_id){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COL_TASK_NAME, task.getName());
	    values.put(DatabaseHelper.COL_TASK_DESC, task.getDescription());
	    values.put(DatabaseHelper.COL_TASK_FIT_XP, task.getFitness_xp());
	    values.put(DatabaseHelper.COL_TASK_INT_XP, task.getLearning_xp());
	    values.put(DatabaseHelper.COL_TASK_ART_XP, task.getCulture_xp());
	    values.put(DatabaseHelper.COL_TASK_CHR_XP, task.getSocial_xp());
	    values.put(DatabaseHelper.COL_TASK_PER_XP, task.getPersonal_xp());
	    values.put(DatabaseHelper.COL_TASK_CREATED, task.getCreated_date());
	    values.put(DatabaseHelper.COL_TASK_COMPLETED, task.getCompleted_date());
	    values.put(DatabaseHelper.COL_TASK_QUEST, quest_id);
	    
	    Log.i(LOG,"Inserting Task record " + values.toString());
	    // insert row
	    long id = db.insert(DatabaseHelper.TABLE_TASK, null, values);
	    
	    db.close();
	    return id;
	}
	
	public List<Task> getTasksForQuest (long id){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<Task> taskList = new ArrayList<>();
		Task task;
		
		String[] args = {Long.toString(id)};
		
		Cursor c = db.query(DatabaseHelper.TABLE_TASK, allTaskColumns, 
				"quest_id = ?", 
				args, null, null, 
				DatabaseHelper.COL_TASK_COMPLETED + "," + DatabaseHelper.COL_TASK_CREATED);
		
		if (c.moveToFirst()){
			task = new Task();
			task.setId(c.getLong(0));
			task.setName(c.getString(1));
			task.setDescription(c.getString(2));
			task.setFitness_xp(c.getInt(3));
			task.setLearning_xp(c.getInt(4));
			task.setCulture_xp(c.getInt(5));
			task.setSocial_xp(c.getInt(6));
			task.setPersonal_xp(c.getInt(7));
			task.setCreated_date(c.getString(8));
			task.setCompleted_date(c.getString(9));
			
			Log.i(LOG,"Adding Task record " + task);
			taskList.add(task);
		}
		
		db.close();
		
		//Log.e(LOG,"No User Record Found!!!");
		return taskList;
	}
		
//	public int updateQuestData(Quest quest){
//		SQLiteDatabase db = dbHelper.getReadableDatabase();
//		
//		String where = "id = ?";
//		String arg[] = {Long.toString(user.getId())};
//		
//		ContentValues values = new ContentValues();
//	    values.put(DatabaseHelper.COL_USER_NAME, user.getName());
//	    values.put(DatabaseHelper.COL_USER_XP, user.getXp());
//	    values.put(DatabaseHelper.COL_USER_LEVEL, user.getLevel());
//	    
//	    Log.i(LOG,"Updating User record " + user);
//	    
//	    int rows = db.update(DatabaseHelper.TABLE_USER, values, where, arg);
//	    
//	    db.close();
//	    
//		return rows;
//	}
}
