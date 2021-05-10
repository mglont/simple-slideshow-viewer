package local.util;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;

/* wrapper around DirectoryChooser so we can mock file filter dialogues */
public class SimpleDirectoryChooser {
    private final DirectoryChooser folderChooser = new DirectoryChooser();

    public File showDialogue(final Window window) {
        return folderChooser.showDialog(window);
    }

    public void setInitialDirectory(final File value) {
     folderChooser.setInitialDirectory(value);
    }

    public File getInitialDirectory() {
        return folderChooser.getInitialDirectory();
    }
}
