/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;

public final class GzCompressAction
extends AbstractAction {
    private static final int BUF_SIZE = 8102;
    private final File source;
    private final File destination;
    private final boolean deleteSource;

    public GzCompressAction(File source, File destination, boolean deleteSource) {
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(destination, "destination");
        this.source = source;
        this.destination = destination;
        this.deleteSource = deleteSource;
    }

    @Override
    public boolean execute() throws IOException {
        return GzCompressAction.execute(this.source, this.destination, this.deleteSource);
    }

    public static boolean execute(File source, File destination, boolean deleteSource) throws IOException {
        if (source.exists()) {
            try (FileInputStream fis = new FileInputStream(source);
                 BufferedOutputStream os = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(destination)));){
                int n;
                byte[] inbuf = new byte[8102];
                while ((n = fis.read(inbuf)) != -1) {
                    os.write(inbuf, 0, n);
                }
            }
            if (deleteSource && !source.delete()) {
                LOGGER.warn("Unable to delete " + source.toString() + '.');
            }
            return true;
        }
        return false;
    }

    @Override
    protected void reportException(Exception ex) {
        LOGGER.warn("Exception during compression of '" + this.source.toString() + "'.", (Throwable)ex);
    }

    public String toString() {
        return GzCompressAction.class.getSimpleName() + '[' + this.source + " to " + this.destination + ", deleteSource=" + this.deleteSource + ']';
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

