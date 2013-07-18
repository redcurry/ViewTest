package com.quaintsoft.imageviewer.color;

import android.graphics.ColorMatrix;

public class BrightnessContrast extends BitmapColorChangerByColorFilter {
	
	private int brightness = 0;
	private int contrast = 0;
	
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	
	public void setContrast(int contrast) {
		this.contrast = contrast;
	}
	
	@Override
	protected ColorMatrix createColorMatrix() {
		float scale = (1f + contrast / 127f);
		float shift = brightness - contrast;
		float[] colorMatrix = {
			scale,  0f,    0f,    0f, shift,
			0f,     scale, 0f,    0f, shift,
			0f,     0f,    scale, 0f, shift,
			0f,     0f,    0f,    1f, 0f };
		return new ColorMatrix(colorMatrix);
	}
	
}
