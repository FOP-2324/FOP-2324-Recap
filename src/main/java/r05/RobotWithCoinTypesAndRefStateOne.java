package r05;

import fopbot.*;

/**
 * A RobotWithCoinTypes that uses reference values to see how the robot moved compared to the last taken reference state
 */
public class RobotWithCoinTypesAndRefStateOne extends RobotWithCoinTypes implements RobotWithReferenceState {
    /**
     * The reference X coordinate of this {@code RobotWithCoinTypesAndRefStateOne}.
     */
    private int refX;
    /**
     * The reference Y coordinate of this {@code RobotWithCoinTypesAndRefStateOne}.
     */
    private int refY;
    /**
     * The reference viewing direction of this {@code RobotWithCoinTypesAndRefStateOne}.
     */
    private Direction refDirection;
    /**
     * The reference number of coins of this {@code RobotWithCoinTypesAndRefStateOne}.
     */
    private int refNumberOfCoins;

    /**
     * Constructs and initializes a RobotWithCoinTypesAndRefStateOne at the specified {@code (x,y} location, viewing direction,
     * number of silver coins, number of brass coins and number of copper coins. And sets the reference values to the current values of the
     * associated parameters.
     *
     * @param x the X coordinate of the newly constructed RobotWithCoinTypesAndRefStateOne
     * @param y the Y coordinate of the newly constructed RobotWithCoinTypesAndRefStateOne
     * @param direction the viewing direction of the newly constructed RobotWithCoinTypesAndRefStateOne
     * @param numberOfSilverCoins the number of silver coins of the newly constructed RobotWithCoinTypesAndRefStateOne
     * @param numberOfBrassCoins the number of brass coins of the newly constructed RobotWithCoinTypesAndRefStateOne
     * @param numberOfCopperCoins the number of copper coins of the newly constructed RobotWithCoinTypesAndRefStateOne
     */
    public RobotWithCoinTypesAndRefStateOne(int x, int y, Direction direction, int numberOfSilverCoins, int numberOfBrassCoins, int numberOfCopperCoins) {
        super(x, y, direction, numberOfSilverCoins, numberOfBrassCoins, numberOfCopperCoins);
        refX = x;
        refY = y;
        refDirection = direction;
        refNumberOfCoins = numberOfSilverCoins+numberOfBrassCoins+numberOfCopperCoins;
    }

    /**
     * Sets the current position of the RobotWithCoinTypesAndRefStateOne to the associated reference values.
     */
    public void setCurrentStateAsReferenceState() {
        refX = getX();
        refY = getY();
        refDirection = getDirection();
        refNumberOfCoins = getNumberOfCoins();
    }

    /**
     * Returns the difference between the current X coordinate and the reference X coordinate.
     *
     * @return the difference between the current X coordinate and the reference X coordinate
     */
    public int getDiffX() {
        return getX() - refX;
    }

    /**
     * Returns the difference between the current Y coordinate and the reference Y coordinate.
     *
     * @return the difference between the current Y coordinate and the reference Y coordinate
     */
    public int getDiffY() {
        return getY() - refY;
    }

    /**
     * Returns in which direction the RobotWithCoinTypesAndRefStateOne has turned compared to the reference viewing direction.
     *
     * @return in which direction the RobotWithCoinTypesAndRefStateOne has turned compared to the reference viewing direction
     */
    public Direction getDiffDirection() {
        return switch((refDirection.ordinal() - getDirection().ordinal()) % 4) {
            case 0 -> Direction.UP;
            case 1 -> Direction.LEFT;
            case 2 -> Direction.DOWN;
            default -> Direction.RIGHT;
        };
    }

    /**
     * Returns the difference between the current number of coins and the reference number of coins.
     *
     * @return the difference between the current number of coins and the reference number of coins
     */
    public int getDiffNumberOfCoins() {
        return getNumberOfCoins() - refNumberOfCoins;
    }
}
