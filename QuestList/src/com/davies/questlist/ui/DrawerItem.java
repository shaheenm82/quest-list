package com.davies.questlist.ui;

import com.davies.questlist.db.User;

public class DrawerItem {
	 
    String ItemName;
    String title;
    int imgResID;
    User user;

    public DrawerItem(User user	) {
        super();
        this.user = user;
    }
    
    public DrawerItem(String itemName, boolean isTitle) {
        super();
        
        if (isTitle){
        	this.title = itemName;
        }else{
        	ItemName = itemName;
        }
    }
    
    public DrawerItem(String itemName, int imgResID) {
          super();
          ItemName = itemName;
          this.imgResID = imgResID;
    }

    public String getItemName() {
          return ItemName;
    }
    public void setItemName(String itemName) {
          ItemName = itemName;
    }
    public int getImgResID() {
          return imgResID;
    }
    public void setImgResID(int imgResID) {
          this.imgResID = imgResID;
    }

    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isTitle(){
    	return (title != null);
    }
    
    public boolean isUserItem(){
    	return (user != null);
    }
}