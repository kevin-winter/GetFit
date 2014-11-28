package at.fhjoanneum.ima.project.getfit;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class Stats extends FragmentActivity implements OnTabChangeListener{
	private FragmentTabHost mTabHost;
	private TabSpec spec;
	public static Integer statsTabCurrentPosition=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		// Show the Up button in the action bar.
		setupActionBar();
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		spec = mTabHost.newTabSpec("1").setIndicator(null,getResources().getDrawable(R.drawable.user_icon_blue));
		mTabHost.addTab(spec, StatsTabBody.class, null);
		spec = mTabHost.newTabSpec("2").setIndicator(null,getResources().getDrawable(R.drawable.arm_logo));
		mTabHost.addTab(spec, StatsTabExercises.class,null);
		mTabHost.setCurrentTab(statsTabCurrentPosition);
		mTabHost.setOnTabChangedListener(this);
	}
	
	@Override
	public void onTabChanged(String tabId) {
		statsTabCurrentPosition=mTabHost.getCurrentTab();
	}
	 


	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent i_back_to_main = new Intent(this, MainActivity.class);
			startActivity(i_back_to_main);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent i_back_to_main = new Intent(this, MainActivity.class);
		startActivity(i_back_to_main);
	}


}
