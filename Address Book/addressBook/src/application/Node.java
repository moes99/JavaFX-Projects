package application;

public class Node {
	private Record record;
	private Node next;
	private Node previous;
	
	public Node(Record r) {
		this.record = r;
		next = null;
		previous = null;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}
}
