package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.int32_t.BusinessLayer.BaseProduct;
import org.int32_t.BusinessLayer.DeliveryService;

import java.io.IOException;

public class NewProductAdmin extends AnchorPane {

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField rating;

    @FXML
    private JFXTextField calories;

    @FXML
    private JFXTextField protein;

    @FXML
    private JFXTextField fat;

    @FXML
    private JFXTextField price;

    @FXML
    private JFXTextField sodium;

    JFXDialog diag;


    public NewProductAdmin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PresentationLayer/newProductAdmin.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void AddItem(ActionEvent event) {
        if(!title.getText().isEmpty() && !rating.getText().isEmpty() && !calories.getText().isEmpty() && !protein.getText().isEmpty() && !fat.getText().isEmpty() && !sodium.getText().isEmpty()) {
            BaseProduct prd = new BaseProduct(Float.parseFloat(rating.getText()),Integer.parseInt(calories.getText()),Integer.parseInt(protein.getText()),Integer.parseInt(fat.getText()),Integer.parseInt(sodium.getText()),Integer.parseInt(price.getText()),title.getText());
            DeliveryService buff = new DeliveryService();
            buff.addToMenu(prd);
            diag.close();
        }
    }

    public void setDiag(JFXDialog dialog) {
        this.diag = dialog;
    }
}
