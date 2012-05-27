package com.quaintsoft.imageviewer.color;

public class Gamma extends BitmapColorChangerByPixelFunction {

	private float gamma = 1.0f;
	
	public int pixelFunction(int value) {
		return (int)(Math.pow(value / 255.0f, 1.0f / gamma) * 255);
	}
	
	public void setGamma(float gamma) {
		this.gamma = gamma;
	}

}
