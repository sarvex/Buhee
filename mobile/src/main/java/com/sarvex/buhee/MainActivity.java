package com.sarvex.buhee;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.optimizely.Optimizely;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

  // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
  private static final String TWITTER_KEY = " yphxIgeBD2sEa1eZpeKkU29Gk";
  private static final String TWITTER_SECRET = " sVHomqlCw9O9OLhIakpVpEjN7AaT0gYxzZ2dJuj9vXL90FGALm";


  // Used to load the 'native-lib' library on application startup.
  static {
    System.loadLibrary("native-lib");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
    Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
    Optimizely.startOptimizelyWithAPIToken(getString(R.string.com_optimizely_api_key), getApplication());
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /**
   * A native method that is implemented by the 'native-lib' native library,
   * which is packaged with this application.
   */
  public native String stringFromJNI();
}
