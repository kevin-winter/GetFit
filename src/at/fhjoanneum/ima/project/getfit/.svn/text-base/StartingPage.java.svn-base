package at.fhjoanneum.ima.project.getfit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StartingPage extends Activity {

	ProgressBar bar;
	CountDownTimer countDown;
	int total = 0;
	TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_starting_page);
		text = (TextView) findViewById(R.id.start_text);
		bar = (ProgressBar) findViewById(R.id.progress_bar);
		text.setText("Loading");
		bar.setProgress(total);
		int delay = 2500; //  2.5 seconds in milli seconds


		/** CountDownTimer starts with 5 seconds and every onTick is 1 second */
		countDown = new CountDownTimer(delay, 500) {

			public void onTick(long millisUntilFinished) {
				total = (int) (total*0.8);
				bar.setProgress(total);
				text.append(".");
			}

			public void onFinish() {
				Intent i_to_main = new Intent(StartingPage.this, MainActivity.class);
				startActivity(i_to_main);
			}
		}.start();

	}
	
	@Override
	public void onBackPressed() {
	}
	

}
