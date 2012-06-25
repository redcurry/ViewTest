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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.quaintsoft.imageviewer.color.BrightnessContrast;

public class BrightnessContrastDialog extends AlertDialog
		implements OnClickListener, OnSeekBarChangeListener {

	private Context context;
	private OnBrightnessContrastSetListener callBack;
	private View view;
	private Bitmap bitmapForPreview;
	private Bitmap originalPreviewBitmap;
	private Bitmap currentPreviewBitmap;
	
	private BrightnessContrast brightnessContrast;
	
	public interface OnBrightnessContrastSetListener {
		void onBrightnessContrastSet(int brightness, int contrast);
	}
	
	public BrightnessContrastDialog(Context context,
			OnBrightnessContrastSetListener callBack) {
		super(context);
		
		this.context = context;
		this.callBack = callBack;
		
		brightnessContrast = new BrightnessContrast();
		
		setTitle(R.string.brightness_contrast_title);
		setView(createView());
		setButton(BUTTON_POSITIVE, "ok", this);
		setButton(BUTTON_NEGATIVE, "cancel", (OnClickListener)null);
		
		setupView();
	}
	
	private View createView() {
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.brightness_contrast, null);
		return view;
	}
	
	private void setupView() {
		setupPreview();
		getBrightnessSeekBar().setOnSeekBarChangeListener(this);
		getContrastSeekBar().setOnSeekBarChangeListener(this);
		setupResetButton();
	}
	
	private void setupPreview() {
		this.setOnShowListener(new OnShowListener() {
			public void onShow(DialogInterface arg0) {
				setupPreviewBitmap();
			}
		});
	}
	
	private void setupPreviewBitmap() {
		ImageView preview = getPreview();
		originalPreviewBitmap = resizeBitmap(bitmapForPreview, preview.getWidth(), preview.getHeight());
		currentPreviewBitmap = Bitmap.createBitmap(originalPreviewBitmap.getWidth(), originalPreviewBitmap.getHeight(), Config.ARGB_8888);
		updatePreview();
	}
	
	private void updatePreview() {
		brightnessContrast.setBrightness(getBrightness());
		brightnessContrast.setContrast(getContrast());
		currentPreviewBitmap = originalPreviewBitmap.copy(Config.ARGB_8888, true);
		brightnessContrast.apply(currentPreviewBitmap);
		getPreview().setImageBitmap(currentPreviewBitmap);
	}

	private ImageView getPreview() {
		return (ImageView)view.findViewById(R.id.bc_preview);
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
	
	private SeekBar getBrightnessSeekBar() {
		return (SeekBar)view.findViewById(R.id.bc_brightness_seek_bar);
	}
	
	private SeekBar getContrastSeekBar() {
		return (SeekBar)view.findViewById(R.id.bc_contrast_seek_bar);
	}
	
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
			case R.id.bc_brightness_seek_bar:
				getBrightnessTextView().setText(String.valueOf(getBrightness()));
				break;
			case R.id.bc_contrast_seek_bar:
				getContrastTextView().setText(String.valueOf(getContrast()));
				break;
		}
		
		updatePreview();
	}
	
	private TextView getBrightnessTextView() {
		return (TextView)view.findViewById(R.id.bc_brightness_value);
	}
	
	public int getBrightness() {
		return progressToBrightness(getBrightnessSeekBar().getProgress());
	}
	
	private int progressToBrightness(int progress) {
		return progress - 255;
	}

	private TextView getContrastTextView() {
		return (TextView)view.findViewById(R.id.bc_contrast_value);
	}

	public int getContrast() {
		return progressToContrast(getContrastSeekBar().getProgress());
	}
	
	private int progressToContrast(int progress) {
		return progress - 127;
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
		return (Button)view.findViewById(R.id.bc_reset_button);
	}
	
	public void reset() {
		getBrightnessSeekBar().setProgress(255);
		getContrastSeekBar().setProgress(127);
	}

	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
			case BUTTON_POSITIVE:
				if (callBack != null)
					callBack.onBrightnessContrastSet(getBrightness(), getContrast());
				break;
		}
	}

}
