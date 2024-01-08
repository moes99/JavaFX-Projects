package application;

public class FileNode {
	private FileBtn file;
	private FileNode next;
	private FileNode prev;
	
	public FileNode(FileBtn btn) {
		file = btn;
		next = null;
		prev = null;
	}

	public FileBtn getFile() {
		return file;
	}

	public void setFile(FileBtn file) {
		this.file = file;
	}

	public FileNode getNext() {
		return next;
	}

	public void setNext(FileNode next) {
		this.next = next;
	}

	public FileNode getPrev() {
		return prev;
	}

	public void setPrev(FileNode prev) {
		this.prev = prev;
	}
}