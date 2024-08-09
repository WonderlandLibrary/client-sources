/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.tar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveSparseEntry;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TarArchiveInputStream
extends ArchiveInputStream {
    private static final int SMALL_BUFFER_SIZE = 256;
    private final byte[] SMALL_BUF = new byte[256];
    private final int recordSize;
    private final int blockSize;
    private boolean hasHitEOF;
    private long entrySize;
    private long entryOffset;
    private final InputStream is;
    private TarArchiveEntry currEntry;
    private final ZipEncoding encoding;

    public TarArchiveInputStream(InputStream inputStream) {
        this(inputStream, 10240, 512);
    }

    public TarArchiveInputStream(InputStream inputStream, String string) {
        this(inputStream, 10240, 512, string);
    }

    public TarArchiveInputStream(InputStream inputStream, int n) {
        this(inputStream, n, 512);
    }

    public TarArchiveInputStream(InputStream inputStream, int n, String string) {
        this(inputStream, n, 512, string);
    }

    public TarArchiveInputStream(InputStream inputStream, int n, int n2) {
        this(inputStream, n, n2, null);
    }

    public TarArchiveInputStream(InputStream inputStream, int n, int n2, String string) {
        this.is = inputStream;
        this.hasHitEOF = false;
        this.encoding = ZipEncodingHelper.getZipEncoding(string);
        this.recordSize = n2;
        this.blockSize = n;
    }

    @Override
    public void close() throws IOException {
        this.is.close();
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    @Override
    public int available() throws IOException {
        if (this.entrySize - this.entryOffset > Integer.MAX_VALUE) {
            return 0;
        }
        return (int)(this.entrySize - this.entryOffset);
    }

    @Override
    public long skip(long l) throws IOException {
        if (l <= 0L) {
            return 0L;
        }
        long l2 = this.entrySize - this.entryOffset;
        long l3 = this.is.skip(Math.min(l, l2));
        this.count(l3);
        this.entryOffset += l3;
        return l3;
    }

    @Override
    public synchronized void reset() {
    }

    public TarArchiveEntry getNextTarEntry() throws IOException {
        byte[] byArray;
        byte[] byArray2;
        if (this.hasHitEOF) {
            return null;
        }
        if (this.currEntry != null) {
            IOUtils.skip(this, Long.MAX_VALUE);
            this.skipRecordPadding();
        }
        if ((byArray2 = this.getRecord()) == null) {
            this.currEntry = null;
            return null;
        }
        try {
            this.currEntry = new TarArchiveEntry(byArray2, this.encoding);
        } catch (IllegalArgumentException illegalArgumentException) {
            IOException iOException = new IOException("Error detected parsing the header");
            iOException.initCause(illegalArgumentException);
            throw iOException;
        }
        this.entryOffset = 0L;
        this.entrySize = this.currEntry.getSize();
        if (this.currEntry.isGNULongLinkEntry()) {
            byArray = this.getLongNameData();
            if (byArray == null) {
                return null;
            }
            this.currEntry.setLinkName(this.encoding.decode(byArray));
        }
        if (this.currEntry.isGNULongNameEntry()) {
            byArray = this.getLongNameData();
            if (byArray == null) {
                return null;
            }
            this.currEntry.setName(this.encoding.decode(byArray));
        }
        if (this.currEntry.isPaxHeader()) {
            this.paxHeaders();
        }
        if (this.currEntry.isGNUSparse()) {
            this.readGNUSparse();
        }
        this.entrySize = this.currEntry.getSize();
        return this.currEntry;
    }

    private void skipRecordPadding() throws IOException {
        if (this.entrySize > 0L && this.entrySize % (long)this.recordSize != 0L) {
            long l = this.entrySize / (long)this.recordSize + 1L;
            long l2 = l * (long)this.recordSize - this.entrySize;
            long l3 = IOUtils.skip(this.is, l2);
            this.count(l3);
        }
    }

    protected byte[] getLongNameData() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n = 0;
        while ((n = this.read(this.SMALL_BUF)) >= 0) {
            byteArrayOutputStream.write(this.SMALL_BUF, 0, n);
        }
        this.getNextEntry();
        if (this.currEntry == null) {
            return null;
        }
        byte[] byArray = byteArrayOutputStream.toByteArray();
        for (n = byArray.length; n > 0 && byArray[n - 1] == 0; --n) {
        }
        if (n != byArray.length) {
            byte[] byArray2 = new byte[n];
            System.arraycopy(byArray, 0, byArray2, 0, n);
            byArray = byArray2;
        }
        return byArray;
    }

    private byte[] getRecord() throws IOException {
        byte[] byArray = this.readRecord();
        this.hasHitEOF = this.isEOFRecord(byArray);
        if (this.hasHitEOF && byArray != null) {
            this.tryToConsumeSecondEOFRecord();
            this.consumeRemainderOfLastBlock();
            byArray = null;
        }
        return byArray;
    }

    protected boolean isEOFRecord(byte[] byArray) {
        return byArray == null || ArchiveUtils.isArrayZero(byArray, this.recordSize);
    }

    protected byte[] readRecord() throws IOException {
        byte[] byArray = new byte[this.recordSize];
        int n = IOUtils.readFully(this.is, byArray);
        this.count(n);
        if (n != this.recordSize) {
            return null;
        }
        return byArray;
    }

    private void paxHeaders() throws IOException {
        Map<String, String> map = this.parsePaxHeaders(this);
        this.getNextEntry();
        this.applyPaxHeadersToCurrentEntry(map);
    }

    Map<String, String> parsePaxHeaders(InputStream inputStream) throws IOException {
        int n;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        block0: do {
            int n2 = 0;
            int n3 = 0;
            while ((n = inputStream.read()) != -1) {
                ++n3;
                if (n == 32) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while ((n = inputStream.read()) != -1) {
                        ++n3;
                        if (n == 61) {
                            String string = byteArrayOutputStream.toString("UTF-8");
                            byte[] byArray = new byte[n2 - n3];
                            int n4 = IOUtils.readFully(inputStream, byArray);
                            if (n4 != n2 - n3) {
                                throw new IOException("Failed to read Paxheader. Expected " + (n2 - n3) + " bytes, read " + n4);
                            }
                            String string2 = new String(byArray, 0, n2 - n3 - 1, "UTF-8");
                            hashMap.put(string, string2);
                            continue block0;
                        }
                        byteArrayOutputStream.write((byte)n);
                    }
                    continue block0;
                }
                n2 *= 10;
                n2 += n - 48;
            }
        } while (n != -1);
        return hashMap;
    }

    private void applyPaxHeadersToCurrentEntry(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String string = entry.getKey();
            String string2 = entry.getValue();
            if ("path".equals(string)) {
                this.currEntry.setName(string2);
                continue;
            }
            if ("linkpath".equals(string)) {
                this.currEntry.setLinkName(string2);
                continue;
            }
            if ("gid".equals(string)) {
                this.currEntry.setGroupId(Integer.parseInt(string2));
                continue;
            }
            if ("gname".equals(string)) {
                this.currEntry.setGroupName(string2);
                continue;
            }
            if ("uid".equals(string)) {
                this.currEntry.setUserId(Integer.parseInt(string2));
                continue;
            }
            if ("uname".equals(string)) {
                this.currEntry.setUserName(string2);
                continue;
            }
            if ("size".equals(string)) {
                this.currEntry.setSize(Long.parseLong(string2));
                continue;
            }
            if ("mtime".equals(string)) {
                this.currEntry.setModTime((long)(Double.parseDouble(string2) * 1000.0));
                continue;
            }
            if ("SCHILY.devminor".equals(string)) {
                this.currEntry.setDevMinor(Integer.parseInt(string2));
                continue;
            }
            if (!"SCHILY.devmajor".equals(string)) continue;
            this.currEntry.setDevMajor(Integer.parseInt(string2));
        }
    }

    private void readGNUSparse() throws IOException {
        if (this.currEntry.isExtended()) {
            byte[] byArray;
            TarArchiveSparseEntry tarArchiveSparseEntry;
            do {
                if ((byArray = this.getRecord()) != null) continue;
                this.currEntry = null;
                break;
            } while ((tarArchiveSparseEntry = new TarArchiveSparseEntry(byArray)).isExtended());
        }
    }

    @Override
    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextTarEntry();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void tryToConsumeSecondEOFRecord() throws IOException {
        boolean bl = true;
        boolean bl2 = this.is.markSupported();
        if (bl2) {
            this.is.mark(this.recordSize);
        }
        try {
            bl = !this.isEOFRecord(this.readRecord());
        } finally {
            if (bl && bl2) {
                this.pushedBackBytes(this.recordSize);
                this.is.reset();
            }
        }
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = 0;
        if (this.hasHitEOF || this.entryOffset >= this.entrySize) {
            return 1;
        }
        if (this.currEntry == null) {
            throw new IllegalStateException("No current tar entry");
        }
        n3 = this.is.read(byArray, n, n2 = Math.min(n2, this.available()));
        if (n3 == -1) {
            if (n2 > 0) {
                throw new IOException("Truncated TAR archive");
            }
            this.hasHitEOF = true;
        } else {
            this.count(n3);
            this.entryOffset += (long)n3;
        }
        return n3;
    }

    @Override
    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        if (archiveEntry instanceof TarArchiveEntry) {
            TarArchiveEntry tarArchiveEntry = (TarArchiveEntry)archiveEntry;
            return !tarArchiveEntry.isGNUSparse();
        }
        return true;
    }

    public TarArchiveEntry getCurrentEntry() {
        return this.currEntry;
    }

    protected final void setCurrentEntry(TarArchiveEntry tarArchiveEntry) {
        this.currEntry = tarArchiveEntry;
    }

    protected final boolean isAtEOF() {
        return this.hasHitEOF;
    }

    protected final void setAtEOF(boolean bl) {
        this.hasHitEOF = bl;
    }

    private void consumeRemainderOfLastBlock() throws IOException {
        long l = this.getBytesRead() % (long)this.blockSize;
        if (l > 0L) {
            long l2 = IOUtils.skip(this.is, (long)this.blockSize - l);
            this.count(l2);
        }
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < 265) {
            return true;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", byArray, 257, 6) && ArchiveUtils.matchAsciiBuffer("00", byArray, 263, 2)) {
            return false;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar ", byArray, 257, 6) && (ArchiveUtils.matchAsciiBuffer(" \u0000", byArray, 263, 2) || ArchiveUtils.matchAsciiBuffer("0\u0000", byArray, 263, 2))) {
            return false;
        }
        return !ArchiveUtils.matchAsciiBuffer("ustar\u0000", byArray, 257, 6) || !ArchiveUtils.matchAsciiBuffer("\u0000\u0000", byArray, 263, 2);
    }
}

