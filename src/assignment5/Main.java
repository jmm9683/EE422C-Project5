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
	final VBox makeAmtLab = new VBox(10);
	final Label makeAmtLabel = new Label("Number of Critters");
	final GridPane makeAmtPane = new GridPane();
	final Slider makeAmtSlider = new Slider(0, 100, 0);
	final Label makeVal = new Label(Integer.toString((int) makeAmtSlider.getValue()));
	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException, URISyntaxException {
		
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
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(75);
	    RowConstraints row2 = new RowConstraints();
	    row2.setPercentHeight(25);
	    scene.getRowConstraints().addAll(row1, row2);
		
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
        
        /*  */
		makeAmtPane.setHgap(10);
		makeAmtPane.setVgap(10);
        
        /* Set make amount columns */
	    ColumnConstraints makeAmtCol1 = new ColumnConstraints();
	    makeAmtCol1.setPercentWidth(10);
	    ColumnConstraints makeAmtCol2 = new ColumnConstraints();
	    makeAmtCol2.setPercentWidth(90);
	    makeAmtPane.getColumnConstraints().addAll(makeAmtCol1, makeAmtCol2);
	    
	    /* Set make amount rows */
	    ColumnConstraints makeAmtRow1 = new ColumnConstraints();
	    makeAmtRow1.setPercentWidth(50);
	    ColumnConstraints makeAmtRow2 = new ColumnConstraints();
	    makeAmtRow2.setPercentWidth(50);
	    makeAmtPane.getColumnConstraints().addAll(makeAmtRow1, makeAmtRow2);
        
	    makeAmtPane.add(makeAmtLabel, 1, 0);
	    makeAmtPane.add(makeVal, 0, 1);
	    makeAmtPane.add(makeAmtSlider, 1, 1);
	    
	    //ObservableList<String> oList = FXCollections.observableArrayList(listOfCritters());
	    //critterList = new ComboBox(oList);
	    //System.out.println(listOfCritters());
        
        /* Controls setup */
        controls.getChildren().addAll(critterList, makeAmtPane);
        
        /* Slider setup */
		makeAmtSlider.setShowTickMarks(true);
		makeAmtSlider.setShowTickLabels(true);
		makeAmtSlider.setMajorTickUnit(10);
		makeAmtSlider.setPrefWidth(1000);
		makeAmtSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	makeAmtSlider.setValue(new_val.intValue());
                makeVal.setText(String.valueOf(new_val.intValue())); }
        });
		
		/*
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(world, makeAmt, makeVal);
		 Canvas canvas = new Canvas(250,250);
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        gc.setFill(Color.BLUE);
	        gc.fillRect(75,75,100,100);
	        vbox.getChildren().add(canvas);
        primaryStage.setScene(new Scene(vbox, 1000, 600));*/
	    
		/* Add everything to scene */
	    scene.add(world, 0, 0);
	    scene.add(controls, 1, 0);
	    
	    //scene.setPrefSize(100, 100);
		//scene.getChildren().addAll(world, makeAmt);
		
        primaryStage.setScene(new Scene(scene, 800, 600));
	    primaryStage.show();
        
       
	}
	
	public static void main(String[] args) {
		launch(args);
		Input.takeInput(new Scanner(System.in));
	}
	
	private static ArrayList<String> listOfCritters ()throws URISyntaxException, ClassNotFoundException{
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