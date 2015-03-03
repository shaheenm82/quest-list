package com.davies.questlist.ui;

import com.davies.questlist.db.Task;

public interface QuestCreationListener {
	public void taskCreated(Task task);
	
	public void taskUpdated(Task task);
}
