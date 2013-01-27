package aeist.mobile.persistence;

import java.util.ArrayList;

import aeist.mobile.global.Variables;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * @since version 1.0
 * 
 * 
 * @author joaovasques
 *
 */
public class EventsDataSource {

	private SQLiteDatabase database;
	private EventOpenHelper dbHelper;
	private String[] allColumns = {
			EventOpenHelper.ID ,
			EventOpenHelper.NAME, 
			EventOpenHelper.DESCRIPTION,
			EventOpenHelper.FACEBOOK_LINK, 
			EventOpenHelper.IMAGE, 
			EventOpenHelper.TAG
	};
	
	/**
	 * 
	 * @param context
	 */
	public EventsDataSource(Context context) {
		dbHelper = new EventOpenHelper(context);
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * 
	 */
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param facebook_link
	 * @param image
	 * @param tag
	 * @return {@link Event}
	 */
	public Event createEvent(String name, String description, String facebook_link,
							byte[] image, String tag) {
		
		database = dbHelper.getWritableDatabase();
		database.beginTransaction();
		ContentValues values = new ContentValues();
		values.put(EventOpenHelper.NAME, name);
		values.put(EventOpenHelper.DESCRIPTION, description);
		values.put(EventOpenHelper.FACEBOOK_LINK, facebook_link);
		values.put(EventOpenHelper.IMAGE, image);
		values.put(EventOpenHelper.TAG, tag);
		
		long insertId = database.insert(Variables.EVENT_TABLE, null, values);
		
		Cursor cursor = database.query(
				Variables.EVENT_TABLE,
		        allColumns, 
		        EventOpenHelper.ID + " = " + insertId,
		        null,
		        null, 
		        null, 
		        null
		);
		
		cursor.moveToFirst();
		Event event = cursorToEvent(cursor);
		cursor.close();
		database.endTransaction();
		
		return event;
	}	
	
	/**
	 * 
	 * @param name
	 * @return {@link Event}
	 * @throws ElementNotFoundException 
	 */
	public Event getEvent(String name) throws ElementNotFoundException {
		
		Event e = new Event();
		database = dbHelper.getReadableDatabase();
		database.beginTransaction();
		
	    Cursor cursor = database.query(Variables.EVENT_TABLE, 
	    		allColumns, EventOpenHelper.NAME + "=?",
	            new String[] { name }, null, null, null, null);
	    if (cursor != null) {
	    	cursor.moveToFirst();
	    	
	    	if(cursor.getCount() == 0)
	    		throw new ElementNotFoundException("Event " + name +" does not exists on database");
	    	else {
	    		e = cursorToEvent(cursor);
			    database.endTransaction();
			    database.close();
				return e;	
	    	}		    
	    }	       
	    else
	    	throw new ElementNotFoundException("Event " + name +" does not exists on database");
	}
	
	/**
	 * Deletes an event from the database given its name
	 * @param name name of the event
	 */
	public void deleteEvent(String name) {
		database = dbHelper.getWritableDatabase();
		database.beginTransaction();
		System.out.println("Event deleted with name: " + name);
		database.delete(Variables.EVENT_TABLE, EventOpenHelper.NAME
		        + " = " + name, null);
		database.endTransaction();
	}	

	/**
	 * 
	 * @return
	 */
	public ArrayList<Event> getAllEvents() {
		ArrayList<Event> events = new ArrayList<Event>();
		database = dbHelper.getReadableDatabase();
		database.beginTransaction();
		
		Cursor cursor = database.query(Variables.EVENT_TABLE,
		        allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Event event = cursorToEvent(cursor);
		    events.add(event);
		    cursor.moveToNext();
		}
		
		cursor.close();
		database.endTransaction();
		database.close();
		return events;
	}	  
	
	/**
	 * Converts info from a cursor to an event object
	 * @param cursor
	 * @return {@link Event} object
	 */
	private Event cursorToEvent(Cursor cursor) {
		Event event = new Event();
		event.setId(cursor.getLong(0));
		event.setName(cursor.getString(1));
		event.setDescription(cursor.getString(2));
		event.setFacebookLink(cursor.getString(3));
		event.setImage(cursor.getBlob(4));
		event.setTag(cursor.getString(5));
		
		return event;
	 }	  
	  
}
