package local;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import local.util.SimpleDirectoryChooser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
public class MainViewTest {
    private final File SAMPLES_FOLDER =
        Paths.get("src", "main", "resources", "samples").toFile();
    private Stage primaryStage;

    @Start
    private void start(Stage stage)  {
        primaryStage = stage;
        new MainView(stage).show();
    }

    @Test
    void shouldDisplayMainWindow(FxRobot robot) {
        assertThat(robot.listWindows().size()).isEqualTo(1);
        assertThat(primaryStage).isFocused();
        assertThat(robot.lookup("#centrePane").queryAs(AnchorPane.class))
            .hasChild("#pages");
    }

    @Test
    void shouldSelectImages(FxRobot robot) {
        var mockChooser = mock(SimpleDirectoryChooser.class);
        when(mockChooser.showDialogue(any(Stage.class)))
            .thenReturn(SAMPLES_FOLDER);
        setDirectoryChooser(mockChooser);
        robot.clickOn("#openBtn");

        var actual = robot.lookup(".photo").queryAllAs(ImageView.class);

        assertThat(actual).hasSize(1);
    }

    private void setDirectoryChooser(SimpleDirectoryChooser mockDirectoryChooser) {
        try {
            var field = Controller.class.getDeclaredField("folderChooser");
            field.setAccessible(true);
            field.set(null, mockDirectoryChooser);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Could not wire DirectoryChooser mock", e);
        }
    }
}
