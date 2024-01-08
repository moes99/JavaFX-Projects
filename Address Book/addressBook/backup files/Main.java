package application;
	
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		//Name text and textfield
		Text nameText = new Text("Name:");
		TextField nameField = new TextField();
		
		//Street text and textfield
		Text streetText = new Text("Street:");
		TextField streetField = new TextField();
		
		//City text and textfield
		Text cityText = new Text("City:");
		TextField cityField = new TextField();
		
		//State text and textfield
		Text stateText = new Text("State:");
		TextField stateField = new TextField();
		
		//Zip text and textfield
		Text zipText = new Text("ZIP:");
		TextField zipField = new TextField();
		
		//Buttons
		HBox buttonBox = new HBox();
		Button add = new Button("Add");
		Button first = new Button("First");
		Button next = new Button("Next");
		Button prev = new Button("Previous");
		Button last = new Button("Last");
		Button clear = new Button("Clear");
		buttonBox.getChildren().addAll(add,first,next,prev,last,clear);
		buttonBox.setSpacing(10);
		buttonBox.setFillHeight(true);
		buttonBox.setAlignment(Pos.CENTER);
		
		//Error message
		Text err = new Text();
		err.setFont(Font.font("Arial",FontWeight.BOLD,FontPosture.ITALIC,12));
		err.setFill(Color.RED);
		
		//Main pane
		GridPane mainPane = new GridPane();
		mainPane.add(nameText, 0, 0);
		mainPane.add(streetText, 0, 1);
		mainPane.add(cityText, 0, 2);
		mainPane.add(nameField, 1, 0, 5, 1);
		mainPane.add(streetField, 1, 1, 5, 1);
		mainPane.add(cityField, 1, 2);
		mainPane.add(stateText, 2, 2);
		mainPane.add(stateField, 3, 2);
		mainPane.add(zipText, 4, 2);
		mainPane.add(zipField, 5, 2);
		mainPane.add(buttonBox, 0, 3, 6, 1);
		mainPane.add(err, 0, 4, 6, 1);
		mainPane.setVgap(10);
		mainPane.setHgap(10);
		mainPane.setPadding(new Insets(10,10,10,10));
		
		//Scene and stage
		Scene mainScene = new Scene(mainPane);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Address Book");
		primaryStage.show();
		
		//TextFields Array
		TextField[] textfields = {nameField,streetField,cityField,stateField,zipField};
		
		//Event handlers
		RandomAccessFile addresses = new RandomAccessFile("addresses.dat", "rw");
		Stack<Long> positions = new Stack<Long>();
		Stack<Long> beforePts = new Stack<Long>();
		Queue<Long> findLastPtr = new LinkedList<Long>();
		findLastElement(addresses,findLastPtr,addresses.length());
		Long lastElementPtr = findLastPtr.remove();
		beforePts.push(lastElementPtr);
		
		add.setOnAction(e ->{
			boolean missingDetails = false;
			for(TextField tf: textfields) {
				if(tf.getText().equals("")) {
					err.setText("Some details are missing!");
					missingDetails = true;
					break;
				}
			}
			if(!missingDetails) {
				try {
					addresses.seek(addresses.length());
					for(TextField tf: textfields) {
						addresses.writeUTF(tf.getText());
						addresses.writeUTF("|");
						tf.setText("");
					}
					err.setText("Entry saved in address book!");
					updateLastElementPtr(lastElementPtr, addresses, findLastPtr);
				} catch (IOException e1) {
					err.setText("File Not Found!");
				}
			}
		});
		
		first.setOnAction(e ->{
		    try {
		        if(addresses.length() != 0) {
		            addresses.seek(0);
		            positions.push(0L);
		            readData(textfields, addresses);
		            err.setText("");
		        }
		        else {
		            err.setText("Address book is empty!");
		        }
		    } catch (IOException e1) {
		        err.setText("File Not Found!");
		    }
		});
		
		clear.setOnAction(e ->{
			for(TextField tf: textfields) {
				tf.setText("");
			}
		});
		
		next.setOnAction(e ->{
			try {
				if(addresses.length() != 0) {
					addresses.seek(addresses.getFilePointer() == addresses.length()? 0 : addresses.getFilePointer());
					positions.push(addresses.getFilePointer());
					readData(textfields, addresses);
				}
				else {
					err.setText("Address book is empty!");
				}
			}
			catch(IOException e1) {
				err.setText("File Not Found!");
			}
		});
		
		prev.setOnAction(e -> {
		    try {
		    	if(addresses.length() != 0) {
		    		if(positions.isEmpty()) {
		    			Long beforeCurrent = findBeforeCurrent(addresses, beforePts);
		    			addresses.seek(beforeCurrent);
		    		}
		    		else {
				    	positions.pop();
				    	addresses.seek(positions.isEmpty()? lastElementPtr:positions.peek());
		    		}
			    	readData(textfields, addresses);
		    	}
		    	else {
		    		err.setText("Address book is empty!");
		    	}
		    } 
		    catch (IOException e1) {
		    	err.setText("File Not Found!");
		    }
		});
		
		last.setOnAction(e ->{
			try {
				if(addresses.length() == 0) {
					err.setText("Address book is empty!");
				}
				else {
					addresses.seek(lastElementPtr);
					readData(textfields, addresses);
				}
			}
			catch (IOException e1) {
				err.setText("File Not Found!");
		    }
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void readData(TextField[] textfields, RandomAccessFile addresses) throws IOException {
		for(int i = 0; i < textfields.length; i++) {
            StringBuilder data = new StringBuilder();
            while(addresses.getFilePointer() < addresses.length()){
                String temp = addresses.readUTF();
                if(temp.equals("|")){
                    break;
                }
                data.append(temp);
            }
            textfields[i].setText(data.toString());
        }
	}
	
	public void findLastElement(RandomAccessFile addresses, Queue<Long> findLastPtr, Long currentPtr) throws IOException {
		if(addresses.length() != 0) {
			int count = 0;
			findLastPtr.add(addresses.getFilePointer());
			String temp = "";
			while(addresses.getFilePointer() < currentPtr) {
				temp = addresses.readUTF();
				count = temp.equals("|")? count+1 : count;
				if(findLastPtr.size() == 2) {
					findLastPtr.remove();
				}
				if(count == 5) {
					findLastPtr.add(addresses.getFilePointer());
					count = 0;
				}
			}
		}
	}
	
	public void updateLastElementPtr(Long lastPtr, RandomAccessFile file, Queue<Long> q) throws IOException {
		findLastElement(file, q, file.length());
		lastPtr = q.remove();
	}
	
	public Long findBeforeCurrent(RandomAccessFile addresses, Stack<Long> ptrs) throws IOException {
		Queue<Long> q = new LinkedList<Long>();
		if(addresses.length() != 0) {
			int count = 0;
			q.add(0L);
			Long originalPtr = addresses.getFilePointer();
			addresses.seek(0);
			String temp = "";
			while(addresses.getFilePointer() < ptrs.peek()) {
				temp = addresses.readUTF();
				count = temp.equals("|")? count+1 : count;
				if(q.size() == 2) {
					q.remove();
				}
				if(count == 5) {
					q.add(addresses.getFilePointer());
					count = 0;
				}
			}
			addresses.seek(originalPtr);
			ptrs.pop();
			ptrs.push(q.remove());
			return ptrs.peek();
		}
		return 0L;
	}
}
