/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.util.Date;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipLong;
import org.apache.commons.compress.archivers.zip.ZipShort;
import org.apache.commons.compress.archivers.zip.ZipUtil;

public class X5455_ExtendedTimestamp
implements ZipExtraField,
Cloneable,
Serializable {
    private static final ZipShort HEADER_ID = new ZipShort(21589);
    private static final long serialVersionUID = 1L;
    public static final byte MODIFY_TIME_BIT = 1;
    public static final byte ACCESS_TIME_BIT = 2;
    public static final byte CREATE_TIME_BIT = 4;
    private byte flags;
    private boolean bit0_modifyTimePresent;
    private boolean bit1_accessTimePresent;
    private boolean bit2_createTimePresent;
    private ZipLong modifyTime;
    private ZipLong accessTime;
    private ZipLong createTime;

    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    public ZipShort getLocalFileDataLength() {
        return new ZipShort(1 + (this.bit0_modifyTimePresent ? 4 : 0) + (this.bit1_accessTimePresent && this.accessTime != null ? 4 : 0) + (this.bit2_createTimePresent && this.createTime != null ? 4 : 0));
    }

    public ZipShort getCentralDirectoryLength() {
        return new ZipShort(1 + (this.bit0_modifyTimePresent ? 4 : 0));
    }

    public byte[] getLocalFileDataData() {
        byte[] byArray = new byte[this.getLocalFileDataLength().getValue()];
        int n = 0;
        byArray[n++] = 0;
        if (this.bit0_modifyTimePresent) {
            byArray[0] = (byte)(byArray[0] | 1);
            System.arraycopy(this.modifyTime.getBytes(), 0, byArray, n, 4);
            n += 4;
        }
        if (this.bit1_accessTimePresent && this.accessTime != null) {
            byArray[0] = (byte)(byArray[0] | 2);
            System.arraycopy(this.accessTime.getBytes(), 0, byArray, n, 4);
            n += 4;
        }
        if (this.bit2_createTimePresent && this.createTime != null) {
            byArray[0] = (byte)(byArray[0] | 4);
            System.arraycopy(this.createTime.getBytes(), 0, byArray, n, 4);
            n += 4;
        }
        return byArray;
    }

    public byte[] getCentralDirectoryData() {
        byte[] byArray = new byte[this.getCentralDirectoryLength().getValue()];
        byte[] byArray2 = this.getLocalFileDataData();
        System.arraycopy(byArray2, 0, byArray, 0, byArray.length);
        return byArray;
    }

    public void parseFromLocalFileData(byte[] byArray, int n, int n2) throws ZipException {
        this.reset();
        int n3 = n + n2;
        this.setFlags(byArray[n++]);
        if (this.bit0_modifyTimePresent) {
            this.modifyTime = new ZipLong(byArray, n);
            n += 4;
        }
        if (this.bit1_accessTimePresent && n + 4 <= n3) {
            this.accessTime = new ZipLong(byArray, n);
            n += 4;
        }
        if (this.bit2_createTimePresent && n + 4 <= n3) {
            this.createTime = new ZipLong(byArray, n);
            n += 4;
        }
    }

    public void parseFromCentralDirectoryData(byte[] byArray, int n, int n2) throws ZipException {
        this.reset();
        this.parseFromLocalFileData(byArray, n, n2);
    }

    private void reset() {
        this.setFlags((byte)0);
        this.modifyTime = null;
        this.accessTime = null;
        this.createTime = null;
    }

    public void setFlags(byte by) {
        this.flags = by;
        this.bit0_modifyTimePresent = (by & 1) == 1;
        this.bit1_accessTimePresent = (by & 2) == 2;
        this.bit2_createTimePresent = (by & 4) == 4;
    }

    public byte getFlags() {
        return this.flags;
    }

    public boolean isBit0_modifyTimePresent() {
        return this.bit0_modifyTimePresent;
    }

    public boolean isBit1_accessTimePresent() {
        return this.bit1_accessTimePresent;
    }

    public boolean isBit2_createTimePresent() {
        return this.bit2_createTimePresent;
    }

    public ZipLong getModifyTime() {
        return this.modifyTime;
    }

    public ZipLong getAccessTime() {
        return this.accessTime;
    }

    public ZipLong getCreateTime() {
        return this.createTime;
    }

    public Date getModifyJavaTime() {
        return this.modifyTime != null ? new Date(this.modifyTime.getValue() * 1000L) : null;
    }

    public Date getAccessJavaTime() {
        return this.accessTime != null ? new Date(this.accessTime.getValue() * 1000L) : null;
    }

    public Date getCreateJavaTime() {
        return this.createTime != null ? new Date(this.createTime.getValue() * 1000L) : null;
    }

    public void setModifyTime(ZipLong zipLong) {
        this.bit0_modifyTimePresent = zipLong != null;
        this.flags = (byte)(zipLong != null ? this.flags | 1 : this.flags & 0xFFFFFFFE);
        this.modifyTime = zipLong;
    }

    public void setAccessTime(ZipLong zipLong) {
        this.bit1_accessTimePresent = zipLong != null;
        this.flags = (byte)(zipLong != null ? this.flags | 2 : this.flags & 0xFFFFFFFD);
        this.accessTime = zipLong;
    }

    public void setCreateTime(ZipLong zipLong) {
        this.bit2_createTimePresent = zipLong != null;
        this.flags = (byte)(zipLong != null ? this.flags | 4 : this.flags & 0xFFFFFFFB);
        this.createTime = zipLong;
    }

    public void setModifyJavaTime(Date date) {
        this.setModifyTime(X5455_ExtendedTimestamp.dateToZipLong(date));
    }

    public void setAccessJavaTime(Date date) {
        this.setAccessTime(X5455_ExtendedTimestamp.dateToZipLong(date));
    }

    public void setCreateJavaTime(Date date) {
        this.setCreateTime(X5455_ExtendedTimestamp.dateToZipLong(date));
    }

    private static ZipLong dateToZipLong(Date date) {
        if (date == null) {
            return null;
        }
        long l = 0x100000000L;
        long l2 = date.getTime() / 1000L;
        if (l2 >= 0x100000000L) {
            throw new IllegalArgumentException("Cannot set an X5455 timestamp larger than 2^32: " + l2);
        }
        return new ZipLong(l2);
    }

    public String toString() {
        Date date;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0x5455 Zip Extra Field: Flags=");
        stringBuilder.append(Integer.toBinaryString(ZipUtil.unsignedIntToSignedByte(this.flags))).append(" ");
        if (this.bit0_modifyTimePresent && this.modifyTime != null) {
            date = this.getModifyJavaTime();
            stringBuilder.append(" Modify:[").append(date).append("] ");
        }
        if (this.bit1_accessTimePresent && this.accessTime != null) {
            date = this.getAccessJavaTime();
            stringBuilder.append(" Access:[").append(date).append("] ");
        }
        if (this.bit2_createTimePresent && this.createTime != null) {
            date = this.getCreateJavaTime();
            stringBuilder.append(" Create:[").append(date).append("] ");
        }
        return stringBuilder.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object object) {
        if (object instanceof X5455_ExtendedTimestamp) {
            X5455_ExtendedTimestamp x5455_ExtendedTimestamp = (X5455_ExtendedTimestamp)object;
            return (this.flags & 7) == (x5455_ExtendedTimestamp.flags & 7) && (this.modifyTime == x5455_ExtendedTimestamp.modifyTime || this.modifyTime != null && this.modifyTime.equals(x5455_ExtendedTimestamp.modifyTime)) && (this.accessTime == x5455_ExtendedTimestamp.accessTime || this.accessTime != null && this.accessTime.equals(x5455_ExtendedTimestamp.accessTime)) && (this.createTime == x5455_ExtendedTimestamp.createTime || this.createTime != null && this.createTime.equals(x5455_ExtendedTimestamp.createTime));
        }
        return true;
    }

    public int hashCode() {
        int n = -123 * (this.flags & 7);
        if (this.modifyTime != null) {
            n ^= this.modifyTime.hashCode();
        }
        if (this.accessTime != null) {
            n ^= Integer.rotateLeft(this.accessTime.hashCode(), 11);
        }
        if (this.createTime != null) {
            n ^= Integer.rotateLeft(this.createTime.hashCode(), 22);
        }
        return n;
    }
}

