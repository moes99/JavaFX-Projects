package application;

import javafx.scene.control.Button;

public class ButtonWithRecord extends Button{
	private Node record;
	
	public ButtonWithRecord() {
		super();
		record = null;
	}
	
	public ButtonWithRecord(String str, Node record) {
		super(str);
		this.record = record;
	}
	
	public Node getRecord() {
		return record;
	}
}
