package at.fhjoanneum.ima.project.database.tables;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import at.fhjoanneum.ima.project.userClasses.Exercise;
import at.fhjoanneum.ima.project.userClasses.History;

/**
 * Contains a set of Records from the table t_history and will be used for
 * storing data from the database
 * 
 * @author Wakonigg
 * 
 */
public class HistoryTable {
	private HashMap<Integer, Float> values = new HashMap<Integer, Float>();
	private ArrayList<History> history = new ArrayList<History>();

	public ArrayList<History> getHistory() {
		return history;
	}

	/**
	 * Converts the current position of the Cursor into a history.
	 * 
	 * @param cursor
	 *            must contain one line from table(t_history) from getFit
	 * @return {@link History} which will be stored in the Arraylist history
	 */
	private History cursorToHistoryTable(Cursor cursor) {
		History history = new History(cursor, false);
		return history;
	}

	/**
	 * Default Constructor
	 */
	public HistoryTable() {
	}

	/**
	 * creates an historytable out of a cursor
	 * 
	 * @param cursor
	 *            contains records from t_history
	 */
	public HistoryTable(Cursor cursor) {
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			History history = cursorToHistoryTable(cursor);
			this.values.put(history.getFkBody(), history.getValue());
			cursor.moveToNext();
		}
		cursor.close();
	}

	/**
	 * creates an historytable out of an exercisetable
	 * 
	 * @param table
	 *            a exercisetable with exercises
	 * @param context
	 *            is used to get the context for opening the db connection
	 */
	public HistoryTable(ExerciseTable table, Context context) {
		for (Exercise ex : table.getValues()) {
			History currentHistory = new History(ex, context);
			this.history.add(currentHistory);
		}

	}

	public HashMap<Integer, Float> getValues() {
		return values;
	}

	public void addKeyValue(Integer key, Float value) {
		this.history.add(new History(key, value));
	}

	/**
	 * Is used to write all current available records in the arraylist into the
	 * t_history table
	 * 
	 * @param context
	 *            is used to get the context for opening the db connection
	 */
	public void addToDatabase(Context context) {
		for (int i = 0; i < this.history.size(); i++)
			this.history.get(i).addToDB(context);
	}

}
