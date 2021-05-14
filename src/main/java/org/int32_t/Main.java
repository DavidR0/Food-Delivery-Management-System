package org.int32_t;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Admin window
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PresentationLayer/loginAdmin.fxml"))); //Loads the UI
        primaryStage.setTitle("Admin");
        Scene scene = new Scene(root, 1123, 721);
        primaryStage.setScene(scene);
        primaryStage.show();


//        Client window
        Stage secondStage = new Stage();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PresentationLayer/loginClient.fxml"))); //Loads the UI
        secondStage.setTitle("Client");
        scene = new Scene(root, 1123, 721);
        secondStage.setScene(scene);
        secondStage.show();

        //second Client window
//        Stage secondStage2 = new Stage();
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PresentationLayer/loginClient.fxml"))); //Loads the UI
//        secondStage2.setTitle("Client 2");
//        scene = new Scene(root, 1123, 721);
//        secondStage2.setScene(scene);
//        secondStage2.show();

        //employee window
        Stage thirdStage = new Stage();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PresentationLayer/loginEmployee.fxml"))); //Loads the UI
        thirdStage.setTitle("Employee");
        scene = new Scene(root, 1123, 721);
        thirdStage.setScene(scene);
        thirdStage.show();

    }

    public static void main(String[] args)  {
        launch(args);
    }
}
