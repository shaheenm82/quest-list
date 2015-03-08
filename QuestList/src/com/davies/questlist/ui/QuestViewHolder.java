package com.davies.questlist.ui;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.Quest.QuestType;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestViewHolder {
	private View parent = null;
	public ImageView questType = null;
	public TextView questName = null;
	public TextView questXp = null;
	
	private Context context;
	
	public QuestViewHolder(View row) {
		context = row.getContext();
		
		parent = row;
		questType = (ImageView) row.findViewById(R.id.imgQuestType);
		questName = (TextView) row.findViewById(R.id.txtNewQuestName);
		questXp = (TextView) row.findViewById(R.id.txtNewQuestXp);
	}

	public void populateFrom(Quest quest){
		String xp;
		
		if (quest.getType() == QuestType.PUBLIC){
			questType.setImageResource(R.drawable.ic_quest_public);
		}else{
			questType.setImageResource(R.drawable.ic_user);
		}
		questName.setText(quest.getName());
		
		if (quest.isQuestCompleted()){
//			questName.setBackgroundColor(context.getResources().getColor(R.color.task_completed));
//			questType.setBackgroundColor(context.getResources().getColor(R.color.task_completed));
//			questXp.setBackgroundColor(context.getResources().getColor(R.color.task_completed));
			parent.setBackgroundColor(context.getResources().getColor(R.color.task_completed));
			
			questName.setPaintFlags(questName.getPaintFlags()|Paint.STRIKE_THRU_TEXT_FLAG);
		}else{
//			questName.setBackgroundColor(context.getResources().getColor(R.color.task_pending));
//			questType.setBackgroundColor(context.getResources().getColor(R.color.task_pending));
//			questXp.setBackgroundColor(context.getResources().getColor(R.color.task_pending));			
			parent.setBackgroundColor(context.getResources().getColor(R.color.task_pending));
			
			questName.setPaintFlags(questName.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
		}
		
		xp = quest.getXp() + " XP";
		questXp.setText(xp);
	}
}
