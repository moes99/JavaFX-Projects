package application;

public class CDLL {
	private Node head;
	private Node tail;
	
	public CDLL() {
		head = null;
		tail = null;
	}
	
	public Node getHead() {
		return head;
	}
	
	public Node getTail() {
		return tail;
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void addRecord(Record r) {
		Node p = new Node(r);
		if(isEmpty()) {
			head = p;
			tail = p;
			head.setNext(tail);
			head.setPrevious(tail);
			tail.setNext(head);
			tail.setPrevious(head);
		}
		else {
			tail.setNext(p);
			p.setPrevious(tail);
			tail = p;
			tail.setNext(head);
			head.setPrevious(tail);
		}
	}
}
