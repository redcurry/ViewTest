package com.quaintsoft.imageviewer.color;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class BrightnessContrast extends AbstractColorChanger {

	private int brightness = 0;
	private int contrast = 0;
	
	@Override
	public void apply(Bitmap src, Bitmap dst) {
		if (src == null || dst == null)
			return;
		
		int width = src.getWidth();
		int height = src.getHeight();
		
		int[] pixels = new int[width * height];
		src.getPixels(pixels, 0, width, 0, 0, width, height);
		
		int[] values = new int[256];
		for (int i = 0; i < values.length; i++)
			values[i] = calcBrightnessContrast(i);
		
		int r, g, b, newR, newG, newB;
		for (int p = 0; p < pixels.length; p++) {
			r = Color.red(pixels[p]);
			g = Color.green(pixels[p]);
			b = Color.blue(pixels[p]);
			newR = values[r];
			newG = values[g];
			newB = values[b];
			pixels[p] = Color.rgb(newR, newG, newB);
		}
		
		dst.setPixels(pixels, 0, width, 0, 0, width, height);
	}
	
	private int calcBrightnessContrast(int i) {
		if (contrast > 0)
			return clamp(clamp((i - contrast) * 255.0f / (255 - 2 * contrast)) + brightness);
			//return clamp((clamp(i + brightness) - contrast) * 255.0f / (255 - 2 * contrast));
		else
			return clamp(clamp(i * (255 + 2 * contrast) / 255.0f - contrast) + brightness);
			//return clamp(clamp(i + brightness) * (255 + 2 * contrast) / 255.0f - contrast);
	}
	
	private int clamp(float x) {
		if (x > 255)
			return 255;
		else if (x < 0)
			return 0;
		else
			return (int)x;
	}
	
	@Override
	protected ColorMatrixColorFilter createColorFilter() {
		// Never used
		return new ColorMatrixColorFilter(new ColorMatrix());
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	
	public void setContrast(int contrast) {
		this.contrast = contrast;
	}

}
