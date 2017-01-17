package com.sarvex.buhee.ledger.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sarvex.buhee.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sarvex on 23/12/2016.
 */

public class LedgerAdapter extends RecyclerView.Adapter<LedgerAdapter.ViewHolder> {
  public final List<Ledger> ledgers;

  public LedgerAdapter(List<Ledger> ledgers) {
    this.ledgers = ledgers;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ledger, parent, false));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(ledgers.get(position));
  }

  @Override
  public int getItemCount() {
    return ledgers.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.telephone) TextView telephone;
    @BindView(R.id.balance) TextView balance;


    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    public void bind(Ledger ledger) {
      image.setImageResource(ledger.getImage());
      name.setText(ledger.getName());
      telephone.setText(ledger.getTelephone());
      balance.setText(String.valueOf(ledger.getBalance()));

    }
  }
}
