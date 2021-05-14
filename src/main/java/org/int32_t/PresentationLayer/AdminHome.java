package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.int32_t.BusinessLayer.BaseProduct;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.MenuItem;

import java.util.LinkedList;
import java.util.List;

public class AdminHome {

    private int pageMultiplier = 0;
    private int nrElementsPerPage = 10;

    @FXML
    private StackPane root;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox productsList;

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

    @FXML
    void filterItems(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void nextPage(ActionEvent event) {

    }

    @FXML
    void previousPage(ActionEvent event) {

    }

    @FXML
    public void initialize(){
        rootPane.setOpacity(0);
        fadeIn();
    }

    private void fadeIn(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    void loadCSV(ActionEvent event) {
        DeliveryService.loadMenuFromCSV();
        updateView(DeliveryService.getMenu());
    }


    @FXML
    public void newProduct(ActionEvent actionEvent) {
        NewProductAdmin diag = new NewProductAdmin();
        JFXDialog dialog = new JFXDialog(root, diag, JFXDialog.DialogTransition.CENTER);
        dialog.show();
        diag.setDiag(dialog);
    }

    private void updateView(List<MenuItem> orders) {

        List<MenuItemView> filterItemsListView = new LinkedList<>();
        //Update the view with the new changes
        for (int i = pageMultiplier * nrElementsPerPage; i < (pageMultiplier + 1) * nrElementsPerPage && i < orders.size(); ++i) {
            MenuItem entry = orders.get(i);
            BaseProduct base = (BaseProduct) entry;
            filterItemsListView.add(new MenuItemView(null, null, null, null, base, false));
        }
        productsList.getChildren().setAll(filterItemsListView);
    }

    public void refresh(ActionEvent actionEvent) {
        updateView(DeliveryService.getMenu());
    }
}
