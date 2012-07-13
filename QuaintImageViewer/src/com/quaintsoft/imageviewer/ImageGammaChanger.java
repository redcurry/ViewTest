package com.quaintsoft.imageviewer;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.quaintsoft.imageviewer.GammaDialog.OnGammaSetListener;
import com.quaintsoft.imageviewer.color.Gamma;

public class ImageGammaChanger implements OnGammaSetListener {
	
	private Context context;
	private ImageViewModel imageViewModel;
	
	Gamma gamma;
	WeakReference bmpRef;
	
	public class WorkThread extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... args) {
			gamma.apply((Bitmap)bmpRef.get());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if (imageViewModel != null)
				imageViewModel.invalidate();
		}
		
	}
	
	public ImageGammaChanger(Context context, ImageViewModel imageViewModel) {
		this.context = context;
		this.imageViewModel = imageViewModel;
		
		gamma = new Gamma();
	}

	public void onGammaSet(float gammaValue) {
		bmpRef = new WeakReference(imageViewModel.getImageBitmap());
		gamma.setGamma(gammaValue);
		
		WorkThread worker = new WorkThread();
		worker.execute();
	}

}
