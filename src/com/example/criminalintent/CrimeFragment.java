package com.example.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CrimeFragment extends Fragment {
	public static final String TAG = "CrimeFragment";
	public static final String EXTRA_CRIME_ID = "com.example.criminalintent.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_IMAGE = "image";
	private static final int REQUEST_DATE = 0;	
	private static final int REQUEST_PHOTO = 1;	
	
	
	private Crime mCrime;
	private EditText mEditText;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	private ImageButton mOpenCameraImageButton;
	private ImageView mPhotoView;
	
	@Override
	public void onCreate(Bundle savedFragmentState) {
		super.onCreate(savedFragmentState);
		setHasOptionsMenu(true);
		
		UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		CrimeLab.get(getActivity()).saveCrimes();
	}
	
	@Override 
	public void onStart() {
		super.onStart();
		showPhoto();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		PictureUtils.cleanImageVIew(mPhotoView);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedFragmentState) {
		View view = layoutInflater.inflate(R.layout.fragment_crime, parent, false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if(NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		mOpenCameraImageButton = (ImageButton) view.findViewById(R.id.crime_imageButton);
		mOpenCameraImageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
				startActivityForResult(i, REQUEST_PHOTO);
			}
		});
		
		PackageManager pm = getActivity().getPackageManager();
		if ((!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) 
				&& (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT))) {
			mOpenCameraImageButton.setEnabled(false);
		}
		
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
		updateDate(mCrime.getDate());
		mDateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
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
		
		mPhotoView = (ImageView) view.findViewById(R.id.crime_imageView);
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Photo p = mCrime.getPhoto();
				if (p == null) return;
				
				FragmentManager manager = getActivity().getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getPhotoName()).getAbsolutePath();
				ImageFragment.newInstance(path).show(manager, DIALOG_IMAGE);
			}
		});
		
		return view;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
				return true;
			default: 
				return super.onOptionsItemSelected(item);
		}	
	}
	
	public static Fragment setInstance(UUID id) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, id);
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != Activity.RESULT_OK) return;
		if(requestCode == REQUEST_DATE) {
			Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
			updateDate(mCrime.getDate());
		}
		
		if(requestCode == REQUEST_PHOTO) {
			String filename = (String) data.getSerializableExtra(CrimeCameraFragment.EXTRA_PHOTO_NAME);
			if (filename != null) {
				mCrime.setPhoto(new Photo(filename));
				showPhoto();
			}
		}
	}
	
	private void updateDate(Date setdate) {
		String formatedString = new SimpleDateFormat("d MMM yyyy, EEEE", Locale.getDefault()).format(setdate);
		mDateButton.setText(formatedString);
	}
	
	private void showPhoto() {
		Photo p = mCrime.getPhoto();
		BitmapDrawable b = null;
		
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getPhotoName()).getAbsolutePath();
			b = PictureUtils.getSelectedDrawable(getActivity(), path);
		}
		
		mPhotoView.setImageDrawable(b);
	}
	
}
