package com.ducnd.dialog;

import java.io.File;
import java.io.Serializable;

import com.ducnd.common.SendIterface;
import com.ducnd.exercise11_filemanager.MainActivity;
import com.ducnd.exercise11_filemanager.R;
import com.ducnd.interfaceactionfile.ActionFile;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

public class List extends Activity{
	private static final String TAG = "List";
	private static ActionFile mActionFile;
	private String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.layout_list);
		
		Window window = getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();
		wlp.gravity = Gravity.TOP|Gravity.RIGHT;
		wlp.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(wlp);
		setTitle("List");
		
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.icon_list);
//		Intent intent = getIntent();
//		Bundle b = intent.getBundleExtra("list");
//		mActionFile = ((SendIterface)b.getSerializable("list")).mActionfile;
	}
	public void actionCoppy ( View v) {
		mActionFile.actionCoppy();
		finish();
	}
	public void actionCut ( View v) {
		mActionFile.actionCut();
		finish();
	}
	public void actionShare( View v ) {
		finish();
	}
	public void actionPaste(View v) {
		this.mActionFile.actionPaste();
		finish();
	}
	public void actionSetting(View v) {
		finish();
	}
	public void actionExit(View v) {
		finish();
	}
	public void addListen( ActionFile action) {
		this.mActionFile = action;
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	
}
