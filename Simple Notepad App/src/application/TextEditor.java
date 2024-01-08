package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TextEditor extends LinkedStrings{
	private LinkedStrings removed;
	private BinarySearchTree bst;
	private RandomAccessFile saveFile;
	private String saveFileName;
	
	public TextEditor() {
		super();
		removed = new LinkedStrings();
		bst = new BinarySearchTree();
		saveFile = null;
		saveFileName = "";
	}
	
	public void clear() {
		super.clear();
		bst.clear();
	}
	
	@Override
	public String undo() {
		String removedString = super.undo();
		if(!removedString.equals("")) {
			removed.appendText(removedString);
			bst.remove(removedString);
		}
		return removedString;
	}
	
	@Override
	public void appendText(String str) {
		super.appendText(str);
		bst.insert(str);
	}
	
	public void redo() {
		if(removed.isEmpty()) {
			System.out.println("You didn't undo any text!");
		}
		else {
			String removedString = removed.undo();
			appendText(removedString);
			bst.insert(removedString);
		}
	}
	
	public int searchWord(String str) {
		if(isEmpty()) {
			return 0;
		}
		else {
			return bst.searchTree(str);
		}
	}
	
	@Override
	public void replace(String oldWord, String newWord) {
		super.replace(oldWord, newWord);
		bst.replace(oldWord, newWord);
	}
	
	@Override
	public void replaceAll(String oldWord, String newWord) {
		super.replaceAll(oldWord, newWord);
		int count = bst.searchTree(oldWord);
		while(count-- > 0) {
			bst.replace(oldWord, newWord);
		}
	}
	
	public void setSaveFile(String fileName) throws FileNotFoundException{
		try {
			saveFileName = fileName + ".txt";
			saveFile = new RandomAccessFile(saveFileName,"rw");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			throw new FileNotFoundException("File does not exist!");
		}
	}
	
	public String getSaveFileName() {
		return saveFileName;
	}
	
	public void readFromFile() {
		try {
			double fileLength = saveFile.length();
			while(saveFile.getFilePointer() != fileLength) {
				appendText(saveFile.readUTF());
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void saveToFile() {
		if(!isEmpty()) {
			try {
				String textToSave = getText().trim();
				String[] stringsToSave = textToSave.split(" ");
				saveFile.setLength(0);
				for(String s: stringsToSave) {
					saveFile.writeUTF(s);
				}
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}