package com.davies.questlist.ui;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.davies.questlist.R;
import com.davies.questlist.db.Task;

public class AddTaskFragment extends Fragment implements OnClickListener{

	private static final String LOG = "AddTaskFragment";
	
	private TaskCreationListener taskCreationListener;
	
	private String purpose;
	private Task task;
	
	private boolean enabled;
	
	private static TextView txtName;
	private static TextView txtDescription;
	private static CheckBox chkFit;
	private static CheckBox chkInt;
	private static CheckBox chkArt;
	private static CheckBox chkChr;
	private static CheckBox chkPer;
	private static TextView txtXp;

	
	public static AddTaskFragment newInstance() {
		AddTaskFragment fragment = new AddTaskFragment();
		return fragment;
	}
	
	public AddTaskFragment() {
		// Required empty public constructor
		purpose = "new";
		enabled = false;
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
		
		((Button) rootView.findViewById(R.id.btnSaveTask)).setOnClickListener(this);
		
		txtName = (TextView) rootView.findViewById(R.id.txtNewTaskName);
		txtDescription = (TextView) rootView.findViewById(R.id.txtNewTaskDesc);
		chkFit = (CheckBox) rootView.findViewById(R.id.chkNewTaskFit);
		chkInt = (CheckBox) rootView.findViewById(R.id.chkNewTaskInt);
		chkArt = (CheckBox) rootView.findViewById(R.id.chkNewTaskArt);
		chkChr = (CheckBox) rootView.findViewById(R.id.chkNewTaskChr);
		chkPer = (CheckBox) rootView.findViewById(R.id.chkNewTaskPer);
		txtXp = (TextView) rootView.findViewById(R.id.txtNewTaskXp);
		
		if (enabled){
			enableFragment();
		}else{
			disableFragment();
		}
		//disableFragment();
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

	public void clearFragment(){
		txtName.setText("");
		
		txtDescription.setText("");
		
		chkFit.setChecked(false);
		
		chkInt.setChecked(false);
		
		chkArt.setChecked(false);
		
		chkChr.setChecked(false);
		
		chkPer.setChecked(false);
		
		txtXp.setText("");
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public void disableFragment(){
		Log.d(LOG,"disableFragment");
		txtName.setEnabled(false);
		
		txtDescription.setEnabled(false);
		
		chkFit.setEnabled(false);
		
		chkInt.setEnabled(false);
		
		chkArt.setEnabled(false);
		
		chkChr.setEnabled(false);
		
		chkPer.setEnabled(false);
		
		txtXp.setEnabled(false);
	}
	
	public void enableFragment(){
		Log.d(LOG,"enableFragment");
		txtName.setEnabled(true);
		
		txtDescription.setEnabled(true);
		
		chkFit.setEnabled(true);
		
		chkInt.setEnabled(true);
		
		chkArt.setEnabled(true);
		
		chkChr.setEnabled(true);
		
		chkPer.setEnabled(true);
		
		txtXp.setEnabled(true);
	}
	
	public void setTaskCreationListener(TaskCreationListener tcl){
		taskCreationListener = tcl;
	}

	public void editTask(Task task){
		this.task = task;
		
		txtName.setText(task.getName());
		
		txtDescription.setText(task.getDescription());
		
		chkFit.setChecked(task.isFitness_xp());
		
		chkInt.setChecked(task.isLearning_xp());
		
		chkArt.setChecked(task.isCulture_xp());
		
		chkChr.setChecked(task.isSocial_xp());
		
		chkPer.setChecked(task.isPersonal_xp());
		
		txtXp.setText(String.valueOf(task.getXp()));
		
		purpose = "edit";
	}
	
	public boolean saveTask(){
		String s;
		int i;
		int v;
		boolean success;
		Task t;
		
		success = false;
		
		Log.d(LOG,"purpose " + purpose);
		
		if (purpose.equals("new")){
			Log.d(LOG,"new task");
			
			t = new Task();
		}else{
			Log.d(LOG,"using task " + task);
			
			t = task;
		}
		
		v = validateInput();
		if ( v == 0){
			s = txtName.getText().toString();
			t.setName(s);
			
			s = txtDescription.getText().toString();
			if (s.length() > 0){
				t.setDescription(s);				
			}
			
			t.setFitness_xp(chkFit.isChecked());
			t.setLearning_xp(chkInt.isChecked());
			t.setCulture_xp(chkArt.isChecked());
			t.setSocial_xp(chkChr.isChecked());
			t.setPersonal_xp(chkPer.isChecked());
			
			s = txtXp.getText().toString();
			if (s.length() > 0){
				i = Integer.parseInt(s);
				t.setXp(i);
			}
			
			if (purpose.equals("new")){
				Date date = new Date();
				t.setCreated_date(DateFormat.format("yyyy-MM-dd HH:mm", date).toString());
				
				taskCreationListener.taskCreated(t);
			}else{
				taskCreationListener.taskUpdated(t);
			}
			
			success = true;
		}else{
			switch (v){
			case 1:
				Toast.makeText(getActivity(), "Task Name not Set", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(getActivity(), "Experience Value not Set", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getActivity(), "No Skill selected", Toast.LENGTH_SHORT).show();
				break;
			}
			
		}
		
		return success;
	}
	
	private int validateInput(){
		String n = txtName.getText().toString();
		String x = txtXp.getText().toString();
		boolean checked = false;
		
		if (n.length() == 0){
			return 1;
		}
		
		if (x.length() == 0){
			return 2;
		}
		
		checked = chkFit.isChecked() || chkInt.isChecked() || chkArt.isChecked()
				|| chkChr.isChecked() || chkPer.isChecked();
		Log.d(LOG,"Fit: " + chkFit.isChecked());
		Log.d(LOG,"Int: " + chkInt.isChecked());
		Log.d(LOG,"Art: " + chkArt.isChecked());
		Log.d(LOG,"Chr: " + chkChr.isChecked());
		Log.d(LOG,"Per: " + chkPer.isChecked());
		Log.d(LOG,"checked: " + checked);
		
		if (!checked){
			
			return 3;
		}
		
		return 0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.btnSaveTask:
			saveTask();
			//if (saveTask()){
//				Log.d(LOG,"Task Created " + task.getName());
//				//taskCreationListener.taskCreated(task);
//				if (purpose.equals("new")){
//					//Date date = new Date();
//					//t.setCreated_date(DateFormat.format("yyyy-MM-dd HH:mm", date).toString());
//					
//					taskCreationListener.taskCreated(task);
//				}else{
//					taskCreationListener.taskUpdated(task);
//				}
			//}
		}
	}
}
