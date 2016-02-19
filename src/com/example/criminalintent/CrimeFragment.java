package com.example.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	public static final String EXTRA_CRIME_ID = "com.example.criminalintent.crime_id";
	private static final String DIALOG_DATE_AND_TIME = "date_and_time";
	private static final int REQUEST_DATE_AND_TIME = 0;
	
	private Crime mCrime;
	private EditText mEditText;
	private Button mDateTimeButton;
	private CheckBox mSolvedCheckBox;
	
	@Override
	public void onCreate(Bundle savedFragmentState) {
		super.onCreate(savedFragmentState);
		
		UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedFragmentState) {
		View view = layoutInflater.inflate(R.layout.fragment_crime, parent, false);
		
		mEditText = (EditText) view.findViewById(R.id.crime_title);
		mEditText.setText(mCrime.getTitle());
		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mCrime.setTitle(s.toString());
			}
		});
		
		mDateTimeButton = (Button) view.findViewById(R.id.crime_date_and_time);
//		updateDate(mCrimeCalendar.getTime());
		mDateTimeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DateAndTimeFragment dialog = DateAndTimeFragment.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE_AND_TIME);
				dialog.show(fm, DIALOG_DATE_AND_TIME);
			}
			
		});	
				
		mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mCrime.setSolved(isChecked);
			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != Activity.RESULT_OK) return;
		if(requestCode == REQUEST_DATE_AND_TIME) {
			Date d = (Date) data.getSerializableExtra(DateAndTimeFragment.EXTRA_DATE_AND_TIME); 
			mCrime.setDate(d);
		}
	}
	
	public static Fragment setInstance(UUID id) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, id);
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	

}
