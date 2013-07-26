package com.etica.qfixr;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class MyAccount extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowTitleEnabled(false);
		
		Button btn_conta= (Button) findViewById(R.id.btn_conta);
		btn_conta.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  
		    	  Intent intent = new Intent(MyAccount.this, MyAccountEdit.class);
		    	  MyAccount.this.startActivity(intent);   
		    	  //MainActivity.this.finish();
		      }
		  });
		
		Button btn_certificacao= (Button) findViewById(R.id.btn_certificacao);
		btn_certificacao.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  
		    	  Intent intent = new Intent(MyAccount.this, MyCertifications.class);
		    	  MyAccount.this.startActivity(intent);   
		    	  //MainActivity.this.finish();
		      }
		  });
		
		Button btn_notificacaoo= (Button) findViewById(R.id.btn_notificacao);
		btn_notificacaoo.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  
		    	  Intent intent = new Intent(MyAccount.this, MyNotifications.class);
		    	  MyAccount.this.startActivity(intent);   
		    	  //MainActivity.this.finish();
		      }
		  });
		

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
