package com.davies.questlist.db;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
	long id;
	String created_date;
	String name;
	String description;
	int xp;
	boolean fitness_xp;
	boolean learning_xp;
	boolean culture_xp;
	boolean social_xp;
	boolean personal_xp;
	String completed_date;
	long quest_id;
	
	public Task(){
		id = 0;
	}
	
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

	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	public boolean isFitness_xp() {
		return fitness_xp;
	}
	public void setFitness_xp(boolean fitness_xp) {
		this.fitness_xp = fitness_xp;
	}
	public boolean isLearning_xp() {
		return learning_xp;
	}
	public void setLearning_xp(boolean learning_xp) {
		this.learning_xp = learning_xp;
	}
	public boolean isCulture_xp() {
		return culture_xp;
	}
	public void setCulture_xp(boolean culture_xp) {
		this.culture_xp = culture_xp;
	}
	public boolean isSocial_xp() {
		return social_xp;
	}
	public void setSocial_xp(boolean social_xp) {
		this.social_xp = social_xp;
	}
	public boolean isPersonal_xp() {
		return personal_xp;
	}
	public void setPersonal_xp(boolean personal_xp) {
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
		
		s = getId() + "," + getName() + "," + getDescription() + "," + isFitness_xp() + "," + isLearning_xp()
				+ "," + isCulture_xp() + "," + isSocial_xp() + "," + isPersonal_xp() + "," + getXp() + ","
				+ getCreated_date() + "," + getCompleted_date();
		return s;
	}
	
	

    protected Task(Parcel in) {
        id = in.readLong();
        created_date = in.readString();
        name = in.readString();
        description = in.readString();
        xp = in.readInt();
        fitness_xp = in.readByte() != 0x00;
        learning_xp = in.readByte() != 0x00;
        culture_xp = in.readByte() != 0x00;
        social_xp = in.readByte() != 0x00;
        personal_xp = in.readByte() != 0x00;
        completed_date = in.readString();
        quest_id = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(created_date);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(xp);
        dest.writeByte((byte) (fitness_xp ? 0x01 : 0x00));
        dest.writeByte((byte) (learning_xp ? 0x01 : 0x00));
        dest.writeByte((byte) (culture_xp ? 0x01 : 0x00));
        dest.writeByte((byte) (social_xp ? 0x01 : 0x00));
        dest.writeByte((byte) (personal_xp ? 0x01 : 0x00));
        dest.writeString(completed_date);
        dest.writeLong(quest_id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}