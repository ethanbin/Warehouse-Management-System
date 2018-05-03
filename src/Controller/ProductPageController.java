package Controller;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;

/**
 * Created by Ethan on 5/1/2018.
 */
public class ProductPageController {

    @FXML
    private TableView<?> productsTable;

    @FXML
    private TableColumn<?, ?> IDColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableColumn<?, ?> countColumn;

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
    void initialize() {
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

//        searchTextField.insertText(0,"test");
//        try() {
//            productsTable.getItems().setAll(DataController.getInstance().selectAllProductsInRange(0,5));
//        }
//        catch (Exception e){}
    }
}
