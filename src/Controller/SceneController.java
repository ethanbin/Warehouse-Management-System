package Controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.util.HashMap;

//MainController class to more easily switch between scenes.
public class SceneController {
    private static SceneController instance = null;
    private static HashMap<String, Pane> screenMap = new HashMap<>();
    private static Scene main;

    protected SceneController() {}

    public static SceneController getInstance() {
        if(instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    public void setScene(Scene main)    {
        this.main = main;
    }

    public static Pane getScreen(String name)  {
        return screenMap.get(name);
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