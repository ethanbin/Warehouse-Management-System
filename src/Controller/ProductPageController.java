package Controller;

import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import javax.swing.*;
import java.net.URL;
import java.security.Key;
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
    private Button searchButton;

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
    private Button prevButton;

    @FXML
    private ComboBox resultButton;

    @FXML
    private VBox productTop;

    @FXML
    public void showNextProductsPage(){
        currentProductPage++;
        if (currentProductPage * productsPerPage > DataController.getInstance().selectCountFromProducts())
            currentProductPage--;
        showCurrentProductsPage();
    }

    @FXML
    public void showPrevProductsPage(){
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
        openProductPage("New Product Page");
    }

    @FXML
    protected void editProduct()    {
        openProductPage("Edit Product Page");
    }

    @FXML
    protected void detailsProduct()    {
        openProductPage("Details Product Page");
    }

    private void openProductPage(String title){
        Stage stage = new Stage();
        Scene scene = new Scene(SceneController.getScreen("Details"));

        if(stage.getScene() != scene) {
            stage.setScene(scene);
        }

        stage.setTitle(title);
        stage.show();

        System.out.printf("User has entered %s.%n", title);
    }

    @FXML
    void logout() {
        currentProductPage = 0;
        productsTable.getItems().clear();
        MainController.getInstance().logout();
    }


    @FXML
    protected void searchProducts() {

    }

    @FXML
    protected void exportReport() {

    }

    @FXML
    protected void generateReport() {

    }

    @FXML
    void setSelectedProductInTable(MouseEvent event) {
        Product selectedProduct = new Product(productsTable.getSelectionModel().getSelectedItem());
        productNameTextField.setText(selectedProduct.getName());
        productDescriptionTextField.setText(selectedProduct.getDescription());
        MainController.getInstance().setSelectedProduct(selectedProduct);
    }

    @FXML
    public void nextButtonEntered()    {
        nextButton.setOpacity(1);
    }

    @FXML
    public void nextButtonExited() {
        nextButton.setOpacity(.5);
    }

    @FXML
    public void prevButtonEntered()    {
        prevButton.setOpacity(1);
    }

    @FXML
    public void prevButtonExited() {
        prevButton.setOpacity(.5);
    }

    @FXML
    public void resultButtonEntered()    {
        resultButton.setOpacity(1);
    }

    @FXML
    public void resultButtonExited() {
        resultButton.setOpacity(.5);
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
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
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
        ImageView nextImageView = new ImageView(image);
        nextImageView.setFitWidth(32);
        nextImageView.setFitHeight(32);
        nextButton.setGraphic(nextImageView);

        //sets and resize a graphic for the "nextButton"
        ImageView prevImageView = new ImageView(image);
        prevImageView.setFitWidth(32);
        prevImageView.setFitHeight(32);
        prevImageView.setScaleX(-1);
        prevButton.setGraphic(prevImageView);

        //Styles result per page drop-down menu


        MainController.getInstance().setProductPageController(this);

    }
}
