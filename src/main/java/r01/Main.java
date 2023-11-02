package r01;

import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.World;

public class Main {
    public static void main(String[] args) {
        /*System.out.println("Hello world!");
        for (int i = 0; i < 6; i++) {
            System.out.println(i);
        }
        int i = 0;
        while(i < 6) {
            System.out.println(i);
            i += 1;
        }

        int a = 42;
        // a = a + 5;
        a += 5;
        int b = ++a;
        System.out.println(b);
        System.out.println(a);
        */
        World.setSize(8, 8);
        World.setDelay(1000);
        World.setVisible(true);

        Robot robby = new Robot(0, 0);
        //System.out.println(robby);
        //robby.setPrintTrace(true);
        robby.move();
        //System.out.println(robby);
        Robot robby2;
        robby2 = new Robot(7, 7, RobotFamily.SQUARE_ORANGE);
        if(robby2.isFrontClear()) {
            robby2.move();
        } else {
            robby2.turnLeft();
            robby2.move();
        }

        moveRobotByAmount(robby, 5);

    }

    public static void moveRobotByAmount(Robot robot, int steps) {
        for (int i = 0; i < steps; i++) {
            robot.move();
        }
    }
}
