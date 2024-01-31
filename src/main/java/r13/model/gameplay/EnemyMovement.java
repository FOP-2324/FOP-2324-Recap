package r13.model.gameplay;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import r13.model.gameplay.sprites.Enemy;
import r13.shared.Utils;

import static r13.controller.GameConstants.*;
import static org.tudalgo.algoutils.student.Student.crash;

/**
 * The {@link EnemyMovement} class is responsible for moving the {@linkplain r13.model.gameplay.sprites.Enemy enemies} in a grid.
 */
public class EnemyMovement implements Updatable {

    // --Variables-- //

    /**
     * The current movement direction
     */
    private Direction direction;

    /**
     * The current movement speed
     */
    private double velocity = INITIAL_ENEMY_MOVEMENT_VELOCITY;

    /**
     * The Next y-coordinate to reach
     */
    private double yTarget = 0;

    /**
     * The current {@link GameState}
     */
    private final GameState gameState;

    // --Constructors-- //

    /**
     * Creates a new EnemyMovement.
     *
     * @param gameState The enemy controller.
     */
    public EnemyMovement(final GameState gameState) {
        this.gameState = gameState;
        nextRound();
    }

    // --Getters and Setters-- //

    /**
     * Gets the current {@link #velocity}.
     *
     * @return The current {@link #velocity}.
     * @see #velocity
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Sets the current {@link #velocity} to the given value.
     *
     * @param velocity The new {@link #velocity}.
     * @see #velocity
     */
    public void setVelocity(final double velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the current {@link #direction}.
     *
     * @return The current {@link #direction}.
     * @see #direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the current {@link #direction} to the given value.
     *
     * @param direction The new {@link #direction}.
     * @see #direction
     */
    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    /**
     * Checks whether the bottom was reached.
     *
     * @return {@code true} if the bottom was reached, {@code false} otherwise.
     */
    public boolean bottomWasReached() {
        return getEnemyBounds().getMaxY() >= ORIGINAL_GAME_BOUNDS.getHeight();
    }

    /**
     * Gets the enemy controller.
     *
     * @return The enemy controller.
     */
    public GameState getGameState() {
        return gameState;
    }

    // --Utility Methods-- //

    /**
     * Creates a BoundingBox around all alive enemies.
     *
     * @return The BoundingBox.
     */
    public Bounds getEnemyBounds() {
        final var minX = gameState.getAliveEnemies().stream().mapToDouble(Enemy::getX).min().orElse(0);
        final var minY = gameState.getAliveEnemies().stream().mapToDouble(Enemy::getY).min().orElse(0);
        final var maxX = gameState.getAliveEnemies().stream().mapToDouble(e -> e.getX() + e.getWidth()).max().orElse(0);
        final var maxY = gameState.getAliveEnemies().stream().mapToDouble(e -> e.getY() + e.getHeight()).max().orElse(0);

        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Checks whether the target Position of the current movement iteration is reached.
     *
     * @param enemyBounds The BoundingBox of all alive enemies.
     * @return {@code true} if the target Position of the current movement iteration is reached, {@code false} otherwise.
     */
    public boolean targetReached(final Bounds enemyBounds) {
        return switch (direction) {
            case NONE -> false;
            case UP -> enemyBounds.getMaxY() <= yTarget;
            case DOWN -> enemyBounds.getMinY() >= yTarget;
            case LEFT -> enemyBounds.getMinX() <= 0;
            case RIGHT -> enemyBounds.getMaxX() >= ORIGINAL_GAME_BOUNDS.getWidth();
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    // --Movement-- //

    @Override
    public void update(final double elapsedTime) {
        if (bottomWasReached()) {
            return;
        }

        final var enemyBounds = getEnemyBounds();
        Bounds newBounds = Utils.getNextPosition(enemyBounds, getVelocity(), direction, elapsedTime);
        if (targetReached(newBounds)) {
            newBounds = Utils.clamp(newBounds);
            nextMovement(enemyBounds);
        }

        updatePositions(
                newBounds.getMinX() - enemyBounds.getMinX(),
                newBounds.getMinY() - enemyBounds.getMinY()
        );
    }

    /**
     * Updates the positions of all alive enemies.
     *
     * @param deltaX The deltaX.
     * @param deltaY The deltaY.
     */
    public void updatePositions(final double deltaX, final double deltaY) {
        getGameState().getEnemies().forEach(enemy -> {
            enemy.setX(enemy.getX() + deltaX);
            enemy.setY(enemy.getY() + deltaY);
        });
    }

    /**
     * Starts the next movement iteration.
     *
     * @param enemyBounds The BoundingBox of all alive enemies.
     */
    public void nextMovement(final Bounds enemyBounds) {
        if (direction.isHorizontal()) {
            direction = Direction.DOWN;
            yTarget += VERTICAL_ENEMY_MOVE_DISTANCE;
        } else {
            direction = enemyBounds.getMaxX() >= ORIGINAL_GAME_BOUNDS.getWidth() ? Direction.LEFT : Direction.RIGHT;
        }

        setVelocity(getVelocity() + ENEMY_MOVEMENT_SPEED_INCREASE);
    }

    /**
     * Prepares the next round of enemies.
     * Uses {@link r13.controller.GameConstants#INITIAL_ENEMY_MOVEMENT_DIRECTION} and {@link r13.controller.GameConstants#INITIAL_ENEMY_MOVEMENT_VELOCITY} to set the initial values.
     */
    public void nextRound() {
        direction = INITIAL_ENEMY_MOVEMENT_DIRECTION;
        yTarget = HUD_HEIGHT;
    }
}
