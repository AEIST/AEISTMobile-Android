package aeist.mobile.events;

import aeist.mobile.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @since version 1.0
 * 
 * Web view that presents the event's Facebook page
 * 
 * @author joaovasques
 *
 */
public class FacebookActivity extends Activity {
	
	private static final String DEFAULT_URL = "http://www.facebook.com/AEISTecnico";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook_event_page);
		setupFacebookPage(DEFAULT_URL);
	}
	
	private void setupFacebookPage(String link) {
		WebView webView = (WebView)findViewById(R.id.FacebookEventPage);
		webView.setWebViewClient(new WebClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(link);
	}
	
	
	 private class WebClient extends WebViewClient{  

         @Override
         public boolean shouldOverrideUrlLoading(WebView view, String url) {
             return (false);
         }

     }

}
