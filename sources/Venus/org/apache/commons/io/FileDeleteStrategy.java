/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileDeleteStrategy {
    public static final FileDeleteStrategy NORMAL = new FileDeleteStrategy("Normal");
    public static final FileDeleteStrategy FORCE = new ForceFileDeleteStrategy();
    private final String name;

    protected FileDeleteStrategy(String string) {
        this.name = string;
    }

    public boolean deleteQuietly(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        try {
            return this.doDelete(file);
        } catch (IOException iOException) {
            return true;
        }
    }

    public void delete(File file) throws IOException {
        if (file.exists() && !this.doDelete(file)) {
            throw new IOException("Deletion failed: " + file);
        }
    }

    protected boolean doDelete(File file) throws IOException {
        return file.delete();
    }

    public String toString() {
        return "FileDeleteStrategy[" + this.name + "]";
    }

    static class ForceFileDeleteStrategy
    extends FileDeleteStrategy {
        ForceFileDeleteStrategy() {
            super("Force");
        }

        @Override
        protected boolean doDelete(File file) throws IOException {
            FileUtils.forceDelete(file);
            return false;
        }
    }
}

