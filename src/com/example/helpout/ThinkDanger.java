package com.example.helpout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ThinkDanger extends Activity implements OnClickListener{

	private CountDownTimer countDownTimer=null;
	private Button startB=null,cancelB=null;
	public TextView text=null;
	private final long startTime = 10 * 1000;
	private final long interval = 1 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.think_danger);

		text = (TextView) this.findViewById(R.id.tv1);
		startB = (Button) this.findViewById(R.id.refresh);
		cancelB = (Button) this.findViewById(R.id.cancel);
		
		countDownTimer = new MyCountDownTimer(startTime, interval);
		text.setText(String.valueOf(startTime / 1000));
		
		
		startB.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				countDownTimer.start(); 
			}
			
		});
		
		cancelB.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				countDownTimer.cancel();
				finish();
			}
			
		});

		countDownTimer.start();
	}


	public class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish() {
			//text.setText("Time's up!")
			hello();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			text.setText("" + millisUntilFinished / 1000);
		}
	}
	
	public void hello(){
		Intent intent=new Intent(this,sendalert.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
