package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.scenicview.ScenicView;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/layout/screen/root.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
//        primaryStage.setMaximized(true);
        ScenicView.show(scene);
        primaryStage.show();


    }

    @Override
    public void stop() throws Exception {
        super.stop();
        IMatDataHandler.getInstance().shutDown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
