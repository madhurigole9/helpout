package com.example.helpout;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class GuardianFragment extends Fragment implements OnClickListener{

	TextView tv;
	View v;
	Button b;
	DataBaseHandler db ;
	ListView l;
	MyAdapter ma;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v= inflater.inflate(R.layout.fragment_guardian, container, false);

		return v;
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);

		db= new DataBaseHandler(getActivity());

		//	String[] a={"a","b","c"};
		l=(ListView)getActivity().findViewById(R.id.lv1);

		ma=new MyAdapter();

		l.setAdapter(ma);



		b=(Button)getActivity().findViewById(R.id.b2);

		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),EditContacts.class);
				startActivityForResult(intent,1);
			}

		});

		//		l.setAdapter(ma);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		ma=new MyAdapter();

		l.setAdapter(ma);

	}//onActivityResult

	class MyAdapter extends BaseAdapter{


		LayoutInflater mInflater;
		TextView tv,tv1;

		List<Contact> contacts = db.getAllContacts();
		ArrayList<String> as = new ArrayList<String>();
		ArrayList<String> as1=new ArrayList<String>();

		MyAdapter(){ 
			for(Contact item:contacts)
			{
				as.add(item.getPhoneNumber());
				as1.add(item.getName());
			}

			mInflater = (LayoutInflater)GuardianFragment.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
				vi = mInflater.inflate(R.layout.listitemforfragment, null);
			}
			tv= (TextView) vi.findViewById(R.id.textview);
			tv1= (TextView) vi.findViewById(R.id.textview1);
			tv.setText(as1.get(position));
			tv1.setText(as.get(position));
			return vi;
		}


	}


	public class contactno{
		String name;
		String no;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	};
}
