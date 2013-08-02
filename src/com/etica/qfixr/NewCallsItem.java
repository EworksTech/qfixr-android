package com.etica.qfixr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etica.qfixr.R;
import com.etica.qfixr.NewCalls.IntentLauncher;
import com.turbomanage.httpclient.AsyncCallback;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.android.AndroidHttpClient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewCallsItem extends Activity {
	
	String callid = "";
	
	 protected void onCreate(Bundle savedInstanceState) {
		 	
		 	super.onCreate(savedInstanceState);
		 	
		 	overridePendingTransition(R.anim.slide_in,R.anim.hold);
			setContentView(R.layout.newcalls_item);
			
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			//actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle("Novos Chamados");
			
			
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
			   callid = extras.getString("callid");
			}
			
			//Toast.makeText(NewCallsItem.this, "Selected :"+callid, Toast.LENGTH_SHORT).show();
			
			final IntentLauncher launcher = new IntentLauncher();
			launcher.getNewCallsJson();
			
			Button atender = (Button) findViewById(R.id.btn_atender); 
			atender.setOnClickListener(new View.OnClickListener() {
			      @Override
			      public void onClick(View v) {
			    	  
			    	  launcher.requestNewCallsItem();
			 	       
			      }
			  });
	 }
	 
	 public void retunToNewCalls(){
		 
		 SharedPreferences prefs = getSharedPreferences("qfixrprefs",Context.MODE_PRIVATE);
    	 SharedPreferences.Editor editor = prefs.edit(); 
    	 editor.putString("newcallsReload", "reload"); 
    	 editor.commit(); 
    	  
    	  Intent upIntent = new Intent(NewCallsItem.this, NewCalls.class);
    	  			    	  
          if (NavUtils.shouldUpRecreateTask(NewCallsItem.this, upIntent)) {
              NavUtils.navigateUpTo(NewCallsItem.this, upIntent);
              finish();
          } else {
              finish();
          }
          overridePendingTransition(R.anim.hold,R.anim.slide_out);
	 }
	 
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		        case android.R.id.home:
		            Intent upIntent = new Intent(this, NewCalls.class);
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
		 
		 Intent upIntent = new Intent(this, NewCalls.class);
         if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
             NavUtils.navigateUpTo(this, upIntent);
             finish();
         } else {
             finish();
         }
         overridePendingTransition(R.anim.hold,R.anim.slide_out);
	        super.onBackPressed();   


	  }
	 
	 
	 //=====================================================================================
	 private void newCallsItemData(JSONArray jarray) {
			
			
			TextView callidtext 	= (TextView) this.findViewById(R.id.callid);
			TextView servico 		= (TextView) this.findViewById(R.id.servico);
			TextView dispositivo 	= (TextView) this.findViewById(R.id.dispositivo);
			TextView endereco 		= (TextView) this.findViewById(R.id.endereco);
			TextView descricao 		= (TextView) this.findViewById(R.id.descricao);
			TextView cliente 		= (TextView) this.findViewById(R.id.cliente);
			TextView contato 		= (TextView) this.findViewById(R.id.contato);
			TextView agenda 		= (TextView) this.findViewById(R.id.agenda);
			
			try {
				
				JSONObject jobjchamado = (JSONObject) jarray.getJSONObject(0);
				JSONObject jobjcliente = (JSONObject) jarray.getJSONObject(1);
				
				callidtext.setText(jobjchamado.get("id").toString());
				endereco.setText(
									jobjcliente.get("rua").toString()+","+
								    jobjcliente.get("numero").toString()+","+
								    jobjcliente.get("bairro").toString()
								);
				
				cliente.setText(jobjcliente.get("nome").toString());
				contato.setText(jobjcliente.get("telefone").toString());
				servico.setText(jobjchamado.get("id_servico").toString());
				dispositivo.setText(jobjchamado.get("id_dispositivo").toString());
				descricao.setText(jobjchamado.get("descricao").toString());
				
				if(jobjchamado.get("agendar").toString() == "null"){
					agenda.setText("");
				} else {
					agenda.setText(jobjchamado.get("agendar").toString());
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	 public class IntentLauncher {
		  
			
		  public void getNewCallsJson(){
			  
			  	
			    AndroidHttpClient httpClient = new AndroidHttpClient("http://192.168.0.4:8080/qfixr-app/index.php");
			    httpClient.setConnectionTimeout(5000);
			    
		        ParameterMap params = httpClient.newParams()
		                .add("appsender", "1");		        		
		        httpClient.post("/chamados/item/"+callid, params, new AsyncCallback() {
		        
		        	@Override
		            public void onError(Exception e) {
		            	
		               e.printStackTrace();
		               
				
						AlertDialog.Builder builder = new AlertDialog.Builder(NewCallsItem.this);
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
							NewCallsItem.this.newCallsItemData(jarray);
							
							
						}catch(Exception e){						
							e.printStackTrace();
						}
						
						
					}
		        });
		        
		        
			  
		  }
		  
		  
		  public void requestNewCallsItem(){
			  
			  	
			    AndroidHttpClient httpClient = new AndroidHttpClient("http://192.168.0.4:8080/qfixr-app/index.php");
			    httpClient.setConnectionTimeout(5000);
			    
		        ParameterMap params = httpClient.newParams()
		                .add("appsender", "1")
		                .add("id_tecnico","1");
		        httpClient.post("/chamados/atender/"+callid, params, new AsyncCallback() {
		        
		        	@Override
		            public void onError(Exception e) {
		            	
		               e.printStackTrace();
		               
				
						AlertDialog.Builder builder = new AlertDialog.Builder(NewCallsItem.this);
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
							
							//JSONArray jarray = new JSONArray(httpResponse.getBodyAsString());
							NewCallsItem.this.retunToNewCalls();
							
							
						}catch(Exception e){						
							e.printStackTrace();
						}
						
						
					}
		        });
		        
		        
			  
		  }
		  
	}
	

}
