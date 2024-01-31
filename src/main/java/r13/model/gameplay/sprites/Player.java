package r13.model.gameplay.sprites;

import r13.controller.ApplicationSettings;
import r13.model.gameplay.Direction;
import r13.model.gameplay.GameState;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A {@link Player} is a {@link BattleShip} that can only move horizontally and shoots upwards.
 */
public class Player extends BattleShip {

    // --Variables-- //

    /**
     * The player's score.
     */
    private int score;

    /**
     * The Player name.
     */
    private @Nullable String name;

    /**
     * Whether the player is continuously shooting.
     */
    private boolean keepShooting = false;

    // --Constructors-- //

    /**
     * Creates a new {@link Player}.
     *
     * @param x         The x-coordinate of the player.
     * @param y         The y-coordinate of the player.
     * @param gameState The game state.
     */
    public Player(final double x, final double y, final double velocity, final GameState gameState) {
        super(x, y, velocity, Color.BLUE, 5, gameState);
        loadTexture("/r13/images/sprites/player.png");
    }

    // --Getters and Setters-- //

    /**
     * Gets the player's current {@link #score}.
     *
     * @return The player's current {@link #score}.
     * @see #score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's {@link #score} to the given value.
     *
     * @param score The player's new {@link #score}.
     * @see #score
     */
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * Gets the player's current {@link #name}.
     *
     * @return The player's current {@link #name}.
     * @see #name
     */
    public @Nullable String getName() {
        return name;
    }

    /**
     * Sets the player's {@link #name} to the given value.
     *
     * @param name The player's new {@link #name}.
     * @see #name
     */
    public void setName(final @Nullable String name) {
        this.name = name;
    }

    /**
     * Increments the player's {@link #score} by the given value.
     *
     * @param points The amount to increment the player's {@link #score} by.
     */
    public void addPoints(final int points) {
        score += points;
    }

    /**
     * Checks whether the player is continuously shooting.
     *
     * @return {@code true} if the player is continuously shooting.
     * @see #keepShooting
     */
    public boolean isKeepShooting() {
        return keepShooting;
    }

    /**
     * Sets the {@link #keepShooting} field to the given value.
     *
     * @param keepShooting whether the player should be continuously shooting.
     * @see #keepShooting
     */
    public void setKeepShooting(final boolean keepShooting) {
        this.keepShooting = keepShooting;
    }

    // --movement-- //

    @Override
    public void moveDown() {
        // Do nothing
    }

    @Override
    public void moveUp() {
        // Do nothing
    }

    // --Shooting-- //

    /**
     * Overloaded method from {@link BattleShip#shoot(Direction)} with the {@link Direction#UP} parameter.
     *
     * @see BattleShip#shoot(Direction)
     */
    public void shoot() {
        shoot(Direction.UP);
    }

    @Override
    public void update(final double elapsedTime) {
        super.update(elapsedTime);

       if (!ApplicationSettings.autoPlayProperty().get()) {
           if (isKeepShooting()) {
               shoot();
           } else {
               // check if there is an enemy in the way
               if (getGameState().getEnemies().stream().anyMatch(enemy -> Math.abs(enemy.getX() - getX()) < 1)) {
                   shoot();
               }

               // find the closest enemy using the euclidean distance
               final Enemy closestEnemy = getGameState().getEnemies().stream()
                       .min(Comparator.comparingDouble(enemy -> Math.sqrt(Math.pow(enemy.getX() - getX(), 2) + Math.pow(enemy.getY() - getY(), 2))))
                       .orElse(null);

               // move towards the closest enemy
               if (closestEnemy != null) {
                   if (closestEnemy.getX() > getX()) {
                       moveRight();
                   } else if (closestEnemy.getX() < getX()) {
                       moveLeft();
                   }
               }

               // check for dangerous bullets
               final var dangerousBullet = getGameState().getSprites().stream()
                       .filter(Bullet.class::isInstance)
                       .map(Bullet.class::cast)
                       .filter(bullet -> bullet.getDirection() == Direction.DOWN)
                       .filter(bullet -> bullet.getX() >= getX() - 10 && bullet.getX() + bullet.getWidth() <= getX() + getWidth() + 10)
                       .min(Comparator.comparingDouble(bullet -> Math.sqrt(Math.pow(bullet.getX() - getX(), 2) + Math.pow(bullet.getY() - getY(), 2))))
                       .orElse(null);

               // move away from the dangerous bullet
               if (dangerousBullet != null) {
                   System.out.println("dodging bullet");
                   if (dangerousBullet.getX() > getX()) {
                       moveLeft();
                   } else if (dangerousBullet.getX() < getX()) {
                       moveRight();
                   }
               }
           }
       }
    }
}
