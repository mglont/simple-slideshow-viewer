package local;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import lombok.Data;

import java.nio.file.Path;
import java.util.Optional;

@Data
public class NamedImage {
    public static final double SCALE_FACTOR = 0.9;
    private final String name;
    private final Image image;

    public NamedImage(Path path, Bounds bounds) {
        name = Optional.ofNullable(path.getFileName())
            .orElseThrow(() -> new IllegalArgumentException(path.toString()))
            .toString();
        this.image = loadImage(path, bounds);
    }

    private static Image loadImage(Path p, Bounds bounds) {
        return new Image(p.toUri().toString(),
            bounds.getWidth() * SCALE_FACTOR,
            bounds.getHeight() * SCALE_FACTOR,
            true, true, true);
    }
}
