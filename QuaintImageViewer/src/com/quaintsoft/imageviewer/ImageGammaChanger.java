package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;

import com.quaintsoft.imageviewer.GammaDialog.OnGammaSetListener;
import com.quaintsoft.imageviewer.color.Gamma;

public class ImageGammaChanger implements
		OnGammaSetListener {
	
	private Context context;
	private ImageViewModel imageViewModel;
	
	public ImageGammaChanger(Context context, ImageViewModel imageViewModel) {
		this.context = context;
		this.imageViewModel = imageViewModel;
	}

	// TODO: Separate brightness contrast from gamma
	public void onGammaSet(float gamma) {
		Bitmap bmp = imageViewModel.getImageBitmap();
		Gamma g = new Gamma();
		g.setGamma(gamma);
		g.apply(bmp);
		imageViewModel.invalidate();
	}

}
