/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.dump;

import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;
import org.apache.commons.compress.archivers.dump.DumpArchiveSummary;
import org.apache.commons.compress.archivers.dump.DumpArchiveUtil;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DumpArchiveEntry
implements ArchiveEntry {
    private String name;
    private TYPE type = TYPE.UNKNOWN;
    private int mode;
    private Set<PERMISSION> permissions = Collections.emptySet();
    private long size;
    private long atime;
    private long mtime;
    private int uid;
    private int gid;
    private final DumpArchiveSummary summary = null;
    private final TapeSegmentHeader header = new TapeSegmentHeader();
    private String simpleName;
    private String originalName;
    private int volume;
    private long offset;
    private int ino;
    private int nlink;
    private long ctime;
    private int generation;
    private boolean isDeleted;

    public DumpArchiveEntry() {
    }

    public DumpArchiveEntry(String string, String string2) {
        this.setName(string);
        this.simpleName = string2;
    }

    protected DumpArchiveEntry(String string, String string2, int n, TYPE tYPE) {
        this.setType(tYPE);
        this.setName(string);
        this.simpleName = string2;
        this.ino = n;
        this.offset = 0L;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    protected void setSimpleName(String string) {
        this.simpleName = string;
    }

    public int getIno() {
        return this.header.getIno();
    }

    public int getNlink() {
        return this.nlink;
    }

    public void setNlink(int n) {
        this.nlink = n;
    }

    public Date getCreationTime() {
        return new Date(this.ctime);
    }

    public void setCreationTime(Date date) {
        this.ctime = date.getTime();
    }

    public int getGeneration() {
        return this.generation;
    }

    public void setGeneration(int n) {
        this.generation = n;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(boolean bl) {
        this.isDeleted = bl;
    }

    public long getOffset() {
        return this.offset;
    }

    public void setOffset(long l) {
        this.offset = l;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int n) {
        this.volume = n;
    }

    public DumpArchiveConstants.SEGMENT_TYPE getHeaderType() {
        return this.header.getType();
    }

    public int getHeaderCount() {
        return this.header.getCount();
    }

    public int getHeaderHoles() {
        return this.header.getHoles();
    }

    public boolean isSparseRecord(int n) {
        return (this.header.getCdata(n) & 1) == 0;
    }

    public int hashCode() {
        return this.ino;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object == null || !object.getClass().equals(this.getClass())) {
            return true;
        }
        DumpArchiveEntry dumpArchiveEntry = (DumpArchiveEntry)object;
        if (this.header == null || dumpArchiveEntry.header == null) {
            return true;
        }
        if (this.ino != dumpArchiveEntry.ino) {
            return true;
        }
        return this.summary == null && dumpArchiveEntry.summary != null || this.summary != null && !this.summary.equals(dumpArchiveEntry.summary);
    }

    public String toString() {
        return this.getName();
    }

    static DumpArchiveEntry parse(byte[] byArray) {
        DumpArchiveEntry dumpArchiveEntry = new DumpArchiveEntry();
        TapeSegmentHeader tapeSegmentHeader = dumpArchiveEntry.header;
        TapeSegmentHeader.access$002(tapeSegmentHeader, DumpArchiveConstants.SEGMENT_TYPE.find(DumpArchiveUtil.convert32(byArray, 0)));
        TapeSegmentHeader.access$102(tapeSegmentHeader, DumpArchiveUtil.convert32(byArray, 12));
        dumpArchiveEntry.ino = TapeSegmentHeader.access$202(tapeSegmentHeader, DumpArchiveUtil.convert32(byArray, 20));
        int n = DumpArchiveUtil.convert16(byArray, 32);
        dumpArchiveEntry.setType(TYPE.find(n >> 12 & 0xF));
        dumpArchiveEntry.setMode(n);
        dumpArchiveEntry.nlink = DumpArchiveUtil.convert16(byArray, 34);
        dumpArchiveEntry.setSize(DumpArchiveUtil.convert64(byArray, 40));
        long l = 1000L * (long)DumpArchiveUtil.convert32(byArray, 48) + (long)(DumpArchiveUtil.convert32(byArray, 52) / 1000);
        dumpArchiveEntry.setAccessTime(new Date(l));
        l = 1000L * (long)DumpArchiveUtil.convert32(byArray, 56) + (long)(DumpArchiveUtil.convert32(byArray, 60) / 1000);
        dumpArchiveEntry.setLastModifiedDate(new Date(l));
        dumpArchiveEntry.ctime = l = 1000L * (long)DumpArchiveUtil.convert32(byArray, 64) + (long)(DumpArchiveUtil.convert32(byArray, 68) / 1000);
        dumpArchiveEntry.generation = DumpArchiveUtil.convert32(byArray, 140);
        dumpArchiveEntry.setUserId(DumpArchiveUtil.convert32(byArray, 144));
        dumpArchiveEntry.setGroupId(DumpArchiveUtil.convert32(byArray, 148));
        TapeSegmentHeader.access$302(tapeSegmentHeader, DumpArchiveUtil.convert32(byArray, 160));
        TapeSegmentHeader.access$402(tapeSegmentHeader, 0);
        for (int i = 0; i < 512 && i < TapeSegmentHeader.access$300(tapeSegmentHeader); ++i) {
            if (byArray[164 + i] != 0) continue;
            TapeSegmentHeader.access$408(tapeSegmentHeader);
        }
        System.arraycopy(byArray, 164, TapeSegmentHeader.access$500(tapeSegmentHeader), 0, 512);
        dumpArchiveEntry.volume = tapeSegmentHeader.getVolume();
        return dumpArchiveEntry;
    }

    void update(byte[] byArray) {
        TapeSegmentHeader.access$102(this.header, DumpArchiveUtil.convert32(byArray, 16));
        TapeSegmentHeader.access$302(this.header, DumpArchiveUtil.convert32(byArray, 160));
        TapeSegmentHeader.access$402(this.header, 0);
        for (int i = 0; i < 512 && i < TapeSegmentHeader.access$300(this.header); ++i) {
            if (byArray[164 + i] != 0) continue;
            TapeSegmentHeader.access$408(this.header);
        }
        System.arraycopy(byArray, 164, TapeSegmentHeader.access$500(this.header), 0, 512);
    }

    @Override
    public String getName() {
        return this.name;
    }

    String getOriginalName() {
        return this.originalName;
    }

    public final void setName(String string) {
        this.originalName = string;
        if (string != null) {
            if (this.isDirectory() && !string.endsWith("/")) {
                string = string + "/";
            }
            if (string.startsWith("./")) {
                string = string.substring(2);
            }
        }
        this.name = string;
    }

    @Override
    public Date getLastModifiedDate() {
        return new Date(this.mtime);
    }

    @Override
    public boolean isDirectory() {
        return this.type == TYPE.DIRECTORY;
    }

    public boolean isFile() {
        return this.type == TYPE.FILE;
    }

    public boolean isSocket() {
        return this.type == TYPE.SOCKET;
    }

    public boolean isChrDev() {
        return this.type == TYPE.CHRDEV;
    }

    public boolean isBlkDev() {
        return this.type == TYPE.BLKDEV;
    }

    public boolean isFifo() {
        return this.type == TYPE.FIFO;
    }

    public TYPE getType() {
        return this.type;
    }

    public void setType(TYPE tYPE) {
        this.type = tYPE;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int n) {
        this.mode = n & 0xFFF;
        this.permissions = PERMISSION.find(n);
    }

    public Set<PERMISSION> getPermissions() {
        return this.permissions;
    }

    @Override
    public long getSize() {
        return this.isDirectory() ? -1L : this.size;
    }

    long getEntrySize() {
        return this.size;
    }

    public void setSize(long l) {
        this.size = l;
    }

    public void setLastModifiedDate(Date date) {
        this.mtime = date.getTime();
    }

    public Date getAccessTime() {
        return new Date(this.atime);
    }

    public void setAccessTime(Date date) {
        this.atime = date.getTime();
    }

    public int getUserId() {
        return this.uid;
    }

    public void setUserId(int n) {
        this.uid = n;
    }

    public int getGroupId() {
        return this.gid;
    }

    public void setGroupId(int n) {
        this.gid = n;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum PERMISSION {
        SETUID(2048),
        SETGUI(1024),
        STICKY(512),
        USER_READ(256),
        USER_WRITE(128),
        USER_EXEC(64),
        GROUP_READ(32),
        GROUP_WRITE(16),
        GROUP_EXEC(8),
        WORLD_READ(4),
        WORLD_WRITE(2),
        WORLD_EXEC(1);

        private int code;

        private PERMISSION(int n2) {
            this.code = n2;
        }

        public static Set<PERMISSION> find(int n) {
            HashSet<PERMISSION> hashSet = new HashSet<PERMISSION>();
            for (PERMISSION pERMISSION : PERMISSION.values()) {
                if ((n & pERMISSION.code) != pERMISSION.code) continue;
                hashSet.add(pERMISSION);
            }
            if (hashSet.isEmpty()) {
                return Collections.emptySet();
            }
            return EnumSet.copyOf(hashSet);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum TYPE {
        WHITEOUT(14),
        SOCKET(12),
        LINK(10),
        FILE(8),
        BLKDEV(6),
        DIRECTORY(4),
        CHRDEV(2),
        FIFO(1),
        UNKNOWN(15);

        private int code;

        private TYPE(int n2) {
            this.code = n2;
        }

        public static TYPE find(int n) {
            TYPE tYPE = UNKNOWN;
            for (TYPE tYPE2 : TYPE.values()) {
                if (n != tYPE2.code) continue;
                tYPE = tYPE2;
            }
            return tYPE;
        }
    }

    static class TapeSegmentHeader {
        private DumpArchiveConstants.SEGMENT_TYPE type;
        private int volume;
        private int ino;
        private int count;
        private int holes;
        private final byte[] cdata = new byte[512];

        TapeSegmentHeader() {
        }

        public DumpArchiveConstants.SEGMENT_TYPE getType() {
            return this.type;
        }

        public int getVolume() {
            return this.volume;
        }

        public int getIno() {
            return this.ino;
        }

        void setIno(int n) {
            this.ino = n;
        }

        public int getCount() {
            return this.count;
        }

        public int getHoles() {
            return this.holes;
        }

        public int getCdata(int n) {
            return this.cdata[n];
        }

        static DumpArchiveConstants.SEGMENT_TYPE access$002(TapeSegmentHeader tapeSegmentHeader, DumpArchiveConstants.SEGMENT_TYPE sEGMENT_TYPE) {
            tapeSegmentHeader.type = sEGMENT_TYPE;
            return tapeSegmentHeader.type;
        }

        static int access$102(TapeSegmentHeader tapeSegmentHeader, int n) {
            tapeSegmentHeader.volume = n;
            return tapeSegmentHeader.volume;
        }

        static int access$202(TapeSegmentHeader tapeSegmentHeader, int n) {
            tapeSegmentHeader.ino = n;
            return tapeSegmentHeader.ino;
        }

        static int access$302(TapeSegmentHeader tapeSegmentHeader, int n) {
            tapeSegmentHeader.count = n;
            return tapeSegmentHeader.count;
        }

        static int access$402(TapeSegmentHeader tapeSegmentHeader, int n) {
            tapeSegmentHeader.holes = n;
            return tapeSegmentHeader.holes;
        }

        static int access$300(TapeSegmentHeader tapeSegmentHeader) {
            return tapeSegmentHeader.count;
        }

        static int access$408(TapeSegmentHeader tapeSegmentHeader) {
            return tapeSegmentHeader.holes++;
        }

        static byte[] access$500(TapeSegmentHeader tapeSegmentHeader) {
            return tapeSegmentHeader.cdata;
        }
    }
}

