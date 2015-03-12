package com.davies.questlist.ui;

import com.davies.questlist.db.Task;

public interface TaskCompletionListener {
	//public void completeTask(int position);
	
	public void completeTask(Task task);
	
	//public void uncompleteTask(int position);
	
	public void uncompleteTask(Task task);
	
}
