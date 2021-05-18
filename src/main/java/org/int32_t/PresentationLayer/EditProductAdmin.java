package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.MenuItem;

import java.io.IOException;

/**
 * Class that handles the edit product page from the admin
 */
public class EditProductAdmin extends AnchorPane {

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

    @FXML
    private JFXButton btn;


    JFXDialog diag;
    private MenuItem item;
    private AdminHome adminHome;

    public EditProductAdmin(AdminHome adminHome, MenuItem item) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PresentationLayer/editProductAdmin.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.adminHome = adminHome;
        this.item = item;
    }

    @FXML
    void edit(ActionEvent event) {
        if(!title.getText().isEmpty()){
            item.setTitle(title.getText());
        }

        if(!rating.getText().isEmpty()){
            item.setRating(Float.parseFloat(rating.getText()));
        }

        if(!calories.getText().isEmpty()){
            item.setCalories(Integer.parseInt(calories.getText()));
        }

        if(!protein.getText().isEmpty()){
            item.setProtein(Integer.parseInt(protein.getText()));
        }

        if(!fat.getText().isEmpty()){
            item.setFat(Integer.parseInt(fat.getText()));
        }

        if(!price.getText().isEmpty()){
            item.setPrice(Integer.parseInt(price.getText()));
        }

        if(!sodium.getText().isEmpty()){
            item.setSodium(Integer.parseInt(sodium.getText()));
        }

        adminHome.refresh(null);
        diag.close();
    }

    @FXML
    void delete(ActionEvent event) {
        DeliveryService buff = new DeliveryService();
        buff.deleteFromMenu(item);
        adminHome.refresh(null);
        diag.close();
    }

    public void setDiag(JFXDialog dialog) {
        this.diag = dialog;
    }
}
