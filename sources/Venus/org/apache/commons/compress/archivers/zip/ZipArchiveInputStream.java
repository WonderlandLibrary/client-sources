/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ExplodingInputStream;
import org.apache.commons.compress.archivers.zip.GeneralPurposeBit;
import org.apache.commons.compress.archivers.zip.UnshrinkingInputStream;
import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException;
import org.apache.commons.compress.archivers.zip.Zip64ExtendedInformationExtraField;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipEightByteInteger;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.archivers.zip.ZipLong;
import org.apache.commons.compress.archivers.zip.ZipMethod;
import org.apache.commons.compress.archivers.zip.ZipShort;
import org.apache.commons.compress.archivers.zip.ZipUtil;
import org.apache.commons.compress.utils.IOUtils;

public class ZipArchiveInputStream
extends ArchiveInputStream {
    private final ZipEncoding zipEncoding;
    private final boolean useUnicodeExtraFields;
    private final InputStream in;
    private final Inflater inf = new Inflater(true);
    private final ByteBuffer buf = ByteBuffer.allocate(512);
    private CurrentEntry current = null;
    private boolean closed = false;
    private boolean hitCentralDirectory = false;
    private ByteArrayInputStream lastStoredEntry = null;
    private boolean allowStoredEntriesWithDataDescriptor = false;
    private static final int LFH_LEN = 30;
    private static final int CFH_LEN = 46;
    private static final long TWO_EXP_32 = 0x100000000L;
    private final byte[] LFH_BUF = new byte[30];
    private final byte[] SKIP_BUF = new byte[1024];
    private final byte[] SHORT_BUF = new byte[2];
    private final byte[] WORD_BUF = new byte[4];
    private final byte[] TWO_DWORD_BUF = new byte[16];
    private int entriesRead = 0;
    private static final byte[] LFH = ZipLong.LFH_SIG.getBytes();
    private static final byte[] CFH = ZipLong.CFH_SIG.getBytes();
    private static final byte[] DD = ZipLong.DD_SIG.getBytes();

    public ZipArchiveInputStream(InputStream inputStream) {
        this(inputStream, "UTF8");
    }

    public ZipArchiveInputStream(InputStream inputStream, String string) {
        this(inputStream, string, true);
    }

    public ZipArchiveInputStream(InputStream inputStream, String string, boolean bl) {
        this(inputStream, string, bl, false);
    }

    public ZipArchiveInputStream(InputStream inputStream, String string, boolean bl, boolean bl2) {
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(string);
        this.useUnicodeExtraFields = bl;
        this.in = new PushbackInputStream(inputStream, this.buf.capacity());
        this.allowStoredEntriesWithDataDescriptor = bl2;
        this.buf.limit(0);
    }

    public ZipArchiveEntry getNextZipEntry() throws IOException {
        boolean bl = true;
        if (this.closed || this.hitCentralDirectory) {
            return null;
        }
        if (this.current != null) {
            this.closeEntry();
            bl = false;
        }
        try {
            if (bl) {
                this.readFirstLocalFileHeader(this.LFH_BUF);
            } else {
                this.readFully(this.LFH_BUF);
            }
        } catch (EOFException eOFException) {
            return null;
        }
        ZipLong zipLong = new ZipLong(this.LFH_BUF);
        if (zipLong.equals(ZipLong.CFH_SIG) || zipLong.equals(ZipLong.AED_SIG)) {
            this.hitCentralDirectory = true;
            this.skipRemainderOfArchive();
        }
        if (!zipLong.equals(ZipLong.LFH_SIG)) {
            return null;
        }
        int n = 4;
        this.current = new CurrentEntry(null);
        int n2 = ZipShort.getValue(this.LFH_BUF, n);
        CurrentEntry.access$100(this.current).setPlatform(n2 >> 8 & 0xF);
        GeneralPurposeBit generalPurposeBit = GeneralPurposeBit.parse(this.LFH_BUF, n += 2);
        boolean bl2 = generalPurposeBit.usesUTF8ForNames();
        ZipEncoding zipEncoding = bl2 ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
        CurrentEntry.access$202(this.current, generalPurposeBit.usesDataDescriptor());
        CurrentEntry.access$100(this.current).setGeneralPurposeBit(generalPurposeBit);
        CurrentEntry.access$100(this.current).setMethod(ZipShort.getValue(this.LFH_BUF, n += 2));
        long l = ZipUtil.dosToJavaTime(ZipLong.getValue(this.LFH_BUF, n += 2));
        CurrentEntry.access$100(this.current).setTime(l);
        n += 4;
        ZipLong zipLong2 = null;
        ZipLong zipLong3 = null;
        if (!CurrentEntry.access$200(this.current)) {
            CurrentEntry.access$100(this.current).setCrc(ZipLong.getValue(this.LFH_BUF, n));
            zipLong3 = new ZipLong(this.LFH_BUF, n += 4);
            zipLong2 = new ZipLong(this.LFH_BUF, n += 4);
            n += 4;
        } else {
            n += 12;
        }
        int n3 = ZipShort.getValue(this.LFH_BUF, n);
        int n4 = ZipShort.getValue(this.LFH_BUF, n += 2);
        n += 2;
        byte[] byArray = new byte[n3];
        this.readFully(byArray);
        CurrentEntry.access$100(this.current).setName(zipEncoding.decode(byArray), byArray);
        byte[] byArray2 = new byte[n4];
        this.readFully(byArray2);
        CurrentEntry.access$100(this.current).setExtra(byArray2);
        if (!bl2 && this.useUnicodeExtraFields) {
            ZipUtil.setNameAndCommentFromExtraFields(CurrentEntry.access$100(this.current), byArray, null);
        }
        this.processZip64Extra(zipLong2, zipLong3);
        if (CurrentEntry.access$100(this.current).getCompressedSize() != -1L) {
            if (CurrentEntry.access$100(this.current).getMethod() == ZipMethod.UNSHRINKING.getCode()) {
                CurrentEntry.access$302(this.current, new UnshrinkingInputStream(new BoundedInputStream(this, this.in, CurrentEntry.access$100(this.current).getCompressedSize())));
            } else if (CurrentEntry.access$100(this.current).getMethod() == ZipMethod.IMPLODING.getCode()) {
                CurrentEntry.access$302(this.current, new ExplodingInputStream(CurrentEntry.access$100(this.current).getGeneralPurposeBit().getSlidingDictionarySize(), CurrentEntry.access$100(this.current).getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BoundedInputStream(this, this.in, CurrentEntry.access$100(this.current).getCompressedSize())));
            }
        }
        ++this.entriesRead;
        return CurrentEntry.access$100(this.current);
    }

    private void readFirstLocalFileHeader(byte[] byArray) throws IOException {
        this.readFully(byArray);
        ZipLong zipLong = new ZipLong(byArray);
        if (zipLong.equals(ZipLong.DD_SIG)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.SPLITTING);
        }
        if (zipLong.equals(ZipLong.SINGLE_SEGMENT_SPLIT_MARKER)) {
            byte[] byArray2 = new byte[4];
            this.readFully(byArray2);
            System.arraycopy(byArray, 4, byArray, 0, 26);
            System.arraycopy(byArray2, 0, byArray, 26, 4);
        }
    }

    private void processZip64Extra(ZipLong zipLong, ZipLong zipLong2) {
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField)CurrentEntry.access$100(this.current).getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        CurrentEntry.access$402(this.current, zip64ExtendedInformationExtraField != null);
        if (!CurrentEntry.access$200(this.current)) {
            if (zip64ExtendedInformationExtraField != null && (zipLong2.equals(ZipLong.ZIP64_MAGIC) || zipLong.equals(ZipLong.ZIP64_MAGIC))) {
                CurrentEntry.access$100(this.current).setCompressedSize(zip64ExtendedInformationExtraField.getCompressedSize().getLongValue());
                CurrentEntry.access$100(this.current).setSize(zip64ExtendedInformationExtraField.getSize().getLongValue());
            } else {
                CurrentEntry.access$100(this.current).setCompressedSize(zipLong2.getValue());
                CurrentEntry.access$100(this.current).setSize(zipLong.getValue());
            }
        }
    }

    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextZipEntry();
    }

    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        if (archiveEntry instanceof ZipArchiveEntry) {
            ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)archiveEntry;
            return ZipUtil.canHandleEntryData(zipArchiveEntry) && this.supportsDataDescriptorFor(zipArchiveEntry);
        }
        return true;
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        if (this.current == null) {
            return 1;
        }
        if (n > byArray.length || n2 < 0 || n < 0 || byArray.length - n < n2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        ZipUtil.checkRequestedFeatures(CurrentEntry.access$100(this.current));
        if (!this.supportsDataDescriptorFor(CurrentEntry.access$100(this.current))) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.DATA_DESCRIPTOR, CurrentEntry.access$100(this.current));
        }
        if (CurrentEntry.access$100(this.current).getMethod() == 0) {
            n3 = this.readStored(byArray, n, n2);
        } else if (CurrentEntry.access$100(this.current).getMethod() == 8) {
            n3 = this.readDeflated(byArray, n, n2);
        } else if (CurrentEntry.access$100(this.current).getMethod() == ZipMethod.UNSHRINKING.getCode() || CurrentEntry.access$100(this.current).getMethod() == ZipMethod.IMPLODING.getCode()) {
            n3 = CurrentEntry.access$300(this.current).read(byArray, n, n2);
        } else {
            throw new UnsupportedZipFeatureException(ZipMethod.getMethodByCode(CurrentEntry.access$100(this.current).getMethod()), CurrentEntry.access$100(this.current));
        }
        if (n3 >= 0) {
            CurrentEntry.access$500(this.current).update(byArray, n, n3);
        }
        return n3;
    }

    private int readStored(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (CurrentEntry.access$200(this.current)) {
            if (this.lastStoredEntry == null) {
                this.readStoredEntry();
            }
            return this.lastStoredEntry.read(byArray, n, n2);
        }
        long l = CurrentEntry.access$100(this.current).getSize();
        if (CurrentEntry.access$600(this.current) >= l) {
            return 1;
        }
        if (this.buf.position() >= this.buf.limit()) {
            this.buf.position(0);
            n3 = this.in.read(this.buf.array());
            if (n3 == -1) {
                return 1;
            }
            this.buf.limit(n3);
            this.count(n3);
            CurrentEntry.access$714(this.current, n3);
        }
        n3 = Math.min(this.buf.remaining(), n2);
        if (l - CurrentEntry.access$600(this.current) < (long)n3) {
            n3 = (int)(l - CurrentEntry.access$600(this.current));
        }
        this.buf.get(byArray, n, n3);
        CurrentEntry.access$614(this.current, n3);
        return n3;
    }

    private int readDeflated(byte[] byArray, int n, int n2) throws IOException {
        int n3 = this.readFromInflater(byArray, n, n2);
        if (n3 <= 0) {
            if (this.inf.finished()) {
                return 1;
            }
            if (this.inf.needsDictionary()) {
                throw new ZipException("This archive needs a preset dictionary which is not supported by Commons Compress.");
            }
            if (n3 == -1) {
                throw new IOException("Truncated ZIP file");
            }
        }
        return n3;
    }

    private int readFromInflater(byte[] byArray, int n, int n2) throws IOException {
        int n3 = 0;
        do {
            if (this.inf.needsInput()) {
                int n4 = this.fill();
                if (n4 > 0) {
                    CurrentEntry.access$714(this.current, this.buf.limit());
                } else {
                    if (n4 != -1) break;
                    return 1;
                }
            }
            try {
                n3 = this.inf.inflate(byArray, n, n2);
            } catch (DataFormatException dataFormatException) {
                throw (IOException)new ZipException(dataFormatException.getMessage()).initCause(dataFormatException);
            }
        } while (n3 == 0 && this.inf.needsInput());
        return n3;
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            this.in.close();
            this.inf.end();
        }
    }

    public long skip(long l) throws IOException {
        if (l >= 0L) {
            long l2;
            int n;
            for (l2 = 0L; l2 < l; l2 += (long)n) {
                long l3 = l - l2;
                n = this.read(this.SKIP_BUF, 0, (int)((long)this.SKIP_BUF.length > l3 ? l3 : (long)this.SKIP_BUF.length));
                if (n != -1) continue;
                return l2;
            }
            return l2;
        }
        throw new IllegalArgumentException();
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < ZipArchiveOutputStream.LFH_SIG.length) {
            return true;
        }
        return ZipArchiveInputStream.checksig(byArray, ZipArchiveOutputStream.LFH_SIG) || ZipArchiveInputStream.checksig(byArray, ZipArchiveOutputStream.EOCD_SIG) || ZipArchiveInputStream.checksig(byArray, ZipArchiveOutputStream.DD_SIG) || ZipArchiveInputStream.checksig(byArray, ZipLong.SINGLE_SEGMENT_SPLIT_MARKER.getBytes());
    }

    private static boolean checksig(byte[] byArray, byte[] byArray2) {
        for (int i = 0; i < byArray2.length; ++i) {
            if (byArray[i] == byArray2[i]) continue;
            return true;
        }
        return false;
    }

    private void closeEntry() throws IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        if (this.current == null) {
            return;
        }
        if (CurrentEntry.access$700(this.current) <= CurrentEntry.access$100(this.current).getCompressedSize() && !CurrentEntry.access$200(this.current)) {
            this.drainCurrentEntryData();
        } else {
            this.skip(Long.MAX_VALUE);
            long l = CurrentEntry.access$100(this.current).getMethod() == 8 ? this.getBytesInflated() : CurrentEntry.access$600(this.current);
            int n = (int)(CurrentEntry.access$700(this.current) - l);
            if (n > 0) {
                this.pushback(this.buf.array(), this.buf.limit() - n, n);
            }
        }
        if (this.lastStoredEntry == null && CurrentEntry.access$200(this.current)) {
            this.readDataDescriptor();
        }
        this.inf.reset();
        this.buf.clear().flip();
        this.current = null;
        this.lastStoredEntry = null;
    }

    private void drainCurrentEntryData() throws IOException {
        long l;
        for (long i = CurrentEntry.access$100(this.current).getCompressedSize() - CurrentEntry.access$700(this.current); i > 0L; i -= l) {
            l = this.in.read(this.buf.array(), 0, (int)Math.min((long)this.buf.capacity(), i));
            if (l < 0L) {
                throw new EOFException("Truncated ZIP entry: " + CurrentEntry.access$100(this.current).getName());
            }
            this.count(l);
        }
    }

    private long getBytesInflated() {
        long l = this.inf.getBytesRead();
        if (CurrentEntry.access$700(this.current) >= 0x100000000L) {
            while (l + 0x100000000L <= CurrentEntry.access$700(this.current)) {
                l += 0x100000000L;
            }
        }
        return l;
    }

    private int fill() throws IOException {
        if (this.closed) {
            throw new IOException("The stream is closed");
        }
        int n = this.in.read(this.buf.array());
        if (n > 0) {
            this.buf.limit(n);
            this.count(this.buf.limit());
            this.inf.setInput(this.buf.array(), 0, this.buf.limit());
        }
        return n;
    }

    private void readFully(byte[] byArray) throws IOException {
        int n = IOUtils.readFully(this.in, byArray);
        this.count(n);
        if (n < byArray.length) {
            throw new EOFException();
        }
    }

    private void readDataDescriptor() throws IOException {
        this.readFully(this.WORD_BUF);
        ZipLong zipLong = new ZipLong(this.WORD_BUF);
        if (ZipLong.DD_SIG.equals(zipLong)) {
            this.readFully(this.WORD_BUF);
            zipLong = new ZipLong(this.WORD_BUF);
        }
        CurrentEntry.access$100(this.current).setCrc(zipLong.getValue());
        this.readFully(this.TWO_DWORD_BUF);
        ZipLong zipLong2 = new ZipLong(this.TWO_DWORD_BUF, 8);
        if (zipLong2.equals(ZipLong.CFH_SIG) || zipLong2.equals(ZipLong.LFH_SIG)) {
            this.pushback(this.TWO_DWORD_BUF, 8, 8);
            CurrentEntry.access$100(this.current).setCompressedSize(ZipLong.getValue(this.TWO_DWORD_BUF));
            CurrentEntry.access$100(this.current).setSize(ZipLong.getValue(this.TWO_DWORD_BUF, 4));
        } else {
            CurrentEntry.access$100(this.current).setCompressedSize(ZipEightByteInteger.getLongValue(this.TWO_DWORD_BUF));
            CurrentEntry.access$100(this.current).setSize(ZipEightByteInteger.getLongValue(this.TWO_DWORD_BUF, 8));
        }
    }

    private boolean supportsDataDescriptorFor(ZipArchiveEntry zipArchiveEntry) {
        return !zipArchiveEntry.getGeneralPurposeBit().usesDataDescriptor() || this.allowStoredEntriesWithDataDescriptor && zipArchiveEntry.getMethod() == 0 || zipArchiveEntry.getMethod() == 8;
    }

    private void readStoredEntry() throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n2 = 0;
        boolean bl = false;
        int n3 = n = CurrentEntry.access$400(this.current) ? 20 : 12;
        while (!bl) {
            int n4 = this.in.read(this.buf.array(), n2, 512 - n2);
            if (n4 <= 0) {
                throw new IOException("Truncated ZIP file");
            }
            if (n4 + n2 < 4) {
                n2 += n4;
                continue;
            }
            bl = this.bufferContainsSignature(byteArrayOutputStream, n2, n4, n);
            if (bl) continue;
            n2 = this.cacheBytesRead(byteArrayOutputStream, n2, n4, n);
        }
        byte[] byArray = byteArrayOutputStream.toByteArray();
        this.lastStoredEntry = new ByteArrayInputStream(byArray);
    }

    private boolean bufferContainsSignature(ByteArrayOutputStream byteArrayOutputStream, int n, int n2, int n3) throws IOException {
        boolean bl = false;
        int n4 = 0;
        for (int i = 0; !bl && i < n2 - 4; ++i) {
            if (this.buf.array()[i] != LFH[0] || this.buf.array()[i + 1] != LFH[1]) continue;
            if (this.buf.array()[i + 2] == LFH[2] && this.buf.array()[i + 3] == LFH[3] || this.buf.array()[i] == CFH[2] && this.buf.array()[i + 3] == CFH[3]) {
                n4 = n + n2 - i - n3;
                bl = true;
            } else if (this.buf.array()[i + 2] == DD[2] && this.buf.array()[i + 3] == DD[3]) {
                n4 = n + n2 - i;
                bl = true;
            }
            if (!bl) continue;
            this.pushback(this.buf.array(), n + n2 - n4, n4);
            byteArrayOutputStream.write(this.buf.array(), 0, i);
            this.readDataDescriptor();
        }
        return bl;
    }

    private int cacheBytesRead(ByteArrayOutputStream byteArrayOutputStream, int n, int n2, int n3) {
        int n4 = n + n2 - n3 - 3;
        if (n4 > 0) {
            byteArrayOutputStream.write(this.buf.array(), 0, n4);
            System.arraycopy(this.buf.array(), n4, this.buf.array(), 0, n3 + 3);
            n = n3 + 3;
        } else {
            n += n2;
        }
        return n;
    }

    private void pushback(byte[] byArray, int n, int n2) throws IOException {
        ((PushbackInputStream)this.in).unread(byArray, n, n2);
        this.pushedBackBytes(n2);
    }

    private void skipRemainderOfArchive() throws IOException {
        this.realSkip(this.entriesRead * 46 - 30);
        this.findEocdRecord();
        this.realSkip(16L);
        this.readFully(this.SHORT_BUF);
        this.realSkip(ZipShort.getValue(this.SHORT_BUF));
    }

    private void findEocdRecord() throws IOException {
        int n = -1;
        boolean bl = false;
        while (bl || (n = this.readOneByte()) > -1) {
            bl = false;
            if (!this.isFirstByteOfEocdSig(n)) continue;
            n = this.readOneByte();
            if (n != ZipArchiveOutputStream.EOCD_SIG[1]) {
                if (n == -1) break;
                bl = this.isFirstByteOfEocdSig(n);
                continue;
            }
            n = this.readOneByte();
            if (n != ZipArchiveOutputStream.EOCD_SIG[2]) {
                if (n == -1) break;
                bl = this.isFirstByteOfEocdSig(n);
                continue;
            }
            n = this.readOneByte();
            if (n == -1 || n == ZipArchiveOutputStream.EOCD_SIG[3]) break;
            bl = this.isFirstByteOfEocdSig(n);
        }
    }

    private void realSkip(long l) throws IOException {
        if (l >= 0L) {
            int n;
            for (long i = 0L; i < l; i += (long)n) {
                long l2 = l - i;
                n = this.in.read(this.SKIP_BUF, 0, (int)((long)this.SKIP_BUF.length > l2 ? l2 : (long)this.SKIP_BUF.length));
                if (n == -1) {
                    return;
                }
                this.count(n);
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    private int readOneByte() throws IOException {
        int n = this.in.read();
        if (n != -1) {
            this.count(1);
        }
        return n;
    }

    private boolean isFirstByteOfEocdSig(int n) {
        return n == ZipArchiveOutputStream.EOCD_SIG[0];
    }

    static void access$800(ZipArchiveInputStream zipArchiveInputStream, int n) {
        zipArchiveInputStream.count(n);
    }

    static CurrentEntry access$900(ZipArchiveInputStream zipArchiveInputStream) {
        return zipArchiveInputStream.current;
    }

    static void access$1000(ZipArchiveInputStream zipArchiveInputStream, int n) {
        zipArchiveInputStream.count(n);
    }

    static class 1 {
    }

    private class BoundedInputStream
    extends InputStream {
        private final InputStream in;
        private final long max;
        private long pos;
        final ZipArchiveInputStream this$0;

        public BoundedInputStream(ZipArchiveInputStream zipArchiveInputStream, InputStream inputStream, long l) {
            this.this$0 = zipArchiveInputStream;
            this.pos = 0L;
            this.max = l;
            this.in = inputStream;
        }

        public int read() throws IOException {
            if (this.max >= 0L && this.pos >= this.max) {
                return 1;
            }
            int n = this.in.read();
            ++this.pos;
            ZipArchiveInputStream.access$800(this.this$0, 1);
            CurrentEntry.access$708(ZipArchiveInputStream.access$900(this.this$0));
            return n;
        }

        public int read(byte[] byArray) throws IOException {
            return this.read(byArray, 0, byArray.length);
        }

        public int read(byte[] byArray, int n, int n2) throws IOException {
            if (this.max >= 0L && this.pos >= this.max) {
                return 1;
            }
            long l = this.max >= 0L ? Math.min((long)n2, this.max - this.pos) : (long)n2;
            int n3 = this.in.read(byArray, n, (int)l);
            if (n3 == -1) {
                return 1;
            }
            this.pos += (long)n3;
            ZipArchiveInputStream.access$1000(this.this$0, n3);
            CurrentEntry.access$714(ZipArchiveInputStream.access$900(this.this$0), n3);
            return n3;
        }

        public long skip(long l) throws IOException {
            long l2 = this.max >= 0L ? Math.min(l, this.max - this.pos) : l;
            long l3 = this.in.skip(l2);
            this.pos += l3;
            return l3;
        }

        public int available() throws IOException {
            if (this.max >= 0L && this.pos >= this.max) {
                return 1;
            }
            return this.in.available();
        }
    }

    private static final class CurrentEntry {
        private final ZipArchiveEntry entry = new ZipArchiveEntry();
        private boolean hasDataDescriptor;
        private boolean usesZip64;
        private long bytesRead;
        private long bytesReadFromStream;
        private final CRC32 crc = new CRC32();
        private InputStream in;

        private CurrentEntry() {
        }

        CurrentEntry(1 var1_1) {
            this();
        }

        static ZipArchiveEntry access$100(CurrentEntry currentEntry) {
            return currentEntry.entry;
        }

        static boolean access$202(CurrentEntry currentEntry, boolean bl) {
            currentEntry.hasDataDescriptor = bl;
            return currentEntry.hasDataDescriptor;
        }

        static boolean access$200(CurrentEntry currentEntry) {
            return currentEntry.hasDataDescriptor;
        }

        static InputStream access$302(CurrentEntry currentEntry, InputStream inputStream) {
            currentEntry.in = inputStream;
            return currentEntry.in;
        }

        static boolean access$402(CurrentEntry currentEntry, boolean bl) {
            currentEntry.usesZip64 = bl;
            return currentEntry.usesZip64;
        }

        static InputStream access$300(CurrentEntry currentEntry) {
            return currentEntry.in;
        }

        static CRC32 access$500(CurrentEntry currentEntry) {
            return currentEntry.crc;
        }

        static long access$600(CurrentEntry currentEntry) {
            return currentEntry.bytesRead;
        }

        static long access$714(CurrentEntry currentEntry, long l) {
            return currentEntry.bytesReadFromStream += l;
        }

        static long access$614(CurrentEntry currentEntry, long l) {
            return currentEntry.bytesRead += l;
        }

        static long access$700(CurrentEntry currentEntry) {
            return currentEntry.bytesReadFromStream;
        }

        static boolean access$400(CurrentEntry currentEntry) {
            return currentEntry.usesZip64;
        }

        static long access$708(CurrentEntry currentEntry) {
            return currentEntry.bytesReadFromStream++;
        }
    }
}

