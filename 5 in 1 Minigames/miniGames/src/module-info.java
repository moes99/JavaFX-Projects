module miniGames {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.media;
	
	opens balloonAssassinator to javafx.graphics, javafx.fxml;
	opens main to javafx.graphics, javafx.fxml;
}
