package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class InvertColors extends AbstractColorChanger {
	
	@Override
	protected ColorMatrixColorFilter createColorFilter() {
		float invert [] = { 
			-1.0f,  0.0f,  0.0f, 1.0f, 0.0f,
			 0.0f, -1.0f,  0.0f, 1.0f, 0.0f,
			 0.0f,  0.0f, -1.0f, 1.0f, 0.0f,
			 1.0f,  1.0f,  1.0f, 1.0f, 0.0f };
		ColorMatrix invertMatrix = new ColorMatrix(invert);
		return new ColorMatrixColorFilter(invertMatrix);
	}

}
