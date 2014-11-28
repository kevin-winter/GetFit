package at.fhjoanneum.ima.project.getfit;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
import at.fhjoanneum.ima.project.database.tables.ExerciseTable;
import at.fhjoanneum.ima.project.userClasses.Exercise;

public class Exercises extends ListActivity implements Serializable {

	private final String TABLE_EXERCISES = "t_exercises";
	private final String SELECT_ALL_FROM_EXERCISES = "SELECT * FROM '"
			+ TABLE_EXERCISES + "' ";
	private final String SELECT_ALL_FROM_EXERCISES_ALPHABETIC = "SELECT * FROM t_exercises order by Name COLLATE NOCASE ASC";
	private final String SELECT_ALL_FROM_EXERCISES_WHERE_ID = SELECT_ALL_FROM_EXERCISES
			+ " where _id = ";
	private final String SELECT_ALL_FROM_EXERCISES_WHERE_NAMELIKE = SELECT_ALL_FROM_EXERCISES
			+ " where Name like '";
	private Menu menu;

	private static class ViewHolder {
		public Button sort_alpha;
		public ImageButton sort_muscles;
		public ImageButton sort_user;

		public ImageView select_line;
		public ImageView select_line2;
		public ImageView select_line3;
	}

	private ViewHolder views = new ViewHolder();
	private String[] columnsM = new String[] { "name", "muscles", "path" };
	private String[] columnsA = new String[] { "name", "muscles", "path", "id" };
	static String EXERCISE = "EXERCISE";
	// Steps to open the DB in onCreate
	private MyDataBaseHelper MyHelper;

	private void openDB(Boolean readwrite) {
		MyHelper = new MyDataBaseHelper(this);
		try {
			MyHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			MyHelper.openDataBase(readwrite);
		} catch (SQLException sqle) {
			throw new Error("sqlite");
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Cursor cursor;
		TextView musclegroup = (TextView) v.findViewById(R.id.exerciseMuscles);
		TextView name = (TextView) v.findViewById(R.id.exerciseName);
		if (views.select_line.getVisibility() == View.VISIBLE) {
			Intent i = new Intent(this, ExerciseViewer.class);
			cursor = MyHelper
					.getData(SELECT_ALL_FROM_EXERCISES_WHERE_ID+ String.valueOf(v.getId()));
			Exercise selected = new Exercise(cursor, true);
			i.putExtra(EXERCISE, (Serializable) selected);
			startActivity(i);

		} else if (views.select_line2.getVisibility() == View.VISIBLE) {

			if (musclegroup.getText().toString().length() > 1) {
				Intent i = new Intent(this, ExerciseViewer.class);
				cursor = MyHelper.getData(SELECT_ALL_FROM_EXERCISES_WHERE_NAMELIKE+ name.getText().toString() + "' COLLATE NOCASE");
				Exercise selected = new Exercise(cursor, true);
				i.putExtra(EXERCISE, (Serializable) selected);
				startActivity(i);
			} else {

				String cathegory = name.getText().toString();
				cursor = MyHelper
						.getData("select * from t_exercises where PrimMuscleGroups like '%"+ cathegory + "%' ORDER BY NAME COLLATE NOCASE");
				setExerciseListAdapter(columnsA,
						getExercisesInAlphabeticalOrder(cursor, columnsA));
			}

		} else if (views.select_line3.getVisibility() == View.VISIBLE) {
			Intent i = new Intent(this, ExerciseViewer.class);
			cursor = MyHelper
					.getData("select * from t_exercises where userobject='true' AND _id="+ String.valueOf(v.getId()));
			Exercise selected = new Exercise(cursor, true);
			i.putExtra(EXERCISE, (Serializable) selected);
			startActivity(i);
		}

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercises);
		views.sort_alpha = (Button) findViewById(R.id.sort_alphabethic);
		views.sort_muscles = (ImageButton) findViewById(R.id.sort_muscle);
		views.sort_user = (ImageButton) findViewById(R.id.sort_user);
		views.select_line = (ImageView) findViewById(R.id.select_line);
		views.select_line2 = (ImageView) findViewById(R.id.select_line2);
		views.select_line3 = (ImageView) findViewById(R.id.select_line3);

		views.sort_alpha.setEnabled(false);
		views.sort_muscles.setEnabled(true);
		views.sort_user.setEnabled(true);
		views.select_line.setVisibility(View.VISIBLE);
		views.select_line2.setVisibility(View.INVISIBLE);
		views.select_line3.setVisibility(View.INVISIBLE);

		openDB(false);
		setExerciseListAdapter(	columnsA,
				getExercisesInAlphabeticalOrder(
						MyHelper.getData(SELECT_ALL_FROM_EXERCISES_ALPHABETIC),
						columnsA));
		
		
		setupActionBar();

	}

	// alphabetisch
	private ArrayList<HashMap<String, String>> getExercisesInAlphabeticalOrder(Cursor cursor, String[] columns) {
		
		ExerciseTable allExercises = new ExerciseTable(cursor);
		ArrayList<HashMap<String, String>> exerciseMap = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < allExercises.getValues().size(); i++) {
			exerciseMap.add(addToArrayList(columns,allExercises,i));
		}
		return exerciseMap;
	}

	// musklegruppe
	private ArrayList<HashMap<String, String>> getExercisesInMuscelGroupOrder(
			Cursor unsortedExercises, String[] columns) {
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		TreeSet<String> muscleGroupMap = new TreeSet<String>();
		unsortedExercises.moveToFirst();
		while (!unsortedExercises.isAfterLast()) {
			if (unsortedExercises.getString(0).contains(" ")) {
				String mem[] = unsortedExercises.getString(0).split(" ");
				for (int i = 0; i < mem.length; i++) {
					muscleGroupMap.add(mem[i]);
				}
			} else {
				muscleGroupMap.add(unsortedExercises.getString(0));
			}
			unsortedExercises.moveToNext();
		}
		unsortedExercises.close();
		for (int i = 0; i < muscleGroupMap.size(); i++) {
			HashMap<String, String> cathegory = new HashMap<String, String>();
			String test = muscleGroupMap.toArray()[i].toString();
			test = test.substring(0, 1).toUpperCase()+test.substring(1);
			cathegory.put(columns[0], test);
			cathegory.put(columns[1], "");
			cathegory.put(columns[2], "");
			data.add(cathegory);
			unsortedExercises.moveToNext();
		}
		return data;
	}

	// userTyp
	private ArrayList<HashMap<String, String>> getExercisesInUserObjectOrder(
			String[] columns) {

		Cursor unsortedExercises = MyHelper
				.getData("select * from 't_exercises' where UserObject = 'true' ORDER BY NAME COLLATE NOCASE");
		ExerciseTable exercises = new ExerciseTable(unsortedExercises);
		ArrayList<HashMap<String, String>> exerciseMap = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < exercises.getValues().size(); i++) {
			exerciseMap.add(addToArrayList(columns,exercises,i));
		}
		return exerciseMap;
	}
	/**
	 * Musclegroups in UpperCase for listadapter
	 * @param columns provided names for listadapter
	 * @param allExercises rawData
	 * @param i position of the data
	 * @return
	 */
	private HashMap<String,String> addToArrayList(String[] columns,ExerciseTable allExercises, int i){
		HashMap<String, String> currentExercise = new HashMap<String, String>();
		currentExercise.put(columns[0], allExercises.getValues().get(i)
				.getName());
		String upperString = allExercises.getValues()
				.get(i).getPrimMuscleGroups().substring(0, 1).toUpperCase() +allExercises.getValues()
				.get(i).getPrimMuscleGroups().substring(1).toLowerCase();
		if (allExercises.getValues().get(i).getSecMuscleGroups().length() > 1) {
			String upperString2 = allExercises.getValues().get(i)
					.getSecMuscleGroups().substring(0, 1).toUpperCase() +allExercises.getValues().get(i)
					.getSecMuscleGroups().substring(1).toLowerCase();
			currentExercise.put(columns[1], (upperString
					+ ", " + upperString2));
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
		ListView listView = getListView();
		listView.setLongClickable(true);        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,int position, long id) {
				ListView list1 = (ListView) findViewById(android.R.id.list);
				final HashMap<String, String> map= (HashMap<String, String>) list1.getAdapter().getItem(position);
				if(map.get("id")!=null && Integer.parseInt(map.get("id"))>277){
		              final String selectedValue = (String) map.get("name");
		                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(Exercises.this);
		                alertDialog.setTitle("Delete " + selectedValue);
		                alertDialog.setMessage("The exercise " + selectedValue + " will be permanently deleted! Are you sure you want to continue?");     
		                alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
		                       public void onClick(DialogInterface dialog, int which) {
		                    	   openDB(true);
		                    	   MyHelper.deleteData("t_exercises", "_id='"+String.valueOf(map.get("id"))+"'");
		                    	   Intent intent2 = new Intent(Exercises.this, Exercises.class);
		                                startActivity(intent2);

		                   } }); 
		                   alertDialog.setPositiveButton("Keep", new DialogInterface.OnClickListener() {
		                       public void onClick(DialogInterface dialog, int which) {
		                   } }); 

		                   alertDialog.show();
	                return true;

					}else {
						return true;
					}
				}});
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
		getMenuInflater().inflate(R.menu.exercise_menu, menu);
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent i_back_to_main = new Intent(this, MainActivity.class);
			startActivity(i_back_to_main);
			return true;
		case R.id.menu_add:
			Intent i_add_exec = new Intent(this, AddExercise.class);
			startActivity(i_add_exec);
			break;
		case R.id.action_search:
			onSearchRequested();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void tabChange(View view) {
		MenuItem add = (MenuItem)menu.findItem(R.id.action_search);
		switch (view.getId()) {
		case R.id.sort_alphabethic:
			add.setEnabled(true);
			add.setIcon(R.drawable.search_icon);
			views.sort_alpha.setEnabled(true);
			views.sort_muscles.setEnabled(true);
			views.sort_user.setEnabled(true);
			views.select_line.setVisibility(View.VISIBLE);
			views.select_line2.setVisibility(View.INVISIBLE);
			views.select_line3.setVisibility(View.INVISIBLE);
			setExerciseListAdapter(
					columnsA,
					getExercisesInAlphabeticalOrder(
							MyHelper.getData(SELECT_ALL_FROM_EXERCISES_ALPHABETIC),
							columnsA));
			break;

		case R.id.sort_muscle:
			add.setEnabled(false);
			add.setIcon(R.drawable.search_icon_gray);
			views.sort_alpha.setEnabled(true);
			views.sort_muscles.setEnabled(true);
			views.sort_user.setEnabled(true);
			views.select_line.setVisibility(View.INVISIBLE);
			views.select_line2.setVisibility(View.VISIBLE);
			views.select_line3.setVisibility(View.INVISIBLE);
			

			setExerciseListAdapter(
					columnsM,
					getExercisesInMuscelGroupOrder(
							MyHelper.getData("select distinct trim(PrimMuscleGroups) from 't_exercises' ORDER BY PrimMuscleGroups COLLATE NOCASE"),
							columnsM));
			break;

		case R.id.sort_user:
			add.setEnabled(false);
			add.setIcon(R.drawable.search_icon_gray);
			views.sort_alpha.setEnabled(true);
			views.sort_muscles.setEnabled(true);
			views.sort_user.setEnabled(true);
			views.select_line.setVisibility(View.INVISIBLE);
			views.select_line2.setVisibility(View.INVISIBLE);
			views.select_line3.setVisibility(View.VISIBLE);
			setExerciseListAdapter(columnsA,
					getExercisesInUserObjectOrder(columnsA));
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i_back_to_main = new Intent(this, MainActivity.class);
		startActivity(i_back_to_main);
	}

}
