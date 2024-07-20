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

    public FileRenameAction(File src, File dst, boolean renameEmptyFiles) {
        this.source = src;
        this.destination = dst;
        this.renameEmptyFiles = renameEmptyFiles;
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

    public static boolean execute(File source, File destination, boolean renameEmptyFiles) {
        if (renameEmptyFiles || source.length() > 0L) {
            File parent = destination.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
                if (!parent.exists()) {
                    LOGGER.error("Unable to create directory {}", (Object)parent.getAbsolutePath());
                    return false;
                }
            }
            try {
                try {
                    Files.move(Paths.get(source.getAbsolutePath(), new String[0]), Paths.get(destination.getAbsolutePath(), new String[0]), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.trace("Renamed file {} to {} with Files.move", (Object)source.getAbsolutePath(), (Object)destination.getAbsolutePath());
                    return true;
                } catch (IOException exMove) {
                    LOGGER.error("Unable to move file {} to {}: {} {}", (Object)source.getAbsolutePath(), (Object)destination.getAbsolutePath(), (Object)exMove.getClass().getName(), (Object)exMove.getMessage());
                    boolean result = source.renameTo(destination);
                    if (!result) {
                        try {
                            Files.copy(Paths.get(source.getAbsolutePath(), new String[0]), Paths.get(destination.getAbsolutePath(), new String[0]), StandardCopyOption.REPLACE_EXISTING);
                            try {
                                Files.delete(Paths.get(source.getAbsolutePath(), new String[0]));
                                LOGGER.trace("Renamed file {} to {} using copy and delete", (Object)source.getAbsolutePath(), (Object)destination.getAbsolutePath());
                            } catch (IOException exDelete) {
                                LOGGER.error("Unable to delete file {}: {} {}", (Object)source.getAbsolutePath(), (Object)exDelete.getClass().getName(), (Object)exDelete.getMessage());
                                try {
                                    new PrintWriter(source.getAbsolutePath()).close();
                                    LOGGER.trace("Renamed file {} to {} with copy and truncation", (Object)source.getAbsolutePath(), (Object)destination.getAbsolutePath());
                                } catch (IOException exOwerwrite) {
                                    LOGGER.error("Unable to overwrite file {}: {} {}", (Object)source.getAbsolutePath(), (Object)exOwerwrite.getClass().getName(), (Object)exOwerwrite.getMessage());
                                }
                            }
                        } catch (IOException exCopy) {
                            LOGGER.error("Unable to copy file {} to {}: {} {}", (Object)source.getAbsolutePath(), (Object)destination.getAbsolutePath(), (Object)exCopy.getClass().getName(), (Object)exCopy.getMessage());
                        }
                    } else {
                        LOGGER.trace("Renamed file {} to {} with source.renameTo", (Object)source.getAbsolutePath(), (Object)destination.getAbsolutePath());
                    }
                    return result;
                }
            } catch (RuntimeException ex) {
                LOGGER.error("Unable to rename file {} to {}: {} {}", (Object)source.getAbsolutePath(), (Object)destination.getAbsolutePath(), (Object)ex.getClass().getName(), (Object)ex.getMessage());
            }
        } else {
            try {
                source.delete();
            } catch (Exception exDelete) {
                LOGGER.error("Unable to delete empty file {}: {} {}", (Object)source.getAbsolutePath(), (Object)exDelete.getClass().getName(), (Object)exDelete.getMessage());
            }
        }
        return false;
    }

    public String toString() {
        return FileRenameAction.class.getSimpleName() + '[' + this.source + " to " + this.destination + ", renameEmptyFiles=" + this.renameEmptyFiles + ']';
    }
}

