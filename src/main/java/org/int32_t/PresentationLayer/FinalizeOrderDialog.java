package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FinalizeOrderDialog extends AnchorPane {

    JFXDialog diag;
    private int clientID;
    private Collection<MenuItem> list;

    @FXML
    private VBox ordersList;

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

        List<MenuItemView> itemOrdersList = new LinkedList<>();
        //Update the view with the new changes
        for (MenuItem entry : orders) {
            if(entry.isBase){ //Item is base product
                BaseProduct base = (BaseProduct) entry;
                itemOrdersList.add(new MenuItemView(null,null,orders, this, base,true));
            }else{ //Item is compound product
                CompositeProduct comp = (CompositeProduct) entry;
                BaseProduct base = comp.getViewElement();
                itemOrdersList.add(new MenuItemView(null,null,orders, this ,base,true));
            }
        }
        ordersList.getChildren().setAll(itemOrdersList);
    }

    @FXML
    void createOrder(ActionEvent event) {
        new DeliveryService().createOrder(list, clientID);
        list.clear();
        diag.close();
    }

    @FXML
    void close(ActionEvent event) {
        diag.close();
    }

}
