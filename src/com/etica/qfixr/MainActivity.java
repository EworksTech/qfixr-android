package com.etica.qfixr;

import com.etica.qfixr.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		
		  Button btn_chamados= (Button) findViewById(R.id.btn_chamados);
		  btn_chamados.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  
		    	  Intent intent = new Intent(MainActivity.this, NewCalls.class);
		    	  MainActivity.this.startActivity(intent);   
		    	  //MainActivity.this.finish();
		      }
		  });
		  
		  Button btn_historico= (Button) findViewById(R.id.btn_historico);
		  btn_historico.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  
		    	  Intent intent = new Intent(MainActivity.this, MyCalls.class);
		    	  MainActivity.this.startActivity(intent);   
		    	  //MainActivity.this.finish();
		      }
		  });
		  
		  Button btn_minha_conta= (Button) findViewById(R.id.btn_minha_conta);
		  btn_minha_conta.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  
		    	  Intent intent = new Intent(MainActivity.this, MyAccount.class);
		    	  MainActivity.this.startActivity(intent);   
		    	  //MainActivity.this.finish();
		      }
		  });
		  
		  Button btn_ajuda= (Button) findViewById(R.id.btn_ajuda);
		  btn_ajuda.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  
		    	  Intent intent = new Intent(MainActivity.this, ConstructionArea.class);
		    	  MainActivity.this.startActivity(intent);   
		    	  //MainActivity.this.finish();
		      }
		  });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
