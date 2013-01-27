package aeist.mobile;

import java.util.ArrayList;

import aeist.mobile.global.Variables;
import aeist.mobile.persistence.ElementNotFoundException;
import aeist.mobile.persistence.Event;
import aeist.mobile.persistence.EventsDataSource;
import aeist.mobile.ui.GridAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * @since version 1.0
 * 
 * 
 * @author joaovasques
 *
 */
public class AEISTMobile extends FragmentActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    /** Database variables **/
    private EventsDataSource db;
    
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeistmobile);
        
        Bundle extras = getIntent().getExtras();
        
        if(extras!=null) {
        	
            ArrayList<Event> events =  (ArrayList<Event>)extras.get(Variables.EVENTS);
            
            if(events!=null)
            	saveEventsToDatabase(events);        	
        }
            
        // Create the adapter that will return a fragment for each of the primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }
    
    /**
     * @since version 1.0
     * @author joaovasques
     * 
     * Saves events on the database. Only the events
     * that are in the database are saved
     * 
     * @param {@link ArrayList<{@link Event}>} events list of events to be saved
     * 
     */
    private void saveEventsToDatabase(ArrayList<Event> events) {
		
    	db = new EventsDataSource(getApplicationContext());
		db.open();
		
		Log.i(Variables.INFO, "Save events to database");
		
		for(int i=0; i < events.size(); i++) {
			
			try {
				db.getEvent(events.get(i).getName());
			} catch (ElementNotFoundException e) {
				Log.i(Variables.INFO, "Event [" + events.get(i).getName() + "]" +
						" does not exist. Add it");
				byte[] dummyImage = new byte[2];
				events.get(i).setImage(dummyImage);
				db.createEvent(events.get(i).getName(), 
					events.get(i).getDescription(), 
					events.get(i).getFacebookLink(),
					events.get(i).getImage(),
					events.get(i).getTag());	
			}
		}
		
		db.close();      	
    }

    /**
     * Gets all events from the database 
     * @return list of events
     */
    private ArrayList<Event> getAllEvents() {
    	db = new EventsDataSource(getApplicationContext());    	
    	ArrayList<Event> lst = db.getAllEvents();
    	db.close();
    	return lst;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_aeistmobile, menu);
        return true;
    }


    /**
     * @since version 1.0
     * 
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     * 
     * @author joaovasques
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

    	
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        

        @Override
        public Fragment getItem(int i) {
        	SectionFragment fragment = new SectionFragment();
            Bundle args = new Bundle();
            args.putInt(SectionFragment.ARG_SECTION_NUMBER, i);
            
            switch (i) {
			case Variables.LAST_NEWS:
            	args.putSerializable(Variables.EVENTS, getAllEvents());
				break;

			default:
				break;
			}
            
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getString(R.string.ultimas_noticias).toUpperCase();
                case 1: return getString(R.string.recreativa).toUpperCase();
                case 2: return getString(R.string.desporto).toUpperCase();
                case 3: return getString(R.string.gefe).toUpperCase();
                case 4: return getString(R.string.politica_educativa).toUpperCase();
            }
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
    public static class SectionFragment extends Fragment {
    	
        public static final String ARG_SECTION_NUMBER = "section_number";
        
        public SectionFragment() {
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	
        }
        
        @SuppressWarnings("unchecked")
		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	int sectionNumber = getArguments().getInt(SectionFragment.ARG_SECTION_NUMBER);
        	
        	GridView grid = new GridView(getActivity());
        	grid.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.FILL_PARENT, 
        							GridView.LayoutParams.FILL_PARENT));
        	grid.setNumColumns(GridView.AUTO_FIT);
        	grid.setHorizontalSpacing(Variables.HORIZONTAL_SPACING);
        	grid.setVerticalSpacing(Variables.VERTICAL_SPACING);
        	grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        	grid.setBackgroundResource(R.drawable.black_wallpaper);
        	
        	
        	ArrayList<Event> evs = new ArrayList<Event>();
        	switch (sectionNumber) {
			case Variables.LAST_NEWS:
        		evs = (ArrayList<Event>) getArguments().getSerializable(Variables.EVENTS);
				break;

			default:
				evs.add(new Event("name-default", "description-default", "link-default", null));
				break;
			}        	
        	
        	GridAdapter adapter = new GridAdapter(getActivity().getApplicationContext());
        	adapter.setEven((sectionNumber%2) == 0);
        	adapter.addEvents(evs);
        	grid.setAdapter(adapter);
        	
        	return grid;
        	
        }
        
        @Override
        public void onPause() {
        	// TODO Auto-generated method stub
        	super.onPause();
        }
        
    }
    
    /**
     * Exit app when back button is pressed.
     * This is done to avoid going to the initial loading menu. 
     */
    
    @Override
    public void onBackPressed() {
    	Intent finish_intent = new Intent(this, FinishActivity.class);
    	finish_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(finish_intent);
    	finish();
    }
}
