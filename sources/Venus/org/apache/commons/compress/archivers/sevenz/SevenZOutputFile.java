/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.sevenz;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.Coders;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZMethod;
import org.apache.commons.compress.archivers.sevenz.SevenZMethodConfiguration;
import org.apache.commons.compress.utils.CountingOutputStream;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SevenZOutputFile
implements Closeable {
    private final RandomAccessFile file;
    private final List<SevenZArchiveEntry> files = new ArrayList<SevenZArchiveEntry>();
    private int numNonEmptyStreams = 0;
    private final CRC32 crc32 = new CRC32();
    private final CRC32 compressedCrc32 = new CRC32();
    private long fileBytesWritten = 0L;
    private boolean finished = false;
    private CountingOutputStream currentOutputStream;
    private CountingOutputStream[] additionalCountingStreams;
    private Iterable<? extends SevenZMethodConfiguration> contentMethods = Collections.singletonList(new SevenZMethodConfiguration(SevenZMethod.LZMA2));
    private final Map<SevenZArchiveEntry, long[]> additionalSizes = new HashMap<SevenZArchiveEntry, long[]>();

    public SevenZOutputFile(File file) throws IOException {
        this.file = new RandomAccessFile(file, "rw");
        this.file.seek(32L);
    }

    public void setContentCompression(SevenZMethod sevenZMethod) {
        this.setContentMethods(Collections.singletonList(new SevenZMethodConfiguration(sevenZMethod)));
    }

    public void setContentMethods(Iterable<? extends SevenZMethodConfiguration> iterable) {
        this.contentMethods = SevenZOutputFile.reverse(iterable);
    }

    @Override
    public void close() throws IOException {
        if (!this.finished) {
            this.finish();
        }
        this.file.close();
    }

    public SevenZArchiveEntry createArchiveEntry(File file, String string) throws IOException {
        SevenZArchiveEntry sevenZArchiveEntry = new SevenZArchiveEntry();
        sevenZArchiveEntry.setDirectory(file.isDirectory());
        sevenZArchiveEntry.setName(string);
        sevenZArchiveEntry.setLastModifiedDate(new Date(file.lastModified()));
        return sevenZArchiveEntry;
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        SevenZArchiveEntry sevenZArchiveEntry = (SevenZArchiveEntry)archiveEntry;
        this.files.add(sevenZArchiveEntry);
    }

    public void closeArchiveEntry() throws IOException {
        if (this.currentOutputStream != null) {
            this.currentOutputStream.flush();
            this.currentOutputStream.close();
        }
        SevenZArchiveEntry sevenZArchiveEntry = this.files.get(this.files.size() - 1);
        if (this.fileBytesWritten > 0L) {
            sevenZArchiveEntry.setHasStream(false);
            ++this.numNonEmptyStreams;
            sevenZArchiveEntry.setSize(this.currentOutputStream.getBytesWritten());
            sevenZArchiveEntry.setCompressedSize(this.fileBytesWritten);
            sevenZArchiveEntry.setCrcValue(this.crc32.getValue());
            sevenZArchiveEntry.setCompressedCrcValue(this.compressedCrc32.getValue());
            sevenZArchiveEntry.setHasCrc(false);
            if (this.additionalCountingStreams != null) {
                long[] lArray = new long[this.additionalCountingStreams.length];
                for (int i = 0; i < this.additionalCountingStreams.length; ++i) {
                    lArray[i] = this.additionalCountingStreams[i].getBytesWritten();
                }
                this.additionalSizes.put(sevenZArchiveEntry, lArray);
            }
        } else {
            sevenZArchiveEntry.setHasStream(true);
            sevenZArchiveEntry.setSize(0L);
            sevenZArchiveEntry.setCompressedSize(0L);
            sevenZArchiveEntry.setHasCrc(true);
        }
        this.currentOutputStream = null;
        this.additionalCountingStreams = null;
        this.crc32.reset();
        this.compressedCrc32.reset();
        this.fileBytesWritten = 0L;
    }

    public void write(int n) throws IOException {
        this.getCurrentOutputStream().write(n);
    }

    public void write(byte[] byArray) throws IOException {
        this.write(byArray, 0, byArray.length);
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (n2 > 0) {
            this.getCurrentOutputStream().write(byArray, n, n2);
        }
    }

    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        this.finished = true;
        long l = this.file.getFilePointer();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        this.writeHeader(dataOutputStream);
        dataOutputStream.flush();
        byte[] byArray = byteArrayOutputStream.toByteArray();
        this.file.write(byArray);
        CRC32 cRC32 = new CRC32();
        this.file.seek(0L);
        this.file.write(SevenZFile.sevenZSignature);
        this.file.write(0);
        this.file.write(2);
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream2);
        dataOutputStream2.writeLong(Long.reverseBytes(l - 32L));
        dataOutputStream2.writeLong(Long.reverseBytes(0xFFFFFFFFL & (long)byArray.length));
        cRC32.reset();
        cRC32.update(byArray);
        dataOutputStream2.writeInt(Integer.reverseBytes((int)cRC32.getValue()));
        dataOutputStream2.flush();
        byte[] byArray2 = byteArrayOutputStream2.toByteArray();
        cRC32.reset();
        cRC32.update(byArray2);
        this.file.writeInt(Integer.reverseBytes((int)cRC32.getValue()));
        this.file.write(byArray2);
    }

    private OutputStream getCurrentOutputStream() throws IOException {
        if (this.currentOutputStream == null) {
            this.currentOutputStream = this.setupFileOutputStream();
        }
        return this.currentOutputStream;
    }

    private CountingOutputStream setupFileOutputStream() throws IOException {
        if (this.files.isEmpty()) {
            throw new IllegalStateException("No current 7z entry");
        }
        OutputStream outputStream = new OutputStreamWrapper(this, null);
        ArrayList<CountingOutputStream> arrayList = new ArrayList<CountingOutputStream>();
        boolean bl = true;
        for (SevenZMethodConfiguration sevenZMethodConfiguration : this.getContentMethods(this.files.get(this.files.size() - 1))) {
            if (!bl) {
                CountingOutputStream countingOutputStream = new CountingOutputStream(outputStream);
                arrayList.add(countingOutputStream);
                outputStream = countingOutputStream;
            }
            outputStream = Coders.addEncoder(outputStream, sevenZMethodConfiguration.getMethod(), sevenZMethodConfiguration.getOptions());
            bl = false;
        }
        if (!arrayList.isEmpty()) {
            this.additionalCountingStreams = arrayList.toArray(new CountingOutputStream[arrayList.size()]);
        }
        return new CountingOutputStream(this, outputStream){
            final SevenZOutputFile this$0;
            {
                this.this$0 = sevenZOutputFile;
                super(outputStream);
            }

            public void write(int n) throws IOException {
                super.write(n);
                SevenZOutputFile.access$100(this.this$0).update(n);
            }

            public void write(byte[] byArray) throws IOException {
                super.write(byArray);
                SevenZOutputFile.access$100(this.this$0).update(byArray);
            }

            public void write(byte[] byArray, int n, int n2) throws IOException {
                super.write(byArray, n, n2);
                SevenZOutputFile.access$100(this.this$0).update(byArray, n, n2);
            }
        };
    }

    private Iterable<? extends SevenZMethodConfiguration> getContentMethods(SevenZArchiveEntry sevenZArchiveEntry) {
        Iterable<? extends SevenZMethodConfiguration> iterable = sevenZArchiveEntry.getContentMethods();
        return iterable == null ? this.contentMethods : iterable;
    }

    private void writeHeader(DataOutput dataOutput) throws IOException {
        dataOutput.write(1);
        dataOutput.write(4);
        this.writeStreamsInfo(dataOutput);
        this.writeFilesInfo(dataOutput);
        dataOutput.write(0);
    }

    private void writeStreamsInfo(DataOutput dataOutput) throws IOException {
        if (this.numNonEmptyStreams > 0) {
            this.writePackInfo(dataOutput);
            this.writeUnpackInfo(dataOutput);
        }
        this.writeSubStreamsInfo(dataOutput);
        dataOutput.write(0);
    }

    private void writePackInfo(DataOutput dataOutput) throws IOException {
        dataOutput.write(6);
        this.writeUint64(dataOutput, 0L);
        this.writeUint64(dataOutput, 0xFFFFFFFFL & (long)this.numNonEmptyStreams);
        dataOutput.write(9);
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (!sevenZArchiveEntry.hasStream()) continue;
            this.writeUint64(dataOutput, sevenZArchiveEntry.getCompressedSize());
        }
        dataOutput.write(10);
        dataOutput.write(1);
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (!sevenZArchiveEntry.hasStream()) continue;
            dataOutput.writeInt(Integer.reverseBytes((int)sevenZArchiveEntry.getCompressedCrcValue()));
        }
        dataOutput.write(0);
    }

    private void writeUnpackInfo(DataOutput dataOutput) throws IOException {
        dataOutput.write(7);
        dataOutput.write(11);
        this.writeUint64(dataOutput, this.numNonEmptyStreams);
        dataOutput.write(0);
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (!sevenZArchiveEntry.hasStream()) continue;
            this.writeFolder(dataOutput, sevenZArchiveEntry);
        }
        dataOutput.write(12);
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (!sevenZArchiveEntry.hasStream()) continue;
            long[] lArray = this.additionalSizes.get(sevenZArchiveEntry);
            if (lArray != null) {
                for (long l : lArray) {
                    this.writeUint64(dataOutput, l);
                }
            }
            this.writeUint64(dataOutput, sevenZArchiveEntry.getSize());
        }
        dataOutput.write(10);
        dataOutput.write(1);
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (!sevenZArchiveEntry.hasStream()) continue;
            dataOutput.writeInt(Integer.reverseBytes((int)sevenZArchiveEntry.getCrcValue()));
        }
        dataOutput.write(0);
    }

    private void writeFolder(DataOutput dataOutput, SevenZArchiveEntry sevenZArchiveEntry) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n = 0;
        for (SevenZMethodConfiguration sevenZMethodConfiguration : this.getContentMethods(sevenZArchiveEntry)) {
            ++n;
            this.writeSingleCodec(sevenZMethodConfiguration, byteArrayOutputStream);
        }
        this.writeUint64(dataOutput, n);
        dataOutput.write(byteArrayOutputStream.toByteArray());
        for (int i = 0; i < n - 1; ++i) {
            this.writeUint64(dataOutput, i + 1);
            this.writeUint64(dataOutput, i);
        }
    }

    private void writeSingleCodec(SevenZMethodConfiguration sevenZMethodConfiguration, OutputStream outputStream) throws IOException {
        byte[] byArray = sevenZMethodConfiguration.getMethod().getId();
        byte[] byArray2 = Coders.findByMethod(sevenZMethodConfiguration.getMethod()).getOptionsAsProperties(sevenZMethodConfiguration.getOptions());
        int n = byArray.length;
        if (byArray2.length > 0) {
            n |= 0x20;
        }
        outputStream.write(n);
        outputStream.write(byArray);
        if (byArray2.length > 0) {
            outputStream.write(byArray2.length);
            outputStream.write(byArray2);
        }
    }

    private void writeSubStreamsInfo(DataOutput dataOutput) throws IOException {
        dataOutput.write(8);
        dataOutput.write(0);
    }

    private void writeFilesInfo(DataOutput dataOutput) throws IOException {
        dataOutput.write(5);
        this.writeUint64(dataOutput, this.files.size());
        this.writeFileEmptyStreams(dataOutput);
        this.writeFileEmptyFiles(dataOutput);
        this.writeFileAntiItems(dataOutput);
        this.writeFileNames(dataOutput);
        this.writeFileCTimes(dataOutput);
        this.writeFileATimes(dataOutput);
        this.writeFileMTimes(dataOutput);
        this.writeFileWindowsAttributes(dataOutput);
        dataOutput.write(0);
    }

    private void writeFileEmptyStreams(DataOutput dataOutput) throws IOException {
        boolean bl = false;
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            if (sevenZArchiveEntry.hasStream()) continue;
            bl = true;
            break;
        }
        if (bl) {
            dataOutput.write(14);
            BitSet bitSet = new BitSet(this.files.size());
            for (int i = 0; i < this.files.size(); ++i) {
                bitSet.set(i, !this.files.get(i).hasStream());
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            this.writeBits(dataOutputStream, bitSet, this.files.size());
            dataOutputStream.flush();
            byte[] byArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byArray.length);
            dataOutput.write(byArray);
        }
    }

    private void writeFileEmptyFiles(DataOutput dataOutput) throws IOException {
        boolean bl = false;
        int n = 0;
        BitSet bitSet = new BitSet(0);
        for (int i = 0; i < this.files.size(); ++i) {
            if (this.files.get(i).hasStream()) continue;
            boolean bl2 = this.files.get(i).isDirectory();
            bitSet.set(n++, !bl2);
            bl |= !bl2;
        }
        if (bl) {
            dataOutput.write(15);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            this.writeBits(dataOutputStream, bitSet, n);
            dataOutputStream.flush();
            byte[] byArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byArray.length);
            dataOutput.write(byArray);
        }
    }

    private void writeFileAntiItems(DataOutput dataOutput) throws IOException {
        boolean bl = false;
        BitSet bitSet = new BitSet(0);
        int n = 0;
        for (int i = 0; i < this.files.size(); ++i) {
            if (this.files.get(i).hasStream()) continue;
            boolean bl2 = this.files.get(i).isAntiItem();
            bitSet.set(n++, bl2);
            bl |= bl2;
        }
        if (bl) {
            dataOutput.write(16);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            this.writeBits(dataOutputStream, bitSet, n);
            dataOutputStream.flush();
            byte[] byArray = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, byArray.length);
            dataOutput.write(byArray);
        }
    }

    private void writeFileNames(DataOutput dataOutput) throws IOException {
        dataOutput.write(17);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.write(0);
        for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
            dataOutputStream.write(sevenZArchiveEntry.getName().getBytes("UTF-16LE"));
            dataOutputStream.writeShort(0);
        }
        dataOutputStream.flush();
        Object object = byteArrayOutputStream.toByteArray();
        this.writeUint64(dataOutput, ((Object)object).length);
        dataOutput.write((byte[])object);
    }

    private void writeFileCTimes(DataOutput dataOutput) throws IOException {
        int n = 0;
        for (SevenZArchiveEntry object2 : this.files) {
            if (!object2.getHasCreationDate()) continue;
            ++n;
        }
        if (n > 0) {
            Object object;
            dataOutput.write(18);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (n != this.files.size()) {
                dataOutputStream.write(0);
                object = new BitSet(this.files.size());
                for (int sevenZArchiveEntry = 0; sevenZArchiveEntry < this.files.size(); ++sevenZArchiveEntry) {
                    ((BitSet)object).set(sevenZArchiveEntry, this.files.get(sevenZArchiveEntry).getHasCreationDate());
                }
                this.writeBits(dataOutputStream, (BitSet)object, this.files.size());
            } else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (!sevenZArchiveEntry.getHasCreationDate()) continue;
                dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getCreationDate())));
            }
            dataOutputStream.flush();
            object = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, ((Object)object).length);
            dataOutput.write((byte[])object);
        }
    }

    private void writeFileATimes(DataOutput dataOutput) throws IOException {
        int n = 0;
        for (SevenZArchiveEntry object2 : this.files) {
            if (!object2.getHasAccessDate()) continue;
            ++n;
        }
        if (n > 0) {
            Object object;
            dataOutput.write(19);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (n != this.files.size()) {
                dataOutputStream.write(0);
                object = new BitSet(this.files.size());
                for (int sevenZArchiveEntry = 0; sevenZArchiveEntry < this.files.size(); ++sevenZArchiveEntry) {
                    ((BitSet)object).set(sevenZArchiveEntry, this.files.get(sevenZArchiveEntry).getHasAccessDate());
                }
                this.writeBits(dataOutputStream, (BitSet)object, this.files.size());
            } else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (!sevenZArchiveEntry.getHasAccessDate()) continue;
                dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getAccessDate())));
            }
            dataOutputStream.flush();
            object = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, ((Object)object).length);
            dataOutput.write((byte[])object);
        }
    }

    private void writeFileMTimes(DataOutput dataOutput) throws IOException {
        int n = 0;
        for (SevenZArchiveEntry object2 : this.files) {
            if (!object2.getHasLastModifiedDate()) continue;
            ++n;
        }
        if (n > 0) {
            Object object;
            dataOutput.write(20);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (n != this.files.size()) {
                dataOutputStream.write(0);
                object = new BitSet(this.files.size());
                for (int sevenZArchiveEntry = 0; sevenZArchiveEntry < this.files.size(); ++sevenZArchiveEntry) {
                    ((BitSet)object).set(sevenZArchiveEntry, this.files.get(sevenZArchiveEntry).getHasLastModifiedDate());
                }
                this.writeBits(dataOutputStream, (BitSet)object, this.files.size());
            } else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (!sevenZArchiveEntry.getHasLastModifiedDate()) continue;
                dataOutputStream.writeLong(Long.reverseBytes(SevenZArchiveEntry.javaTimeToNtfsTime(sevenZArchiveEntry.getLastModifiedDate())));
            }
            dataOutputStream.flush();
            object = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, ((Object)object).length);
            dataOutput.write((byte[])object);
        }
    }

    private void writeFileWindowsAttributes(DataOutput dataOutput) throws IOException {
        int n = 0;
        for (SevenZArchiveEntry object2 : this.files) {
            if (!object2.getHasWindowsAttributes()) continue;
            ++n;
        }
        if (n > 0) {
            Object object;
            dataOutput.write(21);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (n != this.files.size()) {
                dataOutputStream.write(0);
                object = new BitSet(this.files.size());
                for (int sevenZArchiveEntry = 0; sevenZArchiveEntry < this.files.size(); ++sevenZArchiveEntry) {
                    ((BitSet)object).set(sevenZArchiveEntry, this.files.get(sevenZArchiveEntry).getHasWindowsAttributes());
                }
                this.writeBits(dataOutputStream, (BitSet)object, this.files.size());
            } else {
                dataOutputStream.write(1);
            }
            dataOutputStream.write(0);
            for (SevenZArchiveEntry sevenZArchiveEntry : this.files) {
                if (!sevenZArchiveEntry.getHasWindowsAttributes()) continue;
                dataOutputStream.writeInt(Integer.reverseBytes(sevenZArchiveEntry.getWindowsAttributes()));
            }
            dataOutputStream.flush();
            object = byteArrayOutputStream.toByteArray();
            this.writeUint64(dataOutput, ((Object)object).length);
            dataOutput.write((byte[])object);
        }
    }

    private void writeUint64(DataOutput dataOutput, long l) throws IOException {
        int n;
        int n2 = 0;
        int n3 = 128;
        for (n = 0; n < 8; ++n) {
            if (l < 1L << 7 * (n + 1)) {
                n2 = (int)((long)n2 | l >>> 8 * n);
                break;
            }
            n2 |= n3;
            n3 >>>= 1;
        }
        dataOutput.write(n2);
        while (n > 0) {
            dataOutput.write((int)(0xFFL & l));
            l >>>= 8;
            --n;
        }
    }

    private void writeBits(DataOutput dataOutput, BitSet bitSet, int n) throws IOException {
        int n2 = 0;
        int n3 = 7;
        for (int i = 0; i < n; ++i) {
            n2 |= (bitSet.get(i) ? 1 : 0) << n3;
            if (--n3 >= 0) continue;
            dataOutput.write(n2);
            n3 = 7;
            n2 = 0;
        }
        if (n3 != 7) {
            dataOutput.write(n2);
        }
    }

    private static <T> Iterable<T> reverse(Iterable<T> iterable) {
        LinkedList<T> linkedList = new LinkedList<T>();
        for (T t : iterable) {
            linkedList.addFirst(t);
        }
        return linkedList;
    }

    static CRC32 access$100(SevenZOutputFile sevenZOutputFile) {
        return sevenZOutputFile.crc32;
    }

    static RandomAccessFile access$200(SevenZOutputFile sevenZOutputFile) {
        return sevenZOutputFile.file;
    }

    static CRC32 access$300(SevenZOutputFile sevenZOutputFile) {
        return sevenZOutputFile.compressedCrc32;
    }

    static long access$408(SevenZOutputFile sevenZOutputFile) {
        return sevenZOutputFile.fileBytesWritten++;
    }

    static long access$414(SevenZOutputFile sevenZOutputFile, long l) {
        return sevenZOutputFile.fileBytesWritten += l;
    }

    private class OutputStreamWrapper
    extends OutputStream {
        final SevenZOutputFile this$0;

        private OutputStreamWrapper(SevenZOutputFile sevenZOutputFile) {
            this.this$0 = sevenZOutputFile;
        }

        public void write(int n) throws IOException {
            SevenZOutputFile.access$200(this.this$0).write(n);
            SevenZOutputFile.access$300(this.this$0).update(n);
            SevenZOutputFile.access$408(this.this$0);
        }

        public void write(byte[] byArray) throws IOException {
            this.write(byArray, 0, byArray.length);
        }

        public void write(byte[] byArray, int n, int n2) throws IOException {
            SevenZOutputFile.access$200(this.this$0).write(byArray, n, n2);
            SevenZOutputFile.access$300(this.this$0).update(byArray, n, n2);
            SevenZOutputFile.access$414(this.this$0, n2);
        }

        public void flush() throws IOException {
        }

        public void close() throws IOException {
        }

        OutputStreamWrapper(SevenZOutputFile sevenZOutputFile, 1 var2_2) {
            this(sevenZOutputFile);
        }
    }
}

