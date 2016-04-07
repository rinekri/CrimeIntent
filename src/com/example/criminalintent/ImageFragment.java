package com.example.criminalintent;

import java.io.File;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends DialogFragment {
	public static final String EXTRA_IMAGE_PATH = "com.example.criminalintent.image_patch";
	public static final String EXTRA_IMAGE_ORIENTATION = "com.example.criminalintent.image_orientation";
	private ImageView mImageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		mImageView = new ImageView(getActivity());
		String path = (String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
		File pathFile = new File(path);
		if(pathFile.exists()) {
			String orientation = (String) getArguments().getSerializable(EXTRA_IMAGE_ORIENTATION);
			BitmapDrawable image = PictureUtils.getSelectedDrawable(getActivity(), path, orientation);
			mImageView.setImageDrawable(image);
		}
		
		return mImageView;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		PictureUtils.cleanImageVIew(mImageView);
	}
	
	
	public static ImageFragment newInstance(String imagePath, String imageOrientation) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
		args.putSerializable(EXTRA_IMAGE_ORIENTATION, imageOrientation);
		
		ImageFragment fragment = new ImageFragment();
		fragment.setArguments(args);
		fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		
		return fragment;
	}
}
