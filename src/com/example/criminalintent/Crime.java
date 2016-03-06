package com.example.criminalintent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Crime {
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DATE = "date";
	private static final String JSON_SOLVED = "solved";
	
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSolved;
	
	
	public Crime() {
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	public Crime(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		try {
			mTitle = json.getString(JSON_TITLE);
		} catch (Exception ex) {
			mTitle = "";
		}
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

	public boolean isSolved() {
		return mSolved;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public void setSolved(boolean solved) {
		mSolved = solved;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, getId().toString());
		json.put(JSON_TITLE, getTitle());
		json.put(JSON_DATE, getDate().getTime());
		json.put(JSON_SOLVED, isSolved());
		return json;
	}
	
	
	
	@Override
	public String toString() {
		return mTitle;
	}
}
