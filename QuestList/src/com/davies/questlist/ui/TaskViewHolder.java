package com.davies.questlist.ui;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.davies.questlist.R;
import com.davies.questlist.db.Task;

public class TaskViewHolder {
	private static final String LOG = "TaskViewHolder";
	
	public TextView taskName = null;
	public TextView taskDesc = null;
	public TextView taskXp = null;
	public TextView taskSkills = null;
	public CheckBox chkCompleted = null;
	
	private Context context;
	
	public TaskViewHolder(View row) {
		context = row.getContext();
		
		taskName = (TextView) row.findViewById(R.id.txtTaskName);
		taskDesc = (TextView) row.findViewById(R.id.txtTaskDesc);
		taskXp = (TextView) row.findViewById(R.id.txtXp);
		taskSkills = (TextView) row.findViewById(R.id.txtSkills);
		chkCompleted = (CheckBox) row.findViewById(R.id.chkCompleted);
	}

	public void populateFrom(Task task){
		String xp = "";
		
		taskName.setText(task.getName());
		if (task.getCompleted_date() != null){
			taskName.setBackgroundColor(context.getResources().getColor(R.color.task_completed));
			taskName.setPaintFlags(taskName.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
		}else{
			taskName.setBackgroundColor(context.getResources().getColor(R.color.task_pending));
			taskName.setPaintFlags(taskName.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		//Log.d(LOG, context.getString(R.string.xp_fit));
		taskDesc.setText(task.getDescription());
		
		taskXp.setText(context.getString(R.string.quest_xp_new) + " " + task.getXp());
		
		if (task.isFitness_xp()){
			xp = addSkill(xp, context.getString(R.string.xp_fit));
		}
		
		if (task.isLearning_xp()){
			xp = addSkill(xp, context.getString(R.string.xp_int));
		}
		
		if (task.isCulture_xp()){
			xp = addSkill(xp, context.getString(R.string.xp_art));
		}
		
		if (task.isSocial_xp()){
			xp = addSkill(xp, context.getString(R.string.xp_chr));
		}
		
		if (task.isPersonal_xp()){
			xp = addSkill(xp, context.getString(R.string.xp_per));
		}
		
		taskSkills.setText(xp);		
		chkCompleted.setChecked(task.getCompleted_date() != null);
	}
	
	private String addSkill(String skills, String skill){
		String retval;
		
		if (skills.length() > 0){
			retval = skills + ", " + skill;
		}else{
			retval = skill;
		}
		return retval;
	}
}
