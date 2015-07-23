package com.ducnd.exercise11_filemanager;

import java.io.File;
import java.util.ArrayList;

import com.ducnd.common.Common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ActivitySearch extends Activity{
	public static final String PATH_SEARCH = "PATH_SEND";
	private ListView lvSearch;
	private ArrayList<ItemFolder> arrSearch;
	private MyArrayAdapter adapterSearch;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);
		initActivitySearch();
		lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				sendMessage(arrSearch.get(position).path);
				finish();
			}
			
		});
		
	}
	private void initActivitySearch(){
		arrSearch = Common.arrSearch;
		swapSizePath();
		lvSearch = (ListView)findViewById(R.id.lvSearch);
		adapterSearch = new MyArrayAdapter(ActivitySearch.this, R.layout.element_item_listview, arrSearch);
		adapterSearch.setType(MyArrayAdapter.TYPE_LIST_SEARCH);
		for ( int i = 0; i < Common.arrSearch.size(); i++ ) {
			setIcon(arrSearch.get(i), i);
		}
		adapterSearch.notifyDataSetInvalidated();
		lvSearch.setAdapter(adapterSearch);
		intent = getIntent();
		
	}
	private void swapSizePath() {
		for ( ItemFolder i:this.arrSearch ) {
			i.size = i.path;
		}
	}
	private  void sendMessage(String pathSend) {
		intent.putExtra(PATH_SEARCH, pathSend);
		setResult(Activity.RESULT_OK, intent);
	}
 	private void setIcon( ItemFolder item, int position ) {
		String tem = item.name;
		tem = tem.substring(tem.lastIndexOf(".") > -1 ? tem.lastIndexOf("."):tem.length(),tem.length());
		switch (tem) {
		case ".txt":
			arrSearch.get(position).imageIcon = MainActivity.bitmapText;
			break;
		case ".doc":
			arrSearch.get(position).imageIcon = MainActivity.bitmapDoc;
			break;
		case ".pdf":
			arrSearch.get(position).imageIcon = MainActivity.bitmapDoc;
			break;
		case ".mp3":
			arrSearch.get(position).imageIcon = MainActivity.bitmapMusic;
			break;
		case ".mp4":
			arrSearch.get(position).imageIcon = MainActivity.bitmapVideo;
			break;
		case ".flv":
			arrSearch.get(position).imageIcon = MainActivity.bitmapVideo;
			break;
		case ".jpg":
			arrSearch.get(position).imageIcon = MainActivity.bitmapImage;
			break;
		case ".JPG":
			arrSearch.get(position).imageIcon = MainActivity.bitmapImage;
			break;
		case ".png":
			arrSearch.get(position).imageIcon = MainActivity.bitmapImage;
		case ".PNG":
			arrSearch.get(position).imageIcon = MainActivity.bitmapImage;
			break;
		case ".zip":
			arrSearch.get(position).imageIcon = MainActivity.bitmapZip;
			break;
		case ".apk":
			arrSearch.get(position).imageIcon = MainActivity.bitmapApp;
			break;
		case ".rar":
			arrSearch.get(position).imageIcon = MainActivity.bitmapZip;
			break;
		case "":
			arrSearch.get(position).imageIcon = MainActivity.bitmapFolder;
			break;
			
		default:
			arrSearch.get(position).imageIcon = MainActivity.bitmapDefault;
			break;
		}
		if ( new File(item.path).isDirectory() ) arrSearch.get(position).imageIcon = MainActivity.bitmapFolder;
	}
 	@Override
 	public void onBackPressed() {
 		setResult(RESULT_CANCELED);
 		super.onBackPressed();
 	}
}
