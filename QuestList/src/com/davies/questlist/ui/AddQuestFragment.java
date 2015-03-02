package com.davies.questlist.ui;

import java.util.ArrayList;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.Task;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AddQuestFragment extends Fragment implements QuestModificationListener{
	Quest quest;
	
	private static final String LOG = "AddQuestActivity";
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> taskList;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> taskListAdapter;
    
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
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (getArguments() != null) {
//			mParam1 = getArguments().getString(ARG_PARAM1);
//			mParam2 = getArguments().getString(ARG_PARAM2);
//		}
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_add_quest, container, false);
		
		Button btnAddTask = (Button) rootView.findViewById(R.id.btnAddTask);
		btnAddTask.setOnClickListener((OnClickListener)getActivity());
		
		Button btnRemoveTask = (Button) rootView.findViewById(R.id.btnRemoveTask);
		btnAddTask.setOnClickListener((OnClickListener)getActivity());
		
		ListView taskview = (ListView) rootView.findViewById(R.id.lstQuestTasks);
		
		taskList=new ArrayList<String>();
		
		taskListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskList);
		
		taskview.setAdapter(taskListAdapter);
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
	public void taskAdded(Task task) {
		// TODO Auto-generated method stub
		Log.d(LOG,"Adding Task : " + task.getName());
		
		taskList.add(task.getName());
		taskListAdapter.notifyDataSetChanged();
		
		quest.addTask(task);
	}

	@Override
	public void taskRemoved(Task task) {
		// TODO Auto-generated method stub
		
	}
}
