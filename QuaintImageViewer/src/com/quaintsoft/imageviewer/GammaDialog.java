package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.quaintsoft.imageviewer.color.Gamma;

public class GammaDialog extends PreviewDialog
		implements OnSeekBarChangeListener {
	
	private OnGammaSetListener callBack;
	private Gamma gamma;
	
	public interface OnGammaSetListener {
		void onGammaSet(float gamma);
	}
	
	public GammaDialog(Context context, OnGammaSetListener callBack) {
		super(context);
		
		this.callBack = callBack;
		gamma = new Gamma();
	}
	
	@Override
	protected int getTitleID() {
		return R.string.gamma_title;
	}

	@Override
	protected int getLayoutID() {
		return R.layout.gamma;
	}

	@Override
	protected int getPreviewID() {
		return R.id.gamma_preview;
	}

	@Override
	protected void applyColorChanger(Bitmap bmp) {
		gamma.setGamma(getGamma());
		gamma.apply(bmp);
	}
	
	public float getGamma() {
		return progressToGamma(getGammaSeekBar().getProgress());
	}
	
	private float progressToGamma(int progress) {
		return (float)(progress + 1) / 100f;
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
	
	@Override
	protected void setupView() {
		setupGammaSeekBar();
		setupResetButton();
	}
	
	private void setupGammaSeekBar() {
		getGammaSeekBar().setOnSeekBarChangeListener(this);
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
	
	@Override
	protected void okButtonClicked() {
		callBack.onGammaSet(getGamma());
	}
	
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	public void onStopTrackingTouch(SeekBar seekBar) {

	}

}
