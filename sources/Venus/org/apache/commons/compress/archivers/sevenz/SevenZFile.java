/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.sevenz;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.zip.CRC32;
import org.apache.commons.compress.archivers.sevenz.Archive;
import org.apache.commons.compress.archivers.sevenz.BindPair;
import org.apache.commons.compress.archivers.sevenz.BoundedRandomAccessFileInputStream;
import org.apache.commons.compress.archivers.sevenz.Coder;
import org.apache.commons.compress.archivers.sevenz.Coders;
import org.apache.commons.compress.archivers.sevenz.Folder;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZMethod;
import org.apache.commons.compress.archivers.sevenz.SevenZMethodConfiguration;
import org.apache.commons.compress.archivers.sevenz.StartHeader;
import org.apache.commons.compress.archivers.sevenz.StreamMap;
import org.apache.commons.compress.archivers.sevenz.SubStreamsInfo;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.CRC32VerifyingInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class SevenZFile
implements Closeable {
    static final int SIGNATURE_HEADER_SIZE = 32;
    private RandomAccessFile file;
    private final Archive archive;
    private int currentEntryIndex = -1;
    private int currentFolderIndex = -1;
    private InputStream currentFolderInputStream = null;
    private InputStream currentEntryInputStream = null;
    private byte[] password;
    static final byte[] sevenZSignature = new byte[]{55, 122, -68, -81, 39, 28};

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SevenZFile(File file, byte[] byArray) throws IOException {
        boolean bl = false;
        this.file = new RandomAccessFile(file, "r");
        try {
            this.archive = this.readHeaders(byArray);
            if (byArray != null) {
                this.password = new byte[byArray.length];
                System.arraycopy(byArray, 0, this.password, 0, byArray.length);
            } else {
                this.password = null;
            }
            bl = true;
        } finally {
            if (!bl) {
                this.file.close();
            }
        }
    }

    public SevenZFile(File file) throws IOException {
        this(file, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void close() throws IOException {
        if (this.file != null) {
            try {
                this.file.close();
            } finally {
                this.file = null;
                if (this.password != null) {
                    Arrays.fill(this.password, (byte)0);
                }
                this.password = null;
            }
        }
    }

    public SevenZArchiveEntry getNextEntry() throws IOException {
        if (this.currentEntryIndex >= this.archive.files.length - 1) {
            return null;
        }
        ++this.currentEntryIndex;
        SevenZArchiveEntry sevenZArchiveEntry = this.archive.files[this.currentEntryIndex];
        this.buildDecodingStream();
        return sevenZArchiveEntry;
    }

    private Archive readHeaders(byte[] byArray) throws IOException {
        byte[] byArray2 = new byte[6];
        this.file.readFully(byArray2);
        if (!Arrays.equals(byArray2, sevenZSignature)) {
            throw new IOException("Bad 7z signature");
        }
        byte by = this.file.readByte();
        byte by2 = this.file.readByte();
        if (by != 0) {
            throw new IOException(String.format("Unsupported 7z version (%d,%d)", by, by2));
        }
        long l = 0xFFFFFFFFL & (long)Integer.reverseBytes(this.file.readInt());
        StartHeader startHeader = this.readStartHeader(l);
        int n = (int)startHeader.nextHeaderSize;
        if ((long)n != startHeader.nextHeaderSize) {
            throw new IOException("cannot handle nextHeaderSize " + startHeader.nextHeaderSize);
        }
        this.file.seek(32L + startHeader.nextHeaderOffset);
        byte[] byArray3 = new byte[n];
        this.file.readFully(byArray3);
        CRC32 cRC32 = new CRC32();
        cRC32.update(byArray3);
        if (startHeader.nextHeaderCrc != cRC32.getValue()) {
            throw new IOException("NextHeader CRC mismatch");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray3);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        Archive archive = new Archive();
        int n2 = dataInputStream.readUnsignedByte();
        if (n2 == 23) {
            dataInputStream = this.readEncodedHeader(dataInputStream, archive, byArray);
            archive = new Archive();
            n2 = dataInputStream.readUnsignedByte();
        }
        if (n2 != 1) {
            throw new IOException("Broken or unsupported archive: no Header");
        }
        this.readHeader(dataInputStream, archive);
        dataInputStream.close();
        return archive;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private StartHeader readStartHeader(long l) throws IOException {
        StartHeader startHeader = new StartHeader();
        FilterInputStream filterInputStream = null;
        try {
            filterInputStream = new DataInputStream(new CRC32VerifyingInputStream((InputStream)new BoundedRandomAccessFileInputStream(this.file, 20L), 20L, l));
            startHeader.nextHeaderOffset = Long.reverseBytes(((DataInputStream)filterInputStream).readLong());
            startHeader.nextHeaderSize = Long.reverseBytes(((DataInputStream)filterInputStream).readLong());
            startHeader.nextHeaderCrc = 0xFFFFFFFFL & (long)Integer.reverseBytes(((DataInputStream)filterInputStream).readInt());
            StartHeader startHeader2 = startHeader;
            return startHeader2;
        } finally {
            if (filterInputStream != null) {
                filterInputStream.close();
            }
        }
    }

    private void readHeader(DataInput dataInput, Archive archive) throws IOException {
        int n = dataInput.readUnsignedByte();
        if (n == 2) {
            this.readArchiveProperties(dataInput);
            n = dataInput.readUnsignedByte();
        }
        if (n == 3) {
            throw new IOException("Additional streams unsupported");
        }
        if (n == 4) {
            this.readStreamsInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        if (n == 5) {
            this.readFilesInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        if (n != 0) {
            throw new IOException("Badly terminated header");
        }
    }

    private void readArchiveProperties(DataInput dataInput) throws IOException {
        int n = dataInput.readUnsignedByte();
        while (n != 0) {
            long l = SevenZFile.readUint64(dataInput);
            byte[] byArray = new byte[(int)l];
            dataInput.readFully(byArray);
            n = dataInput.readUnsignedByte();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DataInputStream readEncodedHeader(DataInputStream dataInputStream, Archive archive, byte[] byArray) throws IOException {
        this.readStreamsInfo(dataInputStream, archive);
        Folder folder = archive.folders[0];
        boolean bl = false;
        long l = 32L + archive.packPos + 0L;
        this.file.seek(l);
        InputStream inputStream = new BoundedRandomAccessFileInputStream(this.file, archive.packSizes[0]);
        for (Coder object2 : folder.getOrderedCoders()) {
            if (object2.numInStreams != 1L || object2.numOutStreams != 1L) {
                throw new IOException("Multi input/output stream coders are not yet supported");
            }
            inputStream = Coders.addDecoder(inputStream, object2, byArray);
        }
        if (folder.hasCrc) {
            inputStream = new CRC32VerifyingInputStream(inputStream, folder.getUnpackSize(), folder.crc);
        }
        Object object3 = new byte[(int)folder.getUnpackSize()];
        DataInputStream dataInputStream2 = new DataInputStream(inputStream);
        try {
            dataInputStream2.readFully((byte[])object3);
        } finally {
            dataInputStream2.close();
        }
        return new DataInputStream(new ByteArrayInputStream((byte[])object3));
    }

    private void readStreamsInfo(DataInput dataInput, Archive archive) throws IOException {
        int n = dataInput.readUnsignedByte();
        if (n == 6) {
            this.readPackInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        if (n == 7) {
            this.readUnpackInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        } else {
            archive.folders = new Folder[0];
        }
        if (n == 8) {
            this.readSubStreamsInfo(dataInput, archive);
            n = dataInput.readUnsignedByte();
        }
        if (n != 0) {
            throw new IOException("Badly terminated StreamsInfo");
        }
    }

    private void readPackInfo(DataInput dataInput, Archive archive) throws IOException {
        int n;
        archive.packPos = SevenZFile.readUint64(dataInput);
        long l = SevenZFile.readUint64(dataInput);
        int n2 = dataInput.readUnsignedByte();
        if (n2 == 9) {
            archive.packSizes = new long[(int)l];
            for (n = 0; n < archive.packSizes.length; ++n) {
                archive.packSizes[n] = SevenZFile.readUint64(dataInput);
            }
            n2 = dataInput.readUnsignedByte();
        }
        if (n2 == 10) {
            archive.packCrcsDefined = this.readAllOrBits(dataInput, (int)l);
            archive.packCrcs = new long[(int)l];
            for (n = 0; n < (int)l; ++n) {
                if (!archive.packCrcsDefined.get(n)) continue;
                archive.packCrcs[n] = 0xFFFFFFFFL & (long)Integer.reverseBytes(dataInput.readInt());
            }
            n2 = dataInput.readUnsignedByte();
        }
        if (n2 != 0) {
            throw new IOException("Badly terminated PackInfo (" + n2 + ")");
        }
    }

    private void readUnpackInfo(DataInput dataInput, Archive archive) throws IOException {
        int n = dataInput.readUnsignedByte();
        if (n != 11) {
            throw new IOException("Expected kFolder, got " + n);
        }
        long l = SevenZFile.readUint64(dataInput);
        Folder[] folderArray = new Folder[(int)l];
        archive.folders = folderArray;
        int n2 = dataInput.readUnsignedByte();
        if (n2 != 0) {
            throw new IOException("External unsupported");
        }
        for (int i = 0; i < (int)l; ++i) {
            folderArray[i] = this.readFolder(dataInput);
        }
        n = dataInput.readUnsignedByte();
        if (n != 12) {
            throw new IOException("Expected kCodersUnpackSize, got " + n);
        }
        for (Folder folder : folderArray) {
            folder.unpackSizes = new long[(int)folder.totalOutputStreams];
            int n3 = 0;
            while ((long)n3 < folder.totalOutputStreams) {
                folder.unpackSizes[n3] = SevenZFile.readUint64(dataInput);
                ++n3;
            }
        }
        n = dataInput.readUnsignedByte();
        if (n == 10) {
            BitSet bitSet = this.readAllOrBits(dataInput, (int)l);
            for (int i = 0; i < (int)l; ++i) {
                if (bitSet.get(i)) {
                    folderArray[i].hasCrc = true;
                    folderArray[i].crc = 0xFFFFFFFFL & (long)Integer.reverseBytes(dataInput.readInt());
                    continue;
                }
                folderArray[i].hasCrc = false;
            }
            n = dataInput.readUnsignedByte();
        }
        if (n != 0) {
            throw new IOException("Badly terminated UnpackInfo");
        }
    }

    private void readSubStreamsInfo(DataInput dataInput, Archive archive) throws IOException {
        for (Folder folder : archive.folders) {
            folder.numUnpackSubStreams = 1;
        }
        int n = archive.folders.length;
        int n2 = dataInput.readUnsignedByte();
        if (n2 == 13) {
            n = 0;
            for (Folder folder : archive.folders) {
                long l = SevenZFile.readUint64(dataInput);
                folder.numUnpackSubStreams = (int)l;
                n = (int)((long)n + l);
            }
            n2 = dataInput.readUnsignedByte();
        }
        SubStreamsInfo subStreamsInfo = new SubStreamsInfo();
        subStreamsInfo.unpackSizes = new long[n];
        subStreamsInfo.hasCrc = new BitSet(n);
        subStreamsInfo.crcs = new long[n];
        int n3 = 0;
        for (Folder folder : archive.folders) {
            if (folder.numUnpackSubStreams == 0) continue;
            long l = 0L;
            if (n2 == 9) {
                for (int i = 0; i < folder.numUnpackSubStreams - 1; ++i) {
                    long l2 = SevenZFile.readUint64(dataInput);
                    subStreamsInfo.unpackSizes[n3++] = l2;
                    l += l2;
                }
            }
            subStreamsInfo.unpackSizes[n3++] = folder.getUnpackSize() - l;
        }
        if (n2 == 9) {
            n2 = dataInput.readUnsignedByte();
        }
        int n4 = 0;
        for (Folder folder : archive.folders) {
            if (folder.numUnpackSubStreams == 1 && folder.hasCrc) continue;
            n4 += folder.numUnpackSubStreams;
        }
        if (n2 == 10) {
            int n5;
            BitSet bitSet = this.readAllOrBits(dataInput, n4);
            long[] lArray = new long[n4];
            for (n5 = 0; n5 < n4; ++n5) {
                if (!bitSet.get(n5)) continue;
                lArray[n5] = 0xFFFFFFFFL & (long)Integer.reverseBytes(dataInput.readInt());
            }
            n5 = 0;
            int n6 = 0;
            for (Folder folder : archive.folders) {
                if (folder.numUnpackSubStreams == 1 && folder.hasCrc) {
                    subStreamsInfo.hasCrc.set(n5, false);
                    subStreamsInfo.crcs[n5] = folder.crc;
                    ++n5;
                    continue;
                }
                for (int i = 0; i < folder.numUnpackSubStreams; ++i) {
                    subStreamsInfo.hasCrc.set(n5, bitSet.get(n6));
                    subStreamsInfo.crcs[n5] = lArray[n6];
                    ++n5;
                    ++n6;
                }
            }
            n2 = dataInput.readUnsignedByte();
        }
        if (n2 != 0) {
            throw new IOException("Badly terminated SubStreamsInfo");
        }
        archive.subStreamsInfo = subStreamsInfo;
    }

    private Folder readFolder(DataInput dataInput) throws IOException {
        int n;
        Folder folder = new Folder();
        long l = SevenZFile.readUint64(dataInput);
        Coder[] coderArray = new Coder[(int)l];
        long l2 = 0L;
        long l3 = 0L;
        for (int i = 0; i < coderArray.length; ++i) {
            coderArray[i] = new Coder();
            int n2 = dataInput.readUnsignedByte();
            int n3 = n2 & 0xF;
            n = (n2 & 0x10) == 0 ? 1 : 0;
            boolean bl = (n2 & 0x20) != 0;
            boolean bl2 = (n2 & 0x80) != 0;
            coderArray[i].decompressionMethodId = new byte[n3];
            dataInput.readFully(coderArray[i].decompressionMethodId);
            if (n != 0) {
                coderArray[i].numInStreams = 1L;
                coderArray[i].numOutStreams = 1L;
            } else {
                coderArray[i].numInStreams = SevenZFile.readUint64(dataInput);
                coderArray[i].numOutStreams = SevenZFile.readUint64(dataInput);
            }
            l2 += coderArray[i].numInStreams;
            l3 += coderArray[i].numOutStreams;
            if (bl) {
                long l4 = SevenZFile.readUint64(dataInput);
                coderArray[i].properties = new byte[(int)l4];
                dataInput.readFully(coderArray[i].properties);
            }
            if (!bl2) continue;
            throw new IOException("Alternative methods are unsupported, please report. The reference implementation doesn't support them either.");
        }
        folder.coders = coderArray;
        folder.totalInputStreams = l2;
        folder.totalOutputStreams = l3;
        if (l3 == 0L) {
            throw new IOException("Total output streams can't be 0");
        }
        long l5 = l3 - 1L;
        BindPair[] bindPairArray = new BindPair[(int)l5];
        for (n = 0; n < bindPairArray.length; ++n) {
            bindPairArray[n] = new BindPair();
            bindPairArray[n].inIndex = SevenZFile.readUint64(dataInput);
            bindPairArray[n].outIndex = SevenZFile.readUint64(dataInput);
        }
        folder.bindPairs = bindPairArray;
        if (l2 < l5) {
            throw new IOException("Total input streams can't be less than the number of bind pairs");
        }
        long l6 = l2 - l5;
        long[] lArray = new long[(int)l6];
        if (l6 == 1L) {
            int n4;
            for (n4 = 0; n4 < (int)l2 && folder.findBindPairForInStream(n4) >= 0; ++n4) {
            }
            if (n4 == (int)l2) {
                throw new IOException("Couldn't find stream's bind pair index");
            }
            lArray[0] = n4;
        } else {
            for (int i = 0; i < (int)l6; ++i) {
                lArray[i] = SevenZFile.readUint64(dataInput);
            }
        }
        folder.packedStreams = lArray;
        return folder;
    }

    private BitSet readAllOrBits(DataInput dataInput, int n) throws IOException {
        BitSet bitSet;
        int n2 = dataInput.readUnsignedByte();
        if (n2 != 0) {
            bitSet = new BitSet(n);
            for (int i = 0; i < n; ++i) {
                bitSet.set(i, false);
            }
        } else {
            bitSet = this.readBits(dataInput, n);
        }
        return bitSet;
    }

    private BitSet readBits(DataInput dataInput, int n) throws IOException {
        BitSet bitSet = new BitSet(n);
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            if (n2 == 0) {
                n2 = 128;
                n3 = dataInput.readUnsignedByte();
            }
            bitSet.set(i, (n3 & n2) != 0);
            n2 >>>= 1;
        }
        return bitSet;
    }

    private void readFilesInfo(DataInput dataInput, Archive archive) throws IOException {
        int n;
        long l = SevenZFile.readUint64(dataInput);
        SevenZArchiveEntry[] sevenZArchiveEntryArray = new SevenZArchiveEntry[(int)l];
        for (int i = 0; i < sevenZArchiveEntryArray.length; ++i) {
            sevenZArchiveEntryArray[i] = new SevenZArchiveEntry();
        }
        BitSet bitSet = null;
        BitSet bitSet2 = null;
        BitSet bitSet3 = null;
        block13: while ((n = dataInput.readUnsignedByte()) != 0) {
            long l2 = SevenZFile.readUint64(dataInput);
            switch (n) {
                case 14: {
                    bitSet = this.readBits(dataInput, sevenZArchiveEntryArray.length);
                    break;
                }
                case 15: {
                    if (bitSet == null) {
                        throw new IOException("Header format error: kEmptyStream must appear before kEmptyFile");
                    }
                    bitSet2 = this.readBits(dataInput, bitSet.cardinality());
                    break;
                }
                case 16: {
                    if (bitSet == null) {
                        throw new IOException("Header format error: kEmptyStream must appear before kAnti");
                    }
                    bitSet3 = this.readBits(dataInput, bitSet.cardinality());
                    break;
                }
                case 17: {
                    int n2 = dataInput.readUnsignedByte();
                    if (n2 != 0) {
                        throw new IOException("Not implemented");
                    }
                    if ((l2 - 1L & 1L) != 0L) {
                        throw new IOException("File names length invalid");
                    }
                    byte[] byArray = new byte[(int)(l2 - 1L)];
                    dataInput.readFully(byArray);
                    int n3 = 0;
                    int n4 = 0;
                    for (int i = 0; i < byArray.length; i += 2) {
                        if (byArray[i] != 0 || byArray[i + 1] != 0) continue;
                        sevenZArchiveEntryArray[n3++].setName(new String(byArray, n4, i - n4, "UTF-16LE"));
                        n4 = i + 2;
                    }
                    if (n4 == byArray.length && n3 == sevenZArchiveEntryArray.length) continue block13;
                    throw new IOException("Error parsing file names");
                }
                case 18: {
                    int n3;
                    BitSet bitSet4 = this.readAllOrBits(dataInput, sevenZArchiveEntryArray.length);
                    int n5 = dataInput.readUnsignedByte();
                    if (n5 != 0) {
                        throw new IOException("Unimplemented");
                    }
                    for (n3 = 0; n3 < sevenZArchiveEntryArray.length; ++n3) {
                        sevenZArchiveEntryArray[n3].setHasCreationDate(bitSet4.get(n3));
                        if (!sevenZArchiveEntryArray[n3].getHasCreationDate()) continue;
                        sevenZArchiveEntryArray[n3].setCreationDate(Long.reverseBytes(dataInput.readLong()));
                    }
                    continue block13;
                }
                case 19: {
                    int n3;
                    BitSet bitSet5 = this.readAllOrBits(dataInput, sevenZArchiveEntryArray.length);
                    int n6 = dataInput.readUnsignedByte();
                    if (n6 != 0) {
                        throw new IOException("Unimplemented");
                    }
                    for (n3 = 0; n3 < sevenZArchiveEntryArray.length; ++n3) {
                        sevenZArchiveEntryArray[n3].setHasAccessDate(bitSet5.get(n3));
                        if (!sevenZArchiveEntryArray[n3].getHasAccessDate()) continue;
                        sevenZArchiveEntryArray[n3].setAccessDate(Long.reverseBytes(dataInput.readLong()));
                    }
                    continue block13;
                }
                case 20: {
                    int n3;
                    BitSet bitSet6 = this.readAllOrBits(dataInput, sevenZArchiveEntryArray.length);
                    int n7 = dataInput.readUnsignedByte();
                    if (n7 != 0) {
                        throw new IOException("Unimplemented");
                    }
                    for (n3 = 0; n3 < sevenZArchiveEntryArray.length; ++n3) {
                        sevenZArchiveEntryArray[n3].setHasLastModifiedDate(bitSet6.get(n3));
                        if (!sevenZArchiveEntryArray[n3].getHasLastModifiedDate()) continue;
                        sevenZArchiveEntryArray[n3].setLastModifiedDate(Long.reverseBytes(dataInput.readLong()));
                    }
                    continue block13;
                }
                case 21: {
                    int n3;
                    BitSet bitSet7 = this.readAllOrBits(dataInput, sevenZArchiveEntryArray.length);
                    int n8 = dataInput.readUnsignedByte();
                    if (n8 != 0) {
                        throw new IOException("Unimplemented");
                    }
                    for (n3 = 0; n3 < sevenZArchiveEntryArray.length; ++n3) {
                        sevenZArchiveEntryArray[n3].setHasWindowsAttributes(bitSet7.get(n3));
                        if (!sevenZArchiveEntryArray[n3].getHasWindowsAttributes()) continue;
                        sevenZArchiveEntryArray[n3].setWindowsAttributes(Integer.reverseBytes(dataInput.readInt()));
                    }
                    continue block13;
                }
                case 24: {
                    throw new IOException("kStartPos is unsupported, please report");
                }
                case 25: {
                    throw new IOException("kDummy is unsupported, please report");
                }
                default: {
                    throw new IOException("Unknown property " + n);
                }
            }
        }
        n = 0;
        int n9 = 0;
        for (int i = 0; i < sevenZArchiveEntryArray.length; ++i) {
            sevenZArchiveEntryArray[i].setHasStream(bitSet == null ? true : !bitSet.get(i));
            if (sevenZArchiveEntryArray[i].hasStream()) {
                sevenZArchiveEntryArray[i].setDirectory(true);
                sevenZArchiveEntryArray[i].setAntiItem(true);
                sevenZArchiveEntryArray[i].setHasCrc(archive.subStreamsInfo.hasCrc.get(n));
                sevenZArchiveEntryArray[i].setCrcValue(archive.subStreamsInfo.crcs[n]);
                sevenZArchiveEntryArray[i].setSize(archive.subStreamsInfo.unpackSizes[n]);
                ++n;
                continue;
            }
            sevenZArchiveEntryArray[i].setDirectory(bitSet2 == null ? true : !bitSet2.get(n9));
            sevenZArchiveEntryArray[i].setAntiItem(bitSet3 == null ? false : bitSet3.get(n9));
            sevenZArchiveEntryArray[i].setHasCrc(true);
            sevenZArchiveEntryArray[i].setSize(0L);
            ++n9;
        }
        archive.files = sevenZArchiveEntryArray;
        this.calculateStreamMap(archive);
    }

    private void calculateStreamMap(Archive archive) throws IOException {
        int n;
        StreamMap streamMap = new StreamMap();
        int n2 = 0;
        int n3 = archive.folders != null ? archive.folders.length : 0;
        streamMap.folderFirstPackStreamIndex = new int[n3];
        for (int i = 0; i < n3; ++i) {
            streamMap.folderFirstPackStreamIndex[i] = n2;
            n2 += archive.folders[i].packedStreams.length;
        }
        long l = 0L;
        int n4 = archive.packSizes != null ? archive.packSizes.length : 0;
        streamMap.packStreamOffsets = new long[n4];
        for (n = 0; n < n4; ++n) {
            streamMap.packStreamOffsets[n] = l;
            l += archive.packSizes[n];
        }
        streamMap.folderFirstFileIndex = new int[n3];
        streamMap.fileFolderIndex = new int[archive.files.length];
        n = 0;
        int n5 = 0;
        for (int i = 0; i < archive.files.length; ++i) {
            if (!archive.files[i].hasStream() && n5 == 0) {
                streamMap.fileFolderIndex[i] = -1;
                continue;
            }
            if (n5 == 0) {
                while (n < archive.folders.length) {
                    streamMap.folderFirstFileIndex[n] = i;
                    if (archive.folders[n].numUnpackSubStreams > 0) break;
                    ++n;
                }
                if (n >= archive.folders.length) {
                    throw new IOException("Too few folders in archive");
                }
            }
            streamMap.fileFolderIndex[i] = n;
            if (!archive.files[i].hasStream() || ++n5 < archive.folders[n].numUnpackSubStreams) continue;
            ++n;
            n5 = 0;
        }
        archive.streamMap = streamMap;
    }

    private void buildDecodingStream() throws IOException {
        Object object;
        int n = this.archive.streamMap.fileFolderIndex[this.currentEntryIndex];
        if (n < 0) {
            this.currentEntryInputStream = new BoundedInputStream(new ByteArrayInputStream(new byte[0]), 0L);
            return;
        }
        SevenZArchiveEntry sevenZArchiveEntry = this.archive.files[this.currentEntryIndex];
        if (this.currentFolderIndex == n) {
            this.drainPreviousEntry();
            sevenZArchiveEntry.setContentMethods(this.archive.files[this.currentEntryIndex - 1].getContentMethods());
        } else {
            this.currentFolderIndex = n;
            if (this.currentFolderInputStream != null) {
                this.currentFolderInputStream.close();
                this.currentFolderInputStream = null;
            }
            object = this.archive.folders[n];
            int n2 = this.archive.streamMap.folderFirstPackStreamIndex[n];
            long l = 32L + this.archive.packPos + this.archive.streamMap.packStreamOffsets[n2];
            this.currentFolderInputStream = this.buildDecoderStack((Folder)object, l, n2, sevenZArchiveEntry);
        }
        object = new BoundedInputStream(this.currentFolderInputStream, sevenZArchiveEntry.getSize());
        this.currentEntryInputStream = sevenZArchiveEntry.getHasCrc() ? new CRC32VerifyingInputStream((InputStream)object, sevenZArchiveEntry.getSize(), sevenZArchiveEntry.getCrcValue()) : object;
    }

    private void drainPreviousEntry() throws IOException {
        if (this.currentEntryInputStream != null) {
            IOUtils.skip(this.currentEntryInputStream, Long.MAX_VALUE);
            this.currentEntryInputStream.close();
            this.currentEntryInputStream = null;
        }
    }

    private InputStream buildDecoderStack(Folder folder, long l, int n, SevenZArchiveEntry sevenZArchiveEntry) throws IOException {
        this.file.seek(l);
        InputStream inputStream = new BoundedRandomAccessFileInputStream(this.file, this.archive.packSizes[n]);
        LinkedList<SevenZMethodConfiguration> linkedList = new LinkedList<SevenZMethodConfiguration>();
        for (Coder coder : folder.getOrderedCoders()) {
            if (coder.numInStreams != 1L || coder.numOutStreams != 1L) {
                throw new IOException("Multi input/output stream coders are not yet supported");
            }
            SevenZMethod sevenZMethod = SevenZMethod.byId(coder.decompressionMethodId);
            inputStream = Coders.addDecoder(inputStream, coder, this.password);
            linkedList.addFirst(new SevenZMethodConfiguration(sevenZMethod, Coders.findByMethod(sevenZMethod).getOptionsFromCoder(coder, inputStream)));
        }
        sevenZArchiveEntry.setContentMethods(linkedList);
        if (folder.hasCrc) {
            return new CRC32VerifyingInputStream(inputStream, folder.getUnpackSize(), folder.crc);
        }
        return inputStream;
    }

    public int read() throws IOException {
        if (this.currentEntryInputStream == null) {
            throw new IllegalStateException("No current 7z entry");
        }
        return this.currentEntryInputStream.read();
    }

    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (this.currentEntryInputStream == null) {
            throw new IllegalStateException("No current 7z entry");
        }
        return this.currentEntryInputStream.read(byArray, n, n2);
    }

    private static long readUint64(DataInput dataInput) throws IOException {
        long l = dataInput.readUnsignedByte();
        int n = 128;
        long l2 = 0L;
        for (int i = 0; i < 8; ++i) {
            if ((l & (long)n) == 0L) {
                return l2 | (l & (long)(n - 1)) << 8 * i;
            }
            long l3 = dataInput.readUnsignedByte();
            l2 |= l3 << 8 * i;
            n >>>= 1;
        }
        return l2;
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < sevenZSignature.length) {
            return true;
        }
        for (int i = 0; i < sevenZSignature.length; ++i) {
            if (byArray[i] == sevenZSignature[i]) continue;
            return true;
        }
        return false;
    }
}

