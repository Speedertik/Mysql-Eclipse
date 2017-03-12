package com.example.mysql;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

public class Configure extends Activity {
	private Timer timer1 = new Timer();
	private TimerTask task1;
	private Handler handler1;
	private Random random=new Random();  
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_configure);
	        ProgressBar progress = (ProgressBar) findViewById(R.id.Progress);
	        progress.setProgress(0);
	        handler1 = new Handler() {  
	            @Override  
	            public void handleMessage(Message msg) {  
	                //刷新图表  
	                updateProgress();  
	                super.handleMessage(msg);  
	            }  
	        };  
	        task1 = new TimerTask() {  
	            @Override  
	            public void run() {  
	                Message message = new Message();  
	                message.what = 200;  
	                handler1.sendMessage(message);  
	            }  
	        };  
	        timer1.schedule(task1, 2*1000,1000);  
	    }
	    private void updateProgress() {
	    	ProgressBar progress = (ProgressBar) findViewById(R.id.Progress);
	    	progress.setProgress(random.nextInt(101));
	}
}