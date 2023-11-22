package r04;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;

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
        sandboxTests();
    }

    private static void sandboxTests() {
        World.setSize(5, 7);
        World.setVisible(true);

        /*RobotWithOffspring robot = new RobotWithOffspring(5, 7, Direction.LEFT, 12);

        RobotWithOffspring2 robot2 = new RobotWithOffspring2(5, 7, Direction.LEFT, 22);

        // int directionAccu = robot2.directionAccu;
        // robot2.directionAccu = 2;

        // Direction d = robot2.getDirectionFromAccu();

        Robot r = null;
        // r.getX();

        RobotWithOffspring ro = null;
        // ro.getX();

        RobotWithOffspring2 ro2 = null;
        // ro2.getX();

        int number = 3 * 1_000_000_000;
        System.out.println(number);*/
    }
}