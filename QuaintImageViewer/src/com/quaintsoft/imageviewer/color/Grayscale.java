package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class Grayscale extends AbstractColorChanger {
	
	@Override
	protected ColorMatrixColorFilter createColorFilter() {
		ColorMatrix grayMatrix = new ColorMatrix();
		grayMatrix.setSaturation(0f);
		return new ColorMatrixColorFilter(grayMatrix);
	}

}
