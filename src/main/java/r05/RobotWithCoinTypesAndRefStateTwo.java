package r05;

import fopbot.Direction;

/**
 * A RobotWithCoinTypes that creates a refRobot to see how the robot moved compared to the last taken reference state
 */
public class RobotWithCoinTypesAndRefStateTwo extends RobotWithCoinTypes implements RobotWithReferenceState {
    private final ReferenceRobot refRobot;

    /**
     * Constructs and initializes a RobotWithCoinTypesAndRefStateTwo at the specified {@code (x,y} location, viewing direction,
     * number of silver coins, number of brass coins and number of copper coins. And initializes a refRobot
     * and sets the current values to the associated values of the refRobot.
     *
     * @param x the X coordinate of the newly constructed RobotWithCoinTypesAndRefStateTwo
     * @param y the Y coordinate of the newly constructed RobotWithCoinTypesAndRefStateTwo
     * @param direction the viewing direction of the newly constructed RobotWithCoinTypesAndRefStateTwo
     * @param numberOfSilverCoins the number of silver coins of the newly constructed RobotWithCoinTypesAndRefStateTwo
     * @param numberOfBrassCoins the number of brass coins of the newly constructed RobotWithCoinTypesAndRefStateTwo
     * @param numberOfCopperCoins the number of copper coins of the newly constructed RobotWithCoinTypesAndRefStateTwo
     */
    public RobotWithCoinTypesAndRefStateTwo(int x, int y, Direction direction, int numberOfSilverCoins, int numberOfBrassCoins, int numberOfCopperCoins) {
        super(x, y, direction, numberOfSilverCoins, numberOfBrassCoins, numberOfCopperCoins);
        refRobot = new ReferenceRobot(x, y, direction, (numberOfSilverCoins+numberOfBrassCoins+numberOfCopperCoins));
    }

    /**
     * Sets the current position of the RobotWithCoinTypesAndRefStateTwo to the associated reference values of the refRobot.
     */
    public void setCurrentStateAsReferenceState() {
        refRobot.setRefX(getX());
        refRobot.setRefY(getY());
        refRobot.setRefDirection(getDirection());
        refRobot.setRefNumberOfCoins(getNumberOfCoins());
    }

    /**
     * Returns the difference between the current X coordinate and the reference X coordinate of refRobot.
     *
     * @return the difference between the current X coordinate and the reference X coordinate of refRobot
     */
    public int getDiffX() {
        return getX() - refRobot.getRefX();
    }

    /**
     * Returns the difference between the current Y coordinate and the reference Y coordinate of refRobot.
     *
     * @return the difference between the current Y coordinate and the reference Y coordinate of refRobot
     */
    public int getDiffY() {
        return getY() - refRobot.getRefY();
    }

    /**
     * Returns in which direction the RobotWithCoinTypesAndRefStateTwo has turned compared to the reference viewing direction of refRobot.
     *
     * @return in which direction the RobotWithCoinTypesAndRefStateTwo has turned compared to the reference viewing direction of refRobot
     */
    public Direction getDiffDirection() {
        return switch((refRobot.getRefDirection().ordinal() - getDirection().ordinal()) % 4) {
            case 0 -> Direction.UP;
            case 1 -> Direction.LEFT;
            case 2 -> Direction.DOWN;
            default -> Direction.RIGHT;
        };
    }

    /**
     * Returns the difference between the current number of coins and the reference number of coins of refRobot.
     *
     * @return the difference between the current number of coins and the reference number of coins of refRobot
     */
    public int getDiffNumberOfCoins() {
        return getNumberOfCoins() - refRobot.getRefNumberOfCoins();
    }
}
