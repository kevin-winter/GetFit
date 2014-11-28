package at.fhjoanneum.ima.project.database.tables;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import at.fhjoanneum.ima.project.userClasses.Schedule;

public class SchedulesTable implements Serializable {
	private ArrayList<Schedule> values = new ArrayList<Schedule>();

	public SchedulesTable() {
	}

	/**
	 * Is used to create an SchedulesTable which is filled with the given Data of
	 * the cursor.
	 * 
	 * @param cursor Contains the result of a SELECT Statement with ALL columns('*')!!!
	 */
	public SchedulesTable(Cursor cursor, Context context) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Schedule schedule = cursorToScheduleTable(cursor,context);
			this.values.add(schedule);
			cursor.moveToNext();
		}
		cursor.close();
	}

	public ArrayList<Schedule> getValues() {
		return values;
	}

	public void addValues(Schedule values) {
		this.values.add(values);
	}


	/**
	 * Converts the cursor into a Schedule
	 * @param cursor 
	 * cursor with 
	 * @param context
	 * @return A filled Schedule
	 */
	private Schedule cursorToScheduleTable(Cursor cursor,Context context) {
		Schedule schedule = new Schedule(cursor,context);
		return schedule;
	}

}

