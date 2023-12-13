package r05;
import fopbot.Direction;

/**
 * An interface to add functions for a robot to create a reference state
 */
public interface RobotWithReferenceState {
    /**
     * Used to set the current position of a robot as the reference state.
     */
    public void setCurrentStateAsReferenceState();

    /**
     * Used to return the difference between the current X coordinate of a robot
     * and the X coordinate of the reference state.
     *
     * @return the difference between the current X coordinate and the X coordinate of the reference state
     */
    public int getDiffX();

    /**
     * Used to return the difference between the current Y coordinate of a robot
     * and the Y coordinate of the reference state.
     *
     * @return the difference between the current X coordinate and the X coordinate of the reference state
     */
    public int getDiffY();

    /**
     * Used to return the direction the robot has turned to compared to the reference state.
     *
     * @return the direction the robot has turned to compared to the reference state
     */
    public Direction getDiffDirection();

    /**
     * Used to return the difference between the current number of coins of a robot
     * and the number of coins of the reference state.
     *
     * @return the difference between the current number of coins and the number of coins of the reference state
     */
    public int getDiffNumberOfCoins();
}
