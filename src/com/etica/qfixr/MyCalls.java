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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MyCalls extends Activity {

	TabHost tabHost;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycalls);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowTitleEnabled(false);
		
		
		tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
      
        TabSpec spec1=tabHost.newTabSpec("tab 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Em Andamento");
      
      
        TabSpec spec2=tabHost.newTabSpec("tab 2");
        spec2.setIndicator("Concluídos");
        spec2.setContent(R.id.tab2);
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
		
		
		ArrayList<NewCallsObject> image_details = getListData();
		final ListView lv1 = (ListView) findViewById(R.id.listViewMyCalls);
		lv1.setAdapter(new NewCallsListAdapter(this, image_details));
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				
				Intent intent = new Intent(MyCalls.this, MyCallsItem.class);
				MyCalls.this.startActivity(intent);
				
			}

		});
		
		
		final ListView lv2 = (ListView) findViewById(R.id.listViewMyCallsEnded);
		lv2.setAdapter(new NewCallsListAdapter(this, image_details));
		lv2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				
				//Object o = lv1.getItemAtPosition(position);
				//NewsItem newsData = (NewsItem) o;
				//Toast.makeText(MainActivity.this, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();
				//Intent intent = new Intent(MyCalls.this, NewCallsItem.class);
				//MyCalls.this.startActivity(intent);
				
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
	
	private ArrayList<NewCallsObject> getListData() {
		
		ArrayList<NewCallsObject> results = new ArrayList<NewCallsObject>();
		
		NewCallsObject newsData = new NewCallsObject();
		
		newsData.setDefect("Computador nao liga");
		newsData.setAddress("Joaquim Castro, 520, Parque São Francisco");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Sem acesso a internet");
		newsData.setAddress("Rui Barbosa, 120, Centro");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		
		
		return results;
	}
	
}