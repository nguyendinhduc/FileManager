package com.ducnd.exercise11_filemanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.ducnd.common.Common;
import com.ducnd.dialog.List;
import com.ducnd.dialog.NewFile;
import com.ducnd.dialog.NewFolder;
import com.ducnd.dialog.Properties;
import com.ducnd.interfaceactionfile.ActionFile;
import com.ducnd.property.ContentProperty;

import ArraySearch.Node_Search;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
		android.view.View.OnKeyListener, ActionFile, Runnable {
	protected static final String TAG = "MainActivity";
	public static final int NEW_FOLDER = 34;
	public static final int NEW_FILE = 345;
	private static final int DELETE = 2432;
	public static final String RENAME = "RENAME";
	public static final int QUESTION_RENAME = 83475;
	public static final int QUESTION_PROPERTIES = 979;
	public static final String PROPERTY = "PROPERTY";
	public static final int QUESTION_SEARCH = 92364;
	public static final String SEARCH = "SEARCH";
	public static final int ACTION_COPPY = 34534;
	private static final int COPPY = 2874389;
	private static final int CUT = 234234;

	private MyArrayAdapter adapter;
	private ListView lvFolder;
	private ArrayList<ItemFolder> arrFolder;
	private EditText editSearch;
	private ImageButton btnNewFolder, btnAllCheck, btnNewFile, btnRename,
			btnTrash, btnShowList;
	private boolean isSearch = false;
	private Manager_Tree_Folder managerTreeFolder;
	private ArrayList<String> listPath;
	public static Bitmap bitmapFolder, bitmapMusic, bitmapVideo, bitmapText,
			bitmapApp, bitmapImage, bitmapDefault, bitmapZip, bitmapDoc;
	private Node_Folder currentNode;
	private boolean isCoppy = false;
	private ArrayList<String> arrPathCoppy = new ArrayList<String>();
	private String pathPastOn = "";
	private int positionLongOnClick = -1;
	private ArrayList<Node_Search> arrSearch;
	private Handler mHandler;
	private boolean isCopping = false;
	private boolean isCutting = false;
	private boolean isCut = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initMain();
		// managerTreeFolder.preOrder(managerTreeFolder.getRoot());
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case  ACTION_COPPY:
					if ( currentNode.getPath().equals(((String)msg.obj))) {
						changeAllListViewFromRoot(currentNode.getPath());
					}
					break;

				default:
					break;
				}
			}
		};
		lvFolder.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG, "onItemClick_posistion: "+position);
				Log.i(TAG, "onItemClick_id: "+id);
				Log.i(TAG, "setOnItemClickListener_position: " + position);
				Log.i(TAG, "setOnItemClickListener_check: " + arrFolder.get(position).isChecked);
				openItemListView(position);
			}
			
		});

		lvFolder.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				positionLongOnClick = position;
				return false;
			}
			
		});
	}

	private void initMain() {
		btnNewFolder = (ImageButton) MainActivity.this
				.findViewById(R.id.btnNewFolder);
		btnNewFolder.setOnClickListener(MainActivity.this);

		btnAllCheck = (ImageButton) MainActivity.this
				.findViewById(R.id.btnAllChoose);
		btnAllCheck.setOnClickListener(this);

		btnNewFile = (ImageButton) MainActivity.this
				.findViewById(R.id.btnNewFile);
		btnNewFile.setOnClickListener(this);

		btnRename = (ImageButton) MainActivity.this
				.findViewById(R.id.btnRename);
		btnRename.setOnClickListener(this);
		
		btnShowList = (ImageButton)findViewById(R.id.btnShowList);
		btnShowList.setOnClickListener(MainActivity.this);

		btnTrash = (ImageButton) findViewById(R.id.btnTrash);
		btnTrash.setOnClickListener(this);

		bitmapFolder = BitmapFactory.decodeResource(getResources(),
				R.drawable.folder);
		bitmapMusic = BitmapFactory.decodeResource(getResources(),
				R.drawable.music_folder);
		bitmapVideo = BitmapFactory.decodeResource(getResources(),
				R.drawable.movies_folder);
		bitmapText = BitmapFactory.decodeResource(getResources(),
				R.drawable.text);
		bitmapApp = BitmapFactory
				.decodeResource(getResources(), R.drawable.app);
		bitmapImage = BitmapFactory.decodeResource(getResources(),
				R.drawable.image);
		bitmapDoc = BitmapFactory.decodeResource(getResources(), R.drawable.doc);
		bitmapDefault = BitmapFactory.decodeResource(getResources(),
				R.drawable.default112);
		bitmapZip = BitmapFactory
				.decodeResource(getResources(), R.drawable.rar);
		editSearch = (EditText) findViewById(R.id.editSearch);
		editSearch.setOnKeyListener(MainActivity.this);

		arrFolder = new ArrayList<ItemFolder>();
		arrSearch = new ArrayList<Node_Search>();
		try {
			managerTreeFolder = new Manager_Tree_Folder(MainActivity.this);
			managerTreeFolder.initManaget_Tree_Folder();
			currentNode = managerTreeFolder.getRoot();
			// managerTreeFolder.preOrder(managerTreeFolder.getRoot());
			listPath = managerTreeFolder.getPathListChild(managerTreeFolder
					.getRoot());
			String tem;
			File file;
			for (int i = 0; i < listPath.size(); i++) {
				tem = listPath.get(i);
				file = new File(tem);
				addArrListViewFromFile(file);
			}
		} catch (Exception e) {
			Log.i(TAG, "initMain()_ERRO: " + e.toString());
		}

		adapter = new MyArrayAdapter(MainActivity.this,
				R.layout.element_item_listview, arrFolder);

		lvFolder = (ListView) findViewById(R.id.lvFolder);
		lvFolder.setAdapter(adapter);

		registerForContextMenu(lvFolder);

	}

	public void actionBtnSearch(View v) {
		isSearch = !isSearch;
		if (isSearch) {
			editSearch.setVisibility(View.VISIBLE);
			editSearch.requestFocus();
		} else {
			editSearch.setText("");
			editSearch.setVisibility(View.GONE);
			
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == lvFolder.getId()) {
			getMenuInflater().inflate(R.menu.menu_element_lv_folder, menu);
			menu.setHeaderTitle("Action");
			menu.setHeaderIcon(R.drawable.action);
			
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.open:
			openItemListView(positionLongOnClick);
			positionLongOnClick = -1;
			break;
		case R.id.delete:
			dialogQuestionOneItem("Question", "Do you want to delete?", DELETE);
			break;
		case R.id.remane:
			actionRename();
			break;
		case R.id.property:
			// Intent i = new Intent(getBaseContext(), Properties.class);
			// startActivity(i);
			openProperties();
			overridePendingTransition( R.anim.scale_left, R.anim.stable);
			break;
		case R.id.choose:
			arrFolder.get(positionLongOnClick).isChecked = true;
			positionLongOnClick = -1;
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void addArrListViewFromFile(File file) {
		long size = getSizeFile(file);
		if (file.isFile()) {
			switch (Manager_Tree_Folder.getTailFile(file)) {
			case ".txt":
				arrFolder.add(new ItemFolder(bitmapText, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".doc":
				arrFolder.add(new ItemFolder(bitmapDoc, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".pdf":
				arrFolder.add(new ItemFolder(bitmapText, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".apk":
				arrFolder.add(new ItemFolder(bitmapApp, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".mp3":
				arrFolder.add(new ItemFolder(bitmapMusic, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".mp4":
				arrFolder.add(new ItemFolder(bitmapVideo, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".flv":
				arrFolder.add(new ItemFolder(bitmapVideo, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".jpg":
				arrFolder.add(new ItemFolder(bitmapImage, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".JPG":
				arrFolder.add(new ItemFolder(bitmapImage, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".png":
				arrFolder.add(new ItemFolder(bitmapImage, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".PNG":
				arrFolder.add(new ItemFolder(bitmapImage, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".zip":
				arrFolder.add(new ItemFolder(bitmapZip, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			case ".rar":
				arrFolder.add(new ItemFolder(bitmapZip, file.getName(), file
						.getPath(), size + " bytes", false));
				break;
			default:
				arrFolder.add(new ItemFolder(bitmapDefault, file.getName(),
						file.getPath(), size + " bytes", false));
				break;
			}
		} else {
			arrFolder.add(new ItemFolder(bitmapFolder, file.getName(), file
					.getPath(), size + " bytes", false));
		}
	}

	private void changeAllListViewFromRoot(String pathRoot) {
		adapter.clear();
		File[] listFileClick = new File(pathRoot).listFiles();
		arrFolder = new ArrayList<ItemFolder>();
		for (int i = 0; i < listFileClick.length; i++) {
			addArrListViewFromFile(listFileClick[i]);
		}
		adapter = new MyArrayAdapter(MainActivity.this,
				R.layout.element_item_listview, arrFolder);
		adapter.notifyDataSetChanged();
		lvFolder.setAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		if (!currentNode.getPath().equals(
				Manager_Tree_Folder.PATH_EXTERNAL_DATA)) {
			
			int sizeArrSearch = arrSearch.size();
			if ( sizeArrSearch > 0 && currentNode.getPath().equals(arrSearch.get(sizeArrSearch-1).getNodeNew().getPath()) ) {
				currentNode = arrSearch.get(sizeArrSearch-1).getNodeOld();
				arrSearch.remove(sizeArrSearch-1);
			}
			else {
				currentNode = currentNode.getParent();
			}
			changeAllListViewFromRoot(currentNode.getPath());
		} else
			super.onBackPressed();
	}

	private void openFile(File file) {
		Intent intent;
		switch (Manager_Tree_Folder.getTailFile(file)) {
		case ".txt":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "text/plain");
			startActivity(intent);
			break;
		case ".xml":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "text/xml");
			startActivity(intent);
			break;
		case ".apk":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			startActivity(intent);
			break;
		case ".png":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "image/png");
			startActivity(intent);
			break;
		case ".PNG":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "image/png");
			startActivity(intent);
			break;
		case ".mp3":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "audio/mp3");
			startActivity(intent);
			break;
		case ".mp4":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "video/mp4");
			startActivity(intent);
			break;
		case ".zip":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/zip");
			startActivity(intent);
			break;
		case ".rar":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),
					"application/x-rar-compressed");
			startActivity(intent);
			break;
		case ".doc":
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/smword");
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNewFolder:
			Intent intent = new Intent(getBaseContext(), NewFolder.class);
			intent.putExtra(RENAME, "new folder");
			startActivityForResult(intent, NEW_FOLDER);
			break;
		case R.id.btnNewFile:
			Intent intentNewFile = new Intent(getBaseContext(), NewFile.class);
			startActivityForResult(intentNewFile, NEW_FILE);
			break;
		case R.id.btnAllChoose:
			for (ItemFolder i : arrFolder) {
				i.isChecked = !i.isChecked;
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.btnRename:
			clickBtnRename();
			break;
		case R.id.btnTrash:
			if (!hasChecked())
				Toast.makeText(MainActivity.this,
						"Please choose file to need delete!", Toast.LENGTH_LONG)
						.show();
			else
				dialogQuestionChooseItem("Delete",
						"Do you want to delete file choosed?", DELETE);
			break;
		case R.id.btnShowList:
			//SendIterface send = new SendIterface();
			//send.addItActionFIle(this);
			List list = new List();
			list.addListen(this);
			Intent intentShowList = new Intent();
			intentShowList.setClass(getBaseContext(), List.class);
			startActivity(intentShowList);
			overridePendingTransition( R.anim.translate_right, R.anim.stable);
			break;
		default:
			break;
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MainActivity.NEW_FOLDER) {
			if (resultCode == Activity.RESULT_OK) {
				String name = data.getStringExtra(NewFolder.NAME);
				if (compareName(name)) {
					String path = currentNode.getPath() + "/" + name;
					new File(path).mkdir();
					Manager_Tree_Folder.addItem(currentNode, new Node_Folder(
							name, path));
					arrFolder.add(new ItemFolder(bitmapFolder, name, path,
							"0byte", false));
					changeAllListViewFromRoot(currentNode.getPath());
				}
			}

			positionLongOnClick = -1;
		}
		if (requestCode == MainActivity.NEW_FILE) {
			if (resultCode == Activity.RESULT_OK) {
				String name = data.getStringExtra("NAME_FILE");
				if (compareName(name)) {
					String path = currentNode.getPath() + "/" + name;
					try {
						new File(path).createNewFile();
						Manager_Tree_Folder.addItem(currentNode, new Node_Folder(
								name, path));
						arrFolder.add(new ItemFolder(bitmapFolder, name, path,
								"0byte", false));
						changeAllListViewFromRoot(currentNode.getPath());
					} catch (IOException e) {
						Log.i(TAG, e.toString());
					}

				}
			}
			positionLongOnClick = -1;
		}
		if (requestCode == MainActivity.QUESTION_RENAME) {
			if (resultCode == Activity.RESULT_OK) {
				String name = data.getStringExtra(NewFolder.NAME);
				if (compareName(name) && positionLongOnClick > -1) {
					String path = currentNode.getPath() + "/" + name;
					String nameOld = arrFolder.get(positionLongOnClick).name;
					String tail = nameOld.substring(
							nameOld.lastIndexOf('.') >= 0 ? nameOld
									.lastIndexOf('.') : nameOld.length(),
							nameOld.length());
					File file1 = new File(currentNode.getPath() + "/" + nameOld);
					File file2 = new File(path + tail);
					file1.renameTo(file2);
					arrFolder.get(positionLongOnClick).name = name + tail;
					changeAllListViewFromRoot(currentNode.getPath());
				}
			}
			positionLongOnClick = -1;
		}
		if ( requestCode == QUESTION_SEARCH ) {
			//Toast.makeText(MainActivity.this, "search", Toast.LENGTH_LONG).show();
			if ( resultCode == Activity.RESULT_OK ) {
				String pathSearch = data.getStringExtra(ActivitySearch.PATH_SEARCH);
				File fileOpendSearch = new File(pathSearch);
				if ( fileOpendSearch.isFile() ) {
					openFile(fileOpendSearch);
				}
				else {
					Log.i(TAG, "onActivityResult_path old: " +pathSearch);
					arrSearch.add(new Node_Search(currentNode, null));
					currentNode = Manager_Tree_Folder.searchNode(currentNode, pathSearch);
					arrSearch.get(arrSearch.size()-1).setNodeNew(currentNode);
					arrFolder = new ArrayList<ItemFolder>();
					changeAllListViewFromRoot(pathSearch);
				}

			}
		}
	}

	private boolean compareName(String name) {
		for (ItemFolder i : arrFolder) {
			if (i.name.equals(name))
				return false;
		}
		return true;
	}

	private void dialogQuestionOneItem(String title, String message,
			final int type) {
		final AlertDialog oneDialog = new AlertDialog.Builder(MainActivity.this)
				.create();
		oneDialog.setTitle(title);
		oneDialog.setMessage(message);

		oneDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						oneDialog.dismiss();
					}
				});
		oneDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (type == DELETE && positionLongOnClick > -1) {
							deleteOneItem(positionLongOnClick);
							changeAllListViewFromRoot(currentNode.getPath());
						}
					}
				});
		oneDialog.show();
	}

	private void dialogQuestionChooseItem(String title, String message,
			final int type) {
		final AlertDialog oneDialog = new AlertDialog.Builder(MainActivity.this)
				.create();
		oneDialog.setTitle(title);
		oneDialog.setMessage(message);

		oneDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						oneDialog.dismiss();
					}
				});
		oneDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							if (type == DELETE && hasChecked()) {
								for (int i = 0; i < arrFolder.size(); i++) {
									if (arrFolder.get(i).isChecked) {
										deleteOneItem(i);
										i--;
									}
								}
								changeAllListViewFromRoot(currentNode.getPath());
							}

						} catch (Exception e) {
							Log.i(TAG,
									"dialogQuestionChooseItem_ERRO: "
											+ e.toString());
						}
					}
				});
		Window window = oneDialog.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();
		wlp.gravity = Gravity.TOP | Gravity.RIGHT;
		wlp.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		window.setAttributes(wlp);
		
		oneDialog.show();
	}

	private void deleteOneItem(int posistion) {
		Node_Folder tem = Manager_Tree_Folder.searchChildd(currentNode,
				arrFolder.get(posistion).path);
		File file = new File(arrFolder.get(posistion).path);
		deleteFile(file);
		arrFolder.remove(posistion);
		Manager_Tree_Folder.removeItem(tem);
		positionLongOnClick = -1;
	}

	private void deleteFile(File file) {
		File[] tem = file.listFiles();
		if (tem != null) {
			for (int i = 0; i < tem.length; i++) {
				try {
					deleteFile(tem[i]);
					tem[i].delete();
				} catch (Exception e) {

				}
			}
			file.delete();
		}
		file.delete();

	}

	public static long getSizeFile(File file) {
		long size = 0;
		if (file.isDirectory()) {
			for (File i : file.listFiles()) {
				size += getSizeFile(i);
			}
		} else {
			return file.length();
		}
		return size;
	}

	private void actionRename() {
		Intent intent = new Intent(getBaseContext(), NewFolder.class);
		intent.putExtra(RENAME, "rename");
		startActivityForResult(intent, QUESTION_RENAME);
	}

	private void clickBtnRename() {
		positionLongOnClick = positionIsChecked();
		if (positionLongOnClick != -1) {
			actionRename();
		} else {
			Toast.makeText(MainActivity.this, "Please choose one item!",
					Toast.LENGTH_SHORT).show();
		}
	}

	private boolean hasChecked() {
		for (ItemFolder i : arrFolder) {
			if (i.isChecked)
				return true;
		}
		return false;
	}

	private int positionIsChecked() {
		int countChecked = 0;
		int posistionChecked = -1;
		int tem = 0;
		for (ItemFolder i : arrFolder) {
			if (i.isChecked) {
				countChecked++;
				posistionChecked = tem;
			}
			tem++;
		}
		if (countChecked == 1) {
			return posistionChecked;
		} else
			return -1;
	}

	private void openItemListView(int position) {
		String pathClick = arrFolder.get(position).path;
		File file = new File(pathClick);
		if (file.isDirectory()) {
			currentNode = Manager_Tree_Folder.searchChildd(currentNode,
					pathClick);
			changeAllListViewFromRoot(pathClick);
			adapter.notifyDataSetChanged();
		} else {
			openFile(file);
		}
	}

	private void openProperties() {
		Intent i = new Intent(getBaseContext(), Properties.class);
		if (positionLongOnClick > -1) {
			String name = arrFolder.get(positionLongOnClick).name;
			String path = arrFolder.get(positionLongOnClick).path;
			File file = new File(path);
			String modify = ContentProperty.convetDate(file.lastModified());
			String parent = currentNode.getPath();
			String type = arrFolder.get(positionLongOnClick).name;
			type = type.substring(
					type.lastIndexOf(".") > -1 ? type.lastIndexOf(".") + 1
							: type.length(), type.length());
			String size = ((Long) getSizeFile(file)).toString() + " bytes";

			ContentProperty property = new ContentProperty(name, path, parent,
					type, size, modify);

			Bundle bundle = new Bundle();
			bundle.putSerializable(PROPERTY, property);

			i.putExtra(PROPERTY, bundle);
		} else {
			i.putExtra("NO", new Bundle());
		}
		positionLongOnClick = -1;
		startActivity(i);
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {

		switch (event.getAction()) {
		case KeyEvent.ACTION_DOWN:
			switch (v.getId()) {
			case R.id.editSearch:
				if (keyCode == 66) {
					if (editSearch.getText().toString().trim().equals(""))
						Toast.makeText(MainActivity.this,
								"Please input nameSearch!", Toast.LENGTH_SHORT)
								.show();
					else {
						sendSearch(editSearch.getText().toString().trim());
					}
				}
				break;

			default:
				break;
			}
			break;

		default:
			break;
		}
		return false;
	}
	
	private void sendSearch(String name) {

		Common.arr = new ArrayList<ItemFolder>();
		Common.arrSearch = new ArrayList<ItemFolder>();
		for (ItemFolder i : arrFolder) {
			Common.arr.add(new ItemFolder(i.imageIcon, i.name, i.path, i.size,
					false));
		}
		Common.arrSearch = managerTreeFolder.searchAllName(name, currentNode);
		
		if (Common.arrSearch.size() == 0)
			Toast.makeText(MainActivity.this, "Not search!!!",
					Toast.LENGTH_SHORT).show();
		else {
			Intent intent = new Intent(getBaseContext(), ActivitySearch.class);
			Log.i(TAG, "sendSearch_" + true);
			editSearch.setText("");
			editSearch.setVisibility(View.GONE);
			isSearch = false;
			startActivityForResult(intent, QUESTION_SEARCH);
		}

	}
	
	private boolean checkTickCoppy = false;
	@Override
	public void actionCoppy() {
		if ( hasChecked() && !isCutting && !isCopping ) {
			this.isCoppy = true;
			if (!checkTickCoppy ) checkTickCoppy = addArrPaste();
			Log.i(TAG, "actionCoppy_" + true);
		}
		else {
			Toast.makeText(MainActivity.this, "Please choose file need coppy Or Erro!", Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void actionCut() {
		if ( hasChecked() && !isCutting && !isCopping ) {
			this.isCut = true;
			if (!checkTickCoppy ) checkTickCoppy = addArrPaste();
			Log.i(TAG, "actionCoppy_" + true);
		}
		else {
			Toast.makeText(MainActivity.this, "Please choose file need coppy Or Erro!", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public synchronized void actionPaste() {
		if ( isCoppy && checkCuttingOrCoppying(COPPY) ){
			isCopping = true;
			isCutting = false;
			new Thread(this).start();
		}
		else {
			if (isCut && checkCuttingOrCoppying(CUT) ) {
				isCutting = true;
				isCopping = false;
				new Thread(this).start();
			}
		}
		
		
		
	}
	
	private boolean addArrPaste(){
		arrPathCoppy = new ArrayList<String>();
		for ( ItemFolder i:arrFolder ) {
			if ( i.isChecked ) {
				
				arrPathCoppy.add(i.path);
				Log.i(TAG, " addArrPaste_filePaste: " + arrPathCoppy.get(arrPathCoppy.size()-1));
			}
		}
		Log.i(TAG, "addArrPaste..size arrCoppy: "+  arrPathCoppy.size());
		if ( arrPathCoppy.size() > 0 ) return true;
		else return false;
	}
	private void updateIDforArrFolder() {
		for ( ItemFolder i:arrFolder ) {
			i.setId(adapter.getPosition(i));
		}
	}

	@Override
	public synchronized void run() {
		try {
			Node_Folder tem = currentNode;
			if ( isCoppy || isCut) pathPastOn = currentNode.getPath();
			Log.i(TAG, " actionPaste: " +pathPastOn);
			Log.i(TAG, "actionPaste_checkTict : "+ checkTickCoppy);
			if ( checkTickCoppy && (isCoppy || isCut)) {
				Log.i(TAG, "pastFile()");
				Log.i(TAG, "actionPaste_filePathOn: " + pathPastOn);
				for ( String i: arrPathCoppy ) {
					if ( pathPastOn.contains(i) ) continue;
					Log.i(TAG, "actionPaste_file needPaste: "+i);
					Manager_Tree_Folder.coppyFile(new File(i), new File(pathPastOn));
					
					Message msg = new Message();
					msg.what = ACTION_COPPY;
					msg.obj = tem.getPath();
					msg.setTarget(mHandler);
					msg.sendToTarget();
					if ( isCutting && isCut ) deleteFile(new File(i));
				}
				
				Log.i(TAG, "pasteFile..haha.");
				arrPathCoppy = new ArrayList<String>();
				pathPastOn = "";
				checkTickCoppy = false;
				managerTreeFolder.initManaget_Tree_Folder();
				if ( isCoppy && isCopping) {
					isCoppy = false;
					isCopping = false;
				}
				if ( isCutting  && isCut ) {
					isCut = false;
					isCutting = false;
				}
			}
			
		} catch (Exception e) {
			Log.i(TAG, "Threa_copp: " +e.toString());
		}
	
	}
	
	private synchronized boolean checkCuttingOrCoppying( int type ) {
		if ( type == COPPY ) {
			if ( isCutting || isCopping) {
				isCopping = false;
				return false;
			}
			else {
				isCopping = true;
				isCutting = false;
				return true;
			}
		}
		if ( type == CUT ) {
			if ( isCopping || isCutting ) {
				isCutting = false;
				return false;
			}
			else {
				isCopping = false;
				isCutting = true;
				return true;
			}
		}
		return false;
	}
}
