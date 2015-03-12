package com.davies.questlist.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.davies.questlist.R;
import com.davies.questlist.db.User;
import com.davies.questlist.db.UserHelper;
import com.davies.questlist.logic.LevelValidator;

/**
 * A simple {@link Fragment} subclass. Use the
 * {@link UserSummaryFragment#newInstance} factory method to create an instance
 * of this fragment.
 * 
 */
public class UserSummaryFragment extends Fragment {
	TextView userName, userLevel;
	TextView fitLevel, intLevel, artLevel, chrLevel, perLevel;
	TextView fitXP, intXP, artXP, chrXP, perXP;
    ImageView icon;
    ProgressBar userExperience;
    ProgressBar fitExperience, intExperience, artExperience, chrExperience, perExperience;
    
    private static final String LOG = "UserSummaryFragment";
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment UserSummaryFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static UserSummaryFragment newInstance() {
		UserSummaryFragment fragment = new UserSummaryFragment();
		return fragment;
	}

	public UserSummaryFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		User user = new UserHelper(getActivity()).getUserData();
		
		View rootView = inflater.inflate(R.layout.fragment_user_summary, container, false);
		
		userName = (TextView) rootView
                .findViewById( R.id.txtUsername);
        userLevel = (TextView) rootView
                .findViewById( R.id.txtUserLevel);
        userExperience = (ProgressBar) rootView
                .findViewById( R.id.pbarXP);
        
        fitLevel = (TextView) rootView
                .findViewById( R.id.txtUserFitLevel);
        intLevel = (TextView) rootView
                .findViewById( R.id.txtUserIntLevel);
        artLevel = (TextView) rootView
                .findViewById( R.id.txtUserArtLevel);
        chrLevel = (TextView) rootView
                .findViewById( R.id.txtUserChrLevel);
        perLevel = (TextView) rootView
                .findViewById( R.id.txtUserPerLevel);
        
        fitExperience = (ProgressBar) rootView
                .findViewById( R.id.pbarFitXP);
        intExperience = (ProgressBar) rootView
                .findViewById( R.id.pbarIntXP);
        artExperience = (ProgressBar) rootView
                .findViewById( R.id.pbarArtXP);
        chrExperience = (ProgressBar) rootView
                .findViewById( R.id.pbarChrXP);
        perExperience = (ProgressBar) rootView
                .findViewById( R.id.pbarPerXP);
        
        fitXP = (TextView) rootView
                .findViewById( R.id.txtUserFitXp);
        intXP = (TextView) rootView
                .findViewById( R.id.txtUserIntXp);
        artXP = (TextView) rootView
                .findViewById( R.id.txtUserArtXp);
        chrXP = (TextView) rootView
                .findViewById( R.id.txtUserChrXp);
        perXP = (TextView) rootView
                .findViewById( R.id.txtUserPerXp);
        
        Log.d(LOG, "User: " + user.toDebugString());
		userName.setText(user.getName());
        userLevel.setText("Level " + user.getLevel());
        
        int prevLevelValue = LevelValidator.getXpForLevel(user.getLevel()-1);
        int nextLevelValue = LevelValidator.getXpForLevel(user.getLevel());
        
        userExperience.setMax(nextLevelValue - prevLevelValue);
        userExperience.setProgress(user.getXp()-prevLevelValue);
        
        int level = LevelValidator.getLevelForXp(user.getFitness_xp(), user.getLevel());
        
        prevLevelValue = LevelValidator.getXpForLevel(level-1);
        nextLevelValue = LevelValidator.getXpForLevel(level);
        
        Log.d(LOG, "Fit : P " + prevLevelValue + ", N " + nextLevelValue);
        
        fitLevel.setText( getResources().getString(R.string.xp_fit) + " Level: " + level);
        fitExperience.setMax(nextLevelValue - prevLevelValue);
        fitExperience.setProgress(user.getFitness_xp()-prevLevelValue);
        fitXP.setText(user.getFitness_xp()-prevLevelValue + " " + getResources().getString(R.string.quest_xp));
        
        level = LevelValidator.getLevelForXp(user.getLearning_xp(), user.getLevel());
        prevLevelValue = LevelValidator.getXpForLevel(level-1);
        nextLevelValue = LevelValidator.getXpForLevel(level);
        
        Log.d(LOG, "Int : P " + prevLevelValue + ", N " + nextLevelValue);
        
        intLevel.setText( getResources().getString(R.string.xp_int) + " Level: " + level);
        intExperience.setMax(nextLevelValue - prevLevelValue);
        intExperience.setProgress(user.getLearning_xp()-prevLevelValue);
        intXP.setText(user.getLearning_xp()-prevLevelValue + " " + getResources().getString(R.string.quest_xp));
        
        level = LevelValidator.getLevelForXp(user.getCulture_xp(), user.getLevel());
        prevLevelValue = LevelValidator.getXpForLevel(level-1);
        nextLevelValue = LevelValidator.getXpForLevel(level);
        
        Log.d(LOG, "Art : P " + prevLevelValue + ", N " + nextLevelValue);
        
        artLevel.setText( getResources().getString(R.string.xp_art) + " Level: " + level);
        artExperience.setMax(nextLevelValue - prevLevelValue);
        artExperience.setProgress(user.getCulture_xp()-prevLevelValue);
        artXP.setText(user.getCulture_xp()-prevLevelValue + " " + getResources().getString(R.string.quest_xp));
        
        level = LevelValidator.getLevelForXp(user.getSocial_xp(), user.getLevel());
        prevLevelValue = LevelValidator.getXpForLevel(level-1);
        nextLevelValue = LevelValidator.getXpForLevel(level);
        
        Log.d(LOG, "Chr : P " + prevLevelValue + ", N " + nextLevelValue);
        
        chrLevel.setText( getResources().getString(R.string.xp_chr) + " Level: " + level);
        chrExperience.setMax(nextLevelValue - prevLevelValue);
        chrExperience.setProgress(user.getSocial_xp()-prevLevelValue);
        chrXP.setText(user.getSocial_xp()-prevLevelValue + " " + getResources().getString(R.string.quest_xp));
        
        level = LevelValidator.getLevelForXp(user.getPersonal_xp(), user.getLevel());
        prevLevelValue = LevelValidator.getXpForLevel(level-1);
        nextLevelValue = LevelValidator.getXpForLevel(level);
        
        Log.d(LOG, "Per : P " + prevLevelValue + ", N " + nextLevelValue);
        
        perLevel.setText( getResources().getString(R.string.xp_per) + " Level: " + level);
        perExperience.setMax(nextLevelValue - prevLevelValue);
        perExperience.setProgress(user.getPersonal_xp()-prevLevelValue);
        perXP.setText(user.getPersonal_xp()-prevLevelValue + " " + getResources().getString(R.string.quest_xp));
        
		return rootView;
	}

}
