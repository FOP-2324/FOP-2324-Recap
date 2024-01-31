package r13.controller.scene.game;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import r13.controller.ApplicationSettings;
import r13.controller.gamelogic.EnemyController;
import r13.controller.gamelogic.GameInputHandler;
import r13.controller.gamelogic.PlayerController;
import r13.controller.scene.SceneController;
import r13.controller.scene.SceneSwitcher;
import r13.model.HighscoreEntry;
import r13.model.gameplay.GameState;
import r13.model.gameplay.Updatable;
import r13.model.gameplay.sprites.*;
import r13.view.gui.GameBoard;
import r13.view.gui.GameScene;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A {@link SceneController} that controls the {@link GameScene}.
 * It is also responsible for the game loop and various other parts of the game logic.
 */

public class GameController extends SceneController implements Updatable {

    // --Variables-- //

    /**
     * The {@link GameState} that is our main access point to the model.
     */
    private GameState gameState = new GameState();

    /**
     * The {@link GameScene} that is controlled by this {@link GameController}.
     *
     * @see #gameScene
     */
    private final GameScene gameScene;

    /**
     * The last time the game loop was updated.
     */
    private double lastUpdate = 0;

    /**
     * Whether the game is paused.
     */
    private boolean paused = false;

    /**
     * The {@link PlayerController} that controls the {@link Player}.
     *
     * @see #playerController
     */
    private PlayerController playerController;
    /**
     * The {@link EnemyController} that controls the {@link Enemy}s.
     *
     * @see #enemyController
     */
    private EnemyController enemyController;

    /**
     * The {@link GameInputHandler} that handles the input of the player.
     *
     * @see #gameInputHandler
     */
    private GameInputHandler gameInputHandler;

    /**
     * A {@link AnimationTimer} that represents the game loop.
     */
    private final AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(final long now) {
            if (lastUpdate > 0 && !isPaused()) {
                final double elapsedTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                update(elapsedTime);
            } else {
                lastUpdate = now;
            }
        }
    };

    // --Constructors-- //

    /**
     * Creates a new {@link GameController}.
     *
     * @param gameScene The {@link GameScene} that is controlled by this {@link GameController}.
     */
    public GameController(final GameScene gameScene) {
        this.gameScene = gameScene;
        init();
    }

    // --Getters and Setters-- //

    /**
     * Gets the value of {@link #gameState} field.
     *
     * @return The value of {@link #gameState} field.
     * @see #gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Gets the value of {@link #gameScene} field.
     *
     * @return The value of {@link #gameScene} field.
     * @see #gameScene
     */
    public GameScene getGameScene() {
        return gameScene;
    }

    /**
     * Gets the value of {@link #playerController} field.
     *
     * @return The value of {@link #playerController} field.
     * @see #playerController
     */
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * Sets the value of {@link #playerController} field to the given value.
     *
     * @param playerController The new {@link #playerController}.
     * @see #playerController
     */
    public void setPlayerController(final PlayerController playerController) {
        this.playerController = playerController;
    }

    /**
     * Gets the value of {@link #enemyController} field.
     *
     * @return The value of {@link #enemyController} field.
     * @see #enemyController
     */
    public EnemyController getEnemyController() {
        return enemyController;
    }

    /**
     * Sets the value of {@link #enemyController} field to the given value.
     *
     * @param enemyController The new {@link #enemyController}.
     * @see #enemyController
     */
    public void setEnemyController(final EnemyController enemyController) {
        this.enemyController = enemyController;
    }

    /**
     * Gets the value of {@link #gameInputHandler} field.
     *
     * @return The value of {@link #gameInputHandler} field.
     * @see #gameInputHandler
     */
    public GameInputHandler getGameInputHandler() {
        return gameInputHandler;
    }

    /**
     * Sets the value of {@link #gameInputHandler} field to the given value.
     *
     * @param gameInputHandler The new {@link #gameInputHandler}.
     * @see #gameInputHandler
     */
    public void setGameInputHandler(final GameInputHandler gameInputHandler) {
        this.gameInputHandler = gameInputHandler;
    }

    /**
     * Gets the value of the {@link #gameLoop} field.
     *
     * @return The value of the {@link #gameLoop} field.
     * @see #gameLoop
     */
    public AnimationTimer getGameLoop() {
        return gameLoop;
    }

    // --Utility Methods-- //

    /**
     * Retrieves the {@link Player} from the {@link #playerController}.
     *
     * @return The {@link Player} of the {@link #playerController}.
     */
    public Player getPlayer() {
        return getPlayerController().getPlayer();
    }

    /**
     * Retrieves the {@link GameBoard} from the {@link #gameScene}.
     *
     * @return The {@link GameBoard} of the {@link #gameScene}.
     */
    public GameBoard getGameBoard() {
        return getGameScene().getGameBoard();
    }

    /**
     * Checks whether the game is Paused.
     *
     * @return {code true} if the game is paused, {code false} otherwise.
     * @see #paused
     */
    public boolean isPaused() {
        return paused;
    }

    @Override
    public String getTitle() {
        return "Space Invaders";
    }

    @Override
    public void initStage(final Stage stage) {
        super.initStage(stage);

        // Full Screen
        stage.setFullScreen(ApplicationSettings.fullscreenProperty().get());
    }

    /**
     * Initializes the {@link #gameScene}.
     */
    private void init() {
        // reset GameState
        gameState = new GameState();
        
        // Keyboard input handler
        setGameInputHandler(new GameInputHandler(getGameScene()));

        // Player
        setPlayerController(new PlayerController(this));

        // Enemies
        setEnemyController(new EnemyController(this));

        // register keybindings for the game scene
        handleKeyboardInputs();

        // start the game loop
        gameLoop.start();
        
        // unpause the game if necessary
        if (isPaused()) resume();
    }

    /**
     * Changes {@link #paused} to {@code true}.
     */
    public void pause() {
        paused = true;
    }

    /**
     * Changes {@link #paused} to {@code false}.
     */
    public void resume() {
        paused = false;
    }

    /**
     * Prepares the next level if the current level is finished.
     */
    public void refillEnemiesIfNecessary() {
        if (getEnemyController() != null && getEnemyController().isDefeated()) {
            getEnemyController().nextLevel();
        }
    }

    /**
     * Handles what happens when the {@linkplain Player player} is Defeated.
     */
    private void lose() {
        pause();
        final TextInputDialog dialog = new TextInputDialog("<CoolPlayerName>");
        dialog.setTitle("Game Over");
        dialog.setHeaderText("You Lost!\n Your Score: " + getPlayer().getScore());
        dialog.setContentText("Please enter your name to add it to the leaderboard:");

        final Optional<String> playerName = dialog.showAndWait();
        playerName.ifPresent((name) -> {
            getPlayer().setName(name);
            ApplicationSettings.getHighscores().add(
                    new HighscoreEntry(
                            getPlayer().getName(),
                            new Date().toString(),
                            getPlayer().getScore()
                    )
            );
        });

        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Continue?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            reset();
            resume();
            //do stuff
        } else {
            try {
                SceneSwitcher.loadScene(SceneSwitcher.SceneType.MAIN_MENU, getStage());
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Resets the sprites and starts a new game.
     */
    public void reset() {
        getGameState().getSprites().clear();
        init();
    }

    /**
     * Handles the keyboard inputs.
     */
    private void handleKeyboardInputs() {
        getGameInputHandler().addOnKeyReleased(k -> {
            // ecape
            switch (k.getCode()) {
                case ESCAPE -> {
                    pause();
                    final Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Give Up?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                        lose();
                    } else {
                        resume();
                    }
                }

                // F11
                case F11 -> getStage().setFullScreen(!getStage().isFullScreen());
            }
        });
    }

    @Override
    public void update(final double elapsedTime) {
        Platform.runLater(() -> {
            // Add new Sprites
            getGameState().getSprites().addAll(getGameState().getToAdd());
            getGameState().getToAdd().clear();

            // Movement
            updateOthers(elapsedTime);

            // Hit detection
            doCollisions();

            final var killed = getGameState().getSprites().stream().filter(Sprite::isDead).collect(Collectors.toList());
            // check if the player is isDefeated
            updatePoints(killed);

            // check loose condition
            if (killed.contains(getPlayer()) || getGameState().getEnemyMovement().bottomWasReached()) {
                lose();
            }

            killed.forEach(getGameState().getSprites()::remove);

            refillEnemiesIfNecessary();
        });
    }

    /**
     * Updates the {@link Sprite}s and the other Controllers.
     */
    private void updateOthers(final double elapsedTime) {
        // update the Enemy Movement
        getGameState().getEnemyMovement().update(elapsedTime);
        getGameState().getSprites()
            .forEach(s -> s.update(elapsedTime));
        getGameBoard().update(elapsedTime);
    }

    /**
     * Calculate the collision between the sprites and damages the collided sprites.
     */
    private void doCollisions() {
        getGameState().getSprites().stream().filter(Bullet.class::isInstance).map(Bullet.class::cast).forEach(
                b -> getGameState().getSprites().stream()
                        .filter(BattleShip.class::isInstance)
                        .map(BattleShip.class::cast)
                        .filter(b::canHit)
                        .forEach(b::hit)
        );
    }

    /**
     * Updates the points of the {@linkplain Player player}.
     *
     * @param damaged The damaged sprites.
     */
    public void updatePoints(final List<Sprite> damaged) {
        for (final Sprite sprite : damaged) {
            if (sprite instanceof Enemy e && e.isDead()) {
                getPlayer().addPoints(e.getPointsWorth());
            }
        }
    }
}
