package balloonAssassinator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main{
	
	//Load balloon images
	private ImageView redBalloonImg = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/redBalloon.png"));
	private ImageView blueBalloonImg = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/blueBalloon.png"));
	private ImageView aquamarineBalloonImg = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/aquamarineBalloon.png"));
	private ImageView magentaBalloonImg = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/magentaBalloon.png"));
	private ImageView[] availableBalloonColorImgs = {redBalloonImg,blueBalloonImg,aquamarineBalloonImg,magentaBalloonImg};
	//Create hashshet to relate images to their colors
	private HashMap<Color, ImageView> imgToColor = new HashMap<>();
	//Scores array. scores[0] hold total possible score. scores[1] holds user score.
	private int[] scores = new int[2];
	
	//Global transitions and their variables
	private Animation[] transitions = {new FadeTransition(), new PathTransition(), new FadeTransition()};
	private ImageView[] currentBallon = {new ImageView(), new ImageView()};
	private Polygon[] currentPoly = {new Polygon()};
	private Double[] startXY = new Double[2];
	
	private Scene startScreenScene;
	private Scene goBackScene;
	
	//Global pop sound
	MediaPlayer popSound = new MediaPlayer(new Media("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/pop.mp3"));
	MediaPlayer inflateSound = new MediaPlayer(new Media("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/inflate.mp3"));
	MediaPlayer deflateSound = new MediaPlayer(new Media("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/deflate.mp3"));
	
	//Global buttons
	private ColorButton red = new ColorButton(Color.RED);
	private ColorButton blue = new ColorButton(Color.BLUE);
	private ColorButton aquam = new ColorButton(Color.AQUAMARINE);
	private ColorButton magenta = new ColorButton(Color.MAGENTA);
	
	//Global database connection
	private Connection conn;
	private String currentUserID;
	private int currentHighScore;
	
	public Main(Scene previousScene, Connection conn, String userID, int highScore) {
		this.conn = conn;
		this.currentUserID = userID;
		this.currentHighScore = highScore;
		goBackScene = previousScene;
		//Initializing hashset
		imgToColor.put(Color.RED,redBalloonImg);
		imgToColor.put(Color.BLUE,blueBalloonImg);
		imgToColor.put(Color.AQUAMARINE,aquamarineBalloonImg);
		imgToColor.put(Color.MAGENTA,magentaBalloonImg);
	}
	
	public Scene getStartScreenScene(Stage primaryStage) {
		setStartScreenScene(primaryStage);
		return startScreenScene;
	}
	
	public void setStartScreenScene(Stage primaryStage) {
		//Database Connection
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
		
		//Start Screen
		//Background
		ImageView startScreenBack = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/balloonAss-1.jpeg"));
		//Start screen titles
		Text startScreenTitle = new Text("Balloon Assassinator");
		startScreenTitle.setTextAlignment(TextAlignment.CENTER);
		startScreenTitle.setFont(Font.font("Calibri",FontWeight.BOLD,FontPosture.ITALIC,60));
		startScreenTitle.setFill(Color.WHITE);
		startScreenTitle.setStroke(Color.BLACK);
		startScreenTitle.setStrokeWidth(3);
		startScreenTitle.setWrappingWidth(550);
		Label startScreenInfo = new Label("A faction of the balloon empire has rebelled against humanity and overran Earth causing everyone to have high-pitched voices!"
										+ "\nYou have been chosen to pop every last one of them. Are you ready?"
										+ "\n Before you start, select your enemies' color to easily indentify them. There are 4 options, show below."
										+ "\nPopping an enemy balloon awards you 10 points. Popping an ally takes away 5 points."
										+ "\nYou have 1 minute to get the highest score possible!"
										+ "\nThe game starts as soon as you select a color.");
		startScreenInfo.setFont(Font.font("Arial",FontWeight.BOLD,15));
		startScreenInfo.setTextFill(Color.WHITE);
		startScreenInfo.setAlignment(Pos.CENTER);
		startScreenInfo.setTextAlignment(TextAlignment.CENTER);
		startScreenInfo.setWrapText(true);
		startScreenInfo.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0.5), new CornerRadii(15), Insets.EMPTY)));
		startScreenInfo.setPadding(new Insets(10,10,10,10));
		startScreenInfo.setPrefSize(600, 300);
		startScreenInfo.setGraphic(startScreenTitle);
		startScreenInfo.setContentDisplay(ContentDisplay.TOP);
		ColorButton[] btns = {red,blue,aquam,magenta};
		//Buttons pane
		HBox colorButtonsPane = new HBox(red,blue,aquam,magenta);
		colorButtonsPane.setAlignment(Pos.CENTER);
		colorButtonsPane.setPadding(new Insets(10,10,10,10));
		colorButtonsPane.setSpacing(25);
		colorButtonsPane.setMaxWidth(magenta.getWidth()*6 + 4.5*colorButtonsPane.getSpacing());
		colorButtonsPane.setMinHeight(magenta.getWidth()*6 + 30);
		colorButtonsPane.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0.5), new CornerRadii(15), Insets.EMPTY)));
		//Border pane holding all of the above elements
		BorderPane startScreenPane = new BorderPane();
		startScreenPane.setPadding(new Insets(10,10,10,10));
		startScreenPane.setCenter(startScreenInfo);
		startScreenPane.setBottom(colorButtonsPane);
		BorderPane.setAlignment(colorButtonsPane, Pos.CENTER);
		//Stack pane that holds the background and startScreenPane
		StackPane startScreenFinalPane = new StackPane(startScreenBack,startScreenPane);
		//Start screen pane
		startScreenScene = new Scene(startScreenFinalPane,900,700);
		startScreenBack.fitWidthProperty().bind(startScreenPane.widthProperty());
		startScreenBack.setPreserveRatio(true);
		
		//Game pane
		BorderPane mainFinalPane = new BorderPane();
		mainFinalPane.setPadding(new Insets(10,10,10,10));
		
		//Center pane
		VBox mainPane = new VBox();
		
		//Gun pane
		Pane gunPane = new Pane();
		Rectangle barrel = new Rectangle();
		barrel.setWidth(30);
		barrel.setHeight(100);
		barrel.setFill(Color.GRAY);
		barrel.setStroke(Color.BLACK);
		barrel.setStrokeWidth(4);
		barrel.xProperty().bind(gunPane.widthProperty().divide(2).subtract(barrel.getWidth()/2));
		barrel.yProperty().bind(gunPane.heightProperty().subtract(barrel.getHeight()));
		Rectangle trigger = new Rectangle();
		trigger.setWidth(10);
		trigger.setHeight(20);
		trigger.setFill(Color.WHITE);
		trigger.setStroke(Color.BLACK);
		trigger.setStrokeWidth(2);
		trigger.yProperty().bind(barrel.yProperty().add(barrel.getHeight() - 30));
		trigger.xProperty().bind(barrel.xProperty().add((barrel.getWidth()-trigger.getWidth())/2));
		Rectangle mozzle = new Rectangle();
		mozzle.setWidth(17);
		mozzle.setHeight(10);
		mozzle.setFill(Color.BLACK);
		mozzle.xProperty().bind(barrel.xProperty().add((barrel.getWidth() - mozzle.getWidth())/2));
		mozzle.yProperty().bind(barrel.yProperty().subtract(mozzle.getHeight()));
		gunPane.getChildren().addAll(mozzle,barrel,trigger);
		Rotate gunRotate = new Rotate();
		gunRotate.pivotXProperty().bind(barrel.xProperty().add(barrel.getWidth()/2));
		gunRotate.pivotYProperty().bind(barrel.yProperty().add(barrel.getHeight()/2));
		gunPane.getTransforms().add(gunRotate);
		
		//Balloon pane
		Pane balloonPane = new Pane();
		balloonPane.prefWidthProperty().bind(mainPane.widthProperty());
		balloonPane.prefHeightProperty().bind(mainPane.heightProperty());
		
		//Adding elements to main pane
		mainPane.setStyle("-fx-background-color: lightblue;");
		mainPane.getChildren().addAll(balloonPane,gunPane);
		mainFinalPane.setCenter(mainPane);
		
		//Right pane
		VBox leftPane = new VBox();
		leftPane.setSpacing(100);
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setPadding(new Insets(0,5,0,0));
		leftPane.setStyle("-fx-background-color: beige;");
		//Score label
		Label scoreLabel = new Label("Your accuracy: ");
		scoreLabel.setMinWidth(200);
		scoreLabel.setWrapText(true);
		scoreLabel.setTextFill(Color.WHITE);
		scoreLabel.setFont(Font.font("Arial",FontWeight.BOLD,15));
		scoreLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(20), Insets.EMPTY)));
		scoreLabel.setPadding(new Insets(10,10,10,10));
		scoreLabel.setAlignment(Pos.CENTER);
		scoreLabel.setTextAlignment(TextAlignment.CENTER);
		
		//Quit button
		Button quit = new Button("Quit");
		quit.setAlignment(Pos.CENTER);
		quit.setFocusTraversable(false);
		quit.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		quit.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2))));
		quit.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(20), Insets.EMPTY)));
		quit.setMinWidth(Region.USE_PREF_SIZE);
		
		//Time pane
		Pane timePane = new Pane();
		Circle timeAxis = new Circle(0,0,100);
		timeAxis.setFill(Color.TRANSPARENT);
		timeAxis.setStroke(Color.BLACK);
		timeAxis.setStrokeWidth(3);
		timeAxis.centerXProperty().bind(timePane.widthProperty().divide(2));
		timeAxis.centerYProperty().bind(timePane.heightProperty().divide(2));
		Circle indicator = new Circle(0,0,10);
		indicator.setFill(Color.AQUAMARINE);
		indicator.setStroke(Color.BLACK);
		indicator.setStrokeWidth(2);
		indicator.centerXProperty().bind(timeAxis.centerXProperty().add(timeAxis.getRadius()));
		indicator.centerYProperty().bind(timeAxis.centerYProperty());
		//Text element for user to keep track of remaining time
		Text timeRemaining = new Text("60");
		timeRemaining.setFont(Font.font("Arial",FontWeight.BOLD,60));
		//Adding elements to timePane
		timePane.getChildren().addAll(timeAxis,indicator);
		StackPane timeFinalPane = new StackPane(timeRemaining,timePane);
		//Creating pathTransition
		PathTransition timerTrans = new PathTransition();
		timerTrans.setNode(indicator);
		timerTrans.setPath(timeAxis);
		timerTrans.setDuration(Duration.minutes(1));
		timerTrans.setDelay(Duration.millis(1500));
		timerTrans.setCycleCount(1);
		//Adding elements to leftPane
		leftPane.getChildren().addAll(scoreLabel,timeFinalPane,quit);
		mainFinalPane.setLeft(leftPane);
		
		//main Scene
		Scene mainScene = new Scene(mainFinalPane,900,700);
		
		//End screen
		//Background
		ImageView endScreenBack = new ImageView(new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/balloonAssassinatorFiles/balloonAss-1.jpeg"));
		//Start screen titles
		Text endScreenTitle = new Text("Balloon Assassinator");
		endScreenTitle.setTextAlignment(TextAlignment.CENTER);
		endScreenTitle.setFont(Font.font("Calibri",FontWeight.BOLD,FontPosture.ITALIC,60));
		endScreenTitle.setFill(Color.WHITE);
		endScreenTitle.setStroke(Color.BLACK);
		endScreenTitle.setStrokeWidth(3);
		endScreenTitle.setWrappingWidth(550);
		Label endScreenInfo = new Label();
		endScreenInfo.setFont(Font.font("Arial",FontWeight.BOLD,30));
		endScreenInfo.setTextFill(Color.WHITE);
		endScreenInfo.setAlignment(Pos.CENTER);
		endScreenInfo.setTextAlignment(TextAlignment.CENTER);
		endScreenInfo.setWrapText(true);
		endScreenInfo.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0.5), new CornerRadii(15), Insets.EMPTY)));
		endScreenInfo.setPadding(new Insets(10,10,10,10));
		endScreenInfo.setPrefSize(600, 300);
		endScreenInfo.setGraphic(startScreenTitle);
		endScreenInfo.setContentDisplay(ContentDisplay.TOP);
		//Done button
		Button done = new Button("Done");
		done.setFocusTraversable(false);
		done.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		done.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2) )));
		done.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,new CornerRadii(20), Insets.EMPTY)));
		BorderPane.setAlignment(done, Pos.CENTER);
		//Border pane holding all of the above elements
		BorderPane endScreenPane = new BorderPane();
		endScreenPane.setPadding(new Insets(10,10,10,10));
		endScreenPane.setCenter(endScreenInfo);
		endScreenPane.setBottom(done);
		StackPane endScreenFinalPane = new StackPane(endScreenBack,endScreenPane);
		Scene endScreenScene = new Scene(endScreenFinalPane,900,700);
		endScreenBack.fitWidthProperty().bind(endScreenScene.widthProperty());
		endScreenBack.setPreserveRatio(true);
		
		//Event listeners and handlers
		//Event listener for color buttons
		for(ColorButton bt: btns) {
			bt.setOnMouseEntered(e ->{
				bt.pause();
			});
			bt.setOnMouseExited(e ->{
				bt.play();
			});
			bt.setOnAction(e ->{
				//Change scene
				primaryStage.setScene(mainScene);
				//Set selected balloon color
				currentBallon[1] = imgToColor.get(bt.getColor());
				//Start Timer
				timerTrans.play();
				//Make new transitions
				newTransitions(balloonPane,scoreLabel);
				//Stop all button animations
				for(ColorButton btn: btns) {
					btn.stop();
				}
			});
		}
		
		//Event listener to rotate gun
		balloonPane.setOnMouseMoved(e ->{
			double mouseX = e.getSceneX() - leftPane.getWidth();
			double mouseY = e.getSceneY();
			double pivotX = gunRotate.getPivotX();
			double pivotY = gunRotate.getPivotY() + balloonPane.getHeight();
			double deltaX = pivotX - mouseX;
			double deltaY = pivotY - mouseY;
			double hyp = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
			double angle = Math.toDegrees(Math.acos(deltaX/hyp) - Math.PI/2)%180;
			gunRotate.setAngle(angle);
		});
		
		timerTrans.currentTimeProperty().addListener((e,oldVal,newVal) ->{
			int seconds = (int) (60 - newVal.toSeconds());
			timeRemaining.setText(seconds+"");
		});
		
		timerTrans.setOnFinished(e ->{
			//Go to end screen
			primaryStage.setScene(endScreenScene);
			endScreenInfo.setText(scoreLabel.getText());
			transitions[0].stop();
			transitions[1].stop();
			transitions[2].stop();
			int score = 0;
			if (scores[0] != 0) {
				score = (int)(100*100.0*scores[1]/scores[0]);
			}
			if(score > currentHighScore) {
				endScreenInfo.setText(scoreLabel.getText() + "\nNew Highscore!"
									+ "\nYour highscore was: " + (currentHighScore*1.0/100) + "%");
				currentHighScore = score;
				try {
					String updateQuery = "UPDATE users SET game2HighScore = " + currentHighScore + " WHERE userID = " + currentUserID;
					PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
					updatePstmt.executeUpdate();
					System.out.println("Highscore updated!");
				}
				catch(SQLException e1) {
					System.out.println("Highscore was not updated!");
				}
			}
			else {
				endScreenInfo.setText(scoreLabel.getText() + "\nYour highscore: " + (currentHighScore*1.0/100) +"%");
			}
		});
		
		done.setOnAction(e ->{
			primaryStage.setScene(goBackScene);
			primaryStage.setTitle("Mini Games");
		});
		
		quit.setOnAction(e ->{
			primaryStage.setScene(goBackScene);
			primaryStage.setTitle("Mini Games");
			for(Animation trans: transitions) {
				trans.stop();
			}
		});
	}
	
	public void setImgXY(ImageView im) {
		im.setX(startXY[0]);
		im.setY(startXY[1]);
	}
	
	public Polygon generateRandomPolygon(Pane balloonPane) {
		double maxX = balloonPane.getWidth()*0.8;
		double maxY = balloonPane.getHeight()*0.8;
		int polygonVertixCount = (int)(Math.random()*11 + 3);
		Double[] polygonPoints = new Double[polygonVertixCount*2];
		double randX = Math.random()*(maxX+1) + 50;
		double randY = Math.random()*(maxY+1) + 50;
		polygonPoints[0] = randX;
		polygonPoints[1] = randY;
		startXY[0] = randX;
		startXY[1] = randY;
		for(int i = 2; i < polygonPoints.length; i+=2) {
			randX = Math.random()*(maxX) + 50;
			randY = Math.random()*(maxY+1) + 50;
			polygonPoints[i] = randX;
			polygonPoints[i+1] = randY;
		}
		Polygon p = new Polygon();
		p.setOpacity(0);
		balloonPane.getChildren().add(p);
		p.getPoints().addAll(polygonPoints);
		return p;
	}
	
	public ImageView randomBalloon() {
		int randColor = (int) (Math.random()*4);
		ImageView randomImg = availableBalloonColorImgs[randColor];
		randomImg.setFitWidth(100);
		randomImg.setOpacity(0);
		randomImg.setPreserveRatio(true);
		return randomImg;
	}
	
	public FadeTransition makeFadeTrans(ImageView randomColor, int fromValue, int toValue) {
		FadeTransition fade = new FadeTransition();
		fade.setToValue(toValue);
		fade.setFromValue(fromValue);
		fade.setNode(randomColor);
		fade.setCycleCount(1);
		fade.setDuration(Duration.millis(300));
		return fade;
	}
	
	public void newTransitions(Pane balloonPane, Label score) {
		//Clear balloonPane before adding new balloons
		balloonPane.getChildren().clear();
		//Select random balloon color
		ImageView randomColor = randomBalloon();
		//Set current balloon
		currentBallon[0] = randomColor;
		//If randomColot matches selectedColor, add 20 to total possible score
		if(currentBallon[0] == currentBallon[1]) {
			scores[0] += 20;
			score.setText(scores[0] == 0? "Your accuracy: -" : "Your accuracy: " + String.format("%.2f",(scores[1]*100.0/scores[0])) + "%");
		}
		//Create random path
		Polygon randomPoly = generateRandomPolygon(balloonPane);
		//Set current random path
		currentPoly[0] = randomPoly;
		//Add balloon and set random coordinates
		balloonPane.getChildren().add(randomColor);
		setImgXY(randomColor);
		//Create, set, and play fade in transition
		transitions[0] = makeFadeTrans(randomColor,0,1);
		transitions[0].setDelay(Duration.millis(1500));
		transitions[0].play();
		inflateSound.play();
		inflateSound.seek(Duration.ZERO);
		transitions[0].setOnFinished(e1 ->{
			PathTransition randomPath = new PathTransition();
			randomPath.setNode(currentBallon[0]);
			randomPath.setPath(currentPoly[0]);
			double balancedDuration = randomPoly.getPoints().size()*0.4;
			randomPath.setDuration(Duration.seconds(balancedDuration));
			randomPath.setCycleCount(1);
			transitions[1] = randomPath;
			transitions[1].play();
			//After fade in transition ends, path transition starts
			transitions[1].setOnFinished(e2 ->{
				transitions[2] = makeFadeTrans(currentBallon[0], 1, 0);
				transitions[2].play();
				deflateSound.play();
				deflateSound.seek(Duration.ZERO);
				//After path transition ends, fade out transition starts
				transitions[2].setOnFinished(e3 ->{
					//After fade out transition ends, new animations are made
					newTransitions(balloonPane,score);
				});
			});
			//If user presses on image
			currentBallon[0].setOnMouseClicked(e ->{
				popSound.play();
				popSound.seek(Duration.ZERO);
				scores[1] += currentBallon[0] == currentBallon[1]? 20:-5;
				score.setText(scores[0] == 0? "Your accuracy: -" : "Your accuracy: " + String.format("%.2f",(scores[1]*100.0/scores[0])) + "%");
				transitions[1].stop();
				transitions[2].stop();
				//Start fade out transition early
				transitions[2] = makeFadeTrans(currentBallon[0], 1, 0);
				transitions[2].play();
				transitions[2].setOnFinished(e3 ->{
					//After fade out transition ends, new animations are made
					newTransitions(balloonPane,score);
				});
			});
		});
	}
	
	public void playAnimations() {
		red.play();
		blue.play();
		aquam.play();
		magenta.play();
	}
}
