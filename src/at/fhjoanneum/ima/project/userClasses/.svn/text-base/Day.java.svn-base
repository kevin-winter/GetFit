package at.fhjoanneum.ima.project.userClasses;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;

/**
 * Stores the exercises for one day
 * 
 * @author Wakonigg
 * 
 */
public class Day {
	private int id;
	private HashMap<Integer, Integer> Exercises = new HashMap<Integer, Integer>();
	private MyDataBaseHelper myHelper;

	private void openDB(Context context) {
		myHelper = new MyDataBaseHelper(context);
		try {
			myHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myHelper.openDataBase(true);
		} catch (SQLException sqle) {
			throw new Error("sqlite");
		}
	}

	public Day(int id, int exerciseId, int position) {
		this.id = id;
		this.Exercises.put(position, exerciseId);
	}

	public Day() {
	}

	public Day(int id, ArrayList<Integer> arrayList) {
		this.id = id;
		for (int j = 0; j < arrayList.size(); j++) {
			this.Exercises.put(j + 1, arrayList.get(j));
		}

	}

	public void addExercise(int pos, int ex) {
		this.Exercises.put(pos, ex);
	}

	public void delExercise(int pos) {
		this.Exercises.remove(pos);
	}

	public void writeDay(Context context, int fkSchedule) {
		openDB(context);
		int i = 1;
		for (Integer ex : Exercises.keySet()) {
			ContentValues values = new ContentValues();
			values.put("Day", this.id);
			values.put("FK_Schedule", fkSchedule);
			values.put("FK_Exercise", Exercises.get(ex));
			values.put("Position", i);
			myHelper.writeData(values, "t_exercise_schedule");
			i++;
		}
		myHelper.close();

	}

	public Day addDay(Day currentDay) {
		this.Exercises.putAll(currentDay.Exercises);
		return this;

	}

}
