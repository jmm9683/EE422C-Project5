/* 
 * CRITTERS Critter4.java
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
 * Critter4 is a type of critter that only moves westwards
 * It always choose to fight unless it is fighting Critter1
 * it reproduces when it has more than a 150 energy

 */

/*
 * Example critter
 */
public class Critter4 extends Critter {
	
	@Override
	public String toString() { return "4"; }
	
	
	private int dir;
	
	public Critter4() {
		
		dir = 3+ Critter.getRandomInt(3);
	}
	/** fight method
     * @param String not_used 
     * @return boolean which indicates how critter will handle conflict
     */
	public boolean fight(String not_used) { 
		if(not_used.equals("1")){
			return false ;
		}
		return true; }
	/** do TimeStep method
     *  
     */
	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 150) {
			Critter4 child = new Critter4();
			
			reproduce(child, 3+Critter.getRandomInt(3));
		}
		
		/* Pick a new direction based on our genes */
		dir =3+ Critter.getRandomInt(3);
	}
}