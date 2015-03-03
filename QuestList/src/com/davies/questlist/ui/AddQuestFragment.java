package com.davies.questlist.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.Task;

public class AddQuestFragment extends Fragment implements QuestCreationListener, OnClickListener{
	Quest quest;
	
	private static final String LOG = "AddQuestActivity";
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> taskList;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> taskListAdapter;
    
    TaskCreationListener taskCreationListener;
    
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
//		Bundle args = new Bundle();
//		args.putString(ARG_PARAM1, param1);
//		args.putString(ARG_PARAM2, param2);
//		fragment.setArguments(args);
		return fragment;
	}

	public AddQuestFragment() {
		// Required empty public constructor
		quest = new Quest();
		
		taskList=new ArrayList<String>();
		
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
		
		Button btnAddTask = (Button) rootView.findViewById(R.id.btnAddTask);
		btnAddTask.setOnClickListener(this);
		
		ListView taskview = (ListView) rootView.findViewById(R.id.lstQuestTasks);
				
		taskListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskList);
		
		taskview.setAdapter(taskListAdapter);
		//taskview.setOnItemLongClickListener(this);
		registerForContextMenu(taskview);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);				
	}

	@Override
	public void onDetach() {
		super.onDetach();
		//mListener = null;
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

		  Log.d(LOG,menuItemName + " Task : " + listItemName + " (" + menuItemIndex + ")");
		  
		  switch (menuItemIndex){
		  case 0:
			  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				
				fragmentTransaction.replace(R.id.container, (Fragment)taskCreationListener);
				fragmentTransaction.addToBackStack("task");
				fragmentTransaction.commit();
			
				taskCreationListener.editTask(quest.getTasks().get(info.position));
			  break;
		  case 1:
			  quest.getTasks().remove(info.position);
			  taskList.remove(info.position);
			  taskListAdapter.notifyDataSetChanged();
			  break;
		  }
		  //TextView text = (TextView)findViewById(R.id.footer);
		  //text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
		  return true;
	}

	public void setTaskCreationListener(TaskCreationListener tcl){
		taskCreationListener = tcl;
	}

	@Override
	public void taskCreated(Task task) {
		Log.d(LOG,"Adding Task : " + task.getName());
		
		taskList.add(task.getName());
		taskListAdapter.notifyDataSetChanged();
		
		quest.addTask(task);
		getActivity().getSupportFragmentManager().popBackStackImmediate();
	}

	@Override
	public void taskUpdated(Task task) {
		//quest.getTasks().set(, task)	
		getActivity().getSupportFragmentManager().popBackStackImmediate();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddTask:
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			
			fragmentTransaction.replace(R.id.container, (Fragment)taskCreationListener);
			fragmentTransaction.addToBackStack("task");
			fragmentTransaction.commit();
			
			taskCreationListener.newTask();
			break;

		default:
			break;
		}
		
	}	
}
