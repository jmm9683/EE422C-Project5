/* 
 * CRITTERS Input.java
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
import java.util.*;

public class Input {

	private static String myPackage;
	static { myPackage = Critter.class.getPackage().toString().split(" ")[1]; }
	
	/**
	 * Take input and process commands accordingly
	 * 
	 * @param kb is input scanner
	 * @throws InvalidCritterException 
	 */
	public static void takeInput(Scanner kb) {
		
		while (true) {
			
			/* Print prompt */
			System.out.print("critters> ");
			
			/* Next line of input to string array */
			String[] input = kb.nextLine().split(" ");
			
			/* Ensure not empty input */
			if (input.length > 0) {
				
				/* Command is quit */
				if (input[0].equals("quit")) { 
					if (input.length == 1) { break; }
					else { printError(input); }
				}
				
				/* Command is show */
				else if (input[0].equals("show")) {
					if (input.length == 1) { Critter.displayWorld(); }
					else { printError(input); }
				}
				
				/* Command is step */
				else if (input[0].equals("step")) {
					
					/* Check for integer after step */
					if (input.length == 2) {
						
						/* Number of steps to take */
						int steps = 0;
						
						/* Try parsing an integer */
						try { steps = Integer.parseInt(input[1]); }
						catch (NumberFormatException e) { printError(input); }
						
						/* Take steps */
						for (int i = 0; i < steps; i++) { Critter.worldTimeStep(); }
						
					}
					
					/* Take one step */
					else if (input.length == 1) { Critter.worldTimeStep(); }
					
					/* Invalid input length */
					else { printError(input); }
				}
				
				/* Command is seed */
				else if (input[0].equals("seed")) {
					
					/* Set seed with long input */
					if (input.length == 2) {
						try { Critter.setSeed(Long.parseLong(input[1])); }
						catch (NumberFormatException e) { printError(input); } 
					}
					
					/* Invalid input length */
					else { printError(input); }
					
				}
				
				/* Command is make */
				else if (input[0].equals("make")) {
					
					/* Check for name after make */
					if (input.length > 1 && input.length < 4) {
						
						/* Number of critters to initialize */
						int count = 1;
						
						/* Check for specified number of critters to initialize */
						if (input.length == 3) {
							try { count = Integer.parseInt(input[2]); }
							catch (NumberFormatException e) { printError(input); continue; }
						}
						
						/* Initialize critters */
						for (int i = 0; i < count; i++) {
							try { Critter.makeCritter(input[1]); }
							catch (InvalidCritterException e) { printError(input); break; }
						}

					/* Invalid input length */
					} else { printError(input); }
					
				}
				
				/* Command is stats */
				else if (input[0].equals("stats")) {
					
					/* Check for name after stats */
					if (input.length != 2) { printError(input); continue; }
					
					/* The killer one-liner */
					try { Class.forName(myPackage + "." + input[1]).getMethod("runStats", List.class).invoke(input[1], Critter.getInstances(input[1])); }
					catch (Exception e) { printError(input); }
					
				/* Invalid command */
				} else { printInvalid(input); }

			/* No command entered */
			} else { printInvalid(input); }
			
		}
		
	}
	
	/**
	 * Exception occurs error
	 * 
	 * @param input
	 */
	public static void printError(String[] input) {
		System.out.print("error processing: ");
		for (int j = 0; j < input.length; j++) {
			System.out.print(input[j]);
			if (j != input.length - 1) { System.out.print(" "); }
		}
		System.out.println();
	}
	
	/**
	 * Command does not match
	 * 
	 * @param input
	 */
	public static void printInvalid(String[] input) {
		System.out.print("invalid command: ");
		for (int j = 0; j < input.length; j++) {
			System.out.print(input[j]);
			if (j != input.length - 1) { System.out.print(" "); }
		}
		System.out.println();
	}
	
}
