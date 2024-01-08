package application;
import javafx.scene.control.Button;

public class FileBtn extends Button{
	private TextEditor te;
	private boolean savedBefore;
	
	public FileBtn(String name) {
		super(name);
		te = new TextEditor();
		savedBefore = false;
	}
	
	public TextEditor getTextEditor() {
		return te;
	}

	public boolean isSavedBefore() {
		return savedBefore;
	}

	public void setSavedBefore(boolean savedBefore) {
		this.savedBefore = savedBefore;
	}
}