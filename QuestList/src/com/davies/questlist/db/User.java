package com.davies.questlist.db;

import android.util.Log;

import com.davies.questlist.logic.LevelValidator;

public class User {
	long id;
	String name;
	int xp;
	int fitness_xp;
	int learning_xp;
	int culture_xp;
	int social_xp;
	int personal_xp;
	int level;
	
	public User(String name) {
		//New user starts with 0 XP and level of 1
		this(name,0,1);
	}

	
	public User(String name, int xp, int level) {
		super();
		this.name = name;
		this.xp = xp;
		this.level = level;
		this.fitness_xp = 0;
		this.learning_xp = 0;
		this.culture_xp = 0;
		this.social_xp = 0;
		this.personal_xp = 0;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void addXp(int xp){
		this.xp += xp;
		LevelValidator.validateLevel(this);
	}
	
	public void removeXp(int xp){
		//int orig_xp = this.xp;
		this.xp -= xp;
		if (this.xp < 0) {
			this.xp = 0;
		}
		LevelValidator.validateLevel(this);
		
	}
	
	public void addSkillXp(SkillTree skill, int xp){
		switch (skill){
		case FITNESS:
			fitness_xp += xp;
			break;
		case LEARNING:
			learning_xp += xp;
			break;
		case CULTURE:
			culture_xp += xp;
			break;
		case SOCIAL:
			social_xp += xp;
			break;
		case PERSONAL:
			personal_xp += xp;
			break;
		}
	}
	
	public void removeSkillXp(SkillTree skill, int xp){
		switch (skill){
		case FITNESS:
			fitness_xp -= xp;
			if (fitness_xp < 0){
				fitness_xp = 0;
			}
			break;
		case LEARNING:
			learning_xp -= xp;
			if (learning_xp < 0){
				learning_xp = 0;
			}
			break;
		case CULTURE:
			culture_xp -= xp;
			if (culture_xp < 0){
				culture_xp = 0;
			}
			break;
		case SOCIAL:
			social_xp -= xp;
			if (social_xp < 0){
				social_xp = 0;
			}
			break;
		case PERSONAL:
			personal_xp -= xp;
			if (personal_xp < 0){
				personal_xp = 0;
			}
			break;
		}
		
	}
	
	public void incrementLevel(int xp){
		this.level++;
		Log.d("User", "Incremented Level " + this.level);
		//this.xp -= xp; 
	}
	
	public void decrementLevel(){
		if (this.level > 1){
			this.level--;
		}
		Log.d("User", "Decremented Level " + this.level);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public int getFitness_xp() {
		return fitness_xp;
	}


	public void setFitness_xp(int fitness_xp) {
		this.fitness_xp = fitness_xp;
	}


	public int getLearning_xp() {
		return learning_xp;
	}


	public void setLearning_xp(int learning_xp) {
		this.learning_xp = learning_xp;
	}


	public int getCulture_xp() {
		return culture_xp;
	}


	public void setCulture_xp(int culture_xp) {
		this.culture_xp = culture_xp;
	}


	public int getSocial_xp() {
		return social_xp;
	}


	public void setSocial_xp(int social_xp) {
		this.social_xp = social_xp;
	}


	public int getPersonal_xp() {
		return personal_xp;
	}


	public void setPersonal_xp(int personal_xp) {
		this.personal_xp = personal_xp;
	}


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	@Override
	public String toString() {
		String s;
		
		s = name;
		
		return s;
	}
	
	public String toDebugString() {
		String s;
		
		s = "Name: " + name + ", XP: " + xp + ", Level: " + level + "\n"
				+ "Fit: " + fitness_xp + ", Int: " + learning_xp + ", Art: " + culture_xp
				+ ", Chr: " + social_xp + ", Per: " + personal_xp;
		
		return s;
	}
}
