package com.davies.questlist.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.davies.questlist.R;

public class QuestListFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SKILL_TREE = "skill_tree";
	
	private TextView txtTest;
	//private OnClickListener clickListener;
	

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
		//int index;
		//String[] skilltree = getResources().getStringArray(R.array.skill_tree);
		
		View rootView = inflater.inflate(R.layout.fragment_quest_list,
				container, false);
		
		//index = getArguments().getInt(ARG_SKILL_TREE);
		
		Button btnAddNewQuest = (Button) rootView.findViewById(R.id.btnAddNewQuest);
		btnAddNewQuest.setOnClickListener((OnClickListener)getActivity());
		
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		int index;
		
		super.onAttach(activity);
		
		index = getArguments().getInt(ARG_SKILL_TREE);
		((QuestListActivity) activity).onSectionAttached(index);
		
		//clickListener = (OnClickListener)activity;
	}
}
