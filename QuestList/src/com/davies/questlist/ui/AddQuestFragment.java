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
import android.widget.TextView;
import android.widget.Toast;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.Quest.QuestType;

public class AddQuestFragment extends Fragment implements OnClickListener{
	Quest quest;
	int editIndex;
	
	private static final String LOG = "AddQuestFragment";
	    
    TaskCreationListener taskCreationListener;
    
    private static TextView txtName;
	private static TextView txtXP;
	private static TextView txtTotalXP;
	
	private String purpose;
	private boolean enabled;
	
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
		
		purpose = "new";
		enabled = true;
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
		txtTotalXP = (TextView) rootView.findViewById(R.id.txtTotalQuestXp);
		
		((Button) rootView.findViewById(R.id.btnAddTask)).setOnClickListener(this);
		
		if (quest != null){
			txtName.setText(quest.getName());
			txtXP.setText(Integer.toString(quest.getXp()));
			txtTotalXP.setText(Integer.toString(quest.getTotalXP()));
		}
		
		if (enabled){
			enableFragment();
		}else{
			disableFragment();
		}
		//enableFragment();
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

	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public void disableFragment(){
		Log.d(LOG,"disableFragment");
		txtName.setEnabled(false);
		txtXP.setEnabled(false);
	}
	
	public void enableFragment(){
		Log.d(LOG,"enableFragment");
		txtName.setEnabled(true);
		txtXP.setEnabled(true);
	}
	
	public void updateXP(Quest quest){
		quest.setXp(Integer.parseInt(txtXP.getText().toString()));
		txtTotalXP.setText(Integer.toString(quest.getTotalXP()));
	}
	
	public void setTaskCreationListener(TaskCreationListener tcl){
		Log.d(LOG,"setTaskListener");
		taskCreationListener = tcl;
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
		
		
			
		purpose = "edit";
	}
	
	public Quest getQuest(){
		return quest;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.btnAddTask:
			taskCreationListener.newTask();
		}
	}
}
