package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.int32_t.BusinessLayer.DeliveryService;
import org.int32_t.BusinessLayer.MenuItem;

import java.io.IOException;

/**
 * class that creates a simple view of an menu item
 */
public class SimpleProductView extends AnchorPane {

    @FXML
    private Text text;

    @FXML
    private JFXButton btn;

    private AdminCompositeProduct prd;
    private MenuItem item;
    private String btnText;

    public SimpleProductView(AdminCompositeProduct prd, MenuItem item, String btnText) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PresentationLayer/simpleProductView.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        this.prd = prd;
        this.item = item;
        this.btnText = btnText;
        text.setText(item.getTitle());
        btn.setText(btnText);

    }

    @FXML
    public void initialize(){}



    @FXML
    void btnPress(ActionEvent event) {
        if(btn.getText().equals("Add")){
            prd.add(item);
        }

        if(btn.getText().equals("Remove")){
            prd.remove(item);
        }
    }

}