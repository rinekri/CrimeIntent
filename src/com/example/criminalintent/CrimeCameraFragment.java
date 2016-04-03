package com.example.criminalintent;

import java.io.IOException;
import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment {
	public static final String TAG = "CrimeCameraFragment";
	
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private Button mTakePictureButton;
	
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedFragmentState) {
		View v = layoutInflater.inflate(R.layout.fragment_crime_camera, parent, false);
		
		mSurfaceView = (SurfaceView) v.findViewById(R.id.crime_camera_surfaceView);
		
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					if(mCamera != null) {
						mCamera.setPreviewDisplay(holder);
					}
				}catch(IOException ex) {
					Log.e(TAG, "Error setting up preview display", ex);
				}
			}
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if(mCamera != null) {
					mCamera.stopPreview();
				}
			}
			

			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				if(mCamera == null) return;
				
				Camera.Parameters parameters = mCamera.getParameters();
				Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(s.width, s.height);
				mCamera.setParameters(parameters);
				try {
					mCamera.startPreview();
				}catch(Exception ex) {
					Log.e(TAG, "Couldn't start preview", ex);
					mCamera.release();
					mCamera = null;
				}
			}
		});
		
		
		mTakePictureButton = (Button) v.findViewById(R.id.crime_camera_takePictureButton);
		mTakePictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();
				
			}
		});
		
		return v;
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			mCamera = Camera.open(0);
		} else {
			mCamera = Camera.open();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}
	
	private Size getBestSupportedSize(List<Size> sizes, int width, int height) {
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		for (Size s : sizes) {
			int area = s.width * s.height;
			if(area > largestArea) {
				bestSize = s;
				largestArea = area;
			}
		}
		return bestSize;
	}

}
