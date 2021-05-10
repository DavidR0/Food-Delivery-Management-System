package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class FinalizeOrderDialog extends AnchorPane {

    JFXDialog diag;

    public FinalizeOrderDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../PresentationLayer/finalizeOrderDialog.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setDiag(JFXDialog d){
        diag = d;
    }


    @FXML
    void close(ActionEvent event) {
        diag.close();
    }

}
