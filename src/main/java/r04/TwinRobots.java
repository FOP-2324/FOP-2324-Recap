package r04;

import fopbot.Direction;

/**0
 * A class that is being used to compare the classes RobotWithOffspring and RobotWithOffspring2
 */
public class TwinRobots {
    private final RobotWithOffspring[] robots;

    /**
     * Constructs and initializes TwinRobots with two RobotWithOffspring and set one to RobotWithOffspring and one to RobotWithOffspring2
     * and initialize two robots offspring with {@code initOffspring}
     *
     * @param numberOfColumnsOfWorld the number of columns in the current world
     * @param numberOfRowsOfWorld the number of rows in the current world
     */
    public TwinRobots(int numberOfColumnsOfWorld, int numberOfRowsOfWorld) {
        robots = new RobotWithOffspring[2];
        robots[0] = new RobotWithOffspring(numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.RIGHT, 0);
        robots[1] = new RobotWithOffspring2(numberOfColumnsOfWorld, numberOfRowsOfWorld, Direction.UP, 0);
        robots[0].initOffspring(Direction.LEFT, 0);
        robots[1].initOffspring(Direction.LEFT, 0);
    }

    /**
     * Returns the robot at the given index {@code index}
     *
     * @param index the given index for the robot that should be returned
     * @return the robot at the given index {@code index}
     */
    public RobotWithOffspring getRobotByIndex(int index) {
        return robots[index];
    }

    /**
     * Use the function {@code addToDirectionOfOffspring} with a given value {@code valueToBeAdded}
     * and use this function on both robot objects inside robot[]
     *
     * @param valueToBeAdded the value which is used by the {@code addToDirectionOfOffspring} function
     */
    public void addToDirectionOfBothOffsprings(int valueToBeAdded) {
        robots[0].addToDirectionOfOffspring(valueToBeAdded);
        robots[1].addToDirectionOfOffspring(valueToBeAdded);
    }
}
