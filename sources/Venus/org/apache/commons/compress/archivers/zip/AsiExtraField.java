/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.util.zip.CRC32;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipLong;
import org.apache.commons.compress.archivers.zip.ZipShort;

public class AsiExtraField
implements ZipExtraField,
UnixStat,
Cloneable {
    private static final ZipShort HEADER_ID = new ZipShort(30062);
    private static final int WORD = 4;
    private int mode = 0;
    private int uid = 0;
    private int gid = 0;
    private String link = "";
    private boolean dirFlag = false;
    private CRC32 crc = new CRC32();

    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    public ZipShort getLocalFileDataLength() {
        return new ZipShort(14 + this.getLinkedFile().getBytes().length);
    }

    public ZipShort getCentralDirectoryLength() {
        return this.getLocalFileDataLength();
    }

    public byte[] getLocalFileDataData() {
        byte[] byArray = new byte[this.getLocalFileDataLength().getValue() - 4];
        System.arraycopy(ZipShort.getBytes(this.getMode()), 0, byArray, 0, 2);
        byte[] byArray2 = this.getLinkedFile().getBytes();
        System.arraycopy(ZipLong.getBytes(byArray2.length), 0, byArray, 2, 4);
        System.arraycopy(ZipShort.getBytes(this.getUserId()), 0, byArray, 6, 2);
        System.arraycopy(ZipShort.getBytes(this.getGroupId()), 0, byArray, 8, 2);
        System.arraycopy(byArray2, 0, byArray, 10, byArray2.length);
        this.crc.reset();
        this.crc.update(byArray);
        long l = this.crc.getValue();
        byte[] byArray3 = new byte[byArray.length + 4];
        System.arraycopy(ZipLong.getBytes(l), 0, byArray3, 0, 4);
        System.arraycopy(byArray, 0, byArray3, 4, byArray.length);
        return byArray3;
    }

    public byte[] getCentralDirectoryData() {
        return this.getLocalFileDataData();
    }

    public void setUserId(int n) {
        this.uid = n;
    }

    public int getUserId() {
        return this.uid;
    }

    public void setGroupId(int n) {
        this.gid = n;
    }

    public int getGroupId() {
        return this.gid;
    }

    public void setLinkedFile(String string) {
        this.link = string;
        this.mode = this.getMode(this.mode);
    }

    public String getLinkedFile() {
        return this.link;
    }

    public boolean isLink() {
        return this.getLinkedFile().length() != 0;
    }

    public void setMode(int n) {
        this.mode = this.getMode(n);
    }

    public int getMode() {
        return this.mode;
    }

    public void setDirectory(boolean bl) {
        this.dirFlag = bl;
        this.mode = this.getMode(this.mode);
    }

    public boolean isDirectory() {
        return this.dirFlag && !this.isLink();
    }

    public void parseFromLocalFileData(byte[] byArray, int n, int n2) throws ZipException {
        long l = ZipLong.getValue(byArray, n);
        byte[] byArray2 = new byte[n2 - 4];
        System.arraycopy(byArray, n + 4, byArray2, 0, n2 - 4);
        this.crc.reset();
        this.crc.update(byArray2);
        long l2 = this.crc.getValue();
        if (l != l2) {
            throw new ZipException("bad CRC checksum " + Long.toHexString(l) + " instead of " + Long.toHexString(l2));
        }
        int n3 = ZipShort.getValue(byArray2, 0);
        byte[] byArray3 = new byte[(int)ZipLong.getValue(byArray2, 2)];
        this.uid = ZipShort.getValue(byArray2, 6);
        this.gid = ZipShort.getValue(byArray2, 8);
        if (byArray3.length == 0) {
            this.link = "";
        } else {
            System.arraycopy(byArray2, 10, byArray3, 0, byArray3.length);
            this.link = new String(byArray3);
        }
        this.setDirectory((n3 & 0x4000) != 0);
        this.setMode(n3);
    }

    public void parseFromCentralDirectoryData(byte[] byArray, int n, int n2) throws ZipException {
        this.parseFromLocalFileData(byArray, n, n2);
    }

    protected int getMode(int n) {
        int n2 = 32768;
        if (this.isLink()) {
            n2 = 40960;
        } else if (this.isDirectory()) {
            n2 = 16384;
        }
        return n2 | n & 0xFFF;
    }

    public Object clone() {
        try {
            AsiExtraField asiExtraField = (AsiExtraField)super.clone();
            asiExtraField.crc = new CRC32();
            return asiExtraField;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }
}

