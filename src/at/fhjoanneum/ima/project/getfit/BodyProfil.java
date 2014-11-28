package at.fhjoanneum.ima.project.getfit;

import java.io.IOException;
import java.sql.SQLException;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
import at.fhjoanneum.ima.project.database.tables.HistoryTable;

public class BodyProfil extends Activity {
	private static final int SHOULDER = 1003;
	private static final int HIPS = 1004;
	private static final int WAIST = 1005;
	private static final int BICEPS = 1007;
	private static final int CHEST = 1002;
	private static final int THIGH = 1006;
	private static final int HEIGHT = 1001;
	private static final int WEIGHT = 1000;
	private static final String SELECT_ALL_FROM_HISTORY_LATEST = "select * from t_history a where Date = (select max(Date) from t_history b where a.Fk_Body = b.Fk_Body)";
	private HistoryTable bodyValues;
	private Boolean submitable;
	EditText height;
	EditText weight;
	EditText waist;
	EditText shoulder;
	EditText hips;
	EditText thigh;
	EditText biceps;
	EditText chest;
	TextWatcher watcher;

	private MyDataBaseHelper MyHelper;

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
		submitable = false;
		setContentView(R.layout.activity_body_profil);
		// Show the Up button in the action bar.
		setupActionBar();
		openDB();
		height = (EditText) findViewById(R.id.body_height);
		weight = (EditText) findViewById(R.id.body_weight);
		waist = (EditText) findViewById(R.id.body_waist);
		shoulder = (EditText) findViewById(R.id.body_shoulder);
		hips = (EditText) findViewById(R.id.body_hips);
		thigh = (EditText) findViewById(R.id.body_thigh);
		biceps = (EditText) findViewById(R.id.body_biceps);
		chest = (EditText) findViewById(R.id.body_chest);
		watcher = new TextWatcher() {
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
				submitable = true;
				invalidateOptionsMenu();
			}
		};

		bodyValues = new HistoryTable(
				MyHelper.getData(SELECT_ALL_FROM_HISTORY_LATEST));

		if (bodyValues.getValues().containsKey(HEIGHT))
			height.setText(String.valueOf(bodyValues.getValues().get(HEIGHT)));
		if (bodyValues.getValues().containsKey(WEIGHT))
			weight.setText(String.valueOf(bodyValues.getValues().get(WEIGHT)));
		if (bodyValues.getValues().containsKey(THIGH))
			thigh.setText(String.valueOf(bodyValues.getValues().get(THIGH)));
		if (bodyValues.getValues().containsKey(CHEST))
			chest.setText(String.valueOf(bodyValues.getValues().get(CHEST)));
		if (bodyValues.getValues().containsKey(BICEPS))
			biceps.setText(String.valueOf(bodyValues.getValues().get(BICEPS)));
		if (bodyValues.getValues().containsKey(WAIST))
			waist.setText(String.valueOf(bodyValues.getValues().get(WAIST)));
		if (bodyValues.getValues().containsKey(HIPS))
			hips.setText(String.valueOf(bodyValues.getValues().get(HIPS)));
		if (bodyValues.getValues().containsKey(SHOULDER))
			shoulder.setText(String.valueOf(bodyValues.getValues()
					.get(SHOULDER)));

		height.addTextChangedListener(watcher);
		weight.addTextChangedListener(watcher);
		thigh.addTextChangedListener(watcher);
		chest.addTextChangedListener(watcher);
		biceps.addTextChangedListener(watcher);
		waist.addTextChangedListener(watcher);
		hips.addTextChangedListener(watcher);
		shoulder.addTextChangedListener(watcher);
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
	protected void onDestroy() {
		super.onDestroy();
		MyHelper.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.body_profil, menu);
		MenuItem submit = menu.findItem(R.id.body_submit);
		submit.setEnabled(submitable);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent i_back_to_main = new Intent(this, MainActivity.class);
			startActivity(i_back_to_main);
			return true;
		case R.id.body_submit:
			Log.i("YOLO", "JETZT WIRD ES GEFAEHRLICH");
			HistoryTable newValues = new HistoryTable();
			if (!height.getText().toString().isEmpty())
				newValues.addKeyValue(HEIGHT, Float.parseFloat(height.getText().toString()));
			if (!weight.getText().toString().isEmpty())
				newValues.addKeyValue(WEIGHT, Float.parseFloat(weight.getText().toString()));
			if (!thigh.getText().toString().isEmpty())
				newValues.addKeyValue(THIGH, Float.parseFloat(thigh.getText().toString()));
			if (!chest.getText().toString().isEmpty())
				newValues.addKeyValue(CHEST, Float.parseFloat(chest.getText().toString()));
			if (!biceps.getText().toString().isEmpty())
				newValues.addKeyValue(BICEPS, Float.parseFloat(biceps.getText().toString()));
			if (!waist.getText().toString().isEmpty())
				newValues.addKeyValue(WAIST, Float.parseFloat(waist.getText().toString()));
			if (!hips.getText().toString().isEmpty())
				newValues.addKeyValue(HIPS, Float.parseFloat(hips.getText().toString()));
			if (!shoulder.getText().toString().isEmpty())
				newValues.addKeyValue(SHOULDER, Float.parseFloat(shoulder.getText().toString()));
			Log.i("FEHLERBEHEBUNG", "newValues befuellt");
			newValues.addToDatabase(this);
			Context context = getApplicationContext();
			CharSequence text = "Body data has been updated!";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
			submitable = false;
			invalidateOptionsMenu();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		Intent i_back_to_main = new Intent(this, MainActivity.class);
		startActivity(i_back_to_main);
	}

}
