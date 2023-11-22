package r04;

import fopbot.Direction;

/**
 * A RobotWithOffspring that is extended by RobotWithOffspring2 with a different function to calculate the current viewing direction
 */
public class RobotWithOffspring2 extends RobotWithOffspring {
    private int directionAccu;

    /**
     * Constructs and initializes a RobotWithOffspring2 with the super constructor at the specified {@code (numberOfColumnsOfWorld/2, numberOfRowsOfWorld/2)} location, viewing direction
     * and number of coins
     *
     * @param numberOfColumnsOfWorld the number of columns in the current world. Used to set the X coordinate of the newly constructed RobotWithOffspring
     * @param numberOfRowsOfWorld the number of rows in the current world. Used to set the Y coordinate of the newly constructed RobotWithOffspring
     * @param direction the viewing direction of the newly constructed RobotWithOffspring
     * @param numberOfCoins the number of coins of the newly constructed RobotWithOffspring
     */
    public RobotWithOffspring2(int numberOfColumnsOfWorld, int numberOfRowsOfWorld, Direction direction, int numberOfCoins) {
        super(numberOfColumnsOfWorld, numberOfRowsOfWorld, direction, numberOfCoins);
    }

    /**
     * Initializes a robot offspring with the same location as the RobotWithOffspring2 and other values for the direction
     * and number of coins.
     * Sets {@code directionAccu} to the value of {@code direction.ordinal()}
     *
     * @param direction the viewing direction of the robot offspring
     * @param numberOfCoins the number of coins of the robot offspring
     */
    public void initOffspring(Direction direction, int numberOfCoins) {
        super.initOffspring(direction, numberOfCoins);
        directionAccu = direction.ordinal();
    }

    /**
     * Returns the current viewing direction of robot offspring using {@code directionAccu}
     * and using modular arithmetic to get the viewing direction
     *
     * @return the current viewing direction of the robot offspring
     */
    private Direction getDirectionFromAccu() {
        int numDirection;
        if(directionAccu >= 0) {
            numDirection = directionAccu%4;
        } else {
            numDirection = (4-((-(directionAccu))%4))%4;
        }
        return Direction.values()[numDirection];
    }

    /**
     * Returns the current viewing direction of the robot offspring using {@code getDirectionFromAccu()}
     *
     * @return the current viewing direction of the robot offspring
     */
    public Direction getDirectionOfOffspring() {
        return getDirectionFromAccu();
    }

    /**
     * Adds/subtracts the value {@code valueToBeAdded} to the current value of {@code directionAccu} of robot offspring
     *
     * @param valueToBeAdded the value that will be added/subtracted to the current value of {@code directionAccu} of robot offspring
     */
    public void addToDirectionOfOffspring(int valueToBeAdded) {
        if(offspringIsInitialized()) {
            directionAccu += valueToBeAdded;
            while(getDirectionFromAccu() != offspring.getDirection()) {
                offspring.turnLeft();
            }
        }
    }

}
