package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;

public class ColorBalance extends BitmapColorChangerByColorFilter {

	private int red, green, blue;
	
	public void setRed(int r) {
		red = r;
	}

	public void setGreen(int g) {
		green = g;
	}

	public void setBlue(int b) {
		blue = b;
	}

	@Override
	protected ColorMatrix createColorMatrix() {
		float[] colorMatrix = {
			1f, 0f, 0f, 0f, red,
			0f, 1f, 0f, 0f, green,
			0f, 0f, 1f, 0f, blue,
			0f, 0f, 0f, 1f, 0f };
		return new ColorMatrix(colorMatrix);
	}
	
}
