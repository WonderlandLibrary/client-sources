/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;

public class FileRenameAction
extends AbstractAction {
    private final File source;
    private final File destination;
    private final boolean renameEmptyFiles;

    public FileRenameAction(File file, File file2, boolean bl) {
        this.source = file;
        this.destination = file2;
        this.renameEmptyFiles = bl;
    }

    @Override
    public boolean execute() {
        return FileRenameAction.execute(this.source, this.destination, this.renameEmptyFiles);
    }

    public File getDestination() {
        return this.destination;
    }

    public File getSource() {
        return this.source;
    }

    public boolean isRenameEmptyFiles() {
        return this.renameEmptyFiles;
    }

    public static boolean execute(File file, File file2, boolean bl) {
        if (bl || file.length() > 0L) {
            File file3 = file2.getParentFile();
            if (file3 != null && !file3.exists()) {
                file3.mkdirs();
                if (!file3.exists()) {
                    LOGGER.error("Unable to create directory {}", (Object)file3.getAbsolutePath());
                    return true;
                }
            }
            try {
                try {
                    Files.move(Paths.get(file.getAbsolutePath(), new String[0]), Paths.get(file2.getAbsolutePath(), new String[0]), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.trace("Renamed file {} to {} with Files.move", (Object)file.getAbsolutePath(), (Object)file2.getAbsolutePath());
                    return true;
                } catch (IOException iOException) {
                    LOGGER.error("Unable to move file {} to {}: {} {}", (Object)file.getAbsolutePath(), (Object)file2.getAbsolutePath(), (Object)iOException.getClass().getName(), (Object)iOException.getMessage());
                    boolean bl2 = file.renameTo(file2);
                    if (!bl2) {
                        try {
                            Files.copy(Paths.get(file.getAbsolutePath(), new String[0]), Paths.get(file2.getAbsolutePath(), new String[0]), StandardCopyOption.REPLACE_EXISTING);
                            try {
                                Files.delete(Paths.get(file.getAbsolutePath(), new String[0]));
                                LOGGER.trace("Renamed file {} to {} using copy and delete", (Object)file.getAbsolutePath(), (Object)file2.getAbsolutePath());
                            } catch (IOException iOException2) {
                                LOGGER.error("Unable to delete file {}: {} {}", (Object)file.getAbsolutePath(), (Object)iOException2.getClass().getName(), (Object)iOException2.getMessage());
                                try {
                                    new PrintWriter(file.getAbsolutePath()).close();
                                    LOGGER.trace("Renamed file {} to {} with copy and truncation", (Object)file.getAbsolutePath(), (Object)file2.getAbsolutePath());
                                } catch (IOException iOException3) {
                                    LOGGER.error("Unable to overwrite file {}: {} {}", (Object)file.getAbsolutePath(), (Object)iOException3.getClass().getName(), (Object)iOException3.getMessage());
                                }
                            }
                        } catch (IOException iOException4) {
                            LOGGER.error("Unable to copy file {} to {}: {} {}", (Object)file.getAbsolutePath(), (Object)file2.getAbsolutePath(), (Object)iOException4.getClass().getName(), (Object)iOException4.getMessage());
                        }
                    } else {
                        LOGGER.trace("Renamed file {} to {} with source.renameTo", (Object)file.getAbsolutePath(), (Object)file2.getAbsolutePath());
                    }
                    return bl2;
                }
            } catch (RuntimeException runtimeException) {
                LOGGER.error("Unable to rename file {} to {}: {} {}", (Object)file.getAbsolutePath(), (Object)file2.getAbsolutePath(), (Object)runtimeException.getClass().getName(), (Object)runtimeException.getMessage());
            }
        } else {
            try {
                file.delete();
            } catch (Exception exception) {
                LOGGER.error("Unable to delete empty file {}: {} {}", (Object)file.getAbsolutePath(), (Object)exception.getClass().getName(), (Object)exception.getMessage());
            }
        }
        return true;
    }

    public String toString() {
        return FileRenameAction.class.getSimpleName() + '[' + this.source + " to " + this.destination + ", renameEmptyFiles=" + this.renameEmptyFiles + ']';
    }
}

