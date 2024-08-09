/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipLong;
import org.apache.commons.compress.archivers.zip.ZipShort;

public abstract class AbstractUnicodeExtraField
implements ZipExtraField {
    private long nameCRC32;
    private byte[] unicodeName;
    private byte[] data;

    protected AbstractUnicodeExtraField() {
    }

    protected AbstractUnicodeExtraField(String string, byte[] byArray, int n, int n2) {
        CRC32 cRC32 = new CRC32();
        cRC32.update(byArray, n, n2);
        this.nameCRC32 = cRC32.getValue();
        try {
            this.unicodeName = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("FATAL: UTF-8 encoding not supported.", unsupportedEncodingException);
        }
    }

    protected AbstractUnicodeExtraField(String string, byte[] byArray) {
        this(string, byArray, 0, byArray.length);
    }

    private void assembleData() {
        if (this.unicodeName == null) {
            return;
        }
        this.data = new byte[5 + this.unicodeName.length];
        this.data[0] = 1;
        System.arraycopy(ZipLong.getBytes(this.nameCRC32), 0, this.data, 1, 4);
        System.arraycopy(this.unicodeName, 0, this.data, 5, this.unicodeName.length);
    }

    public long getNameCRC32() {
        return this.nameCRC32;
    }

    public void setNameCRC32(long l) {
        this.nameCRC32 = l;
        this.data = null;
    }

    public byte[] getUnicodeName() {
        byte[] byArray = null;
        if (this.unicodeName != null) {
            byArray = new byte[this.unicodeName.length];
            System.arraycopy(this.unicodeName, 0, byArray, 0, byArray.length);
        }
        return byArray;
    }

    public void setUnicodeName(byte[] byArray) {
        if (byArray != null) {
            this.unicodeName = new byte[byArray.length];
            System.arraycopy(byArray, 0, this.unicodeName, 0, byArray.length);
        } else {
            this.unicodeName = null;
        }
        this.data = null;
    }

    public byte[] getCentralDirectoryData() {
        if (this.data == null) {
            this.assembleData();
        }
        byte[] byArray = null;
        if (this.data != null) {
            byArray = new byte[this.data.length];
            System.arraycopy(this.data, 0, byArray, 0, byArray.length);
        }
        return byArray;
    }

    public ZipShort getCentralDirectoryLength() {
        if (this.data == null) {
            this.assembleData();
        }
        return new ZipShort(this.data != null ? this.data.length : 0);
    }

    public byte[] getLocalFileDataData() {
        return this.getCentralDirectoryData();
    }

    public ZipShort getLocalFileDataLength() {
        return this.getCentralDirectoryLength();
    }

    public void parseFromLocalFileData(byte[] byArray, int n, int n2) throws ZipException {
        if (n2 < 5) {
            throw new ZipException("UniCode path extra data must have at least 5 bytes.");
        }
        byte by = byArray[n];
        if (by != 1) {
            throw new ZipException("Unsupported version [" + by + "] for UniCode path extra data.");
        }
        this.nameCRC32 = ZipLong.getValue(byArray, n + 1);
        this.unicodeName = new byte[n2 - 5];
        System.arraycopy(byArray, n + 5, this.unicodeName, 0, n2 - 5);
        this.data = null;
    }

    public void parseFromCentralDirectoryData(byte[] byArray, int n, int n2) throws ZipException {
        this.parseFromLocalFileData(byArray, n, n2);
    }
}

