package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.Order;

import java.io.IOException;

public class MenuItem extends AnchorPane {

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

    public MenuItem(Order orderKey, String title, String subDishes, String rating, String calories, String protein, String fat, String sodium, String price, Boolean isOrder){
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

        this.title.setText(title);
        subDish.setText(subDishes);
        Rating.setText(rating);
        Calories.setText(calories);
        Protein.setText(protein);
        Fat.setText(fat);
        Sodium.setText(sodium);
        this.price.setText(price);
        this.orderKey = orderKey;
    }


    @FXML
    void removeOrder(ActionEvent event) {
        new DeliveryService().removeOrder(orderKey);
    }

}