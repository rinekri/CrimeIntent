package com.example.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {
	private static final String JSON_PHOTO_NAME = "photo_name";
	private static final String JSON_PHOTO_ORIENTATION = "photo_orientation";
	private String mPhotoName;
	private String mPhotoOrientation;

	public Photo(String photoName, String photoOrient) {
		mPhotoName = photoName;
		mPhotoOrientation = photoOrient;
	}
	
	public Photo(JSONObject json) throws JSONException {
		mPhotoName = json.getString(JSON_PHOTO_NAME);
		mPhotoOrientation = json.getString(JSON_PHOTO_ORIENTATION);
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_PHOTO_NAME, mPhotoName);
		json.put(JSON_PHOTO_ORIENTATION, mPhotoOrientation);
		return json;
	}

	public String getPhotoName() {
		return mPhotoName;
	}

	public String getOrientation() {
		return mPhotoOrientation;
	}

	public void setOrientation(String orientation) {
		mPhotoOrientation = orientation;
	}	
}
