package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class Demo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        System.out.println(IMatDataHandler.getInstance().getProducts().size());
        Parent root = FXMLLoader.load(getClass().getResource("/layout/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 960, 540));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
