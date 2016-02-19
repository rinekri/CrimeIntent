package com.example.criminalintent;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {
	public static final String EXTRA_TIME = "com.example.criminalintent.extra_time";
	private Date mDate;
	private Calendar mCalendar;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDate = (Date) getArguments().getSerializable(EXTRA_TIME);
		
		mCalendar = Calendar.getInstance();
		mCalendar.setTime(mDate);
		int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = mCalendar.get(Calendar.MINUTE);
		
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		
		TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mCalendar.set(Calendar.MINUTE, minute);
				mDate = mCalendar.getTime();
			}
		});
		
		return new AlertDialog.Builder(getActivity())
				.setView(v)
				.setTitle(R.string.time_picker_title)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {		
						sendResult(Activity.RESULT_OK);
					}
				})
				.create();
	}
	
	public static TimePickerFragment newInstance(Date crimeDate) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TIME, crimeDate);
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	private void sendResult(int resultCode) {
	
		if (getTargetFragment() == null) {
			return;
		}
		
		Intent i = new Intent();
		i.putExtra(EXTRA_TIME, mDate);
		
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
		
	}
	
}
