package Controller;

import Exceptions.ErrorHandler;
import Model.Product;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Ethan on 5/1/2018.
 */
public class ProductPageController implements Initializable {
    private int productsPerPage = 20;
    private int currentProductPage = 0;
    private Image navButtonImage = new Image("file:res/img/next.png");
    private Image refreshButtonImage = new Image("file:res/img/refresh.png");

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableView<Product> reportsTable;

    @FXML
    private TableColumn<Product, String> productIDColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> priceColumn;

    @FXML
    private TableColumn<Product, Integer> countColumn;

    @FXML
    private TableColumn<Product, String> reportProductIDColumn;

    @FXML
    private TableColumn<Product, String> reportNameColumn;

    @FXML
    private TableColumn<Product, String> reportPriceColumn;

    @FXML
    private TableColumn<Product, String> reportCountColumn;

    @FXML
    private TableColumn<Product, String> reportWarehouseIDColumn;

    @FXML
    private TextField searchField;

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
    private ChoiceBox<String> searchForMenu;

    @FXML
    private ChoiceBox<String> reportTypeChoiceBox;

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
    private ChoiceBox chooseReportMenu;

    @FXML
    public void showNextProductsPage() {
        currentProductPage++;
        if (currentProductPage * productsPerPage >= DataController.getInstance().selectCountFromProducts())
            currentProductPage--;
        else
            showCurrentProductsPage();
    }

    @FXML
    public void showPrevProductsPage() {
        currentProductPage--;
        if (currentProductPage < 0)
            currentProductPage = 0;
        else
            showCurrentProductsPage();
    }

    @FXML
    public void showCurrentProductsPage() {
        clearSelectedProduct();
        productsTable.getItems().clear();
        for (TableRow row: tableRows) {
            row.setStyle(null);
        }
        tableRows.clear();
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
        searchField.clear();
        productsTable.getItems().clear();
        MainController.getInstance().logout();
    }

    @FXML
    protected void searchProducts() {
        switch (searchForMenu.getValue().toString()){
            case "ID":
                int productID;
                try {
                    productID = Integer.valueOf(searchField.getText());
                    clearSelectedProduct();
                    productsTable.getItems().setAll(DataController.getInstance().selectAllProductsWithID(productID,
                            MainController.getInstance().getCurrentWarehouseID()));
                }
                catch (NumberFormatException e){
                    ErrorHandler.errorDialog("Invalid Input Detected",
                            "An invalid ID search was attempted. Make sure a number is entered.", null);
                    System.err.println("An invalid ID search was attempted. Make sure a number is entered.");
                }
                break;
            case "Name":
                String likeName;
                likeName = searchField.getText();
                clearSelectedProduct();
                productsTable.getItems().setAll(DataController.getInstance().selectAllProductsWithName(likeName,
                        MainController.getInstance().getCurrentWarehouseID()));
                break;
            case "Price":
                float price;
                try {
                    price = Float.valueOf(searchField.getText());
                    clearSelectedProduct();
                    productsTable.getItems().setAll(DataController.getInstance().selectAllProductsWithPrice(price,
                            MainController.getInstance().getCurrentWarehouseID()));
                }
                catch (NumberFormatException e){
                    ErrorHandler.errorDialog("Invalid Input Detected",
                            "An invalid Price search was attempted. " +
                                    "Make sure a number is entered.", null );
                    System.err.println("An invalid price search was attempted. Make sure a number is entered.");
                }
                break;
            case "Count":
                int stockCount;
                try {
                    stockCount = Integer.valueOf(searchField.getText());
                    clearSelectedProduct();
                    productsTable.getItems().setAll(DataController.getInstance().selectAllProductsWithStock(stockCount,
                            MainController.getInstance().getCurrentWarehouseID()));
                }
                catch (NumberFormatException e){
                    ErrorHandler.errorDialog("Invalid Input Detected",
                            "An invalid Stock Count search was attempted. " +
                                    "Make sure a number is entered.", null );
                    System.err.println("An invalid stock search was attempted. Make sure a number is entered.");
                }
                break;
            default:
                System.err.println("Select a search type.");
                ErrorHandler.errorDialog("No Search Type Selected", "Please select a search type.", null);
                break;
        }
    }

    @FXML
    protected void exportReport() {
        switch (reportTypeChoiceBox.getValue()) {
            case "Low Stock Report":
                String fileName = "Stock Reports for " + LocalDate.now() + ".csv";
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(fileName), true))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(String.format("%n"));
                    // print out current date and time
                    stringBuilder.append("Time:");
                    stringBuilder.append(',');
                    stringBuilder.append(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now()));
                    stringBuilder.append(String.format("%n"));

                    stringBuilder.append("Product ID,");
                    stringBuilder.append("Name,");
                    stringBuilder.append("Price,");
                    stringBuilder.append("Discontinued,");
                    stringBuilder.append("Stock Exists,");
                    stringBuilder.append("Warehouse ID");
                    stringBuilder.append(String.format("%n"));

                    if (allStoresCheckBox.isSelected()) {
                        for (int currentWarehouseID : DataController.getInstance().selectAllWarehouseIDs())
                            appendProductToStringBuilder(stringBuilder, currentWarehouseID);
                    }
                    else {
                        appendProductToStringBuilder(stringBuilder, MainController.getInstance().getCurrentWarehouseID());
                    }
                    writer.append(stringBuilder);
                    ErrorHandler.errorDialog("Report Generated",
                            "The low stock report has been successfully generated.", null);
                }
                catch (IOException e){
                    ErrorHandler.errorDialog("Report Generation Error",
                            "An error occurred when trying to generate a low stock report.", null);
                }
                break;
            default:
                System.err.println("No Supported Report Type Selected");
                ErrorHandler.errorDialog("No Report Type Selected",
                        "Please select a supported report type to generate.", null);
                break;
        }
    }

    private StringBuilder appendProductToStringBuilder(StringBuilder stringBuilder, int currentWarehouseID){
        for (Product p : (DataController.getInstance().selectAllProductsAtLowStockAtWarehouse(
                MainController.getInstance().getLowStockThreshold(), currentWarehouseID))) {
            stringBuilder.append(p.getId());
            stringBuilder.append(',');
            stringBuilder.append(p.getName());
            stringBuilder.append(',');
            stringBuilder.append(p.getPrice());
            stringBuilder.append(',');
            stringBuilder.append(p.isDiscontinued());
            stringBuilder.append(',');
            stringBuilder.append(p.doesStockExist());
            stringBuilder.append(',');
            stringBuilder.append(p.getWarehouseID());
            stringBuilder.append(String.format("%n"));
        }
        return stringBuilder;
    }

    @FXML
    protected void generateReport() {
        switch (reportTypeChoiceBox.getValue()) {
            case "Low Stock Report":
                reportsTable.getItems().clear();
                List<Product> lowStockProducts = null;
                if (!allStoresCheckBox.isSelected())
                    lowStockProducts = DataController.getInstance().selectAllProductsAtLowStockAtWarehouse(
                            MainController.getInstance().getLowStockThreshold(),
                            MainController.getInstance().getCurrentWarehouseID());
                else {
                    lowStockProducts = new ArrayList<>();
                    for (int currentWarehouseID : DataController.getInstance().selectAllWarehouseIDs()) {
                        lowStockProducts.addAll(DataController.getInstance().selectAllProductsAtLowStockAtWarehouse(
                                MainController.getInstance().getLowStockThreshold(), currentWarehouseID));
                    }
                }
                reportsTable.getItems().addAll(lowStockProducts);
                MainController.getInstance().setLowStockAlertSent(false);
                break;

            case "Sales Report":
                break;

            default:
                System.err.println("No Supported Report Type Selected");
                ErrorHandler.errorDialog("No Report Type Selected",
                        "Please select a supported report type to generate.", null);
                break;
        }
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

        for (TableRow<Product> tableRow : tableRows){
            if (selectedProduct == null)
                break;
            if (tableRow == null) {
                continue;
            }
            if (tableRow.getItem() == null) {
                tableRow.setStyle(null);
                continue;
            }
            if (tableRow.getItem().getId() == selectedProduct.getId()){
                tableRow.setStyle(tableRow.getStyle() + ";-fx-border-color: black");
            }
            else {
                // split style into array using semicolon as delimeter and set the style to the first value
                tableRow.setStyle(tableRow.getStyle().split(";")[0]);
            }
        }

        //changeBackgroundOnHoverUsingBinding();

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
        assert reportsTable != null : "fx:id=\"reportsTable\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert productIDColumn != null : "fx:id=\"IDColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert priceColumn != null : "fx:id=\"priceColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert countColumn != null : "fx:id=\"countColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert reportProductIDColumn != null : "fx:id=\"reportIDColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert reportNameColumn != null : "fx:id=\"reportNameColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert reportPriceColumn != null : "fx:id=\"reportPriceColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert reportCountColumn != null : "fx:id=\"reportCountColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert reportWarehouseIDColumn != null : "fx:id=\"reportWarehouseIDColumn\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert nextButton != null : "fx:id=\"nextButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert refreshButton != null : "fx:id=\"refreshButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert prevButton != null : "fx:id=\"prevButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert resultButton != null : "fx:id=\"resultButton\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert searchForMenu != null : "fx:id=\"criteriaMenu\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert reportTypeChoiceBox != null : "fx:id=\"reportTypeChoiceBox\" was not injected: check your FXML file 'ProductPage.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'ProductPage.fxml'.";
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

        productIDColumn.setCellValueFactory(new PropertyValueFactory("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        countColumn.setCellValueFactory(new PropertyValueFactory("stock"));

        productIDColumn.setReorderable(false);
        nameColumn.setReorderable(false);
        priceColumn.setReorderable(false);
        countColumn.setReorderable(false);

        reportProductIDColumn.setCellValueFactory(new PropertyValueFactory("id"));
        reportNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        reportPriceColumn.setCellValueFactory(new PropertyValueFactory("price"));
        reportCountColumn.setCellValueFactory(new PropertyValueFactory("stock"));
        reportWarehouseIDColumn.setCellValueFactory(new PropertyValueFactory("warehouseID"));

        reportProductIDColumn.setReorderable(false);
        reportNameColumn.setReorderable(false);
        reportPriceColumn.setReorderable(false);
        reportCountColumn.setReorderable(false);
        reportWarehouseIDColumn.setReorderable(false);

        searchForMenu.getItems().addAll("Search Type", "ID", "Name", "Price", "Count");
        searchForMenu.setValue("Search Type");

        reportTypeChoiceBox.getItems().addAll("Report Type", "Low Stock Report", "Sales Report");
        reportTypeChoiceBox.setValue("Report Type");

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

        tableRows = FXCollections.observableArrayList();
        tableRows.addListener(new ListChangeListener<TableRow<Product>>() {
            @Override
            public void onChanged(Change<? extends TableRow<Product>> c) {
                if (tableRows.size() > 0)
                    styleRow(tableRows.get(tableRows.size() - 1));
            }
        });

        countColumn.setCellFactory(column -> {
            return new TableCell<Product, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    TableRow<Product> currentRow = getTableRow();
                    Product productInCurrentRow = currentRow.getItem();
                    if (productInCurrentRow == null) {
                        setGraphic(null);
                        setText(null);
                        currentRow.setItem(null);
                        super.updateItem(item, empty);
                        currentRow.setStyle(null);
                        return;
                    }
                    tableRows.add(currentRow);
                    setText(empty ? "" : String.valueOf(productInCurrentRow.getStock()));
                    setGraphic(null);

//                    if (!isEmpty()) {
//
//                        if (productInCurrentRow.isDiscontinued())
//                            currentRow.setStyle("-fx-background-color:lightblue");
//                        else if (item.equals(0))
//                            currentRow.setStyle("-fx-background-color:lightcoral");
//                        else if (item.compareTo(MainController.getInstance().getLowStockThreshold()) < 0)
//                            currentRow.setStyle("-fx-background-color:lightyellow");
//                    }
                }
            };
        });

        countColumn.setCellFactory(column -> {
            return new TableCell<Product, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    TableRow<Product> currentRow = getTableRow();
                    Product productInCurrentRow = currentRow.getItem();
                    if (productInCurrentRow == null) {
                        setGraphic(null);
                        setText(null);
                        currentRow.setItem(null);
                        super.updateItem(item, empty);
                        //currentRow.setStyle(null);
                        return;
                    }
                    setText(empty ? "" : String.valueOf(productInCurrentRow.getStock()));
                    setGraphic(null);
                    tableRows.add(currentRow);
                    }
            };
        });
    }

    ObservableList<TableRow<Product>> tableRows;

    private void styleRow(TableRow tableRow){
        if (tableRow == null)
            return;
        tableRow.setStyle(null);
        if (!(tableRow.getItem() instanceof Product))
            return;
        Product product = (Product) tableRow.getItem();
        if (product == null)
            return;
        if (product.isDiscontinued())
            tableRow.setStyle("-fx-background-color:#599fe6");
        else if (product.getStock() == 0)
            tableRow.setStyle("-fx-background-color:lightcoral");
        else if (product.getStock() < MainController.getInstance().getLowStockThreshold())
            tableRow.setStyle("-fx-background-color:#fdff66");
    }

    public void clearSelectedProduct(){
        MainController.getInstance().setSelectedProduct(null);
        MainController.getInstance().getDetailsController().clear();
        productNameTextField.clear();
        productDescriptionTextField.clear();

        editProductButton.setDisable(true);
        detailsButton.setDisable(true);
    }
}
