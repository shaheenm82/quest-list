package com.davies.questlist.ui;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.davies.questlist.R;
import com.davies.questlist.db.User;
import com.davies.questlist.logic.LevelValidator;
 
public class UserAdapter extends ArrayAdapter<User>{
 
      public UserAdapter(Context context, int layoutResourceID,
                  int textViewResourceId, List<User> spinnerDataList) {
            super(context, layoutResourceID, textViewResourceId, spinnerDataList);
 
            this.context=context;
            this.layoutResID=layoutResourceID;
            this.spinnerData=spinnerDataList;
 
      }
 
      Context context;
      int layoutResID;
      List<User> spinnerData;
 
      public UserAdapter(Context context, int layoutResourceID,
                  List<User> spinnerDataList) {
            super(context, layoutResourceID, spinnerDataList);
 
            this.context=context;
            this.layoutResID=layoutResourceID;
            this.spinnerData=spinnerDataList;
 
      }
 
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
    	  View row=convertView;
          SpinnerHolder holder;
          int prevLevelValue;
          int nextLevelValue;
          
          if(row==null)
          {
                LayoutInflater inflater=((Activity)context).getLayoutInflater();

                row=inflater.inflate(layoutResID, parent, false);
                holder=new SpinnerHolder();

                holder.userImage=(ImageView)row.findViewById(R.id.imgUser);
                holder.name=(TextView)row.findViewById(R.id.txtUsername);
                holder.level=(TextView)row.findViewById(R.id.txtUserLevel);
                holder.experience = (ProgressBar)row.findViewById(R.id.pbarXP);

                row.setTag(holder);
          }
          else
          {
                holder=(SpinnerHolder)row.getTag();

          }

          User user=spinnerData.get(position);

          //holder.userImage.setImageDrawable(row.getResources().getDrawable(spinnerItem.getDrawableResID()));
          holder.name.setText(user.getName());
          holder.level.setText(user.getLevel());
          prevLevelValue = LevelValidator.getXpForLevel(user.getLevel()-1);
          nextLevelValue = LevelValidator.getXpForLevel(user.getLevel());
          
          holder.experience.setMax(nextLevelValue - prevLevelValue);
          holder.experience.setProgress(user.getLevel()-prevLevelValue);
          
          return row;
      }
 
      private static class SpinnerHolder
      {
            ImageView userImage;
            TextView  name,level;
            ProgressBar experience;
 
      }
 
}