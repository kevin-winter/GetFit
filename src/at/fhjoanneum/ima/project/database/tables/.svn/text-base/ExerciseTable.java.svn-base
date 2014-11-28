package at.fhjoanneum.ima.project.database.tables;

import java.io.Serializable;
import java.util.ArrayList;

import android.database.Cursor;
import at.fhjoanneum.ima.project.userClasses.Exercise;

public class ExerciseTable implements Serializable {
	private ArrayList<Exercise> values = new ArrayList<Exercise>();

	/**
	 * Default Construcor with no Parameters.
	 */
	public ExerciseTable() {
	}
	/**
	 * Is used to create an ExerciseTable which is filled with the given Data of
	 * the cursor.
	 * 
	 * @param cursor Contains the result of a SELECT Statement with ALL columns('*')!!!
	 */
	public ExerciseTable(Cursor cursor) {
		if(cursor.moveToFirst()){
			do {
				Exercise exercise = cursorToExerciseTable(cursor);
				this.values.add(exercise);
			}while(cursor.moveToNext());
		}
		cursor.close();
	}
	
	public ArrayList<Exercise> getValues() {
		return values;
	}

	public void addValues(Exercise values) {
		this.values.add(values);
	}

	/**
	 * Converts the cursor into a Exercise
	 * @param cursor 
	 * @return A filled Exercise
	 */
	private Exercise cursorToExerciseTable(Cursor cursor) {
		Exercise exercise = new Exercise(cursor,false);
		return exercise;
	}

}
