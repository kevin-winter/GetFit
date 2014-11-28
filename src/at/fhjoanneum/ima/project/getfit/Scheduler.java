package at.fhjoanneum.ima.project.getfit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
import at.fhjoanneum.ima.project.database.tables.SchedulesTable;

public class Scheduler extends ListActivity {

	// Steps to open the DB in onCreate
	private SchedulesTable mySchedules;
	private MyDataBaseHelper MyHelper;
	private String[] names;
	private ArrayList<String> defaults;
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
	private void updateSchedule(){
		openDB(true);
		String position = String.valueOf(getListView().getItemAtPosition(getListView().getCheckedItemPosition()).toString());
		ContentValues values = new ContentValues();
		values.put("Checked", "true");
		MyHelper.updateData("t_schedules", values, "Name like '"+position+"'");
		MyHelper.close();
	}

	@Override
	protected void onDestroy() {
		updateSchedule();
		super.onDestroy();
		
	}
	
	@Override
	public void onBackPressed() {
		updateSchedule();
		Intent i_back_to_main = new Intent(this, MainActivity.class);
		startActivity(i_back_to_main);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduler);
		// Show the Up button in the action bar.
		int positionOfChecked = 0;
		openDB(false);
		mySchedules = new SchedulesTable(MyHelper.getData("select * from 't_schedules'"),this);
		defaults = new ArrayList<String>();
		defaults.add("Dieting");
		defaults.add("Muscle Growth");
		defaults.add("Cardio");
		setupActionBar();
		names  = new String[mySchedules.getValues().size()];
		for (int i = 0; i < mySchedules.getValues().size(); i++) {
			names[i] =mySchedules.getValues().get(i).getName();
			if (mySchedules.getValues().get(i).getChecked()) positionOfChecked = i;
				}
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,
                       android.R.id.text1, names));
        ListView listView = getListView();
        listView.setLongClickable(true);
        
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,int position, long id) {
				ListView list1 = (ListView) findViewById(android.R.id.list);
	              final String selectedValue = (String) list1.getItemAtPosition(position);
	              	if (defaults.contains(selectedValue)) return true;
	                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(Scheduler.this);
	                alertDialog.setTitle("Delete " + selectedValue);
	                alertDialog.setMessage("The workout schedule " + selectedValue + " will be permanently deleted! Are you sure you want to continue?");     
	                alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
	                       public void onClick(DialogInterface dialog, int which) {
	                    	   openDB(true);
	                    	   MyHelper.deleteData("t_schedules", "Name='"+selectedValue+"'");
	                    	   Intent intent2 = new Intent(Scheduler.this, Scheduler.class); //go to recipe list
	                                startActivity(intent2);

	                   } }); 
	                   alertDialog.setPositiveButton("Keep", new DialogInterface.OnClickListener() {
	                       public void onClick(DialogInterface dialog, int which) {
	                   } }); 

	                   alertDialog.show();
	                return true;
			}
		});
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(positionOfChecked, true);
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
		getMenuInflater().inflate(R.menu.scheduler, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			updateSchedule();
			Intent i_back_to_main = new Intent(this, MainActivity.class);
			startActivity(i_back_to_main);
			return true;
		case R.id.add_scheduler:
			Intent i_add_scheduler = new Intent(this, AddScheduler.class);
			ArrayList<String> scheduler = new ArrayList<String>();
			for (String s : names) scheduler.add(s.toLowerCase());
			i_add_scheduler.putExtra("scheduler",scheduler);
			startActivity(i_add_scheduler);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
