/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.ar;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.utils.ArchiveUtils;

public class ArArchiveOutputStream
extends ArchiveOutputStream {
    public static final int LONGFILE_ERROR = 0;
    public static final int LONGFILE_BSD = 1;
    private final OutputStream out;
    private long entryOffset = 0L;
    private ArArchiveEntry prevEntry;
    private boolean haveUnclosedEntry = false;
    private int longFileMode = 0;
    private boolean finished = false;

    public ArArchiveOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void setLongFileMode(int n) {
        this.longFileMode = n;
    }

    private long writeArchiveHeader() throws IOException {
        byte[] byArray = ArchiveUtils.toAsciiBytes("!<arch>\n");
        this.out.write(byArray);
        return byArray.length;
    }

    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.prevEntry == null || !this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        }
        if (this.entryOffset % 2L != 0L) {
            this.out.write(10);
        }
        this.haveUnclosedEntry = false;
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        ArArchiveEntry arArchiveEntry = (ArArchiveEntry)archiveEntry;
        if (this.prevEntry == null) {
            this.writeArchiveHeader();
        } else {
            if (this.prevEntry.getLength() != this.entryOffset) {
                throw new IOException("length does not match entry (" + this.prevEntry.getLength() + " != " + this.entryOffset);
            }
            if (this.haveUnclosedEntry) {
                this.closeArchiveEntry();
            }
        }
        this.prevEntry = arArchiveEntry;
        this.writeEntryHeader(arArchiveEntry);
        this.entryOffset = 0L;
        this.haveUnclosedEntry = true;
    }

    private long fill(long l, long l2, char c) throws IOException {
        long l3 = l2 - l;
        if (l3 > 0L) {
            int n = 0;
            while ((long)n < l3) {
                this.write(c);
                ++n;
            }
        }
        return l2;
    }

    private long write(String string) throws IOException {
        byte[] byArray = string.getBytes("ascii");
        this.write(byArray);
        return byArray.length;
    }

    private long writeEntryHeader(ArArchiveEntry arArchiveEntry) throws IOException {
        long l = 0L;
        boolean bl = false;
        String string = arArchiveEntry.getName();
        if (0 == this.longFileMode && string.length() > 16) {
            throw new IOException("filename too long, > 16 chars: " + string);
        }
        if (1 == this.longFileMode && (string.length() > 16 || string.indexOf(" ") > -1)) {
            bl = true;
            l += this.write("#1/" + String.valueOf(string.length()));
        } else {
            l += this.write(string);
        }
        l = this.fill(l, 16L, ' ');
        String string2 = "" + arArchiveEntry.getLastModified();
        if (string2.length() > 12) {
            throw new IOException("modified too long");
        }
        l += this.write(string2);
        l = this.fill(l, 28L, ' ');
        String string3 = "" + arArchiveEntry.getUserId();
        if (string3.length() > 6) {
            throw new IOException("userid too long");
        }
        l += this.write(string3);
        l = this.fill(l, 34L, ' ');
        String string4 = "" + arArchiveEntry.getGroupId();
        if (string4.length() > 6) {
            throw new IOException("groupid too long");
        }
        l += this.write(string4);
        l = this.fill(l, 40L, ' ');
        String string5 = "" + Integer.toString(arArchiveEntry.getMode(), 8);
        if (string5.length() > 8) {
            throw new IOException("filemode too long");
        }
        l += this.write(string5);
        l = this.fill(l, 48L, ' ');
        String string6 = String.valueOf(arArchiveEntry.getLength() + (long)(bl ? string.length() : 0));
        if (string6.length() > 10) {
            throw new IOException("size too long");
        }
        l += this.write(string6);
        l = this.fill(l, 58L, ' ');
        l += this.write("`\n");
        if (bl) {
            l += this.write(string);
        }
        return l;
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.out.write(byArray, n, n2);
        this.count(n2);
        this.entryOffset += (long)n2;
    }

    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        this.out.close();
        this.prevEntry = null;
    }

    public ArchiveEntry createArchiveEntry(File file, String string) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new ArArchiveEntry(file, string);
    }

    public void finish() throws IOException {
        if (this.haveUnclosedEntry) {
            throw new IOException("This archive contains unclosed entries.");
        }
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        this.finished = true;
    }
}

