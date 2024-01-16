package r10;

import java.util.ArrayList;
import java.util.List;
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
        Integer[] intArray = new Integer[5];
        intArray[0] = 1;
        System.out.println(intArray.length);
        intArray[0] = null;
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        //System.out.println(intList.size());
        intList.remove(0);
        System.out.println(intArray.length);
        //System.out.println(intList.size());
    }
        /*SkipList<Integer> list = new SkipList<>(Integer::compareTo, 5,
                () -> ThreadLocalRandom.current().nextBoolean());
        list.add(1);
        list.add(2);
        list.add(3);
    }*/
}