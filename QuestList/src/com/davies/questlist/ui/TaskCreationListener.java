package com.davies.questlist.ui;

import java.util.List;

import com.davies.questlist.db.Task;

public interface TaskCreationListener {
	public void newTask();
	
	public void editTask(Task task);
}
