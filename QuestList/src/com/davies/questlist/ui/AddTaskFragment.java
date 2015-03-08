package com.davies.questlist.ui;

import java.util.Date;
import java.util.List;

import com.davies.questlist.R;
import com.davies.questlist.db.Task;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class AddTaskFragment extends Fragment implements OnClickListener, TaskCreationListener{

	private static final String LOG = "AddTaskFragment";
	
	private QuestCreationListener questCreationListener;
	
	private String purpose;
	private Task task;
	
	private static TextView txtName;
	private static TextView txtDescription;
	private static TextView txtFit;
	private static TextView txtInt;
	private static TextView txtArt;
	private static TextView txtChr;
	private static TextView txtPer;

	
	public static AddTaskFragment newInstance() {
		AddTaskFragment fragment = new AddTaskFragment();
		return fragment;
	}
	
	public AddTaskFragment() {
		// Required empty public constructor
		purpose = "new";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(LOG,"onCreateView");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Log.d(LOG,"onCreateView");
		View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
		
		txtName = (TextView) rootView.findViewById(R.id.txtNewTaskName);
		txtDescription = (TextView) rootView.findViewById(R.id.txtNewTaskDesc);
		txtFit = (TextView) rootView.findViewById(R.id.txtNewTaskFit);
		txtInt = (TextView) rootView.findViewById(R.id.txtNewTaskInt);
		txtArt = (TextView) rootView.findViewById(R.id.txtNewTaskArt);
		txtChr = (TextView) rootView.findViewById(R.id.txtNewTaskChr);
		txtPer = (TextView) rootView.findViewById(R.id.txtNewTaskPer);
		
		Button btnSaveTask = (Button) rootView.findViewById(R.id.btnSaveTask);
		btnSaveTask.setOnClickListener(this);
		
		Log.d(LOG,"purpose " + purpose);

		if (purpose.equals("new")){
			txtName.setText("");
			
			txtDescription.setText("");
			
			txtFit.setText("");
			
			txtInt.setText("");
			
			txtArt.setText("");
			
			txtChr.setText("");
			
			txtPer.setText("");
		}else{
			Log.d(LOG,"Task " + task.toStringDebug());
			
			txtName.setText(task.getName());
			
			txtDescription.setText(task.getDescription());
			
			txtFit.setText(String.valueOf(task.getFitness_xp()));
			
			txtInt.setText(String.valueOf(task.getLearning_xp()));
			
			txtArt.setText(String.valueOf(task.getCulture_xp()));
			
			txtChr.setText(String.valueOf(task.getSocial_xp()));
			
			txtPer.setText(String.valueOf(task.getPersonal_xp()));
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

	public void setQuestCreationListener(QuestCreationListener qcl){
		questCreationListener = qcl;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSaveTask:
			saveTask();
			break;

		default:
			break;
		}
	}

	public boolean saveTask(){
		String s;
		int i;
		boolean success;
		Task t;
		
		success = false;
		
		if (purpose.equals("new")){
			t = new Task();
		}else{
			t = task;
		}
		
		s = txtName.getText().toString();
		if (s.length() > 0){
			t.setName(s);
			
			s = txtDescription.getText().toString();
			if (s.length() > 0){
				t.setDescription(s);				
			}
			
			s = txtFit.getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setFitness_xp(i);
			}
			
			s = txtInt.getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setLearning_xp(i);
			}
			
			s = txtArt.getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setCulture_xp(i);
			}
			
			s = txtChr.getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setSocial_xp(i);
			}
			
			s = txtPer.getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setPersonal_xp(i);
			}
			
			if (purpose.equals("new")){
				Date date = new Date();
				t.setCreated_date(DateFormat.format("yyyy-MM-dd HH:mm", date).toString());
				
				questCreationListener.taskCreated(t);
			}else{
				questCreationListener.taskUpdated(t);
			}
			
			success = true;
		}else{
			Toast.makeText(getActivity(), "Task Name not Set", Toast.LENGTH_SHORT).show();
		}
		
		return success;
	}
	
	@Override
	public void newTask() {
		purpose = "new";
	}

	@Override
	public void editTask(Task task) {
		this.task = task;
		
		purpose = "edit";
	}
}
