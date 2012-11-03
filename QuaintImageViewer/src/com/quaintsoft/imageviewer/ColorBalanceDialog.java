package com.quaintsoft.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.quaintsoft.imageviewer.color.ColorBalance;

public class ColorBalanceDialog extends PreviewDialog
		implements OnSeekBarChangeListener {

	private OnColorBalanceSetListener callBack;
	private ColorBalance colorBalance;
	private int red, green, blue;
	
	public interface OnColorBalanceSetListener {
		void onColorBalanceSet(int red, int green, int blue);
	}
	
	public ColorBalanceDialog(Context context,
			OnColorBalanceSetListener callBack) {
		super(context);
		
		this.callBack = callBack;
		colorBalance = new ColorBalance();
	}
		
	@Override
	protected int getTitleID() {
		return R.string.dialog_color_balance_title;
	}

	@Override
	protected int getLayoutID() {
		return R.layout.color_balance;
	}

	@Override
	protected int getPreviewID() {
		return R.id.cb_preview;
	}

	@Override
	protected void applyColorChanger(Bitmap bmp) {
		colorBalance.setRed(red);
		colorBalance.setGreen(green);
		colorBalance.setBlue(blue);
		colorBalance.apply(bmp);
	}

	@Override
	protected void setupView() {
		setupSeekBars();
		setupResetButton();
	}
	
	private void setupSeekBars() {
		getRedSeekBar().setOnSeekBarChangeListener(this);
		getGreenSeekBar().setOnSeekBarChangeListener(this);
		getBlueSeekBar().setOnSeekBarChangeListener(this);
	}
	
	private SeekBar getRedSeekBar() {
		return (SeekBar)view.findViewById(R.id.cb_red_seek_bar);
	}
	
	private SeekBar getGreenSeekBar() {
		return (SeekBar)view.findViewById(R.id.cb_green_seek_bar);
	}
	
	private SeekBar getBlueSeekBar() {
		return (SeekBar)view.findViewById(R.id.cb_blue_seek_bar);
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
		return (Button)view.findViewById(R.id.cb_reset_button);
	}
	
	public void reset() {
		getRedSeekBar().setProgress(255);
		getGreenSeekBar().setProgress(255);
		getBlueSeekBar().setProgress(255);
	}

	@Override
	protected void okButtonClicked() {
		callBack.onColorBalanceSet(red, green, blue);
	}

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
			case R.id.cb_red_seek_bar:
				red = progressToColor(progress);
				getRedTextView().setText(String.valueOf(red));
				break;
			case R.id.cb_green_seek_bar:
				green = progressToColor(progress);
				getGreenTextView().setText(String.valueOf(green));
				break;
			case R.id.cb_blue_seek_bar:
				blue = progressToColor(progress);
				getBlueTextView().setText(String.valueOf(blue));
		}
	
		updatePreview();
	}
	
	private int progressToColor(int progress) {
		return progress - 255;
	}
	
	private TextView getRedTextView() {
		return (TextView)view.findViewById(R.id.cb_red_value);
	}
	
	private TextView getGreenTextView() {
		return (TextView)view.findViewById(R.id.cb_green_value);
	}
	
	private TextView getBlueTextView() {
		return (TextView)view.findViewById(R.id.cb_blue_value);
	}

	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}

}
