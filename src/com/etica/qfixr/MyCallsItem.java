package com.etica.qfixr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class MyCallsItem extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
	 	
	 	super.onCreate(savedInstanceState);
	 	
	 	overridePendingTransition(R.anim.slide_in,R.anim.hold);
		setContentView(R.layout.mycalls_item);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("Meus Chamados");
		 
 }

 @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent upIntent = new Intent(this, MyCalls.class);
	            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
	                NavUtils.navigateUpTo(this, upIntent);
	                finish();
	            } else {
	                finish();
	            }
	            overridePendingTransition(R.anim.hold,R.anim.slide_out);
	            return true;
	        default: return super.onOptionsItemSelected(item);
	    }
	}
 
 @Override
   public void onBackPressed() {
	 
	 Intent upIntent = new Intent(this, MyCalls.class);
     if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
         NavUtils.navigateUpTo(this, upIntent);
         finish();
     } else {
         finish();
     }
     overridePendingTransition(R.anim.hold,R.anim.slide_out);
        super.onBackPressed();   


  }

}
