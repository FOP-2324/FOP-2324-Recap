package r02;

import fopbot.RobotFamily;
public enum MyFamily {
    AQUA(RobotFamily.SQUARE_AQUA),
    WHITE(RobotFamily.SQUARE_WHITE),
    RED(RobotFamily.SQUARE_RED),
    YELLOW(RobotFamily.SQUARE_YELLOW),
    FOPBOT(RobotFamily.TRIANGLE_BLUE),
    GREEN(RobotFamily.SQUARE_GREEN),
    ORANGE(RobotFamily.SQUARE_ORANGE);

    public final RobotFamily fam;
    private MyFamily(RobotFamily fam) {
        this.fam = fam;
    }
}