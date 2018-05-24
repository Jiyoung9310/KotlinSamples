package jiyoung.example.kotlin.com.kotlinsamples.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by user on 2018-01-19.
 */

public class NetworkCheckReceiver extends BroadcastReceiver{
	public final static String NETWORK_WIFI_STATE		= "NETWORK_WIFI_STATE";
	public final static String NETWORK_MOBILE_STATE		= "NETWORK_MOBILE_STATE";

	public final static int WIFI_STATE_DISABLED         = 0x00;
	public final static int WIFI_STATE_DISABLING        = WIFI_STATE_DISABLED       + 1;
	public final static int WIFI_STATE_ENABLED          = WIFI_STATE_DISABLING      + 1;
	public final static int WIFI_STATE_ENABLING         = WIFI_STATE_ENABLED        + 1;
	public final static int WIFI_STATE_UNKNOWN          = WIFI_STATE_ENABLING       + 1;
	public final static int NETWORK_STATE_CONNECTED     = WIFI_STATE_UNKNOWN        + 1;
	public final static int NETWORK_STATE_CONNECTING    = NETWORK_STATE_CONNECTED   + 1;
	public final static int NETWORK_STATE_DISCONNECTED  = NETWORK_STATE_CONNECTING  + 1;
	public final static int NETWORK_STATE_DISCONNECTING = NETWORK_STATE_DISCONNECTED + 1;
	public final static int NETWORK_STATE_SUSPENDED     = NETWORK_STATE_DISCONNECTING + 1;
	public final static int NETWORK_STATE_UNKNOWN       = NETWORK_STATE_SUSPENDED   + 1;

	public interface onChangeNetworkStatusListener {
		public void onChanged(int status);
	}

	private WifiManager mWifiManager = null;
	private ConnectivityManager mConnManager = null;
	private onChangeNetworkStatusListener mListener = null;

	public NetworkCheckReceiver(Context context) {
		mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		mConnManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public void setOnChangeNetworkStatusListener (onChangeNetworkStatusListener listener) {
		mListener = listener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if(mListener == null) {
			return;
		}

		String strAction = intent.getAction();
		if (strAction.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			switch (mWifiManager.getWifiState()) {
				case WifiManager.WIFI_STATE_DISABLED:
					mListener.onChanged(WIFI_STATE_DISABLED);
					break;
				case WifiManager.WIFI_STATE_DISABLING:
					mListener.onChanged(WIFI_STATE_DISABLING);
					break;
				case WifiManager.WIFI_STATE_ENABLED:
					mListener.onChanged(WIFI_STATE_ENABLED);
					break;
				case WifiManager.WIFI_STATE_ENABLING:
					mListener.onChanged(WIFI_STATE_ENABLING);
					break;
				case WifiManager.WIFI_STATE_UNKNOWN:
					mListener.onChanged(WIFI_STATE_UNKNOWN);
					break;
			}
		} else if (strAction.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			NetworkInfo networkInfo = mConnManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (networkInfo != null && networkInfo.isAvailable()) {
				if(networkInfo.getState() == NetworkInfo.State.CONNECTED) {
					mListener.onChanged(NETWORK_STATE_CONNECTED);
				} else if(networkInfo.getState() == NetworkInfo.State.CONNECTING) {
					mListener.onChanged(NETWORK_STATE_CONNECTING);
				} else if(networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
					mListener.onChanged(NETWORK_STATE_DISCONNECTED);
				} else if(networkInfo.getState() == NetworkInfo.State.DISCONNECTING) {
					mListener.onChanged(NETWORK_STATE_DISCONNECTING);
				} else if(networkInfo.getState() == NetworkInfo.State.SUSPENDED) {
					mListener.onChanged(NETWORK_STATE_SUSPENDED);
				} else if(networkInfo.getState() == NetworkInfo.State.UNKNOWN) {
					mListener.onChanged(NETWORK_STATE_UNKNOWN);
				}
			} else {
				mListener.onChanged(NETWORK_STATE_UNKNOWN);
			}

		}
	}
}
