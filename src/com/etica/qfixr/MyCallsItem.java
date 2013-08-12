package com.etica.qfixr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etica.qfixr.NewCallsItem.IntentLauncher;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyCallsItem extends Activity {

	String callid = "";
	String userid;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.slide_in, R.anim.hold);
		setContentView(R.layout.mycalls_item);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// actionBar.setIcon(R.drawable.logo_horizontal_actionbar);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("Meus Chamados");
		
		SharedPreferences prefs = getSharedPreferences("useridentity",Context.MODE_PRIVATE);   
	    userid = prefs.getString("userid","empty");

		final Bundle extras = getIntent().getExtras();
		if (extras != null) {
			callid = extras.getString("callid");

			String origin = extras.getString("origin", "");

			if (!origin.equals("")) {
				findViewById(R.id.btn_checkin).setVisibility(View.GONE);
				findViewById(R.id.btn_checkout).setVisibility(View.GONE);
			} else {

			}
		}

		final IntentLauncher launcher = new IntentLauncher();
		launcher.getNewCallsJson();

		Button atender = (Button) findViewById(R.id.btn_checkin);
		atender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				launcher.setCallCheckin();

			}
		});

		Button finalizar = (Button) findViewById(R.id.btn_checkout);
		finalizar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				launcher.setCallCheckout();

			}
		});

		RelativeLayout andamento = (RelativeLayout) findViewById(R.id.progress);
		andamento.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MyCallsItem.this,
						MyCallsItemProgress.class);

				String origin = extras.getString("origin", "");

				if (!origin.equals("")) {
					intent.putExtra("origin", "close");
				}

				intent.putExtra("callid", callid);

				MyCallsItem.this.startActivity(intent);

			}
		});

	}

	public void retunToMyCalls() {

		SharedPreferences prefs = getSharedPreferences("qfixrprefs",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("mycallsReload", "reload");
		editor.commit();

		Intent upIntent = new Intent(MyCallsItem.this, MyCalls.class);

		if (NavUtils.shouldUpRecreateTask(MyCallsItem.this, upIntent)) {
			NavUtils.navigateUpTo(MyCallsItem.this, upIntent);
			finish();
		} else {
			finish();
		}
		overridePendingTransition(R.anim.hold, R.anim.slide_out);
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
			overridePendingTransition(R.anim.hold, R.anim.slide_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
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
		overridePendingTransition(R.anim.hold, R.anim.slide_out);
		super.onBackPressed();

	}

	// =====================================================================================
	private void newCallsItemData(JSONArray jarray) {

		TextView callidtext = (TextView) this.findViewById(R.id.callid);
		TextView servico = (TextView) this.findViewById(R.id.servico);
		TextView dispositivo = (TextView) this.findViewById(R.id.dispositivo);
		TextView endereco = (TextView) this.findViewById(R.id.endereco);
		TextView descricao = (TextView) this.findViewById(R.id.descricao);
		TextView cliente = (TextView) this.findViewById(R.id.cliente);
		TextView contato = (TextView) this.findViewById(R.id.contato);
		TextView agenda = (TextView) this.findViewById(R.id.agenda);
		Button btn_checkin = (Button) this.findViewById(R.id.btn_checkin);
		Button btn_checkout = (Button) this.findViewById(R.id.btn_checkout);

		try {

			JSONObject jobjchamado = (JSONObject) jarray.getJSONObject(0);
			JSONObject jobjcliente = (JSONObject) jarray.getJSONObject(1);

			callidtext.setText(jobjchamado.get("id").toString());
			endereco.setText(jobjcliente.get("rua").toString() + ","
					+ jobjcliente.get("numero").toString() + ","
					+ jobjcliente.get("bairro").toString());

			cliente.setText(jobjcliente.get("nome").toString());
			contato.setText(jobjcliente.get("telefone").toString());
			servico.setText(jobjchamado.get("id_servico").toString());
			dispositivo.setText(jobjchamado.get("id_dispositivo").toString());
			descricao.setText(jobjchamado.get("descricao").toString());

			if (jobjchamado.get("agendar").toString() == "null") {
				agenda.setText("");
			} else {
				agenda.setText(jobjchamado.get("agendar").toString());
			}

			if (jobjchamado.get("checkin_em").toString() != "null") {
				btn_checkin.setEnabled(false);
				btn_checkout.setEnabled(true);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class IntentLauncher {

		public void getNewCallsJson() {

			AndroidHttpClient httpClient = new AndroidHttpClient(
					getString(R.string.app_server));
			httpClient.setConnectionTimeout(5000);

			ParameterMap params = httpClient.newParams().add("appsender", "1");
			httpClient.post("/chamados/item/" + callid, params,
					new AsyncCallback() {

						@Override
						public void onError(Exception e) {

							e.printStackTrace();

							AlertDialog.Builder builder = new AlertDialog.Builder(
									MyCallsItem.this);
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

								JSONArray jarray = new JSONArray(httpResponse
										.getBodyAsString());
								MyCallsItem.this.newCallsItemData(jarray);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});
		}

		public void setCallCheckin() {

			AndroidHttpClient httpClient = new AndroidHttpClient(
					getString(R.string.app_server));
			httpClient.setConnectionTimeout(5000);

			ParameterMap params = httpClient.newParams().add("appsender", "1")
					.add("id_tecnico", userid);
			httpClient.post("/chamados/checkin/" + callid, params,
					new AsyncCallback() {

						@Override
						public void onError(Exception e) {

							e.printStackTrace();

							AlertDialog.Builder builder = new AlertDialog.Builder(
									MyCallsItem.this);
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

									Toast.makeText(MyCallsItem.this,
											"Check-in Efetuado com Sucesso.",
											Toast.LENGTH_LONG).show();
									Button btn_checkin = (Button) MyCallsItem.this
											.findViewById(R.id.btn_checkin);
									Button btn_checkout = (Button) MyCallsItem.this
											.findViewById(R.id.btn_checkout);
									btn_checkin.setEnabled(false);
									btn_checkout.setEnabled(true);

								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		}

		public void setCallCheckout() {

			AndroidHttpClient httpClient = new AndroidHttpClient(
					getString(R.string.app_server));
			httpClient.setConnectionTimeout(5000);

			ParameterMap params = httpClient.newParams().add("appsender", "1");
			httpClient.post("/chamados/checkout/" + callid, params,
					new AsyncCallback() {

						@Override
						public void onError(Exception e) {

							e.printStackTrace();

							AlertDialog.Builder builder = new AlertDialog.Builder(
									MyCallsItem.this);
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

									MyCallsItem.this.retunToMyCalls();

								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					});

		}
	}

}
