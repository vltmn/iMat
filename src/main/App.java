package main;

import javafx.application.Application;
import javafx.application.Platform;
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
        primaryStage.setTitle("iMat");
        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        ScenicView.show(scene);
        primaryStage.show();

        primaryStage.onCloseRequestProperty().setValue(event -> Platform.exit());


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
