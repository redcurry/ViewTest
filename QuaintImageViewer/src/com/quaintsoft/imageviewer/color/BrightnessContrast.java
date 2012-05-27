package com.quaintsoft.imageviewer.color;

public class BrightnessContrast extends BitmapColorChangerByPixelFunction {

	private int brightness = 0;
	private int contrast = 0;
	
	public int pixelFunction(int i) {
		if (contrast > 0)
			return clamp(clamp((i - contrast) * 255.0f / (255 - 2 * contrast)) + brightness);
		else
			return clamp(clamp(i * (255 + 2 * contrast) / 255.0f - contrast) + brightness);
	}
	
	private int clamp(float x) {
		if (x > 255)
			return 255;
		else if (x < 0)
			return 0;
		else
			return (int)x;
	}
	
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	
	public void setContrast(int contrast) {
		this.contrast = contrast;
	}

}
