package com.davies.questlist.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.davies.questlist.R;
import com.davies.questlist.db.Task;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link TaskListFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class TaskListFragment extends Fragment {
	private static final String LOG = "TaskListFragment";
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> taskList;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> taskListAdapter;
	
    TaskCreationListener taskCreationListener;
    
	public TaskListFragment() {
		// Required empty public constructor
		taskList=new ArrayList<String>();
		
			
	}
	
	public TaskListFragment(ArrayList<String> taskList) {
		// Required empty public constructor
		this();
		this.taskList = taskList;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(LOG,"TaskListFragment onCreateView ");
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
		
		ListView taskview = (ListView) rootView.findViewById(R.id.lstTasks);
		
		for (String t : taskList) {
			Log.d(LOG,"TaskList Contains " + t);
		}
		
		taskListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskList);	
		
		taskview.setAdapter(taskListAdapter);
		registerForContextMenu(taskview);
		
		taskListAdapter.notifyDataSetChanged();
		
		return rootView;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.lstTasks) {
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
			  taskCreationListener.editTask(info.position);
			  break;
		  case 1: //Delete Option
			  taskCreationListener.deleteTask(info.position);
			  break;
		  }
		  return true;
	}

	public void setTaskCreationListener( TaskCreationListener tcl){
		this.taskCreationListener = tcl;
	}

	public void setTaskList(ArrayList<Task> tasks){
		Log.d(LOG,"TaskListFragment setTaskList");
		
		taskList = new ArrayList<String>();
		for (Task task : tasks) {
			taskList.add(task.getName());
		}
	}
	
	public void addTask(String t){
		taskList.add(t);
		
		taskListAdapter.notifyDataSetChanged();
	}
	
	public void updateTask(int p, String t){
		taskList.set(p, t);
		
		taskListAdapter.notifyDataSetChanged();
	}
	
	public void deleteTask(int p){
		taskList.remove(p);
		
		taskListAdapter.notifyDataSetChanged();
	}
}
