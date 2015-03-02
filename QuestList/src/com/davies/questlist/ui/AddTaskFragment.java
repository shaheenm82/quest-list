package com.davies.questlist.ui;

import java.util.Date;

import com.davies.questlist.R;
import com.davies.questlist.db.Task;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddTaskFragment extends Fragment implements OnClickListener{

	private QuestModificationListener qmodListener;
	
	public static AddTaskFragment newInstance() {
		AddTaskFragment fragment = new AddTaskFragment();
		return fragment;
	}

	public AddTaskFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
		
		Button btnSaveTask = (Button) rootView.findViewById(R.id.btnSaveTask);
		btnSaveTask.setOnClickListener(this);
		
		qmodListener = (QuestModificationListener)getActivity();
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSaveTask:
			Task t = new Task();
			String s;
			int i;
			
			s = ((TextView)getView().findViewById(R.id.txtNewTaskName)).getText().toString();
			if (s.length() > 0){
				t.setName(s);				
			}else{
				Toast.makeText(getActivity(), "Task Name not Set", Toast.LENGTH_SHORT);
				break;
			}
			
			s = ((TextView)getView().findViewById(R.id.txtNewTaskDesc)).getText().toString();
			if (s.length() > 0){
				t.setDescription(s);				
			}
			
			s = ((TextView)getView().findViewById(R.id.txtNewTaskFit)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setFitness_xp(i);
			}
			
			s = ((TextView)getView().findViewById(R.id.txtNewTaskInt)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setLearning_xp(i);
			}
			
			s = ((TextView)getView().findViewById(R.id.txtNewTaskArt)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setCulture_xp(i);
			}
			
			s = ((TextView)getView().findViewById(R.id.txtNewTaskChr)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setSocial_xp(i);
			}
			
			s = ((TextView)getView().findViewById(R.id.txtNewTaskPer)).getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setPersonal_xp(i);
			}
			
			//Time time = new Time();
			//time.setToNow();
			
			Date date = new Date();
			//Log.d(LOG,"Time: " + DateFormat.format("", date));
			
			t.setCreated_date(DateFormat.format("yyyy-MM-dd HH:mm", date).toString());
						
			qmodListener.taskAdded(t);
			//getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
			//getActivity().getSupportFragmentManager().popBackStackImmediate();
			break;

		default:
			break;
		}
		
	}
}
