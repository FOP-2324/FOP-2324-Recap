package r13.view.gui;

import r13.controller.ApplicationSettings;
import r13.controller.GameConstants;
import r13.controller.scene.game.GameController;
import r13.model.gameplay.Updatable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;
import org.jetbrains.annotations.Nullable;

import static r13.controller.GameConstants.ORIGINAL_GAME_BOUNDS;
import static org.tudalgo.algoutils.student.Student.crash;


/**
 * A {@link GameBoard} is a {@link Canvas} on which the
 * {@link r13.model.gameplay.sprites.Sprite}s as well as the HUD are drawn.
 * It is part of the {@link GameScene} and is controlled by a
 * {@link GameController}.
 */
public class GameBoard extends Canvas implements Updatable {

    // --Variables-- //

    /**
     * The {@link GameScene} that contains this {@link GameBoard}.
     *
     * @see GameScene
     */
    private final GameScene gameScene;

    /**
     * The Background of this {@link GameBoard}.
     *
     * @see Image
     */
    private @Nullable Image backgroundImage;

    // --Constructors-- //

    /**
     * Creates a new GameBoard with the given parameters.
     *
     * @param width     The width of the {@link GameBoard}.
     * @param height    The height of the {@link GameBoard}.
     * @param gameScene The {@link GameScene} that contains this {@link GameBoard}.
     */
    public GameBoard(final double width, final double height, final GameScene gameScene) {
        super(width, height);
        this.gameScene = gameScene;
        if (ApplicationSettings.loadBackgroundProperty().get()) {
            try {
                backgroundImage = new Image("/r13/images/wallpapers/Galaxy3.jpg");
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    // --Utility Methods-- //

    /**
     * Calculates the Scale factor of the {@link GameBoard} based on the
     * {@link GameScene}'s width and height.
     *
     * @return The calculated Scale factor.
     */
    private double getScale() {
        return getWidth() / ORIGINAL_GAME_BOUNDS.getWidth();
    }

    /**
     * Gets the {@link GameController} that controls this {@link GameBoard}.
     *
     * @return The {@link GameController} that controls this {@link GameBoard}.
     * @see GameController
     */
    public GameController getGameController() {
        return gameScene.getController();
    }

    // --Methods-- //

    @Override
    public void update(final double elapsedTime) {
        final double scale = getScale();
        final var gc = getGraphicsContext2D();
        gc.setTransform(new Affine(new Scale(scale, scale)));

        drawBackground(gc);
        drawSprites(gc);
        drawHUD(gc);
        drawBorder(gc);
    }

    /**
     * Draws the background of this {@link GameBoard} to the given
     * {@link GraphicsContext} based on the
     * {@link GameConstants#ORIGINAL_GAME_BOUNDS}.
     * <br>
     * If the background is not set, the background is cleared with the
     * {@link GraphicsContext#clearRect(double, double, double, double)} method.
     *
     * @param gc The {@link GraphicsContext} to draw the background to.
     */
    private void drawBackground(final GraphicsContext gc) {
        crash(); // TODO: H2.3 - remove if implemented
    }

    /**
     * Draws the sprites of this {@link GameBoard} to the given
     * {@link GraphicsContext} using the {@link SpriteRenderer} class.
     * <br>
     * The sprites are drawn in the following order (from bottom to top):
     * <ol>
     * <li>Bullets</li>
     * <li>Enemies</li>
     * <li>Player</li>
     * <li>Others (currently none)</li>
     * </ol>
     *
     * @param gc The {@link GraphicsContext} to draw the sprites to.
     */
    private void drawSprites(final GraphicsContext gc) {
        crash(); // TODO: H2.3 - remove if implemented
    }

    /**
     * Draws the Heads-Up-Display (HUD) of this {@link GameBoard} to the given
     * {@link GraphicsContext}.
     * <br>
     * The HUD contains the following information:
     * <ul>
     * <li>Player Score (top left)</li>
     * <li>Remaining Lives (top right)</li>
     * </ul>
     * <br>
     *
     * @param gc The {@link GraphicsContext} to draw the HUD to.
     */
    private void drawHUD(final GraphicsContext gc) {
        crash(); // TODO: H2.3 - remove if implemented
    }

    /**
     * Draws the border of this {@link GameBoard} to the given
     * {@link GraphicsContext}.
     * <br>
     * The border is drawn using the
     * {@link GraphicsContext#strokeRect(double, double, double, double)} method.
     * <br>
     * The border has the color {@link GameConstants#BORDER_COLOR} and the width
     * {@link GameConstants#BORDER_WIDTH}.
     *
     * @param gc The {@link GraphicsContext} to draw the border to.
     */
    private static void drawBorder(final GraphicsContext gc) {
        crash(); // TODO: H2.3 - remove if implemented
    }
}
