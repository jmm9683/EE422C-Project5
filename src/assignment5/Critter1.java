/* 
 * CRITTERS Critter1.java
 * EE422C Project 4 submission by
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
 * Critter1 is a type of critter that only moves in the north directions 
 * It  fights 50% of the time
 * it reproduces when it has more than a 200 energy

 */

public class Critter1 extends Critter {
	
	private int dir;
	
	public Critter1() { dir = Critter.getRandomInt(5); }
	/** toString method
     * @return character to be printed
     */
	@Override
	public String toString() { return "1"; }
	/** fight method
     * @param String not_used 
     * @return boolean which indicates how critter will handle conflict
     */
	public boolean fight(String not_used) { return Critter.getRandomInt(2) > 0; }
	/** do TimeStep method
     *  
     */
	@Override
	public void doTimeStep() {
		
		/* Take one step forward */
		walk(dir);
		
		/* Check if able to reproduce */
		if (getEnergy() > 200) {
			Critter1 child = new Critter1();
			reproduce(child, Critter.getRandomInt(5));
		}
		
		/* Update direction */
		dir = Critter.getRandomInt(5);
	}
}