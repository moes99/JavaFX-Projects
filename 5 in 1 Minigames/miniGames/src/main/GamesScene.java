package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GamesScene {
	
	private Connection conn;
	private Statement stmnt;
	private String currentUserId;
	private String currentUsername;
	private Scene mainScene;
	private Scene goBackScene;
	
	public GamesScene(Connection conn, String userId, String username, Scene previousScene) {
		this.conn = conn;
		this.currentUserId = userId;
		this.currentUsername = username;
		this.goBackScene = previousScene;
	}
	
	public Scene getMainScene(Stage primaryStage) {
		setMainScene(primaryStage);
		return mainScene;
	}
	
	public void setMainScene(Stage primaryStage) {
		//Get highscores from db
		ResultSet highscoresSet;
		int[] highscores = new int[5];
		try {
			String query = "SELECT game1HighScore, game2HighScore, game3HighScore, game4HighScore, game5HighScore "
					+ "FROM users WHERE userID = " + currentUserId;
			stmnt = conn.createStatement();
			highscoresSet = stmnt.executeQuery(query);
			if(highscoresSet.next()) {
				highscores[0] = Integer.parseInt(highscoresSet.getString(1));
				highscores[1] = Integer.parseInt(highscoresSet.getString(2));
				highscores[2] = Integer.parseInt(highscoresSet.getString(3));
				highscores[3] = Integer.parseInt(highscoresSet.getString(4));
				highscores[4] = Integer.parseInt(highscoresSet.getString(5));
			}
			System.out.println("Highscores obtained");
		}
		catch(SQLException e) {
			System.out.println("Highscores not obtained");
		}
		
		//game icons
		Image geekgamserim = new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/mainPageFiles/icon1.png");
		ImageView geekgameriv = new ImageView(geekgamserim);
		geekgameriv.setFitWidth(100);
		geekgameriv.setFitHeight(100);
		Image ballonim = new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/mainPageFiles/icon2.png");
		ImageView balloniv = new ImageView(ballonim);
		balloniv.setFitWidth(100);
		balloniv.setFitHeight(100);
		Image Focusfreakim = new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/mainPageFiles/icon3.png");
		ImageView Focusfreakiv = new ImageView(Focusfreakim);
		Focusfreakiv.setFitWidth(100);
		Focusfreakiv.setFitHeight(100);
		Image streesoutim = new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/mainPageFiles/icon4.png");
		ImageView streesoutiv = new ImageView(streesoutim);
		streesoutiv.setFitWidth(100);
		streesoutiv.setFitHeight(100);
		Image Kangarooraceim = new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/mainPageFiles/icon5.png");
		ImageView Kangarooiv = new ImageView(Kangarooraceim);
		Kangarooiv.setFitWidth(100);
		Kangarooiv.setFitHeight(100);
		
		//Game buttons
		Button g1 = new Button("Geek Gamer");
		g1.setMaxSize(100, 150);
		g1.setWrapText(true);
		Button g2 = new Button("Balloon Assassinator");
		g2.setMaxSize(100, 150);
		g2.setWrapText(true);
		Button g3 = new Button("Focus Freak");
		g3.setMaxSize(100, 150);
		g3.setWrapText(true);
		Button g4 = new Button("Stress Out");
		g4.setMaxSize(100, 150);
		g4.setWrapText(true);
		Button g5 = new Button("Kangaroo Race");
		g5.setMaxSize(100, 150);
		g5.setWrapText(true);
		
		//Set images to buttons
		g1.setContentDisplay(ContentDisplay.TOP);
		g1.setGraphic(geekgameriv);
		g1.setTextFill(Color.WHITE);
		g1.setStyle("-fx-background-color: #146F0C; -fx-font-size: 18px; -fx-border-color: black;"
				+ "-fx-border-width: 5px; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		g2.setContentDisplay(ContentDisplay.TOP);
		g2.setGraphic(balloniv);
		g2.setTextFill(Color.WHITE);
		g2.setStyle("-fx-background-color: #4E1818; -fx-font-size: 18px; -fx-border-color: black;"
				+ "-fx-border-width: 5px; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		g3.setContentDisplay(ContentDisplay.TOP);
		g3.setGraphic(Focusfreakiv);
		g3.setTextFill(Color.WHITE);
		g3.setStyle("-fx-background-color: #8F2100; -fx-font-size: 18px;-fx-border-color: black;"
				+ "-fx-border-width: 5px; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		g4.setContentDisplay(ContentDisplay.TOP);
		g4.setGraphic(streesoutiv);
		g4.setTextFill(Color.WHITE);
		g4.setStyle("-fx-background-color: #351D5D; -fx-font-size: 18px;-fx-border-color: black;"
				+ "-fx-border-width: 5px; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		g5.setContentDisplay(ContentDisplay.TOP);
		g5.setGraphic(Kangarooiv);
		g5.setTextFill(Color.WHITE);
		g5.setStyle("-fx-background-color: #CC9900; -fx-font-size: 18px;-fx-border-color: black;"
				+ "-fx-border-width: 5px; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		
		//game label to wrap button with current highscore for each game
		Label gl1 = new Label("Current Highscore: " + highscores[0]);
		gl1.setGraphic(g1);
		gl1.setContentDisplay(ContentDisplay.TOP);
		gl1.setTextFill(Color.WHITE);
		Label gl2 = new Label("Current Highscore: " + highscores[1]*1.0/100 + "%");
		gl2.setGraphic(g2);
		gl2.setContentDisplay(ContentDisplay.TOP);
		gl2.setTextFill(Color.WHITE);
		Label gl3 = new Label("Current Highscore: " + highscores[2]);
		gl3.setGraphic(g3);
		gl3.setContentDisplay(ContentDisplay.TOP);
		gl3.setTextFill(Color.WHITE);
		Label gl4 = new Label("Current Highscore: " + highscores[3]);
		gl4.setGraphic(g4);
		gl4.setContentDisplay(ContentDisplay.TOP);
		gl4.setTextFill(Color.WHITE);
		Label gl5 = new Label("Current Highscore: " + highscores[4]);
		gl5.setGraphic(g5);
		gl5.setContentDisplay(ContentDisplay.TOP);
		gl5.setTextFill(Color.WHITE);
		
		//Hbox for all labels
		FlowPane labelsPane = new FlowPane(gl1,gl2,gl3,gl4,gl5);
		labelsPane.setPadding(new Insets(40,0,0,0));
		labelsPane.setHgap(20);
		labelsPane.setVgap(20);
		labelsPane.setMinWidth(500);
		labelsPane.setAlignment(Pos.CENTER);
		
		Button logout = new Button("Logout");
		logout.setMinWidth(Region.USE_PREF_SIZE);
		logout.setFocusTraversable(false);
		logout.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		logout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2) )));
		logout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(20), Insets.EMPTY)));
		logout.setOnAction(e ->{
			primaryStage.setScene(goBackScene);
			primaryStage.setTitle("Login");
		});
		
		VBox labelsFinalPane = new VBox(labelsPane,logout);
		labelsFinalPane.setAlignment(Pos.CENTER);
		labelsFinalPane.setSpacing(30);
		
		//Welcome label
		Label welcomeLabel = new Label("Welcome " + currentUsername);
		welcomeLabel.setTextFill(Color.WHITE);
		welcomeLabel.setFont(Font.font("Arial",FontWeight.BOLD,40));
		welcomeLabel.setPadding(new Insets(10,10,10,10));
		welcomeLabel.setGraphic(labelsFinalPane);
		welcomeLabel.setContentDisplay(ContentDisplay.BOTTOM);
		welcomeLabel.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0.5), new CornerRadii(15), Insets.EMPTY)));
		
		//background
		ImageView background = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/mainPageFiles/background.jpg"));
		
		//final pane
		StackPane gamesPane = new StackPane(background,welcomeLabel);
		mainScene = new Scene(gamesPane,900,700);
		background.fitWidthProperty().bind(mainScene.widthProperty());
		background.fitHeightProperty().bind(mainScene.heightProperty());
		
		//Game instances
		geekGamer.Main game1 = new geekGamer.Main( mainScene, conn, currentUserId, highscores[0]);
		balloonAssassinator.Main game2 = new balloonAssassinator.Main(mainScene, conn, currentUserId, highscores[1]);
		focusFreak.Main game3 = new focusFreak.Main(conn, currentUserId, highscores[2], mainScene);
		stressOut.Main game4 = new stressOut.Main(conn, mainScene, currentUserId, highscores[3]);
		kangarooRace.Main game5 = new kangarooRace.Main(conn, currentUserId, highscores[4], mainScene);
		
		g1.setOnAction(e ->{
			primaryStage.setScene(game1.getStartScreenScene(primaryStage));
			primaryStage.setTitle("Geek Gamer");
			game1.playAnimations();
		});
		
		g2.setOnAction(e ->{
			primaryStage.setScene(game2.getStartScreenScene(primaryStage));
			primaryStage.setTitle("Balloon Assassinator");
			game2.playAnimations();
		});
		
		g3.setOnAction(e ->{
			primaryStage.setScene(game3.getStartScreenScene(primaryStage));
			primaryStage.setTitle("Focus Freak");
		});
		
		g4.setOnAction(e ->{
			primaryStage.setScene(game4.getStartScreenScene(primaryStage));
			primaryStage.setTitle("Stress Out");
		});
		
		g5.setOnAction(e ->{
			primaryStage.setScene(game5.getStartScreenScene(primaryStage));
			primaryStage.setTitle("Kangaroo Race");
		});
		
		primaryStage.sceneProperty().addListener(e ->{
			try {
				String query = "SELECT game1HighScore, game2HighScore, game3HighScore, game4HighScore, game5HighScore "
						+ "FROM users WHERE userID = " + currentUserId;
				stmnt = conn.createStatement();
				ResultSet newHighScores = stmnt.executeQuery(query);
				if(newHighScores.next()) {
					highscores[0] = Integer.parseInt(newHighScores.getString(1));
					gl1.setText("Current Highscore: " + highscores[0]);
					highscores[1] = Integer.parseInt(newHighScores.getString(2));
					gl2.setText("Current Highscore: " + highscores[1]*1.0/100 + "%");
					highscores[2] = Integer.parseInt(newHighScores.getString(3));
					gl3.setText("Current Highscore: " + highscores[2]);
					highscores[3] = Integer.parseInt(newHighScores.getString(4));
					gl4.setText("Current Highscore: " + highscores[3]);
					highscores[4] = Integer.parseInt(newHighScores.getString(5));
					gl5.setText("Current Highscore: " + highscores[4]);
				}
				System.out.println("New highscores obtained");
			}
			catch(SQLException ex) {
				System.out.println("New highscores not obtained");
			}
		});
	}
}
