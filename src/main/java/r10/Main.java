package r10;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        SkipList<Integer> list = new SkipList<>(Integer::compareTo, 5,
                () -> ThreadLocalRandom.current().nextBoolean());
        list.add(1);
        list.add(2);
    }
}