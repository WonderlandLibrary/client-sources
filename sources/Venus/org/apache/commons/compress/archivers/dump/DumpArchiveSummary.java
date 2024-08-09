/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.dump;

import java.io.IOException;
import java.util.Date;
import org.apache.commons.compress.archivers.dump.DumpArchiveUtil;
import org.apache.commons.compress.archivers.zip.ZipEncoding;

public class DumpArchiveSummary {
    private long dumpDate;
    private long previousDumpDate;
    private int volume;
    private String label;
    private int level;
    private String filesys;
    private String devname;
    private String hostname;
    private int flags;
    private int firstrec;
    private int ntrec;

    DumpArchiveSummary(byte[] byArray, ZipEncoding zipEncoding) throws IOException {
        this.dumpDate = 1000L * (long)DumpArchiveUtil.convert32(byArray, 4);
        this.previousDumpDate = 1000L * (long)DumpArchiveUtil.convert32(byArray, 8);
        this.volume = DumpArchiveUtil.convert32(byArray, 12);
        this.label = DumpArchiveUtil.decode(zipEncoding, byArray, 676, 16).trim();
        this.level = DumpArchiveUtil.convert32(byArray, 692);
        this.filesys = DumpArchiveUtil.decode(zipEncoding, byArray, 696, 64).trim();
        this.devname = DumpArchiveUtil.decode(zipEncoding, byArray, 760, 64).trim();
        this.hostname = DumpArchiveUtil.decode(zipEncoding, byArray, 824, 64).trim();
        this.flags = DumpArchiveUtil.convert32(byArray, 888);
        this.firstrec = DumpArchiveUtil.convert32(byArray, 892);
        this.ntrec = DumpArchiveUtil.convert32(byArray, 896);
    }

    public Date getDumpDate() {
        return new Date(this.dumpDate);
    }

    public void setDumpDate(Date date) {
        this.dumpDate = date.getTime();
    }

    public Date getPreviousDumpDate() {
        return new Date(this.previousDumpDate);
    }

    public void setPreviousDumpDate(Date date) {
        this.previousDumpDate = date.getTime();
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int n) {
        this.volume = n;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int n) {
        this.level = n;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String string) {
        this.label = string;
    }

    public String getFilesystem() {
        return this.filesys;
    }

    public void setFilesystem(String string) {
        this.filesys = string;
    }

    public String getDevname() {
        return this.devname;
    }

    public void setDevname(String string) {
        this.devname = string;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String string) {
        this.hostname = string;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int n) {
        this.flags = n;
    }

    public int getFirstRecord() {
        return this.firstrec;
    }

    public void setFirstRecord(int n) {
        this.firstrec = n;
    }

    public int getNTRec() {
        return this.ntrec;
    }

    public void setNTRec(int n) {
        this.ntrec = n;
    }

    public boolean isNewHeader() {
        return (this.flags & 1) == 1;
    }

    public boolean isNewInode() {
        return (this.flags & 2) == 2;
    }

    public boolean isCompressed() {
        return (this.flags & 0x80) == 128;
    }

    public boolean isMetaDataOnly() {
        return (this.flags & 0x100) == 256;
    }

    public boolean isExtendedAttributes() {
        return (this.flags & 0x8000) == 32768;
    }

    public int hashCode() {
        int n = 17;
        if (this.label != null) {
            n = this.label.hashCode();
        }
        n = (int)((long)n + 31L * this.dumpDate);
        if (this.hostname != null) {
            n = 31 * this.hostname.hashCode() + 17;
        }
        if (this.devname != null) {
            n = 31 * this.devname.hashCode() + 17;
        }
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || !object.getClass().equals(this.getClass())) {
            return true;
        }
        DumpArchiveSummary dumpArchiveSummary = (DumpArchiveSummary)object;
        if (this.dumpDate != dumpArchiveSummary.dumpDate) {
            return true;
        }
        if (this.getHostname() == null || !this.getHostname().equals(dumpArchiveSummary.getHostname())) {
            return true;
        }
        return this.getDevname() == null || !this.getDevname().equals(dumpArchiveSummary.getDevname());
    }
}

