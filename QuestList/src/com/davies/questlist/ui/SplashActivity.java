package com.davies.questlist.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.davies.questlist.R;
import com.davies.questlist.R.id;
import com.davies.questlist.R.layout;

public class SplashActivity extends Activity {

	// Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		final Animation animation = new AlphaAnimation(0, 1); // Change alpha from fully visible to invisible
	    animation.setDuration(1000); // duration - half a second
	    animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
	    animation.setRepeatCount(2); // Repeat animation infinitely
	    animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
	    final ImageView image = (ImageView) findViewById(R.id.imgQuestList);
	    image.startAnimation(animation);
	    
		enterApplication();
	}

	private void enterApplication(){
		new Handler().postDelayed(new Runnable() {
		 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
            	Intent intent = new Intent(SplashActivity.this, QuestListActivity.class);
    			startActivity(intent);
        		
                // close this activity
                finish();
            }
		}, SPLASH_TIME_OUT);
	}
}
