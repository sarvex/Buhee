package com.sarvex.buhee.main;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.Iconics;
import com.optimizely.Optimizely;
import com.sarvex.buhee.BuildConfig;
import com.sarvex.buhee.R;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

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

    TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
    Fabric.with(this, new Crashlytics(), new Twitter(authConfig), new Answers());
    Optimizely.startOptimizelyWithAPIToken(getString(R.string.com_optimizely_api_key), this);
  }
}
