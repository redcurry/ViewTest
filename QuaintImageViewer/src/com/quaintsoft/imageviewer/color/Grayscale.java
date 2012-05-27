package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class Grayscale extends BitmapColorChangerByColorFilter {
	
	public ColorMatrixColorFilter colorFilter() {
		ColorMatrix grayMatrix = new ColorMatrix();
		grayMatrix.setSaturation(0f);
		return new ColorMatrixColorFilter(grayMatrix);
	}

}
