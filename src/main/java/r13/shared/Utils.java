package r13.shared;

import javafx.geometry.BoundingBox;
import r13.model.gameplay.Direction;
import javafx.geometry.Bounds;

import static org.tudalgo.algoutils.student.Student.crash;
import static r13.controller.GameConstants.ORIGINAL_GAME_BOUNDS;

/**
 * A {@link Utils} class containing utility methods.
 */
public class Utils {

    /**
     * Returns the closest position for the given {@link Bounds} that is within the game bounds.
     *
     * @param bounds The bounds to be clamped.
     * @return the clamped coordinate.
     * @see <a href="https://en.wikipedia.org/wiki/Clamping_(graphics)">Clamping_(graphics)</a>
     * @see r13.controller.GameConstants
     */
    public static Bounds clamp(final Bounds bounds) {
        return new BoundingBox(
                Math.max(0, Math.min(ORIGINAL_GAME_BOUNDS.getWidth() - bounds.getWidth(), bounds.getMinX())),
                Math.max(0, Math.min(ORIGINAL_GAME_BOUNDS.getHeight() - bounds.getHeight(), bounds.getMinY())),
                bounds.getWidth(),
                bounds.getHeight()
        );
    }

    /**
     * Returns the Moved Bounding Box for the given {@link Bounds}, {@link Direction}, velocity and time.
     *
     * @param bounds      The bounds to be moved.
     * @param velocity    The velocity of the movement.
     * @param direction   The direction of the movement.
     * @param elapsedTime The time elapsed since the last movement.
     * @return the moved bounds
     */
    public static Bounds getNextPosition(final Bounds bounds, final double velocity, final Direction direction, final double elapsedTime) {
        return new BoundingBox(
                bounds.getMinX() + direction.getX() * velocity * elapsedTime,
                bounds.getMinY() + direction.getY() * velocity * elapsedTime,
                bounds.getWidth(),
                bounds.getHeight()
        );
    }
}
