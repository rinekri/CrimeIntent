package com.example.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CrimeLab {
	private static final String TAG = "CrimeLab";
	private static final String FILE_NAME = "crimes.json";
	
	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	
	private ArrayList<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;
	
	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILE_NAME);
		
		try {
			mCrimes = mSerializer.loadCrimes();
			Log.d(TAG, "Crimes loaded from file");
		} catch (Exception ex) {
			mCrimes = new ArrayList<Crime>();
			Log.e(TAG, "Error loading crimes", ex);
		}
	}
	
	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		return sCrimeLab;
	}

	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}
	
	public Crime getCrime(UUID id) {
		for (Crime c : mCrimes) {
			if (c.getId().equals(id)) return c; 
		}
		return null;
	}
	
	public void addCrime(Crime c) {
		mCrimes.add(c);
	}
	
	public boolean saveCrimes() {
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILE_NAME);
		try {
			mSerializer.saveCrimes(getCrimes());
			Log.d(TAG, "Crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		}
	}
	
}
