package com.example.mysql;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
     Button btnViewPvs;
     Button btnViewChart;
     Button btnViewConfigure;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnViewPvs=(Button)findViewById(R.id.btnViewPvs);
		btnViewChart=(Button)findViewById(R.id.btnViewChart);
		btnViewConfigure=(Button)findViewById(R.id.btnViewConfigure);
		btnViewPvs.setOnClickListener(new View.OnClickListener() {
			
			@Override
	public void onClick(View v) {
				// TODO Auto-generated method stub
		Intent i =new Intent(getApplicationContext(),AllPvsActivity.class);
		startActivity(i);
		}
		});
	    btnViewChart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i =new Intent(getApplicationContext(),RtChartsActivity.class);
				startActivity(i); 
			}
		});
btnViewConfigure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i =new Intent(getApplicationContext(),Configure.class);
				startActivity(i); 
			}
		});
	}
}

	

