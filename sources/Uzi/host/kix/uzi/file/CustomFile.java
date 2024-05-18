package host.kix.uzi.file;

import host.kix.uzi.Uzi;

import java.io.File;

/**
 * Created by Kix on 6/4/2017.
 * Made for the eclipse project.
 */
public abstract class CustomFile {

    protected final File file;
    private final String name;

    public CustomFile(String name) {
        this.name = name;
        file = new File(Uzi.getInstance().getDirectory(), name + ".txt");
        if (!file.exists()) {
            saveFile();
        }
    }

    public final File getFile() {
        return file;
    }

    public final String getName() {
        return name;
    }

    public abstract void loadFile();

    public abstract void saveFile();

}
