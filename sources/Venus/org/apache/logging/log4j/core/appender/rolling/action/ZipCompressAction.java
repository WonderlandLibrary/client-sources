/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;

public final class ZipCompressAction
extends AbstractAction {
    private static final int BUF_SIZE = 8102;
    private final File source;
    private final File destination;
    private final boolean deleteSource;
    private final int level;

    public ZipCompressAction(File file, File file2, boolean bl, int n) {
        Objects.requireNonNull(file, "source");
        Objects.requireNonNull(file2, "destination");
        this.source = file;
        this.destination = file2;
        this.deleteSource = bl;
        this.level = n;
    }

    @Override
    public boolean execute() throws IOException {
        return ZipCompressAction.execute(this.source, this.destination, this.deleteSource, this.level);
    }

    public static boolean execute(File file, File file2, boolean bl, int n) throws IOException {
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file2));){
                int n2;
                zipOutputStream.setLevel(n);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);
                byte[] byArray = new byte[8102];
                while ((n2 = fileInputStream.read(byArray)) != -1) {
                    zipOutputStream.write(byArray, 0, n2);
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
        return ZipCompressAction.class.getSimpleName() + '[' + this.source + " to " + this.destination + ", level=" + this.level + ", deleteSource=" + this.deleteSource + ']';
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

    public int getLevel() {
        return this.level;
    }
}

