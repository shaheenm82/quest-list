package com.davies.questlist.db;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Quest implements Parcelable {
	public enum QuestType{
		PUBLIC, PERSONAL
	};
	
	long id;
	String created_date;
	String name;
	//String type;
	QuestType type;
	int xp;
	String completed_date;
	List<Task> tasks;
	
	public Quest(){
		tasks = new ArrayList<>();
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
	public QuestType getType() {
		return type;
	}
	public void setType(QuestType type) {
		this.type = type;
	}
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	public String getCompleted_date() {
		return completed_date;
	}
	public void setCompleted_date(String completed_date) {
		this.completed_date = completed_date;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task){
		tasks.add(task);
	}
	
	public boolean isQuestCompleted(){
		boolean completed = true;
		
		for (Task task : tasks) {
			if (task.getCompleted_date() == null){
				completed = false;
				return completed;
			}
		}
		return completed;
	}
	
	public int getIncompleteTasks(){
		int incomplete = 0;
		
		for (Task task : tasks) {
			if (task.getCompleted_date() == null){
				incomplete ++;
			}
		}
		return incomplete;
	}
	
	public int getTotalXP(){
		int x = 0;
		
		for (Task task : tasks) {
			x =+ task.getXp();
		}
		
		x += xp;
		
		return x;
	}
	
	@Override
	public String toString() {
		String s;
		
		s = getName();
		return s;
	}
	
	public String toStringDebug() {
		String s;
		
		s = getId() + "," + getName() + "," + getXp() + "," + getType()
				+ getCreated_date() + "," + getCompleted_date();
		return s;
	}

    protected Quest(Parcel in) {
        id = in.readLong();
        created_date = in.readString();
        name = in.readString();
        type = (QuestType) in.readValue(QuestType.class.getClassLoader());
        xp = in.readInt();
        completed_date = in.readString();
        if (in.readByte() == 0x01) {
            tasks = new ArrayList<Task>();
            in.readList(tasks, Task.class.getClassLoader());
        } else {
            tasks = null;
        }
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
        dest.writeValue(type);
        dest.writeInt(xp);
        dest.writeString(completed_date);
        if (tasks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tasks);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Quest> CREATOR = new Parcelable.Creator<Quest>() {
        @Override
        public Quest createFromParcel(Parcel in) {
            return new Quest(in);
        }

        @Override
        public Quest[] newArray(int size) {
            return new Quest[size];
        }
    };
}