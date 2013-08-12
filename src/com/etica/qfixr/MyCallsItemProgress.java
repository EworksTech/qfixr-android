package com.etica.qfixr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etica.qfixr.MyCallsItem.IntentLauncher;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public class MyCallsItemProgress extends Activity {
	
	String callid = "";	
	ListView myList;
	ProgressDialog dialog; 
 
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
	 
	   super.onCreate(savedInstanceState);
	 
	    overridePendingTransition(R.anim.slide_in,R.anim.hold);
	    setContentView(R.layout.mycalls_item_progress);
		
	    
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("Meu Chamado");
	   
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			
		   callid = extras.getString("callid");
		   
		   
		}
	   
		dialog = ProgressDialog.show(MyCallsItemProgress.this, "", 
                "Carregando...", true);
				dialog.show();
		
		IntentLauncher launcher = new IntentLauncher();
		launcher.getProgressJson();
	 
	}
    
    @Override
	public void onResume() {
		
		super.onResume();		
		
        
		
		SharedPreferences prefs = getSharedPreferences("qfixrprefs",Context.MODE_PRIVATE);   
	    String newcallsReload = prefs.getString("mycallsProgressReload","empty");
	     
	     if(newcallsReload != "empty"){
	    	
	    	 dialog = ProgressDialog.show(MyCallsItemProgress.this, "", 
	                 "Atualizando...", true);
	 				dialog.show();
	    	 
	    	IntentLauncher launcher = new IntentLauncher();
	 		launcher.getProgressJson();
	  		
	    	 
	     } 
	     
	     SharedPreferences preferences = getSharedPreferences("qfixrprefs", 0);
	     preferences.edit().clear().commit();

	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
    	
       
        Bundle extras = getIntent().getExtras();
        String origin = extras.getString("origin", "");
        if (extras != null) {  
		   if (origin.equals("")) {
			   MenuInflater inflater = getMenuInflater();
			   inflater.inflate(R.menu.progress_menu, menu);  			   
		   } 
        }
        
		return super.onCreateOptionsMenu(menu);
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
	            
	        case R.id.action_add_progress:
	        	
	        	 Intent addIntent = new Intent(this, MyCallsItemAddProgress.class);
	        	 addIntent.putExtra("callid", callid);
	        	 MyCallsItemProgress.this.startActivity(addIntent); 
	        	
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
 
 //=================================================================================================
 private List<Map<String, ?>> getListData(JSONArray jarray) {
		
	 	List<Map<String, ?>> results = new ArrayList<Map<String, ?>>();
	
		
		for(int i=0; i< jarray.length();i++){
			
			try {
				
				JSONObject jobj = (JSONObject) jarray.getJSONObject(i);
				results.add(createRow(jobj.get("descricao").toString(), jobj.get("criado_em").toString()));
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		

		return results;
		
	}
	
	public void showListViewWith(JSONArray jarray){
		
		
		myList = (ListView)findViewById(R.id.progressListView);
		myList.setSelector(android.R.color.transparent);
		
		List<Map<String, ?>> data = getListData(jarray);

	    String[] from = {"value1", "value2"};
	    int[] to = {R.id.text1, R.id.text2};

	    SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.custom_simple_listview, from, to);
	    myList.setAdapter(adapter);
	    
	    dialog.hide();
	}
	
	private Map<String, ?> createRow(String value1, String value2) {
	    Map<String, String> row = new HashMap<String, String>();
	    row.put("value1", value1);
	    row.put("value2", value2);
	    return row;
	}
 
 //=================================================================================================
 public class IntentLauncher {
	  
		
	  public void getProgressJson(){
		  
		  	
		    AndroidHttpClient httpClient = new AndroidHttpClient(getString(R.string.app_server));
		    httpClient.setConnectionTimeout(5000);
		    
	        ParameterMap params = httpClient.newParams()
	                .add("appsender", "1");
	        httpClient.post("/chamados/andamento/"+callid, params, new AsyncCallback() {
	        
	        	@Override
	            public void onError(Exception e) {
	            	
	               e.printStackTrace();
	               
			
					AlertDialog.Builder builder = new AlertDialog.Builder(MyCallsItemProgress.this);
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
						MyCallsItemProgress.this.showListViewWith(jarray);
						
						
					}catch(Exception e){						
						e.printStackTrace();
					}
					
					
				}
	        });
	        
	        
		  
	  }
	  
}
 
}