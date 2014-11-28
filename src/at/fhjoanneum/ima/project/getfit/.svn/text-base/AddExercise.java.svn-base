package at.fhjoanneum.ima.project.getfit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import at.fhjoanneum.ima.project.userClasses.Exercise;

public class AddExercise extends Activity {
	// addable used to check if user is allowed to add exercise to SQL Lite Database
	private boolean addable;

	private static class ViewHolder {
		public EditText title;
		public Spinner primMuscle;
		public Spinner secMuscle;
		public Spinner equipment;
		public EditText steps;
		//public Button addToDB;
	}

	private static final String INPUT_ERROR = "INPUT-ERROR";
	private static final String INPUT_NO_PRIMMUSCLE = "Missing Input for PrimMuscleGroup!!!";
	private static final String INPUT_NO_NAME = "Missing Input for Title!!!";
	private ViewHolder views = new ViewHolder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise_add);
		views.title = (EditText) findViewById(R.id.add_titel);
		views.primMuscle = (Spinner) findViewById(R.id.primary_muscle_drop_down);
		views.secMuscle = (Spinner) findViewById(R.id.secundary_muscle_drop_down);
		views.equipment = (Spinner) findViewById(R.id.equipment_drop_down);
		views.steps = (EditText) findViewById(R.id.add_steps);
		
		addable = false;
		
		/**Text Watcher checks if a text is changed, if so invalidate Option Menu again*/
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
				if (views.title.getText().toString().equals("")) {
					addable = false;
					invalidateOptionsMenu();
				} else {
					addable = true;
					invalidateOptionsMenu();
				}
			}
		};
		//apply TextWatcher to the title TextView
		views.title.addTextChangedListener(watcher);
		setupActionBar();
	}


	public void addExerciseToDatabaseOptions() {

		Exercise newExercise = new Exercise();
		newExercise.setName(views.title.getText().toString());
		if(!views.primMuscle.getSelectedItem().toString().contains("-"))newExercise.setPrimMuscleGroups(views.primMuscle.getSelectedItem()
				.toString().toLowerCase());
		if(!views.secMuscle.getSelectedItem().toString().contains("-"))newExercise.setSecMuscleGroups(views.secMuscle.getSelectedItem()
				.toString().toLowerCase());
		if(!views.equipment.getSelectedItem().toString().contains("-"))newExercise.setEquipment(views.equipment.getSelectedItem().toString());
		newExercise.setSteps(views.steps.getText().toString().replace("\n",""));
		newExercise.setUserObject(true);

		if (newExercise.getName().isEmpty())
			Log.i(INPUT_ERROR, INPUT_NO_NAME);
		else if (newExercise.getPrimMuscleGroups().isEmpty())
			Log.i(INPUT_ERROR, INPUT_NO_PRIMMUSCLE);
		else {
			newExercise.addToDB(this);
		}
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
		getMenuInflater().inflate(R.menu.exercises_add, menu);
		// get the menu item 'add'
		MenuItem add_exec = menu.findItem(R.id.add_exercise_add);
		// if addable is true, means that user is allowed to add an exercise
		// set item to enabled and change color
		// otherwise disable item and change color to gray
		if (addable) {
			add_exec.setEnabled(true);
			add_exec.setIcon(getResources().getDrawable(R.drawable.plus_add));
		} else {
			add_exec.setEnabled(false);
			add_exec.setIcon(getResources().getDrawable(
					R.drawable.plus_add_gray));
		}
		return true;
	}

	/**add icon in the action bar adds an exercise to the SQL Lite Database*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//Exercise is added to Database, every TextView and SPinner is set to the default value
		//a toast is made which tells the user that the input was successful
		case android.R.id.home:
			Intent i_back_to_exec = new Intent(this, Exercises.class);
			startActivity(i_back_to_exec);
            return true;
		case R.id.add_exercise_add:
			addExerciseToDatabaseOptions();
			Context context = getApplicationContext();
			CharSequence text = "Exercise " + views.title.getText().toString() + " has been saved!";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			views.title.setText("");
			views.steps.setText("");
			views.primMuscle.setSelection(0);
			views.secMuscle.setSelection(0);
			views.equipment.setSelection(0);
			addable = false;
			invalidateOptionsMenu();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		Intent i_back_to_exec = new Intent(this, Exercises.class);
		startActivity(i_back_to_exec);
	}
}
