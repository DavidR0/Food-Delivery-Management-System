package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.int32_t.BusinessLayer.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ClientHome {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox productsList;

    @FXML
    private StackPane root;

    private Collection<MenuItem> currentOrder = new LinkedList<>();

    @FXML
    public void initialize(){
        updateView(DeliveryService.getMenu());
        rootPane.setOpacity(0);
        fadeIn();
    }

    private void updateView(List<MenuItem> orders){

        List<MenuItemView> itemOrdersList = new LinkedList<>();
        //Update the view with the new changes
        for (MenuItem entry : orders) {
                if(entry.isBase){ //Item is base product
                    BaseProduct base = (BaseProduct) entry;
                    itemOrdersList.add(new MenuItemView(this,null, entry,false));
                }else{ //Item is compound product
                    CompositeProduct comp = (CompositeProduct) entry;
                    BaseProduct base = comp.getViewElement();
                    itemOrdersList.add(new MenuItemView(this,null, base,false));
                }
        }
        productsList.getChildren().setAll(itemOrdersList);
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

        FinalizeOrderDialog diag = new FinalizeOrderDialog();
        JFXDialog dialog = new JFXDialog(root, diag, JFXDialog.DialogTransition.CENTER);
        dialog.show();
        diag.setDiag(dialog);

//        DeliveryService devS = new DeliveryService();
//        devS.createOrder(currentOrder,0);
    }
}
