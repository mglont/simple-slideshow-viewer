package local;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;

@Getter
public class MainView {
    public static final String APP_TITLE = "Simplistic Slideshow Viewer";
    private final ResourceLoader resourceLoader = new ResourceLoader();

    private final URL layoutUrl = resourceLoader.load("/layouts/home.fxml");
    private final Stage primaryStage;

    public MainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle(APP_TITLE);

        // pass the reference to the primary stage to the controller instance
        var controller = new Controller();
        try {
            var loader = new FXMLLoader(layoutUrl);
            loader.setController(controller);
            controller.setPrimaryStage(primaryStage);

            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new IllegalStateException("Fatal error: Could not load " + layoutUrl, e);
        }
        primaryStage.setMaximized(true);
    }

    public void show() {
        primaryStage.show();
    }
}
