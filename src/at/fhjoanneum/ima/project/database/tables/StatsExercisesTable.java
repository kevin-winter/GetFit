package at.fhjoanneum.ima.project.database.tables;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;

public class StatsExercisesTable {
	private List<Map<String, ?>> data=new ArrayList<Map<String, ?>>();
	private Cursor cursor;
	private Context context;
	private MyDataBaseHelper myDataBaseHelper;
	
	/**
	 * The constructor takes context to access database and prepares data for the adapter
	 * 
	 * @param context 	takes context
	 */
	public StatsExercisesTable(Context context) {
		this.context=context;
		openDB();
		cursor=myDataBaseHelper.getData("SELECT DISTINCT h.FK_Exercise, e.Name,e.PrimMuscleGroups,e.SecMuscleGroups,e.Images,e.UserObject from t_history h, t_exercises e where h.FK_Exercise=e._id and Iterations>0 ORDER BY e.name COLLATE NOCASE asc");
		if (cursor.moveToFirst()) {
			do {
				Map<String,String> map = new HashMap<String, String>();
				map.put("name", cursor.getString(cursor.getColumnIndex("Name")));
				map.put("muscle group", cursor.getString(cursor.getColumnIndex("PrimMuscleGroups"))+" "+cursor.getString(cursor.getColumnIndex("SecMuscleGroups")));
				Integer resID=0;
				if(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("UserObject")))){
					resID = context.getResources().getIdentifier("user_icon_blue" , "drawable", context.getPackageName());
				}
				else
				{
					resID = context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex("Images")).split(" ")[0]+"_icon" , "drawable", context.getPackageName());
				}
				map.put("picture", resID.toString());
				data.add(map);
			} while (cursor.moveToNext());
		}
		cursor.close();
		myDataBaseHelper.close();
	}
	public List<Map<String, ?>> getDataExercise(){
		return data;
		}
	
	private void openDB() {
		myDataBaseHelper = new MyDataBaseHelper(context);
		try {
			myDataBaseHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myDataBaseHelper.openDataBase(false);
		} catch (SQLException sqle) {
			throw new Error("sqlite");
		}
	}


}
