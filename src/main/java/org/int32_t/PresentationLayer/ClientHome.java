package org.int32_t.PresentationLayer;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.int32_t.BusinessLayer.BaseProduct;
import org.int32_t.BusinessLayer.CompositeProduct;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.MenuItem;

import java.util.LinkedList;
import java.util.List;

public class ClientHome {
    @FXML
    private AnchorPane rootPane;

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
    void placedOrder(ActionEvent event) {
        List<MenuItem> products = new LinkedList<>();
        products.add(new BaseProduct(5,10,2,6,80,12,"Title 1"));
        products.add(new BaseProduct(15,110,42,26,580,122,"Title 2"));
        CompositeProduct item = new CompositeProduct(products,"Comp 1");
        products.add(item);

        DeliveryService devS = new DeliveryService();
        devS.createOrder(products,0);
    }
}
