package com.sarvex.buhee.utility;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarvex on 20/12/16.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
  private final List<Fragment> fragments = new ArrayList<>();
  private final List<String> titles = new ArrayList<>();

  public PagerAdapter(FragmentManager fragmentManager) {
    super(fragmentManager);
  }

  public void addFragment(Fragment fragment, String title) {
    fragments.add(fragment);
    titles.add(title);
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    return fragments.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return titles.get(position);
  }
}
