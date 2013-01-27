package aeist.mobile.ui;

import java.util.ArrayList;

import aeist.mobile.R;
import aeist.mobile.events.EventActivity;
import aeist.mobile.global.Variables;
import aeist.mobile.persistence.Event;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Event> events;
	private boolean isEven;
	private LayoutInflater inflater;
	
	
	public GridAdapter(Context applicationContext) {
		context = applicationContext;
		events = new ArrayList<Event>();
		inflater = LayoutInflater.from(applicationContext);
	}

	@Override
	public int getCount() {
		return events.size();
	}

	@Override
	public Object getItem(int position) {
		return events.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		WindowManager mWinMgr = (WindowManager)context.getApplicationContext()
									.getSystemService(Context.WINDOW_SERVICE);
		
		DisplayMetrics metrics = new DisplayMetrics();
		mWinMgr.getDefaultDisplay().getMetrics(metrics);
		//int width_pixels = metrics.widthPixels;		

		ViewHolder holder;	
		if(convertView==null) {
			convertView = inflater.inflate(R.layout.grid_element, null);
			convertView.setBackgroundResource(R.layout.round_corners);

			TextView title = (TextView)convertView.findViewById(R.id.eventTitle);
			title.setText(events.get(position).getName());
			title.setTextColor(R.color.RoyalBlue);				
			
			ImageView image = (ImageView)convertView.findViewById(R.id.eventImage);
			image.setPadding(Variables.IMAGE_LEFT_PADDING, Variables.IMAGE_TOP_PADDING,
							Variables.IMAGE_RIGHT_PADDING, Variables.IMAGE_BOTTOM_PADDING);
			
			final String eventName = events.get(position).getName();
			final String description = events.get(position).getDescription();
			final String facebookLink = events.get(position).getFacebookLink();
			
			image.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					final Intent intent = new Intent(context,EventActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					//TODO add event instead of string
					intent.putExtra(Variables.EVENT_NAME, eventName);
					intent.putExtra(Variables.EVENT_DESCRIPTION, description);
					intent.putExtra(Variables.EVENT_FACEBOOK_LINK, facebookLink);
					context.startActivity(intent);
				}
			});
			
			holder = new ViewHolder();
			holder.title = title;
			holder.image = (ImageView)convertView.findViewById(R.id.eventImage);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.title.setText(events.get(position).getName());
		holder.image.setImageResource(R.drawable.aeist_logo);
		
		return convertView;

	}

	public Context getContext() {
		return context;
	}
	
	public void addEvent(Event e) {
		this.events.add(e);
	}
	
	public void addEvents(ArrayList<Event> ev) {
		
		for(int i=0; i < ev.size(); i++) {
			addEvent(ev.get(i));
		}
	}

	public boolean isEven() {
		return isEven;
	}

	public void setEven(boolean isEven) {
		this.isEven = isEven;
	}
	
	private class ViewHolder {
		TextView title;
		ImageView image;
		//TextView description;
	}

}
