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

    // to stop an instance of scene controller from being created
    private SceneController() {}

    public static void setScene(Scene main)    {
        SceneController.main = main;
    }

    public static Pane getScreen(String name)  {
        return new Pane(screenMap.get(name));
    }

    public static void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    public static void removeScreen(String name){
        screenMap.remove(name);
    }

    public static void activate(String name){
        main.setRoot( screenMap.get(name) );

    }
}