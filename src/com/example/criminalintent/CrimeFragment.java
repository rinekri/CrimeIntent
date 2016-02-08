package com.example.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class CrimeFragment extends Fragment {
	
	Crime mCrime;
	
	public void onCreate(Bundle savedFragmentState) {
		super.onCreate(savedFragmentState);
		mCrime = new Crime();
		
	}
	
}
