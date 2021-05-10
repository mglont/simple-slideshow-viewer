package local;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        new MainView(primaryStage).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
