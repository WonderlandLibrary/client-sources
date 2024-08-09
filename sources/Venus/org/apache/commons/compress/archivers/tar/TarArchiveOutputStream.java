/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.tar;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.CountingOutputStream;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TarArchiveOutputStream
extends ArchiveOutputStream {
    public static final int LONGFILE_ERROR = 0;
    public static final int LONGFILE_TRUNCATE = 1;
    public static final int LONGFILE_GNU = 2;
    public static final int LONGFILE_POSIX = 3;
    public static final int BIGNUMBER_ERROR = 0;
    public static final int BIGNUMBER_STAR = 1;
    public static final int BIGNUMBER_POSIX = 2;
    private long currSize;
    private String currName;
    private long currBytes;
    private final byte[] recordBuf;
    private int assemLen;
    private final byte[] assemBuf;
    private int longFileMode = 0;
    private int bigNumberMode = 0;
    private int recordsWritten;
    private final int recordsPerBlock;
    private final int recordSize;
    private boolean closed = false;
    private boolean haveUnclosedEntry = false;
    private boolean finished = false;
    private final OutputStream out;
    private final ZipEncoding encoding;
    private boolean addPaxHeadersForNonAsciiNames = false;
    private static final ZipEncoding ASCII = ZipEncodingHelper.getZipEncoding("ASCII");

    public TarArchiveOutputStream(OutputStream outputStream) {
        this(outputStream, 10240, 512);
    }

    public TarArchiveOutputStream(OutputStream outputStream, String string) {
        this(outputStream, 10240, 512, string);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int n) {
        this(outputStream, n, 512);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int n, String string) {
        this(outputStream, n, 512, string);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int n, int n2) {
        this(outputStream, n, n2, null);
    }

    public TarArchiveOutputStream(OutputStream outputStream, int n, int n2, String string) {
        this.out = new CountingOutputStream(outputStream);
        this.encoding = ZipEncodingHelper.getZipEncoding(string);
        this.assemLen = 0;
        this.assemBuf = new byte[n2];
        this.recordBuf = new byte[n2];
        this.recordSize = n2;
        this.recordsPerBlock = n / n2;
    }

    public void setLongFileMode(int n) {
        this.longFileMode = n;
    }

    public void setBigNumberMode(int n) {
        this.bigNumberMode = n;
    }

    public void setAddPaxHeadersForNonAsciiNames(boolean bl) {
        this.addPaxHeadersForNonAsciiNames = bl;
    }

    @Override
    @Deprecated
    public int getCount() {
        return (int)this.getBytesWritten();
    }

    @Override
    public long getBytesWritten() {
        return ((CountingOutputStream)this.out).getBytesWritten();
    }

    @Override
    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.haveUnclosedEntry) {
            throw new IOException("This archives contains unclosed entries.");
        }
        this.writeEOFRecord();
        this.writeEOFRecord();
        this.padAsNeeded();
        this.out.flush();
        this.finished = true;
    }

    @Override
    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        if (!this.closed) {
            this.out.close();
            this.closed = true;
        }
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    @Override
    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        boolean bl;
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        TarArchiveEntry tarArchiveEntry = (TarArchiveEntry)archiveEntry;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String string = tarArchiveEntry.getName();
        boolean bl2 = this.handleLongName(string, hashMap, "path", (byte)76, "file name");
        String string2 = tarArchiveEntry.getLinkName();
        boolean bl3 = bl = string2 != null && string2.length() > 0 && this.handleLongName(string2, hashMap, "linkpath", (byte)75, "link name");
        if (this.bigNumberMode == 2) {
            this.addPaxHeadersForBigNumbers(hashMap, tarArchiveEntry);
        } else if (this.bigNumberMode != 1) {
            this.failForBigNumbers(tarArchiveEntry);
        }
        if (this.addPaxHeadersForNonAsciiNames && !bl2 && !ASCII.canEncode(string)) {
            hashMap.put("path", string);
        }
        if (this.addPaxHeadersForNonAsciiNames && !bl && (tarArchiveEntry.isLink() || tarArchiveEntry.isSymbolicLink()) && !ASCII.canEncode(string2)) {
            hashMap.put("linkpath", string2);
        }
        if (hashMap.size() > 0) {
            this.writePaxHeaders(string, hashMap);
        }
        tarArchiveEntry.writeEntryHeader(this.recordBuf, this.encoding, this.bigNumberMode == 1);
        this.writeRecord(this.recordBuf);
        this.currBytes = 0L;
        this.currSize = tarArchiveEntry.isDirectory() ? 0L : tarArchiveEntry.getSize();
        this.currName = string;
        this.haveUnclosedEntry = true;
    }

    @Override
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (!this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        }
        if (this.assemLen > 0) {
            for (int i = this.assemLen; i < this.assemBuf.length; ++i) {
                this.assemBuf[i] = 0;
            }
            this.writeRecord(this.assemBuf);
            this.currBytes += (long)this.assemLen;
            this.assemLen = 0;
        }
        if (this.currBytes < this.currSize) {
            throw new IOException("entry '" + this.currName + "' closed at '" + this.currBytes + "' before the '" + this.currSize + "' bytes specified in the header were written");
        }
        this.haveUnclosedEntry = false;
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (!this.haveUnclosedEntry) {
            throw new IllegalStateException("No current tar entry");
        }
        if (this.currBytes + (long)n2 > this.currSize) {
            throw new IOException("request to write '" + n2 + "' bytes exceeds size in header of '" + this.currSize + "' bytes for entry '" + this.currName + "'");
        }
        if (this.assemLen > 0) {
            if (this.assemLen + n2 >= this.recordBuf.length) {
                n3 = this.recordBuf.length - this.assemLen;
                System.arraycopy(this.assemBuf, 0, this.recordBuf, 0, this.assemLen);
                System.arraycopy(byArray, n, this.recordBuf, this.assemLen, n3);
                this.writeRecord(this.recordBuf);
                this.currBytes += (long)this.recordBuf.length;
                n += n3;
                n2 -= n3;
                this.assemLen = 0;
            } else {
                System.arraycopy(byArray, n, this.assemBuf, this.assemLen, n2);
                n += n2;
                this.assemLen += n2;
                n2 = 0;
            }
        }
        while (n2 > 0) {
            if (n2 < this.recordBuf.length) {
                System.arraycopy(byArray, n, this.assemBuf, this.assemLen, n2);
                this.assemLen += n2;
                break;
            }
            this.writeRecord(byArray, n);
            n3 = this.recordBuf.length;
            this.currBytes += (long)n3;
            n2 -= n3;
            n += n3;
        }
    }

    void writePaxHeaders(String string, Map<String, String> map) throws IOException {
        String string2 = "./PaxHeaders.X/" + this.stripTo7Bits(string);
        if (string2.length() >= 100) {
            string2 = string2.substring(0, 99);
        }
        TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(string2, 120);
        StringWriter stringWriter = new StringWriter();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String string3 = entry.getKey();
            String string4 = entry.getValue();
            int n = string3.length() + string4.length() + 3 + 2;
            String string5 = n + " " + string3 + "=" + string4 + "\n";
            int n2 = string5.getBytes("UTF-8").length;
            while (n != n2) {
                n = n2;
                string5 = n + " " + string3 + "=" + string4 + "\n";
                n2 = string5.getBytes("UTF-8").length;
            }
            stringWriter.write(string5);
        }
        Object object = stringWriter.toString().getBytes("UTF-8");
        tarArchiveEntry.setSize(((Object)object).length);
        this.putArchiveEntry(tarArchiveEntry);
        this.write((byte[])object);
        this.closeArchiveEntry();
    }

    private String stripTo7Bits(String string) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = (char)(string.charAt(i) & 0x7F);
            if (this.shouldBeReplaced(c)) {
                stringBuilder.append("_");
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private boolean shouldBeReplaced(char c) {
        return c == '\u0000' || c == '/' || c == '\\';
    }

    private void writeEOFRecord() throws IOException {
        Arrays.fill(this.recordBuf, (byte)0);
        this.writeRecord(this.recordBuf);
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public ArchiveEntry createArchiveEntry(File file, String string) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new TarArchiveEntry(file, string);
    }

    private void writeRecord(byte[] byArray) throws IOException {
        if (byArray.length != this.recordSize) {
            throw new IOException("record to write has length '" + byArray.length + "' which is not the record size of '" + this.recordSize + "'");
        }
        this.out.write(byArray);
        ++this.recordsWritten;
    }

    private void writeRecord(byte[] byArray, int n) throws IOException {
        if (n + this.recordSize > byArray.length) {
            throw new IOException("record has length '" + byArray.length + "' with offset '" + n + "' which is less than the record size of '" + this.recordSize + "'");
        }
        this.out.write(byArray, n, this.recordSize);
        ++this.recordsWritten;
    }

    private void padAsNeeded() throws IOException {
        int n = this.recordsWritten % this.recordsPerBlock;
        if (n != 0) {
            for (int i = n; i < this.recordsPerBlock; ++i) {
                this.writeEOFRecord();
            }
        }
    }

    private void addPaxHeadersForBigNumbers(Map<String, String> map, TarArchiveEntry tarArchiveEntry) {
        this.addPaxHeaderForBigNumber(map, "size", tarArchiveEntry.getSize(), 0x1FFFFFFFFL);
        this.addPaxHeaderForBigNumber(map, "gid", tarArchiveEntry.getGroupId(), 0x1FFFFFL);
        this.addPaxHeaderForBigNumber(map, "mtime", tarArchiveEntry.getModTime().getTime() / 1000L, 0x1FFFFFFFFL);
        this.addPaxHeaderForBigNumber(map, "uid", tarArchiveEntry.getUserId(), 0x1FFFFFL);
        this.addPaxHeaderForBigNumber(map, "SCHILY.devmajor", tarArchiveEntry.getDevMajor(), 0x1FFFFFL);
        this.addPaxHeaderForBigNumber(map, "SCHILY.devminor", tarArchiveEntry.getDevMinor(), 0x1FFFFFL);
        this.failForBigNumber("mode", tarArchiveEntry.getMode(), 0x1FFFFFL);
    }

    private void addPaxHeaderForBigNumber(Map<String, String> map, String string, long l, long l2) {
        if (l < 0L || l > l2) {
            map.put(string, String.valueOf(l));
        }
    }

    private void failForBigNumbers(TarArchiveEntry tarArchiveEntry) {
        this.failForBigNumber("entry size", tarArchiveEntry.getSize(), 0x1FFFFFFFFL);
        this.failForBigNumber("group id", tarArchiveEntry.getGroupId(), 0x1FFFFFL);
        this.failForBigNumber("last modification time", tarArchiveEntry.getModTime().getTime() / 1000L, 0x1FFFFFFFFL);
        this.failForBigNumber("user id", tarArchiveEntry.getUserId(), 0x1FFFFFL);
        this.failForBigNumber("mode", tarArchiveEntry.getMode(), 0x1FFFFFL);
        this.failForBigNumber("major device number", tarArchiveEntry.getDevMajor(), 0x1FFFFFL);
        this.failForBigNumber("minor device number", tarArchiveEntry.getDevMinor(), 0x1FFFFFL);
    }

    private void failForBigNumber(String string, long l, long l2) {
        if (l < 0L || l > l2) {
            throw new RuntimeException(string + " '" + l + "' is too big ( > " + l2 + " )");
        }
    }

    private boolean handleLongName(String string, Map<String, String> map, String string2, byte by, String string3) throws IOException {
        ByteBuffer byteBuffer = this.encoding.encode(string);
        int n = byteBuffer.limit() - byteBuffer.position();
        if (n >= 100) {
            if (this.longFileMode == 3) {
                map.put(string2, string);
                return false;
            }
            if (this.longFileMode == 2) {
                TarArchiveEntry tarArchiveEntry = new TarArchiveEntry("././@LongLink", by);
                tarArchiveEntry.setSize(n + 1);
                this.putArchiveEntry(tarArchiveEntry);
                this.write(byteBuffer.array(), byteBuffer.arrayOffset(), n);
                this.write(0);
                this.closeArchiveEntry();
            } else if (this.longFileMode != 1) {
                throw new RuntimeException(string3 + " '" + string + "' is too long ( > " + 100 + " bytes)");
            }
        }
        return true;
    }
}

