package application;
import javafx.scene.control.Button;

public class ActionButton extends Button{
	
	private TextEditor te;
	
	public ActionButton(String name){
		super(name);
	}
	
	public boolean saveFile(TextEditor te) {
		this.te = te;
		if(te.getSaveFileName().isBlank()) {
			return false;
		}
		else {
			te.saveToFile();
			return true;
		}
	}
	
	public TextEditor closeFile() {
		return te;
	}
	
	public String undo(TextEditor te) {
		this.te = te;
		te.undo();
		return te.getText();
	}
	
	public String redo(TextEditor te) {
		this.te = te;
		te.redo();
		return te.getText();
	}
}