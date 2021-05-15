package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.int32_t.BusinessLayer.BaseProduct;
import org.int32_t.BusinessLayer.CompositeProduct;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.MenuItem;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdminCompositeProduct extends AnchorPane {

    JFXDialog dialog;
    private int pageMultiplier = 0;
    private final int nrElementsPerPage = 10;
    private List<MenuItem> itemsList = new LinkedList<>();
    private List<MenuItem> selectedItems = new LinkedList<>();

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox productsList;

    @FXML
    private VBox selectedProducts;

    @FXML
    private JFXTextField keyword;

    @FXML
    private JFXTextField rating;

    @FXML
    private JFXTextField nrCalories;

    @FXML
    private JFXTextField proteins;

    @FXML
    private JFXTextField fats;

    @FXML
    private JFXTextField sodium;

    @FXML
    private JFXTextField price;


    public AdminCompositeProduct() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PresentationLayer/AdminCompositeProduct.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        updateView(DeliveryService.getMenu());
        itemsList.addAll(DeliveryService.getMenu());
    }


    @FXML
    void filterItems(ActionEvent event) {

        Predicate<MenuItem> keywordFilter = n -> (this.keyword.getText().isEmpty() || n.getTitle().toLowerCase().contains(this.keyword.getText().toLowerCase()));
        itemsList = DeliveryService.getMenu().stream().filter(keywordFilter).collect(Collectors.toList());
        pageMultiplier = 0;
        updateView(itemsList);

    }


    @FXML
    void nextPage(ActionEvent event) {
        if (pageMultiplier * (nrElementsPerPage - 1) < itemsList.size()) {
            pageMultiplier++;
            updateView(itemsList);
        }
    }

    @FXML
    void previousPage(ActionEvent event) {
        if (pageMultiplier >= 1) {
            pageMultiplier--;
            updateView(itemsList);
        }
    }


    @FXML
    void createProduct(ActionEvent event) {
        CompositeProduct product = new CompositeProduct(selectedItems,"title");
        DeliveryService dev = new DeliveryService();
        dev.addToMenu(product.convertToBase());
        dialog.close();
    }


    private void updateView(List<MenuItem> orders) {

        List<SimpleProductView> itemsList = new LinkedList<>();
        //Update the view with the new changes
        for (int i = pageMultiplier * nrElementsPerPage; i < (pageMultiplier + 1) * nrElementsPerPage && i < orders.size(); ++i) {
            MenuItem entry = orders.get(i);
            itemsList.add(new SimpleProductView(this,entry,"Add"));
        }
        productsList.getChildren().clear();
        productsList.getChildren().setAll(itemsList);
    }




    public void setDiag(JFXDialog dialog) {
        this.dialog = dialog;
    }

    public void remove(MenuItem item) {
        selectedItems.remove(item);

        List<SimpleProductView> productViews = new LinkedList<>();
        for(MenuItem itm : selectedItems){
            productViews.add(new SimpleProductView(this, itm,"Remove"));
        }
        selectedProducts.getChildren().clear();
        selectedProducts.getChildren().addAll(productViews);

    }

    public void add(MenuItem item) {
        selectedItems.add(item);

        List<SimpleProductView> productViews = new LinkedList<>();
        for(MenuItem itm : selectedItems){
            productViews.add(new SimpleProductView(this, itm,"Remove"));
        }
        selectedProducts.getChildren().clear();
        selectedProducts.getChildren().addAll(productViews);
    }
}
