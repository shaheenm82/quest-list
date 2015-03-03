package com.davies.questlist.db;

import java.util.ArrayList;
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
	    
	    Log.i(LOG,"Inserting Quest record " + values.toString());
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
		
		Cursor c = db.rawQuery(qry, null);
		
		if (c.moveToFirst()){
			quest = new Quest();
			
			quest.setId(c.getLong(0));
			quest.setName(c.getString(1));
			quest.setType(Quest.QuestType.valueOf(c.getString(2)));
			quest.setXp(c.getInt(3));
			quest.setCreated_date(c.getString(4));
			quest.setCompleted_date(c.getString(5));

			quest.setTasks(thelper.getTasksForQuest(quest.getId()));
			
			Log.i(LOG,"Adding Quest record " + quest);
			
			questList.add(quest);
		}
		
		db.close();
		
		//Log.e(LOG,"No User Record Found!!!");
		return questList;
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
