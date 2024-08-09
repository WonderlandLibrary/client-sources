/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;

public final class CommonsCompressAction
extends AbstractAction {
    private static final int BUF_SIZE = 8102;
    private final String name;
    private final File source;
    private final File destination;
    private final boolean deleteSource;

    public CommonsCompressAction(String string, File file, File file2, boolean bl) {
        Objects.requireNonNull(file, "source");
        Objects.requireNonNull(file2, "destination");
        this.name = string;
        this.source = file;
        this.destination = file2;
        this.deleteSource = bl;
    }

    @Override
    public boolean execute() throws IOException {
        return CommonsCompressAction.execute(this.name, this.source, this.destination, this.deleteSource);
    }

    public static boolean execute(String string, File file, File file2, boolean bl) throws IOException {
        if (!file.exists()) {
            return true;
        }
        LOGGER.debug("Starting {} compression of {}", (Object)string, (Object)file.getPath());
        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new CompressorStreamFactory().createCompressorOutputStream(string, new FileOutputStream(file2)));){
            IOUtils.copy(fileInputStream, bufferedOutputStream, 8102);
            LOGGER.debug("Finished {} compression of {}", (Object)string, (Object)file.getPath());
        } catch (CompressorException compressorException) {
            throw new IOException(compressorException);
        }
        if (bl) {
            try {
                if (Files.deleteIfExists(file.toPath())) {
                    LOGGER.debug("Deleted {}", (Object)file.toString());
                } else {
                    LOGGER.warn("Unable to delete {} after {} compression. File did not exist", (Object)file.toString(), (Object)string);
                }
            } catch (Exception exception) {
                LOGGER.warn("Unable to delete {} after {} compression, {}", (Object)file.toString(), (Object)string, (Object)exception.getMessage());
            }
        }
        return false;
    }

    @Override
    protected void reportException(Exception exception) {
        LOGGER.warn("Exception during " + this.name + " compression of '" + this.source.toString() + "'.", (Throwable)exception);
    }

    public String toString() {
        return CommonsCompressAction.class.getSimpleName() + '[' + this.source + " to " + this.destination + ", deleteSource=" + this.deleteSource + ']';
    }

    public String getName() {
        return this.name;
    }

    public File getSource() {
        return this.source;
    }

    public File getDestination() {
        return this.destination;
    }

    public boolean isDeleteSource() {
        return this.deleteSource;
    }
}

