package com.kilo.alt.handler;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Foundry on 12/31/2015.
 */
public abstract class AbstractClientFile implements ClientFile {
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    protected final String name, extension;
    protected final File file;

    public AbstractClientFile() {
        if (!this.getClass().isAnnotationPresent(FileData.class)) {
            throw new RuntimeException("File Manager: ERROR: Class " + this.getClass().getSimpleName() + " is malformed, no FileData annotation found.");
        }
        this.name = this.getClass().getAnnotation(FileData.class).fileName();
        this.extension = this.getClass().getAnnotation(FileData.class).fileType();
        file = new File("Kilo" + File.separator + name + "." + extension);

        logger.log(Level.FINE, "File Manager: File \"" + file.getAbsolutePath() + "\" registered as a config file.");

        try {
            if ((!file.exists() || file.isDirectory()) && file.createNewFile()) {
                //save();
                logger.log(Level.FINE, "File Manager: File \"" + name + "." + extension + "\" was created successfully.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "File Manager: ERROR: File \"" + name + "." + extension + "\" could not be created, a stacktrace was printed.");
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public File getFile() {
        return file;
    }

}
