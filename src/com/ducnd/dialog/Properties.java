package com.ducnd.dialog;

import com.ducnd.exercise11_filemanager.MainActivity;
import com.ducnd.exercise11_filemanager.R;
import com.ducnd.property.ContentProperty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Properties extends Activity {
	private TextView textName, textPath, textParent, textType, textSize, textModify;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_property);
		initProperties();
	}
	private void initProperties() {
		textName = (TextView)findViewById(R.id.textName);
		textPath = (TextView)findViewById(R.id.textPath);
		textParent = (TextView)findViewById(R.id.textPerant);
		textType = (TextView)findViewById(R.id.textType);
		textSize = (TextView)findViewById(R.id.textSize);
		textModify = (TextView)findViewById(R.id.textLastModify);
		
		intent = getIntent();
		actionRrecieveMessage();
	}
	public void actionOk(View v) {
		finish();
	}
	private void actionRrecieveMessage() {
		try {
			Bundle bundle = intent.getBundleExtra(MainActivity.PROPERTY);
			ContentProperty content = (ContentProperty)bundle.getSerializable(MainActivity.PROPERTY);
			textName.setText(content.getName());
			textPath.setText(content.getPath());
			textParent.setText(content.getParent());
			textType.setText(content.getType());
			textSize.setText(content.getSize());
			textModify.setText(content.getModify());
			
			
		} catch (Exception e) {
			
		}
		
		
	}
}
