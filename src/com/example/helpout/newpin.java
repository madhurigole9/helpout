package com.example.helpout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newpin extends Activity implements OnClickListener{

	EditText editText1,editText2;
	Button button;
	String pinnew,pinconfirm;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newpin);

		editText1 = (EditText) findViewById(R.id.edit_newpin);
		editText1.setRawInputType(Configuration.KEYBOARD_QWERTY);
		editText2 = (EditText) findViewById(R.id.edit_pinconfirm);
		editText2.setRawInputType(Configuration.KEYBOARD_QWERTY);
		button = (Button) findViewById(R.id.buttonnewpin);
		button.setOnClickListener(this);

	}

	private void checkpin(){
		if(pinnew.equals(pinconfirm))
		{
			savePreferences("storedName", editText1.getText().toString());
			finish();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "The two passwords do not match. Try again.", Toast.LENGTH_SHORT).show();
			editText1.setText("");
			editText2.setText("");
			editText1.setFocusable(true);
		}
	}

	private void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(editText1.getText().toString().equals("") & editText2.getText().toString().equals("") )
		{
			Toast.makeText(getApplicationContext(), "Please enter the pin", Toast.LENGTH_SHORT).show();
		}
		else{
			pinnew = editText1.getText().toString();
			pinconfirm = editText2.getText().toString();
			checkpin();
		}
	}

}
