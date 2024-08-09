/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.GeneralPurposeBit;
import org.apache.commons.compress.archivers.zip.UnicodeCommentExtraField;
import org.apache.commons.compress.archivers.zip.UnicodePathExtraField;
import org.apache.commons.compress.archivers.zip.Zip64ExtendedInformationExtraField;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.Zip64RequiredException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipEightByteInteger;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.archivers.zip.ZipLong;
import org.apache.commons.compress.archivers.zip.ZipMethod;
import org.apache.commons.compress.archivers.zip.ZipShort;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.apache.commons.compress.utils.IOUtils;

public class ZipArchiveOutputStream
extends ArchiveOutputStream {
    static final int BUFFER_SIZE = 512;
    protected boolean finished = false;
    private static final int DEFLATER_BLOCK_SIZE = 8192;
    public static final int DEFLATED = 8;
    public static final int DEFAULT_COMPRESSION = -1;
    public static final int STORED = 0;
    static final String DEFAULT_ENCODING = "UTF8";
    @Deprecated
    public static final int EFS_FLAG = 2048;
    private static final byte[] EMPTY = new byte[0];
    private CurrentEntry entry;
    private String comment = "";
    private int level = -1;
    private boolean hasCompressionLevelChanged = false;
    private int method = 8;
    private final List<ZipArchiveEntry> entries = new LinkedList<ZipArchiveEntry>();
    private final CRC32 crc = new CRC32();
    private long written = 0L;
    private long cdOffset = 0L;
    private long cdLength = 0L;
    private static final byte[] ZERO = new byte[]{0, 0};
    private static final byte[] LZERO = new byte[]{0, 0, 0, 0};
    private final Map<ZipArchiveEntry, Long> offsets = new HashMap<ZipArchiveEntry, Long>();
    private String encoding = "UTF8";
    private ZipEncoding zipEncoding = ZipEncodingHelper.getZipEncoding("UTF8");
    protected final Deflater def = new Deflater(this.level, true);
    private final byte[] buf = new byte[512];
    private final RandomAccessFile raf;
    private final OutputStream out;
    private boolean useUTF8Flag = true;
    private boolean fallbackToUTF8 = false;
    private UnicodeExtraFieldPolicy createUnicodeExtraFields = UnicodeExtraFieldPolicy.NEVER;
    private boolean hasUsedZip64 = false;
    private Zip64Mode zip64Mode = Zip64Mode.AsNeeded;
    static final byte[] LFH_SIG = ZipLong.LFH_SIG.getBytes();
    static final byte[] DD_SIG = ZipLong.DD_SIG.getBytes();
    static final byte[] CFH_SIG = ZipLong.CFH_SIG.getBytes();
    static final byte[] EOCD_SIG = ZipLong.getBytes(101010256L);
    static final byte[] ZIP64_EOCD_SIG = ZipLong.getBytes(101075792L);
    static final byte[] ZIP64_EOCD_LOC_SIG = ZipLong.getBytes(117853008L);
    private static final byte[] ONE = ZipLong.getBytes(1L);

    public ZipArchiveOutputStream(OutputStream outputStream) {
        this.out = outputStream;
        this.raf = null;
    }

    public ZipArchiveOutputStream(File file) throws IOException {
        FileOutputStream fileOutputStream = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.setLength(0L);
        } catch (IOException iOException) {
            IOUtils.closeQuietly(randomAccessFile);
            randomAccessFile = null;
            fileOutputStream = new FileOutputStream(file);
        }
        this.out = fileOutputStream;
        this.raf = randomAccessFile;
    }

    public boolean isSeekable() {
        return this.raf != null;
    }

    public void setEncoding(String string) {
        this.encoding = string;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(string);
        if (this.useUTF8Flag && !ZipEncodingHelper.isUTF8(string)) {
            this.useUTF8Flag = false;
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setUseLanguageEncodingFlag(boolean bl) {
        this.useUTF8Flag = bl && ZipEncodingHelper.isUTF8(this.encoding);
    }

    public void setCreateUnicodeExtraFields(UnicodeExtraFieldPolicy unicodeExtraFieldPolicy) {
        this.createUnicodeExtraFields = unicodeExtraFieldPolicy;
    }

    public void setFallbackToUTF8(boolean bl) {
        this.fallbackToUTF8 = bl;
    }

    public void setUseZip64(Zip64Mode zip64Mode) {
        this.zip64Mode = zip64Mode;
    }

    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.entry != null) {
            throw new IOException("This archive contains unclosed entries.");
        }
        this.cdOffset = this.written;
        for (ZipArchiveEntry zipArchiveEntry : this.entries) {
            this.writeCentralFileHeader(zipArchiveEntry);
        }
        this.cdLength = this.written - this.cdOffset;
        this.writeZip64CentralDirectory();
        this.writeCentralDirectoryEnd();
        this.offsets.clear();
        this.entries.clear();
        this.def.end();
        this.finished = true;
    }

    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.entry == null) {
            throw new IOException("No current entry to close");
        }
        if (!CurrentEntry.access$000(this.entry)) {
            this.write(EMPTY, 0, 0);
        }
        this.flushDeflater();
        Zip64Mode zip64Mode = this.getEffectiveZip64Mode(CurrentEntry.access$100(this.entry));
        long l = this.written - CurrentEntry.access$200(this.entry);
        long l2 = this.crc.getValue();
        this.crc.reset();
        boolean bl = this.handleSizesAndCrc(l, l2, zip64Mode);
        if (this.raf != null) {
            this.rewriteSizesAndCrc(bl);
        }
        this.writeDataDescriptor(CurrentEntry.access$100(this.entry));
        this.entry = null;
    }

    private void flushDeflater() throws IOException {
        if (CurrentEntry.access$100(this.entry).getMethod() == 8) {
            this.def.finish();
            while (!this.def.finished()) {
                this.deflate();
            }
        }
    }

    private boolean handleSizesAndCrc(long l, long l2, Zip64Mode zip64Mode) throws ZipException {
        boolean bl;
        if (CurrentEntry.access$100(this.entry).getMethod() == 8) {
            CurrentEntry.access$100(this.entry).setSize(CurrentEntry.access$300(this.entry));
            CurrentEntry.access$100(this.entry).setCompressedSize(l);
            CurrentEntry.access$100(this.entry).setCrc(l2);
            this.def.reset();
        } else if (this.raf == null) {
            if (CurrentEntry.access$100(this.entry).getCrc() != l2) {
                throw new ZipException("bad CRC checksum for entry " + CurrentEntry.access$100(this.entry).getName() + ": " + Long.toHexString(CurrentEntry.access$100(this.entry).getCrc()) + " instead of " + Long.toHexString(l2));
            }
            if (CurrentEntry.access$100(this.entry).getSize() != l) {
                throw new ZipException("bad size for entry " + CurrentEntry.access$100(this.entry).getName() + ": " + CurrentEntry.access$100(this.entry).getSize() + " instead of " + l);
            }
        } else {
            CurrentEntry.access$100(this.entry).setSize(l);
            CurrentEntry.access$100(this.entry).setCompressedSize(l);
            CurrentEntry.access$100(this.entry).setCrc(l2);
        }
        boolean bl2 = bl = zip64Mode == Zip64Mode.Always || CurrentEntry.access$100(this.entry).getSize() >= 0xFFFFFFFFL || CurrentEntry.access$100(this.entry).getCompressedSize() >= 0xFFFFFFFFL;
        if (bl && zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(CurrentEntry.access$100(this.entry)));
        }
        return bl;
    }

    private void rewriteSizesAndCrc(boolean bl) throws IOException {
        long l = this.raf.getFilePointer();
        this.raf.seek(CurrentEntry.access$400(this.entry));
        this.writeOut(ZipLong.getBytes(CurrentEntry.access$100(this.entry).getCrc()));
        if (!this.hasZip64Extra(CurrentEntry.access$100(this.entry)) || !bl) {
            this.writeOut(ZipLong.getBytes(CurrentEntry.access$100(this.entry).getCompressedSize()));
            this.writeOut(ZipLong.getBytes(CurrentEntry.access$100(this.entry).getSize()));
        } else {
            this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
            this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
        }
        if (this.hasZip64Extra(CurrentEntry.access$100(this.entry))) {
            this.raf.seek(CurrentEntry.access$400(this.entry) + 12L + 4L + (long)this.getName(CurrentEntry.access$100(this.entry)).limit() + 4L);
            this.writeOut(ZipEightByteInteger.getBytes(CurrentEntry.access$100(this.entry).getSize()));
            this.writeOut(ZipEightByteInteger.getBytes(CurrentEntry.access$100(this.entry).getCompressedSize()));
            if (!bl) {
                this.raf.seek(CurrentEntry.access$400(this.entry) - 10L);
                this.writeOut(ZipShort.getBytes(10));
                CurrentEntry.access$100(this.entry).removeExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
                CurrentEntry.access$100(this.entry).setExtra();
                if (CurrentEntry.access$500(this.entry)) {
                    this.hasUsedZip64 = false;
                }
            }
        }
        this.raf.seek(l);
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.entry != null) {
            this.closeArchiveEntry();
        }
        this.entry = new CurrentEntry((ZipArchiveEntry)archiveEntry, null);
        this.entries.add(CurrentEntry.access$100(this.entry));
        this.setDefaults(CurrentEntry.access$100(this.entry));
        Zip64Mode zip64Mode = this.getEffectiveZip64Mode(CurrentEntry.access$100(this.entry));
        this.validateSizeInformation(zip64Mode);
        if (this.shouldAddZip64Extra(CurrentEntry.access$100(this.entry), zip64Mode)) {
            Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = this.getZip64Extra(CurrentEntry.access$100(this.entry));
            ZipEightByteInteger zipEightByteInteger = ZipEightByteInteger.ZERO;
            if (CurrentEntry.access$100(this.entry).getMethod() == 0 && CurrentEntry.access$100(this.entry).getSize() != -1L) {
                zipEightByteInteger = new ZipEightByteInteger(CurrentEntry.access$100(this.entry).getSize());
            }
            zip64ExtendedInformationExtraField.setSize(zipEightByteInteger);
            zip64ExtendedInformationExtraField.setCompressedSize(zipEightByteInteger);
            CurrentEntry.access$100(this.entry).setExtra();
        }
        if (CurrentEntry.access$100(this.entry).getMethod() == 8 && this.hasCompressionLevelChanged) {
            this.def.setLevel(this.level);
            this.hasCompressionLevelChanged = false;
        }
        this.writeLocalFileHeader(CurrentEntry.access$100(this.entry));
    }

    private void setDefaults(ZipArchiveEntry zipArchiveEntry) {
        if (zipArchiveEntry.getMethod() == -1) {
            zipArchiveEntry.setMethod(this.method);
        }
        if (zipArchiveEntry.getTime() == -1L) {
            zipArchiveEntry.setTime(System.currentTimeMillis());
        }
    }

    private void validateSizeInformation(Zip64Mode zip64Mode) throws ZipException {
        if (CurrentEntry.access$100(this.entry).getMethod() == 0 && this.raf == null) {
            if (CurrentEntry.access$100(this.entry).getSize() == -1L) {
                throw new ZipException("uncompressed size is required for STORED method when not writing to a file");
            }
            if (CurrentEntry.access$100(this.entry).getCrc() == -1L) {
                throw new ZipException("crc checksum is required for STORED method when not writing to a file");
            }
            CurrentEntry.access$100(this.entry).setCompressedSize(CurrentEntry.access$100(this.entry).getSize());
        }
        if ((CurrentEntry.access$100(this.entry).getSize() >= 0xFFFFFFFFL || CurrentEntry.access$100(this.entry).getCompressedSize() >= 0xFFFFFFFFL) && zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(CurrentEntry.access$100(this.entry)));
        }
    }

    private boolean shouldAddZip64Extra(ZipArchiveEntry zipArchiveEntry, Zip64Mode zip64Mode) {
        return zip64Mode == Zip64Mode.Always || zipArchiveEntry.getSize() >= 0xFFFFFFFFL || zipArchiveEntry.getCompressedSize() >= 0xFFFFFFFFL || zipArchiveEntry.getSize() == -1L && this.raf != null && zip64Mode != Zip64Mode.Never;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public void setLevel(int n) {
        if (n < -1 || n > 9) {
            throw new IllegalArgumentException("Invalid compression level: " + n);
        }
        this.hasCompressionLevelChanged = this.level != n;
        this.level = n;
    }

    public void setMethod(int n) {
        this.method = n;
    }

    public boolean canWriteEntryData(ArchiveEntry archiveEntry) {
        if (archiveEntry instanceof ZipArchiveEntry) {
            ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)archiveEntry;
            return zipArchiveEntry.getMethod() != ZipMethod.IMPLODING.getCode() && zipArchiveEntry.getMethod() != ZipMethod.UNSHRINKING.getCode() && ZipUtil.canHandleEntryData(zipArchiveEntry);
        }
        return true;
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (this.entry == null) {
            throw new IllegalStateException("No current entry");
        }
        ZipUtil.checkRequestedFeatures(CurrentEntry.access$100(this.entry));
        CurrentEntry.access$002(this.entry, true);
        if (CurrentEntry.access$100(this.entry).getMethod() == 8) {
            this.writeDeflated(byArray, n, n2);
        } else {
            this.writeOut(byArray, n, n2);
            this.written += (long)n2;
        }
        this.crc.update(byArray, n, n2);
        this.count(n2);
    }

    private void writeDeflated(byte[] byArray, int n, int n2) throws IOException {
        if (n2 > 0 && !this.def.finished()) {
            CurrentEntry.access$314(this.entry, n2);
            if (n2 <= 8192) {
                this.def.setInput(byArray, n, n2);
                this.deflateUntilInputIsNeeded();
            } else {
                int n3;
                int n4 = n2 / 8192;
                for (n3 = 0; n3 < n4; ++n3) {
                    this.def.setInput(byArray, n + n3 * 8192, 8192);
                    this.deflateUntilInputIsNeeded();
                }
                n3 = n4 * 8192;
                if (n3 < n2) {
                    this.def.setInput(byArray, n + n3, n2 - n3);
                    this.deflateUntilInputIsNeeded();
                }
            }
        }
    }

    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        this.destroy();
    }

    public void flush() throws IOException {
        if (this.out != null) {
            this.out.flush();
        }
    }

    protected final void deflate() throws IOException {
        int n = this.def.deflate(this.buf, 0, this.buf.length);
        if (n > 0) {
            this.writeOut(this.buf, 0, n);
            this.written += (long)n;
        }
    }

    protected void writeLocalFileHeader(ZipArchiveEntry zipArchiveEntry) throws IOException {
        byte[] byArray;
        boolean bl = this.zipEncoding.canEncode(zipArchiveEntry.getName());
        ByteBuffer byteBuffer = this.getName(zipArchiveEntry);
        if (this.createUnicodeExtraFields != UnicodeExtraFieldPolicy.NEVER) {
            this.addUnicodeExtraFields(zipArchiveEntry, bl, byteBuffer);
        }
        this.offsets.put(zipArchiveEntry, this.written);
        this.writeOut(LFH_SIG);
        this.written += 4L;
        int n = zipArchiveEntry.getMethod();
        this.writeVersionNeededToExtractAndGeneralPurposeBits(n, !bl && this.fallbackToUTF8, this.hasZip64Extra(zipArchiveEntry));
        this.written += 4L;
        this.writeOut(ZipShort.getBytes(n));
        this.written += 2L;
        this.writeOut(ZipUtil.toDosTime(zipArchiveEntry.getTime()));
        this.written += 4L;
        CurrentEntry.access$402(this.entry, this.written);
        if (n == 8 || this.raf != null) {
            this.writeOut(LZERO);
            if (this.hasZip64Extra(CurrentEntry.access$100(this.entry))) {
                this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
                this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
            } else {
                this.writeOut(LZERO);
                this.writeOut(LZERO);
            }
        } else {
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCrc()));
            byArray = ZipLong.ZIP64_MAGIC.getBytes();
            if (!this.hasZip64Extra(zipArchiveEntry)) {
                byArray = ZipLong.getBytes(zipArchiveEntry.getSize());
            }
            this.writeOut(byArray);
            this.writeOut(byArray);
        }
        this.written += 12L;
        this.writeOut(ZipShort.getBytes(byteBuffer.limit()));
        this.written += 2L;
        byArray = zipArchiveEntry.getLocalFileDataExtra();
        this.writeOut(ZipShort.getBytes(byArray.length));
        this.written += 2L;
        this.writeOut(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position());
        this.written += (long)byteBuffer.limit();
        this.writeOut(byArray);
        this.written += (long)byArray.length;
        CurrentEntry.access$202(this.entry, this.written);
    }

    private void addUnicodeExtraFields(ZipArchiveEntry zipArchiveEntry, boolean bl, ByteBuffer byteBuffer) throws IOException {
        String string;
        if (this.createUnicodeExtraFields == UnicodeExtraFieldPolicy.ALWAYS || !bl) {
            zipArchiveEntry.addExtraField(new UnicodePathExtraField(zipArchiveEntry.getName(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position()));
        }
        if ((string = zipArchiveEntry.getComment()) != null && !"".equals(string)) {
            boolean bl2 = this.zipEncoding.canEncode(string);
            if (this.createUnicodeExtraFields == UnicodeExtraFieldPolicy.ALWAYS || !bl2) {
                ByteBuffer byteBuffer2 = this.getEntryEncoding(zipArchiveEntry).encode(string);
                zipArchiveEntry.addExtraField(new UnicodeCommentExtraField(string, byteBuffer2.array(), byteBuffer2.arrayOffset(), byteBuffer2.limit() - byteBuffer2.position()));
            }
        }
    }

    protected void writeDataDescriptor(ZipArchiveEntry zipArchiveEntry) throws IOException {
        if (zipArchiveEntry.getMethod() != 8 || this.raf != null) {
            return;
        }
        this.writeOut(DD_SIG);
        this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCrc()));
        int n = 4;
        if (!this.hasZip64Extra(zipArchiveEntry)) {
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCompressedSize()));
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getSize()));
        } else {
            n = 8;
            this.writeOut(ZipEightByteInteger.getBytes(zipArchiveEntry.getCompressedSize()));
            this.writeOut(ZipEightByteInteger.getBytes(zipArchiveEntry.getSize()));
        }
        this.written += (long)(8 + 2 * n);
    }

    protected void writeCentralFileHeader(ZipArchiveEntry zipArchiveEntry) throws IOException {
        boolean bl;
        this.writeOut(CFH_SIG);
        this.written += 4L;
        long l = this.offsets.get(zipArchiveEntry);
        boolean bl2 = bl = this.hasZip64Extra(zipArchiveEntry) || zipArchiveEntry.getCompressedSize() >= 0xFFFFFFFFL || zipArchiveEntry.getSize() >= 0xFFFFFFFFL || l >= 0xFFFFFFFFL;
        if (bl && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
        }
        this.handleZip64Extra(zipArchiveEntry, l, bl);
        this.writeOut(ZipShort.getBytes(zipArchiveEntry.getPlatform() << 8 | (!this.hasUsedZip64 ? 20 : 45)));
        this.written += 2L;
        int n = zipArchiveEntry.getMethod();
        boolean bl3 = this.zipEncoding.canEncode(zipArchiveEntry.getName());
        this.writeVersionNeededToExtractAndGeneralPurposeBits(n, !bl3 && this.fallbackToUTF8, bl);
        this.written += 4L;
        this.writeOut(ZipShort.getBytes(n));
        this.written += 2L;
        this.writeOut(ZipUtil.toDosTime(zipArchiveEntry.getTime()));
        this.written += 4L;
        this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCrc()));
        if (zipArchiveEntry.getCompressedSize() >= 0xFFFFFFFFL || zipArchiveEntry.getSize() >= 0xFFFFFFFFL) {
            this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
            this.writeOut(ZipLong.ZIP64_MAGIC.getBytes());
        } else {
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getCompressedSize()));
            this.writeOut(ZipLong.getBytes(zipArchiveEntry.getSize()));
        }
        this.written += 12L;
        ByteBuffer byteBuffer = this.getName(zipArchiveEntry);
        this.writeOut(ZipShort.getBytes(byteBuffer.limit()));
        this.written += 2L;
        byte[] byArray = zipArchiveEntry.getCentralDirectoryExtra();
        this.writeOut(ZipShort.getBytes(byArray.length));
        this.written += 2L;
        String string = zipArchiveEntry.getComment();
        if (string == null) {
            string = "";
        }
        ByteBuffer byteBuffer2 = this.getEntryEncoding(zipArchiveEntry).encode(string);
        this.writeOut(ZipShort.getBytes(byteBuffer2.limit()));
        this.written += 2L;
        this.writeOut(ZERO);
        this.written += 2L;
        this.writeOut(ZipShort.getBytes(zipArchiveEntry.getInternalAttributes()));
        this.written += 2L;
        this.writeOut(ZipLong.getBytes(zipArchiveEntry.getExternalAttributes()));
        this.written += 4L;
        this.writeOut(ZipLong.getBytes(Math.min(l, 0xFFFFFFFFL)));
        this.written += 4L;
        this.writeOut(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position());
        this.written += (long)byteBuffer.limit();
        this.writeOut(byArray);
        this.written += (long)byArray.length;
        this.writeOut(byteBuffer2.array(), byteBuffer2.arrayOffset(), byteBuffer2.limit() - byteBuffer2.position());
        this.written += (long)byteBuffer2.limit();
    }

    private void handleZip64Extra(ZipArchiveEntry zipArchiveEntry, long l, boolean bl) {
        if (bl) {
            Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = this.getZip64Extra(zipArchiveEntry);
            if (zipArchiveEntry.getCompressedSize() >= 0xFFFFFFFFL || zipArchiveEntry.getSize() >= 0xFFFFFFFFL) {
                zip64ExtendedInformationExtraField.setCompressedSize(new ZipEightByteInteger(zipArchiveEntry.getCompressedSize()));
                zip64ExtendedInformationExtraField.setSize(new ZipEightByteInteger(zipArchiveEntry.getSize()));
            } else {
                zip64ExtendedInformationExtraField.setCompressedSize(null);
                zip64ExtendedInformationExtraField.setSize(null);
            }
            if (l >= 0xFFFFFFFFL) {
                zip64ExtendedInformationExtraField.setRelativeHeaderOffset(new ZipEightByteInteger(l));
            }
            zipArchiveEntry.setExtra();
        }
    }

    protected void writeCentralDirectoryEnd() throws IOException {
        this.writeOut(EOCD_SIG);
        this.writeOut(ZERO);
        this.writeOut(ZERO);
        int n = this.entries.size();
        if (n > 65535 && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive contains more than 65535 entries.");
        }
        if (this.cdOffset > 0xFFFFFFFFL && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
        }
        byte[] byArray = ZipShort.getBytes(Math.min(n, 65535));
        this.writeOut(byArray);
        this.writeOut(byArray);
        this.writeOut(ZipLong.getBytes(Math.min(this.cdLength, 0xFFFFFFFFL)));
        this.writeOut(ZipLong.getBytes(Math.min(this.cdOffset, 0xFFFFFFFFL)));
        ByteBuffer byteBuffer = this.zipEncoding.encode(this.comment);
        this.writeOut(ZipShort.getBytes(byteBuffer.limit()));
        this.writeOut(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position());
    }

    protected void writeZip64CentralDirectory() throws IOException {
        if (this.zip64Mode == Zip64Mode.Never) {
            return;
        }
        if (!(this.hasUsedZip64 || this.cdOffset < 0xFFFFFFFFL && this.cdLength < 0xFFFFFFFFL && this.entries.size() < 65535)) {
            this.hasUsedZip64 = true;
        }
        if (!this.hasUsedZip64) {
            return;
        }
        long l = this.written;
        this.writeOut(ZIP64_EOCD_SIG);
        this.writeOut(ZipEightByteInteger.getBytes(44L));
        this.writeOut(ZipShort.getBytes(45));
        this.writeOut(ZipShort.getBytes(45));
        this.writeOut(LZERO);
        this.writeOut(LZERO);
        byte[] byArray = ZipEightByteInteger.getBytes(this.entries.size());
        this.writeOut(byArray);
        this.writeOut(byArray);
        this.writeOut(ZipEightByteInteger.getBytes(this.cdLength));
        this.writeOut(ZipEightByteInteger.getBytes(this.cdOffset));
        this.writeOut(ZIP64_EOCD_LOC_SIG);
        this.writeOut(LZERO);
        this.writeOut(ZipEightByteInteger.getBytes(l));
        this.writeOut(ONE);
    }

    protected final void writeOut(byte[] byArray) throws IOException {
        this.writeOut(byArray, 0, byArray.length);
    }

    protected final void writeOut(byte[] byArray, int n, int n2) throws IOException {
        if (this.raf != null) {
            this.raf.write(byArray, n, n2);
        } else {
            this.out.write(byArray, n, n2);
        }
    }

    private void deflateUntilInputIsNeeded() throws IOException {
        while (!this.def.needsInput()) {
            this.deflate();
        }
    }

    private void writeVersionNeededToExtractAndGeneralPurposeBits(int n, boolean bl, boolean bl2) throws IOException {
        int n2 = 10;
        GeneralPurposeBit generalPurposeBit = new GeneralPurposeBit();
        generalPurposeBit.useUTF8ForNames(this.useUTF8Flag || bl);
        if (n == 8 && this.raf == null) {
            n2 = 20;
            generalPurposeBit.useDataDescriptor(false);
        }
        if (bl2) {
            n2 = 45;
        }
        this.writeOut(ZipShort.getBytes(n2));
        this.writeOut(generalPurposeBit.encode());
    }

    public ArchiveEntry createArchiveEntry(File file, String string) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new ZipArchiveEntry(file, string);
    }

    private Zip64ExtendedInformationExtraField getZip64Extra(ZipArchiveEntry zipArchiveEntry) {
        if (this.entry != null) {
            CurrentEntry.access$502(this.entry, !this.hasUsedZip64);
        }
        this.hasUsedZip64 = true;
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField)zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (zip64ExtendedInformationExtraField == null) {
            zip64ExtendedInformationExtraField = new Zip64ExtendedInformationExtraField();
        }
        zipArchiveEntry.addAsFirstExtraField(zip64ExtendedInformationExtraField);
        return zip64ExtendedInformationExtraField;
    }

    private boolean hasZip64Extra(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID) != null;
    }

    private Zip64Mode getEffectiveZip64Mode(ZipArchiveEntry zipArchiveEntry) {
        if (this.zip64Mode != Zip64Mode.AsNeeded || this.raf != null || zipArchiveEntry.getMethod() != 8 || zipArchiveEntry.getSize() != -1L) {
            return this.zip64Mode;
        }
        return Zip64Mode.Never;
    }

    private ZipEncoding getEntryEncoding(ZipArchiveEntry zipArchiveEntry) {
        boolean bl = this.zipEncoding.canEncode(zipArchiveEntry.getName());
        return !bl && this.fallbackToUTF8 ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
    }

    private ByteBuffer getName(ZipArchiveEntry zipArchiveEntry) throws IOException {
        return this.getEntryEncoding(zipArchiveEntry).encode(zipArchiveEntry.getName());
    }

    void destroy() throws IOException {
        if (this.raf != null) {
            this.raf.close();
        }
        if (this.out != null) {
            this.out.close();
        }
    }

    static class 1 {
    }

    private static final class CurrentEntry {
        private final ZipArchiveEntry entry;
        private long localDataStart = 0L;
        private long dataStart = 0L;
        private long bytesRead = 0L;
        private boolean causedUseOfZip64 = false;
        private boolean hasWritten;

        private CurrentEntry(ZipArchiveEntry zipArchiveEntry) {
            this.entry = zipArchiveEntry;
        }

        static boolean access$000(CurrentEntry currentEntry) {
            return currentEntry.hasWritten;
        }

        static ZipArchiveEntry access$100(CurrentEntry currentEntry) {
            return currentEntry.entry;
        }

        static long access$200(CurrentEntry currentEntry) {
            return currentEntry.dataStart;
        }

        static long access$300(CurrentEntry currentEntry) {
            return currentEntry.bytesRead;
        }

        static long access$400(CurrentEntry currentEntry) {
            return currentEntry.localDataStart;
        }

        static boolean access$500(CurrentEntry currentEntry) {
            return currentEntry.causedUseOfZip64;
        }

        CurrentEntry(ZipArchiveEntry zipArchiveEntry, 1 var2_2) {
            this(zipArchiveEntry);
        }

        static boolean access$002(CurrentEntry currentEntry, boolean bl) {
            currentEntry.hasWritten = bl;
            return currentEntry.hasWritten;
        }

        static long access$314(CurrentEntry currentEntry, long l) {
            return currentEntry.bytesRead += l;
        }

        static long access$402(CurrentEntry currentEntry, long l) {
            currentEntry.localDataStart = l;
            return currentEntry.localDataStart;
        }

        static long access$202(CurrentEntry currentEntry, long l) {
            currentEntry.dataStart = l;
            return currentEntry.dataStart;
        }

        static boolean access$502(CurrentEntry currentEntry, boolean bl) {
            currentEntry.causedUseOfZip64 = bl;
            return currentEntry.causedUseOfZip64;
        }
    }

    public static final class UnicodeExtraFieldPolicy {
        public static final UnicodeExtraFieldPolicy ALWAYS = new UnicodeExtraFieldPolicy("always");
        public static final UnicodeExtraFieldPolicy NEVER = new UnicodeExtraFieldPolicy("never");
        public static final UnicodeExtraFieldPolicy NOT_ENCODEABLE = new UnicodeExtraFieldPolicy("not encodeable");
        private final String name;

        private UnicodeExtraFieldPolicy(String string) {
            this.name = string;
        }

        public String toString() {
            return this.name;
        }
    }
}

