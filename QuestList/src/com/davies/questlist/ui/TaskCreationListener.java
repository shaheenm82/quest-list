package com.davies.questlist.ui;

import java.util.List;

import com.davies.questlist.db.Task;

public interface TaskCreationListener {
	public void newTask();
	
	//public void editTask(Task task);
	
	public void taskCreated(Task task);
	
	public void editTask(int pos);
	
	public void taskUpdated(Task task);
	
	public void deleteTask(int pos);
}
