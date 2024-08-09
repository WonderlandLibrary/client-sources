/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.changes;

import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;

class Change {
    private final String targetFile;
    private final ArchiveEntry entry;
    private final InputStream input;
    private final boolean replaceMode;
    private final int type;
    static final int TYPE_DELETE = 1;
    static final int TYPE_ADD = 2;
    static final int TYPE_MOVE = 3;
    static final int TYPE_DELETE_DIR = 4;

    Change(String string, int n) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.targetFile = string;
        this.type = n;
        this.input = null;
        this.entry = null;
        this.replaceMode = true;
    }

    Change(ArchiveEntry archiveEntry, InputStream inputStream, boolean bl) {
        if (archiveEntry == null || inputStream == null) {
            throw new NullPointerException();
        }
        this.entry = archiveEntry;
        this.input = inputStream;
        this.type = 2;
        this.targetFile = null;
        this.replaceMode = bl;
    }

    ArchiveEntry getEntry() {
        return this.entry;
    }

    InputStream getInput() {
        return this.input;
    }

    String targetFile() {
        return this.targetFile;
    }

    int type() {
        return this.type;
    }

    boolean isReplaceMode() {
        return this.replaceMode;
    }
}

