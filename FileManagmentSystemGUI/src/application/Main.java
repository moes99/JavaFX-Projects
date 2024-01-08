package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application {
	
	FileManager manager = new FileManager();
	TextArea mainTextArea = new TextArea();
	FileBtn fileInFocus;
	
	@Override
	public void start(Stage primaryStage) {
		//Global variables
		mainTextArea.setFocusTraversable(false);
		
		//Global Buttons and their pane
		HBox glblBtnsPane = new HBox();
		//filesPane.setPadding(new Insets(10,10,10,10));
		glblBtnsPane.setSpacing(10);
		Button openFile = new Button("Open");
		openFile.setMinWidth(Region.USE_PREF_SIZE);
		openFile.setFocusTraversable(false);
		Button newFile = new Button("New");
		newFile.setMinWidth(Region.USE_PREF_SIZE);
		newFile.setFocusTraversable(false);
		
		//files and their panes
		HBox filesPane = new HBox();
		filesPane.setSpacing(10);
		filesPane.setPadding(new Insets(1,5,10,5));
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(filesPane);
		scrollPane.setFitToHeight (true);
		scrollPane.setVbarPolicy (ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy (ScrollPane.ScrollBarPolicy.ALWAYS);
		
		glblBtnsPane.getChildren().addAll(openFile,newFile,scrollPane);
		
		//File specific buttons and their pane
		HBox buttonsPane = new HBox();
		//buttonsPane.setPadding(new Insets(10,10,10,10));
		buttonsPane.setSpacing(10);
		ActionButton save = new ActionButton("Save");
		save.setFocusTraversable(false);
		ActionButton undo = new ActionButton("Undo");
		undo.setFocusTraversable(false);
		ActionButton redo = new ActionButton("Redo");
		redo.setFocusTraversable(false);
		ActionButton find = new ActionButton("Find and Replace");
		find.setFocusTraversable(false);
		ActionButton close = new ActionButton("Close");
		close.setFocusTraversable(false);
		buttonsPane.getChildren().addAll(save,undo,redo,find,close);
		
		//Common elements
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initOwner(primaryStage);
		
		//Open popup
		String openTitle = "Opening file";
		Text openQuestion = new Text("Enter file name to open without the \".txt\" extension.");
		TextField openField = new TextField();
		Button doneOpen = new Button("Done");
		doneOpen.setFocusTraversable(false);
		VBox openPane = new VBox();
		openPane.getChildren().addAll(openQuestion,openField,doneOpen);
		openPane.setSpacing(10);
		openPane.setPadding(new Insets(10,10,10,10));
		openPane.setAlignment(Pos.CENTER);
		Scene openScene = new Scene(openPane);
		
		//Save popup
		String saveTitle = "Saving file";
		Text saveQuestion = new Text("Enter file name. Currently, it is ");
		TextField saveField = new TextField();
		saveField.setFocusTraversable(false);
		Button doneSave = new Button("Done");
		doneSave.setFocusTraversable(false);
		VBox savePane = new VBox();
		savePane.getChildren().addAll(saveQuestion, saveField, doneSave);
		savePane.setSpacing(10);
		savePane.setPadding(new Insets(10,10,10,10));
		savePane.setAlignment(Pos.CENTER);
		Scene saveScene = new Scene(savePane);
		
		//Find and replace popup
		String findTitle = "Find and Replace";
		Text findText = new Text("Find:");
		TextField findField = new TextField();
		Text replaceText = new Text("Replace with:");
		TextField replaceField = new TextField();
		replaceField.setFocusTraversable(false);
		Button findOcc = new Button("Find");
		findOcc.setFocusTraversable(false);
		Button replace = new Button("Replace");
		replace.setFocusTraversable(false);
		Button replaceAll = new Button("Replace All");
		replaceAll.setFocusTraversable(false);
		Button doneFind = new Button("Done");
		doneFind.setFocusTraversable(false);
		GridPane findPane = new GridPane();
		findPane.setPadding(new Insets(10,10,10,10));
		findPane.setVgap(10);
		findPane.setHgap(10);
		findPane.setAlignment(Pos.CENTER);
		findPane.add(findText, 0, 0);
		findPane.add(findField, 1, 0);
		findPane.add(replaceText, 0, 1);
		findPane.add(replaceField, 1, 1);
		findPane.add(findOcc, 0, 2);
		findPane.add(replace, 1, 2);
		findPane.add(replaceAll, 1, 3);
		findPane.add(doneFind, 0, 3);
		Scene findScene = new Scene(findPane);
		
		//Close popup
		String closeTitle = "Closing file";
		Text closeText = new Text("Do you want to save this file before closing?");
		Button yes = new Button("Yes");
		yes.setFocusTraversable(false);
		Button no = new Button("No");
		GridPane closePane = new GridPane();
		closePane.setPadding(new Insets(10,10,10,10));
		closePane.setVgap(10);
		closePane.setHgap(10);
		closePane.add(closeText, 0, 0, 2, 1);
		closePane.add(yes, 0, 1);
		closePane.add(no, 1, 1);
		closePane.setAlignment(Pos.CENTER);
		Scene closeScene = new Scene(closePane);
		
		//Main pane, scene, and stage
		double spacing = 5;
		VBox mainPane = new VBox();
		mainPane.setSpacing(spacing);
		mainPane.setPadding(new Insets(10,10,10,10));
		mainPane.getChildren().addAll(glblBtnsPane,buttonsPane,mainTextArea);
		VBox.setVgrow(mainTextArea, Priority.ALWAYS);
		Scene mainScene = new Scene(mainPane);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("Simple Notepad");
		primaryStage.show();
		
		//Event Handlers
		mainTextArea.setOnMouseClicked(e ->{
			mainTextArea.setEditable(!manager.isEmpty());
		});
		
		mainTextArea.textProperty().addListener(e ->{
			if(!manager.isEmpty()) {
				fileInFocus.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 12));
				TextEditor currentEditor = fileInFocus.getTextEditor();
				currentEditor.clear();
				String[] strings = mainTextArea.getText().trim().split(" ");
				for(String s: strings) {
					currentEditor.appendText(s);
				}
			}
		});
		
		openFile.setOnAction(e ->{
			//Create file button with temp name
			String newFileName = "temp";
			FileBtn fb = new FileBtn(newFileName);
			manager.createNewFile(fb);
			filesPane.getChildren().add(fb);
			fb.setMinWidth(Region.USE_PREF_SIZE);
			fb.setOnAction(e1 ->{
				fileInFocus = fb;
				mainTextArea.setText(fb.getTextEditor().getText());
			});
			fb.focusedProperty().addListener((e1, oldVal, newVal) ->{
				if(newVal) {
					fb.setStyle("-fx-background-color: lightgreen;");
					fileInFocus = fb;
				}
				else {
					fb.setStyle("-fx-background-color: lightgrey;");
				}
			});
			fb.requestFocus();
			
			//Get actual file name from user
			popupStage.setScene(openScene);
			popupStage.setTitle(openTitle);
			popupStage.show();
			doneOpen.setOnAction(e1 ->{
				try {
					File trialFile = new File(openField.getText() + ".txt");
					if(!trialFile.exists() || openField.getText().contains(".txt")) {
						throw new FileNotFoundException();
					}
					fb.setText(openField.getText());
					fb.setSavedBefore(true);
					TextEditor currentEditor = fb.getTextEditor();
					currentEditor.setSaveFile(openField.getText());
					currentEditor.readFromFile();
					popupStage.close();
					openField.setText("");
					mainTextArea.setText(currentEditor.getText());
				}
				catch(FileNotFoundException ex) {
					openQuestion.setText("File not found! Enter file name correctly!");
				}
			});
			popupStage.setOnCloseRequest(e1 ->{
				TextEditor currentEditor = fb.getTextEditor();
				if(currentEditor.getSaveFileName().isBlank()) { //user closed window incorrectly
					String name = fileInFocus.getText().replaceAll(" ", "_");
					try {
						currentEditor.setSaveFile(name);
						currentEditor.saveToFile();
						fileInFocus.setText(name);
					} catch (FileNotFoundException ex) {
						ex.printStackTrace();
					}
				}
			});
		});
		
		newFile.setOnAction(e ->{
			String newFileName = "File " + manager.getFilesCreated();
			FileBtn fb = new FileBtn(newFileName);
			manager.createNewFile(fb);
			filesPane.getChildren().add(fb);
			fb.setMinWidth(Region.USE_PREF_SIZE);
			fb.setOnAction(e1 ->{
				fileInFocus = fb;
				mainTextArea.setText(fb.getTextEditor().getText());
			});
			fb.focusedProperty().addListener((e1, oldVal, newVal) ->{
				if(newVal) {
					fb.setStyle("-fx-background-color: lightgreen;");
					fileInFocus = fb;
				}
				else {
					if(!mainTextArea.isFocused()) {
						fb.setStyle("-fx-background-color: lightgrey;");
					}
				}
			});
			fb.requestFocus();
		});
		
		save.setOnAction(e ->{
			if(!manager.isEmpty()) {
				TextEditor te = fileInFocus.getTextEditor();
				if(!fileInFocus.isSavedBefore() || fileInFocus.getText().contains("temp")) {
					saveQuestion.setText("Enter file name. Currently, it is " + fileInFocus.getText() + ".");
					popupStage.setTitle(saveTitle);
					popupStage.setScene(saveScene);
					popupStage.show();
					
					doneSave.setOnAction(e1 ->{
						if(saveField.getText().isBlank()) { //user didnt enter file name
							String name = fileInFocus.getText().replaceAll(" ", "_");
							try {
								te.setSaveFile(name);
								te.saveToFile();
								fileInFocus.setText(name);
							} catch (FileNotFoundException ex) {
								ex.printStackTrace();
							}
						}
						else {
							String name = saveField.getText().replaceAll(" ", "_");
							try {
								te.setSaveFile(name);
								te.saveToFile();
								fileInFocus.setText(name);
							} catch (FileNotFoundException ex) {
								ex.printStackTrace();
							}
						}
						popupStage.close();
					});
					
					popupStage.setOnCloseRequest(e1 ->{
						if(te.getSaveFileName().isBlank()) { //user closed popup window incorrectly
							String name = fileInFocus.getText().replaceAll(" ", "_");
							try {
								te.setSaveFile(name);
								te.saveToFile();
								fileInFocus.setText(name);
							} catch (FileNotFoundException ex) {
								ex.printStackTrace();
							}
						}
					});
					fileInFocus.setSavedBefore(true);
				}
				else {
					te.saveToFile();
				}
				fileInFocus.setFont(Font.font(Font.getDefault().getName(), FontWeight.NORMAL, 12));
			}
		});
		
		undo.setOnAction(e ->{
			if(!manager.isEmpty()) {
				fileInFocus.getTextEditor().undo();
				mainTextArea.setText(fileInFocus.getTextEditor().getText());
			}
		});
		
		redo.setOnAction(e ->{
			if(!manager.isEmpty()) {
				fileInFocus.getTextEditor().redo();
				mainTextArea.setText(fileInFocus.getTextEditor().getText());
			}
		});
		
		find.setOnAction(e ->{
			if(!manager.isEmpty()) {
				TextEditor currentEditor = fileInFocus.getTextEditor();
				popupStage.setTitle(findTitle);
				popupStage.setScene(findScene);
				popupStage.show();
				findOcc.setOnAction(e1 ->{
					String oldWord = findField.getText().trim();
					if(!oldWord.isBlank()) {
						int count = currentEditor.searchWord(findField.getText().trim());
						findText.setText("Found " + count + " match(es)");
						findText.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 12));
					}
				});
				replace.setOnAction(e1 ->{
					String oldWord = findField.getText().trim();
					String newWord = replaceField.getText().trim();
					if(!oldWord.isBlank() && !newWord.isBlank()) {
						currentEditor.replace(oldWord, newWord);
						int count = currentEditor.searchWord(newWord);
						replaceText.setText("Replaced " + count + " word(s)");
						replaceText.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 12));
					}
					mainTextArea.setText(fileInFocus.getTextEditor().getText());
				});
				replaceAll.setOnAction(e1 ->{
					String oldWord = findField.getText().trim();
					String newWord = replaceField.getText().trim();
					if(!oldWord.isBlank() && !newWord.isBlank()) {
						currentEditor.replaceAll(oldWord, newWord);
						int count = currentEditor.searchWord(newWord);
						replaceText.setText("Replaced " + count + " word(s)");
						replaceText.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 12));
					}
					mainTextArea.setText(fileInFocus.getTextEditor().getText());
				});
				doneFind.setOnAction(e1 ->{
					findText.setText("Find:");
					findText.setFont(Font.font(Font.getDefault().getName(), FontWeight.NORMAL, 12));
					replaceText.setText("Replace with:");
					replaceText.setFont(Font.font(Font.getDefault().getName(), FontWeight.NORMAL, 12));
					popupStage.close();
				});
			}
		});
		
		close.setOnAction(e ->{
			if(!manager.isEmpty()) {
				FileBtn previous = fileInFocus;
				popupStage.setScene(closeScene);
				popupStage.setTitle(closeTitle);
				popupStage.show();
				yes.setOnAction(e1 ->{
					if(fileInFocus.isSavedBefore()) {
						fileInFocus.getTextEditor().saveToFile();
						filesPane.getChildren().remove(fileInFocus);
						fileInFocus = manager.navigateToNextFile(previous);
						if(fileInFocus != null) {
							mainTextArea.setText(fileInFocus.getTextEditor().getText());
						}
						else {
							mainTextArea.setText("");
						}
						manager.closeFile(previous);
						if(manager.isEmpty()) {
							mainTextArea.setText("");
							fileInFocus = null;
						}
						popupStage.close();
					}
					else {
						saveQuestion.setText(saveQuestion.getText() + fileInFocus.getText() + ".");
						popupStage.setTitle(saveTitle);
						popupStage.setScene(saveScene);
						TextEditor te = fileInFocus.getTextEditor();
						doneSave.setOnAction(e2 ->{
							if(saveField.getText().isBlank()) {
								String name = fileInFocus.getText().replaceAll(" ", "_");
								try {
									te.setSaveFile(name);
									te.saveToFile();
									fileInFocus.setText(name);
								} catch (FileNotFoundException ex) {
									ex.printStackTrace();
								}
							}
							else {
								String name = saveField.getText().replaceAll(" ", "_");
								try {
									te.setSaveFile(name);
									te.saveToFile();
									fileInFocus.setText(name);
								} catch (FileNotFoundException ex) {
									ex.printStackTrace();
								}
							}
							filesPane.getChildren().remove(fileInFocus);
							fileInFocus = manager.navigateToNextFile(previous);
							if(fileInFocus != null) {
								mainTextArea.setText(fileInFocus.getTextEditor().getText());
							}
							else {
								mainTextArea.setText("");
							}
							manager.closeFile(previous);
							popupStage.close();
						});
					}
				});
				no.setOnAction(e1 ->{
					filesPane.getChildren().remove(fileInFocus);
					fileInFocus = manager.navigateToNextFile(previous);
					if(fileInFocus != null) {
						mainTextArea.setText(fileInFocus.getTextEditor().getText());
					}
					else {
						mainTextArea.setText("");
					}
					manager.closeFile(previous);
					if(manager.isEmpty()) {
						mainTextArea.setText("");
						fileInFocus = null;
					}
					popupStage.close();
				});
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}