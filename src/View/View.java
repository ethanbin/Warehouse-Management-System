package View;


import Controller.DataController;
import Controller.DetailsController;
import Controller.MainController;
import Controller.SceneController;
import Exceptions.ErrorHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.awt.*;

/**
 * View class which extends Application and defines its start method,
 * which defines how the application is launched.
 */
public class View extends Application {
    /**
     * Immutable title of the application.
     */
    public final static String VIEW_TITLE = "Warehouse Management System";
    private final String FXMLPath = "LoginScreen.fxml";

    /**
     * Overridden method start. Sets the starting scene, loads each FXML and adds it to
     * the SceneController, and shows the application's stage.
     * @param primaryStage primaryStage
     * @throws Exception
     * @see SceneController
     * @see Stage
     * @see Scene
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource(FXMLPath));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        primaryStage.setTitle(VIEW_TITLE);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath));

        // set scene's default size to be two-thirds of screens resolution
        Scene scene = new Scene(loader.load(),screenSize.getWidth()*3/4, screenSize.getHeight()*3/4);

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(screenSize.getHeight()*3/4);
        primaryStage.setMinWidth(screenSize.getWidth()*3/4);
        primaryStage.show();

        //Create a instance of our SceneController class for use in our controller class (will handle scene change).
        SceneController.setScene(scene);
        SceneController.setScene(scene);
        SceneController.addScreen("Home", FXMLLoader.load(getClass().getResource("ProductPage.fxml")));
        System.out.println("Home was added");
        SceneController.addScreen("Details", FXMLLoader.load(getClass().getResource("DetailsPage.fxml")));
        System.out.println("Details was added");
        SceneController.addScreen("Login", FXMLLoader.load(getClass().getResource("LoginScreen.fxml")));
        System.out.println("Login was added");

        Font.loadFont(getClass().getResourceAsStream("font.ttf"), 16);

        primaryStage.setOnCloseRequest( event ->
        {
            System.out.println("Closing other windows");
            MainController.getInstance().getDetailsController().exit();
        });
    }

//    @Override
//    public void stop() throws Exception {
//        //MainController.getInstance().stopLowStockScheduler();
//        //MainController.getInstance().removeTrayIcon();
//        //DataController.getInstance().close();
//        super.stop();
//    }
}
