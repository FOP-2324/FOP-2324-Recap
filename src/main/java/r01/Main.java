package r01;

import fopbot.Robot;
import fopbot.World;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        /*
        for (int i = 0; i < 6; i++) {
            System.out.println(i);
        }
        */
        /*
        int a = 42;
        // a = a + 5;
        a += 5;
        System.out.println(a);
        */
        /*
        World.setSize(8, 8);
        World.setDelay(200);
        World.setVisible(true);

        Robot robby = new Robot(0, 0);
        robby.move();
        */
        /*
        moveRobotByAmount(robby, 5);
        */
    }

    public static void moveRobotByAmount(Robot robot, int steps) {
        for (int i = 0; i < steps; i++) {
            robot.move();
        }
    }
}
