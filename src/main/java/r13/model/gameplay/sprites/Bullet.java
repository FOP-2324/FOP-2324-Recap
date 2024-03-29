package r13.model.gameplay.sprites;

import javafx.geometry.Bounds;
import r13.model.gameplay.Direction;
import r13.model.gameplay.GameState;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

import static r13.controller.GameConstants.*;
import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A {@link Bullet} is a {@link Sprite} that can be fired by a {@link BattleShip} and can can hit other BattleShips.
 *
 * <ul>
 *   <li>A Bullet cannot damage itself, other Bullets, its owner or Friends of its owner.</li>
 *   <li>A Bullet can only damage the same BattleShip only once.</li>
 * </ul>
 */
public class Bullet extends Sprite {
    // --Variables-- //

    /**
     * The owner of the Bullet.
     */
    private final BattleShip owner;

    /**
     * The set of BattleShips that have been damaged by the Bullet.
     */
    private final Set<Sprite> hits = new HashSet<>();

    // --Constructors-- //

    /**
     * Creates a new {@link Bullet}.
     *
     * @param x         The initial x-coordinate of the Bullet.
     * @param y         The initial y-coordinate of the Bullet.
     * @param gameState The game state.
     * @param owner     The owner of the Bullet.
     * @param direction The direction the Bullet is travelling towards.
     */
    public Bullet(final double x, final double y, final GameState gameState, final BattleShip owner, final Direction direction) {
        super(x, y, BULLET_WIDTH, BULLET_HEIGHT, Color.WHITE, BULLET_VELOCITY, 1, gameState);
        this.owner = owner;
        setDirection(direction);
    }

    // --Getters and Setters-- //

    /**
     * Gets the owner of the Bullet.
     *
     * @return The owner of the Bullet.
     * @see #owner
     */
    public BattleShip getOwner() {
        return owner;
    }

    /**
     * Gets the set of BattleShips that have been damaged by the Bullet.
     *
     * @return The set of BattleShips that have been damaged by the Bullet.
     * @see #hits
     */
    public Set<Sprite> getHits() {
        return hits;
    }

    @Override
    protected void onOutOfBounds(final Bounds newBounds) {
        // If the bullet reaches the edge of the game Board, remove it.
        if (!isDead()) die();
    }

    /**
     * Checks if the Bullet can damage the given Sprite.
     *
     * @param other The Sprite to check.
     * @return True if the Bullet can damage the given Sprite.
     */
    public boolean canHit(final BattleShip other) {
        return other.isAlive() && getOwner().isEnemy(other) && other.getBounds().intersects(getBounds()) && !getHits().contains(other);
    }

    /**
     * Hits the given BattleShip by damaging both the Bullet and the BattleShip. Also saves the BattleShip in the set of hits.
     *
     * @param other The BattleShip to hit.
     */
    public void hit(final BattleShip other) {
        other.damage();
        damage();
        getHits().add(other);
    }
}
