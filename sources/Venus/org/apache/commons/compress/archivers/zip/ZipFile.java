/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.ExplodingInputStream;
import org.apache.commons.compress.archivers.zip.GeneralPurposeBit;
import org.apache.commons.compress.archivers.zip.UnshrinkingInputStream;
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

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ZipFile
implements Closeable {
    private static final int HASH_SIZE = 509;
    static final int NIBLET_MASK = 15;
    static final int BYTE_SHIFT = 8;
    private static final int POS_0 = 0;
    private static final int POS_1 = 1;
    private static final int POS_2 = 2;
    private static final int POS_3 = 3;
    private final List<ZipArchiveEntry> entries = new LinkedList<ZipArchiveEntry>();
    private final Map<String, LinkedList<ZipArchiveEntry>> nameMap = new HashMap<String, LinkedList<ZipArchiveEntry>>(509);
    private final String encoding;
    private final ZipEncoding zipEncoding;
    private final String archiveName;
    private final RandomAccessFile archive;
    private final boolean useUnicodeExtraFields;
    private boolean closed;
    private final byte[] DWORD_BUF = new byte[8];
    private final byte[] WORD_BUF = new byte[4];
    private final byte[] CFH_BUF = new byte[42];
    private final byte[] SHORT_BUF = new byte[2];
    private static final int CFH_LEN = 42;
    private static final long CFH_SIG = ZipLong.getValue(ZipArchiveOutputStream.CFH_SIG);
    static final int MIN_EOCD_SIZE = 22;
    private static final int MAX_EOCD_SIZE = 65557;
    private static final int CFD_LOCATOR_OFFSET = 16;
    private static final int ZIP64_EOCDL_LENGTH = 20;
    private static final int ZIP64_EOCDL_LOCATOR_OFFSET = 8;
    private static final int ZIP64_EOCD_CFD_LOCATOR_OFFSET = 48;
    private static final long LFH_OFFSET_FOR_FILENAME_LENGTH = 26L;
    private final Comparator<ZipArchiveEntry> OFFSET_COMPARATOR = new Comparator<ZipArchiveEntry>(this){
        final ZipFile this$0;
        {
            this.this$0 = zipFile;
        }

        @Override
        public int compare(ZipArchiveEntry zipArchiveEntry, ZipArchiveEntry zipArchiveEntry2) {
            Entry entry;
            if (zipArchiveEntry == zipArchiveEntry2) {
                return 1;
            }
            Entry entry2 = zipArchiveEntry instanceof Entry ? (Entry)zipArchiveEntry : null;
            Entry entry3 = entry = zipArchiveEntry2 instanceof Entry ? (Entry)zipArchiveEntry2 : null;
            if (entry2 == null) {
                return 0;
            }
            if (entry == null) {
                return 1;
            }
            long l = OffsetEntry.access$200(entry2.getOffsetEntry()) - OffsetEntry.access$200(entry.getOffsetEntry());
            return l == 0L ? 0 : (l < 0L ? -1 : 1);
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((ZipArchiveEntry)object, (ZipArchiveEntry)object2);
        }
    };

    public ZipFile(File file) throws IOException {
        this(file, "UTF8");
    }

    public ZipFile(String string) throws IOException {
        this(new File(string), "UTF8");
    }

    public ZipFile(String string, String string2) throws IOException {
        this(new File(string), string2, true);
    }

    public ZipFile(File file, String string) throws IOException {
        this(file, string, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ZipFile(File file, String string, boolean bl) throws IOException {
        this.archiveName = file.getAbsolutePath();
        this.encoding = string;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(string);
        this.useUnicodeExtraFields = bl;
        this.archive = new RandomAccessFile(file, "r");
        boolean bl2 = false;
        try {
            Map<ZipArchiveEntry, NameAndComment> map = this.populateFromCentralDirectory();
            this.resolveLocalFileHeaderData(map);
            bl2 = true;
        } finally {
            if (!bl2) {
                this.closed = true;
                IOUtils.closeQuietly(this.archive);
            }
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
        this.archive.close();
    }

    public static void closeQuietly(ZipFile zipFile) {
        IOUtils.closeQuietly(zipFile);
    }

    public Enumeration<ZipArchiveEntry> getEntries() {
        return Collections.enumeration(this.entries);
    }

    public Enumeration<ZipArchiveEntry> getEntriesInPhysicalOrder() {
        ZipArchiveEntry[] zipArchiveEntryArray = this.entries.toArray(new ZipArchiveEntry[0]);
        Arrays.sort(zipArchiveEntryArray, this.OFFSET_COMPARATOR);
        return Collections.enumeration(Arrays.asList(zipArchiveEntryArray));
    }

    public ZipArchiveEntry getEntry(String string) {
        LinkedList<ZipArchiveEntry> linkedList = this.nameMap.get(string);
        return linkedList != null ? linkedList.getFirst() : null;
    }

    public Iterable<ZipArchiveEntry> getEntries(String string) {
        List<ZipArchiveEntry> list = (List<ZipArchiveEntry>)this.nameMap.get(string);
        return list != null ? list : Collections.emptyList();
    }

    public Iterable<ZipArchiveEntry> getEntriesInPhysicalOrder(String string) {
        ZipArchiveEntry[] zipArchiveEntryArray = new ZipArchiveEntry[]{};
        if (this.nameMap.containsKey(string)) {
            zipArchiveEntryArray = this.nameMap.get(string).toArray(zipArchiveEntryArray);
            Arrays.sort(zipArchiveEntryArray, this.OFFSET_COMPARATOR);
        }
        return Arrays.asList(zipArchiveEntryArray);
    }

    public boolean canReadEntryData(ZipArchiveEntry zipArchiveEntry) {
        return ZipUtil.canHandleEntryData(zipArchiveEntry);
    }

    public InputStream getInputStream(ZipArchiveEntry zipArchiveEntry) throws IOException, ZipException {
        if (!(zipArchiveEntry instanceof Entry)) {
            return null;
        }
        OffsetEntry offsetEntry = ((Entry)zipArchiveEntry).getOffsetEntry();
        ZipUtil.checkRequestedFeatures(zipArchiveEntry);
        long l = OffsetEntry.access$000(offsetEntry);
        BoundedInputStream boundedInputStream = new BoundedInputStream(this, l, zipArchiveEntry.getCompressedSize());
        switch (3.$SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.getMethodByCode(zipArchiveEntry.getMethod()).ordinal()]) {
            case 1: {
                return boundedInputStream;
            }
            case 2: {
                return new UnshrinkingInputStream(boundedInputStream);
            }
            case 3: {
                return new ExplodingInputStream(zipArchiveEntry.getGeneralPurposeBit().getSlidingDictionarySize(), zipArchiveEntry.getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BufferedInputStream(boundedInputStream));
            }
            case 4: {
                boundedInputStream.addDummy();
                Inflater inflater = new Inflater(true);
                return new InflaterInputStream(this, boundedInputStream, inflater, inflater){
                    final Inflater val$inflater;
                    final ZipFile this$0;
                    {
                        this.this$0 = zipFile;
                        this.val$inflater = inflater2;
                        super(inputStream, inflater);
                    }

                    public void close() throws IOException {
                        super.close();
                        this.val$inflater.end();
                    }
                };
            }
        }
        throw new ZipException("Found unsupported compression method " + zipArchiveEntry.getMethod());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getUnixSymlink(ZipArchiveEntry zipArchiveEntry) throws IOException {
        if (zipArchiveEntry != null && zipArchiveEntry.isUnixSymlink()) {
            InputStream inputStream = null;
            try {
                inputStream = this.getInputStream(zipArchiveEntry);
                byte[] byArray = IOUtils.toByteArray(inputStream);
                String string = this.zipEncoding.decode(byArray);
                return string;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void finalize() throws Throwable {
        try {
            if (!this.closed) {
                System.err.println("Cleaning up unclosed ZipFile for archive " + this.archiveName);
                this.close();
            }
        } finally {
            super.finalize();
        }
    }

    private Map<ZipArchiveEntry, NameAndComment> populateFromCentralDirectory() throws IOException {
        HashMap<ZipArchiveEntry, NameAndComment> hashMap = new HashMap<ZipArchiveEntry, NameAndComment>();
        this.positionAtCentralDirectory();
        this.archive.readFully(this.WORD_BUF);
        long l = ZipLong.getValue(this.WORD_BUF);
        if (l != CFH_SIG && this.startsWithLocalFileHeader()) {
            throw new IOException("central directory is empty, can't expand corrupt archive.");
        }
        while (l == CFH_SIG) {
            this.readCentralDirectoryEntry(hashMap);
            this.archive.readFully(this.WORD_BUF);
            l = ZipLong.getValue(this.WORD_BUF);
        }
        return hashMap;
    }

    private void readCentralDirectoryEntry(Map<ZipArchiveEntry, NameAndComment> map) throws IOException {
        this.archive.readFully(this.CFH_BUF);
        int n = 0;
        OffsetEntry offsetEntry = new OffsetEntry(null);
        Entry entry = new Entry(offsetEntry);
        int n2 = ZipShort.getValue(this.CFH_BUF, n);
        n += 2;
        entry.setPlatform(n2 >> 8 & 0xF);
        GeneralPurposeBit generalPurposeBit = GeneralPurposeBit.parse(this.CFH_BUF, n += 2);
        boolean bl = generalPurposeBit.usesUTF8ForNames();
        ZipEncoding zipEncoding = bl ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
        entry.setGeneralPurposeBit(generalPurposeBit);
        entry.setMethod(ZipShort.getValue(this.CFH_BUF, n += 2));
        long l = ZipUtil.dosToJavaTime(ZipLong.getValue(this.CFH_BUF, n += 2));
        entry.setTime(l);
        entry.setCrc(ZipLong.getValue(this.CFH_BUF, n += 4));
        entry.setCompressedSize(ZipLong.getValue(this.CFH_BUF, n += 4));
        entry.setSize(ZipLong.getValue(this.CFH_BUF, n += 4));
        int n3 = ZipShort.getValue(this.CFH_BUF, n += 4);
        int n4 = ZipShort.getValue(this.CFH_BUF, n += 2);
        int n5 = ZipShort.getValue(this.CFH_BUF, n += 2);
        int n6 = ZipShort.getValue(this.CFH_BUF, n += 2);
        entry.setInternalAttributes(ZipShort.getValue(this.CFH_BUF, n += 2));
        entry.setExternalAttributes(ZipLong.getValue(this.CFH_BUF, n += 2));
        byte[] byArray = new byte[n3];
        this.archive.readFully(byArray);
        entry.setName(zipEncoding.decode(byArray), byArray);
        OffsetEntry.access$202(offsetEntry, ZipLong.getValue(this.CFH_BUF, n += 4));
        this.entries.add(entry);
        byte[] byArray2 = new byte[n4];
        this.archive.readFully(byArray2);
        entry.setCentralDirectoryExtra(byArray2);
        this.setSizesAndOffsetFromZip64Extra(entry, offsetEntry, n6);
        byte[] byArray3 = new byte[n5];
        this.archive.readFully(byArray3);
        entry.setComment(zipEncoding.decode(byArray3));
        if (!bl && this.useUnicodeExtraFields) {
            map.put(entry, new NameAndComment(byArray, byArray3, null));
        }
    }

    private void setSizesAndOffsetFromZip64Extra(ZipArchiveEntry zipArchiveEntry, OffsetEntry offsetEntry, int n) throws IOException {
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField)zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (zip64ExtendedInformationExtraField != null) {
            boolean bl = zipArchiveEntry.getSize() == 0xFFFFFFFFL;
            boolean bl2 = zipArchiveEntry.getCompressedSize() == 0xFFFFFFFFL;
            boolean bl3 = OffsetEntry.access$200(offsetEntry) == 0xFFFFFFFFL;
            zip64ExtendedInformationExtraField.reparseCentralDirectoryData(bl, bl2, bl3, n == 65535);
            if (bl) {
                zipArchiveEntry.setSize(zip64ExtendedInformationExtraField.getSize().getLongValue());
            } else if (bl2) {
                zip64ExtendedInformationExtraField.setSize(new ZipEightByteInteger(zipArchiveEntry.getSize()));
            }
            if (bl2) {
                zipArchiveEntry.setCompressedSize(zip64ExtendedInformationExtraField.getCompressedSize().getLongValue());
            } else if (bl) {
                zip64ExtendedInformationExtraField.setCompressedSize(new ZipEightByteInteger(zipArchiveEntry.getCompressedSize()));
            }
            if (bl3) {
                OffsetEntry.access$202(offsetEntry, zip64ExtendedInformationExtraField.getRelativeHeaderOffset().getLongValue());
            }
        }
    }

    private void positionAtCentralDirectory() throws IOException {
        boolean bl;
        this.positionAtEndOfCentralDirectoryRecord();
        boolean bl2 = false;
        boolean bl3 = bl = this.archive.getFilePointer() > 20L;
        if (bl) {
            this.archive.seek(this.archive.getFilePointer() - 20L);
            this.archive.readFully(this.WORD_BUF);
            bl2 = Arrays.equals(ZipArchiveOutputStream.ZIP64_EOCD_LOC_SIG, this.WORD_BUF);
        }
        if (!bl2) {
            if (bl) {
                this.skipBytes(16);
            }
            this.positionAtCentralDirectory32();
        } else {
            this.positionAtCentralDirectory64();
        }
    }

    private void positionAtCentralDirectory64() throws IOException {
        this.skipBytes(4);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
        this.archive.readFully(this.WORD_BUF);
        if (!Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.ZIP64_EOCD_SIG)) {
            throw new ZipException("archive's ZIP64 end of central directory locator is corrupt.");
        }
        this.skipBytes(44);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
    }

    private void positionAtCentralDirectory32() throws IOException {
        this.skipBytes(16);
        this.archive.readFully(this.WORD_BUF);
        this.archive.seek(ZipLong.getValue(this.WORD_BUF));
    }

    private void positionAtEndOfCentralDirectoryRecord() throws IOException {
        boolean bl = this.tryToLocateSignature(22L, 65557L, ZipArchiveOutputStream.EOCD_SIG);
        if (!bl) {
            throw new ZipException("archive is not a ZIP archive");
        }
    }

    private boolean tryToLocateSignature(long l, long l2, byte[] byArray) throws IOException {
        long l3;
        boolean bl = false;
        long l4 = Math.max(0L, this.archive.length() - l2);
        if (l3 >= 0L) {
            for (l3 = this.archive.length() - l; l3 >= l4; --l3) {
                this.archive.seek(l3);
                int n = this.archive.read();
                if (n == -1) break;
                if (n != byArray[0] || (n = this.archive.read()) != byArray[1] || (n = this.archive.read()) != byArray[2] || (n = this.archive.read()) != byArray[3]) continue;
                bl = true;
                break;
            }
        }
        if (bl) {
            this.archive.seek(l3);
        }
        return bl;
    }

    private void skipBytes(int n) throws IOException {
        int n2;
        for (int i = 0; i < n; i += n2) {
            n2 = this.archive.skipBytes(n - i);
            if (n2 > 0) continue;
            throw new EOFException();
        }
    }

    private void resolveLocalFileHeaderData(Map<ZipArchiveEntry, NameAndComment> map) throws IOException {
        for (ZipArchiveEntry zipArchiveEntry : this.entries) {
            LinkedList<ZipArchiveEntry> linkedList;
            Object object;
            int n;
            Entry entry = (Entry)zipArchiveEntry;
            OffsetEntry offsetEntry = entry.getOffsetEntry();
            long l = OffsetEntry.access$200(offsetEntry);
            this.archive.seek(l + 26L);
            this.archive.readFully(this.SHORT_BUF);
            int n2 = ZipShort.getValue(this.SHORT_BUF);
            this.archive.readFully(this.SHORT_BUF);
            int n3 = ZipShort.getValue(this.SHORT_BUF);
            for (int i = n2; i > 0; i -= n) {
                n = this.archive.skipBytes(i);
                if (n > 0) continue;
                throw new IOException("failed to skip file name in local file header");
            }
            byte[] byArray = new byte[n3];
            this.archive.readFully(byArray);
            entry.setExtra(byArray);
            OffsetEntry.access$002(offsetEntry, l + 26L + 2L + 2L + (long)n2 + (long)n3);
            if (map.containsKey(entry)) {
                object = map.get(entry);
                ZipUtil.setNameAndCommentFromExtraFields(entry, NameAndComment.access$400((NameAndComment)object), NameAndComment.access$500((NameAndComment)object));
            }
            if ((linkedList = this.nameMap.get(object = entry.getName())) == null) {
                linkedList = new LinkedList();
                this.nameMap.put((String)object, linkedList);
            }
            linkedList.addLast(entry);
        }
    }

    private boolean startsWithLocalFileHeader() throws IOException {
        this.archive.seek(0L);
        this.archive.readFully(this.WORD_BUF);
        return Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.LFH_SIG);
    }

    static RandomAccessFile access$600(ZipFile zipFile) {
        return zipFile.archive;
    }

    static class 3 {
        static final int[] $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod = new int[ZipMethod.values().length];

        static {
            try {
                3.$SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.STORED.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                3.$SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.UNSHRINKING.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                3.$SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.IMPLODING.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                3.$SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.DEFLATED.ordinal()] = 4;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }

    private static class Entry
    extends ZipArchiveEntry {
        private final OffsetEntry offsetEntry;

        Entry(OffsetEntry offsetEntry) {
            this.offsetEntry = offsetEntry;
        }

        OffsetEntry getOffsetEntry() {
            return this.offsetEntry;
        }

        public int hashCode() {
            return 3 * super.hashCode() + (int)(OffsetEntry.access$200(this.offsetEntry) % Integer.MAX_VALUE);
        }

        public boolean equals(Object object) {
            if (super.equals(object)) {
                Entry entry = (Entry)object;
                return OffsetEntry.access$200(this.offsetEntry) == OffsetEntry.access$200(entry.offsetEntry) && OffsetEntry.access$000(this.offsetEntry) == OffsetEntry.access$000(entry.offsetEntry);
            }
            return true;
        }
    }

    private static final class NameAndComment {
        private final byte[] name;
        private final byte[] comment;

        private NameAndComment(byte[] byArray, byte[] byArray2) {
            this.name = byArray;
            this.comment = byArray2;
        }

        NameAndComment(byte[] byArray, byte[] byArray2, 1 var3_3) {
            this(byArray, byArray2);
        }

        static byte[] access$400(NameAndComment nameAndComment) {
            return nameAndComment.name;
        }

        static byte[] access$500(NameAndComment nameAndComment) {
            return nameAndComment.comment;
        }
    }

    private class BoundedInputStream
    extends InputStream {
        private long remaining;
        private long loc;
        private boolean addDummyByte;
        final ZipFile this$0;

        BoundedInputStream(ZipFile zipFile, long l, long l2) {
            this.this$0 = zipFile;
            this.addDummyByte = false;
            this.remaining = l2;
            this.loc = l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public int read() throws IOException {
            if (this.remaining-- <= 0L) {
                if (this.addDummyByte) {
                    this.addDummyByte = false;
                    return 1;
                }
                return 1;
            }
            RandomAccessFile randomAccessFile = ZipFile.access$600(this.this$0);
            synchronized (randomAccessFile) {
                ZipFile.access$600(this.this$0).seek(this.loc++);
                return ZipFile.access$600(this.this$0).read();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public int read(byte[] byArray, int n, int n2) throws IOException {
            if (this.remaining <= 0L) {
                if (this.addDummyByte) {
                    this.addDummyByte = false;
                    byArray[n] = 0;
                    return 0;
                }
                return 1;
            }
            if (n2 <= 0) {
                return 1;
            }
            if ((long)n2 > this.remaining) {
                n2 = (int)this.remaining;
            }
            int n3 = -1;
            RandomAccessFile randomAccessFile = ZipFile.access$600(this.this$0);
            synchronized (randomAccessFile) {
                ZipFile.access$600(this.this$0).seek(this.loc);
                n3 = ZipFile.access$600(this.this$0).read(byArray, n, n2);
            }
            if (n3 > 0) {
                this.loc += (long)n3;
                this.remaining -= (long)n3;
            }
            return n3;
        }

        void addDummy() {
            this.addDummyByte = true;
        }
    }

    private static final class OffsetEntry {
        private long headerOffset = -1L;
        private long dataOffset = -1L;

        private OffsetEntry() {
        }

        static long access$000(OffsetEntry offsetEntry) {
            return offsetEntry.dataOffset;
        }

        OffsetEntry(1 var1_1) {
            this();
        }

        static long access$202(OffsetEntry offsetEntry, long l) {
            offsetEntry.headerOffset = l;
            return offsetEntry.headerOffset;
        }

        static long access$200(OffsetEntry offsetEntry) {
            return offsetEntry.headerOffset;
        }

        static long access$002(OffsetEntry offsetEntry, long l) {
            offsetEntry.dataOffset = l;
            return offsetEntry.dataOffset;
        }
    }
}

