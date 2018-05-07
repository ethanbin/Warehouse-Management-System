package Controller;

import Exceptions.ErrorHandler;
import Model.Product;
import Model.User;
import View.View;
import javafx.application.Application;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * MainController class is a singleton that acts as the interface between the
 * GUI views and the data models. It is also the entry point of the application.
 */
public class MainController {

    private static MainController instance = null;

    private final String SETTINGS_FILE_NAME = "settings";
    private ResourceBundle bundle;
    private String databaseURL;
    private User currentUser;
    private int currentWarehouseID = -1;

    private ProductPageController productPageController;
    private Product selectedProduct = null;

    private DetailsController detailsController;

    private MainController(){
        bundle = ResourceBundle.getBundle(SETTINGS_FILE_NAME);

        if (!bundle.containsKey("databaseURL"))
            ErrorHandler.logCriticalError(
                    new MissingResourceException("dataBaseURL property not found", SETTINGS_FILE_NAME,"databaseURL"));
        databaseURL = bundle.getString("databaseURL");
    }

    /**
     * Following singleton pattern, this method will return the static instance of the
     * MainController class. If no instance yet exists, one will be created using the
     * MainController constructor, saved as a static variable, and returned.
     * If there is a problem creating the MainController, an exception will be passed
     * to ErrorHandler.logCriticalError and the application will terminate.
     * @return MainController's static property instance, of type MainController
     * @see ErrorHandler
     */
    public static MainController getInstance(){
        if (instance == null)
            instance = new MainController();
        return instance;
    }

    /**
     * Return path to settings property file
     * @return
     */
    public String getSettingsFileName() {
        return SETTINGS_FILE_NAME;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean loginUser(String username, String password) {
        currentUser =  DataController.getInstance().selectUser(username, password);
        if (currentUser == null)
            return false;
        currentWarehouseID = currentUser.getWarehouseID();
        return true;
    }

    public int getCurrentWarehouseID() {
        return currentWarehouseID;
    }

    public static void main(String[] args) {
        DataController.getInstance();

        Application.launch(View.class,args);
    }

    public ProductPageController getProductPageController() {
        return productPageController;
    }

    public void setProductPageController(ProductPageController productPageController) {
        this.productPageController = productPageController;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public DetailsController getDetailsController() {
        return detailsController;
    }

    public void setDetailsController(DetailsController detailsController) {
        this.detailsController = detailsController;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public boolean refreshProductsPage(){
        if (productPageController == null)
            return false;
        productPageController.showCurrentProductsPage();
        return true;
    }

    public void logout(){
        currentUser = null;
        currentWarehouseID = -1;
        selectedProduct = null;
        DataController.getInstance().resetDataController();
        detailsController.clear();
        SceneController.activate("Login");
    }
}