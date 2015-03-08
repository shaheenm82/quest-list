package com.davies.questlist.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.QuestHelper;
import com.davies.questlist.db.Task;

public class AddQuestActivity extends ActionBarActivity implements TaskCreationListener, QuestCreationListener{
	private static final String LOG = "AddQuestActivity";
	
	QuestCreationListener questCreationListener;
	
	AddQuestFragment questFragment;
	AddTaskFragment taskFragment;
	
	String current_fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_quest);
		
		questFragment = AddQuestFragment.newInstance();
		
		questCreationListener = questFragment;
		questFragment.setTaskCreationListener(this);
		
		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, questFragment).commit();
		
		current_fragment = "quest";
		
		Bundle b = getIntent().getExtras();
		
		if (b != null){
			long id = b.getLong("quest_id");
			QuestHelper qh = new QuestHelper(this);
			
			questFragment.setQuest(qh.getQuest(id));
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
		//Handle Action Bar Save Button
		if (id == R.id.action_save_quest) {
			//Save Task when on task Fragment
			if (current_fragment.equals("task")){
				taskFragment.saveTask();
			//Save Quest when on quest Fragment
			}else if(current_fragment.equals("quest")){
				QuestHelper qhelper = new QuestHelper(this);
				
				switch (questFragment.saveQuest()){
				case 1:
					qhelper.createQuest(questFragment.getQuest());
					
					Log.d(LOG,"Saving New Quest");
					break;
				case 2:
					qhelper.updateQuest(questFragment.getQuest());
					
					Log.d(LOG,"Updating Quest");
					break;
				}
				
				finish();
				
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void newTask() {
		current_fragment = "task";
		
		taskFragment = AddTaskFragment.newInstance();
		taskFragment.setQuestCreationListener(this);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		fragmentTransaction.replace(R.id.container, taskFragment);
		fragmentTransaction.addToBackStack(current_fragment);
		fragmentTransaction.commit();		
	}

	@Override
	public void editTask(Task task) {
		current_fragment = "task";
		
		taskFragment = AddTaskFragment.newInstance();
		taskFragment.setQuestCreationListener(this);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		fragmentTransaction.replace(R.id.container, taskFragment);
		fragmentTransaction.addToBackStack(current_fragment);
		fragmentTransaction.commit();
		
		taskFragment.editTask(task);
	}

	@Override
	public void taskCreated(Task task) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStack();
		questCreationListener.taskCreated(task);
		
		current_fragment = "quest";
	}

	@Override
	public void taskUpdated(Task task) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.popBackStack();
		questCreationListener.taskUpdated(task);
		
		current_fragment = "quest";
	}
}
