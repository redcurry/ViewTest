package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;

public class InvertColors extends BitmapColorChangerByColorFilter {
	
	@Override
	protected ColorMatrix createColorMatrix() {
		float invert [] = { 
			-1.0f,  0.0f,  0.0f, 1.0f, 0.0f,
			 0.0f, -1.0f,  0.0f, 1.0f, 0.0f,
			 0.0f,  0.0f, -1.0f, 1.0f, 0.0f,
			 1.0f,  1.0f,  1.0f, 1.0f, 0.0f };
		return new ColorMatrix(invert);
	}

}
