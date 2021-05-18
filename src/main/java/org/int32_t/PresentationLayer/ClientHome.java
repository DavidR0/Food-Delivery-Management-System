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
import org.int32_t.BusinessLayer.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * Class that handles the client home page
 */
public class ClientHome {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox productsList;

    @FXML
    private StackPane root;


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

    private Collection<MenuItem> currentOrder = new LinkedList<>();
    private List<MenuItem> itemsList = new LinkedList<>();
    private List<MenuItem> filterItemsList = new LinkedList<>();
    private int clientID = abs(new Random().nextInt());
    int pageMultiplier = 0;
    int nrElementsPerPage = 10;


    @FXML
    public void initialize() {
        itemsList.clear();
        filterItemsList.clear();
        filterItemsList.addAll(DeliveryService.getMenu());
        itemsList.addAll(DeliveryService.getMenu());
        updateView(itemsList);
        rootPane.setOpacity(0);
        fadeIn();
    }

    private void updateView(List<MenuItem> orders) {

        List<MenuItemView> filterItemsListView = new LinkedList<>();
        //Update the view with the new changes
        for (int i = pageMultiplier * nrElementsPerPage; i < (pageMultiplier + 1) * nrElementsPerPage && i < orders.size(); ++i) {
            MenuItem entry = orders.get(i);
            BaseProduct base = (BaseProduct) entry;
            filterItemsListView.add(new MenuItemView(null,this, null, null, null, base, false));
        }
        productsList.getChildren().setAll(filterItemsListView);
    }

    private void fadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void addItem(MenuItem itm) {
        currentOrder.add(itm);
    }

    @FXML
    void viewOrder(ActionEvent event) {
        FinalizeOrderDialog diag = new FinalizeOrderDialog(currentOrder, clientID);
        JFXDialog dialog = new JFXDialog(root, diag, JFXDialog.DialogTransition.CENTER);
        dialog.show();
        diag.setDiag(dialog);
    }

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
        thisStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../PresentationLayer/loginClient.fxml")), 1123, 721));
    }

    @FXML
    void nextPage(ActionEvent event) {
        if (pageMultiplier * (nrElementsPerPage - 1) < itemsList.size()) {
            pageMultiplier++;
            updateView(filterItemsList);
        }
    }

    @FXML
    void previousPage(ActionEvent event) {
        if (pageMultiplier >= 1) {
            pageMultiplier--;
            updateView(filterItemsList);
        }
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

        filterItemsList = itemsList.stream().filter(priceFilter).filter(fatFilter).filter(keywordFilter).filter(sodiumFilter).filter(ratingFilter).filter(nrCaloriesFilter).filter(proteinsFilter).collect(Collectors.toList());
        pageMultiplier = 0;
        updateView(filterItemsList);
    }

    public void refresh(ActionEvent actionEvent) {
        itemsList.addAll(DeliveryService.getMenu());
        filterItems(null);

    }
}