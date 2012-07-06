package com.quaintsoft.imageviewer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public abstract class PreviewDialog extends AlertDialog
		implements OnClickListener {
	
	// TODO: Refactor so that setupOtherViews() is not called every time
	// dialog is shown. Go through steps in showing a dialog (in my mind)
	// in order to figure out when things need to be set up (think!)

	protected Context context;
	protected View view;
	private Bitmap bitmapForPreview;
	private Bitmap originalPreviewBitmap;
	private Bitmap currentPreviewBitmap;
	
	private boolean initialized = false;
	
	public PreviewDialog(Context context) {
		super(context);
		
		this.context = context;
		
		setTitle(getTitleID());
		setView(createView());
		setupOkButton();
		setupCancelButton();
		
		setOnShowListener(new OnShowListener() {
			public void onShow(DialogInterface dialog) {
				if (!initialized) {
					setupDialog();
					initialized = true;
				}
			}
		});
	}
	
	private void setupDialog() {
		setupView();
	}
	
	protected abstract int getTitleID();
	
	protected View createView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(getLayoutID(), null);
		return view;
	}
	
	protected abstract int getLayoutID();
	
	protected abstract void setupOkButton();
	
	protected void setupCancelButton() {
		setButton(BUTTON_NEGATIVE, "cancel", (OnClickListener)null);
	}
	
	protected void setupView() {
		setupPreview();
		setupOtherViews();
	}
	
	protected void setupPreview() {
		originalPreviewBitmap = resizeBitmapToPreview(bitmapForPreview);
		updatePreview();
	}

	private Bitmap resizeBitmapToPreview(Bitmap bmp) {
		ImageView preview = getPreview();
		return resizeBitmap(bmp, preview.getWidth(), preview.getHeight());
	}
	
	private ImageView getPreview() {
		return (ImageView)view.findViewById(getPreviewID());
	}

	protected abstract int getPreviewID();

	private Bitmap resizeBitmap(Bitmap bmp, int width, int height) {
		if (bmp == null)
			return null;
		RectF src = new RectF(0, 0, bmp.getWidth(), bmp.getHeight());
		RectF dst = new RectF(0, 0, width, height);
		Matrix imageMatrix = new Matrix();
		imageMatrix.setRectToRect(src, dst, ScaleToFit.START);
		RectF newBmpRect = new RectF(0, 0, bmp.getWidth(), bmp.getHeight());
		imageMatrix.mapRect(newBmpRect);
		Bitmap newBmp = Bitmap.createBitmap((int)newBmpRect.width(), (int)newBmpRect.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(newBmp);
		canvas.drawBitmap(bmp, imageMatrix, null);
		return newBmp;
	}
	
	protected void updatePreview() {
		getPreview().setImageBitmap(applyColorChangerToPreview());
	}
	
	private Bitmap applyColorChangerToPreview() {
		currentPreviewBitmap = copyBitmap(originalPreviewBitmap);
		applyColorChanger(currentPreviewBitmap);
		return currentPreviewBitmap;
	}
	
	private Bitmap copyBitmap(Bitmap bmp) {
		return bmp.copy(Config.ARGB_8888, true);
	}
	
	protected abstract void applyColorChanger(Bitmap bmp);
	
	protected abstract void setupOtherViews();
	
	public void setBitmapForPreview(Bitmap bmp) {
		Log.d("debug", "bitmap updated");
		bitmapForPreview = bmp;
	}
	
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
			case BUTTON_POSITIVE:
				okButtonClicked();
				break;
		}
	}
	
	protected abstract void okButtonClicked();

}
