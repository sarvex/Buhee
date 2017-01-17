package com.sarvex.buhee.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.sarvex.buhee.R;
import com.sarvex.buhee.entry.AddActivity;
import com.sarvex.buhee.entry.RemoveActivity;
import com.sarvex.buhee.entry.item.EntryDetailFragment;
import com.sarvex.buhee.entry.item.EntryFragment;
import com.sarvex.buhee.ledger.SettleActivity;
import com.sarvex.buhee.ledger.item.LedgerDetailFragment;
import com.sarvex.buhee.ledger.item.LedgerFragment;
import com.sarvex.buhee.login.LoginActivity;
import com.sarvex.buhee.login.ProfileActivity;
import com.sarvex.buhee.utility.PagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;

public class MainActivity extends AppCompatActivity implements LedgerFragment.OnFragmentInteractionListener,
    LedgerDetailFragment.OnFragmentInteractionListener, EntryFragment.OnFragmentInteractionListener,
    EntryDetailFragment.OnFragmentInteractionListener {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.viewpager) ViewPager viewPager;
  @BindView(R.id.add_button) FloatingActionButton addButton;
  @BindView(R.id.sub_button) FloatingActionButton subButton;
  @BindView(R.id.ok_button) FloatingActionButton okButton;

  private AccountHeader accountHeader;
  private Drawer drawer;
  private ShareActionProvider shareActionProvider;

  private LedgerFragment ledgerFragment;
  private LedgerDetailFragment ledgerDetailFragment;
  private EntryFragment entryFragment;
  private EntryDetailFragment entryDetailFragment;

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

    setupCollapsingToolbar();

    setupViewPager();

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);

    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    MenuItem searchItem = menu.findItem(R.drawable.ic_search);
    SearchView searchView = null;
    if (searchItem != null) {
      searchView = (SearchView) searchItem.getActionView();
    }
    if (searchView != null) {
      searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    MenuItem shareItem = menu.findItem(R.drawable.ic_share);
    shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
    shareActionProvider.setShareIntent(doShare());
    return super.onCreateOptionsMenu(menu);
  }

  private Intent doShare() {
    return null;
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
//  public native String stringFromJNI();

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
        .withTranslucentStatusBar(true)
        .withHeader(R.layout.header)
        .addDrawerItems(
            new PrimaryDrawerItem().withName("Order T-Shirts").withIcon(GoogleMaterial.Icon.gmd_local_florist),
            new PrimaryDrawerItem().withName("Profile").withIcon(GoogleMaterial.Icon.gmd_person)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                  toProfileActivity();
                  return true;
                }),
            new PrimaryDrawerItem().withName("Settings").withIcon(GoogleMaterial.Icon.gmd_settings),
            new PrimaryDrawerItem().withName("Logout").withIcon(FontAwesome.Icon.faw_sign_out))
        .build();

    drawer.getRecyclerView().setVerticalScrollBarEnabled(false);
  }



  private void setupCollapsingToolbar() {
    CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);

    collapsingToolbar.setTitleEnabled(false);
    collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.primary));
    collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.primary));
  }

  private void setupViewPager() {
    if (viewPager != null) {
      PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

      ledgerFragment = new LedgerFragment();
      adapter.addFragment(ledgerFragment, "Ledger");
      ledgerDetailFragment = new LedgerDetailFragment();
      adapter.addFragment(ledgerDetailFragment, "Ledger Detail");
      entryFragment = new EntryFragment();
      adapter.addFragment(entryFragment, "Entry");
      entryDetailFragment = new EntryDetailFragment();
      adapter.addFragment(entryDetailFragment, "Entry Detail");

      viewPager.setAdapter(adapter);
    }
  }

  protected void toLoginActivity() {
    startActivity(new Intent(this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  private void toProfileActivity() {
    startActivity(new Intent(this, ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @OnClick(R.id.add_button)
  public void onAddButtonClicked(View view) {
    startActivity(new Intent(this, AddActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @OnClick(R.id.ok_button)
  public void onOkButtonClicked(View view) {
    startActivity(new Intent(this, SettleActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @OnClick(R.id.sub_button)
  public void onSubButtonClicked(View view) {
    startActivity(new Intent(this, RemoveActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }
}
