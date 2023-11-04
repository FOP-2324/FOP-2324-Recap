package r03;

import fopbot.Robot;
import fopbot.World;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * Main entry point in executing the program.
 */
public class Main {
    // Delay between each action in FopBot-World (world), for example:
    // Waits 1000ms between each .move() call
    public static final int DELAY = 1000;

    // Generates random int between 4 (inclusive) and 10 (exclusive)
    public static int getRandomWorldSize() {
        return 4 + ThreadLocalRandom.current().nextInt(6);
    }

    // Name of file for patterns
    public static final String FILENAME = "R03Pattern.txt";

    public static void main(String[] args) {
        // Get number of columns from method
        int numberOfColumns = getRandomWorldSize();

        // Get number of rows from method
        int numberOfRows = getRandomWorldSize();

        // Initialize World with specified number of columns and rows
        World.setSize(numberOfColumns, numberOfRows);

        // Set the internal delay of the world
        World.setDelay(DELAY);

        // Set the world visible
        World.setVisible(true);

        // Print out size of the world to the command line
        System.out.println("Size of world: " + numberOfColumns + "x" + numberOfRows);

        // Initialize new Main-object to call methods
        Main main = new Main();

        // Initialize a pattern provider for the .txt-file in resources
        PatternProvider patternProvider;
        try {
            patternProvider = new PatternProvider(FILENAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the pattern from the .txt file
        boolean[][] testPattern = patternProvider.getPattern();

        // Call initializeRobotsPattern
        Robot[] allRobots = main.initializeRobotsPattern(testPattern, numberOfColumns, numberOfRows);

        main.letRobotsMarch(allRobots);

        // TODO: H2.2 - Put your code here:

    }

    /**
     * Counts the number of robots in a pattern, given a specified world size.
     *
     * @param pattern           The pattern for the robots.
     * @param numberOfColumns   Number of columns in the world.
     * @param numberOfRows      Number of rows in the world.
     * @return                  Number of robots in the world.
     */
    public int countRobotsInPattern(boolean[][] pattern, int numberOfColumns, int numberOfRows) {
        return crash(); // TODO: H1.1 - remove if implemented
    }

    /**
     * Initialize allRobots array for given pattern and world size.
     *
     * @param pattern           The pattern for the robots.
     * @param numberOfColumns   Number of columns in world.
     * @param numberOfRows      Number of rows in world.
     * @return                  Correctly initialized allRobots array.
     */
    public Robot[] initializeRobotsPattern(boolean[][] pattern, int numberOfColumns, int numberOfRows) {
        return crash(); // TODO: H1.2 - remove if implemented
    }

    /**
     * Returns how many of the components of the given robot-array are null.
     *
     * @param allRobots   The Robot-array.
     * @return            True, if array contains robot.
     */
    public int numberOfNullRobots(Robot[] allRobots) {
        return crash(); // TODO: H3.1 - remove if implemented
    }

    /**
     * Creates an array containing three (pseudo-) random int values from 0 (inclusive) to given parameter (exclusive).
     *
     * @param bound   The upper bound for the int values.
     * @return        The array.
     */
    public int[] generateThreeDistinctRandomIndices(int bound) {
        return crash(); // TODO: H3.2 - remove if implemented
    }

    /**
     * Sorts the given 3 valued array from lowest to highest.
     *
     * @param array   The array to be sorted.
     */
    public void sortArray(int[] array) {
        crash(); // TODO: H3.3 - remove if implemented
    }

    /**
     * Swaps three robots in given robot array.
     * Robot at index i will later be at index j.
     * Robot at index j will later be at index k.
     * Robot at index k will later be at index i.
     *
     * @param indices       Array containing indices i, j and k.
     * @param allRobots     Array containing the robots.
     */
    public void swapRobots(int[] indices, Robot[] allRobots) {
        crash(); // TODO: H3.4 - remove if implemented
    }

    /**
     * Reduces the given robot array by the set amount and only keeps non-null components.
     *
     * @param robots    The array to be reduced.
     * @param reduceBy  The number of indices that are reduced.
     * @return          The reduced array.
     */
    public Robot[] reduceRobotArray(Robot[] robots, int reduceBy) {
        return crash(); // TODO: H3.5 - remove if implemented
    }

    /**
     * Lets all robots in the given array walk to the right while also putting down coins.
     * If robots leave the world they are set to null.
     * After the steps are made, if more than three robots exist, three of them change their index.
     * If 3 or more components of the array are null, the array is reduced by the amount of null components.
     *
     * @param allRobots   Array containing all the robots.
     */
    public void letRobotsMarch(Robot[] allRobots) {
        crash(); // TODO: H4 - remove if implemented
    }
}