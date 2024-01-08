package balloonAssassinator;

import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.RotateTransition;

public class ColorButton extends Button{
	private Color buttonColor;
	private RotateTransition rt;
	private static int count = 0;
	
	public ColorButton(Color c) {
		super();
		count++;
		this.buttonColor = c;
		setMinSize(80, 80);
		setBackground(new Background(new BackgroundFill(c, new CornerRadii(20), Insets.EMPTY)));
		setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(17), new BorderWidths(5))));
		rt = new RotateTransition(Duration.seconds(1),this);
		rt.setFromAngle(0);
		rt.setToAngle(360);
		rt.setCycleCount(Timeline.INDEFINITE);
		rt.setAutoReverse(true);
		rt.setDelay(Duration.seconds(2 + count));
	}
	
	public void play() {
		rt.play();
	}
	
	public void stop() {
		rt.stop();
	}
	
	public void pause() {
		rt.pause();
	}
	
	public Color getColor() {
		return buttonColor;
	}
}
