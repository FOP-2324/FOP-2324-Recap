package r05;

/**
 * A coin collection that holds different amounts of silver, brass and copper coins.
 */
public class CoinCollection implements WithCoinTypes {
    /**
     * The number of silver coins in the coin collection.
     */
    private int numberOfSilverCoins;
    /**
     * The number of brass coins in the coin collection.
     */
    private int numberOfBrassCoins;
    /**
     * The number of copper coins in the coin collection.
     */
    private int numberOfCopperCoins;

    /**
     * Constructs and initializes a CoinCollection with the specified values number of silver coins,
     * number of brass coins and number of copper coins.
     *
     * @param numberOfSilverCoins the number of silver coins in the coin collection.
     * @param numberOfBrassCoins the number of brass coins in the coin collection.
     * @param numberOfCopperCoins the number of copper coins in the coin collection.
     */
    public CoinCollection(int numberOfSilverCoins, int numberOfBrassCoins, int numberOfCopperCoins) {
        this.numberOfSilverCoins = numberOfSilverCoins;
        this.numberOfBrassCoins = numberOfBrassCoins;
        this.numberOfCopperCoins = numberOfCopperCoins;
    }

    /**
     * Returns the current number of coins of a given coin type this coin collection has.
     *
     * @param coinType the coin type of which the current value has to be returned
     * @return the current number of coins of a given coin type this coin collection has
     */
    public int getNumberOfCoinsOfType(CoinType coinType) {
        return switch(coinType) {
            case SILVER -> numberOfSilverCoins;
            case BRASS -> numberOfBrassCoins;
            case COPPER -> numberOfCopperCoins;
        };
    }

    /**
     * Sets the current number of coins of a given coin type of the coin collection.
     *
     * @param coinType the coin type that has to be set
     * @param coins the current number of coins of a coin type this coin collection has that has to be set
     */
    public void setNumberOfCoinsOfType(CoinType coinType, int coins) {
        switch(coinType) {
            case SILVER -> numberOfSilverCoins = coins > 0 ? coins : 0;
            case BRASS -> numberOfBrassCoins = coins > 0 ? coins : 0;
            case COPPER -> numberOfCopperCoins = coins > 0 ? coins : 0;
        }
    }

    /**
     * Returns the current number of silver coins of the coin collection.
     *
     * @return the current number of silver coins of the coin collection
     */
    public int getNumberOfSilverCoins() {
        return numberOfSilverCoins;
    }

    /**
     * Returns the current number of brass coins of the coin collection.
     *
     * @return the current number of brass coins of the coin collection
     */
    public int getNumberOfBrassCoins() {
        return numberOfBrassCoins;
    }

    /**
     * Returns the current number of copper coins of the coin collection.
     *
     * @return the current number of copper coins of the coin collection
     */
    public int getNumberOfCopperCoins() {
        return numberOfCopperCoins;
    }

    /**
     * Adds one coin to the given coin type of coin collection.
     *
     * @param coinType the coin type of which the number of coins has to be increased by one
     */
    public void insertCoin(CoinType coinType) {
        switch(coinType) {
            case SILVER -> numberOfSilverCoins++;
            case BRASS -> numberOfBrassCoins++;
            case COPPER -> numberOfCopperCoins++;
        }
    }

    /**
     * Reduces the number of coins by one of a given coin type of coin collection.
     *
     * @param coinType the coin type of which the number of coins has to be decreased by one
     */
    public void removeCoin(CoinType coinType) {
        if(getNumberOfCoinsOfType(coinType) > 0) {
            setNumberOfCoinsOfType(coinType, getNumberOfCoinsOfType(coinType)-1);
        }
    }
}
