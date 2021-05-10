package org.int32_t.PresentationLayer;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.int32_t.BusinessLayer.BaseProduct;
import org.int32_t.BusinessLayer.CompositeProduct;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.Order;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

public class EmployeeHome implements PropertyChangeListener {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox ordersList;

    @FXML
    public void initialize(){
        //Subscribe to order events
        DeliveryService devS = new DeliveryService();
        devS.subscribeListener(this);
        //Play the animation
        rootPane.setOpacity(0);
        fadeIn();
        //Update the view
        updateView();
    }

    private void fadeIn(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        updateView();
    }

    private void updateView(){
        Map<Order, Collection<org.int32_t.BusinessLayer.MenuItem>> orders = new DeliveryService().getOrders();
        List<MenuItemView> itemOrdersList = new LinkedList<>();
        //Update the view with the new changes
        for (Map.Entry<Order, Collection<org.int32_t.BusinessLayer.MenuItem>> entry : orders.entrySet()) {
            for(org.int32_t.BusinessLayer.MenuItem itm : entry.getValue()){
                if(itm.isBase){ //Item is base product
                    BaseProduct base = (BaseProduct) itm;
                    itemOrdersList.add(new MenuItemView(null,entry.getKey(), base,true));
                }else{ //Item is compound product
                    CompositeProduct comp = (CompositeProduct) itm;
                    itemOrdersList.add(new MenuItemView(null,entry.getKey(), comp.getViewElement(),true));
                }

            }
        }
        ordersList.getChildren().setAll(itemOrdersList);
    }

    @FXML
    void logout(ActionEvent event) {
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
        thisStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../PresentationLayer/loginEmployee.fxml")), 1123, 721));
    }
}
