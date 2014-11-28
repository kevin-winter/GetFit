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

public class StatsBodyTable {
	private List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
	private Cursor cursor;
	private Context context;
	private MyDataBaseHelper myDataBaseHelper;
	
	
	/**
	 * The constructor takes context to access database and prepares data for the adapter
	 * 
	 * @param context 	takes context
	 */
	public StatsBodyTable(Context context) {
		this.context = context;
		openDB();
		cursor = myDataBaseHelper
				.getData("SELECT DISTINCT b._id, h.FK_Body, b.Name,b.images from t_history h, t_body b  where h.FK_Body=b._id  and h.Value>0 ORDER BY b.name COLLATE NOCASE asc");
		if (cursor.moveToFirst()) {
			do {
				Map<String,String> map = new HashMap<String, String>();
				map.put("name", cursor.getString(cursor.getColumnIndex("Name")));
				Integer resID =	context.getResources().getIdentifier(cursor.getString(cursor.getColumnIndex("images")), "drawable", context.getPackageName());
				map.put("images", resID.toString());
				data.add(map);
			} while (cursor.moveToNext());
		}
		Boolean weight=false;
		Boolean height=false;
		for (Map entry : data) {
			if (entry.containsValue("weight"))weight=true;
			if (entry.containsValue("height"))height=true;
			if(weight&&height){
				Map<String,String> map = new HashMap<String, String>();
				map.put("name", "bmi");
				Integer resID =	context.getResources().getIdentifier("bmi_icon", "drawable", context.getPackageName());
				map.put("images",resID.toString());
				if(data.get(0).containsValue("biceps"))data.add(1, map);
				else data.add(0, map);
				break;
			}
		}
		cursor.close();
		myDataBaseHelper.close();
	}

	public List<Map<String, ?>> getDataBody() {
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
