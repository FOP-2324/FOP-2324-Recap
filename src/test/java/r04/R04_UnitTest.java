package r04;

import fopbot.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class R04_UnitTest {
    /*
     * Beispiel für einen JUnit-Test
     *
     * assertEquals überprüft, ob der erste aktuale Parameterwert gleich dem zweiten
     * aktualen Parameterwert ist. Ist dies nicht der Fall, schlägt der JUnit-Test
     * fehl.
     */
    @Test
    void testRobotWithOffspringTwins() {
        World.setSize(10, 10);
        World.setDelay(0);

        // TwinRobots twins = new TwinRobots(10, 10);

        // assertEquals(twins.getRobotByIndex(0).getDirectionOfOffspring(),
        // twins.getRobotByIndex(1).getDirectionOfOffspring());
    }
}
