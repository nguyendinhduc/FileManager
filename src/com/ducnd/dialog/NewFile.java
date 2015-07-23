package com.ducnd.dialog;

import com.ducnd.exercise11_filemanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewFile extends Activity implements OnClickListener{
	
	private Button btnCancelNewFile, btnOkNewFile;
	private EditText editNameFile, editFormat;
	private Intent intent;
	public static final String NAME_FILE = "NAME_FILE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_new_file);
		initNewFile();
	}
	private void initNewFile () {
		btnCancelNewFile = (Button)findViewById(R.id.btnCancelNewFile);
		btnCancelNewFile.setOnClickListener(this);
		btnOkNewFile = (Button)findViewById(R.id.btnOkNewFile);
		btnOkNewFile.setOnClickListener(this);
		
		editFormat = (EditText)findViewById(R.id.editFormat);
		editNameFile = (EditText)findViewById(R.id.editNameFile);
		
		intent = getIntent();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOkNewFile:
			actionOk();
			break;
		case R.id.btnCancelNewFile:
			actionCancel();
			break;
		default:
			break;
		}
	}
	private void actionCancel() {
		setResult(Activity.RESULT_CANCELED, intent);
		finish();
	}
	private void actionOk() {
		if ( !editFormat.getText().toString().equals("") && !editNameFile.getText().toString().equals("") ) {
			intent.putExtra(NAME_FILE, editNameFile.getText().toString()+"."+editFormat.getText().toString());
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		else {
			Toast.makeText(NewFile.this, "Please input enough information!!", Toast.LENGTH_LONG).show();
		}
	}
}
