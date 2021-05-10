module local.simpleSlideshowViewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires static com.github.spotbugs.annotations;
    requires org.slf4j;

    opens local to javafx.fxml;

    exports local;
}
