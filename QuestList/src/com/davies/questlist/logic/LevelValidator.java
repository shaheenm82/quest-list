package com.davies.questlist.logic;

import android.util.Log;

import com.davies.questlist.db.User;

public class LevelValidator {
	
	public static void validateLevel(User user){
		int next_level_xp;
		int current_level_xp;
		
		next_level_xp = getXpForLevel(user.getLevel());
		current_level_xp = getXpForLevel(user.getLevel()-1);
		
		Log.d("LevelValidator", "1) U " + user.getXp() + ", L " + user.getLevel() + ", C " + current_level_xp + ", N " + next_level_xp);
		while (user.getXp() >= getXpForLevel(user.getLevel())){
			user.incrementLevel(0);
			next_level_xp = getXpForLevel(user.getLevel());
			Log.d("LevelValidator", "2) U " + user.getXp() + ", L " + user.getLevel() + ", C " + current_level_xp + ", N " + next_level_xp);
			return;
		}
		
		while (user.getXp() < current_level_xp){
			user.decrementLevel();
			current_level_xp = getXpForLevel(user.getLevel()-1);			
			Log.d("LevelValidator", "3) U " + user.getXp() + ", L " + user.getLevel() + ", C" + current_level_xp + ", N " + next_level_xp);
		}
				
		Log.d("LevelValidator", "4) U " + user.getXp() + ", L " + user.getLevel() + ", C" + current_level_xp + ", N " + next_level_xp);
	}
	
	public static int getXpForLevel(int level){
		int retval = 0;
		double xp;
		
		//relook at this formula if levelling up is too easy
		//xp = (0.04 * (level ^ 3)) + (0.8 * (level ^ 2)) + (2 * level);
		xp = (1 * Math.pow(level,3)) + (2 * Math.pow(level,2)) + (4 * level);
		retval = (int) xp;
		
		return retval;
	}
	
	public static int getLevelForXp(int xp, int level){
		int retval = 0;
		int max_xp;
		//double xp;
		
		//relook at this formula if levelling up is too easy
		//xp = (0.04 * (level ^ 3)) + (0.8 * (level ^ 2)) + (2 * level);
		//retval = (int) xp;
		for (int i = 1; i <= level; i ++){
			max_xp = getXpForLevel(i);
			if (xp < max_xp){
				return i;
			}
		}
		
		return retval;
	}
}
