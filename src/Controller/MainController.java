package Controller;

import Exceptions.ErrorHandler;
import Model.Product;
import Model.User;
import View.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A singleton that acts as the interface between the
 * GUI views and the data models. It is also used as the entry point of the application.
 */
public class MainController {
    private ScheduledExecutorService lowStockScheduler;
    private static MainController instance = null;
    private TrayIcon trayIcon = null;
    private boolean lowStockAlertSent = false;

    private final String SETTINGS_FILE_NAME = "settings";
    private ResourceBundle bundle;
    private String databaseURL;
    private User currentUser;
    private int currentWarehouseID = -1;
    private int secondsToCheckStock = 60;
    private int lowStockThreshold = 30;

    private ProductPageController productPageController;
    private Product selectedProduct = null;

    private DetailsController detailsController;

    private MainController(){
        bundle = ResourceBundle.getBundle(SETTINGS_FILE_NAME);

        if (!bundle.containsKey("databaseURL"))
            ErrorHandler.logCriticalError(
                    new MissingResourceException("dataBaseURL property not found", SETTINGS_FILE_NAME,"databaseURL"));
        databaseURL = bundle.getString("databaseURL");

        lowStockScheduler = Executors.newScheduledThreadPool(1);

        SystemTray tray = SystemTray.getSystemTray();
        // temporary until we get a better image
        //TODO - use better image
        Image trayImage = Toolkit.getDefaultToolkit().createImage("res/img/refresh.png");
        PopupMenu trayMenu = new PopupMenu();
        MenuItem openApp = new MenuItem("Open " + View.VIEW_TITLE);
        MenuItem exitApp = new MenuItem("Exit the Application");
        openApp.addActionListener(l ->
        {
            // if no user logged in
//            if (currentUser == null)
//                SceneController.activate("Login");
//            else
//                SceneController.activate("Home");
        });
        exitApp.addActionListener(l ->{
            stopLowStockScheduler();
            logout();
            DataController.getInstance().close();
            removeTrayIcon();
            System.exit(0);
        });
        trayMenu.add(openApp);
        trayMenu.add(exitApp);
        trayIcon = new TrayIcon(trayImage, View.VIEW_TITLE, trayMenu);
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        }
        catch (AWTException e){
            System.out.println(e);
        }
    }

    /**
     * Following singleton pattern, this method will return the static instance of the
     * MainController class. If no instance yet exists, one will be created using the
     * MainController constructor, which also creates a system tray icon, saved as a
     * static variable, and returned. If there is a problem creating the MainController,
     * an exception will be passed to ErrorHandler.logCriticalError and the application
     * will terminate.
     * @return MainController's static property instance, of type MainController
     * @see ErrorHandler
     */
    public synchronized static MainController getInstance(){
        if (instance == null)
            instance = new MainController();
        return instance;
    }

    /**
     * Alert the user via system tray notification if any products are low on stock at
     * the user's location and if a notification has not yet been sent.
     */
    public void lowStockAlert() {
        List<Product> products = DataController.getInstance().
                selectAllProductsAtLowStockAtWarehouse(lowStockThreshold, currentWarehouseID);
        if (products != null && !lowStockAlertSent) {
            String lowProductCaption = "One or more Products are low on stock.";
            String lowProductMessage = "Generate a Low Stock Report for more info.";
            trayIcon.displayMessage(lowProductCaption, lowProductMessage, TrayIcon.MessageType.INFO);
            lowStockAlertSent = true;
        }
    }

    /**
     * Run {@link MainController#lowStockAlert()} repeatedly on a timed schedule on
     * a separate thread.
     */
    public void startLowStockScheduler(){
        Runnable lowStockAlerter = new Runnable() {
            public void run() {
                lowStockAlert();
            }
        };
        lowStockScheduler.scheduleAtFixedRate(lowStockAlerter, 0, secondsToCheckStock, TimeUnit.SECONDS);
    }

    /**
     * Stop the thread scheduled to run {@link MainController#lowStockAlert()}.
     */
    public void stopLowStockScheduler(){
        lowStockScheduler.shutdown();
    }

    /**
     * Remove the application's system tray icon.
     */
    public void removeTrayIcon(){
        SystemTray tray = SystemTray.getSystemTray();
        tray.remove(trayIcon);
    }

    /**
     * Return path to settings property file.
     * @return path to settings property file
     */
    public String getSettingsFileName() {
        return SETTINGS_FILE_NAME;
    }

    /**
     * Get URL or path to the database, as specified in settings file.
     * @return URL or path to the database
     */
    public String getDatabaseURL() {
        return databaseURL;
    }

    /**
     * Return currently logged in User.
     * @return currently logged in User
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Attempt to log in user with given username and password. If login successful,
     * set the MainController's instance of the current user to the one logged in,
     * set the MainController's value of the current warehouse ID to the user's, and
     * call {@link MainController#startLowStockScheduler()}.
     * @param username username
     * @param password password
     * @return true if user logged in successfully
     */
    public boolean loginUser(String username, String password) {
        currentUser =  DataController.getInstance().selectUser(username, password);
        if (currentUser == null)
            return false;
        currentWarehouseID = currentUser.getWarehouseID();
        startLowStockScheduler();
        return true;
    }

    /**
     * Return the warehouse ID of the currently logged in user.
     * @return the warehouse ID of the currently logged in user
     */
    public int getCurrentWarehouseID() {
        return currentWarehouseID;
    }

    /**
     * Return the active instance of the Product Page Controller.
     * @return the active instance of the Product Page Controller
     * @see ProductPageController
     */
    public ProductPageController getProductPageController() {
        return productPageController;
    }

    /**
     * Set the active instance of the Product Page Controller
     * @param productPageController the currently active Product Page Controller
     * @see ProductPageController
     */
    public void setProductPageController(ProductPageController productPageController) {
        this.productPageController = productPageController;
    }

    /**
     * Return the active instance of the Details Controller.
     * @return the active instance of the Details Controller
     * @see DetailsController
     */
    public DetailsController getDetailsController() {
        return detailsController;
    }

    /**
     * Set the active instance of the Details Controller
     * @param detailsController the currently active Details Controller
     * @see DetailsController
     */
    public void setDetailsController(DetailsController detailsController) {
        this.detailsController = detailsController;
    }

    /**
     * Return the currently selected product.
     * @return the currently selected product
     * @see Product
     */
    public Product getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * Set the currently selected product.
     * @param selectedProduct the currently selected Product
     * @see Product
     */
    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    /**
     * Return the value below which the application considers low stock.
     * @return the value below which the application considers low stock
     */
    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    /**
     * Set the status of whether a low stock alert was sent.
     * @param lowStockAlertSent new status of whether a low stock alert was sent
     */
    public void setLowStockAlertSent(boolean lowStockAlertSent) {
        this.lowStockAlertSent = lowStockAlertSent;
    }

    /**
     * If the productPageController has been initialized, refresh the displayed products
     * in the view using {@link ProductPageController#showCurrentProductsPage()} and return true.
     * @return true if the productPageController has been initialized and the products refreshed.
     */
    public boolean refreshProductsPage(){
        if (productPageController == null)
            return false;
        productPageController.showCurrentProductsPage();
        return true;
    }

    /**
     * Logout the user, clean up data, call {@link DataController#resetDataController()},
     * call {@link MainController#stopLowStockScheduler()}, and change the view to the
     * login screen.
     * @see DataController
     */
    public void logout(){
        stopLowStockScheduler();
        currentUser = null;
        currentWarehouseID = -1;
        selectedProduct = null;
        DataController.getInstance().resetDataController();
        detailsController.clear();
        SceneController.activate("Login");
    }

    /**
     * The entry point of the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        DataController.getInstance();

        Application.launch(View.class,args);
    }
}