package com.sarvex.buhee;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by sarvex on 19/12/16.
 */

public class MainApplication extends Application {

  private RefWatcher refWatcher;

  public static RefWatcher getRefWatcher(Context context) {
    MainApplication application = (MainApplication) context.getApplicationContext();
    return application.refWatcher;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return;
    }

    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
    refWatcher = LeakCanary.install(this);

    Iconics.init(this);
    Iconics.registerFont(new GoogleMaterial());
    Iconics.registerFont(new FontAwesome());
  }
}
