package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.int32_t.BusinessLayer.BaseProduct;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.MenuItem;

import java.io.IOException;

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
        /*TODO read new values and set them*/
        item.setPrice(3);
        adminHome.refresh(null);
        diag.close();
    }

    public void setDiag(JFXDialog dialog) {
        this.diag = dialog;
    }
}
