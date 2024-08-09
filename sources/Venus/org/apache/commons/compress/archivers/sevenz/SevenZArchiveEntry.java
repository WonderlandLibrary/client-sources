/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.sevenz;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZMethodConfiguration;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SevenZArchiveEntry
implements ArchiveEntry {
    private String name;
    private boolean hasStream;
    private boolean isDirectory;
    private boolean isAntiItem;
    private boolean hasCreationDate;
    private boolean hasLastModifiedDate;
    private boolean hasAccessDate;
    private long creationDate;
    private long lastModifiedDate;
    private long accessDate;
    private boolean hasWindowsAttributes;
    private int windowsAttributes;
    private boolean hasCrc;
    private long crc;
    private long compressedCrc;
    private long size;
    private long compressedSize;
    private Iterable<? extends SevenZMethodConfiguration> contentMethods;

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public boolean hasStream() {
        return this.hasStream;
    }

    public void setHasStream(boolean bl) {
        this.hasStream = bl;
    }

    @Override
    public boolean isDirectory() {
        return this.isDirectory;
    }

    public void setDirectory(boolean bl) {
        this.isDirectory = bl;
    }

    public boolean isAntiItem() {
        return this.isAntiItem;
    }

    public void setAntiItem(boolean bl) {
        this.isAntiItem = bl;
    }

    public boolean getHasCreationDate() {
        return this.hasCreationDate;
    }

    public void setHasCreationDate(boolean bl) {
        this.hasCreationDate = bl;
    }

    public Date getCreationDate() {
        if (this.hasCreationDate) {
            return SevenZArchiveEntry.ntfsTimeToJavaTime(this.creationDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    public void setCreationDate(long l) {
        this.creationDate = l;
    }

    public void setCreationDate(Date date) {
        boolean bl = this.hasCreationDate = date != null;
        if (this.hasCreationDate) {
            this.creationDate = SevenZArchiveEntry.javaTimeToNtfsTime(date);
        }
    }

    public boolean getHasLastModifiedDate() {
        return this.hasLastModifiedDate;
    }

    public void setHasLastModifiedDate(boolean bl) {
        this.hasLastModifiedDate = bl;
    }

    @Override
    public Date getLastModifiedDate() {
        if (this.hasLastModifiedDate) {
            return SevenZArchiveEntry.ntfsTimeToJavaTime(this.lastModifiedDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    public void setLastModifiedDate(long l) {
        this.lastModifiedDate = l;
    }

    public void setLastModifiedDate(Date date) {
        boolean bl = this.hasLastModifiedDate = date != null;
        if (this.hasLastModifiedDate) {
            this.lastModifiedDate = SevenZArchiveEntry.javaTimeToNtfsTime(date);
        }
    }

    public boolean getHasAccessDate() {
        return this.hasAccessDate;
    }

    public void setHasAccessDate(boolean bl) {
        this.hasAccessDate = bl;
    }

    public Date getAccessDate() {
        if (this.hasAccessDate) {
            return SevenZArchiveEntry.ntfsTimeToJavaTime(this.accessDate);
        }
        throw new UnsupportedOperationException("The entry doesn't have this timestamp");
    }

    public void setAccessDate(long l) {
        this.accessDate = l;
    }

    public void setAccessDate(Date date) {
        boolean bl = this.hasAccessDate = date != null;
        if (this.hasAccessDate) {
            this.accessDate = SevenZArchiveEntry.javaTimeToNtfsTime(date);
        }
    }

    public boolean getHasWindowsAttributes() {
        return this.hasWindowsAttributes;
    }

    public void setHasWindowsAttributes(boolean bl) {
        this.hasWindowsAttributes = bl;
    }

    public int getWindowsAttributes() {
        return this.windowsAttributes;
    }

    public void setWindowsAttributes(int n) {
        this.windowsAttributes = n;
    }

    public boolean getHasCrc() {
        return this.hasCrc;
    }

    public void setHasCrc(boolean bl) {
        this.hasCrc = bl;
    }

    @Deprecated
    public int getCrc() {
        return (int)this.crc;
    }

    @Deprecated
    public void setCrc(int n) {
        this.crc = n;
    }

    public long getCrcValue() {
        return this.crc;
    }

    public void setCrcValue(long l) {
        this.crc = l;
    }

    @Deprecated
    int getCompressedCrc() {
        return (int)this.compressedCrc;
    }

    @Deprecated
    void setCompressedCrc(int n) {
        this.compressedCrc = n;
    }

    long getCompressedCrcValue() {
        return this.compressedCrc;
    }

    void setCompressedCrcValue(long l) {
        this.compressedCrc = l;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    public void setSize(long l) {
        this.size = l;
    }

    long getCompressedSize() {
        return this.compressedSize;
    }

    void setCompressedSize(long l) {
        this.compressedSize = l;
    }

    public void setContentMethods(Iterable<? extends SevenZMethodConfiguration> iterable) {
        if (iterable != null) {
            LinkedList<SevenZMethodConfiguration> linkedList = new LinkedList<SevenZMethodConfiguration>();
            for (SevenZMethodConfiguration sevenZMethodConfiguration : iterable) {
                linkedList.addLast(sevenZMethodConfiguration);
            }
            this.contentMethods = Collections.unmodifiableList(linkedList);
        } else {
            this.contentMethods = null;
        }
    }

    public Iterable<? extends SevenZMethodConfiguration> getContentMethods() {
        return this.contentMethods;
    }

    public static Date ntfsTimeToJavaTime(long l) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        calendar.set(1601, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        long l2 = calendar.getTimeInMillis() + l / 10000L;
        return new Date(l2);
    }

    public static long javaTimeToNtfsTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        calendar.set(1601, 0, 1, 0, 0, 0);
        calendar.set(14, 0);
        return (date.getTime() - calendar.getTimeInMillis()) * 1000L * 10L;
    }
}

