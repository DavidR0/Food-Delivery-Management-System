package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.int32_t.BusinessLayer.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;

public class MenuItemView extends AnchorPane {

    @FXML
    private Text title;

    @FXML
    private Text subDish;

    @FXML
    private Text Rating;

    @FXML
    private Text ClientID;

    @FXML
    private Text Calories;

    @FXML
    private Text Protein;

    @FXML
    private Text Fat;

    @FXML
    private Text Sodium;

    @FXML
    private JFXButton orderBTN;

    @FXML
    private Text price;

    private Order orderKey;
    private boolean isOrder;
    private ClientHome currentClient;
    private MenuItem item;
    private Collection<MenuItem> cartList;
    private FinalizeOrderDialog diag;

    public MenuItemView(ClientHome currentClient, Order orderKey, Collection<MenuItem> cartList, FinalizeOrderDialog diag,MenuItem item, Boolean isOrder) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PresentationLayer/menuItem.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //Set the button text according to the context it is used in
        if (isOrder && cartList == null) {
            orderBTN.setText("Finish Order");
        } else if (!isOrder) {
            orderBTN.setText("Add");
        } else {
            orderBTN.setText("Remove");
        }

        if(orderKey != null){ //Set the client ID Text
            ClientID.setText("Client ID: " +orderKey.getClientID());
        }else{
            ClientID.setText("");
        }

        BaseProduct base;
        if (item.isBase) { //Item is base product
            base = (BaseProduct) item;

        } else { //Item is compound product
            base = ((CompositeProduct) item).convertToBase();
        }

        //Set the objects text
        this.title.setText(base.getTitle());
        DecimalFormat numberFormat = new DecimalFormat("#.00");

        Rating.setText("Rating " + numberFormat.format(base.getRating()));
        Calories.setText("Calories " + String.valueOf(base.getCalories()));
        Protein.setText("Protein " + String.valueOf(base.getProtein()));
        Fat.setText("Fat " + String.valueOf(base.getFat()));
        Sodium.setText("Sodium " + String.valueOf(base.getSodium()));
        this.price.setText(String.valueOf(base.getPrice()) + "$");

        this.orderKey = orderKey;
        this.isOrder = isOrder;
        this.currentClient = currentClient;
        this.item = item;
        this.cartList = cartList;
        this.diag = diag;
    }

    @FXML
    void removeOrder(ActionEvent event) {
        if(cartList != null){
            //Remove from client shopping cart
            cartList.remove(item);
            diag.updateView(cartList);
        }else if(isOrder){
            //Let the employee complete the order
            new DeliveryService().removeOrder(orderKey);
        }else{
            //Add to client shopping cart
            currentClient.addItem(item);
        }
    }

}