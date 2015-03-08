package com.davies.questlist.ui;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.davies.questlist.R;
import com.davies.questlist.db.Task;

public class TaskViewHolder {
	private static final String LOG = "TaskViewHolder";
	
	public TextView taskName = null;
	public TextView taskDesc = null;
	public TextView taskFitXp = null;
	public TextView taskIntXp = null;
	public TextView taskArtXp = null;
	public TextView taskChrXp = null;
	public TextView taskPerXp = null;
	
	private Context context;
	
	public TaskViewHolder(View row) {
		context = row.getContext();
		
		taskName = (TextView) row.findViewById(R.id.txtTaskName);
		taskDesc = (TextView) row.findViewById(R.id.txtTaskDesc);
		taskFitXp = (TextView) row.findViewById(R.id.txtXpFit);
		taskIntXp = (TextView) row.findViewById(R.id.txtXpInt);
		taskArtXp = (TextView) row.findViewById(R.id.txtXpArt);
		taskChrXp = (TextView) row.findViewById(R.id.txtXpChr);
		taskPerXp = (TextView) row.findViewById(R.id.txtXpPer);
	}

	public void populateFrom(Task task){
		String xp;
		
		taskName.setText(task.getName());
		if (task.getCompleted_date() != null){
			taskName.setBackgroundColor(context.getResources().getColor(R.color.task_completed));
			taskName.setPaintFlags(taskName.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
		}else{
			taskName.setBackgroundColor(context.getResources().getColor(R.color.task_pending));
			taskName.setPaintFlags(taskName.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		Log.d(LOG, context.getString(R.string.xp_fit));
		
		xp = context.getString(R.string.xp_fit) + " " + task.getFitness_xp();
		taskFitXp.setText(xp);
		
		xp = context.getString(R.string.xp_int) + " " + task.getLearning_xp();
		taskIntXp.setText(xp);
		
		xp = context.getString(R.string.xp_art) + " " + task.getCulture_xp();
		taskArtXp.setText(xp);
		
		xp = context.getString(R.string.xp_chr) + " " + task.getSocial_xp();
		taskChrXp.setText(xp);
		
		xp = context.getString(R.string.xp_per) + " " + task.getPersonal_xp();
		taskPerXp.setText(xp);
		
		taskDesc.setText(task.getDescription());
		
		
	}
}
