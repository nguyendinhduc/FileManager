package com.ducnd.exercise11_filemanager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.ducnd.common.Common;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.FocusFinder;

public class Manager_Tree_Folder {
	@SuppressWarnings("unused")
	private Context mContext;
	public static final String PATH_EXTERNAL_DATA =  Environment.getExternalStorageDirectory().getPath();

	private static final String TAG = "Managet_Tree_Folder";
	private Node_Folder root;
	public Manager_Tree_Folder(Context mContext) {
		this.mContext = mContext;
		Log.i(TAG, "Manager_Tree_Folder_path root: " + "this");
		Log.i(TAG, "Manager_Tree_Folder_path root: " + PATH_EXTERNAL_DATA);

	}
	public void initManaget_Tree_Folder() {
		this.root = new Node_Folder("root", PATH_EXTERNAL_DATA);
		buildTreeFolder(this.root);
	}
	public Node_Folder getRoot() {
		return this.root;
	}

	private void buildTreeFolder ( Node_Folder root ) {
		File fileRoot = new File(root.getPath());
		if ( fileRoot!=null && !(fileRoot.isFile()) ) {
			File[] listFileRoot = fileRoot.listFiles();
			if ( listFileRoot.length >= 1 ) {
				Node_Folder child = new Node_Folder(listFileRoot[0].getName(), listFileRoot[0].getPath());
				root.setChild(child);
				child.setParent(root);
				buildTreeFolder(child);
				Node_Folder tem = child;	
				for ( int i = 1; i < listFileRoot.length; i++ ) {
					Node_Folder need = new Node_Folder(listFileRoot[i].getName(), listFileRoot[i].getPath());
					buildNextSibling(root, tem, need);
					tem = need;
					buildTreeFolder(need);
				}

			}
		}
	}
	private void buildNextSibling( Node_Folder root, Node_Folder nodeCurrent, Node_Folder need ) {
		nodeCurrent.setNextSibling(need);
		need.setParent(root);
	}
	public void preOrder(Node_Folder root) {
		Log.i(TAG, "preOrder_Name: " + root.getPath());
		if ( root.getChild() != null ) {
			Node_Folder child = root.getChild();
			preOrder(child);
			Node_Folder tem = child;
			while(tem.getNextSibling()!=null) {
				preOrder(tem.getNextSibling());
				tem = tem.getNextSibling();
			}

		}
	}
	public static Node_Folder searchChildd ( Node_Folder root, String pathChild ) {
		//Node_File tem = new Node_File();
		if ( root != null ) {
			Node_Folder child = root.getChild();
			if ( child != null ) {
				if ( child.getPath().equals(pathChild) ) return child;
				else {
					while ( child.getNextSibling() != null ) {
						if ( child.getNextSibling().getPath().equals(pathChild)) return child.getNextSibling();
						child = child.getNextSibling();
					}
				}
			}
		}
		return null;
	}
	public ArrayList<String> getPathListChild( Node_Folder root ) {
		ArrayList<String> pathListChild = new ArrayList<String>();
		if ( root != null ) {
			Node_Folder tem = root.getChild();
			if ( tem != null ) {
				pathListChild.add(tem.getPath());

				while (tem.getNextSibling() != null ) {
					pathListChild.add(tem.getNextSibling().getPath());
					tem = tem.getNextSibling();
				}
			}

		}
		return pathListChild;

	}
	public static String getTailFile( File file ) {
		String tem = "";
		if ( file.exists() ) {
			tem = file.getName();
			//			if ( tem.length() > 4 ) {
			//				tem = tem.substring(tem.length()-4, tem.length());
			//			}
			if ( tem.indexOf('.') > -1 ) {
				return tem.substring(tem.lastIndexOf('.'), tem.length());
			}
		}
		return tem;
	}

	public static void addItem( Node_Folder parent, Node_Folder itemAdd ) {
		Node_Folder tem = parent.getChild();
		if ( tem != null ) {
			while ( tem.getNextSibling() != null ) tem = tem.getNextSibling();
			tem.setNextSibling(itemAdd);
			tem.getNextSibling().setParent(parent);
		}
		else {
			parent.setChild(itemAdd);
			parent.getChild().setParent(parent);
		}
	}
	public static void removeItem( Node_Folder itemRemove ) {
		itemRemove = null;
	}
	public ArrayList<ItemFolder> searchAllName(String name, Node_Folder root) {

		ArrayList<ItemFolder> arr = new ArrayList<ItemFolder>();
		if ( root != null ) {
			//			if ( root.getName().contains(name) ) {
			//				arr.add(new ItemFolder(null, root.getName(), root.getPath(),null, false));
			//			}
			File[] listFile = new File(root.getPath()).listFiles();
			if ( listFile != null ) {
				for ( File i: listFile ) {
					if ( i.getName().contains(name) ) {
						arr.add(new ItemFolder(null, i.getName(), i.getPath(), null, false));
					}
				}
			}
			if ( root.getChild() != null ) {
				arr.addAll(searchAllName(name, root.getChild()));
				Node_Folder tem = root.getChild();
				while (tem.getNextSibling() != null) {
					arr.addAll(searchAllName(name, tem.getNextSibling()));
					tem = tem.getNextSibling();
				}

			}
		}
		return arr;
	}
	public static Node_Folder searchNode( Node_Folder root, String path ) {

		Node_Folder result = null;
		if ( root != null ) {
			if ( root.getPath().equals(path)) {
				return root;
			}
			if ( root.getChild() != null ) {
				result = searchNode(root.getChild(), path);
				if ( result != null ) return result;
				else {
					Node_Folder tem2 = root.getChild();
					while( tem2.getNextSibling() != null ) {
						result = searchNode(tem2.getNextSibling(), path);
						if ( result != null ) return result;
						else tem2 = tem2.getNextSibling();
					}
				}
			}
		}
		return result;

	}
	public static String getNameFormePath( String path ) {
		if ( path.equals("") ) return null;
		String name = path.substring(path.lastIndexOf('/') > -1 ? path.lastIndexOf('/')+1 : path.length(), path.length());
		Log.i(TAG, "getNameFormePath_"+name);
		return name;
	}
	public static void updateTree( Node_Folder root, Node_Folder need , String pathPastOn) {
		//parent is path paste in

		String nameNeed = need.getName();
		String pathPasted = pathPastOn + "/" + nameNeed;

		Node_Folder tem = searchNode(root, pathPastOn);

		if ( tem.getChild() == null ) {
			Log.i(TAG, "updateTree....on not child: " + pathPastOn);
			Log.i(TAG, "updateTree....ed not child:" + pathPasted);

			tem.setChild(new Node_Folder(nameNeed, pathPasted));
			tem.getChild().setParent(tem);

			File fileCoppy = new File(need.getPath());
			if ( !fileCoppy.isFile() ) {
				new File(tem.getChild().getPath()).mkdir();
				try {
					File[] listSub = new File(need.getPath()).listFiles();
					for ( File i:listSub ) {
						updateTree(root, new Node_Folder(i.getName(),i.getPath()), pathPasted);
					}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
			else {
				Log.i(TAG, "updateTree....on: " + pathPastOn);
				Log.i(TAG, "updateTree....ed: " + pathPasted);
				//if ( fileCoppy.exists() )
					//coppyFile(tem.getChild().getPath(), fileCoppy);
			}	
		}
		else {
			Log.i(TAG, "updateTree....on: " + pathPastOn);
			Log.i(TAG, "updateTree....ed: " + pathPasted);

			while ( tem.getNextSibling() != null ) tem = tem.getNextSibling();
			tem.setNextSibling(need);
			updateTree(root, need, pathPastOn);
		}
	}

	public static void coppyFile( File fileCoppy, File fileRootPaste ) {
		if ( fileRootPaste.exists() && fileCoppy.exists() ) {
			File filePaste = new File(fileRootPaste, fileCoppy.getName());
			Log.i(TAG, "coppyFile_file paste: "+filePaste.getPath());
			Log.i(TAG, "coppyFile_file coppy: " + fileCoppy.getPath());
		
			if ( !filePaste.exists() && fileCoppy.exists()) {
				if ( fileCoppy.isDirectory()) {
					filePaste.mkdir();
					File[] listFile = fileCoppy.listFiles();
					if ( listFile != null ) {
						for ( File f:listFile ) {
							coppyFile(f, filePaste);
						}
					}
				}
				else {
					try {
						FileInputStream in = new FileInputStream(fileCoppy);
						DataInputStream data = new DataInputStream(in);

						FileOutputStream out = new FileOutputStream(filePaste);
						DataOutputStream outData = new DataOutputStream(out);

						byte[] b = new byte[2014];
						int len;
						while ( (len = data.read(b)) > 0 ) {
							outData.write(b, 0, len);
							len = 0;
						}
						data.close();
						outData.close();
						in.close();
						out.close();
						Log.i(TAG, "coppyFile_...");

					} 
					catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
	}

}

