/* 
 * CRITTERS GUI Critter.java
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

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */

public abstract class Critter {
	
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	/* Gets the package name.  Assumes that Critter and its subclasses are all in the same package. */
	static { myPackage = Critter.class.getPackage().toString().split(" ")[1]; }
	
	protected String look(int direction, boolean steps) {return "";}
	
	/* Returns a random integer */
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) { return rand.nextInt(max); }
	
	/* Get random seed */
	public static void setSeed(long new_seed) { rand = new java.util.Random(new_seed); }
	
	/* A one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	/* Returns energy */
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	private boolean hasMoved;
	private boolean fighting;
	
	/* 
	 * Walks in given direction
	 */
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		if (!this.hasMoved) { move(direction, 1); }
		this.hasMoved = true;
	}
	
	/* 
	 * Runs in given direction
	 */
	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;
		if (!this.hasMoved) { move(direction, 2); }
		this.hasMoved = true;
	}
	
	/**
	 * Moves in given direction
	 * @param direction to move in
	 * @param steps to take
	 */
	private void move(int direction, int steps) {
		
		int x = this.x_coord;
		int y = this.y_coord;
		
		/* Is fighting, need to check if space is occupied */
		if (fighting) {
			switch (direction) {
				case 0: 	x += steps;
							break;
				case 1:    	x += steps;
							y -= steps;
							break;
				case 2: 	y -= steps;
							break;
				case 3: 	x -= steps;
							y -= steps;
							break;
				case 4: 	x -= steps;
							break;
				case 5: 	x -= steps;
							y += steps;
							break;
				case 6: 	y += steps;
							break;
				case 7: 	x += steps;
							y += steps;
							break;
				default:	break;
			}
			
			/* Check new coordinates for occupation */
			for (Critter c : population) {
				if (x == c.x_coord && y == c.y_coord) {
					return;
				}
			}
		}
		
		/* Update coordinates */
		switch (direction) {
			case 0: 	this.x_coord += steps;
						break;
			case 1:    	this.x_coord += steps;
						this.y_coord -= steps;
						break;
			case 2: 	this.y_coord -= steps;
						break;
			case 3: 	this.x_coord -= steps;
						this.y_coord -= steps;
						break;
			case 4: 	this.x_coord -= steps;
						break;
			case 5: 	this.x_coord -= steps;
						this.y_coord += steps;
						break;
			case 6: 	this.y_coord += steps;
						break;
			case 7: 	this.x_coord += steps;
						this.y_coord += steps;
						break;
			default:	break;
		}
		
		/* Check world wrapping */
		if(this.x_coord < 0){ this.x_coord += Params.world_width; }
		if(this.x_coord >= Params.world_width){ this.x_coord -= Params.world_width; }
		if(this.y_coord < 0){ this.y_coord += Params.world_height; }
		if(this.y_coord >= Params.world_height){ this.y_coord -= Params.world_height; }
		
	}
	
	/**
	 * Reproduces critter and sets new direction
	 * @param offspring is new child
	 * @param direction offspring will take
	 */
	protected final void reproduce(Critter offspring, int direction) {
		
		/* Ensure enough energy */
		if (this.energy < Params.min_reproduce_energy) { return; }
		
		/* Offspring has half energy of parent */
		offspring.energy = this.energy / 2;
		
		/* Parent gets half energy, rounding up */
		this.energy = this.energy / 2 + this.energy % 2;
		
		/* Set proper coordinates */
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		
		/* Move offspring in proper direction */
		offspring.walk(direction);
		
		/* Add offspring to list of babies */
		babies.add(offspring);
		
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critterClass) throws InvalidCritterException {

		try{
			
			/* Create new critter */
			Critter newcrit = (Critter) Class.forName(myPackage+"." + critterClass).newInstance();
			Critter.population.add(newcrit);
			
			/* Initialize new critter values */
			newcrit.hasMoved = false;
			newcrit.fighting = false;
			newcrit.energy = Params.start_energy;
			newcrit.x_coord = Critter.getRandomInt(Params.world_width);
			newcrit.y_coord = Critter.getRandomInt(Params.world_height);
			
		}
		catch(InstantiationException | IllegalAccessException | ClassNotFoundException e){
			throw new InvalidCritterException(critterClass); }
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		
		/* Initialize new critter */
		Class<?> critter = null;
		
		/* Try getting critter class */
		try { critter = Class.forName(myPackage + "." + critter_class_name); }
		catch (ClassNotFoundException e) { throw new InvalidCritterException(critter_class_name); }
		
		/* List to return */
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		/* Add critters to result */
		for (Critter c : population) { if (critter.isInstance(c)) { result.add(c); } }
		
		return result;
		
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* The TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: You must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();
		babies.clear();
	}
	
	/**
	 * Move the world forward in time
	 */
	public static void worldTimeStep() {
		
		/* Every critter takes a step */
		for (Critter ls : population) { ls.doTimeStep(); }
		
		/* Check for overlapping critters */
		for (int i = 0; i < population.size(); i++) {
			
			/* First critter */
			Critter count = population.get(i);
			
			/* Critter is dead, will remove later */
			if (count.energy <= 0) { continue; }
			
			/* Second critter loop */
			for (int j = i + 1; j < population.size(); j++) {
				
				/* Critter is dead, will remove later */
				if (count.energy <= 0) { break; }
				
				/* Second critter */
				Critter chk = population.get(j);
				
				/* Second critter is dead */
				if (chk.energy <= 0){ continue; }
				
				/* Two critters occupy the same space */
				if (count.x_coord == chk.x_coord && count.y_coord == chk.y_coord) {
					
					/* Set fighting booleans */
					count.fighting = true;
					chk.fighting = true;
					
					/* See if critters want to fight */
					boolean a = count.fight(chk.toString());
					boolean b = chk.fight(count.toString());
					
					/* Reset fighting booleans */
					count.fighting = false;
					chk.fighting = false;
					
					/* Check if both critters are still alive */
					if (count.energy >= 0 && chk.energy >= 0) {
						
						/* Check if both critters still occupy the same space */
						if (count.x_coord == chk.x_coord && count.y_coord == chk.y_coord) {
							
							/* Roll strengths */
							int randcount = 0;
							if (a) { randcount = Critter.getRandomInt(count.energy + 1); }
							int randchk = 0;
							if (b) { randchk = Critter.getRandomInt(chk.energy + 1); }
							
							/* See who won */
							if (randchk > randcount) {
								chk.energy += count.energy / 2;
								count.energy = 0;
							}
							else {
								count.energy += chk.energy / 2;
								chk.energy = 0;
							}
						}
					}
				}
			}
		}
		for(int j=0;j<Params.refresh_algae_count;j++){
			try{
			makeCritter("Algae");}
			catch(InvalidCritterException e){}
		}
		
		/* Add all babies to population */
		for (Critter b : babies) { population.add(b); }
		babies.clear();
		int size = population.size();
		int index=0;
		/* Check for dead critters */
		for (int i = 0; i < size; i++) {
			
			/* Subtract rest energy cost */
			population.get(index).energy -= Params.rest_energy_cost;
			
			/* Remove dead critters */
			if(population.get(index).energy <= 0) { population.remove(population.get(index)); }
			else{
				index+=1;
			}
			
		}
		
		/* Reset movement */
		for (Critter c : population) { c.hasMoved = false; }
	}
	
	/**
	 * Display entire world
	 */
	public static void displayWorld() {
		printEdge();
		printMiddle();
		printEdge();
	}
	
	/**
	 * Prints top and bottom edges of world
	 */
	private static void printEdge() {
		System.out.print("+");
		for (int i = 0; i < Params.world_width; i++) { System.out.print("-"); }
		System.out.println("+");
	}
	
	/**
	 * Fills in middle of world
	 */
	private static void printMiddle() {
		boolean found = false;
		for (int i = 0; i < Params.world_height; i++) {
			System.out.print("|");
			for (int j = 0; j < Params.world_width; j++) {
				for (int k = 0; k < population.size(); k++) {
					if (population.get(k).x_coord == j && population.get(k).y_coord == i) {
						System.out.print(population.get(k).toString());
						found = true;
						break;
					}
				}
				if (!found) { System.out.print(" "); }
				else { found = false; }
			}
			System.out.println("|");
		}
	}
}
