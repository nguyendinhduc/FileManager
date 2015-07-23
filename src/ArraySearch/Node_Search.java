package ArraySearch;

import com.ducnd.exercise11_filemanager.Node_Folder;

public class Node_Search {
	private Node_Folder nodeOld;
	private Node_Folder nodeNew;
	public Node_Search() {
		nodeNew = new Node_Folder();
		nodeOld = new Node_Folder();
	}
	public Node_Search (Node_Folder nodeOld, Node_Folder nodeNew ) {
		this.nodeOld = nodeOld;
		this.nodeNew = nodeNew;
	}
	public Node_Folder getNodeOld() {
		return nodeOld;
	}
	public void setNodeOld(Node_Folder nodeOld) {
		this.nodeOld = nodeOld;
	}
	public Node_Folder getNodeNew() {
		return nodeNew;
	}
	public void setNodeNew(Node_Folder nodeNew) {
		this.nodeNew = nodeNew;
	}
	
}
