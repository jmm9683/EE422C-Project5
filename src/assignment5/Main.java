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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;


/*
 * Usage: java assignment5.Main <input file> test
 * Input file is optional. If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	final GridPane scene = new GridPane();
	final GridPane world = new GridPane();
	final VBox controls = new VBox(20);
	ComboBox critterList = new ComboBox();
	final Label makeAmtLabel = new Label("Number of Critters");
	final Label multiplierLabel = new Label("Multiplier");
	final GridPane makeAmtPane = new GridPane();
	final Slider makeAmtSlider = new Slider(0, 100, 1);
	final Slider makeAmtMult = new Slider(0, 10, 1);
	final Label makeAmtVal = new Label(Integer.toString((int) makeAmtSlider.getValue()));
	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException, URISyntaxException {
		ArrayList<String> critters = listOfCritters();
		/* Scene configuration */
		scene.setHgap(10);
		scene.setVgap(10);
		scene.setPadding(new Insets(10, 10, 10 ,10));
		
		/* Set scene columns */
	    ColumnConstraints sceneCol1 = new ColumnConstraints();
	    sceneCol1.setPercentWidth(75);
	    ColumnConstraints sceneCol2 = new ColumnConstraints();
	    sceneCol2.setPercentWidth(25);
	    scene.getColumnConstraints().addAll(sceneCol1, sceneCol2);
	    
	    /* Set scene rows */
	    RowConstraints sceneRow1 = new RowConstraints();
	    sceneRow1.setPercentHeight(75);
	    RowConstraints sceneRow2 = new RowConstraints();
	    sceneRow2.setPercentHeight(25);
	    scene.getRowConstraints().addAll(sceneRow1, sceneRow2);
		
		/* World setup */
		world.setGridLinesVisible(true);
        for (int i = 0; i < Params.world_width; i++) {
        	ColumnConstraints colConst = new ColumnConstraints();
        	colConst.setPercentWidth(100.0 / Params.world_width);
        	world.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < Params.world_height; i++) {
        	RowConstraints rowConst = new RowConstraints();
        	rowConst.setPercentHeight(100.0 / Params.world_height);
        	world.getRowConstraints().add(rowConst);
        }
        
        /* Set make Amount Pane Spacing */
		makeAmtPane.setHgap(10);
		makeAmtPane.setVgap(0);
        
        /* Set make amount columns */
	    ColumnConstraints makeAmtCol1 = new ColumnConstraints();
	    makeAmtCol1.setPercentWidth(80);
	    ColumnConstraints makeAmtCol2 = new ColumnConstraints();
	    makeAmtCol2.setPercentWidth(20);
	    makeAmtPane.getColumnConstraints().addAll(makeAmtCol1, makeAmtCol2);
	    
	    /* Set make amount rows */
	    RowConstraints makeAmtRow1 = new RowConstraints();
	    makeAmtRow1.setPercentHeight(50);
	    RowConstraints makeAmtRow2 = new RowConstraints();
	    makeAmtRow2.setPercentHeight(50);
	    RowConstraints makeAmtRow3 = new RowConstraints();
	    makeAmtRow3.setPercentHeight(50);
	    RowConstraints makeAmtRow4 = new RowConstraints();
	    makeAmtRow4.setPercentHeight(50);
	    makeAmtPane.getRowConstraints().addAll(makeAmtRow1, makeAmtRow2, makeAmtRow3, makeAmtRow4);
        
	    makeAmtPane.add(makeAmtLabel, 0, 0);
	    makeAmtPane.add(makeAmtSlider, 0, 1);
	    makeAmtPane.add(multiplierLabel, 0, 2);
	    makeAmtPane.add(makeAmtMult, 0, 3);
	    makeAmtPane.add(makeAmtVal, 1, 1);
	    
	    //ObservableList<String> oList = FXCollections.observableArrayList(listOfCritters());
	    //critterList = new ComboBox(oList);
	    //System.out.println(listOfCritters());
        
        /* Controls setup */
        controls.getChildren().addAll(critterList, makeAmtPane);
        
        /* Make amount slider setup */
		makeAmtSlider.setShowTickMarks(true);
		makeAmtSlider.setShowTickLabels(true);
		makeAmtSlider.setMajorTickUnit(10);
		makeAmtSlider.setPrefWidth(1000);
		makeAmtSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	makeAmtSlider.setValue(new_val.intValue());
                makeAmtVal.setText(String.valueOf((int)((int) makeAmtMult.getValue() * new_val.intValue()))); }
        });
		
		/* Make amount multiplier setup */
		makeAmtMult.setShowTickMarks(true);
		makeAmtMult.setShowTickLabels(true);
		makeAmtMult.setMajorTickUnit(5);
		makeAmtMult.setPrefWidth(1000);
		makeAmtMult.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                makeAmtVal.setText(String.valueOf((int)((int) makeAmtSlider.getValue() * new_val.intValue()))); }
        });
	    
		/* Add everything to scene */
	    scene.add(world, 0, 0);
	    scene.add(controls, 1, 0);
		
        primaryStage.setScene(new Scene(scene, 800, 600));
	    primaryStage.show();
        
       
	}
	
	

	public static void main(String[] args) {
		launch(args);
		Input.takeInput(new Scanner(System.in));
	}
	
	private  ArrayList<String> listOfCritters ()throws URISyntaxException, ClassNotFoundException{
		ArrayList <String>allcrits = new ArrayList<String>();
		File directory =new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String pkg = Main.class.getPackage().toString().split(" ")[1];
		directory=new File(directory.getAbsolutePath()+"/"+pkg);
		String [] lfile = directory.list();
		for (int i =0; i<lfile.length;i++){
			if((Class.forName(pkg+".Critter").isAssignableFrom(Class.forName(pkg+"."+lfile[i].substring(0, lfile[i].length()-6))))
		      &&!(Modifier.isAbstract(Class.forName(pkg+"."+lfile[i].substring(0, lfile[i].length()-6)).getModifiers()))){
			
		allcrits.add(lfile[i].substring(0, lfile[i].length()-6));
			}
		
		

		}
		return allcrits;
}
}