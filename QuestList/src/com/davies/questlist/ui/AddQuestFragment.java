package com.davies.questlist.ui;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.TaskHelper;
import com.davies.questlist.db.Quest.QuestType;
import com.davies.questlist.db.Task;

public class AddQuestFragment extends Fragment implements QuestCreationListener, OnClickListener{
	Quest quest;
	Task task;
	int editIndex;
	
	private static final String LOG = "AddQuestFragment";
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> taskList;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> taskListAdapter;
    
    TaskCreationListener taskCreationListener;
    
    private static TextView txtName;
	private static TextView txtXP;
	
	private String purpose;
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment NewUserFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static AddQuestFragment newInstance() {
		AddQuestFragment fragment = new AddQuestFragment();
		return fragment;
	}

	public AddQuestFragment() {
		// Required empty public constructor
		quest = new Quest();
		
		taskList=new ArrayList<String>();
		
		purpose = "new";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_add_quest, container, false);
		
		txtName = (TextView) rootView.findViewById(R.id.txtNewQuestName);
		txtXP = (TextView) rootView.findViewById(R.id.txtNewQuestXp);
		
		Button btnAddTask = (Button) rootView.findViewById(R.id.btnAddTask);
		btnAddTask.setOnClickListener(this);
		
		ListView taskview = (ListView) rootView.findViewById(R.id.lstQuestTasks);
				
		taskListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskList);
		
		taskview.setAdapter(taskListAdapter);
		registerForContextMenu(taskview);
		
		if (quest.getId() != 0 && purpose.equals("new")){
			txtName.setText(quest.getName());
			txtXP.setText(Integer.toString(quest.getXp()));
			
			for (Task task : quest.getTasks()) {
				taskList.add(task.toString());
			}
			taskListAdapter.notifyDataSetChanged();
			
			purpose = "edit";
		}
		return rootView;
	}

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);				
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.lstQuestTasks) {
		    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		    menu.setHeaderTitle("Task: " + taskList.get(info.position));
		    String[] menuItems = getResources().getStringArray(R.array.task_list_menu);
		    for (int i = 0; i<menuItems.length; i++) {
		      menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		  }
	}

	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		  int menuItemIndex = item.getItemId();
		  
		  
		  String[] menuItems = getResources().getStringArray(R.array.task_list_menu);
		  String menuItemName = menuItems[menuItemIndex];
		  String listItemName = taskList.get(info.position);

		  switch (menuItemIndex){
		  case 0: //Edit Option
			  	editIndex = info.position;
				task = quest.getTasks().get(info.position);
				taskCreationListener.editTask(task);
			  break;
		  case 1: //Delete Option
			  Task t = quest.getTasks().remove(info.position);
			  taskList.remove(info.position);
			  taskListAdapter.notifyDataSetChanged();
			  if (purpose.equals("edit")){
				  TaskHelper thelper = new TaskHelper(getActivity());
				  thelper.deleteTask(t.getId());
			  }
			  break;
		  }
		  return true;
	}

	public void setTaskCreationListener(TaskCreationListener tcl){
		taskCreationListener = tcl;
	}

	@Override
	public void taskCreated(Task task) {
		Log.i(LOG,"Adding Task : " + task.getName());
		
		taskList.add(task.getName());
		taskListAdapter.notifyDataSetChanged();
		
		quest.addTask(task);
	}

	@Override
	public void taskUpdated(Task task) {
		Log.d(LOG,"Updating Task " + task.getName() + " at index " + editIndex);
		
		for (Task t: quest.getTasks()) {
			Log.d(LOG,"Quest contained " + t.toStringDebug());
		}
		quest.getTasks().set(editIndex, task);
//		for (Task t: quest.getTasks()) {
//			Log.d(LOG,"Quest contains " + t.toStringDebug());
//		}
		
		taskList.clear();
		taskListAdapter.notifyDataSetChanged();
		
		for (Task t: quest.getTasks()) {
			Log.d(LOG,"Quest contains " + t.toStringDebug());
			taskList.add(t.getName());
		}
		taskListAdapter.notifyDataSetChanged();
		
//		for (String s: taskList) {
//			Log.d(LOG,"tasklist had " + s);
//		}
//		taskList.set(editIndex, task.getName());
//		
//		
//		for (String s: taskList) {
//			Log.d(LOG,"tasklist has " + s);
//		}
		
		
	}

	public int saveQuest(){
		String s;
		int i;
		int success;
		
		success = 0;
		s = txtName.getText().toString();
		if (s.length() > 0){
			quest.setName(s);
			
			s = txtXP.getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				quest.setXp(i);
			}
			
			quest.setType(QuestType.PERSONAL);
			
			success = 2;
			
			if (purpose.equals("new")){
				Date date = new Date();
				
				quest.setCreated_date(DateFormat.format("yyyy-MM-dd HH:mm", date).toString());
				
				success = 1;
			}
			
			
		}else{
			Toast.makeText(getActivity(), "Quest Name not Set", Toast.LENGTH_SHORT).show();
		}
		
		return success;
	}
	
	public void setQuest(Quest quest){
		this.quest= quest;			
	}
	
	public Quest getQuest(){
		return quest;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddTask:
			taskCreationListener.newTask();
			break;

		default:
			break;
		}
		
	}	
}
