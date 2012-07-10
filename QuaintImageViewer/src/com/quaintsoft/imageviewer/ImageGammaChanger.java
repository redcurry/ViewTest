package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.quaintsoft.imageviewer.GammaDialog.OnGammaSetListener;
import com.quaintsoft.imageviewer.color.Gamma;

public class ImageGammaChanger implements OnGammaSetListener {
	
	private Context context;
	private ImageViewModel imageViewModel;
	
	Bitmap bmp;
	
	public class WorkThread extends AsyncTask<Float, Void, Void> {

		@Override
		protected Void doInBackground(Float... args) {
			Gamma g = new Gamma();
			g.setGamma(args[0]);
			g.apply(bmp);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			imageViewModel.invalidate();
		}
		
	}
	
	public ImageGammaChanger(Context context, ImageViewModel imageViewModel) {
		this.context = context;
		this.imageViewModel = imageViewModel;
	}

	public void onGammaSet(float gamma) {
		bmp = imageViewModel.getImageBitmap();
		WorkThread worker = new WorkThread();
		worker.execute(gamma);
	}

}
