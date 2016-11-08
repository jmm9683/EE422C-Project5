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

package assignment5;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;
import java.io.*;

import assignment5.Critter.CritterShape;


/*
 * Usage: java assignment5.Main <input file> test
 * Input file is optional. If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {
	
	private static String myPackage;
	static { myPackage = Critter.class.getPackage().toString().split(" ")[1]; }
	
	/* Overall UI tools */
	static GridPane scene = new GridPane();
	static GridPane world = new GridPane();
	static VBox controls = new VBox(10);
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static ComboBox<String> critterList = new ComboBox<String>();
	Timeline timeline = new Timeline ();
	
	/* Make pane */
	static Label makeAmtLabel = new Label("Number of Critters");
	static Label makeMultLabel = new Label("Multiplier");
	static GridPane makeAmtPane = new GridPane();
	static Slider makeAmtSlider = new Slider(0, 100, 1);
	static Slider makeAmtMult = new Slider(0, 10, 1);
	static Label makeAmtVal = new Label(Integer.toString((int) makeAmtSlider.getValue()));
	static Button makeButton = new Button("Make");


	/* Step pane */
	static Label stepLabel = new Label("Number of Steps");
	static Label stepMultLabel = new Label("Multiplier");
	static GridPane stepPane = new GridPane();
	static Slider stepSlider = new Slider(0, 100, 1);
	static Slider stepMult = new Slider(0, 10, 1);
	static Label stepVal = new Label(Integer.toString((int) stepSlider.getValue()));
	static Button stepButton = new Button("Step");
	
	/* Animate pane */
	static class AnimateTimer extends AnimationTimer{
    	private int timesteps;
    	AnimateTimer(int timesteps){ this.timesteps = timesteps; }
    	@Override public void handle(long now){
    		for (int i = 0; i < timesteps; i++){ Critter.worldTimeStep(); }
    		updateCanvas();
    	}
    }
	static Label aniLabel = new Label("Animate Speed");
	static GridPane aniPane = new GridPane();
	static Slider aniSlider = new Slider(0, 100, 1);
	static Label aniVal = new Label(Integer.toString((int) aniSlider.getValue()));
	static Button aniButton = new Button("Animate");
	static boolean anibool = false;
	static AnimateTimer aTimer = new AnimateTimer((int) aniSlider.getValue());
	
	/* Run statistics */
    static class Console extends OutputStream {
        private TextArea output;
        public Console(TextArea ta) { this.output = ta; }
        @Override public void write(int i) throws IOException { output.appendText(String.valueOf((char) i)); }
    }
	static VBox statsContainer = new VBox();
	static Button statsButton = new Button("Run Stats");
	static TextArea stats = new TextArea();
	static PrintStream ps;
	
	/* Seed */
	static Label seedLabel = new Label("Seed");
	static GridPane seedPane = new GridPane();
	static Button seedButton = new Button("Set Seed");
	static TextField seed = new TextField() {
		@Override public void replaceText(int start, int end, String text) { if (text.matches("[0-9]*")) { super.replaceText(start, end, text); } }
	    @Override public void replaceSelection(String text) { if (text.matches("[0-9]*")) { super.replaceSelection(text); } }
	};
	
	/* Exit */
	static VBox exitBox = new VBox();
	static Button exitButton = new Button("End Program");
	
	/* Shapes */
	static Circle circle = new Circle();
	static Polygon square = new Polygon();
	static Polygon triangle = new Polygon();
	static Polygon diamond = new Polygon();
	static Polygon star = new Polygon();
	
	/* Window size */
	static double winWidth = screenSize.getWidth();
	static double winHeight = screenSize.getHeight();
	static boolean chkcombo = false;
   
	
	@Override public void start(Stage primaryStage) throws ClassNotFoundException, URISyntaxException {
		

		seedConfig();
		
		/* Listen for screen resize */
		screenListener();
		
		/* Test polygons
		world.add(circle, 0, 0);
		world.add(square, 1, 0);
		world.add(triangle, 2, 0);
		world.add(diamond, 3, 0);
		world.add(star, 4, 0);
        */

        /* Slider setup */
        makeAmtConfig();
        stepConfig();
       
        ObservableList<String> oList = FXCollections.observableArrayList(listOfCritters());
        critterList = new ComboBox<String>(oList);
        critterList.setPromptText("Select Critter");
	    //critterList.getSelectionModel().setSelectedIndex(0);
        
       // ------- Button Handlers 
	   
        critterList.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	chkcombo = true;
		    }
	    });
        
        
//		
        makeButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
				int nocritters =(int) ((int) makeAmtSlider.getValue() * makeAmtMult.getValue());
				for (int i =0; i<nocritters;i++){
					try {
						Critter.makeCritter(critterList.getValue());
					} catch (Exception e1) { }
				}
				updatecanvas();
			}
        });
        
        stepButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		         
		         int nsteps = (int) (stepSlider.getValue()*stepMult.getValue());
		         for (int j=0; j<nsteps;j++){
                    Critter.worldTimeStep();
		         
		         }
		         updatecanvas();
		         }
		         
		    });
        

        
        
        
        
        
        class AnimateTimer extends AnimationTimer{
        	private int timesteps;
        	AnimateTimer(int timesteps){
        		this.timesteps =timesteps;
        		
        	}
        	
        	@Override
        	public void handle(long now){
        		
        		for (int i =0; i<timesteps;i++){
        			
        			Critter.worldTimeStep();
        			
        		}
        		updatecanvas();
        		
        	}
        	
        	
        }
       static AnimateTimer aTimer =new AnimateTimer(0);
        
        aniButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try{
		    	if (aniButton.isSelected()){
		    		
		    			
		    			int timeSteps =(int) aniSlider.getValue();
		    			anibool=true;
		    			aTimer = new AnimateTimer(timeSteps);
		    			aTimer.start();
		    			 makeAmtSlider.setDisable(true);
		    			 makeAmtMult.setDisable(true);
		    		    makeButton.setDisable(true);
		    		    stepSlider.setDisable(true);
		    		 stepMult.setDisable(true);
		    		 stepButton.setDisable(true);
		    	     aniButton.setDisable(true);
		    	     aniSlider.setDisable(true);
		    		 
		    		 statsButton.setDisable(true);
		    		 seedButton.setDisable(true);
		    		 seed.setDisable(true);
		    		 exitButton.setDisable(true);
		    	     		
		    				
		    		}
		    	 else{
				    	anibool=false;
				    	aTimer.stop();
				    	 makeAmtSlider.setDisable(false);
		    			 makeAmtMult.setDisable(false);
		    		    makeButton.setDisable(false);
		    		    stepSlider.setDisable(false);
		    		 stepMult.setDisable(false);
		    		 stepButton.setDisable(false);
		    	     aniButton.setDisable(false);
		    	     aniSlider.setDisable(false);
		    		 statsButton.setDisable(false);
		    		 seedButton.setDisable(false);
		    		 seed.setDisable(false);
		    		 exitButton.setDisable(false);
				    
				       
				         
				    }
		    		
		    	}
		    
		   
		         
		     
		         
		    catch(Exception e1){
    			
    		}
		         
		    }});
        

        statsContainer.setAlignment(Pos.CENTER);
        statsContainer.setSpacing(10);
        statsContainer.setPadding(new Insets(20, 0, 0, 0));
        statsContainer.getChildren().addAll(statsButton, stats);
        
        class Console extends OutputStream {

            private TextArea output;

            public Console(TextArea ta) {
                this.output = ta;
            }

            @Override
            public void write(int i) throws IOException {
                output.appendText(String.valueOf((char) i));
            }
        }
        
        statsButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	stats.clear();
		    	Console console = new Console(stats);
		        PrintStream ps = new PrintStream(console);
		        System.setOut(ps);
		        System.setErr(ps);
		    	stats();
	    	}
        });

		/* Initialization */
		runInit();

        
        /* Add controls to pane */
        controls.getChildren().addAll(critterList, makeAmtPane, stepPane, statsContainer, seedPane, exitBox);
	    
		/* Add everything to scene */
	    scene.add(world, 0, 0);
	    scene.add(controls, 1, 0);
		scene.add(aniPane, 0, 1);
	    
		/* Set stage */
        primaryStage.setScene(new Scene(scene, screenSize.getWidth(), screenSize.getHeight()));
	    primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Initialize everything
	 * @throws URISyntaxException 
	 * @throws ClassNotFoundException 
	 */
	private static void runInit() throws ClassNotFoundException, URISyntaxException {
		
		/* Basic setup */
		sceneConfig();
		shapeConfig();
		worldConfig();
		comboConfig();
		aniConfig();
		statsConfig();
		seedConfig();
        makeAmtConfig();
        stepConfig();
        exitConfig();
        
        /* Initialize handlers */
        handlerInit();
		
		/* Listen for screen resize */
		screenListener();
	}
	
	/**
	 * Configure entire scene
	 */
	private static void sceneConfig() {
		scene.setHgap(10);
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
	    sceneRow1.setPercentHeight(85);
	    sceneRow1.setValignment(VPos.CENTER);
	    RowConstraints sceneRow2 = new RowConstraints();
	    sceneRow2.setPercentHeight(15);
	    sceneRow2.setValignment(VPos.TOP);
	    scene.getRowConstraints().addAll(sceneRow1, sceneRow2);
	}
	
	/**
	 * Configure world
	 */
	private static void worldConfig() {
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
	
	/**
	 * Clear current world
	 */
	private static void worldClear() {
		Node node = world.getChildren().get(0);
		world.getChildren().clear();
		world.getChildren().add(0,node);
	}
	
	/**
	 * Configure combo box
	 * @throws ClassNotFoundException
	 * @throws URISyntaxException
	 */
	private static void comboConfig() throws ClassNotFoundException, URISyntaxException {
        ObservableList<String> oList = FXCollections.observableArrayList(listOfCritters());
        critterList = new ComboBox<String>(oList);
        critterList.setPromptText("Select Critter");
	}
	
	/**
	 * Configure make amount pane
	 */
	private static void makeAmtConfig() {
		
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
		makeAmtSlider.setMinorTickCount(4);
		makeAmtSlider.setPrefWidth(1000);
		makeAmtSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	makeAmtSlider.setValue(new_val.intValue());
            	if (((int) makeAmtMult.getValue() * new_val.intValue() < 1)) { makeButton.setDisable(true); }
            	else { makeButton.setDisable(false); }
                makeAmtVal.setText(String.valueOf((int)((int) makeAmtMult.getValue() * new_val.intValue()))); }
        });
		
		/* Make amount multiplier setup */
		makeAmtMult.setShowTickMarks(true);
		makeAmtMult.setShowTickLabels(true);
		makeAmtMult.setSnapToTicks(true);
		makeAmtMult.setMajorTickUnit(5);
		makeAmtMult.setMinorTickCount(4);
		makeAmtMult.setPrefWidth(1000);
		makeAmtMult.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	if (((int) makeAmtSlider.getValue() * new_val.intValue() < 1)) { makeButton.setDisable(true); }
            	else { makeButton.setDisable(false); }
                makeAmtVal.setText(String.valueOf((int)((int) makeAmtSlider.getValue() * new_val.intValue()))); }
        });

		/* Add everything to pane */
	    makeAmtPane.add(makeAmtLabel, 0, 0);
	    makeAmtPane.add(makeAmtSlider, 0, 1);
	    makeAmtPane.add(makeMultLabel, 0, 2);
	    makeAmtPane.add(makeAmtMult, 0, 3);
	    makeAmtPane.add(makeAmtVal, 1, 1);
	    makeAmtPane.add(makeButton, 1, 2);
	}
	
	/**
	 * Configure step pane
	 */
	private static void stepConfig() {
		
		/* Set step columns */
	    ColumnConstraints stepCol1 = new ColumnConstraints();
	    stepCol1.setPercentWidth(75);
	    stepCol1.setHalignment(HPos.CENTER);
	    ColumnConstraints stepCol2 = new ColumnConstraints();
	    stepCol2.setPercentWidth(25);
	    stepCol2.setHalignment(HPos.CENTER);
	    stepPane.getColumnConstraints().addAll(stepCol1, stepCol2);
	    
	    /* Set step rows */
	    RowConstraints stepRow1 = new RowConstraints();
	    stepRow1.setPercentHeight(50);
	    RowConstraints stepRow2 = new RowConstraints();
	    stepRow2.setPercentHeight(50);
	    RowConstraints stepRow3 = new RowConstraints();
	    stepRow3.setPercentHeight(50);
	    RowConstraints stepRow4 = new RowConstraints();
	    stepRow4.setPercentHeight(50);
	    stepPane.getRowConstraints().addAll(stepRow1, stepRow2, stepRow3, stepRow4);
		
		/* Step slider setup */
		stepSlider.setShowTickMarks(true);
		stepSlider.setShowTickLabels(true);
		stepSlider.setMajorTickUnit(25);
		stepSlider.setMinorTickCount(4);
		stepSlider.setPrefWidth(1000);
		stepSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	stepSlider.setValue(new_val.intValue());
            	if (((int) stepMult.getValue() * new_val.intValue() < 1)) { stepButton.setDisable(true); }
            	else { stepButton.setDisable(false); }
                stepVal.setText(String.valueOf((int)((int) stepMult.getValue() * new_val.intValue()))); }
        });
		
		/* Step multiplier setup */
		stepMult.setShowTickMarks(true);
		stepMult.setShowTickLabels(true);
		stepMult.setSnapToTicks(true);
		stepMult.setMajorTickUnit(5);
		stepMult.setMinorTickCount(4);
		stepMult.setPrefWidth(1000);
		stepMult.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	if (((int) stepSlider.getValue() * new_val.intValue() < 1)) { stepButton.setDisable(true); }
            	else { stepButton.setDisable(false); }
                stepVal.setText(String.valueOf((int)((int) stepSlider.getValue() * new_val.intValue()))); }
        });

		/* Add everything to pane */
		stepPane.add(stepLabel, 0, 0);
		stepPane.add(stepSlider, 0, 1);
		stepPane.add(stepMultLabel, 0, 2);
		stepPane.add(stepMult, 0, 3);
		stepPane.add(stepVal, 1, 1);
		stepPane.add(stepButton, 1, 2);
	}
	
	/**
	 * Configure animate pane
	 */
	private static void aniConfig() {
		
		/* Set animation columns */
	    ColumnConstraints aniCol1 = new ColumnConstraints();
	    aniCol1.setPercentWidth(60);
	    aniCol1.setHalignment(HPos.CENTER);
	    ColumnConstraints aniCol2 = new ColumnConstraints();
	    aniCol2.setPercentWidth(20);
	    aniCol2.setHalignment(HPos.CENTER);
	    ColumnConstraints aniCol3 = new ColumnConstraints();
	    aniCol3.setPercentWidth(20);
	    aniCol3.setHalignment(HPos.CENTER);
	    aniPane.getColumnConstraints().addAll(aniCol1, aniCol2, aniCol3);

	    /* Set animation rows */
	    RowConstraints aniRow1 = new RowConstraints();
	    aniRow1.setPercentHeight(50);
	    aniRow1.setValignment(VPos.BOTTOM);
	    RowConstraints aniRow2 = new RowConstraints();
	    aniRow2.setPercentHeight(50);
	    aniRow2.setValignment(VPos.CENTER);
	    aniPane.getRowConstraints().addAll(aniRow1, aniRow2);
	    
	    /* Animation slider setup */
		aniSlider.setShowTickMarks(true);
		aniSlider.setShowTickLabels(true);
		aniSlider.setMajorTickUnit(25);
		aniSlider.setPrefWidth(1000);
		aniSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	aniSlider.setValue(new_val.intValue());
            	if (new_val.intValue() < 1) { aniButton.setDisable(true); }
            	else { aniButton.setDisable(false); }
                aniVal.setText(String.valueOf(new_val.intValue())); }
        });
		
		/* Add everything to pane */
		aniPane.setPadding(new Insets(0, 0, 10, 0));
		aniPane.add(aniLabel, 0, 0);
		aniPane.add(aniSlider, 0, 1);
		aniPane.add(aniVal, 1, 1);
		aniPane.add(aniButton, 2, 1);
		
	}
	
	/**
	 * Configure statistics
	 */
	private static void statsConfig() {
        statsContainer.setAlignment(Pos.CENTER);
        statsContainer.setSpacing(10);
        statsContainer.setPadding(new Insets(20, 0, 0, 0));
        statsContainer.getChildren().addAll(statsButton, stats);
	}
	
	/**
	 * Configure exit button
	 */
	private static void exitConfig() {
        exitBox.setAlignment(Pos.CENTER);
        exitBox.setPadding(new Insets(20, 0, 0, 0));
        exitBox.getChildren().add(exitButton);
        exitButton.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { Platform.exit(); } });
	}
	
	/**
	 * Initialize handlers
	 */
	private static void handlerInit() {
		
		/* Combo box handler */
        critterList.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) { chkcombo = true; } });
        
        /* Make button handler */
        makeButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
				int nocritters =(int) ((int) makeAmtSlider.getValue() * makeAmtMult.getValue());
				for (int i =0; i<nocritters;i++) { try { Critter.makeCritter(critterList.getValue()); } catch (Exception e1) { } }
				updateCanvas();
			}
        });

        /* Step button handler */
        stepButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		         int nsteps = (int) (stepSlider.getValue()*stepMult.getValue());
		         for (int j = 0 ;j < nsteps; j++){ Critter.worldTimeStep(); }
		         updateCanvas();
	        }
	    });

        /* Animate button handler */
        aniButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	try {
		    		aTimer.timesteps = (int) aniSlider.getValue();
		    		anibool = !anibool;
	    			if (anibool) {
	    				aniButton.setText("Stop");
		    			aTimer.start();
		    			makeAmtSlider.setDisable(true);
		    			makeAmtMult.setDisable(true);
		    		    makeButton.setDisable(true);
		    		    stepSlider.setDisable(true);
		    		    stepMult.setDisable(true);
		    		    stepButton.setDisable(true);
		    		    aniSlider.setDisable(true);
		    		    statsButton.setDisable(true);
		    		    seedButton.setDisable(true);
		    		    seed.setDisable(true);
		    		    exitButton.setDisable(true);	
		    		}
			    	else {
			    		aniButton.setText("Animate");
					    aTimer.stop();
					    makeAmtSlider.setDisable(false);
			    		makeAmtMult.setDisable(false);
			    		makeButton.setDisable(false);
			    		stepSlider.setDisable(false);
			    		stepMult.setDisable(false);
			    		stepButton.setDisable(false);
			    	    aniSlider.setDisable(false);
			    		statsButton.setDisable(false);
			    		seedButton.setDisable(false);
			    		seed.setDisable(false);
			    		exitButton.setDisable(false);
		    		}
    			} catch(Exception e1) { }
	    	}
	    });
        
        /* Statistics button handler */
        statsButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	stats.clear();
		    	Console console = new Console(stats);
		        PrintStream ps = new PrintStream(console);
		        System.setOut(ps);
		        System.setErr(ps);
		    	stats();
	    	}
        });
	}
	
	/**
	 * Listen for changes in screen size
	 */
	private static void screenListener() {
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		        winWidth = newSceneWidth.doubleValue();
		        shapeConfig();
		        updateCanvas();
		    }
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
		        winHeight = newSceneHeight.doubleValue();
		        shapeConfig();
		        updateCanvas();
		    }
		});
	}
	
	/**
	 * Configure basic shapes
	 */
	private static void shapeConfig() {
		double factor1 = (winHeight * 0.6) / Params.world_height;
		double factor2 = (winWidth * 0.3) / Params.world_width;
		double scaleFactor = 0;
		if (factor1 < factor2) { scaleFactor = factor1; }
		else { scaleFactor = factor2; }
		circle.setRadius(scaleFactor / 2.0);
		square.getPoints().clear();
		square.getPoints().addAll(
				0.0, 0.0,
				scaleFactor, 0.0,
				scaleFactor, scaleFactor,
				0.0, scaleFactor);
		triangle.getPoints().clear();
		triangle.getPoints().addAll(
				scaleFactor / 2.0, 0.0,
				0.0, scaleFactor,
				scaleFactor, scaleFactor);
		diamond.getPoints().clear();
		diamond.getPoints().addAll(
				scaleFactor / 2.0, 0.0,
				scaleFactor, scaleFactor / 2.0,
				scaleFactor / 2.0, scaleFactor,
				0.0, scaleFactor / 2.0);
		star.getPoints().clear();
		star.getPoints().addAll(
				scaleFactor / 2.0, -(scaleFactor / 12.0),
				scaleFactor / 1.6, scaleFactor / 4.0,
				scaleFactor, scaleFactor / 4.0,
				scaleFactor / 1.33, scaleFactor / 2.0,
				scaleFactor / 1.2, scaleFactor / 1.2,
				scaleFactor / 2.0, scaleFactor / 1.6,
				scaleFactor / 6.0, scaleFactor / 1.2,
				scaleFactor / 4.0, scaleFactor / 2.0,
				0.0, scaleFactor / 4.0,
				scaleFactor / 2.67, scaleFactor / 4.0);
	}
	
	/**
	 * Configure seed pane
	 */
	private static void seedConfig() {
		
		/* Set seed columns */
	    ColumnConstraints seedCol1 = new ColumnConstraints();
	    seedCol1.setPercentWidth(60);
	    seedCol1.setHalignment(HPos.CENTER);
	    ColumnConstraints seedCol2 = new ColumnConstraints();
	    seedCol2.setPercentWidth(20);
	    seedCol2.setHalignment(HPos.CENTER);
	    seedPane.getColumnConstraints().addAll(seedCol1, seedCol2);

	    /* Set seed rows */
	    RowConstraints seedRow1 = new RowConstraints();
	    seedRow1.setPercentHeight(50);
	    seedRow1.setValignment(VPos.BOTTOM);
	    RowConstraints seedRow2 = new RowConstraints();
	    seedRow2.setPercentHeight(50);
	    seedRow2.setValignment(VPos.CENTER);
	    seedPane.getRowConstraints().addAll(seedRow1, seedRow2);
	    
	    /* Seed button listener */
	    seedButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
				try { Critter.setSeed(Long.parseLong(seed.getText())); }
				catch (NumberFormatException e1) { System.out.println("Not a number"); } 
		    }
	    });

		/* Add everything to pane */
	    seedPane.setAlignment(Pos.CENTER);
		seedPane.add(seedLabel, 0, 0);
		seedPane.add(seed, 0, 1);
		seedPane.add(seedButton, 1, 1);
	}
	
	private static ArrayList<String> listOfCritters() throws URISyntaxException, ClassNotFoundException {
		ArrayList<String> allcrits = new ArrayList<String>();
		File directory = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		String pkg = Main.class.getPackage().toString().split(" ")[1];
		directory = new File(directory.getAbsolutePath() + "/" + pkg);
		String [] lfile = directory.list();
		for (int i = 0;i < lfile.length; i++) {
			if((Class.forName(pkg + ".Critter").isAssignableFrom(Class.forName(pkg + "." + lfile[i].substring(0, lfile[i].length() - 6))))
					&& !(Modifier.isAbstract(Class.forName(pkg + "." + lfile[i].substring(0, lfile[i].length() - 6)).getModifiers()))) {
				allcrits.add(lfile[i].substring(0, lfile[i].length() - 6));
			}
		}
		return allcrits;
	}
	
	/**
	 * Update screen
	 */
	private static void updateCanvas(){
		worldClear();
		String a = "Crittersgetter";
		try {
			
			/* Check for all critter instances */
			List<Critter> chkpop = Critter.getInstances(a);
			
			for (Critter ls : chkpop) {
				
				/* Store old coordinates */
				int oldx = ls.getX();
				int oldy = ls.getY();
				
				/* Check critter shape */
				CritterShape b = ls.viewShape();
				
				/* Critter is circle */
				if (b == CritterShape.CIRCLE) {
					Circle circ = new Circle();
					circ.setRadius(circle.getRadius());
					circ.setFill(ls.viewFillColor());
					circ.setStroke(ls.viewOutlineColor());
					world.add(circ, oldx, oldy);
				}
				
				/* Critter is a polygon */
				else{
					Polygon poly = new Polygon();
					poly.setFill  (ls.viewFillColor());
					poly.setStroke(ls.viewOutlineColor());
					
					/* Possible critter shapes */
					if (b == CritterShape.SQUARE) { poly.getPoints().addAll(square.getPoints()); }
					if(b==CritterShape.DIAMOND){ poly.getPoints().addAll(diamond.getPoints()); }
					if(b==CritterShape.STAR){ poly.getPoints().addAll(star.getPoints()); }
					if(b== CritterShape.TRIANGLE){ poly.getPoints().addAll(triangle.getPoints()); }
					
					world.add(poly, oldx, oldy);
				}
				
				/* Run statistics when canvas is updated */
		    	stats.clear();
		    	Console console = new Console(stats);
		        PrintStream ps = new PrintStream(console);
		        System.setOut(ps);
		        System.setErr(ps);
		    	stats();
		    	
			}
		} catch (Exception e) { }
	}
	
	/**
	 * Statistics runner
	 */
	private static void stats() {
		try { Class.forName(myPackage + "." + critterList.getValue()).getMethod("runStats", List.class).invoke(critterList.getValue(), Critter.getInstances(critterList.getValue())); }
		catch (Exception e) { }
	}
}