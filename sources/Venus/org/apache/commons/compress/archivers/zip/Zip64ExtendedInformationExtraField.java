/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.ZipEightByteInteger;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipLong;
import org.apache.commons.compress.archivers.zip.ZipShort;

public class Zip64ExtendedInformationExtraField
implements ZipExtraField {
    static final ZipShort HEADER_ID = new ZipShort(1);
    private static final String LFH_MUST_HAVE_BOTH_SIZES_MSG = "Zip64 extended information must contain both size values in the local file header.";
    private static final byte[] EMPTY = new byte[0];
    private ZipEightByteInteger size;
    private ZipEightByteInteger compressedSize;
    private ZipEightByteInteger relativeHeaderOffset;
    private ZipLong diskStart;
    private byte[] rawCentralDirectoryData;

    public Zip64ExtendedInformationExtraField() {
    }

    public Zip64ExtendedInformationExtraField(ZipEightByteInteger zipEightByteInteger, ZipEightByteInteger zipEightByteInteger2) {
        this(zipEightByteInteger, zipEightByteInteger2, null, null);
    }

    public Zip64ExtendedInformationExtraField(ZipEightByteInteger zipEightByteInteger, ZipEightByteInteger zipEightByteInteger2, ZipEightByteInteger zipEightByteInteger3, ZipLong zipLong) {
        this.size = zipEightByteInteger;
        this.compressedSize = zipEightByteInteger2;
        this.relativeHeaderOffset = zipEightByteInteger3;
        this.diskStart = zipLong;
    }

    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    public ZipShort getLocalFileDataLength() {
        return new ZipShort(this.size != null ? 16 : 0);
    }

    public ZipShort getCentralDirectoryLength() {
        return new ZipShort((this.size != null ? 8 : 0) + (this.compressedSize != null ? 8 : 0) + (this.relativeHeaderOffset != null ? 8 : 0) + (this.diskStart != null ? 4 : 0));
    }

    public byte[] getLocalFileDataData() {
        if (this.size != null || this.compressedSize != null) {
            if (this.size == null || this.compressedSize == null) {
                throw new IllegalArgumentException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
            }
            byte[] byArray = new byte[16];
            this.addSizes(byArray);
            return byArray;
        }
        return EMPTY;
    }

    public byte[] getCentralDirectoryData() {
        byte[] byArray = new byte[this.getCentralDirectoryLength().getValue()];
        int n = this.addSizes(byArray);
        if (this.relativeHeaderOffset != null) {
            System.arraycopy(this.relativeHeaderOffset.getBytes(), 0, byArray, n, 8);
            n += 8;
        }
        if (this.diskStart != null) {
            System.arraycopy(this.diskStart.getBytes(), 0, byArray, n, 4);
            n += 4;
        }
        return byArray;
    }

    public void parseFromLocalFileData(byte[] byArray, int n, int n2) throws ZipException {
        if (n2 == 0) {
            return;
        }
        if (n2 < 16) {
            throw new ZipException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
        }
        this.size = new ZipEightByteInteger(byArray, n);
        this.compressedSize = new ZipEightByteInteger(byArray, n += 8);
        n += 8;
        int n3 = n2 - 16;
        if (n3 >= 8) {
            this.relativeHeaderOffset = new ZipEightByteInteger(byArray, n);
            n += 8;
            n3 -= 8;
        }
        if (n3 >= 4) {
            this.diskStart = new ZipLong(byArray, n);
            n += 4;
            n3 -= 4;
        }
    }

    public void parseFromCentralDirectoryData(byte[] byArray, int n, int n2) throws ZipException {
        this.rawCentralDirectoryData = new byte[n2];
        System.arraycopy(byArray, n, this.rawCentralDirectoryData, 0, n2);
        if (n2 >= 28) {
            this.parseFromLocalFileData(byArray, n, n2);
        } else if (n2 == 24) {
            this.size = new ZipEightByteInteger(byArray, n);
            this.compressedSize = new ZipEightByteInteger(byArray, n += 8);
            this.relativeHeaderOffset = new ZipEightByteInteger(byArray, n += 8);
        } else if (n2 % 8 == 4) {
            this.diskStart = new ZipLong(byArray, n + n2 - 4);
        }
    }

    public void reparseCentralDirectoryData(boolean bl, boolean bl2, boolean bl3, boolean bl4) throws ZipException {
        if (this.rawCentralDirectoryData != null) {
            int n = (bl ? 8 : 0) + (bl2 ? 8 : 0) + (bl3 ? 8 : 0) + (bl4 ? 4 : 0);
            if (this.rawCentralDirectoryData.length < n) {
                throw new ZipException("central directory zip64 extended information extra field's length doesn't match central directory data.  Expected length " + n + " but is " + this.rawCentralDirectoryData.length);
            }
            int n2 = 0;
            if (bl) {
                this.size = new ZipEightByteInteger(this.rawCentralDirectoryData, n2);
                n2 += 8;
            }
            if (bl2) {
                this.compressedSize = new ZipEightByteInteger(this.rawCentralDirectoryData, n2);
                n2 += 8;
            }
            if (bl3) {
                this.relativeHeaderOffset = new ZipEightByteInteger(this.rawCentralDirectoryData, n2);
                n2 += 8;
            }
            if (bl4) {
                this.diskStart = new ZipLong(this.rawCentralDirectoryData, n2);
                n2 += 4;
            }
        }
    }

    public ZipEightByteInteger getSize() {
        return this.size;
    }

    public void setSize(ZipEightByteInteger zipEightByteInteger) {
        this.size = zipEightByteInteger;
    }

    public ZipEightByteInteger getCompressedSize() {
        return this.compressedSize;
    }

    public void setCompressedSize(ZipEightByteInteger zipEightByteInteger) {
        this.compressedSize = zipEightByteInteger;
    }

    public ZipEightByteInteger getRelativeHeaderOffset() {
        return this.relativeHeaderOffset;
    }

    public void setRelativeHeaderOffset(ZipEightByteInteger zipEightByteInteger) {
        this.relativeHeaderOffset = zipEightByteInteger;
    }

    public ZipLong getDiskStartNumber() {
        return this.diskStart;
    }

    public void setDiskStartNumber(ZipLong zipLong) {
        this.diskStart = zipLong;
    }

    private int addSizes(byte[] byArray) {
        int n = 0;
        if (this.size != null) {
            System.arraycopy(this.size.getBytes(), 0, byArray, 0, 8);
            n += 8;
        }
        if (this.compressedSize != null) {
            System.arraycopy(this.compressedSize.getBytes(), 0, byArray, n, 8);
            n += 8;
        }
        return n;
    }
}

