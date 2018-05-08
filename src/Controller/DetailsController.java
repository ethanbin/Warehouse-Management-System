package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Product;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.converter.NumberStringConverter;

public class DetailsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField countTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private CheckBox discontinuedCheckBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button returnButton;

    @FXML
    private ImageView productImage;

    @FXML
    void exitDetails(ActionEvent event) {

    }

    private boolean updateStock() {
        return updateStock(MainController.getInstance().getSelectedProduct().getId());
    }

    private boolean updateStock(int productID) {
        int stock = 0;
        if (!countTextField.getText().isEmpty())
            stock = Integer.valueOf(countTextField.getText());
        return DataController.getInstance().updateProductStockForProductAtWarehouse(stock,
                productID,
                MainController.getInstance().getCurrentWarehouseID());
    }

    public boolean updateDetailsPage(){
        Product product = MainController.getInstance().getSelectedProduct();
        if (product == null) {
            clear();
            return false;
        }
        IDTextField.setText(String.valueOf(product.getId()));
        nameTextField.setText(product.getName());
        countTextField.setText(String.valueOf(product.getStock()));
        priceTextField.setText(String.valueOf(product.getPrice()));
        descriptionTextArea.setText(product.getDescription());
        discontinuedCheckBox.setSelected(product.isDiscontinued());
        return true;
    }

    public void clear(){
        IDTextField.clear();
        nameTextField.clear();
        countTextField.clear();
        priceTextField.clear();
        descriptionTextArea.clear();
        discontinuedCheckBox.setSelected(false);
    }

    public void newProductMode(){
        clear();
        nameTextField.setEditable(true);
        countTextField.setEditable(true);
        priceTextField.setEditable(true);
        descriptionTextArea.setEditable(true);
        discontinuedCheckBox.setDisable(false);

        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        returnButton.setVisible(false);

        saveButton.setOnAction(event -> {
            System.out.println("save new clicked");
            int stock = Integer.valueOf(countTextField.getText());
            if (!nameTextField.getText().isEmpty() &&
                    !countTextField.getText().isEmpty() &&
                    !priceTextField.getText().isEmpty()) {
                DataController.getInstance().insertProduct(
                        nameTextField.getText(),
                        descriptionTextArea.getText(),
                        Float.parseFloat(priceTextField.getText()),
                        discontinuedCheckBox.isSelected() ? 1 : 0,
                        stock > 0 ? 1 : 0);
                updateStock();
                clear();
                MainController.getInstance().refreshProductsPage();
            }
        });
    }

    public void editProductMode(){
        updateDetailsPage();
        nameTextField.setEditable(true);
        countTextField.setEditable(true);
        priceTextField.setEditable(true);
        descriptionTextArea.setEditable(true);
        discontinuedCheckBox.setDisable(false);

        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        returnButton.setVisible(false);

        saveButton.setOnAction(event -> {
            System.out.println("save edit clicked");
            int stock = Integer.valueOf(countTextField.getText());
            if (!nameTextField.getText().isEmpty() &&
                    !countTextField.getText().isEmpty() &&
                    !priceTextField.getText().isEmpty()) {
                DataController.getInstance().updateProductAtIndex(Integer.parseInt(IDTextField.getText()),
                        nameTextField.getText(),
                        descriptionTextArea.getText(),
                        Float.parseFloat(priceTextField.getText()),
                        discontinuedCheckBox.isSelected() ? 1 : 0,
                        //stock exists:
                        stock > 0 ? 1 : 0);
            }
            // if stock has been changed
            if (stock != MainController.getInstance().getSelectedProduct().getStock()) {
                updateStock();
            }
            clear();
            MainController.getInstance().refreshProductsPage();
        });

    }

    public void detailsMode(){
        updateDetailsPage();
        nameTextField.setEditable(false);
        countTextField.setEditable(false);
        priceTextField.setEditable(false);
        descriptionTextArea.setEditable(false);
        discontinuedCheckBox.setDisable(true);

        IDTextField.setMouseTransparent(true);
        nameTextField.setMouseTransparent(true);
        countTextField.setMouseTransparent(true);
        priceTextField.setMouseTransparent(true);
        descriptionTextArea.setMouseTransparent(true);

        IDTextField.setFocusTraversable(false);
        nameTextField.setFocusTraversable(false);
        countTextField.setFocusTraversable(false);
        priceTextField.setFocusTraversable(false);
        descriptionTextArea.setFocusTraversable(false);

        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        returnButton.setVisible(true);
    }

    public void exit() {
        Platform.exit();
    }

    @FXML
    void initialize() {
        assert IDTextField != null : "fx:id=\"IDTextField\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert countTextField != null : "fx:id=\"countTextField\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert priceTextField != null : "fx:id=\"priceTextField\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert descriptionTextArea != null : "fx:id=\"descriptionTextArea\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert discontinuedCheckBox != null : "fx:id=\"discontinuedCheckBox\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert returnButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert productImage != null : "fx:id=\"productImage\" was not injected: check your FXML file 'DetailsPage.fxml'.";

        priceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d{0,2})?")) {
                    priceTextField.setText(oldValue);
                }
            }
        });

        countTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^\\d*")) {
                    countTextField.setText(oldValue);
                }
            }
        });

        MainController.getInstance().setDetailsController(this);


    }
}
