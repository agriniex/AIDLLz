package com.teamharambe.agris.aidll;

import android.app.Application;
import android.content.Context;

public class AIDLL extends Application{

    private static Context context;

    public void onCreate() {
        super.onCreate();
        AIDLL.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AIDLL.context;
    }
}
