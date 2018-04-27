package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controllers.Welcome;
import org.scenicview.ScenicView;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/layout/root.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 960, 540);
        primaryStage.setScene(scene);
        primaryStage.show();
        ScenicView.show(scene);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
