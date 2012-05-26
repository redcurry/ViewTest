package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class Brightness extends AbstractColorChanger {

	private int brightness = 0;
	
	@Override
	protected ColorMatrixColorFilter createColorFilter() {
		float filter [] = { 
			1.0f, 0.0f, 0.0f, 0.0f, brightness,
			0.0f, 1.0f, 0.0f, 0.0f, brightness,
			0.0f, 0.0f, 1.0f, 0.0f, brightness,
			0.0f, 0.0f, 0.0f, 1.0f, 0.0f };
		ColorMatrix colorMatrix = new ColorMatrix(filter);
		return new ColorMatrixColorFilter(colorMatrix);
	}
	
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

}
