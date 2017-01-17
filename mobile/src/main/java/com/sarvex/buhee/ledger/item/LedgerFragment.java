package com.sarvex.buhee.ledger.item;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sarvex.buhee.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LedgerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LedgerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LedgerFragment extends Fragment {

  @BindView(R.id.progress_bar) ProgressBar progressBar;
  @BindView(R.id.progress_text) TextView progressText;
  @BindView(R.id.ledger_recycler_view) RecyclerView recyclerView;
  private LedgerAdapter adapter;
  private List<Ledger> ledgers;

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  public LedgerFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment LedgerFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static LedgerFragment newInstance(String param1, String param2) {
    LedgerFragment fragment = new LedgerFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // TODO dummy data
    ledgers = new ArrayList<>(10);
    ledgers.add(new Ledger(R.drawable.ic_tv_dark, "Ledger 1", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_light, "Ledger 2", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_dark, "Ledger 3", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_light, "Ledger 4", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_dark, "Ledger 5", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_light, "Ledger 6", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_dark, "Ledger 7", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_light, "Ledger 8", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_dark, "Ledger 9", "+91-8866442200", 154));
    ledgers.add(new Ledger(R.drawable.ic_tv_light, "Ledger 0", "+91-8866442200", 154));


    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_ledger, container, false);
    ButterKnife.bind(this, view);
    load();

    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);

    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_ledger, container, false);
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  private void load() {
    showProgress();
    hideProgress();
  }

  protected void hideProgress() {
    progressBar.setIndeterminate(false);
    progressBar.setVisibility(View.INVISIBLE);
    progressText.setVisibility(View.INVISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  protected void showProgress() {
    progressBar.setIndeterminate(true);
    progressBar.setVisibility(View.VISIBLE);
    progressText.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.INVISIBLE);
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }
}
