package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.viewModel.MainController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController controller = new MainController();
        primaryStage.setTitle("Gilad schweiger and Adi hahamov - Flight Gear Simulator");
        primaryStage.setScene(new Scene(controller, 1123, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
