package com.davies.questlist.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.Task;

public class AddQuestActivity extends ActionBarActivity implements OnClickListener{
	QuestCreationListener qmodListener;
	//TaskCreationListener 
	
	//Quest quest;
	AddQuestFragment questFragment;
	AddTaskFragment taskFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_quest);		
		if (savedInstanceState == null) {
			questFragment = AddQuestFragment.newInstance();
			taskFragment = AddTaskFragment.newInstance();
			
			questFragment.setTaskCreationListener(taskFragment);
			taskFragment.setQuestCreationListener(questFragment);
			
			//Fragment f = AddQuestFragment.newInstance();
			//qmodListener = (QuestCreationListener)f;
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, questFragment).commit();
			
			//quest = new Quest();
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_quest, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_save_quest) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.btnAddTask:
			Fragment f = AddTaskFragment.newInstance();
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			
			fragmentTransaction.replace(R.id.container, f);
			fragmentTransaction.addToBackStack("task");
			fragmentTransaction.commit();
			break;
		}		
	}

//	@Override
//	public void taskAdded(Task task) {
//		getSupportFragmentManager().popBackStackImmediate();
//		qmodListener.taskAdded(task);		
//	}
//
//	@Override
//	public void taskRemoved(Task task) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void newTask() {
//		// TODO Auto-generated method stub
//		Fragment f = AddTaskFragment.newInstance();
//		FragmentManager fragmentManager = getSupportFragmentManager();
//		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//		
//		fragmentTransaction.replace(R.id.container, f);
//		fragmentTransaction.addToBackStack("task");
//		fragmentTransaction.commit();
//	}
//
//	@Override
//	public void editTask(Task task) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void taskCreated(Task task) {
//		// TODO Auto-generated method stub
//		quest.addTask(task);
//		getSupportFragmentManager().popBackStackImmediate();
//	}
//
//	@Override
//	public void taskUpdated(Task task) {
//		// TODO Auto-generated method stub
//		
//	}
}
