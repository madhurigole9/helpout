package com.example.helpout;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnClickListener{

	ImageView imageview,imageview1;
	String name;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, container,false);


		imageview = (ImageView) view.findViewById(R.id.bell);
		imageview1=(ImageView)view.findViewById(R.id.warning);
		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		
		final long startTime = 10 * 1000;

		imageview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//Toast.makeText(getActivity(), "jamatay",Toast.LENGTH_SHORT).show();

				final LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //ERRORS HERE!!

				final View popupView = layoutInflater.inflate(R.layout.popup, null);  
				final PopupWindow popupWindow = new PopupWindow(
						popupView, 
						LayoutParams.MATCH_PARENT,  
						LayoutParams.MATCH_PARENT,true);

				final TextView text=(TextView)popupView.findViewById(R.id.tv1);
				final Button button=(Button)popupView.findViewById(R.id.cancel);
				text.setText(String.valueOf(startTime / 1000));

				final CountDownTimer mcounter = new CountDownTimer(startTime, 1000){

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						//Toast.makeText(getActivity(), "finished", Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(getActivity(),sendalert.class);
						startActivity(intent);
						popupWindow.dismiss();
					}

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						text.setText("" + millisUntilFinished / 1000);
					}


				};
				mcounter.start();

				button.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mcounter.cancel();
			
						//Toast.makeText(getActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(getActivity(),CancelAlarm.class);
						startActivity(intent);
						popupWindow.dismiss();
					}

				});
				popupWindow.showAsDropDown(popupView,0,0);
			}


		});

		imageview1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),ThinkDanger.class);
				startActivity(intent);
			}
			
		});
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		//super.onViewCreated(view, savedInstanceState);




	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {



		}
	}

}

