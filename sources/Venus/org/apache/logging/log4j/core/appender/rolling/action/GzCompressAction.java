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

    public GzCompressAction(File file, File file2, boolean bl) {
        Objects.requireNonNull(file, "source");
        Objects.requireNonNull(file2, "destination");
        this.source = file;
        this.destination = file2;
        this.deleteSource = bl;
    }

    @Override
    public boolean execute() throws IOException {
        return GzCompressAction.execute(this.source, this.destination, this.deleteSource);
    }

    public static boolean execute(File file, File file2, boolean bl) throws IOException {
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(file2)));){
                int n;
                byte[] byArray = new byte[8102];
                while ((n = fileInputStream.read(byArray)) != -1) {
                    bufferedOutputStream.write(byArray, 0, n);
                }
            }
            if (bl && !file.delete()) {
                LOGGER.warn("Unable to delete " + file.toString() + '.');
            }
            return false;
        }
        return true;
    }

    @Override
    protected void reportException(Exception exception) {
        LOGGER.warn("Exception during compression of '" + this.source.toString() + "'.", (Throwable)exception);
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

