package com.example.criminalintent;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

public class CrimeListFragment extends ListFragment {
	ArrayList<Crime> crimes;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
        crimes = CrimeLab.get(getContext()).getCrimes();
	}
}
