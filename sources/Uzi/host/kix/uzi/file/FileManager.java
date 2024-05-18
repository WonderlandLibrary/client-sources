package host.kix.uzi.file;

import host.kix.uzi.file.files.ModInfo;
import host.kix.uzi.management.ListManager;
import host.kix.uzi.ui.alt.SavedAlts;

import java.io.FileNotFoundException;
import java.io.WriteAbortedException;
import java.nio.file.FileAlreadyExistsException;

/**
 * Created by Kix on 6/4/2017.
 * Made for the eclipse project.
 */
public class FileManager extends ListManager<CustomFile> {

    public FileManager() {
        addContent(new ModInfo());
        addContent(new SavedAlts());
    }

    public void load() throws FileNotFoundException {
        getContents()
                .forEach(CustomFile::loadFile);
    }

    public void save() {
        getContents()
                .forEach(CustomFile::saveFile);
    }

    public final CustomFile find(String name) {
        if (getContents() == null)
            return null;
        for (final CustomFile file : getContents()) {
            if (file.getName().equalsIgnoreCase(name))
                return file;
        }
        return null;
    }

}
