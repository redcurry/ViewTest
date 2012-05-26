package com.quaintsoft.imageviewer.preference;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.quaintsoft.imageviewer.R;

public class Preferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

}
