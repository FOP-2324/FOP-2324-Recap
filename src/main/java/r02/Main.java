package r02;

import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.Direction;
import fopbot.World;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import static fopbot.Direction.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        /* final int SIZE = 5;
        World.setSize(SIZE, SIZE);
        World.setDelay(300);
        World.setVisible(true);
        MyRobot robby = doSomething(MyFamily.FOPBOT);
        doSomethingElse(robby);*/
        Direction direction = LEFT;
        final int SIZE = 8;
        World.setSize(SIZE, SIZE/2);
        World.setVisible(true);
        MyRobot robby = createRobotWithFamily(MyFamily.GREEN);
        for(MyFamily family: MyFamily.values()) {
            System.out.println(family.fam);
        }
        changeRobotColors(robby);
        showContinueBreakReturn();
    }

    public static void showContinueBreakReturn() {
        System.out.println("Vor der For-Schleife");
        for(int i = 0; i < 10; i++) {
            if(i==4) {
                return;
            }
            else if(i == 5) {
                break;
            }
                System.out.println(i);

        }
        System.out.println("Nach der For-Schleife");
    }
    public static MyRobot createRobotWithFamily(MyFamily family) {
        return new MyRobot(0, 3, family);
    }

    public static void changeRobotColors(MyRobot robot) {
        MyFamily family;
        while(true) {
            family = MyFamily.values()[getRandom().nextInt(7)];
            System.out.println(family);
            robot.setRobotFamily(family.fam);
            if(robot.getRobotFamily() == RobotFamily.TRIANGLE_BLUE) {
                break;
            }
        }
    }

    public static Random getRandom() {
        return ThreadLocalRandom.current();
    }
}