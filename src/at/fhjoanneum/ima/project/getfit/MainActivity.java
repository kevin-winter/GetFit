package at.fhjoanneum.ima.project.getfit;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
		actionBar.hide();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
	}

	public void mainMenuButtonOnClick(View view) {
		switch (view.getId()) {
		case R.id.scheduler_button:
			Intent i_scheduler = new Intent(this, Scheduler.class);
			startActivity(i_scheduler);
			break;

		case R.id.exercise_button:
			Intent i_exercises = new Intent(this, Exercises.class);
			startActivity(i_exercises);
			break;

		case R.id.body_profile_button:
			Intent i_body = new Intent(this, BodyProfil.class);
			startActivity(i_body);
			break;

		case R.id.stats_button:
			Intent i_stats = new Intent(this, Stats.class);
			startActivity(i_stats);
			break;

		case R.id.todays_exec_button:
			Intent i_todayse = new Intent(this, TodaysTraining.class);
			startActivity(i_todayse);
			break;
			
		case R.id.credtits_button:
			Intent i_credits = new Intent(this, Credits.class);
			startActivity(i_credits);
			break;

		}
	

	}

}