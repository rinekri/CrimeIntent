package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CrimePagerActivity extends FragmentActivity {
	public static final String TAG = "CrimePagerActivity";
	
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
		
		UUID crimeId = (UUID) getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); i++) {
			String crimeTitle = mCrimes.get(i).getTitle();
			if (mCrimes.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				if (crimeTitle != null) setTitle(crimeTitle);
				break;
			}
		}
		
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				Crime crime = mCrimes.get(position);
				if (crime.getTitle() != null) CrimePagerActivity.this.setTitle(crime.getTitle());
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_crime_pager, menu);
		return true;
	}
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_delete_crime:
				int currentPositition = mViewPager.getCurrentItem();
				Log.d(TAG, "CurrentPosition: "+currentPositition);
				FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) mViewPager.getAdapter();
				Crime crime = mCrimes.get(currentPositition);
				CrimeLab.get(this).deleteCrime(crime);
				adapter.notifyDataSetChanged();
				if (NavUtils.getParentActivityName(this) != null) {
					NavUtils.navigateUpFromSameTask(this);
				}
//				int size = mViewPager.getAdapter().getCount();
//				Fragment page = adapter.getItem(currentPositition);


//				if ((currentPositition == 0) && (size > 0))  {
//					mViewPager.setCurrentItem(currentPositition+1);
//				}
//				adapter.destroyItem(mViewPager, currentPositition, page);

				return true;
			default: 
				return super.onOptionsItemSelected(item);
		}
	}
	
}
