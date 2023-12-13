package r05;

import fopbot.*;

/**
 * A robot that represents a robot with different type of coins on a graphical user interface.
 */
public class RobotWithCoinTypes extends Robot implements WithCoinTypes {
    /**
     * The number of silver coins that this RobotWithCoinTypes currently owns.
     */
    private int numberOfSilverCoins;
    /**
     * The number of brass coins that this RobotWithCoinTypes currently owns.
     */
    private int numberOfBrassCoins;
    /**
     * The number of copper coins that this RobotWithCoinTypes currently owns.
     */
    private int numberOfCopperCoins;

    /**
     * Constructs and initializes a RobotWithCoinTypes at the specified {@code (x,y)} location, viewing direction,
     * number of silver coins, number of brass coins and number of copper coins.
     *
     * @param x the X coordinate of the newly constructed RobotWithCoinTypes
     * @param y the Y coordinate of the newly constructed RobotWithCoinTypes
     * @param direction the viewing direction of the newly constructed RobotWithCoinTypes
     * @param numberOfSilverCoins the number of silver coins of the newly constructed RobotWithCoinTypes
     * @param numberOfBrassCoins the number of brass coins of the newly constructed RobotWithCoinTypes
     * @param numberOfCopperCoins the number of copper coins of the newly constructed RobotWithCoinTypes
     */
    public RobotWithCoinTypes(int x, int y, Direction direction, int numberOfSilverCoins, int numberOfBrassCoins, int numberOfCopperCoins) {
        super(x, y, direction, (numberOfSilverCoins+numberOfBrassCoins+numberOfCopperCoins));
        this.numberOfSilverCoins = numberOfSilverCoins;
        this.numberOfBrassCoins = numberOfBrassCoins;
        this.numberOfCopperCoins = numberOfCopperCoins;
    }

    /**
     * Returns the sum of the number of silver coins, the number of brass coins and the number of copper coins.
     *
     * @return the sum of the number of silver coins, the number of brass coins and the number of copper coins
     */
    private int getTotalNumberOfCoins() {
        return numberOfSilverCoins+numberOfBrassCoins+numberOfCopperCoins;
    }

    /**
     * Sets the current number of coins this robot has.
     *
     * @param coins The current number of coins this robot has
     */
    public void setNumberOfCoins(int coins) {
        if(coins < 0)
            super.setNumberOfCoins(coins);
        else {
            numberOfCopperCoins = coins;
            super.setNumberOfCoins(getTotalNumberOfCoins());
        }
    }

    /**
     * Sets the current number of coins of a coin type the current RobotWithCoinTypes has.
     * And sets the total amount of coins to the sum of all  three coin types.
     *
     * @param coinType the coin type that has to be set
     * @param coins The current number of coins of a coin type this RobotWithCoinTypes has that has to be set
     */
    public void setNumberOfCoinsOfType(CoinType coinType, int coins) {
        if(coins < 0)
            super.setNumberOfCoins(coins);
        else {
            switch (coinType) {
                case SILVER -> numberOfSilverCoins = coins;
                case BRASS -> numberOfBrassCoins = coins;
                case COPPER -> numberOfCopperCoins = coins;
            }
            super.setNumberOfCoins(getTotalNumberOfCoins());
        }
    }

    /**
     * Returns the current number of coins of a given coin type this RobotWithCoinTypes has.
     *
     * @param coinType the coin type of which the current value has to be returned.
     * @return the current number of coins of a given coin type this RobotWithCoinTypes has.
     */
    public int getNumberOfCoinsOfType(CoinType coinType) {
        return switch(coinType) {
            case SILVER -> numberOfSilverCoins;
            case BRASS -> numberOfBrassCoins;
            case COPPER -> numberOfCopperCoins;
        };
    }

    /**
     * Places a coin at its current position.
     * Places a copper coin first if it has any.
     * If it doesn't have copper coins it places a brass coin if it has any.
     * And if it doesn't have brass coins it places a silver coin if it has any.
     * Otherwise, no coin is being put down.
     */
    public void putCoin() {
        if(getNumberOfCoinsOfType(CoinType.COPPER) > 0) {
            numberOfCopperCoins--;
        } else if(getNumberOfCoinsOfType(CoinType.BRASS) > 0) {
            numberOfBrassCoins--;
        } else if(getNumberOfCoinsOfType(CoinType.SILVER) > 0) {
            numberOfSilverCoins--;
        }
        super.putCoin();
    }

    /**
     * Picks up a coin from the current position and interprets it as a copper coin.
     */
    public void pickCoin() {
        numberOfCopperCoins++;
        super.pickCoin();
    }
}
