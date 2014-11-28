package at.fhjoanneum.ima.project.getfit;

import java.io.IOException;
import java.sql.SQLException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;

public class Credits extends Activity {
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		setupActionBar();
	}

	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
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

	public void openWebpageFH(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.fh-joanneum.at/ima"));
		startActivity(browserIntent);
	}

	public void openWebpageEverkinetic(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.everkinetic.com"));
		startActivity(browserIntent);
	}

	@Override
	public void onBackPressed() {
		Intent i_back_to_main = new Intent(this, MainActivity.class);
		startActivity(i_back_to_main);
	}

	public void wipeUserData(View v) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Wipe User Data");
		alertDialog
				.setMessage("Your self defined workout schedules, exercises and stats will be permanently deleted! Are you sure you want to continue?");
		alertDialog.setNegativeButton("Delete",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						openDB(false);
						MyHelper.wipeUser();
						MyHelper.close();
						openDB(false);
						MyHelper.close();
					}
				});
		alertDialog.setPositiveButton("Keep",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDialog.show();
	}
}
