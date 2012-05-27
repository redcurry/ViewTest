package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;

import com.quaintsoft.imageviewer.BrightnessContrastGammaDialog.OnBrightnessContrastGammaSetListener;
import com.quaintsoft.imageviewer.color.BrightnessContrast;
import com.quaintsoft.imageviewer.color.Gamma;

public class ImageBrightnessContrastGammaChanger implements
		OnBrightnessContrastGammaSetListener {
	
	private Context context;
	private ImageViewModel imageViewModel;
	
	public ImageBrightnessContrastGammaChanger(Context context, ImageViewModel imageViewModel) {
		this.context = context;
		this.imageViewModel = imageViewModel;
	}

	public void onBrightnessContrastGammaSet(int brightness, int contrast, float gamma) {
		Bitmap bmp = imageViewModel.getImageBitmap();
		BrightnessContrast bc = new BrightnessContrast();
		bc.setBrightness(brightness);
		bc.setContrast(contrast);
		bc.apply(bmp);
		Gamma g = new Gamma();
		g.setGamma(gamma);
		g.apply(bmp);
		imageViewModel.invalidate();
	}

}
