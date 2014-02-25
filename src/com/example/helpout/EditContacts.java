package com.example.helpout;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class EditContacts extends Activity{
	DataBaseHandler db = new DataBaseHandler(this);
	TextView resultText= null;
	ListView l=null;
	MyAdapter ma;
	SearchView s1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editcontacts);

		LayoutInflater inflater=LayoutInflater.from(this);
		
		//resultText = (TextView)findViewById(R.id.searchViewResult);
		setupSearchView();
		l=(ListView)findViewById(R.id.lv1);
		
		//View v=inflater.inflate(R.layout.footer,null);
		//l.addFooterView(v);
	    ma = new MyAdapter();
	      
	    l.setAdapter(ma);
	    
	    s1=(SearchView)findViewById(R.id.searchView);
	    //s1.setQuery("Add guardian", false);
	}

	private void setupSearchView() {
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) findViewById(R.id.searchView);
		SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
		searchView.setSearchableInfo(searchableInfo);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		
		//Toast.makeText(getApplicationContext(), "in new intent", Toast.LENGTH_SHORT).show();
		
		contactno cn=new contactno();
		if (ContactsContract.Intents.SEARCH_SUGGESTION_CLICKED.equals(intent.getAction())) {
			//handles suggestion clicked query
			cn = getDisplayNameForContact(intent);
			if(cn==null){
				//Do nothing so leave it like that 
				
				} 
					else{
							boolean isthere=db.search_database(cn.no);
								if(isthere){
								db.addContact(new Contact(cn.name,cn.no));}
								else{
										Toast.makeText(getApplicationContext(), "The contact is already in your list.", Toast.LENGTH_SHORT).show();
									}

								l=(ListView)findViewById(R.id.lv1);
								ma = new MyAdapter();
								l.setAdapter(ma);
								s1.setQuery("", false);
								s1.clearFocus();
		    
					}
			} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// handles a search query
			String query = intent.getStringExtra(SearchManager.QUERY);
			//resultText.setText("should search for query: '" + query + "'...");
	
		
		}
	}
	
	private contactno getDisplayNameForContact(Intent intent) {
		
		contactno cn=new contactno();
		Cursor phoneCursor = getContentResolver().query(intent.getData(), null, null, null, null);
		
		phoneCursor.moveToFirst();
		
		String id=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.Contacts._ID));
		String name=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		//String number=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		if(Integer.parseInt(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0)
		{
			Cursor pcur=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?" ,new String[]{id}, null);
			//while(pcur.moveToNext()){
				
				while(pcur.moveToNext()){
				int ph=pcur.getInt(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
				//String type=pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
				if(ph==ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE){
					String number=pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					cn.name=name;
					cn.no=number;	
				}
				else if((ph==ContactsContract.CommonDataKinds.Phone.TYPE_HOME)||(ph==ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME)){
					Toast.makeText(getApplicationContext(), "not mobile no.", Toast.LENGTH_SHORT).show();
					return null;
				}
				else
				{
					String number=pcur.getString(pcur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					cn.name=name;
					cn.no=number;
				}
			}
			pcur.close();
			
		}
		else{
			Toast.makeText(getApplicationContext(), "Does not have a phone no.", Toast.LENGTH_SHORT).show();
			return null;
		}
		//cn.name=name;
		//cn.no=number;
		phoneCursor.close();
		
		return cn;
	}


	
	public void deletecontacts(View v){
		contactno cn=null;
		for(int i=0;i<ma.as.size();i++){
		
			if(ma.mcheck.get(i)==true){
				
				//cn.name=ma.as1.get(i).toString();
				//cn.no=ma.as.get(i).toString();
				db.deleteContact(ma.as.get(i).toString());
				//Toast.makeText(getApplicationContext(), "came into delete", Toast.LENGTH_SHORT).show();
				
			}
				
		}
		l=(ListView)findViewById(R.id.lv1);
		 
	    ma = new MyAdapter();
	 
		l.setAdapter(ma);
	}
	
	public void backtofragment(View v){
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED, returnIntent);        
		finish();
	}
	
class MyAdapter extends BaseAdapter implements OnCheckedChangeListener{

		
		LayoutInflater mInflater;
		TextView tv,tv1;
		CheckBox cb;	
		private SparseBooleanArray mcheck;
		
		  List<Contact> contacts = db.getAllContacts();
		    ArrayList<String> as = new ArrayList<String>();
		    ArrayList<String> as1=new ArrayList<String>();
		
		MyAdapter(){ 
			mcheck=new SparseBooleanArray(as1.size());
			    for(Contact item:contacts)
			    {
			    	as.add(item.getPhoneNumber());
			    	as1.add(item.getName());
			    }
			  
			mInflater = (LayoutInflater)EditContacts.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return as.size(); 
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi=convertView;
			if(convertView==null){
				vi = mInflater.inflate(R.layout.listitem, null);
			}
			tv= (TextView) vi.findViewById(R.id.textview);
			tv1= (TextView) vi.findViewById(R.id.textview1);
			cb = (CheckBox) vi.findViewById(R.id.checkbox);
			tv.setText(as1.get(position));
			tv1.setText(as.get(position));
			cb.setTag(position);
			cb.setChecked(mcheck.get(position, false));
			cb.setOnCheckedChangeListener(this);
			return vi;
		}

	
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			mcheck.put((Integer)buttonView.getTag(), isChecked);
		}
		
		public boolean isChecked(int position){
			return (mcheck.get(position, false));
		}
		
		public void setChecked(int position,boolean isChecked){
			mcheck.put(position, isChecked);
		}
		
		public void toggle(int position){
			setChecked(position,!isChecked(position));
		}
		
	}

	public class contactno{
		String name;
		String no;
	};

}
