package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Screen.OrderHistory;
import org.scenicview.ScenicView;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class Demo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        System.out.println(IMatDataHandler.getInstance().getProducts().size());
//        Parent root = FXMLLoader.load(getClass().getResource("/layout/screen/sample.fxml"));
        Parent root = new OrderHistory(IMatDataHandler.getInstance().getOrders());
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        ScenicView.show(root);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
