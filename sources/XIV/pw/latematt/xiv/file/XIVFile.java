package pw.latematt.xiv.file;

import pw.latematt.xiv.XIV;

import java.io.File;
import java.io.IOException;

/**
 * @author Matthew
 */
public abstract class XIVFile {
    public static final File XIV_DIRECTORY = new File(System.getProperty("user.home") + File.separator + "XIV");
    protected final String name, extension;
    protected final File file;

    public XIVFile(String name, String extension) {
        this.name = name;
        this.extension = extension;
        file = new File(XIV_DIRECTORY + File.separator + name + "." + extension);

        XIV.getInstance().getLogger().info("File \"" + file.getAbsolutePath() + "\" registered as a config file.");

        try {
            if ((!file.exists() || file.isDirectory()) && file.createNewFile()) {
                save();
                XIV.getInstance().getLogger().info("File \"" + name + "." + extension + "\" was created successfully.");
            }
        } catch (IOException e) {
            XIV.getInstance().getLogger().warn("File \"" + name + "." + extension + "\" could not be created, a stacktrace was printed.");
            e.printStackTrace();
        }

        XIV.getInstance().getFileManager().getContents().add(this);
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public File getFile() {
        return file;
    }

    public abstract void load() throws IOException;

    public abstract void save() throws IOException;
}
