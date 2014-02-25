package com.example.helpout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMap extends Activity {

	private GoogleMap map;
	Marker[] m=new Marker[20];
	String line,id="56",lat1,long1;
	TextView t1,t2,t3;
	double l1,l2,l3,l4;
	int i1,i2;

	JSONArray contacts = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);

		drawmap();
	}

	@Override
	protected void onStart(){
		super.onStart();
		new getinternetData().execute();
	}


	private class getinternetData extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void...abc ) {

			try{
				
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet("http://192.168.1.102:3000/location");


				HttpResponse response = httpclient.execute(httpget);

				InputStream content = response.getEntity().getContent();

				line = convertStreamToString(content);


				
			}catch(Exception e){
				
			}
			
			return lat1;
		}

		protected void onPostExecute(String result) {
			if (line != null) {
				try {

					JSONArray a=new JSONArray(line);

					for(int i=0;i<a.length();i++)
					{
						JSONObject b=a.getJSONObject(i);

						id=b.getString("id");
						lat1=b.getString("latitude");
						long1=b.getString("longitude");

						i1=Integer.parseInt(id);
						l1=Double.parseDouble(lat1);
						l2=Double.parseDouble(long1);

						moremarker(i1,l1,l2);
					}


				}
				catch (JSONException e) {
					e.printStackTrace();
				}
			} 
			else{
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

		}
	}

	private String convertStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = rd.readLine()) != null) {
				
				total.append(line);
			}
		} catch (Exception e) {
			Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
		}
		return total.toString();
	}


	public void drawmap(){
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		final LatLng location1=new LatLng(0,0);
		
		m[0] = map.addMarker(new MarkerOptions().position(location1).title("Sender").snippet("Bachao!!").draggable(true));
		
		m[0].showInfoWindow();
		
		for(int i=1;i<20;i++){
			
		m[i] = map.addMarker(new MarkerOptions().position(location1).title("Helper "+i).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
		m[i].showInfoWindow();
		
		}
	}

	public void moremarker(int z,double a,double b){
		final LatLng location1=new LatLng(a,b);
		LatLng sender=null;
		
		if(z==0)
		{
			sender=new LatLng(a,b);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(sender, 13));
		}
		
		
		m[z].setPosition(location1);


	}

}