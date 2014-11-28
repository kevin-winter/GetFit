package at.fhjoanneum.ima.project.getfit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.ListActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
import at.fhjoanneum.ima.project.database.tables.ExerciseTable;

public class TodaysTraining extends ListActivity {
	private Button start_training;
	private String[] columnsA = new String[] { "name", "muscles", "path", "id" };
	static String EXERCISE = "EXERCISE";
	private MyDataBaseHelper MyHelper;

	private final String TABLE_EXERCISES = "t_exercises";
	private final String SELECT_ALL_FROM_EXERCISES = "SELECT * FROM '"
			+ TABLE_EXERCISES + "' ";

	private final String SELECT_WEEK_AND_CURRENTDAY_FROM_CHECKED_SCHEDULE = "select (((Date('now','localtime') - CreatedDate)+strftime('%w',CreatedDate))/7)%(Duration/7) as week, strftime('%w',Date('now','localtime')) as day from 't_schedules' where Checked ='true'";

	private final String SELECT_DATETIME = "select datetime('now','localtime') as time";
	private ExerciseTable allExercises;
	ExerciseAdapter myAdapter;

	private void openDB() {
		MyHelper = new MyDataBaseHelper(this);
		try {
			MyHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			MyHelper.openDataBase(false);
		} catch (SQLException sqle) {
			throw new Error("sqlite");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todays_training);
		start_training = (Button) findViewById(R.id.start_toadys_training);
		openDB();
		getTodaysDay();
		boolean check=setExerciseListAdapter(
				columnsA,
				getExercisesInAlphabeticalOrder(
						MyHelper.getData((SELECT_ALL_FROM_EXERCISES
								+ "where _id  in (select FK_Exercise from 't_exercise_schedule' "
								+ "where FK_Schedule=(select _id from 't_schedules' where Checked='true') "
								+ "AND Day="+getTodaysDay()+") ORDER BY NAME COLLATE NOCASE")), columnsA));
		if (check)setContentView(R.layout.activity_todays_training_empty);
		setupActionBar();

	}

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
			Intent i_back_to_main = new Intent(this, MainActivity.class);
			startActivity(i_back_to_main);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String getTodaysDay() {
		Cursor cursor = MyHelper.getData(SELECT_WEEK_AND_CURRENTDAY_FROM_CHECKED_SCHEDULE);
		cursor.moveToFirst();
		Integer currentDay = Integer.valueOf(cursor.getString(cursor
				.getColumnIndex("day")));
		Integer currentWeek = Integer.valueOf(cursor.getString(cursor
				.getColumnIndex("week")));
		cursor.close();
		if (currentDay.equals(0))
			currentDay = 7;
		return String.valueOf((currentWeek * 7) + currentDay);
	}

	public void todaysTrainingViewer(View view) {
		if (!allExercises.getValues().isEmpty()){
		Intent i_todays_training_viewer = new Intent(this,
				TodaysTrainingViewer.class);
		i_todays_training_viewer.putExtra("ExerciseTable", allExercises);
		startActivity(i_todays_training_viewer);}
	}

	private ArrayList<HashMap<String, String>> getExercisesInAlphabeticalOrder(
			Cursor cursor, String[] columns) {
		allExercises = new ExerciseTable(cursor);
		ArrayList<HashMap<String, String>> exerciseMap = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < allExercises.getValues().size(); i++) {
			exerciseMap.add(addToArrayList(columns, allExercises, i));
		}
		return exerciseMap;
	}

	private HashMap<String, String> addToArrayList(String[] columns,
			ExerciseTable allExercises, int i) {
		HashMap<String, String> currentExercise = new HashMap<String, String>();
		currentExercise.put(columns[0], allExercises.getValues().get(i)
				.getName());
		String upperString = allExercises.getValues().get(i)
				.getPrimMuscleGroups().substring(0, 1).toUpperCase()
				+ allExercises.getValues().get(i).getPrimMuscleGroups()
						.substring(1).toLowerCase();
		if (allExercises.getValues().get(i).getSecMuscleGroups().length() > 1) {
			String upperString2 = allExercises.getValues().get(i)
					.getSecMuscleGroups().substring(0, 1).toUpperCase()
					+ allExercises.getValues().get(i).getSecMuscleGroups()
							.substring(1).toLowerCase();
			currentExercise
					.put(columns[1], (upperString + ", " + upperString2));
		} else {
			currentExercise.put(columns[1], upperString);
		}
		currentExercise.put(columns[2], allExercises.getValues().get(i)
				.getImages());
		currentExercise.put(columns[3],
				String.valueOf(allExercises.getValues().get(i).getId()));
		return currentExercise;
	}

	// setListAdapter
	private boolean setExerciseListAdapter(String[] columns,
			ArrayList<HashMap<String, String>> ex) {
		int[] listEntryFields = new int[] { R.id.exerciseName,
				R.id.exerciseMuscles };
		myAdapter = new ExerciseAdapter(this, ex,
				R.layout.activity_listadapter_exercises, columns,
				listEntryFields);
		setListAdapter(myAdapter);
		return myAdapter.isEmpty();		
	}

	@Override
	protected void onPause() {
		this.finish();
		super.onPause();
	}

	@Override
	protected void onDestroy() {

		MyHelper.close();
		super.onDestroy();

	}
	
	@Override
	public void onBackPressed() {
		Intent i_back_to_main = new Intent(this, MainActivity.class);
		startActivity(i_back_to_main);
	}

}
