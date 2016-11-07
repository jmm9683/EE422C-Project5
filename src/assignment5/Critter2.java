/* 
 * CRITTERS GUI Critter2.java 
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

import assignment5.Critter.CritterShape;

/**
 * Critter1 is a type of critter that only moves in the west directions 
 * It  fights 66% of the time
 * it reproduces when it has more than a 100 energy

 */

public class Critter2 extends Critter {
	
	private int dir;
	
	public Critter2() { dir = Critter.getRandomInt(4) + 3; }
	/** toString method
     * @return character to be printed
     */
	@Override
	public String toString() { return "2"; }
	/** fight method
     * @param String not_used 
     * @return boolean which indicates how critter will handle conflict
     */
	public boolean fight(String not_used) {int a = Critter.getRandomInt(3);String b = look(dir,true); return a > 0; }
	/** do TimeStep method
     *  
     */
	@Override
	public void doTimeStep() {
		
		/* Take two steps forward */
		run(dir);
		
		/* Check if able to reproduce */
		if (getEnergy() > 100) {
			Critter2 child = new Critter2();
			reproduce(child, Critter.getRandomInt(4) + 3);
		}
		
		/* Update direction */
		dir = Critter.getRandomInt(4) + 3;
	}
	@Override

	public CritterShape viewShape() { return CritterShape.STAR;}
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.BLANCHEDALMOND; }
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.CYAN; }
}