package com.davies.questlist.db;

import com.davies.questlist.logic.LevelValidator;

public class User {
	long id;
	String name;
	int xp;
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
		LevelValidator.validateLevel(this);
		
	}
	
	public void incrementLevel(int xp){
		this.level++;
		//this.xp -= xp; 
	}
	
	public void decrementLevel(){
		this.level--;
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

	public int getLevel() {
		return level;
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
		
		s = "Name: " + name + ", XP: " + xp + ", Level: " + level;
		
		return s;
	}
}
