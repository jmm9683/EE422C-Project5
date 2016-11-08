# EE 422C - Project5 Critters 2

This project provides a GUI for simulating the life of various critters in Java.

##Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

###Prerequisites

* [Java SE](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [JavaFX](http://docs.oracle.com/javafx/)

###Installing

####Linux

1. Navigate to where you want the project
2. Clone repository: `git clone https://github.com/atchisonbrent/EE422C-Project5.git`
3. Move to source folder: `cd EE422C-Project5/src/`
4. Compile: `javac assignment5/*.java`
5. Run: `java assignment5.Main`

##Class List

* Main.java
  * Sub-Classes:
    * AnimateTimer
      * timesteps : int
      * AnimateTimer(int)
      * handle(long) : void
    * Console
      * output : TextArea
      * Console(TextArea)
      * write(int) : void
    * TextField
      * replaceText(int, int, String) : void
      * replaceSelection(String) : void
    * ChangeListener
      * changed(ObservableValue<? extends Number>, Number, Number) : void
    * EventHandler
      * handle(ActionEvent) : void
  * Fields:
    * myPackage : String
    * scene : GridPane
    * world : GridPane
    * controls : VBox
    * screenSize : Dimension
    * critterList : ComboBox<String>
    * timeline : Timeline
    * makeAmtLabel : Label
    * makeMultLabel : Label
    * makeAmtPane : GridPane
    * makeAmtSlider : Slider
    * makeAmtMult : Slider
    * makeAmtVal : Label
    * makeButton : Button
    * stepLabel : Label
    * stepMultLabel : Label
    * stepPane : GridPane
    * stepSlider : Slider
    * stepMult : Slider
    * stepVal : Label
    * stepButton : Button
    * aniLabel : Label
    * aniPane : GridPane
    * aniSlider : Slider
    * aniVal : Label
    * aniButton : Button
    * anibool : boolean
    * aTimer : AnimateTimer
    * statsContainer : VBox
    * statsButton : Button
    * stats : TextArea
    * ps : PrintStream
    * seedLabel : Label
    * seedPane : GridPane
    * seedButton : Button
    * seed : TextField
    * exitBox : VBox
    * exitButton : Button
    * circle : Circle
    * square : Polygon
    * triangle : Polygon
    * diamond : Polygon
    * star : Polygon
    * winWidth : double
    * winHeight : double
    * chkcombo : boolean
  * Methods:
    * main(String[]) : void
    * start(Stage) : void
    * runInit() : void
    * sceneConfig() : void
    * worldConfig() : void
    * worldClear() : void
    * comboConfig() : void
    * makeAmtConfig() : void
    * stepConfig() : void
    * aniConfig() : void
    * statsConfig() : void
    * exitConfig() : void
    * handlerInit() : void
    * togglePane() : void
    * screenListener() : void
    * shapeConfig() : void
    * seedConfig() : void
    * listOfCritters() : void
    * updateCanvas() : void
    * stats() : void
* Input.java
  * Fields:
    * myPackage : String
  * Methods:
    * takeInput(Scanner) : void
    * printError(String[]) : void
    * printInvalid(String[]) : void
* Critter.java
  * Fields:
    * myPackage : String
    * CritterShape { CIRCLE, SQUARE, TRIANGLE, DIAMOND, STAR } : enum
    * population : List<Critter>
    * babies : List<Critter>
    * a : int[]
    * rand : Random
    * energy : int
    * x_coord : int
    * y_coord : int
    * hasMoved : boolean
    * fighting : boolean
  * Methods:
    * viewColor() : Color
    * viewOutlineColor() : Color
    * viewFillColor() : Color
    * viewShape() : CritterShape
    * getX() : int
    * getY() : int
    * look(int, boolean) : String
    * getRandomInt(int) : int
    * setSeed(long) : void
    * toString() : String
    * getEnergy() : int
    * walk(int) : void
    * run(int) : void
    * move(int, int) : void
    * reproduce(Critter, int) : void
    * doTimeStep() : void
    * fight(String) : boolean
    * makeCritter(String) : void
    * getInstances(String) : List<Critter>
    * runStats(List<Critter>) : void
    * clearWorld() : void
    * worldTimeStep() : void
    * displayWorld() : void
* Critter1.java
  * Fields:
    * dir : int
  * Methods:
    * Critter1()
    * toString() : String
    * fight(String) : boolean
    * doTimeStep() : void
    * viewShape() : CritterShape
    * viewOutlineColor() : Color
* Critter2.java
  * Fields:
    * dir : int
  * Methods:
    * Critter2()
    * toString() : String
    * fight(String) : boolean
    * doTimeStep() : void
    * viewShape() : CritterShape
    * viewFillColor() : Color
    * viewOutlineColor() : Color
* Craig.java
  * Fields:
    * GENE_TOTAL : int
    * genes : int[]
    * dir : int
  * Methods:
    * toString() : String
    * Craig()
    * fight(String) : boolean
    * doTimeStep() : void
    * runStats(List<Critter>) : void
    * viewShape()
    * viewOutlineColor()
* Algae.java
  * Methods:
    * toString() : String
    * fight(String) : boolean
    * doTimeStep : void
    * viewShape()
    * viewColor()
* AlgaephobicCritter.java
  * Methods:
    * toString() : String
    * fight(String) : boolean
    * doTimeStep() : void
    * runStats2(List<Critter>) : String
    * viewShape() : CritterShape
    * viewOutlineColor() : Color
* Params.java
  * Fields:
    * world_width : int
    * world_height : int
    * walk_energy_cost : int
    * run_energy_cost : int
    * rest_energy_cost : int
    * min_reproduce_energy : int
    * refresh_algae_count : int
    * photosynthesis_energy_amount : int
    * start_energy : int
    * look_energy_cost : int
* InvalidCritterException.java
  * Fields:
    * offending_class : String
  * Methods:
    * InvalidCritterException(String)
    * toString() : String

##Critter Object

* Fields:
  * population : ArrayList<Critter>()
  * babies : List<Critter>
  * energy : int
  * x_coord : int
  * y_coord : int
  * hasMoved : boolean
  * fighting : boolean
* Methods:
  * viewColor()
  * viewOutlineColor()
  * viewFillColor()
  * viewShape()
  * getX()
  * getY()
  * getRandomInt(int) : int
  * setSeed(long) : void
  * toString() : String
  * getEnergy() : int
  * walk(int) : void
  * run(int) : void
  * move(int, int) : void
  * reproduce(Critter, int) : void
  * doTimeStep() : void
  * fight(String) : boolean
  * makeCritter(String) : void
  * getInstances(String) : List<Critter>
  * runStats(List<Critter>) : void

##Authors

* Brent Atchison
* Dhruv Mathew
