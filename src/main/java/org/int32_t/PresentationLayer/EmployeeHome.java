package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.int32_t.BusinessLayer.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.*;

public class EmployeeHome implements PropertyChangeListener {
    private List<String> colors = new LinkedList<>();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox ordersList;

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
    public void initialize(){
        colors.add("#F1FAEE");
        colors.add("#A8DADC");
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
        Map<Order, Collection<MenuItem>> orders = new DeliveryService().getOrders();
        List<MenuItemView> itemOrdersList = new LinkedList<>();
        int poz = 0;
        //Update the view with the new changes
        for (Map.Entry<Order, Collection<MenuItem>> entry : orders.entrySet()) {
            //Select a random Color for each order
            String color = colors.get(poz%2);
            poz++;

            for(MenuItem itm : entry.getValue()){
                if(itm.isBase){ //Item is base product
                    BaseProduct base = (BaseProduct) itm;
                    MenuItemView panel = new MenuItemView(null,entry.getKey(),null,null, base,true);
                    panel.setStyle("-fx-background-color: " + color + ";");
                    itemOrdersList.add(panel);
                }else{ //Item is compound product
                    CompositeProduct comp = (CompositeProduct) itm;
                    MenuItemView panel = new MenuItemView(null,entry.getKey(),null, null,comp.getViewElement(),true);
                    panel.setStyle("-fx-background-color: " + color + ";");
                    itemOrdersList.add(panel);
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

    @FXML
    void filterItems(ActionEvent event) {

    }
}
