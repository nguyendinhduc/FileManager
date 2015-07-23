package com.ducnd.exercise11_filemanager;

public class Node_Folder {
	private Node_Folder parent = null;
	private Node_Folder child = null;
	private Node_Folder nextSibling = null;
	private String name, path;
	public Node_Folder(String name, String path) {
		this.name = name;
		this.path = path;
	}
	public Node_Folder() {
		
	}
	public Node_Folder getParent() {
		return parent;
	}
	public void setParent(Node_Folder parent) {
		this.parent = parent;
	}
	public Node_Folder getChild() {
		return child;
	}
	public void setChild(Node_Folder child) {
		this.child = child;
	}
	public Node_Folder getNextSibling() {
		return nextSibling;
	}
	public void setNextSibling(Node_Folder nextSibling) {
		this.nextSibling = nextSibling;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
