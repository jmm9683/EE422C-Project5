/* 
 * CRITTERS GUI Critter3.java
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

/**
 * Critter3 is a type of critter that only moves in the east directions 
 * It never fights choosing to walk instead
 * it reproduces when it has more than a 75 energy

 */
public class Critter3 extends Critter {
	/** toString method
     * @return character to be printed
     */
	@Override
	public String toString() { return "3"; }
	
	
	private int dir;
	
	public Critter3() {
		
		dir = Critter.getRandomInt(3);
		if (dir ==2){
			dir = 7;
		}
	}
	/** fight method
     * @param String not_used 
     * @return boolean which indicates how critter will handle conflict
     */
	
	public boolean fight(String not_used) { walk(dir);return false; }
	/** do TimeStep method
     *  
     */
	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		int childdir = Critter.getRandomInt(3);
		if (childdir ==2){
			childdir = 7;
		}
		
		if (getEnergy() > 75) {
			Critter3 child = new Critter3();
			reproduce(child, childdir);
		}
		
		/* Pick a new direction based on our genes */
		
		

		
		dir = Critter.getRandomInt(3);
		if (dir ==2){
			dir = 7;
		}
	}
	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}
}