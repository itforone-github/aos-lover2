package util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by 투덜이2 on 2017-08-14.
 */

public class NetworkCheck {
    Activity act;
    Context context;
    boolean isBoolean=true;
    LinearLayout webLayout;
    RelativeLayout networkLayout;

    public void setNetworkLayout(RelativeLayout networkLayout) {
        this.networkLayout = networkLayout;
    }

    public void setWebLayout(LinearLayout webLayout) {
        this.webLayout = webLayout;
    }

    public NetworkCheck(Activity act, Context context){
        this.act=act;
        this.context=context;
    }
    public void networkCheck(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        act.registerReceiver(mReciver,intentFilter);
    }
    public void stopReciver(){
        try {
            act.unregisterReceiver(mReciver);
        }catch(Exception e){

        }
    }
    BroadcastReceiver mReciver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action=intent.getAction();

            if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                NetworkInfo mobile=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifi=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (activeNetwork != null) {
                    if (wifi.isConnected()||mobile.isConnected()) {
                        isBoolean=true;
                    }else{
                        isBoolean=false;
                    }
                }else{

                    isBoolean=false;
                }
            }else{

                Log.d("network","연결 안됨");
            }
            Log.d("network",isBoolean+"");
            if(isBoolean){
                webLayout.setVisibility(View.VISIBLE);
                networkLayout.setVisibility(View.GONE);
            }else{
                webLayout.setVisibility(View.GONE);
                networkLayout.setVisibility(View.VISIBLE);
            }
        }
    };
}
