package com.sarvex.buhee.ledger.item;

import com.sarvex.buhee.entry.item.Entry;

import java.util.List;

/**
 * Created by sarvex on 23/12/2016.
 */

public class Ledger {
  int image;
  String Name;
  String telephone;
  long balance;

  public Ledger(int image, String name, String telephone, long balance) {
    this.image = image;
    Name = name;
    this.telephone = telephone;
    this.balance = balance;
  }


  public int getImage() {
    return image;
  }

  public void setImage(int image) {
    this.image = image;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public long getBalance() {
    return balance;
  }

  public void setBalance(long balance) {
    this.balance = balance;
  }

  public List<Entry> getEntries() {
    return entries;
  }

  public void setEntries(List<Entry> entries) {
    this.entries = entries;
  }

  List<Entry> entries;
}
