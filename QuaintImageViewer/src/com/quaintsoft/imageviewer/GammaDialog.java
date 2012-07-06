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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.quaintsoft.imageviewer.color.Gamma;

public class GammaDialog extends AlertDialog
		implements OnClickListener, OnSeekBarChangeListener {

	private Context context;
	private OnGammaSetListener callBack;
	private View view;
	private Bitmap bitmapForPreview;
	private Bitmap originalPreviewBitmap;
	private Bitmap currentPreviewBitmap;
	
	private Gamma gamma;
	
	public interface OnGammaSetListener {
		void onGammaSet(float gamma);
	}
	// TODO: Can I derive GammaDialog from some general one
	// that handles all the preview logic?
	public GammaDialog(Context context,	OnGammaSetListener callBack) {
		super(context);
		
		this.context = context;
		this.callBack = callBack;
		
		gamma = new Gamma();
		
		setTitle(R.string.gamma_title);
		setView(createView());
		setButton(BUTTON_POSITIVE, "ok", this);
		setButton(BUTTON_NEGATIVE, "cancel", (OnClickListener)null);
		
		setupView();
	}
	
	private View createView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.gamma, null);
		return view;
	}
	
	private void setupView() {
		setupPreview();
		getGammaSeekBar().setOnSeekBarChangeListener(this);
		setupResetButton();
	}
	
	private void setupPreview() {
		this.setOnShowListener(new OnShowListener() {
			public void onShow(DialogInterface dialog) {
				setupPreviewBitmap();
			}
		});
	}
	
	private void setupPreviewBitmap() {
		ImageView preview = getPreview();
		originalPreviewBitmap = resizeBitmap(bitmapForPreview, preview.getWidth(), preview.getHeight());
		updatePreview();
	}
	
	private void updatePreview() {
		if (originalPreviewBitmap != null) {
			gamma.setGamma(getGamma());
			currentPreviewBitmap = originalPreviewBitmap.copy(Config.ARGB_8888, true);
			gamma.apply(currentPreviewBitmap);
			getPreview().setImageBitmap(currentPreviewBitmap);
		}
	}

	private ImageView getPreview() {
		return (ImageView)view.findViewById(R.id.gamma_preview);
	}
	
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
	
	private SeekBar getGammaSeekBar() {
		return (SeekBar)view.findViewById(R.id.gamma_seek_bar);
	}
	
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		getGammaTextView().setText(String.valueOf(getGamma()));
		updatePreview();
	}
	
	private TextView getGammaTextView() {
		return (TextView)view.findViewById(R.id.gamma_value);
	}

	public float getGamma() {
		return progressToGamma(getGammaSeekBar().getProgress());
	}
	
	private float progressToGamma(int progress) {
		return (float)(progress + 1) / 100f;
	}

	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}
	
	public void setBitmapForPreview(Bitmap bmp) {
		bitmapForPreview = bmp;
	}
	
	private void setupResetButton() {
		Button resetButton = getResetButton();
		resetButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				reset();
			}
		});
	}
	
	private Button getResetButton() {
		return (Button)view.findViewById(R.id.gamma_reset_button);
	}
	
	public void reset() {
		getGammaSeekBar().setProgress(99);
	}

	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
			case BUTTON_POSITIVE:
				if (callBack != null)
					callBack.onGammaSet(getGamma());
				break;
		}
	}

}
