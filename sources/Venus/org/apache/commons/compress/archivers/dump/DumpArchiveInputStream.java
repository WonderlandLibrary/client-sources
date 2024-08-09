/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.dump;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.dump.Dirent;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;
import org.apache.commons.compress.archivers.dump.DumpArchiveEntry;
import org.apache.commons.compress.archivers.dump.DumpArchiveSummary;
import org.apache.commons.compress.archivers.dump.DumpArchiveUtil;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.compress.archivers.dump.TapeInputStream;
import org.apache.commons.compress.archivers.dump.UnrecognizedFormatException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DumpArchiveInputStream
extends ArchiveInputStream {
    private DumpArchiveSummary summary;
    private DumpArchiveEntry active;
    private boolean isClosed;
    private boolean hasHitEOF;
    private long entrySize;
    private long entryOffset;
    private int readIdx;
    private final byte[] readBuf = new byte[1024];
    private byte[] blockBuffer;
    private int recordOffset;
    private long filepos;
    protected TapeInputStream raw;
    private final Map<Integer, Dirent> names = new HashMap<Integer, Dirent>();
    private final Map<Integer, DumpArchiveEntry> pending = new HashMap<Integer, DumpArchiveEntry>();
    private Queue<DumpArchiveEntry> queue;
    private final ZipEncoding encoding;

    public DumpArchiveInputStream(InputStream inputStream) throws ArchiveException {
        this(inputStream, null);
    }

    public DumpArchiveInputStream(InputStream inputStream, String string) throws ArchiveException {
        Object object;
        this.raw = new TapeInputStream(inputStream);
        this.hasHitEOF = false;
        this.encoding = ZipEncodingHelper.getZipEncoding(string);
        try {
            object = this.raw.readRecord();
            if (!DumpArchiveUtil.verify(object)) {
                throw new UnrecognizedFormatException();
            }
            this.summary = new DumpArchiveSummary((byte[])object, this.encoding);
            this.raw.resetBlockSize(this.summary.getNTRec(), this.summary.isCompressed());
            this.blockBuffer = new byte[4096];
            this.readCLRI();
            this.readBITS();
        } catch (IOException iOException) {
            throw new ArchiveException(iOException.getMessage(), iOException);
        }
        object = new Dirent(2, 2, 4, ".");
        this.names.put(2, (Dirent)object);
        this.queue = new PriorityQueue<DumpArchiveEntry>(10, new Comparator<DumpArchiveEntry>(this){
            final DumpArchiveInputStream this$0;
            {
                this.this$0 = dumpArchiveInputStream;
            }

            @Override
            public int compare(DumpArchiveEntry dumpArchiveEntry, DumpArchiveEntry dumpArchiveEntry2) {
                if (dumpArchiveEntry.getOriginalName() == null || dumpArchiveEntry2.getOriginalName() == null) {
                    return 0;
                }
                return dumpArchiveEntry.getOriginalName().compareTo(dumpArchiveEntry2.getOriginalName());
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((DumpArchiveEntry)object, (DumpArchiveEntry)object2);
            }
        });
    }

    @Deprecated
    public int getCount() {
        return (int)this.getBytesRead();
    }

    public long getBytesRead() {
        return this.raw.getBytesRead();
    }

    public DumpArchiveSummary getSummary() {
        return this.summary;
    }

    private void readCLRI() throws IOException {
        byte[] byArray = this.raw.readRecord();
        if (!DumpArchiveUtil.verify(byArray)) {
            throw new InvalidFormatException();
        }
        this.active = DumpArchiveEntry.parse(byArray);
        if (DumpArchiveConstants.SEGMENT_TYPE.CLRI != this.active.getHeaderType()) {
            throw new InvalidFormatException();
        }
        if (this.raw.skip(1024 * this.active.getHeaderCount()) == -1L) {
            throw new EOFException();
        }
        this.readIdx = this.active.getHeaderCount();
    }

    private void readBITS() throws IOException {
        byte[] byArray = this.raw.readRecord();
        if (!DumpArchiveUtil.verify(byArray)) {
            throw new InvalidFormatException();
        }
        this.active = DumpArchiveEntry.parse(byArray);
        if (DumpArchiveConstants.SEGMENT_TYPE.BITS != this.active.getHeaderType()) {
            throw new InvalidFormatException();
        }
        if (this.raw.skip(1024 * this.active.getHeaderCount()) == -1L) {
            throw new EOFException();
        }
        this.readIdx = this.active.getHeaderCount();
    }

    public DumpArchiveEntry getNextDumpEntry() throws IOException {
        return this.getNextEntry();
    }

    public DumpArchiveEntry getNextEntry() throws IOException {
        DumpArchiveEntry dumpArchiveEntry = null;
        String string = null;
        if (!this.queue.isEmpty()) {
            return this.queue.remove();
        }
        while (dumpArchiveEntry == null) {
            if (this.hasHitEOF) {
                return null;
            }
            while (this.readIdx < this.active.getHeaderCount()) {
                if (this.active.isSparseRecord(this.readIdx++) || this.raw.skip(1024L) != -1L) continue;
                throw new EOFException();
            }
            this.readIdx = 0;
            this.filepos = this.raw.getBytesRead();
            byte[] byArray = this.raw.readRecord();
            if (!DumpArchiveUtil.verify(byArray)) {
                throw new InvalidFormatException();
            }
            this.active = DumpArchiveEntry.parse(byArray);
            while (DumpArchiveConstants.SEGMENT_TYPE.ADDR == this.active.getHeaderType()) {
                if (this.raw.skip(1024 * (this.active.getHeaderCount() - this.active.getHeaderHoles())) == -1L) {
                    throw new EOFException();
                }
                this.filepos = this.raw.getBytesRead();
                byArray = this.raw.readRecord();
                if (!DumpArchiveUtil.verify(byArray)) {
                    throw new InvalidFormatException();
                }
                this.active = DumpArchiveEntry.parse(byArray);
            }
            if (DumpArchiveConstants.SEGMENT_TYPE.END == this.active.getHeaderType()) {
                this.hasHitEOF = true;
                return null;
            }
            dumpArchiveEntry = this.active;
            if (dumpArchiveEntry.isDirectory()) {
                this.readDirectoryEntry(this.active);
                this.entryOffset = 0L;
                this.entrySize = 0L;
                this.readIdx = this.active.getHeaderCount();
            } else {
                this.entryOffset = 0L;
                this.entrySize = this.active.getEntrySize();
                this.readIdx = 0;
            }
            this.recordOffset = this.readBuf.length;
            string = this.getPath(dumpArchiveEntry);
            if (string != null) continue;
            dumpArchiveEntry = null;
        }
        dumpArchiveEntry.setName(string);
        dumpArchiveEntry.setSimpleName(this.names.get(dumpArchiveEntry.getIno()).getName());
        dumpArchiveEntry.setOffset(this.filepos);
        return dumpArchiveEntry;
    }

    private void readDirectoryEntry(DumpArchiveEntry dumpArchiveEntry) throws IOException {
        long l = dumpArchiveEntry.getEntrySize();
        boolean bl = true;
        while (bl || DumpArchiveConstants.SEGMENT_TYPE.ADDR == dumpArchiveEntry.getHeaderType()) {
            int n;
            if (!bl) {
                this.raw.readRecord();
            }
            if (!this.names.containsKey(dumpArchiveEntry.getIno()) && DumpArchiveConstants.SEGMENT_TYPE.INODE == dumpArchiveEntry.getHeaderType()) {
                this.pending.put(dumpArchiveEntry.getIno(), dumpArchiveEntry);
            }
            if (this.blockBuffer.length < (n = 1024 * dumpArchiveEntry.getHeaderCount())) {
                this.blockBuffer = new byte[n];
            }
            if (this.raw.read(this.blockBuffer, 0, n) != n) {
                throw new EOFException();
            }
            int n2 = 0;
            for (int i = 0; i < n - 8 && (long)i < l - 8L; i += n2) {
                int n3 = DumpArchiveUtil.convert32(this.blockBuffer, i);
                n2 = DumpArchiveUtil.convert16(this.blockBuffer, i + 4);
                byte by = this.blockBuffer[i + 6];
                String string = DumpArchiveUtil.decode(this.encoding, this.blockBuffer, i + 8, this.blockBuffer[i + 7]);
                if (".".equals(string) || "..".equals(string)) continue;
                Dirent dirent = new Dirent(n3, dumpArchiveEntry.getIno(), by, string);
                this.names.put(n3, dirent);
                for (Map.Entry<Integer, DumpArchiveEntry> entry : this.pending.entrySet()) {
                    String string2 = this.getPath(entry.getValue());
                    if (string2 == null) continue;
                    entry.getValue().setName(string2);
                    entry.getValue().setSimpleName(this.names.get(entry.getKey()).getName());
                    this.queue.add(entry.getValue());
                }
                for (DumpArchiveEntry dumpArchiveEntry2 : this.queue) {
                    this.pending.remove(dumpArchiveEntry2.getIno());
                }
            }
            byte[] byArray = this.raw.peek();
            if (!DumpArchiveUtil.verify(byArray)) {
                throw new InvalidFormatException();
            }
            dumpArchiveEntry = DumpArchiveEntry.parse(byArray);
            bl = false;
            l -= 1024L;
        }
    }

    private String getPath(DumpArchiveEntry dumpArchiveEntry) {
        Stack<String> stack = new Stack<String>();
        Dirent dirent = null;
        int n = dumpArchiveEntry.getIno();
        while (true) {
            if (!this.names.containsKey(n)) {
                stack.clear();
                break;
            }
            dirent = this.names.get(n);
            stack.push(dirent.getName());
            if (dirent.getIno() == dirent.getParentIno()) break;
            n = dirent.getParentIno();
        }
        if (stack.isEmpty()) {
            this.pending.put(dumpArchiveEntry.getIno(), dumpArchiveEntry);
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder((String)stack.pop());
        while (!stack.isEmpty()) {
            stringBuilder.append('/');
            stringBuilder.append((String)stack.pop());
        }
        return stringBuilder.toString();
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = 0;
        if (this.hasHitEOF || this.isClosed || this.entryOffset >= this.entrySize) {
            return 1;
        }
        if (this.active == null) {
            throw new IllegalStateException("No current dump entry");
        }
        if ((long)n2 + this.entryOffset > this.entrySize) {
            n2 = (int)(this.entrySize - this.entryOffset);
        }
        while (n2 > 0) {
            int n4;
            int n5 = n4 = n2 > this.readBuf.length - this.recordOffset ? this.readBuf.length - this.recordOffset : n2;
            if (this.recordOffset + n4 <= this.readBuf.length) {
                System.arraycopy(this.readBuf, this.recordOffset, byArray, n, n4);
                n3 += n4;
                this.recordOffset += n4;
                n2 -= n4;
                n += n4;
            }
            if (n2 <= 0) continue;
            if (this.readIdx >= 512) {
                byte[] byArray2 = this.raw.readRecord();
                if (!DumpArchiveUtil.verify(byArray2)) {
                    throw new InvalidFormatException();
                }
                this.active = DumpArchiveEntry.parse(byArray2);
                this.readIdx = 0;
            }
            if (!this.active.isSparseRecord(this.readIdx++)) {
                int n6 = this.raw.read(this.readBuf, 0, this.readBuf.length);
                if (n6 != this.readBuf.length) {
                    throw new EOFException();
                }
            } else {
                Arrays.fill(this.readBuf, (byte)0);
            }
            this.recordOffset = 0;
        }
        this.entryOffset += (long)n3;
        return n3;
    }

    public void close() throws IOException {
        if (!this.isClosed) {
            this.isClosed = true;
            this.raw.close();
        }
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < 32) {
            return true;
        }
        if (n >= 1024) {
            return DumpArchiveUtil.verify(byArray);
        }
        return 60012 == DumpArchiveUtil.convert32(byArray, 24);
    }

    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextEntry();
    }
}

