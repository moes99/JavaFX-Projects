package application;

import java.io.IOException;
import java.io.RandomAccessFile;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application{
	
	public void start(Stage primaryStage) throws IOException {
		//Name text and textfield
		Text nameText = new Text("Name:");
		nameText.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		TextField nameField = new TextField();
		nameField.setFocusTraversable(false);
		
		//Street text and textfield
		Text streetText = new Text("Street:");
		streetText.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		TextField streetField = new TextField();
		streetField.setFocusTraversable(false);
		
		//City text and textfield
		Text cityText = new Text("City:");
		cityText.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		TextField cityField = new TextField();
		cityField.setFocusTraversable(false);
		
		//State text and textfield
		Text stateText = new Text("State:");
		stateText.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		TextField stateField = new TextField();
		stateField.setFocusTraversable(false);
		
		//Zip text and textfield
		Text zipText = new Text("ZIP:");
		zipText.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		TextField zipField = new TextField();
		zipField.setFocusTraversable(false);
		
		//Buttons
		HBox buttonBox = new HBox();
		Button add = new Button("Add");
		add.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		add.setFocusTraversable(false);
		Button first = new Button("First");
		first.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		first.setFocusTraversable(false);
		Button next = new Button("Next");
		next.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		next.setFocusTraversable(false);
		Button prev = new Button("Previous");
		prev.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		prev.setFocusTraversable(false);
		Button last = new Button("Last");
		last.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		last.setFocusTraversable(false);
		Button clear = new Button("Clear");
		clear.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		clear.setFocusTraversable(false);
		buttonBox.getChildren().addAll(add,first,next,prev,last,clear);
		buttonBox.setSpacing(20);
		buttonBox.setFillHeight(true);
		buttonBox.setAlignment(Pos.CENTER);
		
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
		mainPane.setVgap(10);
		mainPane.setHgap(10);
		mainPane.setPadding(new Insets(10,10,10,10));
		VBox detailsPane = new VBox();
		Text details = new Text("Address Details");
		details.setStyle("-fx-background-color: lightgreen;");
		details.setFont(Font.font("Arial",FontWeight.BOLD,15));
		details.setTextAlignment(TextAlignment.CENTER);
		StackPane detPane = new StackPane(details);
		detPane.setPrefHeight(30);
		detPane.setBorder(new Border(new BorderStroke(Color.AQUA,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(3))));
		detailsPane.getChildren().addAll(detPane,mainPane);
		detailsPane.setSpacing(10);
		detailsPane.setPadding(new Insets(10,10,10,10));
		
		//Arrays and Data Structures
		TextField[] textfields = {nameField,streetField,cityField,stateField,zipField};
		String[] dataStrings = new String[5];
		
		//Reading file into linkedlist
		RandomAccessFile addresses = new RandomAccessFile("addresses.dat","rw");
		CDLL records = new CDLL();
		Node[] nodes = new Node[4];
		while(addresses.getFilePointer() != addresses.length()) {
			for(int i = 0; i < dataStrings.length; i++) {
	            StringBuilder data = new StringBuilder();
	            while(addresses.getFilePointer() < addresses.length()){
	                String temp = addresses.readUTF();
	                if(temp.equals("|")){
	                    break;
	                }
	                data.append(temp);
	            }
	            dataStrings[i] = data.toString();
	        }
			Record r = new Record(dataStrings[0], dataStrings[1], dataStrings[2], dataStrings[3], dataStrings[4]);
			records.addRecord(r);
		}
		nodes[0] = records.getHead(); //constant pointer for first address
		nodes[1] = records.getHead(); //variable pointer for navigating the address book
		nodes[2] = records.getTail(); //constant pointer for last address
		nodes[3] = records.getHead(); //iterator for while loop below
		
		//Adding records to buttons
		ObservableList<ButtonWithRecord> allButtons = FXCollections.observableArrayList();
		if(!records.isEmpty()) { //if the linked list is not empty, fill the listview with pressable buttons
			while(nodes[3] != records.getTail()) {
				ButtonWithRecord br = makeButton(nodes[3], nodes, textfields);
				allButtons.add(br);
				nodes[3] = nodes[3].getNext();
			}
			ButtonWithRecord br = makeButton(nodes[3], nodes, textfields); //make button for tail element
			allButtons.add(br);
		}
		
		//Listview of all addresses
		VBox rightPane = new VBox();
		Text title = new Text("All Available Addresses");
		title.setFont(Font.font("Arial",FontWeight.BOLD,15));
		title.setTextAlignment(TextAlignment.CENTER);
		StackPane titlePane = new StackPane(title);
		titlePane.setPrefHeight(30);
		titlePane.setBorder(new Border(new BorderStroke(Color.LIGHTCORAL,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(3))));
		ListView<ButtonWithRecord> allAddresses = new ListView<ButtonWithRecord>(allButtons);
		allAddresses.setPrefHeight(155);
		allAddresses.setPrefWidth(190);
		allAddresses.setFocusTraversable(false);
		rightPane.setSpacing(10);
		rightPane.setAlignment(Pos.CENTER);
		rightPane.getChildren().addAll(titlePane,allAddresses);
		rightPane.setPadding(new Insets(10,10,10,10));
		
		//Ultimate pane
		HBox ultimatePane = new HBox();
		ultimatePane.getChildren().addAll(rightPane,detailsPane);
		ultimatePane.setSpacing(10);
		
		//Scene and stage
		Scene mainScene = new Scene(ultimatePane);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Address Book");
		primaryStage.show();
		
		//Nodes for popup
		VBox main = new VBox();
		Text notif = new Text();
		notif.setTextAlignment(TextAlignment.CENTER);
		notif.setFont(Font.font(15));
		notif.setWrappingWidth(190);
		Button ok = new Button("OK");
		main.getChildren().addAll(notif,ok);
		main.setSpacing(20);
		main.setAlignment(Pos.CENTER);
		main.setPadding(new Insets(10,10,10,10));
		Scene alertScene = new Scene(main,200,100);
		Stage alertStage = new Stage();
		alertStage.setTitle("Alert");
		alertStage.setScene(alertScene);
		ok.setOnAction(e ->{
			alertStage.close();
		});
		
		//Event handlers
		clear.setOnAction(e ->{
			for(TextField tf: textfields) {
				tf.setText("");
			}
		});
		
		add.setOnAction(e ->{
			boolean missingDetails = false;
			for(TextField tf: textfields) {
				if(tf.getText().equals("")) {
					popup("Some details are missing!",notif,alertStage);
					missingDetails = true;
					break;
				}
			}
			if(!missingDetails) {
				try {
					Integer.parseInt(textfields[4].getText());
					try {
						addresses.seek(addresses.length());
						for(int i = 0; i < textfields.length; i++) {
							addresses.writeUTF(textfields[i].getText());
							dataStrings[i] = textfields[i].getText();
							addresses.writeUTF("|");
							textfields[i].setText("");
						}
						Record r = new Record(dataStrings[0], dataStrings[1], dataStrings[2], dataStrings[3], dataStrings[4]);
						records.addRecord(r);
						if(nodes[0] == null) { //updated nodes if file is empty
							nodes[0] = records.getHead();
							nodes[1] = records.getTail();
						}
						nodes[2] = records.getTail();
						ButtonWithRecord rb = makeButton(new Node(r), nodes, textfields);
						allButtons.add(rb);
						allAddresses.setItems(allButtons);
						rb.requestFocus();
						rb.setStyle("-fx-background-color: lightgreen;");
					} 
					catch (IOException e1) {
						popup("File Not Found!",notif,alertStage);
					}
				}
				catch(NumberFormatException ex) {
					popup("ZIP code can only be numeric!",notif,alertStage);
				}
			}
		});
		
		first.setOnAction(e ->{
			if(!records.isEmpty()) {
				setData(nodes[0],textfields);
				nodes[1] = nodes[0];
			}
			else {
				popup("Address book is empty!",notif,alertStage);
			}
		});
		
		next.setOnAction(e ->{
			if(!records.isEmpty()) {
				nodes[1] = nodes[1].getNext();
				setData(nodes[1],textfields);
			}
			else {
				popup("Address book is empty!",notif,alertStage);
			}
		});
		
		prev.setOnAction(e ->{
			if(!records.isEmpty()) {
				nodes[1] = nodes[1].getPrevious();
				setData(nodes[1], textfields);
			}
			else {
				popup("Address book is empty!",notif,alertStage);
			}
		});
		
		last.setOnAction(e ->{
			if(!records.isEmpty()) {
				setData(nodes[2], textfields);
				nodes[1] = nodes[2];
			}
			else {
				popup("Address book is empty!",notif,alertStage);
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void setData(Node node, TextField[] tfs) {
		tfs[0].setText(node.getRecord().getName());
		tfs[1].setText(node.getRecord().getStreet());
		tfs[2].setText(node.getRecord().getCity());
		tfs[3].setText(node.getRecord().getState());
		tfs[4].setText(node.getRecord().getZip());
	}
	
	public ButtonWithRecord makeButton(Node node, Node[] nodes, TextField[] textfields) {
		ButtonWithRecord br = new ButtonWithRecord(node.getRecord().getName(),node);
		br.setStyle("-fx-background-color: transparent");
		br.setFocusTraversable(false);
		br.setPrefWidth(170);
		br.setWrapText(true);
		br.setTextAlignment(TextAlignment.CENTER);
		br.setAlignment(Pos.CENTER);
		br.setFont(Font.font("Calibri",FontWeight.BOLD,14));
		br.setOnAction(e ->{
			nodes[1] = br.getRecord();
			setData(br.getRecord(), textfields);
		});
		br.setOnMouseEntered(e ->{
			br.setStyle("-fx-background-color: lightcoral;");
		});
		br.setOnMouseExited(e ->{
			br.setStyle("-fx-background-color: transparent;");
		});
		return br;
	}
	
	public void popup(String notification, Text notifText, Stage popUpStage) {
		notifText.setText(notification);
		popUpStage.show();
	}
}
