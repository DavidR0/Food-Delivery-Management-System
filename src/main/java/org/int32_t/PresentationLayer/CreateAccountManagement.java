package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class CreateAccountManagement {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXCheckBox adminCheck;

    @FXML
    public void initialize(){
        rootPane.setOpacity(0);
        fadeIn();
    }

    private void fadeIn(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public void create(ActionEvent actionEvent) {
        loadLogin(adminCheck.isSelected());
    }

    private void loadLogin(boolean admin){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event1 -> {

            try {
                Stage thisStage = (Stage) rootPane.getScene().getWindow();
                if(admin) {
                    thisStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../PresentationLayer/loginAdmin.fxml")), 1123, 721));
                }else{
                    thisStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../PresentationLayer/loginEmployee.fxml")), 1123, 721));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        fadeTransition.play();
    }

}
