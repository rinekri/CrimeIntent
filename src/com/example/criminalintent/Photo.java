package com.example.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {
	private static final String JSON_PHOTONAME = "photoname";
	private String mPhotoName;

	public Photo(String photoName) {
		super();
		mPhotoName = photoName;
	}
	
	public Photo(JSONObject json) throws JSONException {
		mPhotoName = json.getString(JSON_PHOTONAME);
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_PHOTONAME, mPhotoName);
		return json;
	}

	public String getPhotoName() {
		return mPhotoName;
	}	
}
