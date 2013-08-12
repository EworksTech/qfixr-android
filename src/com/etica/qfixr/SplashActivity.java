package com.etica.qfixr;

import com.etica.qfixr.R;

import android.app.Activity;
import android.content.Context;//para testes do shared prefs
import android.content.Intent;
import android.content.SharedPreferences;//para testes do shared prefs
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {

 private static String TAG = SplashActivity.class.getName();
 private static long SLEEP_TIME = 3;    // Sleep for some time

 @Override
 protected void onCreate(Bundle savedInstanceState) {

 
  super.onCreate(savedInstanceState);

  
  setContentView(R.layout.splash);

  // Start timer and launch main activity
  IntentLauncher launcher = new IntentLauncher();
  launcher.start();
}

 private class IntentLauncher extends Thread {
  @Override
  /**
   * Sleep for some time and than start new activity.
   */
  public void run() {
     try {
        // Sleeping
        Thread.sleep(SLEEP_TIME*1000);
     } catch (Exception e) {
        Log.e(TAG, e.getMessage());
     }
     /*
     Intent intent = new Intent(SplashActivity.this, Login.class);
     SplashActivity.this.startActivity(intent);   
     SplashActivity.this.finish();
     */
     // Start main activity
     
     SharedPreferences prefs = getSharedPreferences("useridentity",Context.MODE_PRIVATE);   
     String userid = prefs.getString("userid","empty");
     
     if(userid == "empty"){
     	
    	 Intent intent = new Intent(SplashActivity.this, Login.class);
         SplashActivity.this.startActivity(intent);   
         SplashActivity.this.finish();
     	
     } else {
     	
    	 Intent intent = new Intent(SplashActivity.this, MainActivity.class);
         SplashActivity.this.startActivity(intent);   
         SplashActivity.this.finish();
         
     }
     
    
     
      
  }
}
}