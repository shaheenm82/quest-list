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
    private Context context;
    
    private String allTaskColumns[] = {DatabaseHelper.KEY_ID,
    		DatabaseHelper.COL_TASK_NAME, 
    		DatabaseHelper.COL_TASK_DESC,
    		DatabaseHelper.COL_TASK_XP,
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
		this.context = context;
	}
	
	public long createTask(Task task, long quest_id){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COL_TASK_NAME, task.getName());
	    values.put(DatabaseHelper.COL_TASK_DESC, task.getDescription());
	    values.put(DatabaseHelper.COL_TASK_XP, task.getXp());
	    values.put(DatabaseHelper.COL_TASK_FIT_XP, task.isFitness_xp());
	    values.put(DatabaseHelper.COL_TASK_INT_XP, task.isLearning_xp());
	    values.put(DatabaseHelper.COL_TASK_ART_XP, task.isCulture_xp());
	    values.put(DatabaseHelper.COL_TASK_CHR_XP, task.isSocial_xp());
	    values.put(DatabaseHelper.COL_TASK_PER_XP, task.isPersonal_xp());
	    values.put(DatabaseHelper.COL_TASK_CREATED, task.getCreated_date());
	    values.put(DatabaseHelper.COL_TASK_COMPLETED, task.getCompleted_date());
	    values.put(DatabaseHelper.COL_TASK_QUEST, quest_id);
	    
	    Log.i(LOG,"Inserting Task record " + values.toString());
	    // insert row
	    long id = db.insert(DatabaseHelper.TABLE_TASK, null, values);
	    
	    db.close();
	    return id;
	}
	
	public long updateTask(Task task, long quest_id){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = task.getId();
		
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COL_TASK_NAME, task.getName());
	    values.put(DatabaseHelper.COL_TASK_DESC, task.getDescription());
	    values.put(DatabaseHelper.COL_TASK_XP, task.getXp());
	    values.put(DatabaseHelper.COL_TASK_FIT_XP, task.isFitness_xp());
	    values.put(DatabaseHelper.COL_TASK_INT_XP, task.isLearning_xp());
	    values.put(DatabaseHelper.COL_TASK_ART_XP, task.isCulture_xp());
	    values.put(DatabaseHelper.COL_TASK_CHR_XP, task.isSocial_xp());
	    values.put(DatabaseHelper.COL_TASK_PER_XP, task.isPersonal_xp());
	    values.put(DatabaseHelper.COL_TASK_CREATED, task.getCreated_date());
	    values.put(DatabaseHelper.COL_TASK_COMPLETED, task.getCompleted_date());
	    values.put(DatabaseHelper.COL_TASK_QUEST, quest_id);
	    
	    String[] args = {Long.toString(task.getId())};
	    
	    Log.i(LOG,"Updating Task record " + task.getId() + " values " + values.toString());
	    // insert row
	    int updated = db.update(DatabaseHelper.TABLE_TASK, values, "_id = ?", args);
	    
	    if(updated == 0){
	    	id = createTask(task, quest_id);
	    }
	    
	    db.close();
	    return id;
	}
	
	public long completeTask(Task task, User user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//UserHelper uhelper = new UserHelper(context); 
	    ContentValues values = new ContentValues();
	    
	    values.put(DatabaseHelper.COL_TASK_COMPLETED, task.getCompleted_date());
	    
	    String[] args = {Long.toString(task.getId())};
	    
	    Log.i(LOG,"Updating Task : id " + task.getId() + " values " + values.toString());
	    // update row
	    int id = db.update(DatabaseHelper.TABLE_TASK, values, "_id = ?", args);
	    //User user = uhelper.getUserData();
	    
	    user.addXp(task.getXp());
	    
	    if (task.isFitness_xp()){
	    	 user.addSkillXp(SkillTree.FITNESS, task.getXp());
	    }
	    if (task.isLearning_xp()){
	    	 user.addSkillXp(SkillTree.LEARNING, task.getXp());
	    }
	    if (task.isCulture_xp()){
	    	 user.addSkillXp(SkillTree.CULTURE, task.getXp());
	    }
	    if (task.isSocial_xp()){
	    	 user.addSkillXp(SkillTree.SOCIAL, task.getXp());
	    }
	    if (task.isPersonal_xp()){
	    	 user.addSkillXp(SkillTree.PERSONAL, task.getXp());
	    }
	    
	    Log.i(LOG,"Updating User : " + user.toDebugString());
	    
	    db.close();
	    return id;
	}
	
	public long uncompleteTask(Task task, User user){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//UserHelper uhelper = new UserHelper(context); 
	     
	    ContentValues values = new ContentValues();
	    values.putNull(DatabaseHelper.COL_TASK_COMPLETED);
	    
	    String[] args = {Long.toString(task.getId())};
	    
	    Log.i(LOG,"Updating Quest : id " + task.getId() + " values " + values.toString());
	    // update row
	    int id = db.update(DatabaseHelper.TABLE_TASK, values, "_id = ?", args);
	    
	    //User user = uhelper.getUserData();
	    
	    user.removeXp(task.getXp());
	    
	    if (task.isFitness_xp()){
	    	 user.removeSkillXp(SkillTree.FITNESS, task.getXp());
	    }
	    if (task.isLearning_xp()){
	    	 user.removeSkillXp(SkillTree.LEARNING, task.getXp());
	    }
	    if (task.isCulture_xp()){
	    	 user.removeSkillXp(SkillTree.CULTURE, task.getXp());
	    }
	    if (task.isSocial_xp()){
	    	 user.removeSkillXp(SkillTree.SOCIAL, task.getXp());
	    }
	    if (task.isPersonal_xp()){
	    	 user.removeSkillXp(SkillTree.PERSONAL, task.getXp());
	    }
	    
	    Log.i(LOG,"Updating User : " + user.toDebugString());
//	    if (id > 0){
//	    	QuestHelper qh = new QuestHelper(context);
//	    	qh.uncompleteQuest(task.getQuest_id());
//	    }
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
			do{
			task = new Task();
			task.setId(c.getLong(0));
			task.setName(c.getString(1));
			task.setDescription(c.getString(2));
			task.setXp(c.getInt(3));
			task.setFitness_xp(c.getInt(4) == 1);
			task.setLearning_xp(c.getInt(5) == 1);
			task.setCulture_xp(c.getInt(6) == 1);
			task.setSocial_xp(c.getInt(7) == 1);
			task.setPersonal_xp(c.getInt(8)== 1);
			task.setCreated_date(c.getString(9));
			task.setCompleted_date(c.getString(10));
			task.setQuest_id(c.getLong(11));
			
			Log.i(LOG,"Retrieving Task record " + task.toStringDebug());
			taskList.add(task);
			} while (c.moveToNext());
		}
		
		db.close();
		
		return taskList;
	}
		
	public int deleteTask(long id){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rows;
		String[] args = {Long.toString(id)};
		
		rows = db.delete(DatabaseHelper.TABLE_TASK, "_id = ?", args);
		
		db.close();
		return rows;
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
