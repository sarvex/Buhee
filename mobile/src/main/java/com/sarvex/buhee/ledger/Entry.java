package com.sarvex.buhee.ledger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sarvex on 20/12/16.
 */

public class Entry implements Parcelable {
  public static final Creator<Entry> CREATOR = new Creator<Entry>() {
    @Override
    public Entry createFromParcel(Parcel in) {
      return new Entry(in);
    }

    @Override
    public Entry[] newArray(int size) {
      return new Entry[size];
    }
  };

  protected Entry(Parcel in) {
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
  }
}
