package com.etica.qfixr;

import java.util.ArrayList;

import com.etica.qfixr.R;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewCalls extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcalls);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowTitleEnabled(false);
		
		
		ArrayList<NewCallsObject> image_details = getListData();
		final ListView lv1 = (ListView) findViewById(R.id.listViewCalls);
		lv1.setAdapter(new NewCallsListAdapter(this, image_details));
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				
				//Object o = lv1.getItemAtPosition(position);
				//NewsItem newsData = (NewsItem) o;
				//Toast.makeText(MainActivity.this, "Selected :" + " " + newsData, Toast.LENGTH_LONG).show();
				Intent intent = new Intent(NewCalls.this, NewCallsItem.class);
				NewCalls.this.startActivity(intent);
				
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
		
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);

		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
		newsData.setCallId("100");
		results.add(newsData);
		
		newsData = new NewCallsObject();
		newsData.setDefect("Impressora enroscando papel");
		newsData.setAddress("Filadelfo Santos Reis, 293, Vila dos Comerciarios I");
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
