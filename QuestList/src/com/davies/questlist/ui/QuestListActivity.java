package com.davies.questlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.davies.questlist.R;
import com.davies.questlist.db.DatabaseHelper;

public class QuestListActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks, OnClickListener {

	private static final String LOG = "QuestListActivity";
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	
	DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quest_list);
	
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();
		
		dbHelper = new DatabaseHelper(getApplicationContext());
		
		if (dbHelper.isNewDb()){
			//Place registration fragment here.
			Log.d(LOG,"New DB");
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							new NewUserFragment()).commit();
		}else{
			// Set up the drawer.
			Log.d(LOG,"Setting up Drawer");
			
			mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
					(DrawerLayout) findViewById(R.id.drawer_layout));					
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		Fragment fragment;
		
		if (position == 0){
			Log.d(LOG,"Showing User Summary");
			fragment = UserSummaryFragment.newInstance();
		}else{			
			fragment = QuestListFragment.newInstance(position-1);
			((QuestListFragment)fragment).setUserChangeListener(mNavigationDrawerFragment);			
		}
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						fragment).commit();
	}

	public void onSectionAttached(int number) {
		String[] skilltree = getResources().getStringArray(R.array.skill_tree);
		
		
		mTitle = skilltree[number];
		Log.d(LOG, mTitle.toString());
		
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.quest_list, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add_quest) {
			addNewQuest();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		switch (v.getId()){
////		case R.id.btnAddNewQuest:
////			//open activity for new quest
////			addNewQuest();
////			break;
//		}
		Log.i("QuestListActivity","Click Event");
	}

	private void addNewQuest(){
		Intent intent = new Intent(this, AddQuestActivity.class);
		startActivity(intent);
	}
}
