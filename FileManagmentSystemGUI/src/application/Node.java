package application;

public class Node {
	private String text;
	private Node next;
	
	public Node(String t) {
		text = t;
		next = null;
	}
	
	public String getText() {
		return text;
	}
	
	public void setString(String t) {
		text = t;
	}
	
	public Node getNext() {
		return next;
	}
	
	public void setNext(Node n) {
		next = n;
	}
}