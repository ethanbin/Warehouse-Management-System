package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.ErrorHandler;
import Model.Product;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.hide();
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

    /**
     * Populate the details page with data from Main Controller's selected Product.
     * @returntrue if details page updated successfully
     */
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

    /**
     * Clear the details page by clearing every text field and check box in the details page.
     */
    public void clear(){
        IDTextField.clear();
        nameTextField.clear();
        countTextField.clear();
        priceTextField.clear();
        descriptionTextArea.clear();
        discontinuedCheckBox.setSelected(false);
    }

    /**
     * Clear the details page and prepare it for creating a product and set up the
     * save button to create a new product with given data when clicked.
     */
    public void newProductMode(){
        clear();
        setUpEditability(true);
        countTextField.setDisable(true);

        saveButton.setOnAction(event -> {
            boolean updateSuccessful = false;
            //int stock = Integer.valueOf(countTextField.getText());
            if (!nameTextField.getText().isEmpty() &&
                    !priceTextField.getText().isEmpty()) {
                updateSuccessful = DataController.getInstance().insertProduct(
                        nameTextField.getText(),
                        descriptionTextArea.getText(),
                        Float.parseFloat(priceTextField.getText()),
                        discontinuedCheckBox.isSelected() ? 1 : 0,
                        0);
                        //stock > 0 ? 1 : 0);
                //updateStock();
                clear();
                MainController.getInstance().refreshProductsPage();
            }
            if (!updateSuccessful){
                ErrorHandler.errorDialog("Product Not Saved","Product failed to be saved in the database.", null);
                return;
            }

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.hide();
        });
    }

    /**
     * Clear the details page and prepare it for viewing and editing a product. Set up the
     * save button to update an existing product with given data when clicked.
     */
    public void editProductMode(){
        updateDetailsPage();
        setUpEditability(true);

        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        returnButton.setVisible(false);

        saveButton.setOnAction(event -> {
            int stock = Integer.valueOf(countTextField.getText());
            boolean updateSuccessful = false;
            if (!nameTextField.getText().isEmpty() &&
                    !countTextField.getText().isEmpty() &&
                    !priceTextField.getText().isEmpty()) {
                updateSuccessful = DataController.getInstance().updateProductAtIndex(
                        Integer.parseInt(IDTextField.getText()),
                        nameTextField.getText(),
                        descriptionTextArea.getText(),
                        Float.parseFloat(priceTextField.getText()),
                        discontinuedCheckBox.isSelected() ? 1 : 0,
                        //stock exists:
                        stock > 0 ? 1 : 0);
            }
            // if stock has been changed
            if (stock != MainController.getInstance().getSelectedProduct().getStock()) {
                updateSuccessful = updateSuccessful && updateStock();
            }
            if (!updateSuccessful){
                ErrorHandler.errorDialog("Product Not Saved","Product failed to be saved in the database.", null);
                return;
            }
            clear();
            MainController.getInstance().refreshProductsPage();

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.hide();

        });
    }

    /**
     * Clear the details page and prepare it for only viewing a product's details.
     */
    public void detailsMode(){
        updateDetailsPage();
        setUpEditability(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        returnButton.setVisible(true);
    }

    private void setUpEditability(boolean isEditable){
        nameTextField.setEditable               (isEditable);
        countTextField.setEditable              (isEditable);
        priceTextField.setEditable              (isEditable);
        descriptionTextArea.setEditable         (isEditable);
        discontinuedCheckBox.setDisable         (!isEditable);

        IDTextField.setMouseTransparent         (!isEditable);
        nameTextField.setMouseTransparent       (!isEditable);
        countTextField.setMouseTransparent      (!isEditable);
        priceTextField.setMouseTransparent      (!isEditable);
        descriptionTextArea.setMouseTransparent (!isEditable);

        IDTextField.setFocusTraversable         (isEditable);
        nameTextField.setFocusTraversable       (isEditable);
        countTextField.setFocusTraversable      (isEditable);
        priceTextField.setFocusTraversable      (isEditable);
        descriptionTextArea.setFocusTraversable (isEditable);

        saveButton.setVisible(isEditable);
        cancelButton.setVisible(isEditable);
        returnButton.setVisible(!isEditable);
    }

    /**
     * Exit the Details page.
     */
    public void exit() {
        Platform.exit();
    }

    /**
     * Initialize the LoginController when its FXML is loaded. Initialize by setting
     * the price text field to only take integers and decimal numbers, the count text
     * field to only take integers, disabling the Product ID text field, and setting
     * the Main Controller's Details Controller to this.
     */
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

        IDTextField.setDisable(true);

        // ensure price field only takes decimal values
        priceTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d{0,2})?")) {
                    priceTextField.setText(oldValue);
                }
            }
        });

        // ensure count field only takes integer values
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
