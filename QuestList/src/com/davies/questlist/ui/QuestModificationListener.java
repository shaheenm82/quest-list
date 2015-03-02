package com.davies.questlist.ui;

import com.davies.questlist.db.Task;

public interface QuestModificationListener {
	public void taskAdded(Task task);
	
	public void taskRemoved(Task task);
}
