package com.sarvex.buhee.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.sarvex.buhee.R;
import com.sarvex.buhee.entry.AddEntryActivity;
import com.sarvex.buhee.entry.RemoveEntryActivity;
import com.sarvex.buhee.entry.SettleEntryActivity;
import com.sarvex.buhee.ledger.AddLedgerActivity;
import com.sarvex.buhee.ledger.RemoveLedgerActivity;
import com.sarvex.buhee.ledger.SettleLedgerActivity;
import com.sarvex.buhee.login.LoginActivity;
import com.sarvex.buhee.login.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.tabs) TabLayout tabLayout;
  @BindView(R.id.add_button) FloatingActionButton addButton;
  @BindView(R.id.sub_button) FloatingActionButton subButton;
  @BindView(R.id.ok_button) FloatingActionButton okButton;

  private AccountHeader accountHeader;
  private Drawer drawer;
  private ShareActionProvider shareActionProvider;

  // Used to load the 'native-lib' library on application startup.
  static {
    System.loadLibrary("native-lib");
  }

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
    super.onCreate(savedInstanceState);

    Icepick.restoreInstanceState(this, savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setupToolbar();

    setupDrawer();

    setupViewPager();

    setupCollapsingToolbar();

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

  private void setupToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setDisplayShowTitleEnabled(false);
    }
  }

  private void setupDrawer() {

    String username = "";
    String email = "";

    accountHeader = new AccountHeaderBuilder()
        .withActivity(this)
        .withHeaderBackground(R.drawable.header)
        .addProfiles(new ProfileDrawerItem().withName(username).withEmail(email))
        .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
          @Override
          public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
            return false;
          }
        })
        .build();

    drawer = new DrawerBuilder()
        .withActivity(this)
        .withToolbar(toolbar)
        .withAccountHeader(accountHeader)
        .withHeader(R.layout.header)
        .addDrawerItems(
            new PrimaryDrawerItem().withName("Order T-Shirts").withIcon(Icon.gmd_local_florist),
            new PrimaryDrawerItem().withName("Profile").withIcon(Icon.gmd_person)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                  @Override
                  public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                    toProfileActivity();
                    return true;
                  }
                }),
            new PrimaryDrawerItem().withName("Settings").withIcon(Icon.gmd_settings),
            new PrimaryDrawerItem().withName("Logout").withIcon(FontAwesome.Icon.faw_sign_out)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                  @Override
                  public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                  }
                }))
        .build();

    drawer.getRecyclerView().setVerticalScrollBarEnabled(false);
  }

  private void setupViewPager() {
    if (viewPager != null) {
      PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
      viewPager.setAdapter(adapter);
    }

    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        super.onTabSelected(tab);
        switch (tab.getText().toString()) {
          case MainActivity.HOME:
            addButton.hide();
            break;
          case MainActivity.REFERRALS:
            addButton.show();
            break;
          case MainActivity.TEAM:
            addButton.show();
            break;
          case MainActivity.VIDEOS:
            addButton.hide();
            break;
        }
      }

      @Override
      public void onTabUnselected(Tab tab) {
        super.onTabUnselected(tab);
      }

      @Override
      public void onTabReselected(Tab tab) {
        super.onTabReselected(tab);
      }
    });
  }

  private void setupCollapsingToolbar() {
    CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

    collapsingToolbar.setTitleEnabled(false);
    collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.primary));
    collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.primary));
  }

  protected void toLoginActivity() {
    startActivity(new Intent(this, LoginActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  private void toProfileActivity() {
    startActivity(new Intent(this, ProfileActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @OnClick(R.id.add_button)
  public void onAddButtonClicked(View view) {
    switch (viewPager.getCurrentItem()) {
      case 1:
        startActivityForResult(new Intent(this, AddLedgerActivity.class), MainActivity.ADD_LEDGER);
        break;
      default:
        startActivityForResult(new Intent(this, AddEntryActivity.class), MainActivity.ADD_ENTRY);
        break;
    }
  }

  @OnClick(R.id.ok_button)
  public void onOkButtonClicked(View view) {
    switch (viewPager.getCurrentItem()) {
      case 1:
        startActivityForResult(new Intent(this, SettleLedgerActivity.class), MainActivity.SETTLE_LEDGER);
        break;
      default:
        startActivityForResult(new Intent(this, SettleEntryActivity.class), MainActivity.SETTLE_ENTRY);
        break;
    }
  }

  @OnClick(R.id.add_button)
  public void onAddButtonClicked(View view) {
    switch (viewPager.getCurrentItem()) {
      case 1:
        startActivityForResult(new Intent(this, RemoveLedgerActivity.class), MainActivity.REMOVE_LEDGER);
        break;
      default:
        startActivityForResult(new Intent(this, RemoveEntryActivity.class), MainActivity.REMOVE_ENTRY);
        break;
    }
  }

}
