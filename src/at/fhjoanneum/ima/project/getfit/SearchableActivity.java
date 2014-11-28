package at.fhjoanneum.ima.project.getfit;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
import at.fhjoanneum.ima.project.database.tables.ExerciseTable;
import at.fhjoanneum.ima.project.userClasses.Exercise;

public class SearchableActivity extends ListActivity {
	private MyDataBaseHelper MyHelper;
	private String[] columnsA = new String[] { "name", "muscles", "path", "id" };
	static String EXERCISE = "EXERCISE";

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
		setContentView(R.layout.activity_searchable);
		setupActionBar();
		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doMySearch(query);
		}
	}

	private void doMySearch(String query) {
		openDB();
		ExerciseTable myExercises = new ExerciseTable(
				MyHelper.getData("select * from 't_exercises'"));
		ArrayList<HashMap<String, String>> exerciseMap = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < myExercises.getValues().size(); i++) {
			if (myExercises.getValues().get(i).getName().toString()
					.toLowerCase().contains(query.toLowerCase()) == true) {
				exerciseMap.add(addToArrayList(columnsA, myExercises, i));
			}
		}
		setExerciseListAdapter(columnsA, exerciseMap);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyHelper.close();
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
		getMenuInflater().inflate(R.menu.searchable, menu);
		return true;
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
	private void setExerciseListAdapter(String[] columns,
			ArrayList<HashMap<String, String>> ex) {
		int[] listEntryFields = new int[] { R.id.exerciseName,
				R.id.exerciseMuscles };
		ExerciseAdapter myAdapter = new ExerciseAdapter(this, ex,
				R.layout.activity_listadapter_exercises, columns,
				listEntryFields);

		setListAdapter(myAdapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Cursor cursor;
		Intent i = new Intent(this, ExerciseViewer.class);
		cursor = MyHelper.getData("select * from 't_exercises' where _id = "
				+ String.valueOf(v.getId()));
		Exercise selected = new Exercise(cursor, true);
		i.putExtra(EXERCISE, (Serializable) selected);
		startActivity(i);
	}
	@Override
	public void onBackPressed() {
		Intent i_back_to_exec = new Intent(this, Exercises.class);
		startActivity(i_back_to_exec);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent i_back_to_exec = new Intent(this, Exercises.class);
			startActivity(i_back_to_exec);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
