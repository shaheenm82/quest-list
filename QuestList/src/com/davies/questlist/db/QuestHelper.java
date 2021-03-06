package com.davies.questlist.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuestHelper {
	// Logcat tag
    private static final String LOG = "QuestHelper";
    private Context context;
    
    private String allQuestColumns[] = {DatabaseHelper.KEY_ID,
    		DatabaseHelper.COL_QUEST_NAME, 
    		DatabaseHelper.COL_QUEST_TYPE, 
    		DatabaseHelper.COL_QUEST_XP, 
    		DatabaseHelper.COL_QUEST_CREATED, 
    		DatabaseHelper.COL_QUEST_COMPLETED}; 
	
	DatabaseHelper dbHelper;
	
	public QuestHelper(Context context){
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}
	
	public long createQuest(Quest quest){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COL_QUEST_NAME, quest.getName());
	    values.put(DatabaseHelper.COL_QUEST_TYPE, quest.getType().toString());
	    values.put(DatabaseHelper.COL_QUEST_XP, quest.getXp());
	    values.put(DatabaseHelper.COL_QUEST_CREATED, quest.getCreated_date());
	    
	    Log.i(LOG,"Creating Quest : " + values.toString());
	    // insert row
	    long id = db.insert(DatabaseHelper.TABLE_QUEST, null, values);
	    
	    TaskHelper thelper = new TaskHelper(context);
	    for (Task task : quest.getTasks()) {
	    	//use Task Helper to add task
	    	thelper.createTask(task, id);
		}
	    db.close();
	    return id;
	}
	
	public long updateQuest(Quest quest){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COL_QUEST_NAME, quest.getName());
	    values.put(DatabaseHelper.COL_QUEST_TYPE, quest.getType().toString());
	    values.put(DatabaseHelper.COL_QUEST_XP, quest.getXp());
	    values.put(DatabaseHelper.COL_QUEST_CREATED, quest.getCreated_date());
	    
	    String[] args = {Long.toString(quest.getId())};
	    
	    Log.i(LOG,"Updating Quest : id " + quest.getId() + " values " + values.toString());
	    // update row
	    int id = db.update(DatabaseHelper.TABLE_QUEST, values, "_id = ?", args);
	    
	    TaskHelper thelper = new TaskHelper(context);
	    for (Task task : quest.getTasks()) {
	    	//use Task Helper to add task
	    	thelper.updateTask(task, quest.getId());
		}
	    db.close();
	    return id;
	}
	
	public long completeTask(Quest quest, Task task){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		TaskHelper th = new TaskHelper(context); 
		UserHelper uhelper = new UserHelper(context);
	    ContentValues values = new ContentValues();
	    
	    User user = uhelper.getUserData();
	    
	    int id = 0;
	    if (th.completeTask(task, user) > 0 && quest.isQuestCompleted()){
		    values.put(DatabaseHelper.COL_QUEST_COMPLETED, task.getCompleted_date());
		    
		    String[] args = {Long.toString(quest.getId())};
		    
		    Log.i(LOG,"Updating Quest : id " + quest.getId() + " values " + values.toString());
		    // update row
		    id = db.update(DatabaseHelper.TABLE_QUEST, values, "_id = ?", args);
		    
		    
		    user.addXp(quest.getXp());
		    
		    Log.i(LOG,"Updating User : " + user.toDebugString());
	    }
	    uhelper.updateUserData(user);
	    
	    db.close();
	    return id;
	}
	
	public long uncompleteTask(Quest quest, Task task){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		TaskHelper th = new TaskHelper(context); 
		UserHelper uhelper = new UserHelper(context);
	    ContentValues values = new ContentValues();
	    
	    User user = uhelper.getUserData();
	    int id = 0;
	    
	    if (th.uncompleteTask(task, user) > 0 && quest.getIncompleteTasks() == 1){
		    values.putNull(DatabaseHelper.COL_QUEST_COMPLETED);
		    
		    String[] args = {Long.toString(quest.getId())};
		    
		    Log.i(LOG,"Updating Quest : id " + quest.getId() + " values " + values.toString());
		    // update row
		    id = db.update(DatabaseHelper.TABLE_QUEST, values, "_id = ?", args);
		    		    		    
		    user.removeXp(quest.getXp());
		    
		    Log.i(LOG,"Updating User : " + user.toDebugString());
	    }
	    
	    uhelper.updateUserData(user);
		
	    db.close();
	    return id;
	}
	
//	public long uncompleteQuest(Quest quest){
//		return uncompleteQuest(quest.getId());
//	}
//	
//	public long uncompleteQuest(long quest_id){
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		 
//	    ContentValues values = new ContentValues();
//	    values.putNull(DatabaseHelper.COL_QUEST_COMPLETED);
//	    
//	    String[] args = {Long.toString(quest_id)};
//	    
//	    Log.i(LOG,"Updating Quest : id " + quest_id + " values " + values.toString());
//	    // update row
//	    int id = db.update(DatabaseHelper.TABLE_QUEST, values, "_id = ?", args);
//	    
//	    db.close();
//	    return id;
//	}
	
	public List<Quest> getQuestsForSkill(String skill){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		TaskHelper thelper = new TaskHelper(context);
		List<Quest> questList = new ArrayList<>();
		Quest quest;
		
		String qry = "select distinct(quest._id), quest.name, quest.type, quest.xp, quest.created_date, quest.completed from quest "
				+ "inner join task "
				+ "on (quest._id = task.quest_id) "
				+ "where task." + skill + "_xp > 0 "
				+ "order by quest.completed, quest.created_date";
		
		Log.d(LOG,qry);
		
		Cursor c = db.rawQuery(qry, null);
		
		if (c.moveToFirst()){
			do{
			quest = new Quest();
			
			quest.setId(c.getLong(0));
			quest.setName(c.getString(1));
			quest.setType(Quest.QuestType.valueOf(c.getString(2)));
			quest.setXp(c.getInt(3));
			quest.setCreated_date(c.getString(4));
			quest.setCompleted_date(c.getString(5));

			quest.setTasks(thelper.getTasksForQuest(quest.getId()));
			
			Log.i(LOG,"Retrieving Quest record " + quest.toStringDebug());
			
			questList.add(quest);
			} while (c.moveToNext());
		}
		
		db.close();
		
		return questList;
	}
	
	public Quest getQuest(long id){
		SQLiteDatabase db = dbHelper.getReadableDatabase();	
		TaskHelper thelper = new TaskHelper(context);
		String[] args = {Long.toString(id)};
		Quest quest = new Quest();
		
		Cursor c = db.query(DatabaseHelper.TABLE_QUEST, allQuestColumns, 
				"_id = ?", args, 
				null, null, null);
		
		if (c.moveToFirst()){
			quest.setId(c.getLong(0));
			quest.setName(c.getString(1));
			quest.setType(Quest.QuestType.valueOf(c.getString(2)));
			quest.setXp(c.getInt(3));
			quest.setCreated_date(c.getString(4));
			quest.setCompleted_date(c.getString(5));

			quest.setTasks(thelper.getTasksForQuest(quest.getId()));
			
			Log.i(LOG,"Retrieving Quest record " + quest);
		}
		
		db.close();
		return quest;
	}
	
	public int deleteQuest(Quest quest){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rows;
		String[] args = {Long.toString(quest.getId())};
		TaskHelper thelper = new TaskHelper(context);
		
		for (Task task: quest.getTasks()) {
			thelper.deleteTask(task.getId());
		}
		
		rows = db.delete(DatabaseHelper.TABLE_QUEST, "_id = ?", args);
		
		db.close();
		return rows;
	}
//	public int updateQuestData(Quest quest){
//		SQLiteDatabase db = dbHelper.getReadableDatabase();
//		
//		String where = "id = ?";
//		String arg[] = {Long.toString(quest.getId())};
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
