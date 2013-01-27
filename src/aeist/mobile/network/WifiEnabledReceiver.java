package aeist.mobile.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WifiEnabledReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		int state = intent.getIntExtra(WifiManager.WIFI_STATE_CHANGED_ACTION, -1);
		Toast.makeText(context, "WIFI CHANGED", Toast.LENGTH_LONG);
		/*if (extras != null) {
			String previousState = extras.getString("previous_wifi_state");
			String currentState = extras.getString("wifi_state");
			int a =1;
			
		}*/
	}

}
