package com.example.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	
	private Crime mCrime;
	private EditText mEditText;
	private Button mDateButton;
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
		
		mDateButton = (Button) view.findViewById(R.id.crime_date);
		String formatedString = new SimpleDateFormat("d MMM yyyy, EEEE", Locale.getDefault()).format(mCrime.getDate());
		mDateButton.setText(formatedString);
		mDateButton.setEnabled(false);
		
		
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
	
	public static Fragment setInstance(UUID id) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, id);
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		return fragment;
	}
}
