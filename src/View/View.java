package View;


import Controller.LoginController;
import Controller.ProductPageController;
import Controller.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.awt.*;

public class View extends Application {
    private final String viewTitle = "Warehouse Management System";
    private final String FXMLPath = "LoginScreen.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource(FXMLPath));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        primaryStage.setTitle(viewTitle);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLPath));

        // set scene's default size to be two-thirds of screens resolution
        Scene scene = new Scene(loader.load(),screenSize.getWidth()*3/4, screenSize.getHeight()*3/4);

        primaryStage.setScene(scene);
        primaryStage.setMinHeight(screenSize.getHeight()*3/4);
        primaryStage.setMinWidth(screenSize.getWidth()*3/4);
        primaryStage.show();

        //Create a instance of our SceneController class for use in our controller class (will handle scene change).
        SceneController sc = new SceneController(primaryStage.getScene());
        sc.addScreen("Home", FXMLLoader.load(getClass().getResource("ProductPage.fxml")));
        System.out.println("Home was added");
        sc.addScreen("Details", FXMLLoader.load(getClass().getResource("DetailsPage.fxml")));
        System.out.println("Details was added");


        Font.loadFont(getClass().getResourceAsStream("font.ttf"), 16);
        LoginController controller = loader.getController();
        controller.init(sc);

    }
}
