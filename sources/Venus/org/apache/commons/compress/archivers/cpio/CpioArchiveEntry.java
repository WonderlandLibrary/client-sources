/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.cpio;

import java.io.File;
import java.util.Date;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.cpio.CpioConstants;
import org.apache.commons.compress.archivers.cpio.CpioUtil;

public class CpioArchiveEntry
implements CpioConstants,
ArchiveEntry {
    private final short fileFormat;
    private final int headerSize;
    private final int alignmentBoundary;
    private long chksum = 0L;
    private long filesize = 0L;
    private long gid = 0L;
    private long inode = 0L;
    private long maj = 0L;
    private long min = 0L;
    private long mode = 0L;
    private long mtime = 0L;
    private String name;
    private long nlink = 0L;
    private long rmaj = 0L;
    private long rmin = 0L;
    private long uid = 0L;

    public CpioArchiveEntry(short s) {
        switch (s) {
            case 1: {
                this.headerSize = 110;
                this.alignmentBoundary = 4;
                break;
            }
            case 2: {
                this.headerSize = 110;
                this.alignmentBoundary = 4;
                break;
            }
            case 4: {
                this.headerSize = 76;
                this.alignmentBoundary = 0;
                break;
            }
            case 8: {
                this.headerSize = 26;
                this.alignmentBoundary = 2;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown header type");
            }
        }
        this.fileFormat = s;
    }

    public CpioArchiveEntry(String string) {
        this(1, string);
    }

    public CpioArchiveEntry(short s, String string) {
        this(s);
        this.name = string;
    }

    public CpioArchiveEntry(String string, long l) {
        this(string);
        this.setSize(l);
    }

    public CpioArchiveEntry(short s, String string, long l) {
        this(s, string);
        this.setSize(l);
    }

    public CpioArchiveEntry(File file, String string) {
        this(1, file, string);
    }

    public CpioArchiveEntry(short s, File file, String string) {
        this(s, string, file.isFile() ? file.length() : 0L);
        if (file.isDirectory()) {
            this.setMode(16384L);
        } else if (file.isFile()) {
            this.setMode(32768L);
        } else {
            throw new IllegalArgumentException("Cannot determine type of file " + file.getName());
        }
        this.setTime(file.lastModified() / 1000L);
    }

    private void checkNewFormat() {
        if ((this.fileFormat & 3) == 0) {
            throw new UnsupportedOperationException();
        }
    }

    private void checkOldFormat() {
        if ((this.fileFormat & 0xC) == 0) {
            throw new UnsupportedOperationException();
        }
    }

    public long getChksum() {
        this.checkNewFormat();
        return this.chksum;
    }

    public long getDevice() {
        this.checkOldFormat();
        return this.min;
    }

    public long getDeviceMaj() {
        this.checkNewFormat();
        return this.maj;
    }

    public long getDeviceMin() {
        this.checkNewFormat();
        return this.min;
    }

    public long getSize() {
        return this.filesize;
    }

    public short getFormat() {
        return this.fileFormat;
    }

    public long getGID() {
        return this.gid;
    }

    public int getHeaderSize() {
        return this.headerSize;
    }

    public int getAlignmentBoundary() {
        return this.alignmentBoundary;
    }

    public int getHeaderPadCount() {
        int n;
        if (this.alignmentBoundary == 0) {
            return 1;
        }
        int n2 = this.headerSize + 1;
        if (this.name != null) {
            n2 += this.name.length();
        }
        if ((n = n2 % this.alignmentBoundary) > 0) {
            return this.alignmentBoundary - n;
        }
        return 1;
    }

    public int getDataPadCount() {
        if (this.alignmentBoundary == 0) {
            return 1;
        }
        long l = this.filesize;
        int n = (int)(l % (long)this.alignmentBoundary);
        if (n > 0) {
            return this.alignmentBoundary - n;
        }
        return 1;
    }

    public long getInode() {
        return this.inode;
    }

    public long getMode() {
        return this.mode == 0L && !"TRAILER!!!".equals(this.name) ? 32768L : this.mode;
    }

    public String getName() {
        return this.name;
    }

    public long getNumberOfLinks() {
        return this.nlink == 0L ? (this.isDirectory() ? 2L : 1L) : this.nlink;
    }

    public long getRemoteDevice() {
        this.checkOldFormat();
        return this.rmin;
    }

    public long getRemoteDeviceMaj() {
        this.checkNewFormat();
        return this.rmaj;
    }

    public long getRemoteDeviceMin() {
        this.checkNewFormat();
        return this.rmin;
    }

    public long getTime() {
        return this.mtime;
    }

    public Date getLastModifiedDate() {
        return new Date(1000L * this.getTime());
    }

    public long getUID() {
        return this.uid;
    }

    public boolean isBlockDevice() {
        return CpioUtil.fileType(this.mode) == 24576L;
    }

    public boolean isCharacterDevice() {
        return CpioUtil.fileType(this.mode) == 8192L;
    }

    public boolean isDirectory() {
        return CpioUtil.fileType(this.mode) == 16384L;
    }

    public boolean isNetwork() {
        return CpioUtil.fileType(this.mode) == 36864L;
    }

    public boolean isPipe() {
        return CpioUtil.fileType(this.mode) == 4096L;
    }

    public boolean isRegularFile() {
        return CpioUtil.fileType(this.mode) == 32768L;
    }

    public boolean isSocket() {
        return CpioUtil.fileType(this.mode) == 49152L;
    }

    public boolean isSymbolicLink() {
        return CpioUtil.fileType(this.mode) == 40960L;
    }

    public void setChksum(long l) {
        this.checkNewFormat();
        this.chksum = l;
    }

    public void setDevice(long l) {
        this.checkOldFormat();
        this.min = l;
    }

    public void setDeviceMaj(long l) {
        this.checkNewFormat();
        this.maj = l;
    }

    public void setDeviceMin(long l) {
        this.checkNewFormat();
        this.min = l;
    }

    public void setSize(long l) {
        if (l < 0L || l > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("invalid entry size <" + l + ">");
        }
        this.filesize = l;
    }

    public void setGID(long l) {
        this.gid = l;
    }

    public void setInode(long l) {
        this.inode = l;
    }

    public void setMode(long l) {
        long l2 = l & 0xF000L;
        switch ((int)l2) {
            case 4096: 
            case 8192: 
            case 16384: 
            case 24576: 
            case 32768: 
            case 36864: 
            case 40960: 
            case 49152: {
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown mode. Full: " + Long.toHexString(l) + " Masked: " + Long.toHexString(l2));
            }
        }
        this.mode = l;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void setNumberOfLinks(long l) {
        this.nlink = l;
    }

    public void setRemoteDevice(long l) {
        this.checkOldFormat();
        this.rmin = l;
    }

    public void setRemoteDeviceMaj(long l) {
        this.checkNewFormat();
        this.rmaj = l;
    }

    public void setRemoteDeviceMin(long l) {
        this.checkNewFormat();
        this.rmin = l;
    }

    public void setTime(long l) {
        this.mtime = l;
    }

    public void setUID(long l) {
        this.uid = l;
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.name == null ? 0 : this.name.hashCode());
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        CpioArchiveEntry cpioArchiveEntry = (CpioArchiveEntry)object;
        return this.name == null ? cpioArchiveEntry.name != null : !this.name.equals(cpioArchiveEntry.name);
    }
}

