package com.example.criminalintent;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new CrimeListFragment();
	}
	
	@Override
	protected int getLayoutResuorceId() {
		return R.layout.activity_twopane;
	}
}
