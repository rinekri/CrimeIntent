package com.example.criminalintent;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CrimePagerActivity extends FragmentActivity {
	private ViewPager mViewPager; 

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
	}
}
