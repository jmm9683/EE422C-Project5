/* 
 * CRITTERS Main.java
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

package assignment5; // cannot be in default package
import java.util.*;
import java.io.*;


/*
 * Usage: java assignment5.Main <input file> test
 * Input file is optional. If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default package.
    //private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    /* Gets the package name. Assumes that Critter and its subclasses are all in the same package. */
    static { myPackage = Critter.class.getPackage().toString().split(" ")[1]; }

    /**
     * Main method.
     * @param args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     * @throws InvalidCritterException 
     */
    public static void main(String[] args) {
    	
    	/* Check for arguments */
        if (args.length != 0) {
            try {
            	inputFile = args[0];
            	kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
            	System.out.println("USAGE: java Main OR java Main <input file> <test output>");
            	e.printStackTrace();
            } catch (NullPointerException e) {
            	System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            
            if (args.length >= 2) {
            	
            	/* Second argument is "test" */
        		if (args[1].equals("test")) {
                    
                	/* Create a stream to hold the output */
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    
                    /* Save the old System.out */
                    old = System.out;
                    
                    /* Tell Java to use the special stream
                     * All console output will be redirected here now */
                    System.setOut(ps);
                }
            }
        }
        
        /* Use keyboard and console */
        else { kb = new Scanner(System.in); }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        Input.takeInput(kb);
        
        /* Write your code above */
        System.out.flush();

    }
}
