package com.ducnd.dialog;

import com.ducnd.exercise11_filemanager.MainActivity;
import com.ducnd.exercise11_filemanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewFolder extends Activity implements OnClickListener {
	private Intent intent;
	private Button btnOkNewFolder, btnCancelNewFolder;
	private EditText editNameNewFolder;
	public static String NAME = "NAME";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_new_folder);
		Window window = getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();
		wlp.gravity = Gravity.CENTER;
		wlp.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(wlp);
		initNewFolder();
		
	}
	private void initNewFolder() {
		intent = getIntent();
		
		btnOkNewFolder = (Button)NewFolder.this.findViewById(R.id.btnOkNewFolder);
		btnOkNewFolder.setOnClickListener(this);
		
		btnCancelNewFolder = (Button)NewFolder.this.findViewById(R.id.btnCancelNewFolder);
		btnCancelNewFolder.setOnClickListener(this);
		
		editNameNewFolder = (EditText)NewFolder.this.findViewById(R.id.editNameFolder);
		
		if ( intent.getCharSequenceExtra(MainActivity.RENAME).toString().equals("rename") ) {
			((TextView)NewFolder.this.findViewById(R.id.textTitle)).setText("Rename");
			((TextView)NewFolder.this.findViewById(R.id.textLabel)).setText("New name");
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnOkNewFolder:
			sendMessage();
			break;
		case R.id.btnCancelNewFolder:
			setResult(Activity.RESULT_CANCELED, intent);
			finish();
			break;
		default:
			break;
		}
	}
	private void sendMessage() {
		String name = editNameNewFolder.getText().toString();
		if ( !name.equals("") ) {
			intent.putExtra(NAME, name);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
		else {
			Toast.makeText(NewFolder.this, "Please input name", Toast.LENGTH_SHORT).show();
		}
	}
	
}
