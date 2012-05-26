package com.quaintsoft.imageviewer.color;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class Gamma extends AbstractColorChanger {

	private float gamma = 1.0f;
	
	@Override
	public void apply(Bitmap src, Bitmap dst) {
		if (src == null || dst == null)
			return;
		
		int width = src.getWidth();
		int height = src.getHeight();
		
		int[] pixels = new int[width * height];
		src.getPixels(pixels, 0, width, 0, 0, width, height);
		
		int[] powers = new int[256];
		for (int i = 0; i < powers.length; i++)
			powers[i] = (int)(Math.pow(i / 255.0f, 1.0f / gamma) * 255);
		
		int r, g, b, newR, newG, newB;
		for (int p = 0; p < pixels.length; p++) {
			r = Color.red(pixels[p]);
			g = Color.green(pixels[p]);
			b = Color.blue(pixels[p]);
			newR = powers[r];
			newG = powers[g];
			newB = powers[b];
			pixels[p] = Color.rgb(newR, newG, newB);
		}
		
		dst.setPixels(pixels, 0, width, 0, 0, width, height);
	}
	
	@Override
	protected ColorMatrixColorFilter createColorFilter() {
		// Never used
		return new ColorMatrixColorFilter(new ColorMatrix());
	}
	
	public void setGamma(float gamma) {
		this.gamma = gamma;
	}

}
