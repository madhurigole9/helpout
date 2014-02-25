package com.example.helpout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class sendalert extends Activity{

	Button b1;
	LocationManager lm;
	LocationListener ll;
	DataBaseHandler db=new DataBaseHandler(this);
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tp);

		b1=(Button)findViewById(R.id.b1);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		ll = new myLocationListener();

		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
				(android.location.LocationListener) ll);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				(android.location.LocationListener) ll);
		
		//SendSMS(18.4908234,73.857534);
		//CallForHelp(18.5150917,73.8168936);
		//CallForHelp(18.4908234,73.857534);
		
		b1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), ShowMap.class);
				startActivity(intent);
			}
			
		});
		
	}

	class myLocationListener implements LocationListener {

		double lat1;
		double long1;
		@Override
		public void onLocationChanged(android.location.Location arg0) {
			// TODO Auto-generated method stub
		
			if (arg0 != null) {
				lat1 = arg0.getLatitude();
				long1 = arg0.getLongitude();
//				SendSMS(lat1,long1);
				CallForHelp(lat1,long1);
				lm.removeUpdates(ll);
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
	}

	public void SendSMS(Double l1,Double l2){
		
		//Toast.makeText(getApplicationContext(), "yoooooo..i should show you location...but i dont want to...yo yo honey singh!!", Toast.LENGTH_SHORT).show();
		String number=null;
		List<Contact> contacts = db.getAllContacts();
		
		String uri="http://maps.google.com/maps?saddr="+l1+","+l2;
		SmsManager sms=SmsManager.getDefault();
		StringBuffer smsBody = new StringBuffer(); 
		smsBody.append(Uri.parse(uri));
		
		for(Contact cn:contacts){
			number=cn.getPhoneNumber();
			sms.sendTextMessage(number, null, smsBody.toString(), null, null);
		}
		
		/*String number="9763012389";
		
		String uri="http://maps.google.com/maps?saddr="+l1+","+l2;
		SmsManager sms=SmsManager.getDefault();
		StringBuffer smsBody = new StringBuffer(); 
		smsBody.append(Uri.parse(uri));
		sms.sendTextMessage(number, null, smsBody.toString(), null, null);*/
		
	}
	
	public void CallForHelp(double latitude, double longitude){
		
		new PostHttpTask().execute(latitude,longitude);
		//Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_SHORT).show();
	}
	
	
	private class PostHttpTask extends AsyncTask<Double, Void, Void> {
		protected Void doInBackground(Double... loc) {
			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost("http://192.168.1.102:3000/callforhelp");
			try {

				List<NameValuePair> pairs = new ArrayList<NameValuePair>();

				pairs.add(new BasicNameValuePair("SenderLatitude", Double.toString(loc[0]) ));
				pairs.add(new BasicNameValuePair("SenderLongitude", Double.toString(loc[1]) ));
				httppost.setEntity(new UrlEncodedFormEntity(pairs));


				HttpResponse response = httpclient.execute(httppost);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;

		}


		protected void onPostExecute(Long result) {
			Toast.makeText(getApplicationContext(), "posted", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
}
