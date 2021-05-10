package local;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResourceLoaderTest {
    private final ResourceLoader service = new ResourceLoader();

    @Test
    public void shouldLoadExistingResources() {
        Assertions.assertThat(service.load("/samples/1.jpg")).isNotNull();
    }

    @Test
    public void returnsNullIfResourceNotFound() {
        Assertions.assertThat(service.load("dummy")).isNull();
    }
}
