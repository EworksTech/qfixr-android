package com.etica.qfixr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.etica.qfixr.R;
import com.turbomanage.httpclient.AsyncCallback;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.android.AndroidHttpClient;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewCalls extends Activity {
	
	ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcalls);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowTitleEnabled(false);
		
		dialog = ProgressDialog.show(NewCalls.this, "", 
                "Carregando...", true);
				dialog.show();
		
		IntentLauncher launcher = new IntentLauncher();
		launcher.getNewCallsJson();

	}
	
	
	
	
	@Override
	public void onResume() {
		
		super.onResume();		
		
        
		
		SharedPreferences prefs = getSharedPreferences("qfixrprefs",Context.MODE_PRIVATE);   
	    String newcallsReload = prefs.getString("newcallsReload","empty");
	     
	     if(newcallsReload != "empty"){
	    	 
	    	 dialog = ProgressDialog.show(NewCalls.this, "", 
	                 "Atualizando...", true);
	 				dialog.show();
	    	 
	    	 IntentLauncher launcher = new IntentLauncher();
	  		 launcher.getNewCallsJson();
	  		
	    	 Toast.makeText(NewCalls.this, "Você atendeu um chamado. Vá para 'Meus Chamados' para acompanha-lo.", Toast.LENGTH_LONG).show();
	     } 
	     
	     SharedPreferences preferences = getSharedPreferences("qfixrprefs", 0);
	     preferences.edit().clear().commit();

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
	
	private ArrayList<NewCallsObject> getListData(JSONArray jarray) {
		
		ArrayList<NewCallsObject> results = new ArrayList<NewCallsObject>();
	
		
		for(int i=0; i< jarray.length();i++){
			
			try {
				
				JSONObject jobj = (JSONObject) jarray.getJSONObject(i);
				NewCallsObject newsData = new NewCallsObject();
				
				newsData.setDefect(jobj.get("descricao").toString());
				newsData.setAddress(jobj.get("rua").toString()+","+jobj.get("numero").toString()+","+jobj.get("bairro").toString());
				newsData.setCallId(jobj.get("id").toString());
				results.add(newsData);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		

		return results;
		
	}
	
	public void showListViewWith(JSONArray jarray){
		
		final ArrayList<NewCallsObject> calls_details = getListData(jarray);
		final ListView lv1 = (ListView) findViewById(R.id.listViewCalls);
		lv1.setAdapter(new NewCallsListAdapter(this, calls_details));
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				
				
				//Toast.makeText(NewCalls.this, "Selected :"+calls_details.get(position).getCallId().toString(), Toast.LENGTH_LONG).show();
				
				String callid = calls_details.get(position).getCallId().toString();
				Intent intent = new Intent(NewCalls.this, NewCallsItem.class);
				intent.putExtra("callid",callid);
				NewCalls.this.startActivity(intent);
				
				
			}

		});
		dialog.hide();
		
	}
	
	public class IntentLauncher {
		  
		
		  public void getNewCallsJson(){
			  
			  	
			    AndroidHttpClient httpClient = new AndroidHttpClient(getString(R.string.app_server));
			    httpClient.setConnectionTimeout(5000);
			    
		        ParameterMap params = httpClient.newParams()
		                .add("appsender", "1");
		        httpClient.post("/chamados/json", params, new AsyncCallback() {
		        
		        	@Override
		            public void onError(Exception e) {
		            	
		               e.printStackTrace();
		               
				
						AlertDialog.Builder builder = new AlertDialog.Builder(NewCalls.this);
						builder.setMessage("Um erro acorreu. Por favor tente novamente.")
						       .setCancelable(false)
						       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	   		//
						           }
						       });
						
						AlertDialog alert = builder.create();
						alert.show();
		            }
		            
					@Override
					public void onComplete(HttpResponse httpResponse) {
						
						try{
							
							
							JSONArray jarray = new JSONArray(httpResponse.getBodyAsString());
							NewCalls.this.showListViewWith(jarray);
							
							
						}catch(Exception e){						
							e.printStackTrace();
						}
						
						
					}
		        });
		        
		        
			  
		  }
		  
	}
	
}
