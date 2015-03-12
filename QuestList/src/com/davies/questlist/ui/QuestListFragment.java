package com.davies.questlist.ui;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListAdapter;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.QuestHelper;
import com.davies.questlist.db.Task;
import com.davies.questlist.db.UserHelper;

public class QuestListFragment extends Fragment implements TaskCompletionListener, OnChildClickListener{
	private static final String LOG = "QuestListFragment";
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SKILL_TREE = "skill_tree";
	//private static final int SWIPE_MIN_DISTANCE = 200;
	
	
	private ExpandableListView listView = null;
	private QuestListAdapter listAdapter = null;
	private QuestHelper qHelper = null;
	private String skill;
	
	private UserChangeListener userChangeListener;
	
	private GestureDetectorCompat gestureDetector;
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static QuestListFragment newInstance(int skilltree) {
		QuestListFragment fragment = new QuestListFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SKILL_TREE, skilltree);
		fragment.setArguments(args);
		return fragment;
	}

	public QuestListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int index;
		//String skill;
		
		View rootView = inflater.inflate(R.layout.fragment_quest_list,
				container, false);
		
		index = getArguments().getInt(ARG_SKILL_TREE);
		listView  = (ExpandableListView) rootView.findViewById(R.id.questListView);
		
		
		
		switch (index){
		case 0:
			skill = getResources().getString(R.string.xp_fit);
			break;
		case 1:
			skill = getResources().getString(R.string.xp_int);
			break;
		case 2:
			skill = getResources().getString(R.string.xp_art);
			break;
		case 3:
			skill = getResources().getString(R.string.xp_chr);
			break;
		case 4:
			skill = getResources().getString(R.string.xp_per);
			break;
		default:
			skill = getResources().getString(R.string.xp_fit);
			break;
		}
		
		qHelper = new QuestHelper(getActivity());	
		listAdapter = new QuestListAdapter(getActivity(), qHelper.getQuestsForSkill(skill));
		listAdapter.setTaskCompletionListener(this);
		
		listView.setAdapter(listAdapter);
		listView.setOnChildClickListener(this);
		//listView.setOnTouchListener(this);
		
		//Button btnAddNewQuest = (Button) rootView.findViewById(R.id.btnAddNewQuest);
		//btnAddNewQuest.setOnClickListener((OnClickListener)getActivity());
		
		registerForContextMenu(listView);
		return rootView;
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listAdapter = new QuestListAdapter(getActivity(), qHelper.getQuestsForSkill(skill));
		//listAdapter.setTaskCompletionListener(this);
		listView.setAdapter(listAdapter);
		listView.setOnChildClickListener(this);
		
	}

	@Override
	public void onAttach(Activity activity) {
		int index;
		
		super.onAttach(activity);
		
		index = getArguments().getInt(ARG_SKILL_TREE);
		((QuestListActivity) activity).onSectionAttached(index);		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.questListView) {
		    ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo)menuInfo;
		    
		    menu.setHeaderTitle(listAdapter.getGroup(ExpandableListView.getPackedPositionGroup(info.packedPosition)).toString());
		    String[] menuItems = getResources().getStringArray(R.array.task_list_menu);
		    for (int i = 0; i<menuItems.length; i++) {
		      menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		  }
	}

	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo)item.getMenuInfo();
		  int menuItemIndex = item.getItemId();
		  
		  
		  String[] menuItems = getResources().getStringArray(R.array.task_list_menu);
		  String menuItemName = menuItems[menuItemIndex];
		  //String listItemName = taskList.get(info.position);
		  
		  final Quest quest = (Quest)listAdapter.getGroup(ExpandableListView.getPackedPositionGroup(info.packedPosition));
		  
		  switch (menuItemIndex){
		  case 0: //Edit Option
//			  	editIndex = info.position;
//				task = quest.getTasks().get(info.position);
//				taskCreationListener.editTask(task);
			  	Intent intent = new Intent(getActivity(), AddQuestActivity.class);
			  	intent.putExtra("quest_id", quest.getId());
				startActivity(intent);
			  break;
		  case 1: //Delete Option
			  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			    builder.setTitle("Confirm");
			    builder.setMessage("Are you sure you want to delete this Quest?");

			    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing but close the dialog
			        	QuestHelper qhelper = new QuestHelper(getActivity());
						if (qhelper.deleteQuest(quest) > 0){
							listAdapter.removeItem(quest);
						}
						
						dialog.dismiss();
			        }

			    });

			    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing
			            dialog.dismiss();
			        }
			    });

			    AlertDialog alert = builder.create();
			    alert.show();
//			  quest.getTasks().remove(info.position);
//			  taskList.remove(info.position);
//			  taskListAdapter.notifyDataSetChanged();
			  break;
		  }
		  return true;
	}
	
//	public void completeTask(int position){
//		//Quest quest;
//		Task task;
//		int groupPosition = 0;
//		int childPosition = 0;
//		
//		// 1. Flat list position  ->  Packed position
//		long packedPosition = listView.getExpandableListPosition(position);
//		// 2. Unpack packed position type
//		int positionType = ExpandableListView.getPackedPositionType(packedPosition);
//
//		  
//		// 3. Unpack position values based on positionType
//		//    if not PACKED_POSITION_TYPE_NULL there will at least be a groupPosition
//		if( positionType != ExpandableListView.PACKED_POSITION_TYPE_NULL ){
//		      groupPosition = listView.getPackedPositionGroup(packedPosition);
//		      Log.d(LOG,"Group Pos: " + groupPosition);
//			  
//		      if(positionType == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
//		          childPosition = listView.getPackedPositionChild(packedPosition);
//		          Log.d(LOG,"Child Pos : " + childPosition);
//		          //quest = (Quest)listAdapter.getGroup(groupPosition);
//		          task = (Task) listAdapter.getChild(groupPosition, childPosition);
//		          
//		          completeTask(task);
////		          Date date = new Date();
////					
////		          task.setCompleted_date(DateFormat.format("yyyy-MM-dd HH:mm", date).toString());
////		          qHelper.completeTask(quest, task);		          
////		          //listView.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.task_completed));
////		          
////		          userChangeListener.userChanged(new UserHelper(getActivity()).getUserData());
////		          listAdapter.notifyDataSetChanged();
//		      }
////		      else{		  		
////		    	  //quest = (Quest)listAdapter.getGroup(groupPosition);
////		    	  //Date date = new Date();
////					
////		          //quest.setCompleted_date(DateFormat.format("yyyy-MM-dd HH:mm", date).toString());
////		      }
//
//		  }else{
//		      Log.d(LOG, "positionType was NULL - header/footer?");
//		  }
//	}
//	
//	public void uncompleteTask(int position){
//		//Quest quest;
//		Task task;
//		int groupPosition = 0;
//		int childPosition = 0;
//		
//		// 1. Flat list position  ->  Packed position
//		long packedPosition = listView.getExpandableListPosition(position);
//		// 2. Unpack packed position type
//		int positionType = ExpandableListView.getPackedPositionType(packedPosition);
//
//		  
//		// 3. Unpack position values based on positionType
//		//    if not PACKED_POSITION_TYPE_NULL there will at least be a groupPosition
//		if( positionType != ExpandableListView.PACKED_POSITION_TYPE_NULL ){
//		      groupPosition = listView.getPackedPositionGroup(packedPosition);
//		      Log.d(LOG,"Group Pos: " + groupPosition);
//			  
//		      if(positionType == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
//		          childPosition = listView.getPackedPositionChild(packedPosition);
//		          Log.d(LOG,"Child Pos : " + childPosition);
//		          //quest = (Quest)listAdapter.getGroup(groupPosition);
//		          task = (Task) listAdapter.getChild(groupPosition, childPosition);
//		          
//		          uncompleteTask(task);
////		          task.setCompleted_date(null);
////		          qHelper.uncompleteTask(quest, task);	
////		          
////		          userChangeListener.userChanged(new UserHelper(getActivity()).getUserData());
////		          listAdapter.notifyDataSetChanged();
//		      }
////		      else{		  		
////		    	  //quest = (Quest)listAdapter.getGroup(groupPosition);
////		    	  //Date date = new Date();
////					
////		          //quest.setCompleted_date(DateFormat.format("yyyy-MM-dd HH:mm", date).toString());
////		      }
//
//		  }else{
//		      Log.d(LOG, "positionType was NULL - header/footer?");
//		  }
//	}
	
	public void setUserChangeListener(UserChangeListener listener){
		userChangeListener = listener;
	}

	@Override
	public void completeTask(Task task) {
		// TODO Auto-generated method stub
		Quest quest;
		Date date = new Date();
		
		quest = qHelper.getQuest(task.getQuest_id());
		
        task.setCompleted_date(DateFormat.format("yyyy-MM-dd hh:mm", date).toString());
        qHelper.completeTask(quest, task);		          
        //listView.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.task_completed));
        
        userChangeListener.userChanged(new UserHelper(getActivity()).getUserData());
        listAdapter.notifyDataSetChanged();
	}

	@Override
	public void uncompleteTask(Task task) {
		// TODO Auto-generated method stub
		Quest quest;
		
		quest = qHelper.getQuest(task.getQuest_id());
		
		task.setCompleted_date(null);
        qHelper.uncompleteTask(quest, task);	
        
        userChangeListener.userChanged(new UserHelper(getActivity()).getUserData());
        listAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		Log.d(LOG,"G " + groupPosition + ", C " + childPosition);
		Task t = (Task) listAdapter.getChild(groupPosition, childPosition);
		
		if (t.getCompleted_date() == null){
			completeTask(t);
		}else{
			uncompleteTask(t);
		}
		
		return true;
	}
}
