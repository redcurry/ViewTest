package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.quaintsoft.imageviewer.color.BrightnessContrast;

public class BrightnessContrastDialog extends PreviewDialog
		implements OnSeekBarChangeListener {

	private OnBrightnessContrastSetListener callBack;
	private BrightnessContrast brightnessContrast;
	private int brightness, contrast;
	
	public interface OnBrightnessContrastSetListener {
		void onBrightnessContrastSet(int brightness, int contrast);
	}
	
	public BrightnessContrastDialog(Context context,
			OnBrightnessContrastSetListener callBack) {
		super(context);
		
		this.callBack = callBack;
		brightnessContrast = new BrightnessContrast();
	}
		
	@Override
	protected int getTitleID() {
		return R.string.brightness_contrast_title;
	}

	@Override
	protected int getLayoutID() {
		return R.layout.brightness_contrast;
	}

	@Override
	protected int getPreviewID() {
		return R.id.bc_preview;
	}

	@Override
	protected void applyColorChanger(Bitmap bmp) {
		brightnessContrast.setBrightness(brightness);
		brightnessContrast.setContrast(contrast);
		brightnessContrast.apply(bmp);
	}

	@Override
	protected void setupView() {
		setupSeekBars();
		setupResetButton();
	}
	
	private void setupSeekBars() {
		getBrightnessSeekBar().setOnSeekBarChangeListener(this);
		getContrastSeekBar().setOnSeekBarChangeListener(this);
	}
	
	private SeekBar getBrightnessSeekBar() {
		return (SeekBar)view.findViewById(R.id.bc_brightness_seek_bar);
	}
	
	private SeekBar getContrastSeekBar() {
		return (SeekBar)view.findViewById(R.id.bc_contrast_seek_bar);
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

	@Override
	protected void okButtonClicked() {
		callBack.onBrightnessContrastSet(brightness, contrast);
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
			case R.id.bc_brightness_seek_bar:
				brightness = progressToBrightness(progress);
				getBrightnessTextView().setText(String.valueOf(brightness));
				break;
			case R.id.bc_contrast_seek_bar:
				contrast = progressToContrast(progress);
				getContrastTextView().setText(String.valueOf(contrast));
				break;
		}
	
		updatePreview();
	}
	
	private int progressToBrightness(int progress) {
		return progress - 255;
	}
	
	private int progressToContrast(int progress) {
		return progress - 127;
	}
	
	private TextView getBrightnessTextView() {
		return (TextView)view.findViewById(R.id.bc_brightness_value);
	}
	
	private TextView getContrastTextView() {
		return (TextView)view.findViewById(R.id.bc_contrast_value);
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}

}
