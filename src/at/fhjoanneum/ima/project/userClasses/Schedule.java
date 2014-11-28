package at.fhjoanneum.ima.project.userClasses;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
/**
 * Stores a HashMap of Days which contain exercises
 * @author Wakonigg
 *
 */
public class Schedule {
	private int _id;
	private String name;
	private int duration;
	private Date createdDate;
	private Boolean checked;
	private HashMap<Integer, Day> days = new HashMap<Integer, Day>();

	public Boolean getChecked() {
		return checked;
	}

	private void setChecked(Boolean checked) {
		this.checked = checked;
	}

	private MyDataBaseHelper myHelper;

	/**
	 * Used to open DB connection
	 */
	private void openDB(Context context, Boolean readwrite) {
		myHelper = new MyDataBaseHelper(context);
		try {
			myHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myHelper.openDataBase(readwrite);
		} catch (SQLException sqle) {
			throw new Error("sqlite");
		}
	}

	public Schedule(Cursor cursor, Context context) {
		cursorToSchedule(cursor);
		loadDaysFromDB(context);

	}

	private void loadDaysFromDB(Context context) {

		openDB(context, false);
		Cursor cursor = myHelper
				.getData("select * from 't_exercise_schedule' where FK_Schedule = "
						+ this.get_id());

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Integer currentPos = cursor.getInt(cursor
					.getColumnIndex("Position"));
			Integer currentExercise = cursor.getInt(cursor
					.getColumnIndex("FK_Exercise"));
			Integer currentDayId = cursor.getInt(cursor.getColumnIndex("Day"));
			Day currentDay = new Day(currentDayId, currentExercise, currentPos);
			if (!this.days.containsKey(currentDayId)) {
				this.days.put(currentDayId, currentDay);
			} else {
				this.days.put(currentDayId,
						this.days.get(currentDayId).addDay(currentDay));
			}
			cursor.moveToNext();
		}
		cursor.close();
		myHelper.close();
	}

	private void cursorToSchedule(Cursor cursor) {
		this.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
		this.setName(cursor.getString(cursor.getColumnIndex("Name")));
		this.setDuration(cursor.getInt(cursor.getColumnIndex("Duration")));
		this.setCreatedDate(Date.valueOf(cursor.getString(cursor
				.getColumnIndex("CreatedDate"))));
		this.setChecked(Boolean.valueOf(cursor.getString(cursor
				.getColumnIndex("Checked"))));
	}

	public Schedule(String name, int duration) {
		this.setName(name);
		this.setDuration(duration);
	}

	public int get_id() {
		return _id;
	}

	private void set_id(int _id) {
		this._id = _id;
	}

	private void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return this.duration;
	}

	private void setDuration(int duration) {
		this.duration = duration;
	}

	public Day getDay(int pos) {
		return this.days.get(pos);
	}

	public void addDay(int pos, Day day) {
		this.days.put(pos, day);
	}

	public void delDay(int pos) {
		this.days.remove(pos);
	}

	public void writeScheduler(Context context) {
		openDB(context, true);
		ContentValues values = new ContentValues();// Write scheduler
		values.put("Name", getName());
		values.put("Duration", getDuration());
		myHelper.writeData(values, "t_schedules");
		Cursor cursor = myHelper
				.getData("select max(_id) as id from 't_schedules'");
		cursor.moveToFirst();
		this.set_id(cursor.getInt(cursor.getColumnIndex("id")));// get ID from
																// new created
																// scheduler
		cursor.close();
		for (Integer day : days.keySet()) {
			days.get(day).writeDay(context, get_id());
		}
		myHelper.close();
	}
}
