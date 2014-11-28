package at.fhjoanneum.ima.project.userClasses;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;
/**
 * Stores information about an specific history entry from t_history
 * @author Wakonigg
 *
 */
public class History {
	private static final String EXERCISE_COLUMN_FKEXERCISE = "FK_Exercise";
	private static final String EXERCISE_COLUMN_FKBODY = "FK_Body";
	private static final String EXERCISE_COLUMN_VALUES = "Value";
	private static final String EXERCISE_COLUMN_SETS = "Sets";
	private static final String EXERCISE_COLUMN_ITERATION = "Iterations";
	
	private int id;
	private Integer fkExercises = -1;
	private Integer fkBody = -1;
	private float value;
	private Integer sets = -1;
	private Date date;
	private Integer iterations = -1;
	
	private Exercise exercise;
	
	public Exercise getExercise() {
		return exercise;
	}
	private MyDataBaseHelper myHelper;
	
	/**
	 * Used to open DB connection
	 */
	private void openDB(Context context,Boolean readwrite) {
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
	/**
	 * Stores information from the current cursor position into the object
	 * @param cursor
	 */
	private void cursorToHistory(Cursor cursor) {
		this.setId(cursor.getInt(cursor.getColumnIndex("_id")));
		this.setFkExercises(cursor.getInt(cursor.getColumnIndex("FK_Exercise")));
		if(this.getFkExercises() == null)this.setFkExercises(-1);
		this.setFkBody(cursor.getInt(cursor.getColumnIndex("FK_Body")));
		if(this.getFkBody() == null)this.setFkBody(-1);
		this.setValue(cursor.getFloat(cursor.getColumnIndex("Value")));
		this.setSets(cursor.getInt(cursor.getColumnIndex("Sets")));
		if(this.getSets() == null)this.setSets(-1);
		this.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		this.setIterations(cursor.getInt(cursor.getColumnIndex("Iterations")));
		if(this.getIterations()==null)this.setIterations(-1);
		}
	
	private void setId(int id) {
		this.id = id;
	}
	private void setFkExercises(Integer fkExercises) {
		this.fkExercises = fkExercises;
	}
	private void setFkBody(Integer fkBody) {
		this.fkBody = fkBody;
	}
	public void setSets(Integer Sets) {
		this.sets = Sets;
	}
	public void setValue(float value) {
		this.value = value;
	}
	private void setDate(String date) {
		this.date = Date.valueOf(date);
	}
	public void setIterations(Integer iterations) {
		this.iterations = iterations;
	}
	public int getId() {
		return id;
	}
	public Integer getFkExercises() {
		return fkExercises;
	}
	public Integer getFkBody() {
		return fkBody;
	}
	public float getValue() {
		return value;
	}
	public Integer getSets() {
		return sets;
	}
	public Date getDate() {
		return date;
	}
	public Integer getIterations() {
		return iterations;
	}
	
	public History(){	
	}
	public History(Cursor cursor, Boolean moveCursor){
		if (moveCursor) {
			cursor.moveToFirst();
			cursorToHistory(cursor);
			cursor.close();
		} else if (!moveCursor) {
			cursorToHistory(cursor);
		}
	}
	public History(int fkBody,float value){
		this.fkBody = fkBody;
		this.value =value;
		
	}
	public History(Exercise ex,Context context){
		this.exercise = ex;
		this.fkExercises = ex.getId();
		openDB(context,false);
		try{
		Cursor c = myHelper.getData("select * from 't_history' where _id=(select max(_id) from 't_history' where FK_Exercise='"+String.valueOf(this.fkExercises)+"')");
		c.moveToFirst();
		this.value = c.isNull(c.getColumnIndex("Value"))?0:c.getFloat(c.getColumnIndex("Value"));
		this.sets = c.isNull(c.getColumnIndex("Sets"))?0:c.getInt(c.getColumnIndex("Sets"));
		this.iterations = c.isNull(c.getColumnIndex("Iterations"))?0:c.getInt(c.getColumnIndex("Iterations"));
		c.close();
		} catch (CursorIndexOutOfBoundsException n){
			this.value = 0;
			this.sets = 0;
			this.iterations = 0;
		}finally{
		myHelper.close();
		}
		
	}
	
	public String addToDB(Context context) {
		ContentValues values = new ContentValues();
		openDB(context,true);
		if(this.getFkExercises().compareTo(-1)!=0) values.put(EXERCISE_COLUMN_FKEXERCISE, this.getFkExercises());
		if(this.getFkBody().compareTo(-1)!=0) values.put(EXERCISE_COLUMN_FKBODY, this.getFkBody());
		values.put(EXERCISE_COLUMN_VALUES, this.getValue());
		if(this.getSets().compareTo(-1)!=0) values.put(EXERCISE_COLUMN_SETS, this.getSets());
		if(this.getIterations().compareTo(-1)!=0) values.put(EXERCISE_COLUMN_ITERATION, this.getIterations());
		
		myHelper.writeData(values,"t_history");
		
		myHelper.close();
		return null;
	}

}
