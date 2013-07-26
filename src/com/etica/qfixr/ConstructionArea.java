package com.etica.qfixr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class ConstructionArea extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.construction_area);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowTitleEnabled(false);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent upIntent = new Intent(this, MainActivity.class);
	            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
	                NavUtils.navigateUpTo(this, upIntent);
	                finish();
	            } else {
	                finish();
	            }
	            return true;
	        default: return super.onOptionsItemSelected(item);
	    }
	}
}
