package com.davies.questlist.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.QuestHelper;
import com.davies.questlist.db.Task;

public class AddQuestActivity extends ActionBarActivity implements TaskCreationListener{
	private static final String LOG = "AddQuestActivity";
	
	Quest quest;
	QuestCreationListener questCreationListener;
	
	AddQuestFragment questFragment;
	AddTaskFragment taskFragment;
	TaskListFragment taskListFragment;
	
	String purpose;
	String current_fragment;
	int editIndex = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_quest);
		ViewGroup questLayout;
		ViewGroup taskLayout;
		ViewGroup taskListLayout;
		
		FragmentTransaction fragmentTransaction;
		
		Log.i(LOG, "onCreate: AddQuestActivity");
		// Restore state
	    if (savedInstanceState != null) {
	        // The fragment manager will handle restoring them if we are being
	        // restored from a saved state
	    	questFragment = (AddQuestFragment) getSupportFragmentManager().findFragmentByTag(AddQuestFragment.class.getName());
	    	questFragment.setTaskCreationListener(this);
	    	
	    	taskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag(AddTaskFragment.class.getName());
	    	taskFragment.setTaskCreationListener(this);
	    	
	    	taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentByTag(TaskListFragment.class.getName());
	    	taskListFragment.setTaskCreationListener(this);
	    	 
	    	current_fragment = savedInstanceState.getString("current");
	    	Log.i(LOG, "current fragment " + current_fragment);
	    	if (current_fragment.equals("task")){
	    		fragmentTransaction = getSupportFragmentManager().beginTransaction();
	    		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	    		fragmentTransaction.show(taskFragment);
	    		fragmentTransaction.commit();
	    		
	    		questFragment.setEnabled(false);
				taskFragment.setEnabled(true);
	    	}else{
	    		fragmentTransaction = getSupportFragmentManager().beginTransaction();
	    		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	    		fragmentTransaction.hide(taskFragment);
	    		fragmentTransaction.commit();
	    		
	    		questFragment.setEnabled(true);
				taskFragment.setEnabled(false);
	    	}
	    	purpose = savedInstanceState.getString("purpose");
	    	Log.i(LOG, "purpose " + purpose);
	    	
	    	quest = savedInstanceState.getParcelable("quest");
	    	
	    	taskListFragment.setTaskList((ArrayList<Task>)quest.getTasks());
	    }
	    // If this is the first creation of the activity, add fragments to it
	    else {
	    	questLayout = (ViewGroup) findViewById(R.id.quest_container);
            // Add quest fragment to the activity's container layout
            questFragment = AddQuestFragment.newInstance();
            questFragment.setTaskCreationListener(this);
    		quest = new Quest();
    		
    		fragmentTransaction = getSupportFragmentManager().beginTransaction();
    		fragmentTransaction.add(questLayout.getId(), questFragment,
    				AddQuestFragment.class.getName());    		
    		// Commit the transaction
            fragmentTransaction.commit();
                        
	        taskLayout = (ViewGroup) findViewById(R.id.task_container);
            // Add image rotator fragment to the activity's container layout
            taskFragment = AddTaskFragment.newInstance();
            Log.i(LOG, "onCreate: setTaskCreationListener");
    		taskFragment.setTaskCreationListener(this);
    		
    		fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(taskLayout.getId(), taskFragment,
            		AddTaskFragment.class.getName());
 
            // Commit the transaction
            fragmentTransaction.hide(taskFragment);
            fragmentTransaction.commit();
	            
	        taskListLayout = (ViewGroup) findViewById(R.id.quest_tasks);
	        taskListFragment = new TaskListFragment();
	        taskListFragment.setTaskCreationListener(this);
    		
    		fragmentTransaction = getSupportFragmentManager().beginTransaction();
    		
            fragmentTransaction.add(taskListLayout.getId(), taskListFragment,
            		TaskListFragment.class.getName());
 
            // Commit the transaction
            fragmentTransaction.commit();
            
            Bundle b = getIntent().getExtras();
    		
    		if (b != null){
    			long id = b.getLong("quest_id");
    			QuestHelper qh = new QuestHelper(this);
    			
    			quest = qh.getQuest(id);
    			questFragment.setQuest(quest);
    			
    			taskListFragment.setTaskList((ArrayList<Task>)quest.getTasks());
    			
    			purpose = "edit";
    		}else{
    			purpose = "new";
    		}
    		
    		current_fragment = "quest";
    		questFragment.setEnabled(true);
			taskFragment.setEnabled(false);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_quest, menu);
		return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
//		if (current_fragment.equals("quest")){
//			menu.add(R.id.action_add_task);
//			menu.removeItem(R.id.action_save_task);
//		}else{
//			menu.add(R.id.action_save_task);
//			menu.removeItem(R.id.action_add_task);
//			menu.removeItem(R.id.action_save_quest);
//		}
		
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		//Handle Action Bar Save Button
		if (id == R.id.action_save_quest) {
			QuestHelper qhelper = new QuestHelper(this);
			Quest q ;
				
			
				
			switch (questFragment.saveQuest()){
			case 1:
				q = questFragment.getQuest();
				quest.setName(q.getName());
				quest.setXp(q.getXp());
				quest.setType(q.getType());
				quest.setCreated_date(q.getCreated_date());
				
				qhelper.createQuest(quest);
				
				Log.d(LOG,"Saving New Quest");
				break;
			case 2:
				q = questFragment.getQuest();
				quest.setName(q.getName());
				quest.setXp(q.getXp());
				quest.setType(q.getType());
				quest.setCreated_date(q.getCreated_date());
				
				qhelper.updateQuest(quest);
				
				Log.d(LOG,"Updating Quest");
				break;
			}
			
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		outState.putString("current", current_fragment);
		outState.putString("purpose", purpose);
		
		outState.putParcelable("quest", quest);
	}

	@Override
	public void taskCreated(Task task) {
		quest.addTask(task);
		taskListFragment.addTask(task.getName());
		//taskFragment.clearFragment();
		
		taskFragment.disableFragment();
		questFragment.enableFragment();
		
		FragmentTransaction fragmentTransaction;
		
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.hide(taskFragment);
		fragmentTransaction.commit();
		
		questFragment.updateXP(quest);
		
		current_fragment = "quest";
	}

	@Override
	public void taskUpdated(Task task) {
		quest.getTasks().set(editIndex, task);
		taskListFragment.updateTask(editIndex, task.getName());
		
		current_fragment = "quest";
	}

	@Override
	public void editTask(int pos) {
		editIndex = pos;
		taskFragment.editTask(quest.getTasks().get(pos));
		taskFragment.enableFragment();
		questFragment.disableFragment();
		
		current_fragment = "task";
		supportInvalidateOptionsMenu();
	}

	@Override
	public void deleteTask(int pos) {
		quest.getTasks().remove(pos);
		taskListFragment.deleteTask(pos);
	}

	@Override
	public void newTask() {
		FragmentTransaction fragmentTransaction;
		
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.show(taskFragment);
		fragmentTransaction.commit();
		
		questFragment.disableFragment();
		taskFragment.enableFragment();
		taskFragment.clearFragment();
	}
}
