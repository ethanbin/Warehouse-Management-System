package Controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.util.HashMap;

//MainController class to more easily switch between scenes.

/**
 * Static controller for the different scenes the application goes through.
 */
public class SceneController {
    private static SceneController instance = null;
    private static HashMap<String, Pane> screenMap = new HashMap<>();
    private static Scene main;

    // to prevent an instance of scene controller from being created since this should just be static methods
    private SceneController() {}

    /**
     * Set the scene.
     * @param main main scene to set
     */
    public static void setScene(Scene main)    {
        SceneController.main = main;
    }

    /**
     * Return a copy of the Pane with the given name.
     * @param name name of the Pane to get
     * @return a copy of the Pane with the given name
     * @see Pane
     */
    public static Pane getScreen(String name)  {
        return new Pane(screenMap.get(name));
    }

    /**
     * Add a Pane to SceneController's HashMap of Panes.
     * @param name name of the pane to use as a key
     * @param pane pane to add to SceneController
     */
    public static void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    /**
     * Remove a Pane with the given name from SceneController's HashMap of Panes.
     * @param name name of pane to remove
     */
    public static void removeScreen(String name){
        screenMap.remove(name);
    }

    /**
     * Activate the pane with the given name by setting it as the scene's root.
     * @param name name of the pane to activate
     */
    public static void activate(String name){
        main.setRoot( screenMap.get(name) );

    }
}