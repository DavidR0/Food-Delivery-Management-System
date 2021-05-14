package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.int32_t.BusinessLayer.BaseProduct;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.MenuItem;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FinalizeOrderDialog extends AnchorPane {

    JFXDialog diag;
    private int clientID;
    private Collection<MenuItem> list;

    @FXML
    private VBox ordersList;
    @FXML
    private Label OrderTotalText;

    public FinalizeOrderDialog(Collection<MenuItem> list, int clientID) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PresentationLayer/finalizeOrderDialog.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //Load the products from the cart
        updateView(list);

        this.clientID = clientID;
        this.list = list;

    }

    public void setDiag(JFXDialog d){
        diag = d;
    }

    public void updateView(Collection<MenuItem> orders){
        int totalPrice = 0;
        List<MenuItemView> itemOrdersList = new LinkedList<>();
        //Update the view with the new changes
        for (MenuItem entry : orders) {
            BaseProduct base = (BaseProduct) entry;
            totalPrice += base.computePrice();
            itemOrdersList.add(new MenuItemView(null,null,null,orders, this, base,true));
        }
        ordersList.getChildren().setAll(itemOrdersList);
        OrderTotalText.setText("Order Total: $" + totalPrice);
    }

    @FXML
    void createOrder(ActionEvent event) {
        if(!list.isEmpty()) {
            new DeliveryService().createOrder(list, clientID);
        }
        list.clear();
        diag.close();
    }

    @FXML
    void close(ActionEvent event) {
        diag.close();
    }

}
