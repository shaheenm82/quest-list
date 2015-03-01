package com.davies.questlist.ui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.Task;

public class NewQuestActivity extends ActionBarActivity implements OnClickListener{
	Quest quest;
	
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> taskList=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> taskListAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_quest);
		
		quest = new Quest();
		
		Button btnAddTask = (Button) findViewById(R.id.btnAddTask);
		btnAddTask.setOnClickListener(this);
		
		Button btnRemoveTask = (Button) findViewById(R.id.btnRemoveTask);
		btnRemoveTask.setOnClickListener(this);
		
		Button btnSaveQuest = (Button) findViewById(R.id.btnSaveQuest);
		btnSaveQuest.setOnClickListener(this);
		
		taskListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_quest, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.btnAddTask:
			Task t = new Task();
			String s;
			int i;
			
			s = ((TextView)findViewById(R.id.txtNewTaskName)).getText().toString();
			if (s.length() > 0){
				t.setName(s);				
			}else{
				Toast.makeText(this, "Task Name not Set", Toast.LENGTH_SHORT);
				break;
			}
			
			s = ((TextView)findViewById(R.id.txtNewTaskDesc)).getText().toString();
			if (s.length() > 0){
				t.setDescription(s);				
			}
			
			s = ((TextView)findViewById(R.id.txtNewTaskFit)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setFitness_xp(i);
			}
			
			s = ((TextView)findViewById(R.id.txtNewTaskInt)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setLearning_xp(i);
			}
			
			s = ((TextView)findViewById(R.id.txtNewTaskArt)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setCulture_xp(i);
			}
			
			s = ((TextView)findViewById(R.id.txtNewTaskChr)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setSocial_xp(i);
			}
			
			s = ((TextView)findViewById(R.id.txtNewTaskPer)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setPersonal_xp(i);
			}
			
			t.setCreated_date(DateFormat.getDateTimeInstance().format(Calendar.getInstance()));
			quest.addTask(t);
			break;
		case R.id.btnRemoveTask:
			
			break;
		case R.id.btnSaveQuest:
			
			break;
		}
	}
}
