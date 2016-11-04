/* 
 * CRITTERS GUI Main.java
 * EE422C Project 5 submission by
 * Brent Atchison
 * bma862
 * 16455
 * Dhruv Mathew
 * dkm989
 * 16455
 * Slip days used: <0>
 * Fall 2016
 */

package assignment5; // cannot be in default package
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import java.util.*;


/*
 * Usage: java assignment5.Main <input file> test
 * Input file is optional. If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	static int count = 0;
	final Slider slider = new Slider(0, 100, 0);
	final Label sliderVal = new Label(Integer.toString((int) slider.getValue()));
	@Override
	public void start(Stage primaryStage) {
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(10);
		slider.setMaxWidth(500);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	slider.setValue(new_val.intValue());
                sliderVal.setText(String.valueOf(new_val.intValue())); }
        });
		HBox box = new HBox();
    	box.setAlignment(Pos.CENTER);
        primaryStage.setTitle("Count");
        Button btn = new Button();
        btn.setText("Count");
        Label lab = new Label(Integer.toString(count));
        box.getChildren().addAll(lab);
        btn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent event) { lab.setText(Integer.toString(++count)); }
        });

		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(box, btn, slider, sliderVal);
        primaryStage.setScene(new Scene(vbox, 1000, 600));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		Input.takeInput(new Scanner(System.in));
	}
}
