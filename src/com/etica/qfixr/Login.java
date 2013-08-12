package com.etica.qfixr;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.etica.qfixr.MyCallsItemAddProgress.IntentLauncher;
import com.turbomanage.httpclient.AsyncCallback;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.android.AndroidHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {
	
	ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		/*
		ProgressDialog dialog = ProgressDialog.show(Login.this, "", 
                "Verificando. Aguarde...", true);
				dialog.show();
		*/
		
		final EditText usuario 	= (EditText) findViewById(R.id.user);
		final EditText senha 	= (EditText) findViewById(R.id.password);
		
		Button enviar = (Button) findViewById(R.id.btn_login);
		enviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				dialog = ProgressDialog.show(Login.this, "", 
		                "Verificando. Aguarde...", true);
						dialog.show();
				
				IntentLauncher launcher = new IntentLauncher();
				launcher.verifyUser(usuario.getText().toString(),senha.getText().toString());
				
			}
		});
	}

	//======================================================================================================
		public class IntentLauncher {


			public void verifyUser(String user, String password) {

				AndroidHttpClient httpClient = new AndroidHttpClient(
						getString(R.string.app_server));
				httpClient.setConnectionTimeout(5000);
				
				ParameterMap params = httpClient.newParams()
						.add("appsender", "1")
					    .add("usuario", user)
					    .add("senha", password);
				httpClient.post("/chamados/login", params,	new AsyncCallback() {

							@Override
							public void onError(Exception e) {

								e.printStackTrace();

								AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
								builder.setMessage(
										"Um erro acorreu. Por favor tente novamente.")
										.setCancelable(false)
										.setPositiveButton(
												"OK",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														//
													}
												});

								AlertDialog alert = builder.create();
								
								alert.getWindow().getAttributes();
							    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
							    textView.setTextSize(11);
							    alert.show();
							}

							@Override
							public void onComplete(HttpResponse httpResponse) {

								try {
									
									String data = httpResponse.getBodyAsString();
									Object json = new JSONTokener(data).nextValue();
									if (json instanceof JSONObject){
										
										dialog.hide();
										JSONObject jobj = new JSONObject(httpResponse
												.getBodyAsString());
										boolean status = (jobj.get("status").toString()
												.equals("error")) ? true : false;

										if (status) {
											AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
											builder.setMessage(
													"Um erro acorreu. Por favor tente novamente.")
													.setCancelable(false)
													.setPositiveButton(
															"OK",
															new DialogInterface.OnClickListener() {
																public void onClick(
																		DialogInterface dialog,
																		int id) {
																	//
																}
															});

											AlertDialog alert = builder.create();
											alert.show();
										}
										
									} else if (json instanceof JSONArray){
										
										dialog.hide();
										JSONArray jarray = new JSONArray(httpResponse.getBodyAsString());
										JSONObject jobj = (JSONObject) jarray.getJSONObject(0);
										
										SharedPreferences prefs = getSharedPreferences("useridentity",Context.MODE_PRIVATE);
										SharedPreferences.Editor editor = prefs.edit(); 
										editor.putString("userid", jobj.get("id").toString()); 
										editor.putString("username", jobj.get("nome").toString()); 
										
										editor.commit(); 
										
										Intent intent = new Intent(Login.this, MainActivity.class);     
										Login.this.startActivity(intent);   
									    Login.this.finish();
										
									}
									

								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						});

			}
		}
}
