package at.fhjoanneum.ima.project.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBaseHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/at.fhjoanneum.ima.project.getfit/databases/";
	private static String DB_NAME = "getFit.sqlite";
	private static final int DATABASE_VERSION = 5;

	private static final String TAG_DB = "DBHelper";
	private static final String DATABASEHELPER_INSTANTIATED = "DataBaseHelper started; \n DATABASE PATH: "
			+ DB_PATH;
	private static final String DATABASEHELPER_OPEN_TRY_DB = "DataBaseHelper trys to open system DB!";
	private static final String DATABASEHELPER_OPEN_TRY_SUCCESS = "DBHelper could find and open SystemDatabase";
	private static final String DATABASE_EXISTS = "System Database was found";
	private static final String DATABASE_EXISTS_NOT = "No Database was found on the system";
	private static final String DATABASE_COPY_START = "New database is being copied to device!";
	private static final String DATABASE_COPY_FINISHED = "New database has been copied to device!";

	private static final String TAG_SQL = "SQL-Database";
	private static final String SQL_WRITEDATA_ERROR = "Error in INSERT/UPDATE Statement!";
	private static final String SQL_INSERT_BEGIN = "Starting Insertoperation!";
	private static final String SQL_TRANSACTION_START = "Begin Transaction dispatched";
	private static final String SQL_TRANSACTION_SUCCESSFUL = "No Errors in INSERT/UPDATE Statement";
	private static final String SQL_TRANSACTION_END = "End Transaction without any ERRORS!!!";
	private static final String DATABASEHELPER_OPEN_READWRITE = "Opened in READWRITE";
	private static final String DATABASEHELPER_OPEN_READONLY = "Opened in READONLY";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public MyDataBaseHelper(Context context) {

		super(context, DB_NAME, null, DATABASE_VERSION);
		this.myContext = context;
		Log.i(TAG_DB, DATABASEHELPER_INSTANTIATED);
	}

	/**
	 * Creates a empty database on the system and rewrites it with the
	 * getFit.sqlite database if it does not exist yet.
	 * */
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			Log.i(TAG_DB, DATABASE_EXISTS);
		}
		if (!dbExist) {
			Log.i(TAG_DB, DATABASE_EXISTS_NOT);
			this.getReadableDatabase();

			try {
				this.close();
				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Checks if the database already exists to avoid re-copying the file each
	 * time the application gets opened.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			Log.i(TAG_DB, DATABASEHELPER_OPEN_TRY_DB);
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			Log.i(TAG_DB, DATABASE_EXISTS_NOT);
		}

		if (checkDB != null) {
			checkDB.close();
			Log.i(TAG_DB, DATABASEHELPER_OPEN_TRY_SUCCESS);
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering via bytestream.
	 * */
	private void copyDataBase() throws IOException {

		Log.i(TAG_DB, DATABASE_COPY_START);
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

		Log.i(TAG_DB, DATABASE_COPY_FINISHED);

	}

	/**
	 * Opens the SystemDatabase with the given path and name of the Database.
	 * 
	 * @throws SQLException
	 */
	public void openDataBase(Boolean readWrite) throws SQLException {

		Log.i(TAG_DB, DATABASEHELPER_OPEN_TRY_DB);
		String myPath = DB_PATH + DB_NAME;

		if (readWrite) {
			myDataBase = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
			Log.i(TAG_DB, DATABASEHELPER_OPEN_READWRITE);
		} else {
			myDataBase = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
			Log.i(TAG_DB, DATABASEHELPER_OPEN_READONLY);
		}
		Log.i(TAG_DB, DATABASEHELPER_OPEN_TRY_SUCCESS);

	}

	/**
	 * Close the open DB-Connection
	 */
	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * Provides easy access to the Database for getting Data
	 * 
	 * @param selectStatement
	 *            Contains a SQL Query
	 * @return A cursor with the results of the SQL Query
	 */
	public Cursor getData(String selectStatement) {
		return myDataBase.rawQuery(selectStatement, null);
	}

	/**
	 * Is used for insert Statements where no values are returned.
	 * 
	 * @param values
	 *            Contain ContentValues (TableColumn, Value)
	 * @param tableName
	 *            Name of the Table
	 */

	public void writeData(ContentValues values, String tableName) {
		Log.i(TAG_SQL, SQL_INSERT_BEGIN);
		myDataBase.beginTransaction();
		Log.i(TAG_SQL, SQL_TRANSACTION_START);
		try {
			myDataBase.insert(tableName, null, values);
			myDataBase.setTransactionSuccessful();
			Log.i(TAG_SQL, SQL_TRANSACTION_SUCCESSFUL);
		} catch (SQLiteException sqlite) {
			Log.e(TAG_SQL, SQL_WRITEDATA_ERROR);
		} finally {
			myDataBase.endTransaction();
			Log.i(TAG_SQL, SQL_TRANSACTION_END);

		}

	}

	/**
	 * Is used for update Statements where no values are returned.
	 * 
	 * @param values
	 *            Contain ContentValues (TableColumn, Value)
	 * @param tableName
	 *            Name of the Table
	 */
	public void updateData(String tableName, ContentValues values,
			String whereClause) {
		myDataBase.beginTransaction();
		try {
			myDataBase.update(tableName, values, whereClause, null);
			myDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG_SQL, "ERROR");
		} finally {
			myDataBase.endTransaction();
		}

	}

	/**
	 * Is used for delete Statements where no values are returned.
	 * 
	 * @param tableName
	 *            Name of the Table
	 * @param whereClause
	 * 
	 */
	public void deleteData(String tableName, String whereClause) {
		myDataBase.beginTransaction();
		try {
			myDataBase.delete(tableName, whereClause, null);
			myDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e(TAG_SQL, "DeleteError");
		} finally {
			myDataBase.endTransaction();
		}
	}

	/**
	 * Is used for reload the default database from the assets folder
	 */
	public void wipeUser() {
		myContext.deleteDatabase(DB_NAME);
		try {
			createDataBase();
		} catch (IOException e) {
			Log.e(TAG_DB, "IOExecption");
		}

	}
}