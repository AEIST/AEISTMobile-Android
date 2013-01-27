/*package aeist.mobile.network;

import org.json.JSONException;
import org.json.JSONObject;

import aeist.mobile.R;
import aeist.mobile.tabs.TabHostProvider;
import aeist.mobile.tabs.TabView;
import aeist.mobile.tabs.TabbarView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;

public class FacebookActivity extends Activity{

	private TabHostProvider tabProvider;
	private TabView tabView;
	private WebView facebook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		tabProvider = new TabbarView(this);
		JSONObject ob = new JSONObject();
		
		String url = "https://www.facebook.com/AEISTecnico";
		
		// If there are extras, then the activity was launched from an event and not from the navigation bar		
		if(getIntent().getExtras()!=null)
		{
			// Gets event information
			String eventName = getIntent().getExtras().getString("Nome");
			String eventDescription = getIntent().getExtras().getString("Descricao");
			String facebook_link = getIntent().getExtras().getString("FacebookLink");
			url = facebook_link;
			byte[] event_image = getIntent().getExtras().getByteArray("Imagem");
			
			try {
				ob.put("Nome", eventName);
				ob.put("Descricao", eventDescription);
				ob.put("FacebookLink", facebook_link);
				ob.put("Imagem", event_image);
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		}
		tabView = tabProvider.getTabHost("main",ob);
		tabView.setCurrentView(R.layout.facebook_page);		
		setContentView(tabView.render(TabbarView.FACEBOOK));
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
		
		facebook = (WebView)findViewById(R.id.FacebookPage);
		
	    // Web content
		facebook.getSettings().setJavaScriptEnabled(true);
		facebook.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		facebook.getSettings().setPluginsEnabled(true);
		facebook.getSettings().setSupportMultipleWindows(false);
		facebook.getSettings().setSupportZoom(false);
		facebook.setVerticalScrollBarEnabled(false);
		facebook.setHorizontalScrollBarEnabled(false);
		facebook.getSettings().setPluginState(PluginState.ON);
		facebook.setWebViewClient(new FacebookWebViewClient());
		facebook.getSettings().setDomStorageEnabled(true);
		facebook.loadUrl(url);

	}

	private class FacebookWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }	    
	    
	}
	
}
*/