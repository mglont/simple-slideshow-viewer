package local;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SlideshowMode {
    START("Start Slideshow"), STOP("Exit slideshow");

    private final String toggleActionText;

    public boolean isStart() {
        return this == START;
    }

    public SlideshowMode toggle() {
        return isStart() ? STOP : START;
    }
}
