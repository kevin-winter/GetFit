package at.fhjoanneum.ima.project.database.tables;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import at.fhjoanneum.ima.project.database.MyDataBaseHelper;

public class StatsBodyMeasurementTable {
	private final Integer BODYWEIGHTID = 1000;
	private final Integer BODYHEIGHTID = 1001;
	private List<Double> dateDayDiff = new ArrayList<Double>();
	private List<String> dateAsString = new ArrayList<String>();
	private List<Double> bodyDataList = new ArrayList<Double>();
	private List<Double> bodyHeightList = new ArrayList<Double>();
	private List<Double> bodyWeightList = new ArrayList<Double>();
	private List<Double> bmiList = new ArrayList<Double>();
	private Context context;
	private Cursor cursor;
	private MyDataBaseHelper myHelper;

	/**
	 * The constructor takes context and dataSelect to get the required data of the database
	 * 
	 * @param context 	takes context
	 * @param dataSelect	takes String
	 */
	public StatsBodyMeasurementTable(Context context, String dataSelect) {
		this.context = context;
		openDB();
		if (dataSelect.contains("bmi")) {
			cursor = myHelper
					.getData("select FK_Body,Value,Date,round((julianday(Date) - julianday('now') ),0)as dateDayDiff from (select a.date as adate from t_history a join t_history b on a.date = b.date where a.FK_body = 1000 and b.FK_body = 1001) a,(SELECT FK_Body,Value,Date,round((julianday(Date) - julianday('now') ),0)as dateDayDiff FROM t_history where FK_Body=1000 or FK_Body=1001 group by date,FK_BODY ) b on adate=b.date order by dateDayDiff");
			if (cursor.moveToFirst()) {
				do {
					if (cursor.getInt(cursor.getColumnIndex("FK_Body")) == BODYHEIGHTID) {
						bodyHeightList.add(cursor.getDouble(cursor
								.getColumnIndex("Value")));
						dateAsString.add(cursor.getString(cursor
								.getColumnIndex("Date")));
						dateDayDiff.add(cursor.getDouble(cursor
								.getColumnIndex("dateDayDiff")));
					}
					if (cursor.getInt(cursor.getColumnIndex("FK_Body")) == BODYWEIGHTID) {
						bodyWeightList.add(cursor.getDouble(cursor
								.getColumnIndex("Value")));
					}
				} while (cursor.moveToNext());
			}
			calcBMI();
		} else {
			cursor = myHelper
					.getData("SELECT _ID,FK_Body,Value,Date,round((julianday(Date) - julianday('now') ),0)as dateDayDiff FROM t_history where FK_Body=(SELECT _ID from t_body where name='"
							+ dataSelect
							+ "')group by date ORDER BY DATE(Date) ASC ");
			if (cursor.moveToFirst()) {
				do {
					bodyDataList.add(cursor.getDouble(cursor
							.getColumnIndex("Value")));
					dateAsString.add(cursor.getString(cursor
							.getColumnIndex("Date")));
					dateDayDiff.add(cursor.getDouble(cursor
							.getColumnIndex("dateDayDiff")));

				} while (cursor.moveToNext());
			}
		}
		cursor.close();
		myHelper.close();
	}

	public List<Double> getBodyDataList() {
		return bodyDataList;
	}
	
	/**
	 * calcs the bmi in add the bmi the bmi list
	 */
	private void calcBMI() {
		for (int i = 0; i < bodyWeightList.size(); i++) {
			Double bmi = bodyWeightList.get(i)
					/ Math.pow((bodyHeightList.get(i) / 100), 2);
			bmi = Math.rint(bmi * 10) / 10;
			this.bmiList.add(bmi);
		}
	}

	public List<String> getDateAsString() {
		return dateAsString;
	}

	public List<Double> getDateDayDiff() {
		return dateDayDiff;
	}

	public List<Double> getBodyHeightList() {
		return bodyHeightList;
	}

	public List<Double> getBodyWeightList() {
		return bodyWeightList;
	}

	public List<Double> getBmiList() {
		return bmiList;
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
