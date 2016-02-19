package com.example.criminalintent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

public class DateAndTimeFragment extends DialogFragment {
	public static final String EXTRA_DATE_AND_TIME = "com.example.criminalintent.extra_date_and_time";
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_TIME = "time";
	private static final int REQUEST_DATE = 0;	
	private static final int REQUEST_TIME = 1;	
	
	private Date mDate;
	private Button mDateButton;
	private Button mTimeButton;
	private Calendar mCrimeCalendar; 


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date) getArguments().getSerializable(EXTRA_DATE_AND_TIME);
		mCrimeCalendar = Calendar.getInstance();
		mCrimeCalendar.setTime(mDate);

		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date_time, null);
		
		mDateButton = (Button) v.findViewById(R.id.crime_date);
		updateDate(mCrimeCalendar.getTime());
		mDateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment.newInstance(mCrimeCalendar.getTime());
				dialog.setTargetFragment(DateAndTimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
			
		});
		
		mTimeButton = (Button) v.findViewById(R.id.crime_time);
		updateTime(mCrimeCalendar.getTime());
		mTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				TimePickerFragment dialog = TimePickerFragment.newInstance(mCrimeCalendar.getTime());
				dialog.setTargetFragment(DateAndTimeFragment.this, REQUEST_TIME);
				dialog.show(fm, DIALOG_TIME);
				
			}
		});
		
		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle(R.string.crime_date_and_time_title)
				.create();
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != Activity.RESULT_OK) return;
		Calendar calendar = new GregorianCalendar();
		if(requestCode == REQUEST_DATE) {
			Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			calendar.setTime(date);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			mCrimeCalendar.set(year, month, day);
			updateDate(mCrimeCalendar.getTime());
		}
		if(requestCode == REQUEST_TIME) {
			Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
			calendar.setTime(time);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			mCrimeCalendar.set(Calendar.HOUR_OF_DAY, hour);
			mCrimeCalendar.set(Calendar.MINUTE, minute);
			updateTime(mCrimeCalendar.getTime());
		}
		mDate = (mCrimeCalendar.getTime());
		getArguments().putSerializable(EXTRA_DATE_AND_TIME, mDate);
		sendResult(Activity.RESULT_OK);
	}
	

	public static DateAndTimeFragment newInstance(Date crimeDate) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE_AND_TIME, crimeDate);
		DateAndTimeFragment fragment = new DateAndTimeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null) {
			return;
		}
		
		Intent i = new Intent();
		i.putExtra(EXTRA_DATE_AND_TIME, mCrimeCalendar.getTime());
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	
	private void updateDate(Date setDate) {
		String formatedString = new SimpleDateFormat("d MMM yyyy, EEEE", Locale.getDefault()).format(setDate);
		mDateButton.setText(formatedString);
	}
	
	private void updateTime(Date setTime) {
		String formatedString = DateFormat.getTimeInstance(DateFormat.SHORT,Locale.getDefault()).format(setTime);
		mTimeButton.setText(formatedString);
	}
	
}
