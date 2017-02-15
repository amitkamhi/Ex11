package com.example.kamhi.ex11.View;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kamhi on 24/1/2017.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext(){return context;}
}
