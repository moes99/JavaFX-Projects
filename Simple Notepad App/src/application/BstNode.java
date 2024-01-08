package application;

public class BstNode {
	private BstNode left, right;
	private int count;
	private String str;
	
	public BstNode() {
		left = null;
		right = null;
		count = 0;
		str = "";
	}
	
	public BstNode(String str) {
		this.left = null;
		this.right = null;
		this.count = 1;
		this.str = str;
	}

	public BstNode getLeft() {
		return left;
	}

	public void setLeft(BstNode left) {
		this.left = left;
	}

	public BstNode getRight() {
		return right;
	}

	public void setRight(BstNode right) {
		this.right = right;
	}

	public int getCount() {
		return count;
	}

	public void incrementCount() {
		this.count++;
	}
	
	public void decrementCount() {
		this.count--;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}