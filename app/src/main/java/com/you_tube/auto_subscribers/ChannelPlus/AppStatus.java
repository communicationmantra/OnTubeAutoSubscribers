package com.you_tube.auto_subscribers.ChannelPlus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class AppStatus {
    static Context context;
    private static AppStatus instance = new AppStatus();
    boolean connected = false;
    ConnectivityManager connectivityManager;
    NetworkInfo mobileInfo;
    NetworkInfo wifiInfo;

    public static AppStatus getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline() {
        try {
            this.connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo networkInfo = this.connectivityManager.getActiveNetworkInfo();
            boolean z = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            this.connected = z;
            return this.connected;
        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
            return this.connected;
        }
    }
}
