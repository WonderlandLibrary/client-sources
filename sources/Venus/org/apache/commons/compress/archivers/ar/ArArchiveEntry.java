/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.ar;

import java.io.File;
import java.util.Date;
import org.apache.commons.compress.archivers.ArchiveEntry;

public class ArArchiveEntry
implements ArchiveEntry {
    public static final String HEADER = "!<arch>\n";
    public static final String TRAILER = "`\n";
    private final String name;
    private final int userId;
    private final int groupId;
    private final int mode;
    private static final int DEFAULT_MODE = 33188;
    private final long lastModified;
    private final long length;

    public ArArchiveEntry(String string, long l) {
        this(string, l, 0, 0, 33188, System.currentTimeMillis() / 1000L);
    }

    public ArArchiveEntry(String string, long l, int n, int n2, int n3, long l2) {
        this.name = string;
        this.length = l;
        this.userId = n;
        this.groupId = n2;
        this.mode = n3;
        this.lastModified = l2;
    }

    public ArArchiveEntry(File file, String string) {
        this(string, file.isFile() ? file.length() : 0L, 0, 0, 33188, file.lastModified() / 1000L);
    }

    public long getSize() {
        return this.getLength();
    }

    public String getName() {
        return this.name;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public int getMode() {
        return this.mode;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public Date getLastModifiedDate() {
        return new Date(1000L * this.getLastModified());
    }

    public long getLength() {
        return this.length;
    }

    public boolean isDirectory() {
        return true;
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.name == null ? 0 : this.name.hashCode());
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ArArchiveEntry arArchiveEntry = (ArArchiveEntry)object;
        return this.name == null ? arArchiveEntry.name != null : !this.name.equals(arArchiveEntry.name);
    }
}

