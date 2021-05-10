package local.util;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Window;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScreenUtils {
    // take up 75% of the screen
    public static final double PREFERRED_WINDOW_SCREEN_RATIO = 0.75;
    // ensure the window is centred on the main screen it's rendered on
    public static final double MIN_WINDOW_DENOMINATOR = 8.0;

    public static Rectangle2D getPreferredWindowSize() {
        var bounds = Screen.getPrimary().getVisualBounds();
        return new Rectangle2D(bounds.getWidth() / MIN_WINDOW_DENOMINATOR,
            bounds.getHeight() / MIN_WINDOW_DENOMINATOR,
            bounds.getWidth() * PREFERRED_WINDOW_SCREEN_RATIO,
            bounds.getHeight() * PREFERRED_WINDOW_SCREEN_RATIO);
    }

    public static void resizeWindow(Window window) {
        var dimensions = getPreferredWindowSize();

        window.setX(dimensions.getMinX());
        window.setY(dimensions.getMinY());
        window.setWidth(dimensions.getWidth());
        window.setHeight(dimensions.getHeight());

    }
}
