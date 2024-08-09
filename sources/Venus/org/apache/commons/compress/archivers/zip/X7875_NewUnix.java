/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipShort;
import org.apache.commons.compress.archivers.zip.ZipUtil;

public class X7875_NewUnix
implements ZipExtraField,
Cloneable,
Serializable {
    private static final ZipShort HEADER_ID = new ZipShort(30837);
    private static final BigInteger ONE_THOUSAND = BigInteger.valueOf(1000L);
    private static final long serialVersionUID = 1L;
    private int version = 1;
    private BigInteger uid;
    private BigInteger gid;

    public X7875_NewUnix() {
        this.reset();
    }

    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    public long getUID() {
        return ZipUtil.bigToLong(this.uid);
    }

    public long getGID() {
        return ZipUtil.bigToLong(this.gid);
    }

    public void setUID(long l) {
        this.uid = ZipUtil.longToBig(l);
    }

    public void setGID(long l) {
        this.gid = ZipUtil.longToBig(l);
    }

    public ZipShort getLocalFileDataLength() {
        int n = X7875_NewUnix.trimLeadingZeroesForceMinLength(this.uid.toByteArray()).length;
        int n2 = X7875_NewUnix.trimLeadingZeroesForceMinLength(this.gid.toByteArray()).length;
        return new ZipShort(3 + n + n2);
    }

    public ZipShort getCentralDirectoryLength() {
        return this.getLocalFileDataLength();
    }

    public byte[] getLocalFileDataData() {
        byte[] byArray = this.uid.toByteArray();
        byte[] byArray2 = this.gid.toByteArray();
        byArray = X7875_NewUnix.trimLeadingZeroesForceMinLength(byArray);
        byArray2 = X7875_NewUnix.trimLeadingZeroesForceMinLength(byArray2);
        byte[] byArray3 = new byte[3 + byArray.length + byArray2.length];
        ZipUtil.reverse(byArray);
        ZipUtil.reverse(byArray2);
        int n = 0;
        byArray3[n++] = ZipUtil.unsignedIntToSignedByte(this.version);
        byArray3[n++] = ZipUtil.unsignedIntToSignedByte(byArray.length);
        System.arraycopy(byArray, 0, byArray3, n, byArray.length);
        n += byArray.length;
        byArray3[n++] = ZipUtil.unsignedIntToSignedByte(byArray2.length);
        System.arraycopy(byArray2, 0, byArray3, n, byArray2.length);
        return byArray3;
    }

    public byte[] getCentralDirectoryData() {
        return this.getLocalFileDataData();
    }

    public void parseFromLocalFileData(byte[] byArray, int n, int n2) throws ZipException {
        this.reset();
        this.version = ZipUtil.signedByteToUnsignedInt(byArray[n++]);
        int n3 = ZipUtil.signedByteToUnsignedInt(byArray[n++]);
        byte[] byArray2 = new byte[n3];
        System.arraycopy(byArray, n, byArray2, 0, n3);
        n += n3;
        this.uid = new BigInteger(1, ZipUtil.reverse(byArray2));
        int n4 = ZipUtil.signedByteToUnsignedInt(byArray[n++]);
        byte[] byArray3 = new byte[n4];
        System.arraycopy(byArray, n, byArray3, 0, n4);
        this.gid = new BigInteger(1, ZipUtil.reverse(byArray3));
    }

    public void parseFromCentralDirectoryData(byte[] byArray, int n, int n2) throws ZipException {
        this.reset();
        this.parseFromLocalFileData(byArray, n, n2);
    }

    private void reset() {
        this.uid = ONE_THOUSAND;
        this.gid = ONE_THOUSAND;
    }

    public String toString() {
        return "0x7875 Zip Extra Field: UID=" + this.uid + " GID=" + this.gid;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object object) {
        if (object instanceof X7875_NewUnix) {
            X7875_NewUnix x7875_NewUnix = (X7875_NewUnix)object;
            return this.version == x7875_NewUnix.version && this.uid.equals(x7875_NewUnix.uid) && this.gid.equals(x7875_NewUnix.gid);
        }
        return true;
    }

    public int hashCode() {
        int n = -1234567 * this.version;
        n ^= Integer.rotateLeft(this.uid.hashCode(), 16);
        return n ^= this.gid.hashCode();
    }

    static byte[] trimLeadingZeroesForceMinLength(byte[] byArray) {
        if (byArray == null) {
            return byArray;
        }
        int n = 0;
        for (byte by : byArray) {
            if (by != 0) break;
            ++n;
        }
        boolean bl = true;
        byte[] byArray2 = new byte[Math.max(1, byArray.length - n)];
        int n2 = byArray2.length - (byArray.length - n);
        System.arraycopy(byArray, n, byArray2, n2, byArray2.length - n2);
        return byArray2;
    }
}

