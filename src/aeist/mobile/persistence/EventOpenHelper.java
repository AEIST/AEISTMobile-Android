package aeist.mobile.persistence;

import aeist.mobile.global.Variables;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @since version 1.0
 * @author joaovasques
 *
 */
public class EventOpenHelper extends SQLiteOpenHelper{

	/** Event variables **/
	public static final String ID = "id";
	public static final String NAME ="name";
	public static final String DESCRIPTION = "description";
	public static final String FACEBOOK_LINK = "link";
	public static final String IMAGE = "image";
	public static final String TAG = "tag";
	
	/** SQL database create command **/
	private static final String DATABASE_CREATE =
             "CREATE TABLE " + Variables.EVENT_TABLE + " (" +
             ID + " integer primary key autoincrement, " +
             NAME + " text not null, " +
             DESCRIPTION + " text not null, " +
             FACEBOOK_LINK + " text not null, " +
             IMAGE + " blob, " +
             TAG + " text"+
             		");";
	
	public EventOpenHelper(Context context) {
		super(context, Variables.DATABASE_NAME, null, Variables.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(EventOpenHelper.class.getName(), DATABASE_CREATE);
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(EventOpenHelper.class.getName(), "Upgrading database from version " 
				+ oldVersion + " to " + 
				newVersion + ", which will destroy all old data");	
		db.execSQL("DROP TABLE IF EXISTS " + Variables.EVENT_TABLE);
		onCreate(db);
	}

}
