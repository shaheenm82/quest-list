package com.davies.questlist.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.Task;

public class QuestListAdapter extends BaseExpandableListAdapter{

	private Context context;
    
    private List<Quest> quests;
    
    public QuestListAdapter(Context context) {
        this.context = context;
    }
    
    public QuestListAdapter(Context context,List<Quest> quests) {
        this.context = context;
        this.quests = quests;
    }
    
    public void removeItem(Quest quest){
    	quests.remove(quest);
    	notifyDataSetChanged();
    }
    
    public void setItems(List<Quest> quests){
    	this.quests = quests;
    }
    
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return quests.get(groupPosition).getTasks().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return (groupPosition * 100) + childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TaskViewHolder taskviewHolder;
		Task task = (Task)getChild(groupPosition, childPosition);
		
		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.task_list_item, null);
			
			taskviewHolder = new TaskViewHolder(convertView);
			convertView.setTag(taskviewHolder);
		}else{
			taskviewHolder = (TaskViewHolder) convertView.getTag();
		}
		
		taskviewHolder.populateFrom(task);				
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return quests.get(groupPosition).getTasks().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return quests.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return quests.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return (groupPosition*100);
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		QuestViewHolder questviewHolder;
		Quest mcat = (Quest) getGroup(groupPosition);
			
		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.quest_list_item, null);
			
			questviewHolder = new QuestViewHolder(convertView);			
			convertView.setTag(questviewHolder);
		}else{
			questviewHolder = (QuestViewHolder) convertView.getTag();
		}
			
		questviewHolder.populateFrom(mcat);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
