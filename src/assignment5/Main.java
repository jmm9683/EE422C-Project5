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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;

import assignment5.Critter.CritterShape;


/*
 * Usage: java assignment5.Main <input file> test
 * Input file is optional. If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	static GridPane scene = new GridPane();
	static GridPane world = new GridPane();
	static VBox controls = new VBox(10);
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/* Make pane */
	static Label makeAmtLabel = new Label("Number of Critters");
	static Label makeMultLabel = new Label("Multiplier");
	static GridPane makeAmtPane = new GridPane();
	static Slider makeAmtSlider = new Slider(0, 100, 1);
	static Slider makeAmtMult = new Slider(0, 10, 1);
	static Label makeAmtVal = new Label(Integer.toString((int) makeAmtSlider.getValue()));
	static Button makeButton = new Button();
	
	/* Step pane */
	static Label stepLabel = new Label("Number of Steps");
	static Label stepMultLabel = new Label("Multiplier");
	static GridPane stepPane = new GridPane();
	static Slider stepSlider = new Slider(0, 100, 1);
	static Slider stepMult = new Slider(0, 10, 1);
	static Label stepVal = new Label(Integer.toString((int) makeAmtSlider.getValue()));
	static Button stepButton = new Button();
	
	/* Shapes */
	static Circle circle = new Circle();
	static Polygon square = new Polygon();
	static Polygon triangle = new Polygon();
	static Polygon diamond = new Polygon();
	static Polygon star = new Polygon();
	
	
	@Override
	public void start(Stage primaryStage) throws ClassNotFoundException, URISyntaxException {
		
		/* Scene setup */
		sceneConfig();
		
		/* Initialize shapes */
		shapeConfig();
		
		/* World setup */
		worldConfig();
		
		world.add(circle, 0, 0);
		world.add(square, 1, 0);
		world.add(triangle, 2, 0);
		world.add(diamond, 3, 0);
		world.add(star, 4, 0);
        
        /* Button setup */
        makeButton.setText("Make");
        if (makeAmtSlider.getValue() == 0 || makeAmtMult.getValue() == 0) { makeButton.setDisable(true); }
        else { makeButton.setDisable(false); }

        stepButton.setText("Step");
        if (stepSlider.getValue() == 0 || stepMult.getValue() == 0) { stepButton.setDisable(true); }
        else { stepButton.setDisable(false); }
        
        /* Slider setup */
        makeAmtConfig();
        stepConfig();
	    
	    ObservableList<String> oList = FXCollections.observableArrayList(listOfCritters());
	    final ComboBox<String> critterList = new ComboBox<String>(oList);
	    //critterList.getSelectionModel().setSelectedIndex(0);
        
        /* Controls setup */
        controls.getChildren().addAll(critterList, makeAmtPane, stepPane);
	    
		/* Add everything to scene */
	    scene.add(world, 0, 0);
	    scene.add(controls, 1, 0);
		
        primaryStage.setScene(new Scene(scene, screenSize.getWidth(), screenSize.getHeight()));
	    primaryStage.show();
        
	}
	
	

	public static void main(String[] args) {
		launch(args);
		//Input.takeInput(new Scanner(System.in));
	}
	
	public static void sceneConfig() {
		scene.setHgap(10);
		scene.setVgap(10);
		scene.setPadding(new Insets(10, 10, 10 ,10));
		
		/* Set scene columns */
	    ColumnConstraints sceneCol1 = new ColumnConstraints();
	    sceneCol1.setPercentWidth(75);
	    sceneCol1.setHalignment(HPos.CENTER);
	    ColumnConstraints sceneCol2 = new ColumnConstraints();
	    sceneCol2.setPercentWidth(25);
	    sceneCol2.setHalignment(HPos.CENTER);
	    scene.getColumnConstraints().addAll(sceneCol1, sceneCol2);
	    
	    /* Set scene rows */
	    RowConstraints sceneRow1 = new RowConstraints();
	    sceneRow1.setPercentHeight(75);
	    sceneRow1.setValignment(VPos.CENTER);
	    RowConstraints sceneRow2 = new RowConstraints();
	    sceneRow2.setPercentHeight(25);
	    sceneRow2.setValignment(VPos.CENTER);
	    scene.getRowConstraints().addAll(sceneRow1, sceneRow2);
	}
	
	public static void worldConfig() {
		world.getChildren().clear();
		world.setGridLinesVisible(true);
        for (int i = 0; i < Params.world_width; i++) {
        	ColumnConstraints colConst = new ColumnConstraints();
        	colConst.setPercentWidth(100.0 / Params.world_width);
        	colConst.setHalignment(HPos.CENTER);
        	world.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < Params.world_height; i++) {
        	RowConstraints rowConst = new RowConstraints();
        	rowConst.setPercentHeight(100.0 / Params.world_height);
        	rowConst.setValignment(VPos.CENTER);
        	world.getRowConstraints().add(rowConst);
        }
	}
	
	public static void makeAmtConfig() {
		
		/* Set make amount columns */
	    ColumnConstraints makeAmtCol1 = new ColumnConstraints();
	    makeAmtCol1.setPercentWidth(75);
	    makeAmtCol1.setHalignment(HPos.CENTER);
	    ColumnConstraints makeAmtCol2 = new ColumnConstraints();
	    makeAmtCol2.setPercentWidth(25);
	    makeAmtCol2.setHalignment(HPos.CENTER);
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
		
		/* Make amount slider setup */
		makeAmtSlider.setShowTickMarks(true);
		makeAmtSlider.setShowTickLabels(true);
		makeAmtSlider.setMajorTickUnit(25);
		makeAmtSlider.setPrefWidth(1000);
		makeAmtSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	makeAmtSlider.setValue(new_val.intValue());
                makeAmtVal.setText(String.valueOf((int)((int) makeAmtMult.getValue() * new_val.intValue()))); }
        });
		
		/* Make amount multiplier setup */
		makeAmtMult.setShowTickMarks(true);
		makeAmtMult.setShowTickLabels(true);
		makeAmtMult.setSnapToTicks(true);
		makeAmtMult.setMajorTickUnit(5);
		makeAmtMult.setPrefWidth(1000);
		makeAmtMult.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                makeAmtVal.setText(String.valueOf((int)((int) makeAmtSlider.getValue() * new_val.intValue()))); }
        });
        
	    makeAmtPane.add(makeAmtLabel, 0, 0);
	    makeAmtPane.add(makeAmtSlider, 0, 1);
	    makeAmtPane.add(makeMultLabel, 0, 2);
	    makeAmtPane.add(makeAmtMult, 0, 3);
	    makeAmtPane.add(makeAmtVal, 1, 1);
	    makeAmtPane.add(makeButton, 1, 2);
	}
	
	public static void stepConfig() {
		
		/* Set make amount columns */
	    ColumnConstraints stepCol1 = new ColumnConstraints();
	    stepCol1.setPercentWidth(75);
	    stepCol1.setHalignment(HPos.CENTER);
	    ColumnConstraints stepCol2 = new ColumnConstraints();
	    stepCol2.setPercentWidth(25);
	    stepCol2.setHalignment(HPos.CENTER);
	    stepPane.getColumnConstraints().addAll(stepCol1, stepCol2);
	    
	    /* Set make amount rows */
	    RowConstraints stepRow1 = new RowConstraints();
	    stepRow1.setPercentHeight(50);
	    RowConstraints stepRow2 = new RowConstraints();
	    stepRow2.setPercentHeight(50);
	    RowConstraints stepRow3 = new RowConstraints();
	    stepRow3.setPercentHeight(50);
	    RowConstraints stepRow4 = new RowConstraints();
	    stepRow4.setPercentHeight(50);
	    stepPane.getRowConstraints().addAll(stepRow1, stepRow2, stepRow3, stepRow4);
		
		/* Make amount slider setup */
		stepSlider.setShowTickMarks(true);
		stepSlider.setShowTickLabels(true);
		stepSlider.setMajorTickUnit(25);
		stepSlider.setPrefWidth(1000);
		stepSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	stepSlider.setValue(new_val.intValue());
                stepVal.setText(String.valueOf((int)((int) stepMult.getValue() * new_val.intValue()))); }
        });
		
		/* Make amount multiplier setup */
		stepMult.setShowTickMarks(true);
		stepMult.setShowTickLabels(true);
		stepMult.setSnapToTicks(true);
		stepMult.setMajorTickUnit(5);
		stepMult.setPrefWidth(1000);
		stepMult.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                stepVal.setText(String.valueOf((int)((int) stepSlider.getValue() * new_val.intValue()))); }
        });
		
		stepPane.add(stepLabel, 0, 0);
		stepPane.add(stepSlider, 0, 1);
		stepPane.add(stepMultLabel, 0, 2);
		stepPane.add(stepMult, 0, 3);
		stepPane.add(stepVal, 1, 1);
		stepPane.add(stepButton, 1, 2);
	}
	
	public static void shapeConfig() {

		circle.setRadius(200.0 / Params.world_width);
		square.getPoints().addAll(
				0.0, 0.0,
				400.0 / Params.world_width, 0.0,
				400.0 / Params.world_width, 400.0 / Params.world_width,
				0.0, 400.0 / Params.world_width);
		triangle.getPoints().addAll(
				200.0 / Params.world_width, 0.0,
				0.0, 400.0 / Params.world_width,
				400.0 / Params.world_width, 400.0 / Params.world_width);
		diamond.getPoints().addAll(
				200.0 / Params.world_width, 0.0,
				400.0 / Params.world_width, 200.0 / Params.world_width,
				200.0 / Params.world_width, 400.0 / Params.world_width,
				0.0, 200.0 / Params.world_width);
		star.getPoints().addAll(
				6.0, 0.0,
				7.5, 3.0,
				12.0, 3.0,
				9.0, 6.0,
				10.0, 10.0,
				6.0, 7.5,
				2.0, 10.0,
				3.0, 6.0,
				0.0, 3.0,
				4.5, 3.0);

		triangle.getPoints().addAll(5.0, 0.0, 1.0, 5.0, 9.0, 5.0);
		square.getPoints().addAll(0.0, 0.0, 5.0, 0.0, 5.0, 5.0, 0.0, 5.0);
		

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
	public static void updatecanvas(){
		worldConfig();
		String a = "Crittersgetter";
		try {
			List<Critter> chkpop = Critter.getInstances(a);
			for (Critter ls : chkpop){
				
				int oldx =ls.getX();
				int oldy = ls.getY();
				
				CritterShape b = ls.viewShape();
				if(b ==CritterShape.CIRCLE){
					Circle circ = new Circle();
					circ.setRadius(circle.getRadius());
				}
				
				else{
					Polygon poly = new Polygon();
					poly.setFill  (ls.viewFillColor());
					poly.setStroke(ls.viewOutlineColor());
				if(b==CritterShape.SQUARE){
					poly.getPoints().addAll(square.getPoints());
					}
				
				if(b==CritterShape.DIAMOND){
					poly.getPoints().addAll(diamond.getPoints());
					}
				
				if(b==CritterShape.STAR){
					poly.getPoints().addAll(star.getPoints());
					}
				
				if(b== CritterShape.TRIANGLE){
					poly.getPoints().addAll(triangle.getPoints());
				}
				
				scene.add(poly, oldx, oldy);
				}
				
				// Circle circ = new Circle();
				//circ.set..a.. setRadius (circle)
				
			}
		} catch (InvalidCritterException e) {

			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	
}