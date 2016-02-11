package com.example.criminalintent;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CrimeActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crime);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment crimeFragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (crimeFragment == null) {
			crimeFragment = new CrimeFragment();
			
			fm.beginTransaction().add(R.id.fragmentContainer, crimeFragment).commit();
			
		}
		
		
	}
}
