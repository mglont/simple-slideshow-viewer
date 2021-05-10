package local;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@NoArgsConstructor
@Slf4j
public class ResourceLoader {

    public URL load(String fullyQualifiedName) {
        URL resource = getClass().getResource(fullyQualifiedName);
        if (null == resource) {
            var path = Paths.get("src", "main", "resources", fullyQualifiedName);
            if (Files.isRegularFile(path)) {
                try {
                    resource = path.toUri().toURL();
                } catch (MalformedURLException ignore) {
                    log.warn("Could not find resource {}", fullyQualifiedName);
                }
            }
        }

        return resource;
    }
}
