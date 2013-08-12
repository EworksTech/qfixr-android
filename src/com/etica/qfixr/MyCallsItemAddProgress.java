package com.etica.qfixr;

import org.json.JSONArray;
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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyCallsItemAddProgress extends Activity {

	String callid = "";
	String userid;
	

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// overridePendingTransition(R.anim.slide_in, R.anim.hold);
		setContentView(R.layout.mycalls_add_progress);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("Andamento");
		
		SharedPreferences prefs = getSharedPreferences("useridentity",Context.MODE_PRIVATE);   
	    userid = prefs.getString("userid","empty");
		
		
		final Bundle extras = getIntent().getExtras();
		
		if (extras != null) {
			callid = extras.getString("callid");
		}
		
		
		final EditText msgtext = (EditText) findViewById(R.id.editEnviar);
		msgtext.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                getSystemService(MyCallsItemAddProgress.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(msgtext, 0);
            }
        },200);
		
		Button enviar = (Button) findViewById(R.id.btn_enviar);
		enviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				IntentLauncher launcher = new IntentLauncher();
				launcher.setProgress();
				
			}
		});
		

	}

	// ===========================================================
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent upIntent = new Intent(this, MyCallsItemProgress.class);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				NavUtils.navigateUpTo(this, upIntent);
				finish();
			} else {
				finish();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {

		Intent upIntent = new Intent(this, MyCallsItemProgress.class);
		if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
			NavUtils.navigateUpTo(this, upIntent);
			finish();
		} else {
			finish();
		}
		super.onBackPressed();

	}
	
	public void retunToProgress() {

		SharedPreferences prefs = getSharedPreferences("qfixrprefs",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("mycallsProgressReload", "reload");
		editor.commit();

		Intent upIntent = new Intent(MyCallsItemAddProgress.this, MyCallsItemProgress.class);

		if (NavUtils.shouldUpRecreateTask(MyCallsItemAddProgress.this, upIntent)) {
			NavUtils.navigateUpTo(MyCallsItemAddProgress.this, upIntent);
			finish();
		} else {
			finish();
		}
		
		
	}
	//======================================================================================================
	public class IntentLauncher {


		public void setProgress() {

			AndroidHttpClient httpClient = new AndroidHttpClient(
					getString(R.string.app_server));
			httpClient.setConnectionTimeout(5000);
			
			//Text to Send
			EditText descricao = (EditText) findViewById(R.id.editEnviar);
			
			ParameterMap params = httpClient.newParams()
					.add("appsender", "1")
				    .add("id_chamado", callid)
				    .add("id_tecnico", userid)
				    .add("descricao", descricao.getText().toString());
			httpClient.post("/chamados/progresso", params,	new AsyncCallback() {

						@Override
						public void onError(Exception e) {

							e.printStackTrace();

							AlertDialog.Builder builder = new AlertDialog.Builder(MyCallsItemAddProgress.this);
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

						@Override
						public void onComplete(HttpResponse httpResponse) {

							try {

								JSONObject jobj = new JSONObject(httpResponse
										.getBodyAsString());
								boolean status = (jobj.get("status").toString()
										.equals("true")) ? true : false;

								if (status) {
									
									//Log.e("voltar","chamou");
									MyCallsItemAddProgress.this.retunToProgress();

								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		}
	}

}
