package com.davies.questlist.db;

public class Task {
	long id;
	String created_date;
	String name;
	String description;
	int fitness_xp;
	int learning_xp;
	int culture_xp;
	int social_xp;
	int personal_xp;
	String completed_date;
	long quest_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getCompleted_date() {
		return completed_date;
	}
	public void setCompleted_date(String completed_date) {
		this.completed_date = completed_date;
	}
	
	public long getQuest_id() {
		return quest_id;
	}
	public void setQuest_id(long quest_id) {
		this.quest_id = quest_id;
	}
	
	@Override
	public String toString() {
		String s;
		
		s = getName();
		return s;
	}
	
	public String toStringDebug() {
		String s;
		
		s = getId() + "," + getName() + "," + getDescription() + "," + getFitness_xp() + "," + getLearning_xp()
				+ "," + getCulture_xp() + "," + getSocial_xp() + "," + getPersonal_xp() + ","
				+ getCreated_date() + "," + getCompleted_date();
		return s;
	}
	
	
}
