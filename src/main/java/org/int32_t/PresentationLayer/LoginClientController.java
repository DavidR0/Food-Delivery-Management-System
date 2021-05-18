package org.int32_t.PresentationLayer;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.int32_t.BusinessLayer.User;
import org.int32_t.BusinessLayer.UserManagement;

import java.io.IOException;
import java.util.Objects;

/**
 * Class that handles the client login home page
 */
public class LoginClientController {

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField password;

    @FXML
    private AnchorPane rootPane;

    @FXML
    void createAccount(ActionEvent event) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(500));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(event1 -> {

            try {
                loadCreateAccount();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        fadeTransition.play();
    }

    @FXML
    void signIn(ActionEvent event) {

        User user = new User(userName.getText(), password.getText(), 2);

        if(new UserManagement().validateLogin(user)) {

            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.millis(500));
            fadeTransition.setNode(rootPane);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setOnFinished(event1 -> {
                try {
                    loadSignIn();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            fadeTransition.play();
        }
    }

    private void loadSignIn() throws IOException {
        Stage thisStage = (Stage) rootPane.getScene().getWindow();
        thisStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../PresentationLayer/clientHome.fxml"))), 1123, 721));
    }

    private void loadCreateAccount() throws IOException {
        Stage thisStage = (Stage) rootPane.getScene().getWindow();
        thisStage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../PresentationLayer/createAccountClient.fxml"))), 1123, 721));
    }
}
