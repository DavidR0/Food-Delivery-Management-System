package org.int32_t.PresentationLayer;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class CreateAccountClient {

    @FXML
    private AnchorPane rootPane;

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

    public void createAccount(ActionEvent actionEvent) {
        loadLogin();

    }

    private void loadLogin(){
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event1 -> {

            try {
                Stage thisStage = (Stage) rootPane.getScene().getWindow();
                thisStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../PresentationLayer/loginClient.fxml")), 1123, 721));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        fadeTransition.play();
    }


}
