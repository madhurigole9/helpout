package com.example.helpout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CancelAlarm extends Activity{

	String name;
	EditText ed1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enterpin);
		
		ed1=(EditText)findViewById(R.id.edit1);
		ed1.setRawInputType(Configuration.KEYBOARD_QWERTY);
		
		final long startTime = 10 * 1000;
		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		final CountDownTimer mcounter1 = new CountDownTimer(startTime, 1000){
			

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "finished waiting for PIN", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), sendalert.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
			}


		};

		mcounter1.start();

		Button ok=(Button)findViewById(R.id.ok1);
		
		
		ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final EditText edittext=(EditText)findViewById(R.id.edit1);
				final String edittext1=edittext.getText().toString();
				
				name = sharedPreferences.getString("storedName", "1234");
				//Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
				// TODO Auto-generated method stub
				if(edittext1.equals(name)){
					mcounter1.cancel();
					Toast.makeText(getApplicationContext(), "The alarm has been cancelled successfully.", Toast.LENGTH_SHORT).show();
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Wrong PIN. Try again.", Toast.LENGTH_SHORT).show();
					edittext.getText().clear();
				}
			}

		});

		
	}
	
	
}
