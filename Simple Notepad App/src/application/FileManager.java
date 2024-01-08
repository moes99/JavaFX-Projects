package application;

public class FileManager {
	private FileNode head;
	private FileNode tail;
	private int openFileCount;
	private static int filesCreated = 0;
	
	public FileManager() {
		head = null;
		tail = null;
		openFileCount = 0;
	}
	
	public int getOpenFileCount() {
		return openFileCount;
	}
	
	public int getFilesCreated() {
		return filesCreated;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void createNewFile(FileBtn te) {
		FileNode fn = new FileNode(te);
		if(isEmpty()) {
			head = fn;
			tail = fn;
			head.setNext(tail);
			head.setPrev(tail);
			tail.setPrev(head);
			tail.setNext(head);
		}
		else{
			tail.setNext(fn);
			fn.setPrev(tail);
			tail = fn;
			tail.setNext(head);
		}
		openFileCount++;
		filesCreated++;
	}
	
	public void closeFile(FileBtn openFile) {
		if(openFileCount == 1) {
			head = null;
			tail = null;
		}
		else if(head.getFile() == openFile) {
			head = head.getNext();
			head.setPrev(tail);
		}
		else if(tail.getFile() == openFile) {
			tail = tail.getPrev();
			tail.setNext(head);
		}
		else {
			FileNode current = head;
			while(current != tail) {
				if(current.getFile() == openFile) {
					current.getPrev().setNext(current.getNext());
					current.getNext().setPrev(current.getPrev());
					break;
				}
				current = current.getNext();
			}
		}
		openFileCount--;
	}
	
	public FileBtn navigateToNextFile(FileBtn targetFile) {
		if(!isEmpty()) {
			FileNode current = head;
			while(current != tail) {
				if(current.getFile() == targetFile) {
					return current.getNext().getFile();
				}
				current = current.getNext();
			}
			if(tail.getFile() == targetFile) {
				return head.getFile();
			}
			return null;
		}
		return null;
	}
}