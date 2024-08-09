/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.ar;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;

public class ArArchiveInputStream
extends ArchiveInputStream {
    private final InputStream input;
    private long offset = 0L;
    private boolean closed;
    private ArArchiveEntry currentEntry = null;
    private byte[] namebuffer = null;
    private long entryOffset = -1L;
    private final byte[] NAME_BUF = new byte[16];
    private final byte[] LAST_MODIFIED_BUF = new byte[12];
    private final byte[] ID_BUF = new byte[6];
    private final byte[] FILE_MODE_BUF = new byte[8];
    private final byte[] LENGTH_BUF = new byte[10];
    static final String BSD_LONGNAME_PREFIX = "#1/";
    private static final int BSD_LONGNAME_PREFIX_LEN = 3;
    private static final String BSD_LONGNAME_PATTERN = "^#1/\\d+";
    private static final String GNU_STRING_TABLE_NAME = "//";
    private static final String GNU_LONGNAME_PATTERN = "^/\\d+";

    public ArArchiveInputStream(InputStream inputStream) {
        this.input = inputStream;
        this.closed = false;
    }

    public ArArchiveEntry getNextArEntry() throws IOException {
        int n;
        int n2;
        Object object;
        if (this.currentEntry != null) {
            long l = this.entryOffset + this.currentEntry.getLength();
            IOUtils.skip(this, l - this.offset);
            this.currentEntry = null;
        }
        if (this.offset == 0L) {
            byte[] byArray = ArchiveUtils.toAsciiBytes("!<arch>\n");
            object = new byte[byArray.length];
            int n3 = IOUtils.readFully(this, object);
            if (n3 != byArray.length) {
                throw new IOException("failed to read header. Occured at byte: " + this.getBytesRead());
            }
            for (n2 = 0; n2 < byArray.length; ++n2) {
                if (byArray[n2] == object[n2]) continue;
                throw new IOException("invalid header " + ArchiveUtils.toAsciiString(object));
            }
        }
        if (this.offset % 2L != 0L && this.read() < 0) {
            return null;
        }
        if (this.input.available() == 0) {
            return null;
        }
        IOUtils.readFully(this, this.NAME_BUF);
        IOUtils.readFully(this, this.LAST_MODIFIED_BUF);
        IOUtils.readFully(this, this.ID_BUF);
        int n4 = this.asInt(this.ID_BUF, true);
        IOUtils.readFully(this, this.ID_BUF);
        IOUtils.readFully(this, this.FILE_MODE_BUF);
        IOUtils.readFully(this, this.LENGTH_BUF);
        object = ArchiveUtils.toAsciiBytes("`\n");
        byte[] byArray = new byte[((byte[])object).length];
        n2 = IOUtils.readFully(this, byArray);
        if (n2 != ((byte[])object).length) {
            throw new IOException("failed to read entry trailer. Occured at byte: " + this.getBytesRead());
        }
        for (n = 0; n < ((byte[])object).length; ++n) {
            if (object[n] == byArray[n]) continue;
            throw new IOException("invalid entry trailer. not read the content? Occured at byte: " + this.getBytesRead());
        }
        this.entryOffset = this.offset;
        object = ArchiveUtils.toAsciiString(this.NAME_BUF).trim();
        if (ArArchiveInputStream.isGNUStringTable((String)object)) {
            this.currentEntry = this.readGNUStringTable(this.LENGTH_BUF);
            return this.getNextArEntry();
        }
        long l = this.asLong(this.LENGTH_BUF);
        if (object.endsWith("/")) {
            object = object.substring(0, object.length() - 1);
        } else if (this.isGNULongName((String)object)) {
            n = Integer.parseInt(object.substring(1));
            object = this.getExtendedName(n);
        } else if (ArArchiveInputStream.isBSDLongName((String)object)) {
            object = this.getBSDLongName((String)object);
            n = object.length();
            l -= (long)n;
            this.entryOffset += (long)n;
        }
        this.currentEntry = new ArArchiveEntry((String)object, l, n4, this.asInt(this.ID_BUF, true), this.asInt(this.FILE_MODE_BUF, 8), this.asLong(this.LAST_MODIFIED_BUF));
        return this.currentEntry;
    }

    private String getExtendedName(int n) throws IOException {
        if (this.namebuffer == null) {
            throw new IOException("Cannot process GNU long filename as no // record was found");
        }
        for (int i = n; i < this.namebuffer.length; ++i) {
            if (this.namebuffer[i] != 10) continue;
            if (this.namebuffer[i - 1] == 47) {
                --i;
            }
            return ArchiveUtils.toAsciiString(this.namebuffer, n, i - n);
        }
        throw new IOException("Failed to read entry: " + n);
    }

    private long asLong(byte[] byArray) {
        return Long.parseLong(ArchiveUtils.toAsciiString(byArray).trim());
    }

    private int asInt(byte[] byArray) {
        return this.asInt(byArray, 10, false);
    }

    private int asInt(byte[] byArray, boolean bl) {
        return this.asInt(byArray, 10, bl);
    }

    private int asInt(byte[] byArray, int n) {
        return this.asInt(byArray, n, false);
    }

    private int asInt(byte[] byArray, int n, boolean bl) {
        String string = ArchiveUtils.toAsciiString(byArray).trim();
        if (string.length() == 0 && bl) {
            return 1;
        }
        return Integer.parseInt(string, n);
    }

    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextArEntry();
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.input.close();
        }
        this.currentEntry = null;
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = n2;
        if (this.currentEntry != null) {
            long l = this.entryOffset + this.currentEntry.getLength();
            if (n2 > 0 && l > this.offset) {
                n3 = (int)Math.min((long)n2, l - this.offset);
            } else {
                return 1;
            }
        }
        int n4 = this.input.read(byArray, n, n3);
        this.count(n4);
        this.offset += n4 > 0 ? (long)n4 : 0L;
        return n4;
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < 8) {
            return true;
        }
        if (byArray[0] != 33) {
            return true;
        }
        if (byArray[1] != 60) {
            return true;
        }
        if (byArray[2] != 97) {
            return true;
        }
        if (byArray[3] != 114) {
            return true;
        }
        if (byArray[4] != 99) {
            return true;
        }
        if (byArray[5] != 104) {
            return true;
        }
        if (byArray[6] != 62) {
            return true;
        }
        return byArray[7] != 10;
    }

    private static boolean isBSDLongName(String string) {
        return string != null && string.matches(BSD_LONGNAME_PATTERN);
    }

    private String getBSDLongName(String string) throws IOException {
        int n = Integer.parseInt(string.substring(BSD_LONGNAME_PREFIX_LEN));
        byte[] byArray = new byte[n];
        int n2 = IOUtils.readFully(this.input, byArray);
        this.count(n2);
        if (n2 != n) {
            throw new EOFException();
        }
        return ArchiveUtils.toAsciiString(byArray);
    }

    private static boolean isGNUStringTable(String string) {
        return GNU_STRING_TABLE_NAME.equals(string);
    }

    private ArArchiveEntry readGNUStringTable(byte[] byArray) throws IOException {
        int n = this.asInt(byArray);
        this.namebuffer = new byte[n];
        int n2 = IOUtils.readFully(this, this.namebuffer, 0, n);
        if (n2 != n) {
            throw new IOException("Failed to read complete // record: expected=" + n + " read=" + n2);
        }
        return new ArArchiveEntry(GNU_STRING_TABLE_NAME, n);
    }

    private boolean isGNULongName(String string) {
        return string != null && string.matches(GNU_LONGNAME_PATTERN);
    }
}

