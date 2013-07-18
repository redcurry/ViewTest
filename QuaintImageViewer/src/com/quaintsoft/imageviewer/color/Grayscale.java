package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;

public class Grayscale extends BitmapColorChangerByColorFilter {
	
	@Override
	protected ColorMatrix createColorMatrix() {
		ColorMatrix grayMatrix = new ColorMatrix();
		grayMatrix.setSaturation(0f);
		return grayMatrix;
	}

}
