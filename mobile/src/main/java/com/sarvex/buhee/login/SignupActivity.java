package com.sarvex.buhee.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.sarvex.buhee.R;

import butterknife.ButterKnife;
import icepick.Icepick;

public class SignupActivity extends AppCompatActivity {

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Icepick.saveInstanceState(this, outState);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Icepick.restoreInstanceState(this, savedInstanceState);
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);

    setContentView(R.layout.activity_signup);
  }
}
