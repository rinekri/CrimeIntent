package com.example.criminalintent;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CrimePagerActivity extends FragmentActivity {
	private ViewPager mViewPager; 
	private ArrayList<Crime> mCrimes;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		
		mCrimes = CrimeLab.get(this).getCrimes();
		
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public Fragment getItem(int position) {
				Crime c = mCrimes.get(position);
				return CrimeFragment.setInstance(c.getId());
			}

			@Override
			public int getCount() {
				return mCrimes.size();
			}
			
		});
	}
}
