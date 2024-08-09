/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.arj;

import java.util.Arrays;

class LocalFileHeader {
    int archiverVersionNumber;
    int minVersionToExtract;
    int hostOS;
    int arjFlags;
    int method;
    int fileType;
    int reserved;
    int dateTimeModified;
    long compressedSize;
    long originalSize;
    long originalCrc32;
    int fileSpecPosition;
    int fileAccessMode;
    int firstChapter;
    int lastChapter;
    int extendedFilePosition;
    int dateTimeAccessed;
    int dateTimeCreated;
    int originalSizeEvenForVolumes;
    String name;
    String comment;
    byte[][] extendedHeaders = null;

    LocalFileHeader() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LocalFileHeader [archiverVersionNumber=");
        stringBuilder.append(this.archiverVersionNumber);
        stringBuilder.append(", minVersionToExtract=");
        stringBuilder.append(this.minVersionToExtract);
        stringBuilder.append(", hostOS=");
        stringBuilder.append(this.hostOS);
        stringBuilder.append(", arjFlags=");
        stringBuilder.append(this.arjFlags);
        stringBuilder.append(", method=");
        stringBuilder.append(this.method);
        stringBuilder.append(", fileType=");
        stringBuilder.append(this.fileType);
        stringBuilder.append(", reserved=");
        stringBuilder.append(this.reserved);
        stringBuilder.append(", dateTimeModified=");
        stringBuilder.append(this.dateTimeModified);
        stringBuilder.append(", compressedSize=");
        stringBuilder.append(this.compressedSize);
        stringBuilder.append(", originalSize=");
        stringBuilder.append(this.originalSize);
        stringBuilder.append(", originalCrc32=");
        stringBuilder.append(this.originalCrc32);
        stringBuilder.append(", fileSpecPosition=");
        stringBuilder.append(this.fileSpecPosition);
        stringBuilder.append(", fileAccessMode=");
        stringBuilder.append(this.fileAccessMode);
        stringBuilder.append(", firstChapter=");
        stringBuilder.append(this.firstChapter);
        stringBuilder.append(", lastChapter=");
        stringBuilder.append(this.lastChapter);
        stringBuilder.append(", extendedFilePosition=");
        stringBuilder.append(this.extendedFilePosition);
        stringBuilder.append(", dateTimeAccessed=");
        stringBuilder.append(this.dateTimeAccessed);
        stringBuilder.append(", dateTimeCreated=");
        stringBuilder.append(this.dateTimeCreated);
        stringBuilder.append(", originalSizeEvenForVolumes=");
        stringBuilder.append(this.originalSizeEvenForVolumes);
        stringBuilder.append(", name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", comment=");
        stringBuilder.append(this.comment);
        stringBuilder.append(", extendedHeaders=");
        stringBuilder.append(Arrays.toString((Object[])this.extendedHeaders));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static class Methods {
        static final int STORED = 0;
        static final int COMPRESSED_MOST = 1;
        static final int COMPRESSED_FASTEST = 4;
        static final int NO_DATA_NO_CRC = 8;
        static final int NO_DATA = 9;

        Methods() {
        }
    }

    static class FileTypes {
        static final int BINARY = 0;
        static final int SEVEN_BIT_TEXT = 1;
        static final int DIRECTORY = 3;
        static final int VOLUME_LABEL = 4;
        static final int CHAPTER_LABEL = 5;

        FileTypes() {
        }
    }

    static class Flags {
        static final int GARBLED = 1;
        static final int VOLUME = 4;
        static final int EXTFILE = 8;
        static final int PATHSYM = 16;
        static final int BACKUP = 32;

        Flags() {
        }
    }
}

