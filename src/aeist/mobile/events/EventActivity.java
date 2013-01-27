package aeist.mobile.events;


import aeist.mobile.R;
import aeist.mobile.R.color;
import aeist.mobile.global.Variables;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends Activity {

	/**
	 * Event attributes
	 */
	
	private String name;
	private String description;
	private String facebookLink;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.name = getIntent().getExtras().getString(Variables.EVENT_NAME);
		this.description = getIntent().getExtras().getString(Variables.EVENT_DESCRIPTION);
		this.facebookLink = getIntent().getExtras().getString(Variables.EVENT_FACEBOOK_LINK);
		
		final Window window = getWindow();
		boolean useTitleFeature = false;
		// If the window has a container, then we are not free
		// to request window features.
		if (window.getContainer() == null) {
		    useTitleFeature = window
		        .requestFeature(Window.FEATURE_CUSTOM_TITLE);
		}
				
		//Create layout to add event information elements
		LinearLayout layout = setupEventInfoLayout();	
		setContentView(layout);
		
		if (useTitleFeature) {
		    window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		        R.layout.event_title);
		}
		
		setupTitle();
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	/**
	 * 
	 */
	private void setupTitle() {
		TextView title = (TextView)findViewById(R.id.EventTitleName);
		title.setText(this.name);
		
		TextView facebook = (TextView)findViewById(R.id.EventTitleFacebook);
		final String fb = this.facebookLink;
		facebook.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), FacebookActivity.class);
				intent.putExtra(Variables.EVENT_FACEBOOK_LINK, fb);
				startActivity(intent);
			}
		});
		
		TextView share = (TextView)findViewById(R.id.EventTitleShare);
//		share.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(EventActivity.this, "TO IMPLEMENT!", Toast.LENGTH_LONG);
//			}
//		});
		
		TextView map = (TextView)findViewById(R.id.EventTitleMap);
//		map.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(EventActivity.this, "TO IMPLEMENT!", Toast.LENGTH_LONG);
//			}
//		});
	}
	
	/**
	 * 
	 * @return
	 */
	private LinearLayout setupEventInfoLayout() {
		
		
		//Create layout to add event information elements
		LinearLayout layout = new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(color.DarkGrey);
		
		//Get screen size information
		WindowManager mWinMgr = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);

		DisplayMetrics metrics = new DisplayMetrics();
		mWinMgr.getDefaultDisplay().getMetrics(metrics);
		float height_pixels = metrics.heightPixels;
			
		//Event image element
		ImageView eventImage = new ImageView(getApplicationContext());
		int imageHeight = (int) (height_pixels * 0.45);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, imageHeight);
		eventImage.setLayoutParams(params);
		eventImage.setBackgroundResource(R.drawable.aeist_logo); //TODO default image			
		layout.addView(eventImage);
		
		//Event description element
		int textHeight = (int) (height_pixels - imageHeight);
		WebView eventDescription = new WebView(getApplicationContext());
		eventDescription.setVerticalScrollBarEnabled(false);
		eventDescription.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, textHeight));
		eventDescription.setPadding(
				Variables.EVENT_DESCRIPTION_LEFT_PADDING,
				Variables.EVENT_DESCRIPTION_TOP_PADDING,
				Variables.EVENT_DESCRIPTION_RIGHT_PADDING,
				Variables.EVENT_DESCRIPTION_BOTTOM_PADDING);
		
		eventDescription.loadData(createDescriptionContent(Variables.EVENT_DEFAULT_DESCRIPTION), "text/html", "utf-8");
		eventDescription.setBackgroundColor(00000000);
		layout.addView(eventDescription);
		
		return layout;
	}	
	
	private String createDescriptionContent(String description) {
		StringBuilder descBuilder = new StringBuilder();
		
		descBuilder.append(
				"<body style=\"text-align:justify;color:white;\">" +
				description +
				"</body>");
		
		return descBuilder.toString();
	}
}
