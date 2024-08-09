/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.cpio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.apache.commons.compress.archivers.cpio.CpioConstants;
import org.apache.commons.compress.archivers.cpio.CpioUtil;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;

public class CpioArchiveInputStream
extends ArchiveInputStream
implements CpioConstants {
    private boolean closed = false;
    private CpioArchiveEntry entry;
    private long entryBytesRead = 0L;
    private boolean entryEOF = false;
    private final byte[] tmpbuf = new byte[4096];
    private long crc = 0L;
    private final InputStream in;
    private final byte[] TWO_BYTES_BUF = new byte[2];
    private final byte[] FOUR_BYTES_BUF = new byte[4];
    private final byte[] SIX_BYTES_BUF = new byte[6];
    private final int blockSize;
    private final ZipEncoding encoding;

    public CpioArchiveInputStream(InputStream inputStream) {
        this(inputStream, 512, "US-ASCII");
    }

    public CpioArchiveInputStream(InputStream inputStream, String string) {
        this(inputStream, 512, string);
    }

    public CpioArchiveInputStream(InputStream inputStream, int n) {
        this(inputStream, n, "US-ASCII");
    }

    public CpioArchiveInputStream(InputStream inputStream, int n, String string) {
        this.in = inputStream;
        this.blockSize = n;
        this.encoding = ZipEncodingHelper.getZipEncoding(string);
    }

    public int available() throws IOException {
        this.ensureOpen();
        if (this.entryEOF) {
            return 1;
        }
        return 0;
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.in.close();
            this.closed = true;
        }
    }

    private void closeEntry() throws IOException {
        while (this.skip((long)Integer.MAX_VALUE) == Integer.MAX_VALUE) {
        }
    }

    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }

    public CpioArchiveEntry getNextCPIOEntry() throws IOException {
        this.ensureOpen();
        if (this.entry != null) {
            this.closeEntry();
        }
        this.readFully(this.TWO_BYTES_BUF, 0, this.TWO_BYTES_BUF.length);
        if (CpioUtil.byteArray2long(this.TWO_BYTES_BUF, false) == 29127L) {
            this.entry = this.readOldBinaryEntry(false);
        } else if (CpioUtil.byteArray2long(this.TWO_BYTES_BUF, true) == 29127L) {
            this.entry = this.readOldBinaryEntry(true);
        } else {
            System.arraycopy(this.TWO_BYTES_BUF, 0, this.SIX_BYTES_BUF, 0, this.TWO_BYTES_BUF.length);
            this.readFully(this.SIX_BYTES_BUF, this.TWO_BYTES_BUF.length, this.FOUR_BYTES_BUF.length);
            String string = ArchiveUtils.toAsciiString(this.SIX_BYTES_BUF);
            if (string.equals("070701")) {
                this.entry = this.readNewEntry(false);
            } else if (string.equals("070702")) {
                this.entry = this.readNewEntry(true);
            } else if (string.equals("070707")) {
                this.entry = this.readOldAsciiEntry();
            } else {
                throw new IOException("Unknown magic [" + string + "]. Occured at byte: " + this.getBytesRead());
            }
        }
        this.entryBytesRead = 0L;
        this.entryEOF = false;
        this.crc = 0L;
        if (this.entry.getName().equals("TRAILER!!!")) {
            this.entryEOF = true;
            this.skipRemainderOfLastBlock();
            return null;
        }
        return this.entry;
    }

    private void skip(int n) throws IOException {
        if (n > 0) {
            this.readFully(this.FOUR_BYTES_BUF, 0, n);
        }
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        this.ensureOpen();
        if (n < 0 || n2 < 0 || n > byArray.length - n2) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 1;
        }
        if (this.entry == null || this.entryEOF) {
            return 1;
        }
        if (this.entryBytesRead == this.entry.getSize()) {
            this.skip(this.entry.getDataPadCount());
            this.entryEOF = true;
            if (this.entry.getFormat() == 2 && this.crc != this.entry.getChksum()) {
                throw new IOException("CRC Error. Occured at byte: " + this.getBytesRead());
            }
            return 1;
        }
        int n3 = (int)Math.min((long)n2, this.entry.getSize() - this.entryBytesRead);
        if (n3 < 0) {
            return 1;
        }
        int n4 = this.readFully(byArray, n, n3);
        if (this.entry.getFormat() == 2) {
            for (int i = 0; i < n4; ++i) {
                this.crc += (long)(byArray[i] & 0xFF);
            }
        }
        this.entryBytesRead += (long)n4;
        return n4;
    }

    private final int readFully(byte[] byArray, int n, int n2) throws IOException {
        int n3 = IOUtils.readFully(this.in, byArray, n, n2);
        this.count(n3);
        if (n3 < n2) {
            throw new EOFException();
        }
        return n3;
    }

    private long readBinaryLong(int n, boolean bl) throws IOException {
        byte[] byArray = new byte[n];
        this.readFully(byArray, 0, byArray.length);
        return CpioUtil.byteArray2long(byArray, bl);
    }

    private long readAsciiLong(int n, int n2) throws IOException {
        byte[] byArray = new byte[n];
        this.readFully(byArray, 0, byArray.length);
        return Long.parseLong(ArchiveUtils.toAsciiString(byArray), n2);
    }

    private CpioArchiveEntry readNewEntry(boolean bl) throws IOException {
        CpioArchiveEntry cpioArchiveEntry = bl ? new CpioArchiveEntry(2) : new CpioArchiveEntry(1);
        cpioArchiveEntry.setInode(this.readAsciiLong(8, 16));
        long l = this.readAsciiLong(8, 16);
        if (CpioUtil.fileType(l) != 0L) {
            cpioArchiveEntry.setMode(l);
        }
        cpioArchiveEntry.setUID(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setGID(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setNumberOfLinks(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setTime(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setSize(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setDeviceMaj(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setDeviceMin(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setRemoteDeviceMaj(this.readAsciiLong(8, 16));
        cpioArchiveEntry.setRemoteDeviceMin(this.readAsciiLong(8, 16));
        long l2 = this.readAsciiLong(8, 16);
        cpioArchiveEntry.setChksum(this.readAsciiLong(8, 16));
        String string = this.readCString((int)l2);
        cpioArchiveEntry.setName(string);
        if (CpioUtil.fileType(l) == 0L && !string.equals("TRAILER!!!")) {
            throw new IOException("Mode 0 only allowed in the trailer. Found entry name: " + string + " Occured at byte: " + this.getBytesRead());
        }
        this.skip(cpioArchiveEntry.getHeaderPadCount());
        return cpioArchiveEntry;
    }

    private CpioArchiveEntry readOldAsciiEntry() throws IOException {
        CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry(4);
        cpioArchiveEntry.setDevice(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setInode(this.readAsciiLong(6, 8));
        long l = this.readAsciiLong(6, 8);
        if (CpioUtil.fileType(l) != 0L) {
            cpioArchiveEntry.setMode(l);
        }
        cpioArchiveEntry.setUID(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setGID(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setNumberOfLinks(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setRemoteDevice(this.readAsciiLong(6, 8));
        cpioArchiveEntry.setTime(this.readAsciiLong(11, 8));
        long l2 = this.readAsciiLong(6, 8);
        cpioArchiveEntry.setSize(this.readAsciiLong(11, 8));
        String string = this.readCString((int)l2);
        cpioArchiveEntry.setName(string);
        if (CpioUtil.fileType(l) == 0L && !string.equals("TRAILER!!!")) {
            throw new IOException("Mode 0 only allowed in the trailer. Found entry: " + string + " Occured at byte: " + this.getBytesRead());
        }
        return cpioArchiveEntry;
    }

    private CpioArchiveEntry readOldBinaryEntry(boolean bl) throws IOException {
        CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry(8);
        cpioArchiveEntry.setDevice(this.readBinaryLong(2, bl));
        cpioArchiveEntry.setInode(this.readBinaryLong(2, bl));
        long l = this.readBinaryLong(2, bl);
        if (CpioUtil.fileType(l) != 0L) {
            cpioArchiveEntry.setMode(l);
        }
        cpioArchiveEntry.setUID(this.readBinaryLong(2, bl));
        cpioArchiveEntry.setGID(this.readBinaryLong(2, bl));
        cpioArchiveEntry.setNumberOfLinks(this.readBinaryLong(2, bl));
        cpioArchiveEntry.setRemoteDevice(this.readBinaryLong(2, bl));
        cpioArchiveEntry.setTime(this.readBinaryLong(4, bl));
        long l2 = this.readBinaryLong(2, bl);
        cpioArchiveEntry.setSize(this.readBinaryLong(4, bl));
        String string = this.readCString((int)l2);
        cpioArchiveEntry.setName(string);
        if (CpioUtil.fileType(l) == 0L && !string.equals("TRAILER!!!")) {
            throw new IOException("Mode 0 only allowed in the trailer. Found entry: " + string + "Occured at byte: " + this.getBytesRead());
        }
        this.skip(cpioArchiveEntry.getHeaderPadCount());
        return cpioArchiveEntry;
    }

    private String readCString(int n) throws IOException {
        byte[] byArray = new byte[n - 1];
        this.readFully(byArray, 0, byArray.length);
        this.in.read();
        return this.encoding.decode(byArray);
    }

    public long skip(long l) throws IOException {
        int n;
        int n2;
        if (l < 0L) {
            throw new IllegalArgumentException("negative skip length");
        }
        this.ensureOpen();
        int n3 = (int)Math.min(l, Integer.MAX_VALUE);
        for (n = 0; n < n3; n += n2) {
            n2 = n3 - n;
            if (n2 > this.tmpbuf.length) {
                n2 = this.tmpbuf.length;
            }
            if ((n2 = this.read(this.tmpbuf, 0, n2)) != -1) continue;
            this.entryEOF = true;
            break;
        }
        return n;
    }

    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextCPIOEntry();
    }

    private void skipRemainderOfLastBlock() throws IOException {
        long l;
        long l2;
        long l3 = this.getBytesRead() % (long)this.blockSize;
        long l4 = l2 = l3 == 0L ? 0L : (long)this.blockSize - l3;
        while (l2 > 0L && (l = this.skip((long)this.blockSize - l3)) > 0L) {
            l2 -= l;
        }
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < 6) {
            return true;
        }
        if (byArray[0] == 113 && (byArray[1] & 0xFF) == 199) {
            return false;
        }
        if (byArray[1] == 113 && (byArray[0] & 0xFF) == 199) {
            return false;
        }
        if (byArray[0] != 48) {
            return true;
        }
        if (byArray[1] != 55) {
            return true;
        }
        if (byArray[2] != 48) {
            return true;
        }
        if (byArray[3] != 55) {
            return true;
        }
        if (byArray[4] != 48) {
            return true;
        }
        if (byArray[5] == 49) {
            return false;
        }
        if (byArray[5] == 50) {
            return false;
        }
        return byArray[5] != 55;
    }
}

