package com.quaintsoft.imageviewer.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.quaintsoft.imageviewer.ImageViewModel;
import com.quaintsoft.imageviewer.R;

public class PreferenceListener	implements OnSharedPreferenceChangeListener {

	private Context context;
	private ImageViewModel imageViewModel;
	
	public PreferenceListener(Context ctx, ImageViewModel model) {
		context = ctx;
		imageViewModel = model;
		
		applyAllPreferences();
	}
	
	private void applyAllPreferences() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		for (String key : prefs.getAll().keySet())
			onSharedPreferenceChanged(prefs, key);
	}
	
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (key.equals(getCenterPrefKey()))
			imageViewModel.setAlignCenter(prefs.getBoolean(key, true));
		else if (key.equals(getAntialiasPrefKey()))
			imageViewModel.setAntialiasing(prefs.getBoolean(key, true));
	}
	
	private String getCenterPrefKey() {
		return getResourceString(R.string.pref_center_option);
	}
	
	private String getAntialiasPrefKey() {
		return getResourceString(R.string.pref_antialias_option);
	}
	
	private String getResourceString(int id) {
		return context.getResources().getString(id);
	}

}
