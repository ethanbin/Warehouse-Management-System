package Controller;

import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Ethan on 5/1/2018.
 */
public class ProductPageController implements Initializable {

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> IDColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> priceColumn;

    @FXML
    private TableColumn<Product, String> countColumn;

    @FXML
    private MenuButton searchMenuButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private ImageView productImage;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextArea productDescriptionTextField;

    @FXML
    private Button newProductButton;

    @FXML
    private Button editProductButton;

    @FXML
    private Button detailsButton;

    @FXML
    private ToggleGroup Export;

    @FXML
    private MenuButton criteriaMenu;

    @FXML
    private Button nextButton;

    @FXML
    private VBox productTop;


    private int productsPerPage = 25;

    private int currentProductPage = 0;

    private Image image = new Image("file:res/img/next.png");


    // todo - add code to prevent going too far
    public void showNextProductsPage(){
        currentProductPage++;
        showCurrentProductsPage();
    }

    public void showPreviousProductsPage(){
        currentProductPage--;
        if (currentProductPage < 0)
            currentProductPage = 0;
        showCurrentProductsPage();
    }

    private void showCurrentProductsPage(){
        productsTable.getItems().setAll(DataController.getInstance().selectAllProductsInRange(
                currentProductPage * productsPerPage,productsPerPage));
    }

    @FXML
    protected void newProduct() {
        Stage stage = new Stage();
        Scene scene = new Scene(SceneController.getScreen("Details"));

        if(stage.getScene() != scene) {
            stage.setScene(scene);
        }

        stage.setTitle("New Product Page");
        stage.show();

        System.out.println("User has entered new product.");
    }

    // -todo: Make the Details Scene Reusable
    @FXML
    protected void editProduct()    {
//        Stage stage = new Stage();
//        Scene scene = new Scene(SceneController.getScreen("Details"));
//
//        if(stage.getScene() != scene) {
//            stage.setScene(scene);
//        }
//
//        stage.setTitle("Edit Product Page");
//        stage.show();
//
//        System.out.println("User has entered edit product.");
    }

    @FXML
    protected void detailsProduct()    {
//        Stage stage = new Stage();
//        Scene scene = new Scene(SceneController.getScreen("Details"));
//
//        if(stage.getScene() != scene) {
//            stage.setScene(scene);
//        }
//
//        stage.setTitle("Details Product Page");
//        stage.show();
//
//        System.out.println("User has entered product details.");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert productsTable != null : "fx:id=\"productsTable\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert IDColumn != null : "fx:id=\"IDColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert priceColumn != null : "fx:id=\"priceColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert countColumn != null : "fx:id=\"countColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert searchMenuButton != null : "fx:id=\"searchMenuButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert searchTextField != null : "fx:id=\"searchTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productImage != null : "fx:id=\"productImage\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productNameTextField != null : "fx:id=\"productNameTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productDescriptionTextField != null : "fx:id=\"productDescriptionTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert newProductButton != null : "fx:id=\"newProductButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert editProductButton != null : "fx:id=\"editProductButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert detailsButton != null : "fx:id=\"detailsButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert Export != null : "fx:id=\"Export\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert criteriaMenu != null : "fx:id=\"criteriaMenu\" was not injected: check your FXML file 'ProductPage.fxml'.";

        IDColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        //countColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("count"));

        showCurrentProductsPage();

        criteriaMenu.getItems().addAll(new MenuItem("Id"), new MenuItem("Name"), new MenuItem("Price"), new MenuItem("Count"));

        //sets and resizes a graphic for the "nextButton"
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        nextButton.setGraphic(imageView);

        MainController.getInstance().setProductPageController(this);

        //searchTextField.insertText(0,"test");

    }
}
