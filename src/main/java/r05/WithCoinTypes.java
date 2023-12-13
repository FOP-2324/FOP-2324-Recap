package r05;

/**
 * An interface to add functions with coin types.
 */
public interface WithCoinTypes {
    /**
     * Used to set the number of a given coin type.
     *
     * @param coinType the given coin type of which the amount has to be set
     * @param coins the number of coins that will be set as the amount of the given coin type
     */
    public void setNumberOfCoinsOfType(CoinType coinType, int coins);

    /**
     * Used to get the number of coins of a given coin type.
     *
     * @param coinType the given coin type of which the amount of coins has to be returned.
     * @return the amount of coins of the given coin type
     */
    public int getNumberOfCoinsOfType(CoinType coinType);
}
