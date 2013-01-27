package aeist.mobile.network;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import aeist.mobile.exceptions.NoResultFromServerException;
import aeist.mobile.persistence.Event;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Base64;

/**
 * @since version 1.0
 * 
 * TODO: description
 * 
 * @author joaovasques
 *
 */
public class ServerCommunication {
	
	public static final String SERVER_ADDR="http://aeistmobile.appspot.com/";
	public static final String GET_EVENT_INFO_ADDR=SERVER_ADDR+"geteventinfo";
	public static final String GET_ALL_EVENTS_INFO=SERVER_ADDR+"getallevents.Get";
	public static final String GET_ALL_EVENTS_NAMES = SERVER_ADDR + "getalleventsnames";
	public static final String GET_EVENT_IMAGE = SERVER_ADDR+"getEventImage";
	
	// Request types
	public static final int GET_NON_IMAGE_CONTENT=1;
	public static final int GET_IMAGE_CONTENT=3;
	
	//private static final int DEFAULT_RESPONSE_SIZE = 5000;
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasInternetConnection(Context context)
	{
		boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	
	/**
	 * 
	 * @param context
	 */
	public static void enableWifi(Context context)
	{
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
		wifiManager.setWifiEnabled(true);
	}
	
	/**
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws JSONException
	 * @throws NoResultFromServerException 
	 */
	public static LinkedList<String> getAllEventsName() throws MalformedURLException, IOException, JSONException
, NoResultFromServerException
	{
		JSONObject result=null;
		result = communication(GET_ALL_EVENTS_NAMES, null,GET_NON_IMAGE_CONTENT);
		
		if(result==null)
			return null;
		
		JSONArray events;
		try {
			events = result.getJSONArray("events_names");
		} catch (JSONException e1) {
			e1.printStackTrace();
			return null;
		}
		
		LinkedList<String> eventsName = new LinkedList<String>();
		
		for(int i=0; i<events.length(); i++)
		{
			JSONObject e;
			try {
				e = events.getJSONObject(i);
				eventsName.add(e.getString("name"));
			} catch (JSONException e1) {
				e1.printStackTrace();
				return null;
			}
		}
		
		return eventsName;
		
	}
	
	/**
	 * 
	 * @param event_name
	 * @return
	 * @throws JSONException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NoResultFromServerException 
	 */
	public static Event getEventInformation(String event_name) throws JSONException, MalformedURLException, IOException, NoResultFromServerException
	{
		JSONObject arguments = new JSONObject();
		JSONObject result=null;
		
		// get event information = {description, Facebook link, image_key}
		arguments.put("name", event_name);		
		result = communication(GET_EVENT_INFO_ADDR, arguments,GET_NON_IMAGE_CONTENT);
		
		String name = result.getString("name");
		String description = result.getString("description");
		String facebook_link =  result.getString("facebook_link");
		String image_key = ""; //result.getString("image_key");
		
		// TODO get event picture
/*		arguments.remove("event_name");
		arguments.put("key", image_key);
		result = communication(GET_EVENT_IMAGE, arguments,GET_IMAGE_CONTENT);
		String image_base_64 = result.getString("image");*/
		byte[] image = null;//Base64.decode(image_base_64, Base64.DEFAULT);
		
		Event eventInfo = new Event(name, description, facebook_link, image);
		return eventInfo;
	}

	/**
	 * Sends requests to the remote BackOffice server
	 * @param request_addr URL of the servlet to make the request
	 * @param request request arguments
	 * @param request_type Identifies the type of request: non-image request or image request
	 * @return JSON object with response
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws JSONException
	 * @throws NoResultFromServerException 
	 */
	public static JSONObject communication(String request_addr, JSONObject request, int request_type) throws MalformedURLException, IOException, JSONException, NoResultFromServerException
	{
		
		//TODO authentication
		
		JSONObject server_response = null;

		switch (request_type) {
		
		case GET_IMAGE_CONTENT:
			
			// adds arguments to the given URL
			String image_key = request.getString("key");
			String query = String.format("?key=%s", image_key);
			String url = request_addr+query;
			
			// Prepares to setup connection
			HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();			
			c.setRequestMethod("GET");
			c.setDoOutput(true);
			c.setRequestProperty("Content-Type", "image/jpeg");
			c.setDoInput(true);
			c.connect();
				
			// Reads image from the server into byteArrayOut
			BufferedInputStream in = new BufferedInputStream(c.getInputStream());
		    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		       int count;
		       while ((count = in.read()) != -1) {
		         byteArrayOut.write(count);
		       }
			
			String b64_image = Base64.encodeToString(byteArrayOut.toByteArray()/*image*/, Base64.DEFAULT);
			server_response = new JSONObject().put("image", b64_image);
			c.disconnect();
			break;

		case GET_NON_IMAGE_CONTENT:
			
			
			 // Create a new HttpClient and Post Header
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpParams myParams = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		    HttpConnectionParams.setSoTimeout(myParams, 10000);
		    
		    String parameters = null;
		    
			if(request!=null)
			{
				String event_name = request.getString("name");
				parameters = String.format("?name=%s",event_name);
				StringBuilder b = new StringBuilder(request_addr);
				b.append(parameters);
				request_addr = b.toString();
				request_addr = request_addr.replace(" ", "_");
			}
		    
		    HttpGet get = new HttpGet(request_addr);

	        HttpResponse response = httpclient.execute(get);
	        String temp = EntityUtils.toString(response.getEntity());    	        
	        server_response = new JSONObject(temp);
	        			
		default:
			break;
		}
		
		if(server_response==null)
		{
			String msg = null;
			if(request_type == GET_IMAGE_CONTENT)
				msg = "GET_IMAGE_CONTENT";
			else
				msg = "GET_NON_IMAGE_CONTENT";
			
			throw new NoResultFromServerException(msg);
		}

		
		return server_response;
	}
	
}
