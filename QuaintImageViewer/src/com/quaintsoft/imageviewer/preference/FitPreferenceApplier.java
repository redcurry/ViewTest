package com.quaintsoft.imageviewer.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.quaintsoft.imageviewer.ImageViewZoomer;
import com.quaintsoft.imageviewer.R;

public class FitPreferenceApplier {
	
	private Context context;
	private ImageViewZoomer imageViewZoomer;
	
	public FitPreferenceApplier(Context ctx, ImageViewZoomer zoomer) {
		context = ctx;
		imageViewZoomer = zoomer;
	}
	
	public void apply() {
		String fitPref = getFitPref();
		String[] allFitPrefs = getAllFitPrefs();
		if (fitPref.equals(allFitPrefs[0]))
			imageViewZoomer.zoomImageToActualSize();
		else if (fitPref.equals(allFitPrefs[1]))
			imageViewZoomer.fitImageToView();
		else if (fitPref.equals(allFitPrefs[2]))
			imageViewZoomer.fitImageToViewIfLarge();
		else if (fitPref.equals(allFitPrefs[3]))
			imageViewZoomer.fitImageWidthToView();
		else if (fitPref.equals(allFitPrefs[4]))
			imageViewZoomer.fitImageHeightToView();
	}
	
	private String getFitPref() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString(getFitPrefKey(), getDefaultFitPref());
	}

	private String getFitPrefKey() {
		return getResourceString(R.string.pref_list_fit_selected_option);
	}

	private String getDefaultFitPref() {
		return getResourceString(R.string.pref_list_fit_default_value);
	}

	private String getResourceString(int id) {
		return context.getResources().getString(id);
	}
	
	private String[] getAllFitPrefs() {
		return getResourceStringArray(R.array.fit_options);
	}

	private String[] getResourceStringArray(int id) {
		return context.getResources().getStringArray(id);
	}

}
