package r04;

import fopbot.Direction;
import fopbot.Robot;

/**
 * A robot that is extended with additional functions to create an offspring robot of the first initialized robot and work with the offspring
 */
public class RobotWithOffspring extends Robot {
    private final int numberOfColumnsOfWorld;
    private final int numberOfRowsOfWorld;
    protected Robot offspring;

    /**
     * Constructs and initializes a RobotWithOffspring at the specified {@code (numberOfColumnsOfWorld/2, numberOfRowsOfWorld/2)} location, viewing direction
     * and number of coins
     *
     * @param numberOfColumnsOfWorld the number of rolumns in the current world. Used to set the X coordinate of the newly constructed RobotWithOffspring
     * @param numberOfRowsOfWorld the number of rows in the current world. Used to set the Y coordinate of the newly constructed RobotWithOffspring
     * @param direction the viewing direction of the newly constructed RobotWithOffspring
     * @param numberOfCoins the number of coins of the newly constructed RobotWithOffspring
     */
    public RobotWithOffspring(int numberOfColumnsOfWorld, int numberOfRowsOfWorld, Direction direction, int numberOfCoins) {
        super (numberOfColumnsOfWorld/2, numberOfRowsOfWorld/2, direction, numberOfCoins);
        this.numberOfColumnsOfWorld = numberOfColumnsOfWorld;
        this.numberOfRowsOfWorld = numberOfRowsOfWorld;
    }

    /**
     * Initializes a robot offspring with the same location as the RobotWithOffspring and other values for the direction
     * and number of coins
     *
     * @param direction the viewing direction of the robot offspring
     * @param numberOfCoins the number of coins of the robot offspring
     */
    public void initOffspring(Direction direction, int numberOfCoins) {
        offspring = new Robot(this.getX(), this.getY(), direction, numberOfCoins);
    }

    /**
     * Returns the X coordinate of the {@code robot offspring}
     *
     * @return the X coordinate of the {@code robot offspring}
     */
    public int getXOfOffspring() {
        return offspring.getX();
    }

    /**
     * Returns the Y coordinate of the robot offspring
     *
     * @return the Y coordinate of the robot offspring
     */
    public int getYOfOffspring() {
        return offspring.getY();
    }

    /**
     * Returns the current viewing direction of the robot offspring
     *
     * @return the current viewing direction of the robot offspring
     */
    public Direction getDirectionOfOffspring() {
        return offspring.getDirection();
    }

    /**
     * Returns the current number of coins the robot offspring has
     *
     * @return the current number of coins the robot offspring has
     */
    public int getNumberOfCoinsOfOffspring() {
        return offspring.getNumberOfCoins();
    }

    /**
     * Return {@code true} if the robot offspring is initialized in the {@code RobotWithOffspring}
     *
     * @return {@code true} if the robot offspring is initialized in the {@code RobotWithOffspring}
     */
    public boolean offspringIsInitialized() {
        return offspring != null;
    }

    /**
     * Adds/subtracts a value {@code valueToBeAdded} to the current X coordinate of robot offspring.
     * If the new X coordinate will be below 0 it will be set to 0.
     * If the new X coordinate will be above the {@code numberOfColumnsOfWorld-1} it will be set to {@code numberOfColumnsOfWorld-1}.
     *
     * @param valueToBeAdded the value that will be added/subtracted to the current X coordinate of robot offspring
     */
    public void addToXOfOffspring(int valueToBeAdded) {
        if(offspringIsInitialized()) {
            if(offspring.getX()+valueToBeAdded < 0) {
                offspring.setX(0);
            } else if(offspring.getX()+valueToBeAdded >= numberOfColumnsOfWorld) {
                offspring.setX(numberOfColumnsOfWorld-1);
            } else {
                offspring.setX(offspring.getX()+valueToBeAdded);
            }
        }
    }

    /**
     * Adds/subtracts a value {@code valueToBeAdded} to the current Y coordinate of robot offspring.
     * If the new Y coordinate will be below 0 it will be set to 0.
     * If the new Y coordinate will be above the {@code numberOfRowsOfWorld-1} it will be set to {@code numberOfRowsOfWorld-1}.
     *
     * @param valueToBeAdded the value that will be added/subtracted to the current Y coordinate of robot offspring
     */
    public void addToYOfOffspring(int valueToBeAdded) {
        if(offspringIsInitialized()) {
            if(offspring.getY()+valueToBeAdded < 0) {
                offspring.setY(0);
            } else if(offspring.getY()+valueToBeAdded >= numberOfRowsOfWorld) {
                offspring.setY(numberOfRowsOfWorld-1);
            } else {
                offspring.setY(offspring.getY()+valueToBeAdded);
            }
        }
    }

    /**
     * Adds/subtracts a value {@code valueToBeAdded} to the current {@code Direction.ordinal()} of robot offspring
     * and changes the viewing direction of this robot.
     *
     * @param valueToBeAdded the value that will be added/subtracted to the current {@code Direction.ordinal()} of robot offspring
     */
    public void addToDirectionOfOffspring(int valueToBeAdded) {
        int numDirection;
        if(offspringIsInitialized()) {
            if(offspring.getDirection().ordinal()+valueToBeAdded >= 0) {
                numDirection = (offspring.getDirection().ordinal()+valueToBeAdded)%4;
            } else {
                numDirection = (4-((-(offspring.getDirection().ordinal()+valueToBeAdded))%4))%4;
            }
            while(offspring.getDirection().ordinal() != numDirection) {
                offspring.turnLeft();
            }
        }
    }

    /**
     * Adds/subtracts a value {@code valueToBeAdded} to the current number of coins of robot offspring.
     * If the new number of coins will be below 0 it will be set to 0.
     *
     * @param valueToBeAdded the value that will be added/subtracted to the current number of coins of robot offspring
     */
    public void addToNumberOfCoinsOfOffspring(int valueToBeAdded) {
        if(offspringIsInitialized()) {
            if(offspring.getNumberOfCoins()+valueToBeAdded <= 0) {
                offspring.setNumberOfCoins(0);
            } else {
                offspring.setNumberOfCoins(offspring.getNumberOfCoins()+valueToBeAdded);
            }
        }
    }
}
