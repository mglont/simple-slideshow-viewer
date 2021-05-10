package local;

import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.TreeSet;

@RequiredArgsConstructor
@Slf4j
public class FolderImagesTask extends Task<Collection<Path>> {
    public static final String JPEG_GLOB = "*.{jpg,jpeg,JPG,JPEG}";
    public static final String JPEG_MIME_TYPE = "image/jpeg";
    public static final String DEFAULT_MIME_TYPE = "application/octet-stream";

    private final Path folder;

    @Override
    protected Collection<Path> call() throws Exception {
        TreeSet<Path> files = new TreeSet<>();
        final long fileCount = Files.list(folder)
            .count();
        long current = 0;
        try (DirectoryStream<Path> images = Files.newDirectoryStream(folder, JPEG_GLOB)) {
            for (Path p: images) {
                updateProgress(++current, fileCount);
                log.info("Processing file {}", p);
                String contentTypeHint = Optional.ofNullable(Files.probeContentType(p))
                    .orElse(DEFAULT_MIME_TYPE);
                if (contentTypeHint.equals(JPEG_MIME_TYPE)) {
                    files.add(p);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not look for images in " + folder, e);
        }
        updateProgress(fileCount, fileCount);
        log.info("Found {} images in {}", files.size(), folder);
        return files;
    }
}
