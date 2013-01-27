package aeist.mobile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONException;

import aeist.mobile.exceptions.NoResultFromServerException;
import aeist.mobile.global.Variables;
import aeist.mobile.network.ServerCommunication;
import aeist.mobile.persistence.Event;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * @since version 1.0
 * 
 * 
 * @author joaovasques
 *
 */
public class LaunchingPageActivity extends Activity {
	
	private static final int LOAD_CONTENT = 0;
	private ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Hide header and set background image
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
        	WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        
		setContentView(R.layout.launching);
		
		Setup setup = new Setup();
		setup.execute();
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch (id) {
		case LOAD_CONTENT:
			progress = new ProgressDialog(this);
			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progress.setIndeterminate(true);
			progress.setCancelable(true);
			progress.show();
			return progress;

		default:
			return null;
		}		
	}
	
	/**
	 * @since version 1.0
	 * 
	 * 
	 * @author joaovasques
	 *
	 */
	private class Setup extends AsyncTask<Void, Integer, ArrayList<Event>>
	{
		private boolean wifi_enabled;
		
		protected void onPreExecute() {
			
			boolean internet_connection = ServerCommunication.hasInternetConnection(getApplicationContext());
			if(internet_connection==false)
				wifi_enabled = false;
			else
				wifi_enabled = true;
		}

		@Override
		protected ArrayList<Event> doInBackground(Void... params) {
			
			ArrayList<Event> events = new ArrayList<Event>();
			
			if(!wifi_enabled)
				return events;

			try {
				LinkedList<String> events_names = ServerCommunication.getAllEventsName();
				
				Iterator<String> iter = events_names.iterator();
				while(iter.hasNext())
				{
					String event_name = iter.next();
					//TODO check cache
					Event event = ServerCommunication.getEventInformation(event_name);
					events.add(event);
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (NoResultFromServerException e) {
				e.printStackTrace();
			}

			return events;
		}
		
		@Override
		protected void onPostExecute(ArrayList<Event> events) {		

			final Intent intent = new Intent(LaunchingPageActivity.this, AEISTMobile.class);
			
			if(!wifi_enabled)
				intent.putExtra(Variables.INTERNET_CONNECTION, 
								Variables.NO_INTERNET_CONNECTION);
				
			if(events.size() > 0)
				intent.putExtra(Variables.EVENTS, events);
			
	        startActivity(intent);	
			
		}
		
	}

}
