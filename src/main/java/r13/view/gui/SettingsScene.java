package r13.view.gui;

import r13.controller.scene.menu.SettingsController;
import javafx.scene.control.TabPane;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * The {@link SettingsScene} is a {@link SubMenuScene} that displays the "Settings" menu.
 */
public class SettingsScene extends SubMenuScene<SettingsController, TabPane> {

    /**
     * Creates a new {@link SettingsScene}.
     */
    public SettingsScene() {
        super(new TabPane(), new SettingsController(), "Settings");
        init();
    }

    /**
     * Initialize the content of the scene.
     */
    private void init() {
        crash(); // TODO: H4 - remove if implemented
    }
}
