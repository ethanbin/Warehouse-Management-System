package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    @FXML
    void saveProductAndExitDetails(ActionEvent event) {
        exitDetails(event);
    }

    public boolean updateDetailsPage(){
        Product product = MainController.getInstance().getSelectedProduct();
        if (product == null)
            return false;
        IDTextField.setText(String.valueOf(product.getId()));
        nameTextField.setText(product.getName());
        countTextField.setText(String.valueOf(product.getStock()));
        priceTextField.setText(String.valueOf(product.getPrice()));
        descriptionTextArea.setText(product.getDescription());
        return true;
    }

    @FXML
    void initialize() {
        assert IDTextField != null : "fx:id=\"IDTextField\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert countTextField != null : "fx:id=\"countTextField\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert priceTextField != null : "fx:id=\"priceTextField\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert descriptionTextArea != null : "fx:id=\"descriptionTextArea\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert returnButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'DetailsPage.fxml'.";
        assert productImage != null : "fx:id=\"productImage\" was not injected: check your FXML file 'DetailsPage.fxml'.";

        MainController.getInstance().setDetailsController(this);
    }
}
