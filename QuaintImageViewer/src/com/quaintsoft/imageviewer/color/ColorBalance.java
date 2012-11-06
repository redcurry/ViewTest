package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class ColorBalance extends BitmapColorChangerByColorFilter {

	private int red, green, blue;
	
	public ColorMatrixColorFilter colorFilter() {
		float[] colorMatrix = {
				1.0f, 0.0f, 0.0f, 0.0f, red,
				0.0f, 1.0f, 0.0f, 0.0f, green,
				0.0f, 0.0f, 1.0f, 0.0f, blue,
				0.0f, 0.0f, 0.0f, 1.0f, 0.0f };
		return new ColorMatrixColorFilter(new ColorMatrix(colorMatrix));
	}
	
	public void setRed(int r) {
		red = r;
	}
	
	public void setGreen(int g) {
		green = g;
	}
	
	public void setBlue(int b) {
		blue = b;
	}
	
}
