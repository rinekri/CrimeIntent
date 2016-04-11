package com.example.criminalintent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DATE = "date";
	private static final String JSON_PHOTO = "photo";
	private static final String JSON_SOLVED = "solved";
	private static final String JSON_SUSPECT = "suspect";
	private static final String JSON_SUSPECT_TELEPHONE = "suspect_telephone";
	
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	private Photo mPhoto;
	private String mSuspect;
	private String mSuspectTelephone;
	
	
	public Crime() {
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	public Crime(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		if(json.has(JSON_TITLE)) {
			mTitle = json.getString(JSON_TITLE);
		} else {
			mTitle = "";
		}
		if (json.has(JSON_PHOTO)) mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
		if (json.has(JSON_SUSPECT)) mSuspect = json.getString(JSON_SUSPECT);
		if (json.has(JSON_SUSPECT_TELEPHONE)) mSuspectTelephone = json.getString(JSON_SUSPECT_TELEPHONE);
		
		mDate = new Date(json.getLong(JSON_DATE));
		mSolved = json.getBoolean(JSON_SOLVED);
	}

	public UUID getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Date getDate() {
		return mDate;
	}
	
	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isSolved() {
		return mSolved;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}
	
	public Photo getPhoto() {
		return mPhoto;
	}

	public void setPhoto(Photo photo) {
		mPhoto = photo;
	}
	
	public String getSuspect() {
		return mSuspect;
	}

	public void setSuspect(String suspect) {
		mSuspect = suspect;
	}
	
	public String getSuspectTelephone() {
		return mSuspectTelephone;
	}

	public void setSuspectTelephone(String suspectTelephone) {
		mSuspectTelephone = suspectTelephone;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, getId().toString());
		json.put(JSON_TITLE, getTitle());
		json.put(JSON_DATE, getDate().getTime());
		if (mPhoto != null) json.put(JSON_PHOTO, mPhoto.toJSON());
		if (mSuspect != null) json.put(JSON_SUSPECT, mSuspect);
		if (mSuspectTelephone != null) json.put(JSON_SUSPECT_TELEPHONE, mSuspectTelephone);
		json.put(JSON_SOLVED, isSolved());
		return json;
	}
	
	@Override
	public String toString() {
		return mTitle;
	}
}
