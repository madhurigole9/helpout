package com.example.helpout;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class SettingsFragment extends Fragment implements OnItemClickListener {


	public SettingsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}




	public static final String[] titles = new String[] { "Change Pin",
		"About", "Need Help?"};


	public static final Integer[] images = { R.drawable.redkey_final,
	 R.drawable.ic_launcher,R.drawable.redquestionmark_final};

	ListView listView;
	List<rowitem_settings> rowItems;


	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		rowItems = new ArrayList<rowitem_settings>();
		for (int i = 0; i < titles.length; i++) {
			rowitem_settings item = new rowitem_settings(images[i], titles[i]);
			rowItems.add(item);
		}

		listView = (ListView)getActivity().findViewById(R.id.list_settings);
		CustomBaseAdapter_settings adapter = new CustomBaseAdapter_settings(getActivity(), rowItems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		if(position==0)
		{
			Intent intent = new Intent(getActivity(),pinchange.class);
			startActivity(intent);
		}
		if(position==1)
		{
			Intent intent = new Intent(getActivity(),about.class);
			startActivity(intent);
		}
		if(position==2)
		{
			Intent intent = new Intent(getActivity(),help.class);
			startActivity(intent);
		}
	}

}

