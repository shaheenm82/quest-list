package com.davies.questlist.ui;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.Quest.QuestType;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestViewHolder {
	public ImageView questType = null;
	public TextView questName = null;
	public TextView questXp = null;
	
	public QuestViewHolder(View row) {
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
		
		xp = quest.getXp() + " XP";
		questXp.setText(xp);
	}
}
