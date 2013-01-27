package aeist.mobile.global;

/**
 * @since version 1.0
 * @author joaovasques
 *
 */
public class Variables {
	
	/** Project name **/
	private static final String APP_NAME = "[AEIST-Mobile]: ";
	
	
	/** Debug levels **/
	public static final String INFO = APP_NAME + "INFO";
	
	/** Activities extras names **/
	public static final String EVENTS = "events";
	
	/** Internet connection **/
	public static final String INTERNET_CONNECTION = "Net";
	public static final int NO_INTERNET_CONNECTION = 0;
	
	/** Sections numbers **/
	public static final int LAST_NEWS = 0;
	public static final int RECREATIVA = 1;
	public static final int DESPORTO = 2;
	public static final int GEFE = 3;
	public static final int PE = 4;
	
	/** SQLite database **/
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "aeistmobile.db";
	public static final String EVENT_TABLE = "Events";
	
	/** General grid **/
	public static final int NUMBER_OF_COLUMNS= 1;
	public static final double GRID_PROPORTION = 0.55;
	public static final int HORIZONTAL_SPACING = 15;
	public static final int VERTICAL_SPACING = 15;
	
	/** Grid elements **/
	public static final int IMAGE_TOP_PADDING = 10;
	public static final int IMAGE_BOTTOM_PADDING = 10;
	public static final int IMAGE_LEFT_PADDING = 10;
	public static final int IMAGE_RIGHT_PADDING = 10;
	
	/** Event information **/
	public static final String EVENT_DEFAULT_DESCRIPTION = "Lorem Ipsum is simply dummy text of the" +
			" printing and typesetting industry. " +
			"Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
			"when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
			"It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged";
	
	public static final String EVENT_NAME = "NAME";
	public static final String EVENT_DESCRIPTION = "DESCRIPTION";
	public static final String EVENT_FACEBOOK_LINK = "FACEBOOK";
	public static final int EVENT_DESCRIPTION_TOP_PADDING = 10;
	public static final int EVENT_DESCRIPTION_BOTTOM_PADDING = 10;
	public static final int EVENT_DESCRIPTION_LEFT_PADDING = 10;
	public static final int EVENT_DESCRIPTION_RIGHT_PADDING = 10;
}
