package at.fhjoanneum.ima.project.database.tables;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;

public class StatsExerciseMeasurementTable {
	private Context context;
	private Cursor cursor;
	private List<Double> dateDayDiff = new ArrayList<Double>();
	private List<String> dateAsString = new ArrayList<String>();
	private List<Double> exerciseWeightList = new ArrayList<Double>();
	private List<Double> exerciseRepetitionesList = new ArrayList<Double>();
	private List<Double> exerciseSets = new ArrayList<Double>();

	private MyDataBaseHelper myHelper;

	/**
	 * The constructor takes context and dataSelect to get the required data of the database
	 * 
	 * @param context 	takes context
	 * @param dataSelect	takes String
	 */
	public StatsExerciseMeasurementTable(Context context, String dataSelect) {
		this.context = context;
		openDB();
		cursor = myHelper.getData("SELECT _ID,FK_Exercise,Value,Iterations,Sets,Date,round((julianday(Date) - julianday('now') ),0)as dateDayDiff FROM t_history where FK_Exercise=(SELECT _ID from t_exercises where name='"+dataSelect+"') group by date,FK_Exercise ORDER BY DATE(Date) ASC");
		if (cursor.moveToFirst()) {
			do {
				exerciseWeightList.add(cursor.getDouble(cursor.getColumnIndex("Value")));
				exerciseRepetitionesList.add(cursor.getDouble(cursor.getColumnIndex("Iterations")));
				exerciseSets.add(cursor.getDouble(cursor.getColumnIndex("Sets")));
				dateAsString.add(cursor.getString(cursor.getColumnIndex("Date")));
				dateDayDiff.add(cursor.getDouble(cursor.getColumnIndex("dateDayDiff")));
			} while (cursor.moveToNext());
		}
		cursor.close();
		myHelper.close();

	}

	public List<Double> getExerciseSets() {
		return exerciseSets;
	}

	public List<Double> getDateDayDiff() {
		return dateDayDiff;
	}

	public void setDateDayDiff(List<Double> dateDayDiff) {
		this.dateDayDiff = dateDayDiff;
	}

	public List<String> getDateAsString() {
		return dateAsString;
	}

	public void setDateAsString(List<String> dateAsString) {
		this.dateAsString = dateAsString;
	}

	public List<Double> getExerciseWeightList() {
		return exerciseWeightList;
	}

	public void setExerciseWeightList(List<Double> exerciseWeightList) {
		this.exerciseWeightList = exerciseWeightList;
	}

	public List<Double> getExerciseRepetitionesList() {
		return exerciseRepetitionesList;
	}

	public void setExerciseRepetitionesList(
			List<Double> exerciseRepetitionesList) {
		this.exerciseRepetitionesList = exerciseRepetitionesList;
	}

	private void openDB() {
		myHelper = new MyDataBaseHelper(context);
		try {
			myHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myHelper.openDataBase(false);
		} catch (SQLException sqle) {
			throw new Error("sqlite");
		}
	}
}
