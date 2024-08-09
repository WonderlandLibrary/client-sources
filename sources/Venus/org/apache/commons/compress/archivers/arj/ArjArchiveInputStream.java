/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.arj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.CRC32;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.arj.ArjArchiveEntry;
import org.apache.commons.compress.archivers.arj.LocalFileHeader;
import org.apache.commons.compress.archivers.arj.MainHeader;
import org.apache.commons.compress.utils.BoundedInputStream;
import org.apache.commons.compress.utils.CRC32VerifyingInputStream;
import org.apache.commons.compress.utils.IOUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ArjArchiveInputStream
extends ArchiveInputStream {
    private static final int ARJ_MAGIC_1 = 96;
    private static final int ARJ_MAGIC_2 = 234;
    private final DataInputStream in;
    private final String charsetName;
    private final MainHeader mainHeader;
    private LocalFileHeader currentLocalFileHeader = null;
    private InputStream currentInputStream = null;

    public ArjArchiveInputStream(InputStream inputStream, String string) throws ArchiveException {
        this.in = new DataInputStream(inputStream);
        this.charsetName = string;
        try {
            this.mainHeader = this.readMainHeader();
            if ((this.mainHeader.arjFlags & 1) != 0) {
                throw new ArchiveException("Encrypted ARJ files are unsupported");
            }
            if ((this.mainHeader.arjFlags & 4) != 0) {
                throw new ArchiveException("Multi-volume ARJ files are unsupported");
            }
        } catch (IOException iOException) {
            throw new ArchiveException(iOException.getMessage(), iOException);
        }
    }

    public ArjArchiveInputStream(InputStream inputStream) throws ArchiveException {
        this(inputStream, "CP437");
    }

    public void close() throws IOException {
        this.in.close();
    }

    private int read8(DataInputStream dataInputStream) throws IOException {
        int n = dataInputStream.readUnsignedByte();
        this.count(1);
        return n;
    }

    private int read16(DataInputStream dataInputStream) throws IOException {
        int n = dataInputStream.readUnsignedShort();
        this.count(2);
        return Integer.reverseBytes(n) >>> 16;
    }

    private int read32(DataInputStream dataInputStream) throws IOException {
        int n = dataInputStream.readInt();
        this.count(4);
        return Integer.reverseBytes(n);
    }

    private String readString(DataInputStream dataInputStream) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((n = dataInputStream.readUnsignedByte()) != 0) {
            byteArrayOutputStream.write(n);
        }
        if (this.charsetName != null) {
            return new String(byteArrayOutputStream.toByteArray(), this.charsetName);
        }
        return new String(byteArrayOutputStream.toByteArray());
    }

    private void readFully(DataInputStream dataInputStream, byte[] byArray) throws IOException {
        dataInputStream.readFully(byArray);
        this.count(byArray.length);
    }

    private byte[] readHeader() throws IOException {
        boolean bl = false;
        byte[] byArray = null;
        do {
            int n = 0;
            int n2 = this.read8(this.in);
            do {
                n = n2;
                n2 = this.read8(this.in);
            } while (n != 96 && n2 != 234);
            int n3 = this.read16(this.in);
            if (n3 == 0) {
                return null;
            }
            if (n3 > 2600) continue;
            byArray = new byte[n3];
            this.readFully(this.in, byArray);
            long l = (long)this.read32(this.in) & 0xFFFFFFFFL;
            CRC32 cRC32 = new CRC32();
            cRC32.update(byArray);
            if (l != cRC32.getValue()) continue;
            bl = true;
        } while (!bl);
        return byArray;
    }

    private MainHeader readMainHeader() throws IOException {
        byte[] byArray = this.readHeader();
        if (byArray == null) {
            throw new IOException("Archive ends without any headers");
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
        int n = dataInputStream.readUnsignedByte();
        byte[] byArray2 = new byte[n - 1];
        dataInputStream.readFully(byArray2);
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(byArray2));
        MainHeader mainHeader = new MainHeader();
        mainHeader.archiverVersionNumber = dataInputStream2.readUnsignedByte();
        mainHeader.minVersionToExtract = dataInputStream2.readUnsignedByte();
        mainHeader.hostOS = dataInputStream2.readUnsignedByte();
        mainHeader.arjFlags = dataInputStream2.readUnsignedByte();
        mainHeader.securityVersion = dataInputStream2.readUnsignedByte();
        mainHeader.fileType = dataInputStream2.readUnsignedByte();
        mainHeader.reserved = dataInputStream2.readUnsignedByte();
        mainHeader.dateTimeCreated = this.read32(dataInputStream2);
        mainHeader.dateTimeModified = this.read32(dataInputStream2);
        mainHeader.archiveSize = 0xFFFFFFFFL & (long)this.read32(dataInputStream2);
        mainHeader.securityEnvelopeFilePosition = this.read32(dataInputStream2);
        mainHeader.fileSpecPosition = this.read16(dataInputStream2);
        mainHeader.securityEnvelopeLength = this.read16(dataInputStream2);
        this.pushedBackBytes(20L);
        mainHeader.encryptionVersion = dataInputStream2.readUnsignedByte();
        mainHeader.lastChapter = dataInputStream2.readUnsignedByte();
        if (n >= 33) {
            mainHeader.arjProtectionFactor = dataInputStream2.readUnsignedByte();
            mainHeader.arjFlags2 = dataInputStream2.readUnsignedByte();
            dataInputStream2.readUnsignedByte();
            dataInputStream2.readUnsignedByte();
        }
        mainHeader.name = this.readString(dataInputStream);
        mainHeader.comment = this.readString(dataInputStream);
        int n2 = this.read16(this.in);
        if (n2 > 0) {
            mainHeader.extendedHeaderBytes = new byte[n2];
            this.readFully(this.in, mainHeader.extendedHeaderBytes);
            long l = 0xFFFFFFFFL & (long)this.read32(this.in);
            CRC32 cRC32 = new CRC32();
            cRC32.update(mainHeader.extendedHeaderBytes);
            if (l != cRC32.getValue()) {
                throw new IOException("Extended header CRC32 verification failure");
            }
        }
        return mainHeader;
    }

    private LocalFileHeader readLocalFileHeader() throws IOException {
        int n;
        byte[] byArray = this.readHeader();
        if (byArray == null) {
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
        int n2 = dataInputStream.readUnsignedByte();
        byte[] byArray2 = new byte[n2 - 1];
        dataInputStream.readFully(byArray2);
        DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(byArray2));
        LocalFileHeader localFileHeader = new LocalFileHeader();
        localFileHeader.archiverVersionNumber = dataInputStream2.readUnsignedByte();
        localFileHeader.minVersionToExtract = dataInputStream2.readUnsignedByte();
        localFileHeader.hostOS = dataInputStream2.readUnsignedByte();
        localFileHeader.arjFlags = dataInputStream2.readUnsignedByte();
        localFileHeader.method = dataInputStream2.readUnsignedByte();
        localFileHeader.fileType = dataInputStream2.readUnsignedByte();
        localFileHeader.reserved = dataInputStream2.readUnsignedByte();
        localFileHeader.dateTimeModified = this.read32(dataInputStream2);
        localFileHeader.compressedSize = 0xFFFFFFFFL & (long)this.read32(dataInputStream2);
        localFileHeader.originalSize = 0xFFFFFFFFL & (long)this.read32(dataInputStream2);
        localFileHeader.originalCrc32 = 0xFFFFFFFFL & (long)this.read32(dataInputStream2);
        localFileHeader.fileSpecPosition = this.read16(dataInputStream2);
        localFileHeader.fileAccessMode = this.read16(dataInputStream2);
        this.pushedBackBytes(20L);
        localFileHeader.firstChapter = dataInputStream2.readUnsignedByte();
        localFileHeader.lastChapter = dataInputStream2.readUnsignedByte();
        this.readExtraData(n2, dataInputStream2, localFileHeader);
        localFileHeader.name = this.readString(dataInputStream);
        localFileHeader.comment = this.readString(dataInputStream);
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        while ((n = this.read16(this.in)) > 0) {
            byte[] byArray3 = new byte[n];
            this.readFully(this.in, byArray3);
            long l = 0xFFFFFFFFL & (long)this.read32(this.in);
            CRC32 cRC32 = new CRC32();
            cRC32.update(byArray3);
            if (l != cRC32.getValue()) {
                throw new IOException("Extended header CRC32 verification failure");
            }
            arrayList.add(byArray3);
        }
        localFileHeader.extendedHeaders = (byte[][])arrayList.toArray((T[])new byte[arrayList.size()][]);
        return localFileHeader;
    }

    private void readExtraData(int n, DataInputStream dataInputStream, LocalFileHeader localFileHeader) throws IOException {
        if (n >= 33) {
            localFileHeader.extendedFilePosition = this.read32(dataInputStream);
            if (n >= 45) {
                localFileHeader.dateTimeAccessed = this.read32(dataInputStream);
                localFileHeader.dateTimeCreated = this.read32(dataInputStream);
                localFileHeader.originalSizeEvenForVolumes = this.read32(dataInputStream);
                this.pushedBackBytes(12L);
            }
            this.pushedBackBytes(4L);
        }
    }

    public static boolean matches(byte[] byArray, int n) {
        return n >= 2 && (0xFF & byArray[0]) == 96 && (0xFF & byArray[1]) == 234;
    }

    public String getArchiveName() {
        return this.mainHeader.name;
    }

    public String getArchiveComment() {
        return this.mainHeader.comment;
    }

    public ArjArchiveEntry getNextEntry() throws IOException {
        if (this.currentInputStream != null) {
            IOUtils.skip(this.currentInputStream, Long.MAX_VALUE);
            this.currentInputStream.close();
            this.currentLocalFileHeader = null;
            this.currentInputStream = null;
        }
        this.currentLocalFileHeader = this.readLocalFileHeader();
        if (this.currentLocalFileHeader != null) {
            this.currentInputStream = new BoundedInputStream(this.in, this.currentLocalFileHeader.compressedSize);
            if (this.currentLocalFileHeader.method == 0) {
                this.currentInputStream = new CRC32VerifyingInputStream(this.currentInputStream, this.currentLocalFileHeader.originalSize, this.currentLocalFileHeader.originalCrc32);
            }
            return new ArjArchiveEntry(this.currentLocalFileHeader);
        }
        this.currentInputStream = null;
        return null;
    }

    public boolean canReadEntryData(ArchiveEntry archiveEntry) {
        return archiveEntry instanceof ArjArchiveEntry && ((ArjArchiveEntry)archiveEntry).getMethod() == 0;
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (this.currentLocalFileHeader == null) {
            throw new IllegalStateException("No current arj entry");
        }
        if (this.currentLocalFileHeader.method != 0) {
            throw new IOException("Unsupported compression method " + this.currentLocalFileHeader.method);
        }
        return this.currentInputStream.read(byArray, n, n2);
    }

    public ArchiveEntry getNextEntry() throws IOException {
        return this.getNextEntry();
    }
}

