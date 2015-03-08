package com.davies.questlist.logic;

import com.davies.questlist.db.User;

public class LevelValidator {
	
	public static void validateLevel(User user){
		int next_level_xp;
		int current_level_xp;
		
		next_level_xp = getXpForLevel(user.getLevel() + 1);
		
		if (user.getXp() >= next_level_xp){
			user.incrementLevel(next_level_xp);
		}
		
		current_level_xp = getXpForLevel(user.getLevel());
		
		if (user.getXp() < current_level_xp){
			user.decrementLevel();
		}
	}
	
	public static int getXpForLevel(int level){
		int retval = 0;
		double xp;
		
		//relook at this formula if levelling up is too easy
		xp = (0.04 * (level ^ 3)) + (0.8 * (level ^ 2)) + (2 * level);
		retval = (int) xp;
		
		return retval;
	}
}
