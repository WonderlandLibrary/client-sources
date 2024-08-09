/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.monitor;

import java.io.File;
import java.io.Serializable;

public class FileEntry
implements Serializable {
    private static final long serialVersionUID = -2505664948818681153L;
    static final FileEntry[] EMPTY_ENTRIES = new FileEntry[0];
    private final FileEntry parent;
    private FileEntry[] children;
    private final File file;
    private String name;
    private boolean exists;
    private boolean directory;
    private long lastModified;
    private long length;

    public FileEntry(File file) {
        this(null, file);
    }

    public FileEntry(FileEntry fileEntry, File file) {
        if (file == null) {
            throw new IllegalArgumentException("File is missing");
        }
        this.file = file;
        this.parent = fileEntry;
        this.name = file.getName();
    }

    public boolean refresh(File file) {
        boolean bl = this.exists;
        long l = this.lastModified;
        boolean bl2 = this.directory;
        long l2 = this.length;
        this.name = file.getName();
        this.exists = file.exists();
        this.directory = this.exists && file.isDirectory();
        this.lastModified = this.exists ? file.lastModified() : 0L;
        this.length = this.exists && !this.directory ? file.length() : 0L;
        return this.exists != bl || this.lastModified != l || this.directory != bl2 || this.length != l2;
    }

    public FileEntry newChildInstance(File file) {
        return new FileEntry(this, file);
    }

    public FileEntry getParent() {
        return this.parent;
    }

    public int getLevel() {
        return this.parent == null ? 0 : this.parent.getLevel() + 1;
    }

    public FileEntry[] getChildren() {
        return this.children != null ? this.children : EMPTY_ENTRIES;
    }

    public void setChildren(FileEntry[] fileEntryArray) {
        this.children = fileEntryArray;
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(long l) {
        this.lastModified = l;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long l) {
        this.length = l;
    }

    public boolean isExists() {
        return this.exists;
    }

    public void setExists(boolean bl) {
        this.exists = bl;
    }

    public boolean isDirectory() {
        return this.directory;
    }

    public void setDirectory(boolean bl) {
        this.directory = bl;
    }
}

