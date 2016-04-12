package com.example.criminalintent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {
	private static final String TAG = "CrimeListFragment";
	
	private ArrayList<Crime> mCrimes;
	private boolean mSubtitleVisible;
	private Callbacks mCallbacks;
	
	public interface Callbacks {
		void onCrimeSelected(Crime crime);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		mSubtitleVisible = false;
		
		getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedFragmentState) {
		
		View v = super.onCreateView(layoutInflater, parent, savedFragmentState);
		
		if (mSubtitleVisible) {
			getActivity().getActionBar().setSubtitle(R.string.subtitle);
		}
		
		ListView listView = (ListView) v.findViewById(android.R.id.list);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			//Context menu for API < 11
			registerForContextMenu(listView);
		} else {
			//Context action panel for API > 11 
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
				
				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					// We don't use it
					return false;
				}
				
				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO Auto-generated method stub
					// We don't use it
				}
				
				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.crime_list_item_context, menu);
					return true;
				}
				
				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					switch(item.getItemId()) {
						case R.id.menu_item_delete_crime:
							CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
							CrimeLab crimeLab = CrimeLab.get(getActivity());
							for(int i = adapter.getCount() - 1; i >= 0; i--) {
								if (getListView().isItemChecked(i)) {
									crimeLab.deleteCrime(adapter.getItem(i));
								}
							}
							mode.finish();
							return true;
						default:
							return false;
					}
				}
				
				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
					// TODO Auto-generated method stub
					// We don't use it
				}
			});
		}
		return v;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallbacks = (Callbacks) activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
		mCallbacks.onCrimeSelected(c);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if (mSubtitleVisible && showSubtitle != null) {
			showSubtitle.setTitle(R.string.hide_subtitle);
		}
	}
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_new_crime:
				Crime crime = new Crime();
				CrimeLab.get(getActivity()).addCrime(crime);
				((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
				mCallbacks.onCrimeSelected(crime);
				return true;
			case R.id.menu_item_show_subtitle:
				if (getActivity().getActionBar().getSubtitle() != null) {
					getActivity().getActionBar().setSubtitle(null);
					item.setTitle(R.string.show_subtitle);
					mSubtitleVisible = false;
				} else {
					getActivity().getActionBar().setSubtitle(R.string.subtitle);
					item.setTitle(R.string.hide_subtitle);
					mSubtitleVisible = true;
				}
				return true;
			default: 
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int crimePosition = info.position;
		CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
		Crime crime = adapter.getItem(crimePosition);
		
		switch(item.getItemId()) {
			case R.id.menu_item_delete_crime:
				CrimeLab.get(getActivity()).deleteCrime(crime);
				adapter.notifyDataSetChanged();
				return true;
		}
		return super.onContextItemSelected(item);
	}
	
	public class CrimeAdapter extends ArrayAdapter<Crime> {

		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}
			
			Crime c = getItem(position);
			
			TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			
			TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
			String formatedDate = new SimpleDateFormat("d MMM yyyy, EEEE", Locale.getDefault()).format(c.getDate());
			dateTextView.setText(formatedDate);
			
			CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			
			return convertView;
		}
	}
	
	public void updateUI() {
		((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
	}
}
