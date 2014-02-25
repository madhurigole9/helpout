package com.example.helpout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class pinchange extends Activity implements OnClickListener{
	TextView text;
	EditText editText;
	Button button;
	String name,pin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinchange);

		editText = (EditText) findViewById(R.id.edit_oldpin);
		editText.setRawInputType(Configuration.KEYBOARD_QWERTY);
		text =(TextView) findViewById(R.id.tv_pinchange);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		getSavedPreferences();
	}

	private void getSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		name = sharedPreferences.getString("storedName", "1234");
	//	Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();
		
		
		//Toast.makeText(getApplicationContext(),pin, Toast.LENGTH_SHORT).show();	
	}


	@Override
	public void onClick(View v) {
	// TODO Auto-generated method stub
		
		pin = editText.getText().toString();
	//	Toast.makeText(getApplicationContext(),pin, Toast.LENGTH_SHORT).show();	
		
		
	if(name.equals(pin)){
			Intent intent = new Intent(this,newpin.class);
			startActivity(intent);
			finish();
		}
		else{
			Toast.makeText(getApplicationContext(), "Enter pin again", Toast.LENGTH_SHORT).show();
			editText.setText("");
		}
	}
}
