/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.cpio;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.apache.commons.compress.archivers.cpio.CpioConstants;
import org.apache.commons.compress.archivers.cpio.CpioUtil;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;

public class CpioArchiveOutputStream
extends ArchiveOutputStream
implements CpioConstants {
    private CpioArchiveEntry entry;
    private boolean closed = false;
    private boolean finished;
    private final short entryFormat;
    private final HashMap<String, CpioArchiveEntry> names = new HashMap();
    private long crc = 0L;
    private long written;
    private final OutputStream out;
    private final int blockSize;
    private long nextArtificalDeviceAndInode = 1L;
    private final ZipEncoding encoding;

    public CpioArchiveOutputStream(OutputStream outputStream, short s) {
        this(outputStream, s, 512, "US-ASCII");
    }

    public CpioArchiveOutputStream(OutputStream outputStream, short s, int n) {
        this(outputStream, s, n, "US-ASCII");
    }

    public CpioArchiveOutputStream(OutputStream outputStream, short s, int n, String string) {
        this.out = outputStream;
        switch (s) {
            case 1: 
            case 2: 
            case 4: 
            case 8: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown format: " + s);
            }
        }
        this.entryFormat = s;
        this.blockSize = n;
        this.encoding = ZipEncodingHelper.getZipEncoding(string);
    }

    public CpioArchiveOutputStream(OutputStream outputStream) {
        this(outputStream, 1);
    }

    public CpioArchiveOutputStream(OutputStream outputStream, String string) {
        this(outputStream, 1, 512, string);
    }

    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        short s;
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        CpioArchiveEntry cpioArchiveEntry = (CpioArchiveEntry)archiveEntry;
        this.ensureOpen();
        if (this.entry != null) {
            this.closeArchiveEntry();
        }
        if (cpioArchiveEntry.getTime() == -1L) {
            cpioArchiveEntry.setTime(System.currentTimeMillis() / 1000L);
        }
        if ((s = cpioArchiveEntry.getFormat()) != this.entryFormat) {
            throw new IOException("Header format: " + s + " does not match existing format: " + this.entryFormat);
        }
        if (this.names.put(cpioArchiveEntry.getName(), cpioArchiveEntry) != null) {
            throw new IOException("duplicate entry: " + cpioArchiveEntry.getName());
        }
        this.writeHeader(cpioArchiveEntry);
        this.entry = cpioArchiveEntry;
        this.written = 0L;
    }

    private void writeHeader(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        switch (cpioArchiveEntry.getFormat()) {
            case 1: {
                this.out.write(ArchiveUtils.toAsciiBytes("070701"));
                this.count(6);
                this.writeNewEntry(cpioArchiveEntry);
                break;
            }
            case 2: {
                this.out.write(ArchiveUtils.toAsciiBytes("070702"));
                this.count(6);
                this.writeNewEntry(cpioArchiveEntry);
                break;
            }
            case 4: {
                this.out.write(ArchiveUtils.toAsciiBytes("070707"));
                this.count(6);
                this.writeOldAsciiEntry(cpioArchiveEntry);
                break;
            }
            case 8: {
                boolean bl = true;
                this.writeBinaryLong(29127L, 2, bl);
                this.writeOldBinaryEntry(cpioArchiveEntry, bl);
                break;
            }
            default: {
                throw new IOException("unknown format " + cpioArchiveEntry.getFormat());
            }
        }
    }

    private void writeNewEntry(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        long l = cpioArchiveEntry.getInode();
        long l2 = cpioArchiveEntry.getDeviceMin();
        if ("TRAILER!!!".equals(cpioArchiveEntry.getName())) {
            l2 = 0L;
            l = 0L;
        } else if (l == 0L && l2 == 0L) {
            l = this.nextArtificalDeviceAndInode & 0xFFFFFFFFFFFFFFFFL;
            l2 = this.nextArtificalDeviceAndInode++ >> 32 & 0xFFFFFFFFFFFFFFFFL;
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, l + 0x100000000L * l2) + 1L;
        }
        this.writeAsciiLong(l, 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getMode(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getUID(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getGID(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getNumberOfLinks(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getTime(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getSize(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getDeviceMaj(), 8, 16);
        this.writeAsciiLong(l2, 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getRemoteDeviceMaj(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getRemoteDeviceMin(), 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getName().length() + 1, 8, 16);
        this.writeAsciiLong(cpioArchiveEntry.getChksum(), 8, 16);
        this.writeCString(cpioArchiveEntry.getName());
        this.pad(cpioArchiveEntry.getHeaderPadCount());
    }

    private void writeOldAsciiEntry(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        long l = cpioArchiveEntry.getInode();
        long l2 = cpioArchiveEntry.getDevice();
        if ("TRAILER!!!".equals(cpioArchiveEntry.getName())) {
            l2 = 0L;
            l = 0L;
        } else if (l == 0L && l2 == 0L) {
            l = this.nextArtificalDeviceAndInode & 0x3FFFFL;
            l2 = this.nextArtificalDeviceAndInode++ >> 18 & 0x3FFFFL;
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, l + 262144L * l2) + 1L;
        }
        this.writeAsciiLong(l2, 6, 8);
        this.writeAsciiLong(l, 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getMode(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getUID(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getGID(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getNumberOfLinks(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getRemoteDevice(), 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getTime(), 11, 8);
        this.writeAsciiLong(cpioArchiveEntry.getName().length() + 1, 6, 8);
        this.writeAsciiLong(cpioArchiveEntry.getSize(), 11, 8);
        this.writeCString(cpioArchiveEntry.getName());
    }

    private void writeOldBinaryEntry(CpioArchiveEntry cpioArchiveEntry, boolean bl) throws IOException {
        long l = cpioArchiveEntry.getInode();
        long l2 = cpioArchiveEntry.getDevice();
        if ("TRAILER!!!".equals(cpioArchiveEntry.getName())) {
            l2 = 0L;
            l = 0L;
        } else if (l == 0L && l2 == 0L) {
            l = this.nextArtificalDeviceAndInode & 0xFFFFL;
            l2 = this.nextArtificalDeviceAndInode++ >> 16 & 0xFFFFL;
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, l + 65536L * l2) + 1L;
        }
        this.writeBinaryLong(l2, 2, bl);
        this.writeBinaryLong(l, 2, bl);
        this.writeBinaryLong(cpioArchiveEntry.getMode(), 2, bl);
        this.writeBinaryLong(cpioArchiveEntry.getUID(), 2, bl);
        this.writeBinaryLong(cpioArchiveEntry.getGID(), 2, bl);
        this.writeBinaryLong(cpioArchiveEntry.getNumberOfLinks(), 2, bl);
        this.writeBinaryLong(cpioArchiveEntry.getRemoteDevice(), 2, bl);
        this.writeBinaryLong(cpioArchiveEntry.getTime(), 4, bl);
        this.writeBinaryLong(cpioArchiveEntry.getName().length() + 1, 2, bl);
        this.writeBinaryLong(cpioArchiveEntry.getSize(), 4, bl);
        this.writeCString(cpioArchiveEntry.getName());
        this.pad(cpioArchiveEntry.getHeaderPadCount());
    }

    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        this.ensureOpen();
        if (this.entry == null) {
            throw new IOException("Trying to close non-existent entry");
        }
        if (this.entry.getSize() != this.written) {
            throw new IOException("invalid entry size (expected " + this.entry.getSize() + " but got " + this.written + " bytes)");
        }
        this.pad(this.entry.getDataPadCount());
        if (this.entry.getFormat() == 2 && this.crc != this.entry.getChksum()) {
            throw new IOException("CRC Error");
        }
        this.entry = null;
        this.crc = 0L;
        this.written = 0L;
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.ensureOpen();
        if (n < 0 || n2 < 0 || n > byArray.length - n2) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        if (this.entry == null) {
            throw new IOException("no current CPIO entry");
        }
        if (this.written + (long)n2 > this.entry.getSize()) {
            throw new IOException("attempt to write past end of STORED entry");
        }
        this.out.write(byArray, n, n2);
        this.written += (long)n2;
        if (this.entry.getFormat() == 2) {
            for (int i = 0; i < n2; ++i) {
                this.crc += (long)(byArray[i] & 0xFF);
            }
        }
        this.count(n2);
    }

    public void finish() throws IOException {
        this.ensureOpen();
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.entry != null) {
            throw new IOException("This archive contains unclosed entries.");
        }
        this.entry = new CpioArchiveEntry(this.entryFormat);
        this.entry.setName("TRAILER!!!");
        this.entry.setNumberOfLinks(1L);
        this.writeHeader(this.entry);
        this.closeArchiveEntry();
        int n = (int)(this.getBytesWritten() % (long)this.blockSize);
        if (n != 0) {
            this.pad(this.blockSize - n);
        }
        this.finished = true;
    }

    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        if (!this.closed) {
            this.out.close();
            this.closed = true;
        }
    }

    private void pad(int n) throws IOException {
        if (n > 0) {
            byte[] byArray = new byte[n];
            this.out.write(byArray);
            this.count(n);
        }
    }

    private void writeBinaryLong(long l, int n, boolean bl) throws IOException {
        byte[] byArray = CpioUtil.long2byteArray(l, n, bl);
        this.out.write(byArray);
        this.count(byArray.length);
    }

    private void writeAsciiLong(long l, int n, int n2) throws IOException {
        String string;
        StringBuilder stringBuilder = new StringBuilder();
        if (n2 == 16) {
            stringBuilder.append(Long.toHexString(l));
        } else if (n2 == 8) {
            stringBuilder.append(Long.toOctalString(l));
        } else {
            stringBuilder.append(Long.toString(l));
        }
        if (stringBuilder.length() <= n) {
            long l2 = n - stringBuilder.length();
            int n3 = 0;
            while ((long)n3 < l2) {
                stringBuilder.insert(0, "0");
                ++n3;
            }
            string = stringBuilder.toString();
        } else {
            string = stringBuilder.substring(stringBuilder.length() - n);
        }
        byte[] byArray = ArchiveUtils.toAsciiBytes(string);
        this.out.write(byArray);
        this.count(byArray.length);
    }

    private void writeCString(String string) throws IOException {
        ByteBuffer byteBuffer = this.encoding.encode(string);
        int n = byteBuffer.limit() - byteBuffer.position();
        this.out.write(byteBuffer.array(), byteBuffer.arrayOffset(), n);
        this.out.write(0);
        this.count(n + 1);
    }

    public ArchiveEntry createArchiveEntry(File file, String string) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new CpioArchiveEntry(file, string);
    }
}

