package stressOut;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main {
	private Connection conn;
	private String currentUserID;
	private int currentHighScore;
	private Scene startScreenScene;
	private Scene goBackScene;
	
	private int score = 0;
	private int timeSeconds = 30;
	private Button nextButton;
	
	public Main(Connection conn, Scene previousScene, String userId, int highscore) {
		this.conn = conn;
		this.currentUserID = userId;
		this.currentHighScore = highscore;
		this.goBackScene = previousScene;
	}
	
	public Scene getStartScreenScene(Stage primaryStage) {
		setStartScreenScene(primaryStage);
		return startScreenScene;
	}
	
	@SuppressWarnings("incomplete-switch")
	public void setStartScreenScene(Stage primaryStage) {
		Image image = new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/stressOutFiles/background.jpeg");
		ImageView imageView = new ImageView(image);
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: #2f4f4f;");
		BorderPane p = new BorderPane();
		Label Title = new Label("Stress Out");
		Title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 50));

		// Apply CSS styles to the title
		Title.setStyle("-fx-background-color: #8b0000; " + // Dark red background color
				"-fx-text-fill: white; " + // White text
				"-fx-font-size: 50px; " + // Font size 50px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px 20px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 5px; " + // Border width 5px
				"-fx-border-radius: 20px; " + // Rounded border corners
				"-fx-background-radius: 20px;"); // Rounded background corners

		HBox hbtop = new HBox();
		hbtop.getChildren().add(Title);
		hbtop.setTranslateX(250);
		hbtop.setTranslateY(20);
		p.setTop(hbtop);
		HBox hbcenter = new HBox();
		Label discription = new Label("The Game Stress Out is a game\n" + " that depands on your concentration.\n "
				+ "Here is the construction you should do to win the game\n "
				+ "First you should get a score of 120 points in 30 seconds\n "
				+ "Second when the ball color is Maganta\n" + " and you press on g you gane 10 points\n "
				+ "if you press on g and the color is AQUAMARINE then you loose 5 points\n "
				+ "the ball color change every second \n " + "at the last 10 seconds you will \n "
				+ "see the big ball color changing meaning you entered the last 10 seconds");

		// Apply CSS styles to the label
		discription.setStyle("-fx-background-color: #a5ffd6; " + // Light green background color
				"-fx-text-fill: black; " + // Black text
				"-fx-font-size: 20px; " + // Font size 20px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 2px; " + // Border width 2px
				"-fx-border-radius: 5px; " + // Rounded border corners
				"-fx-background-radius: 5px;"); // Rounded background corners

		hbcenter.getChildren().add(discription);
		hbcenter.setTranslateX(60);
		hbcenter.setTranslateY(50);
		p.setCenter(hbcenter);
		HBox hbbutton = new HBox();
		Button start = new Button("Start");

		// Apply CSS styles to the button
		start.setStyle("-fx-background-color: #ff7f50; " + // Coral background color
				"-fx-text-fill: white; " + // White text
				"-fx-font-size: 20px; " + // Font size 20px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px 20px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 2px; " + // Border width 2px
				"-fx-border-radius: 5px; " + // Rounded border corners
				"-fx-background-radius: 5px;"); // Rounded background corners

		hbbutton.getChildren().add(start);
		p.setBottom(hbbutton);
		hbbutton.setTranslateX(380);
		hbbutton.setTranslateY(250);

		//Start screen scene
		pane.getChildren().addAll(imageView, p);
		startScreenScene = new Scene(pane, 800, 750);
		imageView.fitWidthProperty().bind(startScreenScene.widthProperty());
		imageView.fitHeightProperty().bind(startScreenScene.heightProperty());
		
		//game scene
		Sphere largeSphere = new Sphere(200);
		PhongMaterial largeSphereMaterial = new PhongMaterial();
		largeSphereMaterial.setDiffuseColor(Color.BEIGE);
		largeSphere.setMaterial(largeSphereMaterial);
		largeSphere.setTranslateX(400);
		largeSphere.setTranslateY(330);
		largeSphere.setTranslateZ(0);
		
		Sphere fourthSphere = new Sphere(50);
		PhongMaterial fourthSphereMaterial = new PhongMaterial();
		fourthSphereMaterial.setDiffuseColor(Color.MAGENTA);
		fourthSphere.setMaterial(fourthSphereMaterial);
		fourthSphere.setTranslateX(400);
		fourthSphere.setTranslateY(330);
		fourthSphere.setTranslateZ(0);
		
		Circle path = new Circle();
		path.setCenterX(400);
		path.setCenterY(330);
		path.setRadius(250);

		Group group = new Group();
		group.getChildren().addAll(largeSphere, fourthSphere);
		
		//game scen
		Scene mainScene = new Scene(group, 800, 750);
		mainScene.setFill(Color.BEIGE);
		mainScene.setCamera(new PerspectiveCamera());
		
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.seconds(3));
		pathTransition.setPath(path);
		pathTransition.setNode(fourthSphere);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(Timeline.INDEFINITE);
		
		Timeline colorChangeTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			if (fourthSphereMaterial.getDiffuseColor().equals(Color.MAGENTA)) {
				fourthSphereMaterial.setDiffuseColor(Color.AQUAMARINE);
			} else {
				fourthSphereMaterial.setDiffuseColor(Color.MAGENTA);
			}
		}));
		colorChangeTimeline.setCycleCount(Timeline.INDEFINITE);
		
		Label timerLabel = new Label("Time left: " + timeSeconds);
		timerLabel.setFont(new Font("Arial", 20));
		timerLabel.setStyle(
				"-fx-background-color: #000; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");

		Label scoreLabel = new Label("Score: " + score + " / 120");
		scoreLabel.setFont(new Font("Arial", 20));
		scoreLabel.setStyle(
				"-fx-background-color: #000; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		
		Button quitButton = new Button("Quit");
		quitButton.setFont(new Font("Arial", 20));
		quitButton.setStyle(
				"-fx-background-color: #f00; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		
		nextButton =  new Button("Next");
		
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setStyle("-fx-background-color: #336699;");
		hbox.getChildren().addAll(timerLabel, scoreLabel, quitButton, nextButton);
		hbox.setAlignment(Pos.CENTER);
		hbox.setTranslateX(200);
		hbox.setStyle(
				"-fx-background-color: #0fsdf5; -fx-text-fill: #ffff; -fx-padding: 10px; -fx-border-color: #000000; -fx-border-radius: 20px; -fx-background-radius: 20px;");
		hbox.setTranslateY(largeSphere.getTranslateY() + largeSphere.getRadius() + 130);
		group.getChildren().add(hbox);
		
		//end screen scene
		Image endimage = new Image("file:///C:/Users/Mohammad%20Omar%20Shehab/Desktop/GUI/Project/stressOutFiles/background.jpeg");
		ImageView endimageView = new ImageView(endimage);
		Pane endpane = new Pane();
		endpane.setStyle("-fx-background-color: #2f4f4f;");
		BorderPane endp = new BorderPane();
		Label endTitle = new Label("Stress Out");
		endTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 50));

		// Apply CSS styles to the title
		endTitle.setStyle("-fx-background-color: #8b0000; " + // Dark red background color
				"-fx-text-fill: white; " + // White text
				"-fx-font-size: 50px; " + // Font size 50px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px 20px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 5px; " + // Border width 5px
				"-fx-border-radius: 20px; " + // Rounded border corners
				"-fx-background-radius: 20px;"); // Rounded background corners

		HBox endhbtop = new HBox();
		endhbtop.getChildren().add(endTitle);
		endhbtop.setTranslateX(250);
		endhbtop.setTranslateY(20);
		endp.setTop(endhbtop);
		HBox endhbcenter = new HBox();
		Label enddiscription = new Label();

		// Apply CSS styles to the label
		enddiscription.setStyle("-fx-background-color: #a5ffd6; " + // Light green background color
				"-fx-text-fill: black; " + // Black text
				"-fx-font-size: 20px; " + // Font size 20px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 2px; " + // Border width 2px
				"-fx-border-radius: 5px; " + // Rounded border corners
				"-fx-background-radius: 5px;"); // Rounded background corners

		endhbcenter.getChildren().add(enddiscription);
		endhbcenter.setTranslateX(60);
		endhbcenter.setTranslateY(50);
		endp.setCenter(endhbcenter);
		HBox endhbbutton = new HBox();
		Button done = new Button("Done");

		// Apply CSS styles to the button
		done.setStyle("-fx-background-color: #ff7f50; " + // Coral background color
				"-fx-text-fill: white; " + // White text
				"-fx-font-size: 20px; " + // Font size 20px
				"-fx-font-weight: bold; " + // Bold text
				"-fx-padding: 10px 20px; " + // Padding around text
				"-fx-border-color: black; " + // Black border
				"-fx-border-width: 2px; " + // Border width 2px
				"-fx-border-radius: 5px; " + // Rounded border corners
				"-fx-background-radius: 5px;"); // Rounded background corners

		endhbbutton.getChildren().add(done);
		endp.setBottom(endhbbutton);
		endhbbutton.setTranslateX(380);
		endhbbutton.setTranslateY(250);

		//Start screen scene
		endpane.getChildren().addAll(endimageView, endp);
		Scene endScreenScene = new Scene(endpane, 800, 750);
		endimageView.fitWidthProperty().bind(endScreenScene.widthProperty());
		endimageView.fitHeightProperty().bind(endScreenScene.heightProperty());
		
		done.setOnAction(e ->{
			primaryStage.setScene(goBackScene);
			primaryStage.setTitle("Mini Games");
		});
		
		Timeline countdownTimeline = new Timeline();
		countdownTimeline.setCycleCount(Timeline.INDEFINITE);
		Timeline flashTimeline = new Timeline();
		countdownTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
			timeSeconds--;
			timerLabel.setText("Time left: " + timeSeconds);
			if (timeSeconds <= 10) {
				flashTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), ev -> {
					if (largeSphereMaterial.getDiffuseColor().equals(Color.MAGENTA)) {
						largeSphereMaterial.setDiffuseColor(Color.WHITE);
					} else {
						largeSphereMaterial.setDiffuseColor(Color.MAGENTA);
					}
				}));
				flashTimeline.setCycleCount(Timeline.INDEFINITE);
				flashTimeline.play();
			}
			if (timeSeconds <= 0) {
				if(score > currentHighScore) {
					try{
						String updateQuery = "UPDATE users SET game4HighScore = " + score + " WHERE userID = " + currentUserID;
						PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
						updatePstmt.executeUpdate();
						System.out.println("Highscore updated!");
					}
					catch(SQLException ex) {
						System.out.println("Highscore not updated!");
					}
					primaryStage.setScene(endScreenScene);
					enddiscription.setText("Your score: " + score + "\nNew Highscore!"
										+ "\nYour previous highscore: " + currentHighScore);
					currentHighScore = score;
				}
				else {
					primaryStage.setScene(endScreenScene);
					enddiscription.setText("Your score: " + score
							+ "\nYour highscore: " + currentHighScore);
				}
				score = 0;
				timeSeconds = 30;
				countdownTimeline.stop();
				flashTimeline.stop();
				pathTransition.stop();
			}
		}));
		
		updateButtonColor(primaryStage,endScreenScene,enddiscription,countdownTimeline,flashTimeline,pathTransition);
		
		mainScene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case G:
				if (timeSeconds > 0) {
					if (fourthSphereMaterial.getDiffuseColor().equals(Color.MAGENTA)) {
						score += 10;
					} else {
						score -= 5;
					}
					scoreLabel.setText("Score: " + score + " / 120");
					updateButtonColor(primaryStage,endScreenScene,enddiscription,countdownTimeline,flashTimeline,pathTransition);
				}
				break;
			}
		});
		
		start.setOnAction(e -> {
			primaryStage.setScene(mainScene);
			pathTransition.play();
			colorChangeTimeline.play();
			countdownTimeline.playFromStart();
		});
		
		quitButton.setOnAction(e -> {
			primaryStage.setScene(goBackScene);
			primaryStage.setTitle("Mini Games");
			pathTransition.stop();
			colorChangeTimeline.stop();
			countdownTimeline.stop();
		});
		
	}
	
	// Method to update the color of the button based on the score
    public void updateButtonColor(Stage primaryStage, Scene endScreenScene, Label enddiscription, Timeline countdownTimeline, Timeline flashTimeline, PathTransition pathTransition) {
        if(score >= 120) {
            nextButton.setFont(new Font("Arial", 20));
            nextButton.setStyle(
                    "-fx-background-color: #0f0; -fx-text-fill: #fff; -fx-padding: 10px; -fx-border-color: #fff; -fx-border-radius: 20px; -fx-background-radius: 20px;");
            nextButton.setOnAction(e -> {
            	if(score > currentHighScore) {
					try{
						String updateQuery = "UPDATE users SET game4HighScore = " + score + " WHERE userID = " + currentUserID;
						PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
						updatePstmt.executeUpdate();
						System.out.println("Highscore updated!");
					}
					catch(SQLException ex) {
						System.out.println("Highscore not updated!");
					}
					primaryStage.setScene(endScreenScene);
					enddiscription.setText("Your score: " + score + "\nNew Highscore!"
										+ "\nYour previous highscore: " + currentHighScore);
					currentHighScore = score;
				}
				else {
					primaryStage.setScene(endScreenScene);
					enddiscription.setText("Your score: " + score
							+ "\nYour highscore: " + currentHighScore);
				}
				score = 0;
				timeSeconds = 30;
				countdownTimeline.stop();
				flashTimeline.stop();
				pathTransition.stop();
            });
        } else {
            nextButton.setFont(new Font("Arial", 20));
            nextButton.setStyle(
                "-fx-background-color: #d3d3d3; " + // Light gray background color
                "-fx-text-fill: #000; " + // Black text
                "-fx-padding: 10px; " + // Padding around text
                "-fx-border-color: #000; " + // Black border
                "-fx-border-radius: 20px; " + // Rounded border corners
                "-fx-background-radius: 20px;" // Rounded background corners
            );
        }
    }
}
