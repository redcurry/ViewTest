package com.quaintsoft.imageviewer.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.quaintsoft.imageviewer.ImageViewModel;
import com.quaintsoft.imageviewer.ImageViewZoomer;
import com.quaintsoft.imageviewer.R;

public class PreferenceListener	implements OnSharedPreferenceChangeListener {

	private Context context;
	private ImageViewModel imageViewModel;
	private ImageViewZoomer imageViewZoomer;
	
	public PreferenceListener(Context context, ImageViewModel model, ImageViewZoomer zoomer) {
		this.context = context;
		imageViewModel = model;
		imageViewZoomer = zoomer;
		
		applyAllPreferences();
	}
	
	private void applyAllPreferences() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		for (String key : prefs.getAll().keySet())
			onSharedPreferenceChanged(prefs, key);
	}
	
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if (key.equals(getCenterPrefKey()))
			imageViewModel.setAlignCenter(prefs.getBoolean(key, getCenterDefault()));
		else if (key.equals(getAntialiasPrefKey()))
			imageViewModel.setAntialiasing(prefs.getBoolean(key, getAntialiasDefault()));
		else if (key.equals(getMaxZoomPrefKey()))
			imageViewZoomer.setMaxZoom(getPrefsFloat(prefs, key, getMaxZoomDefault()));
		else if (key.equals(getMinZoomPrefKey()))
			imageViewZoomer.setMinZoom(getPrefsFloat(prefs, key, getMinZoomDefault()));
	}
	
	private String getCenterPrefKey() {
		return getResourceString(R.string.pref_center_option);
	}
	
	private boolean getCenterDefault() {
		return Boolean.valueOf(getResourceString(R.string.pref_center_default));
	}
	
	private String getAntialiasPrefKey() {
		return getResourceString(R.string.pref_antialias_option);
	}
	
	private boolean getAntialiasDefault() {
		return Boolean.valueOf(getResourceString(R.string.pref_antialias_default));
	}
	
	private String getMaxZoomPrefKey() {
		return getResourceString(R.string.pref_max_zoom_option);
	}
	
	private float getPrefsFloat(SharedPreferences prefs, String key, String defaultValue) {
		return Float.valueOf(prefs.getString(key, defaultValue));
	}
	
	private String getMaxZoomDefault() {
		return getResourceString(R.string.pref_max_zoom_default);
	}
	
	private String getMinZoomPrefKey() {
		return getResourceString(R.string.pref_min_zoom_option);
	}
	
	private String getMinZoomDefault() {
		return getResourceString(R.string.pref_min_zoom_default);
	}
	
	private String getResourceString(int id) {
		return context.getResources().getString(id);
	}

}
