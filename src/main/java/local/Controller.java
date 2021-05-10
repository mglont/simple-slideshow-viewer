package local;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import local.util.SimpleDirectoryChooser;
import local.util.SimpleThreadFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

@Slf4j
public class Controller {
    public static final int SLIDESHOW_TRANSITION_DURATION = 3; // seconds
    @Setter
    @Getter
    private Stage primaryStage;
    // use static, but not final, so we can wire the mock without having to resort to DI
    private static SimpleDirectoryChooser folderChooser = new SimpleDirectoryChooser();

    private final ObservableList<NamedImage> photos = FXCollections.observableArrayList();
    private final SlideshowService slideshowService = new SlideshowService();
    @FXML
    private ProgressBar folderLoadedProgress;

    @FXML
    private Pagination pages;

    private SlideshowMode slideshowMode = SlideshowMode.START;

    @FXML
    private HBox slideshowControls;
    @FXML
    private Button slideshowBtn;
    @FXML // TODO
    private Button cancelSlideshowBtn;
    @FXML // TODO
    private Button restartSlideshowBtn;

    @FXML
    public void initialize() {
        pages.pageCountProperty().addListener(((observable, oldValue, newValue) -> {
            slideshowService.updateLimit(newValue.intValue());
        }));
        pages.currentPageIndexProperty().addListener(((observable, oldValue, newValue) -> {
            slideshowService.updateIndex(newValue.intValue());
        }));
        slideshowBtn.setText(slideshowMode.getToggleActionText());
        slideshowService.setPeriod(Duration.seconds(SLIDESHOW_TRANSITION_DURATION));
        slideshowService.setOnSucceeded(t -> {
            Integer value = (Integer) t.getSource().getValue();
            log.info("Render picture {}", value);
            pages.setCurrentPageIndex(value);
        });
        pages.setPageFactory(this::renderImagePage);

        AnchorPane.setTopAnchor(pages, 10d);
        AnchorPane.setRightAnchor(pages, 10d);
        AnchorPane.setLeftAnchor(pages, 10d);
        AnchorPane.setBottomAnchor(pages, 20d);
    }

    private ImageView renderImagePage(Integer page) {
        if (pages.isDisabled()) return null;
        NamedImage namedImage = photos.get(page);
        var view = new ImageView(namedImage.getImage());
        view.getStyleClass().add("photo");
        view.setCache(true);
        view.setSmooth(true);
        view.setEffect(new DropShadow());
        primaryStage.setTitle(namedImage.getName());
        return view;
    }

    @FXML
    public void handleSlideshowAction(ActionEvent event) {
        if (slideshowMode.isStart()) {
            slideshowService.start();
        } else {
            slideshowService.cancel();
            slideshowService.reset();
        }
        slideshowMode = slideshowMode.toggle();
        slideshowBtn.setText(slideshowMode.getToggleActionText());
    }

    @FXML
    public void openButtonClicked(ActionEvent event) {

        var selected = folderChooser.showDialogue(primaryStage);
        if (null == selected) {
            log.debug("Folder selection cancelled");
            return;
        }
        photos.clear();
        pages.setDisable(false);
        var task = new FolderImagesTask(selected.toPath());
        log.info("Folder {} selected.", selected);
        task.setOnSucceeded(taskDoneEvent -> processImages(selected, taskDoneEvent));
        Thread thread = SimpleThreadFactory.newDaemonThread(task, "FolderImagesTask");
        folderLoadedProgress.setVisible(true);
        folderLoadedProgress.progressProperty().bind(task.progressProperty());

        thread.start();
        log.info("Started worker thread for folder {}", selected);
    }

    private void processImages(File selected, WorkerStateEvent taskDoneEvent) {
        log.info("Finished processing images from {}", selected);
        folderLoadedProgress.setVisible(false);
        Collection<Path> jpgs = getImagesFromTaskCompletionEvent(taskDoneEvent);
        if (jpgs.isEmpty()) {
            log.info("No JPEG pictures found in {}", selected);
            pages.setCurrentPageIndex(0);
            pages.setPageCount(0);
            pages.setVisible(false);
            slideshowControls.setVisible(false);
        } else {
            loadImages(jpgs);
            pages.setCurrentPageIndex(0);
            pages.setPageCount(jpgs.size());
            pages.setVisible(true);
            slideshowControls.setVisible(true);
        }
    }

    private void loadImages(Collection<Path> paths) {
        Bounds bounds = pages.getBoundsInLocal();
        paths.stream()
            // load images in the background
            .map(p -> new NamedImage(p, bounds))
            .forEach(photos::add);
    }

    @SuppressWarnings("unchecked")
    private Collection<Path> getImagesFromTaskCompletionEvent(WorkerStateEvent taskDoneEvent) {
        return (Collection<Path>) taskDoneEvent.getSource().getValue();
    }
}
