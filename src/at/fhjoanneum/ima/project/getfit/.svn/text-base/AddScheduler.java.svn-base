package at.fhjoanneum.ima.project.getfit;

import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import at.fhjoanneum.ima.project.userClasses.Day;
import at.fhjoanneum.ima.project.userClasses.Schedule;

public class AddScheduler extends Activity {
	private boolean addable;
	private Spinner duration_spinner;
	private EditText scheduler_name;
	private TextView showDay;

	private LinearLayout bLine2;
	private LinearLayout bLine3;

	private ExerciseCheckList listFrag;
	private ArrayMap<Integer, ArrayList<Integer>> exePerDay;
	private Integer button;
	private ArrayList<String> scheduler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_scheduler);
		
		scheduler = (ArrayList<String>) getIntent().getExtras().get("scheduler");
		addable = false;
		duration_spinner = (Spinner) findViewById(R.id.duration_drop_down);
		scheduler_name = (EditText) findViewById(R.id.scheduler_title);
		exePerDay = new ArrayMap<Integer, ArrayList<Integer>>(21);
		showDay = (TextView) findViewById(R.id.showDay);

		FragmentManager fragManager = getFragmentManager();
		listFrag = (ExerciseCheckList) fragManager.findFragmentById(R.id.list);
		listFrag.getExePerDay();

		button = 1;
		bLine2 = (LinearLayout) findViewById(R.id.bLine2);
		bLine3 = (LinearLayout) findViewById(R.id.bLine3);

		TextWatcher watcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (scheduler_name.getText().toString().equals("") || scheduler.contains(String.valueOf(s).toLowerCase())) {
					addable = false;
					invalidateOptionsMenu();
				} else {
					addable = true;
					invalidateOptionsMenu();
				}

			}
		};
		scheduler_name.addTextChangedListener(watcher);
		duration_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int pos, long id) {
						if (duration_spinner.getSelectedItem().toString()
								.equals("7 days")) {
							bLine2.setVisibility(View.GONE);
							bLine3.setVisibility(View.GONE);
						} else if (duration_spinner.getSelectedItem()
								.toString().equals("14 days")) {
							bLine2.setVisibility(View.VISIBLE);
							bLine3.setVisibility(View.GONE);
						} else {
							bLine2.setVisibility(View.VISIBLE);
							bLine3.setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});

		setupActionBar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_scheduler, menu);
		MenuItem add_scheduler = menu.findItem(R.id.add_scheduler_to_db);
		if (addable) {
			add_scheduler.setEnabled(true);
			add_scheduler.setIcon(getResources().getDrawable(
					R.drawable.plus_add));
		} else {
			add_scheduler.setEnabled(false);
			add_scheduler.setIcon(getResources().getDrawable(
					R.drawable.plus_add_gray));
		}
		return true;
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent i_back_to_sched = new Intent(this, Scheduler.class);
			startActivity(i_back_to_sched);
			return true;
		case R.id.add_scheduler_to_db:
			manageDayClick(findViewById(R.id.day1));
			manageDayClick(findViewById(R.id.day2));
			addSchedulerToDatabaseOptions();
			Context context = getApplicationContext();
			CharSequence text = "Scheduler "
					+ scheduler_name.getText().toString() + " has been saved!";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			scheduler_name.setText("");
			duration_spinner.setSelection(0);
			addable = false;
			invalidateOptionsMenu();
			Intent back_scheduler =  new Intent(this, Scheduler.class);
			startActivity(back_scheduler);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void addSchedulerToDatabaseOptions() {
		Schedule newSchedule = new Schedule(
				scheduler_name.getText().toString(),
				Integer.parseInt(duration_spinner.getSelectedItem().toString()
						.split(" ")[0]));

		for (Integer day : exePerDay.keySet()) {
			if ((!exePerDay.get(day).isEmpty())
					&& exePerDay.get(day)!=null)
				newSchedule.addDay(day + 1, new Day(day+1,exePerDay.get(day)));
		}
		newSchedule.writeScheduler(this);

	}

	public void manageDayClick(View v) {
		ArrayList<Integer> t = new ArrayList<Integer>();
		t.addAll(listFrag.getExePerDay());

		if (!(t.size() == 0)) {
			exePerDay.put(button - 1, t);
			listFrag.getListView().clearChoices();
			listFrag.getListView().requestLayout();
		}
		button = Integer.valueOf(String.valueOf(v.getTag()).substring(3));
		if (exePerDay.containsKey(button - 1))
			listFrag.setExePerDay(exePerDay.get(button - 1));
		showDay.setText("Day " + String.valueOf(button));


	}
	
	@Override
	public void onBackPressed() {
		Intent i_back_to_sched = new Intent(this, Scheduler.class);
		startActivity(i_back_to_sched);
	}

}
