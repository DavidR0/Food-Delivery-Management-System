package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.int32_t.BusinessLayer.BaseProduct;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.MenuItem;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdminHome {

    private int pageMultiplier = 0;
    private final int nrElementsPerPage = 10;
    private List<MenuItem> itemsList = new LinkedList<>();

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
    void logout(ActionEvent event) {
        productsList.getChildren().clear();
        itemsList.clear();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event1 -> {

            try {
                loadSignIn();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        fadeTransition.play();
    }

    private void loadSignIn() throws IOException {
        Stage thisStage = (Stage) rootPane.getScene().getWindow();
        thisStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../PresentationLayer/loginAdmin.fxml"))), 1123, 721));
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
    public void initialize(){
        itemsList.addAll(DeliveryService.getMenu());
        updateView(DeliveryService.getMenu());
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
        itemsList.clear();
        itemsList.addAll(DeliveryService.getMenu());
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
            filterItemsListView.add(new MenuItemView(this,null, null, null, null, base, false));

        }
        filterItemsListView.forEach(n->n.setBtnText("Edit"));
        productsList.getChildren().setAll(filterItemsListView);
    }

    @FXML
    public void filterItems(ActionEvent actionEvent) {
        Predicate<MenuItem> priceFilter = n -> (this.price.getText().isEmpty() || n.getPrice() <= Integer.parseInt(this.price.getText()));
        Predicate<MenuItem> fatFilter = n -> (this.fats.getText().isEmpty() || n.getFat() <= Integer.parseInt(this.fats.getText()));
        Predicate<MenuItem> sodiumFilter = n -> (this.sodium.getText().isEmpty() || n.getSodium() <= Integer.parseInt(this.sodium.getText()));
        Predicate<MenuItem> ratingFilter = n -> (this.rating.getText().isEmpty() || n.getRating() >= Float.parseFloat(this.rating.getText()));
        Predicate<MenuItem> nrCaloriesFilter = n -> (this.nrCalories.getText().isEmpty() || n.getCalories() <= Integer.parseInt(this.nrCalories.getText()));
        Predicate<MenuItem> proteinsFilter = n -> (this.proteins.getText().isEmpty() || n.getProtein() <= Integer.parseInt(this.proteins.getText()));
        Predicate<MenuItem> keywordFilter = n -> (this.keyword.getText().isEmpty() || n.getTitle().toLowerCase().contains(this.keyword.getText().toLowerCase()));

        itemsList = DeliveryService.getMenu().stream().filter(priceFilter).filter(fatFilter).filter(keywordFilter).filter(sodiumFilter).filter(ratingFilter).filter(nrCaloriesFilter).filter(proteinsFilter).collect(Collectors.toList());
        pageMultiplier = 0;
        updateView(itemsList);
    }


    public void editProductDiag(MenuItem item){
        EditProductAdmin diag = new EditProductAdmin(this,item);
        JFXDialog dialog = new JFXDialog(root, diag, JFXDialog.DialogTransition.CENTER);
        diag.setDiag(dialog);
        dialog.show();
    }

    public void refresh(ActionEvent actionEvent) {
       filterItems(null);
    }

    @FXML
    void newCompositeProduct(ActionEvent event) {
        AdminCompositeProduct diag = new AdminCompositeProduct();
        JFXDialog dialog = new JFXDialog(root, diag, JFXDialog.DialogTransition.CENTER);
        diag.setDiag(dialog);
        dialog.show();

    }
}
