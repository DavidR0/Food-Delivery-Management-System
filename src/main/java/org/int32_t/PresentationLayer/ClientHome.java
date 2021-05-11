package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class ClientHome {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox productsList;

    @FXML
    private StackPane root;

    private Collection<MenuItem> currentOrder = new LinkedList<>();
    private List<MenuItem> itemsList = new LinkedList<>();
    private int clientID = abs(new Random().nextInt());
    int pageMultiplier = 0;
    int nrElementsPerPage = 10;


    @FXML
    public void initialize(){
        itemsList.clear();
        itemsList = DeliveryService.getMenu();
        updateView(itemsList);
        rootPane.setOpacity(0);
        fadeIn();
    }

    private void updateView(List<MenuItem> orders){

        List<MenuItemView> filterItemsList = new LinkedList<>();
        //Update the view with the new changes
        for (int i = pageMultiplier*nrElementsPerPage ; i < (pageMultiplier + 1)*nrElementsPerPage && i < itemsList.size(); ++i) {
            MenuItem entry = orders.get(i);
                if(entry.isBase){ //Item is base product
                    BaseProduct base = (BaseProduct) entry;
                    filterItemsList.add(new MenuItemView(this,null,null,null, base,false));
                }else{ //Item is compound product
                    CompositeProduct comp = (CompositeProduct) entry;
                    BaseProduct base = comp.getViewElement();
                    filterItemsList.add(new MenuItemView(this,null,null, null,base,false));
                }
        }
        productsList.getChildren().setAll(filterItemsList);
    }

    private void fadeIn(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void addItem(MenuItem itm){
        currentOrder.add(itm);
    }

    @FXML
    void viewOrder(ActionEvent event) {
        FinalizeOrderDialog diag = new FinalizeOrderDialog(currentOrder,clientID);
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
        if(pageMultiplier*(nrElementsPerPage-1) < itemsList.size()) {
            pageMultiplier++;
            updateView(itemsList);
        }
    }

    @FXML
    void previousPage(ActionEvent event) {
        if(pageMultiplier >= 1){
            pageMultiplier--;
            updateView(itemsList);
        }
    }


}
