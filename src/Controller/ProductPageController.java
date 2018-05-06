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
    private int productsPerPage = 5;
    private int currentProductPage = 0;
    private Image image = new Image("file:res/img/next.png");

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
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private CheckBox allStoresCheckBox;

    @FXML
    private CheckBox inStockOnlyCheckBox;

    @FXML
    private ComboBox<?> exportToComboBox;

    @FXML
    private Button generateButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ToggleGroup Export;

    @FXML
    private MenuButton criteriaMenu;

    @FXML
    private Button nextButton;

    @FXML
    private VBox productTop;

    @FXML
    public void showNextProductsPage(){
        currentProductPage++;
        if (currentProductPage * productsPerPage > DataController.getInstance().selectCountFromProducts())
            currentProductPage--;
        showCurrentProductsPage();
    }


    public void showPreviousProductsPage(){
        currentProductPage--;
        if (currentProductPage < 0)
            currentProductPage = 0;
        showCurrentProductsPage();
    }

    public void showCurrentProductsPage(){
        productsTable.getItems().setAll(DataController.getInstance().selectAllProductsInRange(
                currentProductPage * productsPerPage + 1,productsPerPage));
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
    void logout() {
        currentProductPage = 0;
        productsTable.getItems().clear();
        MainController.getInstance().logout();
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

    @FXML
    void exportReport() {

    }

    @FXML
    void generateReport() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert productTop != null : "fx:id=\"productTop\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productsTable != null : "fx:id=\"productsTable\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert IDColumn != null : "fx:id=\"IDColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert priceColumn != null : "fx:id=\"priceColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert countColumn != null : "fx:id=\"countColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert criteriaMenu != null : "fx:id=\"criteriaMenu\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert searchTextField != null : "fx:id=\"searchTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productImage != null : "fx:id=\"productImage\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productNameTextField != null : "fx:id=\"productNameTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productDescriptionTextField != null : "fx:id=\"productDescriptionTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert newProductButton != null : "fx:id=\"newProductButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert editProductButton != null : "fx:id=\"editProductButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert detailsButton != null : "fx:id=\"detailsButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert startDate != null : "fx:id=\"startDate\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert endDate != null : "fx:id=\"endDate\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert allStoresCheckBox != null : "fx:id=\"allStoresCheckBox\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert inStockOnlyCheckBox != null : "fx:id=\"inStockOnlyCheckBox\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert exportToComboBox != null : "fx:id=\"exportToComboBox\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert generateButton != null : "fx:id=\"generateButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert exportButton != null : "fx:id=\"exportButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert logoutButton != null : "fx:id=\"logoutButton\" was not injected: check your FXML file 'ProductPage.fxml'.";

        IDColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
        countColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("stock"));

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
