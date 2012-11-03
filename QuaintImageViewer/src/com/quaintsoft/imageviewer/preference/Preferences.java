package com.quaintsoft.imageviewer.preference;

import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.quaintsoft.imageviewer.R;

public class Preferences extends PreferenceActivity {

	private abstract class ZoomPreferenceChangeListener implements OnPreferenceChangeListener {

		public boolean onPreferenceChange(Preference preference, Object newValue) {
			if (!isFloat(newValue)) {
				showErrorMessage(getResources().getString(R.string.pref_no_number_error));
				return false;
			}
			
			float newZoomValue = Float.valueOf((String)newValue);
			
			if (!isGreaterThanZero(newZoomValue)) {
				showErrorMessage(getResources().getString(R.string.pref_not_greater_than_zero_error));
				return false;
			}
			
			if (!isMaxZoomLessThanMinZoom(newZoomValue)) {
				showErrorMessage(getResources().getString(R.string.pref_max_less_than_min_error));
				return false;
			}
			
			return true;
		}
		
		private boolean isFloat(Object newValue) {
			try {
				Float.valueOf((String)newValue);
			} catch (NumberFormatException e) {
				return false;
			}
			
			return true;
		}
		
		private boolean isGreaterThanZero(float newValue) {
			return newValue > 0;
		}

		private void showErrorMessage(String message) {
			Toast errorMessage = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
			errorMessage.show();
		}
		
		protected abstract boolean isMaxZoomLessThanMinZoom(float zoomValue);

	}
	
	private class MaxZoomPreferenceChangeListener extends ZoomPreferenceChangeListener {

		@Override
		protected boolean isMaxZoomLessThanMinZoom(float maxZoomValue) {
			return maxZoomValue >= getMinZoomValue();
		}
		
	}
	
	private class MinZoomPreferenceChangeListener extends ZoomPreferenceChangeListener {

		@Override
		protected boolean isMaxZoomLessThanMinZoom(float minZoomValue) {
			return minZoomValue <= getMaxZoomValue();
		}
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		setupValidation();
	}
	
	private void setupValidation() {
		getMaxZoomPreference().setOnPreferenceChangeListener(new MaxZoomPreferenceChangeListener());
		getMinZoomPreference().setOnPreferenceChangeListener(new MinZoomPreferenceChangeListener());
	}
	
	private EditTextPreference getMaxZoomPreference() {
		return (EditTextPreference)findPreference(getMaxZoomKey());
	}
	
	private String getMaxZoomKey() {
		return getResources().getString(R.string.pref_max_zoom_option);
	}

	private EditTextPreference getMinZoomPreference() {
		return (EditTextPreference)findPreference(getMinZoomKey());
	}
	
	private CharSequence getMinZoomKey() {
		return getResources().getString(R.string.pref_min_zoom_option);
	}
	
	private float getMaxZoomValue() {
		return Float.valueOf(getMaxZoomPreference().getText());
	}

	private float getMinZoomValue() {
		return Float.valueOf(getMinZoomPreference().getText());
	}
	
	private Context getContext() {
		return this;
	}

}
