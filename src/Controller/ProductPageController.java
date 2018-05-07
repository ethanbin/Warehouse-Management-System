package Controller;

import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
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
    private Image navButtonImage = new Image("file:res/img/next.png");
    private Image refreshButtonImage = new Image("file:res/img/refresh.png");

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
    private ComboBox criteriaMenu;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private ComboBox resultButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button nextReportButton;

    @FXML
    private Button prevReportButton;

    @FXML
    private ComboBox resultReportButton;

    @FXML
    private Button refreshReportButton;

    @FXML
    private VBox productTop;

    @FXML
    public void showNextProductsPage(){
        currentProductPage++;
        if (currentProductPage * productsPerPage >= DataController.getInstance().selectCountFromProducts())
            currentProductPage--;
        else
            showCurrentProductsPage();
    }

    @FXML
    public void showPrevProductsPage(){
        currentProductPage--;
        if (currentProductPage < 0)
            currentProductPage = 0;
        else
            showCurrentProductsPage();
    }

    @FXML
    public void showCurrentProductsPage(){
        MainController.getInstance().setSelectedProduct(null);
        MainController.getInstance().getDetailsController().clear();
        productNameTextField.clear();
        productDescriptionTextField.clear();

        editProductButton.setDisable(true);
        detailsButton.setDisable(true);

        productsTable.getItems().setAll(DataController.getInstance().selectAllProductsInRange(
                currentProductPage * productsPerPage, productsPerPage));
    }

    @FXML
    protected void newProduct() {
        MainController.getInstance().getDetailsController().newProductMode();
        openProductDetailsPage("New Product Page");
    }

    @FXML
    protected void editProduct()    {
        MainController.getInstance().getDetailsController().editProductMode();
        openProductDetailsPage("Edit Product Page");
    }

    @FXML
    protected void detailsProduct()    {
        MainController.getInstance().getDetailsController().detailsMode();
        openProductDetailsPage("Details Product Page");
    }

    private void openProductDetailsPage(String title){
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
        productDescriptionTextField.clear();
        productNameTextField.clear();
        searchTextField.clear();
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
        Product selectedProduct;
        if (productsTable.getSelectionModel().getSelectedItem() == null)
            return;

        selectedProduct = new Product(productsTable.getSelectionModel().getSelectedItem());
        productNameTextField.setText(selectedProduct.getName());
        productDescriptionTextField.setText(selectedProduct.getDescription());
        MainController.getInstance().setSelectedProduct(selectedProduct);

        editProductButton.setDisable(false);
        detailsButton.setDisable(false);
        
        if (event.getClickCount() == 2)
            detailsProduct();
    }

    @FXML
    public void nextButtonEntered()    {
        nextButton.setOpacity(1);
        nextReportButton.setOpacity(1);
    }

    @FXML
    public void nextButtonExited() {
        nextButton.setOpacity(.5);
        nextReportButton.setOpacity(.5);
    }

    @FXML
    public void prevButtonEntered()    {
        prevButton.setOpacity(1);
        prevReportButton.setOpacity(1);
    }

    @FXML
    public void prevButtonExited() {
        prevButton.setOpacity(.5);
        prevReportButton.setOpacity(.5);
    }

    @FXML
    public void resultButtonEntered()    {
        resultButton.setOpacity(1);
        resultReportButton.setOpacity(1);
    }

    @FXML
    public void resultButtonExited() {
        resultButton.setOpacity(.5);
        resultReportButton.setOpacity(.5);
    }

    @FXML
    public void refreshButtonEntered()    {
        refreshButton.setOpacity(1);
        refreshReportButton.setOpacity(1);
    }

    @FXML
    public void refreshButtonExited() {
        refreshButton.setOpacity(.5);
        refreshReportButton.setOpacity(.5);
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
        assert refreshButton != null : "fx:id=\"refreshButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert prevButton != null : "fx:id=\"prevButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert resultButton != null : "fx:id=\"resultButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert criteriaMenu != null : "fx:id=\"criteriaMenu\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert searchTextField != null : "fx:id=\"searchTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productImage != null : "fx:id=\"productImage\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productNameTextField != null : "fx:id=\"productNameTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productDescriptionTextField != null : "fx:id=\"productDescriptionTextField\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert newProductButton != null : "fx:id=\"newProductButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert editProductButton != null : "fx:id=\"editProductButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert detailsButton != null : "fx:id=\"detailsButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert nextReportButton != null : "fx:id=\"nextReportButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert refreshReportButton != null : "fx:id=\"refreshReportButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert prevReportButton != null : "fx:id=\"prevReportButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert resultReportButton != null : "fx:id=\"resultReportButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
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

        criteriaMenu.getItems().addAll("ID", "Name", "Price", "Count");

        //sets and resizes a graphic for the "nextButton"
        ImageView nextImageView = new ImageView(navButtonImage);
        nextImageView.setFitWidth(32);
        nextImageView.setFitHeight(32);
        nextButton.setGraphic(nextImageView);

        //sets and resize a graphic for the "prevButton"
        ImageView prevImageView = new ImageView(navButtonImage);
        prevImageView.setFitWidth(32);
        prevImageView.setFitHeight(32);
        prevImageView.setScaleX(-1);
        prevButton.setGraphic(prevImageView);

        //sets and resize a graphic for the "refreshButton"
        ImageView refreshImageView = new ImageView(refreshButtonImage);
        refreshImageView.setFitWidth(30);
        refreshImageView.setFitHeight(30);
        refreshButton.setGraphic(refreshImageView);

        //sets and resizes a graphic for the "nextReportButton"
        ImageView nextReportImageView = new ImageView(navButtonImage);
        nextReportImageView.setFitWidth(32);
        nextReportImageView.setFitHeight(32);
        nextReportButton.setGraphic(nextReportImageView);

        //sets and resize a graphic for the "prevReportButton"
        ImageView prevReportImageView = new ImageView(navButtonImage);
        prevReportImageView.setFitWidth(32);
        prevReportImageView.setFitHeight(32);
        prevReportImageView.setScaleX(-1);
        prevReportButton.setGraphic(prevReportImageView);

        //sets and resize a graphic for the "refreshReportButton"
        ImageView refreshReportImageView = new ImageView(refreshButtonImage);
        refreshReportImageView.setFitWidth(30);
        refreshReportImageView.setFitHeight(30);
        refreshReportButton.setGraphic(refreshReportImageView);


        editProductButton.setDisable(true);
        detailsButton.setDisable(true);

        MainController.getInstance().setProductPageController(this);

    }
}
