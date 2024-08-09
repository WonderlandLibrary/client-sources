/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.gzip;

public class GzipParameters {
    private int compressionLevel = -1;
    private long modificationTime;
    private String filename;
    private String comment;
    private int operatingSystem = 255;

    public int getCompressionLevel() {
        return this.compressionLevel;
    }

    public void setCompressionLevel(int n) {
        if (n < -1 || n > 9) {
            throw new IllegalArgumentException("Invalid gzip compression level: " + n);
        }
        this.compressionLevel = n;
    }

    public long getModificationTime() {
        return this.modificationTime;
    }

    public void setModificationTime(long l) {
        this.modificationTime = l;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String string) {
        this.filename = string;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    public int getOperatingSystem() {
        return this.operatingSystem;
    }

    public void setOperatingSystem(int n) {
        this.operatingSystem = n;
    }
}

