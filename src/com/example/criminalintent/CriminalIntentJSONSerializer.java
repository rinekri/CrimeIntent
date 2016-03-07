package com.example.criminalintent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

public class CriminalIntentJSONSerializer {
	private Context mContext;
	private String mFilename;
	
	public CriminalIntentJSONSerializer(Context c, String f) {
		mContext = c;
		mFilename = f;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException{
		JSONArray array = new JSONArray();
		for (Crime c : crimes) 
			array.put(c.toJSON());
		Writer writer = null;
		try {
			OutputStream out = null;
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
				out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			} else {
				if (Environment.isExternalStorageEmulated() == true) {
					File file = new File(mContext.getExternalFilesDir(null), mFilename);
					out = new FileOutputStream(file, false);
				} else {
					out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
				}
			}
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		try {
			InputStream in = null;
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
				in = mContext.openFileInput(mFilename);
			} else {
				if (Environment.isExternalStorageEmulated() == true) {
					File file = new File(mContext.getExternalFilesDir(null), mFilename);
					in = new FileInputStream(file);
				} else {
					in = mContext.openFileInput(mFilename);
				}
			}
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			for (int i = 0; i < array.length(); i++) {
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch(FileNotFoundException ex) {
			
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return crimes;
	}	
}
