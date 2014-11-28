package at.fhjoanneum.ima.project.userClasses;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
/**
 * Stores information about an specific exercise
 * @author Wakonigg
 *
 */
public class Exercise implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String EXERCISE_COLUMN_NAME = "Name";
	private static final String EXERCISE_COLUMN_EQUIPMENT = "Equipment";
	private static final String EXERCISE_COLUMN_STEPS = "Steps";
	private static final String EXERCISE_COLUMN_PRIMMUSCLEGROUP = "PrimMuscleGroups";
	private static final String EXERCISE_COLUMN_SECMUSCLEGROUP = "SecMuscleGroups";
	private static final String EXERCISE_COLUMN_USEROBJECT = "UserObject";
	
	private int id;
	private String name ="";
	private String description="";
	private String steps="";
	private String tips="";
	private String equipment="";
	private String images="";
	private String primMuscleGroups="";
	private String secMuscleGroups="";
	private Boolean userObject;

	private MyDataBaseHelper myHelper;

	/**
	 * Used to open DB connection
	 */
	private void openDB() {
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

	/**
	 * Default Constructor.
	 */
	public Exercise() {
	}

	/**
	 * Creates an Exercise with Cursor data.
	 * 
	 * @param cursor
	 *            Contains the result of a SELECT Statement
	 * @param moveCursor
	 * <br>
	 *            True = Cursor ONLY contains one result<br>
	 *            False = Needed for ExerciseTable construction
	 * 
	 */
	public Exercise(Cursor cursor, Boolean moveCursor) {
		if (moveCursor) {
			cursor.moveToFirst();
			cursorToExercise(cursor);
			cursor.close();
		} else if (!moveCursor) {
			cursorToExercise(cursor);
		}
	}

	public Exercise(int exerciseId) {
		this.setId(id);
	}
	/**
	 * Stores information from the current cursor position into the object
	 * @param cursor
	 */
	private void cursorToExercise(Cursor cursor) {
		this.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		this.setName(cursor.getString(cursor.getColumnIndex("Name")));
		this.setDescribtion(cursor.getString(cursor.getColumnIndex("Description")));
		if(this.getDescribtion()==null)this.setDescribtion("");
		this.setSteps(cursor.getString(cursor.getColumnIndex("Steps")));
		if(this.getSteps()==null)this.setSteps("");
		this.setTips(cursor.getString(cursor.getColumnIndex("Tips")));
		if(this.getTips()==null)this.setTips("");
		this.setEquipment(cursor.getString(cursor.getColumnIndex("Equipment")));
		if(this.getEquipment()==null)this.setEquipment("");
		this.setImages(cursor.getString(cursor.getColumnIndex("Images")));
		if(this.getImages()==null)this.setImages("");
		this.setPrimMuscleGroups(cursor.getString(cursor.getColumnIndex("PrimMuscleGroups")));
		if(this.getPrimMuscleGroups()==null)this.setPrimMuscleGroups("");
		this.setSecMuscleGroups(cursor.getString(cursor.getColumnIndex("SecMuscleGroups")));
		if(this.getSecMuscleGroups()==null)this.setSecMuscleGroups("");
		this.setUserObject(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("UserObject"))));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribtion() {
		return description;
	}

	public void setDescribtion(String describtion) {
		this.description = describtion;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getPrimMuscleGroups() {
		return primMuscleGroups;
	}

	public void setPrimMuscleGroups(String primMuscleGroups) {
		this.primMuscleGroups = primMuscleGroups;
	}

	public String getSecMuscleGroups() {
		return secMuscleGroups;
	}

	public void setSecMuscleGroups(String secMuscleGroups) {
		this.secMuscleGroups = secMuscleGroups;
	}

	public Boolean isUserObject() {
		return userObject;
	}

	public void setUserObject(Boolean userObject) {
		this.userObject = userObject;
	}

	public String addToDB(Context context) {
		myHelper = new MyDataBaseHelper(context);
		ContentValues values = new ContentValues();
		openDB();
		values.put(EXERCISE_COLUMN_NAME, this.getName());
		values.put(EXERCISE_COLUMN_EQUIPMENT, this.getEquipment());
		values.put(EXERCISE_COLUMN_STEPS, this.getSteps());
		values.put(EXERCISE_COLUMN_PRIMMUSCLEGROUP, this.getPrimMuscleGroups());
		values.put(EXERCISE_COLUMN_SECMUSCLEGROUP, this.getSecMuscleGroups());
		values.put(EXERCISE_COLUMN_USEROBJECT, String.valueOf(this.isUserObject()));
		myHelper.writeData(values,"t_exercises");
		myHelper.close();
		return null;
	}

}
