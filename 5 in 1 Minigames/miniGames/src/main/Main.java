package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
	
	private Connection conn;
	private String loggedInUserId;
	private String loggedInUsername;
	MessageDigest md;
	
	@Override
	public void start(Stage primaryStage) {
		//Database connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/projectdb","root","");
			System.out.println("Database connected");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not loaded");;
		} catch (SQLException e) {
			System.out.println("Database not connected");
		}
		
		//Login page
		ImageView loginBack = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/Login&RegisterFiles/loginBack.jpeg"));
		GridPane gridpaneLogin = new GridPane();
		gridpaneLogin.setPadding(new Insets(10,10,10,10));
		gridpaneLogin.setBackground(new Background(new BackgroundFill(Color.color(1,1,1,0.5), new CornerRadii(15), Insets.EMPTY)));
		gridpaneLogin.setMaxSize(500,150);
		gridpaneLogin.setAlignment(Pos.CENTER);
		Text login = new Text("Login");
		login.setFont(Font.font("Arial",FontWeight.BOLD,20));
		Text username = new Text("Enter your username: ");
		username.setFont(Font.font("Arial",FontWeight.BOLD,15));
		TextField usernametextfield = new TextField();
		usernametextfield.setMinWidth(100);
		Text password = new Text("Enter your password:");
		password.setFont(Font.font("Arial",FontWeight.BOLD,15));
		PasswordField passwordtextfield = new PasswordField();
		passwordtextfield.setFocusTraversable(false);
		passwordtextfield.setMinWidth(100);
		TextField textField = new TextField();
		textField.setMinWidth(100);
		textField.textProperty().bindBidirectional(passwordtextfield.textProperty());
		Button toggleButton = new Button("Show");
		toggleButton.setFont(Font.font("Arial",FontWeight.BOLD,15));
		toggleButton.setMinWidth(Region.USE_PREF_SIZE);
		//Show/hide password button
		toggleButton.setOnAction(event -> {
			toggleButton.setText(toggleButton.getText().equals("Show") ? "Hide" : "Show");
			if (textField.isVisible()) {
				textField.setVisible(false);
				passwordtextfield.setVisible(true);
			} else {
				textField.setVisible(true);
				passwordtextfield.setVisible(false);
			}
		});
		//login button
		Button loginbutton = new Button("Login");
		loginbutton.setMinWidth(Region.USE_PREF_SIZE);
		loginbutton.setFont(Font.font("Arial",FontWeight.BOLD,15));
		loginbutton.setFocusTraversable(false);
		
		//register button
		Button register = new Button("no Account? Register");
		register.setMinWidth(Region.USE_PREF_SIZE);
		register.setFont(Font.font("Arial",FontWeight.BOLD,15));
		register.setFocusTraversable(false);
		
		//adding items to login pane
		gridpaneLogin.setVgap(10);
		gridpaneLogin.setHgap(10);
		gridpaneLogin.setPadding(new Insets(20));
		gridpaneLogin.add(login, 0, 1, 2, 1);
		gridpaneLogin.add(username, 0, 2);
		gridpaneLogin.add(usernametextfield, 1, 2);
		gridpaneLogin.add(password, 0, 3);
		gridpaneLogin.add(textField, 1, 3);
		textField.setVisible(false);
		gridpaneLogin.add(passwordtextfield, 1, 3);
		gridpaneLogin.add(toggleButton, 2, 3);
		gridpaneLogin.add(loginbutton, 0, 4);
		gridpaneLogin.add(register, 1, 4);
		StackPane loginFinalPane = new StackPane(loginBack,gridpaneLogin);
		//login scene
		Scene logInScene = new Scene(loginFinalPane,900,700);
		loginBack.fitHeightProperty().bind(logInScene.heightProperty());
		loginBack.setPreserveRatio(true);
		primaryStage.setScene(logInScene);
		primaryStage.setTitle("Login");
		primaryStage.show();
		
		//register pane
		ImageView registerBack = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/Login&RegisterFiles/registerBack.jpeg"));
		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(10,10,10,10));
		gridpane.setBackground(new Background(new BackgroundFill(Color.color(1,1,1,0.5), new CornerRadii(15), Insets.EMPTY)));
		gridpane.setMaxSize(500,150);
		gridpane.setAlignment(Pos.CENTER);
		Text Registertext = new Text("Registration");
		Registertext.setFont(Font.font("Arial",FontWeight.BOLD,20));
		Text registerUsername = new Text("Enter your username: ");
		registerUsername.setFont(Font.font("Arial",FontWeight.BOLD,15));
		TextField registerUsernametextfield = new TextField();
		registerUsernametextfield.setMinWidth(100);
		registerUsernametextfield.setFocusTraversable(false);
		Text registerPassword = new Text("Enter your password:");
		registerPassword.setFont(Font.font("Arial",FontWeight.BOLD,15));
		PasswordField registerPasswordtextfield = new PasswordField();
		registerPasswordtextfield.setMinWidth(100);
		registerPasswordtextfield.setFocusTraversable(false);
		TextField registerTextField = new TextField();
		registerTextField.setMinWidth(100);
		Button registerToggleButton = new Button("Show");
		registerToggleButton.setFont(Font.font("Arial",FontWeight.BOLD,15));
		registerToggleButton.setMinWidth(Region.USE_PREF_SIZE);
		registerToggleButton.setOnAction(event -> {
			registerToggleButton.setText(registerToggleButton.getText().equals("Show") ? "Hide" : "Show");
			if (registerTextField.isVisible()) {
				registerTextField.setVisible(false);
				registerPasswordtextfield.setVisible(true);
			} else {
				registerTextField.setVisible(true);
				registerPasswordtextfield.setVisible(false);
			}
		});
		
		Button register2 = new Button("Register");
		register2.setMinWidth(Region.USE_PREF_SIZE);
		register2.setFont(Font.font("Arial",FontWeight.BOLD,15));
		register2.setFocusTraversable(false);
		TextField textfields[] = {registerUsernametextfield, registerPasswordtextfield};
		
		//adding elements to register pane
		gridpane.setVgap(10);
		gridpane.setHgap(10);
		gridpane.setPadding(new Insets(20));
		gridpane.add(Registertext, 0, 1, 2, 1);
		gridpane.add(registerUsername, 0, 2);
		gridpane.add(registerUsernametextfield, 1, 2);
		gridpane.add(registerPassword, 0, 3);
		gridpane.add(registerTextField, 1, 3);
		registerTextField.setVisible(false);
		gridpane.add(registerPasswordtextfield, 1, 3);
		gridpane.add(registerToggleButton, 2, 3);
		gridpane.add(register2, 0, 5);
		registerTextField.textProperty().bindBidirectional(registerPasswordtextfield.textProperty());
		StackPane registerFinalPane = new StackPane(registerBack,gridpane);
		//register scene
		Scene registerScene = new Scene(registerFinalPane,900,700);
		registerBack.fitHeightProperty().bind(registerScene.heightProperty());
		registerBack.setPreserveRatio(true);
		
		register.setOnAction(event -> {
			passwordtextfield.setText("");
			usernametextfield.setText("");
			textField.setText("");
			primaryStage.setScene(registerScene);
			primaryStage.setTitle("Registration");
		});
		
		register2.setOnAction(e -> {
			boolean missingDetails = false;
			for (TextField tf : textfields) {
				if (tf.getText().equals("")) {
					missingDetails = true;
					break;
				}
			}
			if (missingDetails) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);
				// Set the title of the Alert
				alert.setTitle("Error Dialog");
				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");
				// Set the content text of the Alert
				alert.setContentText("Please enter the username and password");
				alert.showAndWait();
			} 
			else if (!registerUsernametextfield.getText().matches("^[a-zA-Z]+$")
					|| !registerPasswordtextfield.getText().matches("^[a-zA-Z1-9]+$")) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);
				// Set the title of the Alert
				alert.setTitle("Error Dialog");
				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");
				// Set the content text of the Alert
				alert.setContentText("Please enter the correct pattern");
				alert.showAndWait();
			} 
			else if (registerPasswordtextfield.getText().length() < 8) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);
				// Set the title of the Alert
				alert.setTitle("Error Dialog");
				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");
				// Set the content text of the Alert
				alert.setContentText("Please enter a password at least of 8 characters");
				alert.showAndWait();
			} 
			else {
				// Encrypt the password
				String enteredpassword = registerPasswordtextfield.getText();
				try {
					md = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				byte[] hash = md.digest(enteredpassword.getBytes());
				StringBuilder sb = new StringBuilder();
				for (byte b : hash) {
					sb.append(String.format("%02x", b));
				}
				String encryptedPassword = sb.toString();
				try {
					boolean isUserAdded = addUser(registerUsernametextfield.getText(),
							encryptedPassword, registerUsernametextfield, registerPasswordtextfield);
					if (isUserAdded) {
						// Show confirmation alert
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Confirmation");
						alert.setHeaderText("A Confirmation has occured");
						alert.setContentText("Welcome you registered successfully");
						alert.showAndWait();
						registerPasswordtextfield.setText("");
						registerUsernametextfield.setText("");
						registerTextField.setText("");
						login.setText("Login now using your new credentials!");
						primaryStage.setScene(logInScene);
						primaryStage.setTitle("Login Using Your New Credentials");
					}
					else {
						registerPasswordtextfield.setText("");
						registerUsernametextfield.setText("");
						registerTextField.setText("");
						login.setText("Existing Acount. Login");
						primaryStage.setScene(logInScene);
						primaryStage.setTitle("Login Using Your Credentials");
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		});
		
		loginbutton.setOnAction(e -> {
			String enteredPassword = passwordtextfield.getText();
			String encryptedEnteredPassword;
			try {
				encryptedEnteredPassword = encryptPassword(enteredPassword);
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
				return;
			}
			String[] result;
			try {
				result = findMyUser(usernametextfield.getText(), encryptedEnteredPassword);
			} catch (SQLException ex) {
				ex.printStackTrace();
				return;
			}
			int userId = Integer.parseInt(result[0]);
			String userName = result[1];
			if (userId != -1) {
				// for the first page when the user enter the first game a
				loggedInUserId = result[0];
				loggedInUsername = userName;
				passwordtextfield.setText("");
				usernametextfield.setText("");
				textField.setText("");
				GamesScene games = new GamesScene(conn,loggedInUserId,loggedInUsername, logInScene);
				primaryStage.setScene(games.getMainScene(primaryStage));
				primaryStage.setTitle("Mini Games");
				//go to games scene
			} 
			else if (usernametextfield.getText() == null) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);
				// Set the title of the Alert
				alert.setTitle("Error Dialog");
				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");
				// Set the content text of the Alert
				alert.setContentText("Please enter your username");
				alert.showAndWait();
			} 
			else if (passwordtextfield.getText() == null) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);
				// Set the title of the Alert
				alert.setTitle("Error Dialog");
				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");
				// Set the content text of the Alert
				alert.setContentText("Please enter a password");
				alert.showAndWait();
			} 
			else if (!usernametextfield.getText().matches("^[a-zA-Z]+$")
					|| !passwordtextfield.getText().matches("^[a-zA-Z1-9]+$")) {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);
				// Set the title of the Alert
				alert.setTitle("Error Dialog");
				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");
				// Set the content text of the Alert
				alert.setContentText("Please enter the correct pattern");
				alert.showAndWait();
			} 
			else {
				// Create a new Alert of type ERROR
				Alert alert = new Alert(Alert.AlertType.ERROR);
				// Set the title of the Alert
				alert.setTitle("Error Dialog");
				// Set the header text of the Alert
				alert.setHeaderText("An Error Occurred");
				// Set the content text of the Alert
				alert.setContentText("Username or password does not exist!"
									+" Please try agin or signup for a new account");
				alert.showAndWait();
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public String[] findMyUser(String username, String password) throws SQLException {
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			return new String[] { "-1", null};
		}
		String query = "SELECT userID, username, isNewUser FROM users WHERE username = ? AND password = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		ResultSet rset = pstmt.executeQuery();
		if (rset.next()) {
			String userId = rset.getString(1);
			String userName = rset.getString(2);
			String newState = rset.getString(3);
			// If the user is new, update isNewUser to false
			if (newState.equals("yes")) {
				String updateQuery = "UPDATE users SET isNewUser = \"no\" WHERE userID = " + userId;
				PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
				updatePstmt.executeUpdate();
			}
			return new String[] {userId, userName};
		} 
		else {
			return new String[] {"-1", null};
		}
	}

	public static String encryptPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hash = md.digest(password.getBytes());
		StringBuilder sb = new StringBuilder();
		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	public boolean addUser(String username, String password,
			TextField usernameField, TextField passwordField) throws SQLException {
		if (username == null || password == null) {
			return false;
		} 
		else {
			String query = "SELECT * FROM users WHERE username = ? AND password = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()) {
				showAlert("An account with the same username already exits! Please login instead.");
				return false;
			} else {
				query = "INSERT INTO users (username,password,isNewUser) VALUES (?, ?, ?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.setString(3, "yes"); // Set isNewUser to true
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows > 0) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
