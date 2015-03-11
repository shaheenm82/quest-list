package com.davies.questlist.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.davies.questlist.R;
import com.davies.questlist.logic.LevelValidator;
 
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {
 
      Context context;
      List<DrawerItem> drawerItemList;
      int layoutResID;
 
      public DrawerAdapter(Context context, int layoutResourceID,
                  List<DrawerItem> listItems) {
            super(context, layoutResourceID, listItems);
            this.context = context;
            this.drawerItemList = listItems;
            this.layoutResID = layoutResourceID;
 
      }
 
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
    	    DrawerItemHolder drawerHolder;
            View view = convertView;
 
            if (view == null) {
                  LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                  drawerHolder = new DrawerItemHolder();
 
                  view = inflater.inflate(layoutResID, parent, false);
                  drawerHolder.ItemName = (TextView) view
                              .findViewById( R.id.drawer_itemName);
                  drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);
 
                  drawerHolder.title = (TextView) view
                          .findViewById( R.id.drawerTitle);
                                    
                  drawerHolder.userName = (TextView) view
                          .findViewById( R.id.txtUsername);
                  drawerHolder.userLevel = (TextView) view
                          .findViewById( R.id.txtUserLevel);
                  drawerHolder.userExperience = (ProgressBar) view
                          .findViewById( R.id.pbarXP);
                  
                  drawerHolder.headerLayout = (LinearLayout) view
                          .findViewById(R.id.headerLayout);
                  drawerHolder.itemLayout = (LinearLayout) view
                          .findViewById(R.id.itemLayout);
                  drawerHolder.userLayout = (LinearLayout) view
                          .findViewById(R.id.userLayout);
              
                  view.setTag(drawerHolder);
 
            } else {
                  drawerHolder = (DrawerItemHolder) view.getTag();
 
            }
 
            DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
 
            if (dItem.isUserItem()) {
                drawerHolder.headerLayout.setVisibility(LinearLayout.GONE);
                drawerHolder.itemLayout.setVisibility(LinearLayout.GONE);
                drawerHolder.userLayout.setVisibility(LinearLayout.VISIBLE);
                
                drawerHolder.userName.setText(dItem.getUser().getName());
                drawerHolder.userLevel.setText("Level " + dItem.getUser().getLevel());
                
                int prevLevelValue = LevelValidator.getXpForLevel(dItem.getUser().getLevel()-1);
                int nextLevelValue = LevelValidator.getXpForLevel(dItem.getUser().getLevel());
                
                drawerHolder.userExperience.setMax(nextLevelValue - prevLevelValue);
                drawerHolder.userExperience.setProgress(dItem.getUser().getXp()-prevLevelValue);                

          } else if (dItem.isTitle()) {
                drawerHolder.headerLayout.setVisibility(LinearLayout.VISIBLE);
                drawerHolder.itemLayout.setVisibility(LinearLayout.GONE);
                drawerHolder.userLayout.setVisibility(LinearLayout.GONE);
                
                drawerHolder.title.setText(dItem.getTitle());

          } else {

                drawerHolder.headerLayout.setVisibility(LinearLayout.GONE);
                drawerHolder.userLayout.setVisibility(LinearLayout.GONE);
                drawerHolder.itemLayout.setVisibility(LinearLayout.VISIBLE);

//                drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
//                            dItem.getImgResID()));
                drawerHolder.ItemName.setText(dItem.getItemName());

          }
 
            return view;
      }
 
      private static class DrawerItemHolder {
            TextView ItemName, userName, userLevel, title;
            ImageView icon;
            ProgressBar userExperience;
            LinearLayout headerLayout, itemLayout,userLayout; 
      }
}