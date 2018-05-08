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

public class View extends Application {
    public final static String VIEW_TITLE = "Warehouse Management System";
    private final String FXMLPath = "LoginScreen.fxml";

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
        SceneController.getInstance().setScene(scene);
        SceneController sc = SceneController.getInstance();
        sc.setScene(scene);
        sc.addScreen("Home", FXMLLoader.load(getClass().getResource("ProductPage.fxml")));
        System.out.println("Home was added");
        sc.addScreen("Details", FXMLLoader.load(getClass().getResource("DetailsPage.fxml")));
        System.out.println("Details was added");
        sc.addScreen("Login", FXMLLoader.load(getClass().getResource("LoginScreen.fxml")));
        System.out.println("Login was added");

        Font.loadFont(getClass().getResourceAsStream("font.ttf"), 16);

        primaryStage.setOnCloseRequest( event ->
        {
            System.out.println("Closing other windows");
            MainController.getInstance().getDetailsController().exit();
        });
    }

    @Override
    public void stop() throws Exception {
        MainController.getInstance().stopLowStockScheduler();
        MainController.getInstance().removeTrayIcon();
        DataController.getInstance().close();
        super.stop();
    }
}
