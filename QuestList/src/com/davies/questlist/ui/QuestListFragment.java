package com.davies.questlist.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.davies.questlist.R;
import com.davies.questlist.db.Quest;
import com.davies.questlist.db.QuestHelper;

public class QuestListFragment extends Fragment {
	private static final String LOG = "QuestListFragment";
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SKILL_TREE = "skill_tree";
	
	private ExpandableListView listView = null;
	private QuestListAdapter listAdapter = null;
	private QuestHelper qHelper = null;
	private String skill;
	
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
		
		listView.setAdapter(listAdapter);
		
		Button btnAddNewQuest = (Button) rootView.findViewById(R.id.btnAddNewQuest);
		btnAddNewQuest.setOnClickListener((OnClickListener)getActivity());
		
		registerForContextMenu(listView);
		return rootView;
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listAdapter = new QuestListAdapter(getActivity(), qHelper.getQuestsForSkill(skill));
		listView.setAdapter(listAdapter);
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
}
