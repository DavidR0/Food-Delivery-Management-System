package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.int32_t.BusinessLayer.*;

import java.io.IOException;

public class MenuItemView extends AnchorPane {

    @FXML
    private Text title;

    @FXML
    private Text subDish;

    @FXML
    private Text Rating;

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

    public MenuItemView(ClientHome currentClient, Order orderKey, MenuItem item, Boolean isOrder){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PresentationLayer/menuItem.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if(isOrder){
            orderBTN.setText("Finish Order");
        }else{
            orderBTN.setText("Add");
        }

        BaseProduct base;
        if(item.isBase){ //Item is base product
            base = (BaseProduct) item;

        }else{ //Item is compound product
            base = ((CompositeProduct)item).getViewElement();
        }

        this.title.setText(base.getTitle());
        Rating.setText("Rating " + String.valueOf(base.getRating()));
        Calories.setText("Calories " + String.valueOf(base.getCalories()));
        Protein.setText("Protein " + String.valueOf(base.getProtein()));
        Fat.setText("Fat " + String.valueOf(base.getFat()));
        Sodium.setText("Sodium " + String.valueOf(base.getSodium()));
        this.price.setText(String.valueOf(base.getPrice()) + "$");

        this.orderKey = orderKey;
        this.isOrder = isOrder;
        this.currentClient = currentClient;
        this.item = item;
    }

    @FXML
    void removeOrder(ActionEvent event) {
        if(isOrder){
            new DeliveryService().removeOrder(orderKey);
        }else{
            //Add to client order
            currentClient.addItem(item);
        }
    }

}