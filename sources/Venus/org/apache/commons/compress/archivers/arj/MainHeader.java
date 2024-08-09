/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.arj;

import java.util.Arrays;

class MainHeader {
    int archiverVersionNumber;
    int minVersionToExtract;
    int hostOS;
    int arjFlags;
    int securityVersion;
    int fileType;
    int reserved;
    int dateTimeCreated;
    int dateTimeModified;
    long archiveSize;
    int securityEnvelopeFilePosition;
    int fileSpecPosition;
    int securityEnvelopeLength;
    int encryptionVersion;
    int lastChapter;
    int arjProtectionFactor;
    int arjFlags2;
    String name;
    String comment;
    byte[] extendedHeaderBytes = null;

    MainHeader() {
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MainHeader [archiverVersionNumber=");
        stringBuilder.append(this.archiverVersionNumber);
        stringBuilder.append(", minVersionToExtract=");
        stringBuilder.append(this.minVersionToExtract);
        stringBuilder.append(", hostOS=");
        stringBuilder.append(this.hostOS);
        stringBuilder.append(", arjFlags=");
        stringBuilder.append(this.arjFlags);
        stringBuilder.append(", securityVersion=");
        stringBuilder.append(this.securityVersion);
        stringBuilder.append(", fileType=");
        stringBuilder.append(this.fileType);
        stringBuilder.append(", reserved=");
        stringBuilder.append(this.reserved);
        stringBuilder.append(", dateTimeCreated=");
        stringBuilder.append(this.dateTimeCreated);
        stringBuilder.append(", dateTimeModified=");
        stringBuilder.append(this.dateTimeModified);
        stringBuilder.append(", archiveSize=");
        stringBuilder.append(this.archiveSize);
        stringBuilder.append(", securityEnvelopeFilePosition=");
        stringBuilder.append(this.securityEnvelopeFilePosition);
        stringBuilder.append(", fileSpecPosition=");
        stringBuilder.append(this.fileSpecPosition);
        stringBuilder.append(", securityEnvelopeLength=");
        stringBuilder.append(this.securityEnvelopeLength);
        stringBuilder.append(", encryptionVersion=");
        stringBuilder.append(this.encryptionVersion);
        stringBuilder.append(", lastChapter=");
        stringBuilder.append(this.lastChapter);
        stringBuilder.append(", arjProtectionFactor=");
        stringBuilder.append(this.arjProtectionFactor);
        stringBuilder.append(", arjFlags2=");
        stringBuilder.append(this.arjFlags2);
        stringBuilder.append(", name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", comment=");
        stringBuilder.append(this.comment);
        stringBuilder.append(", extendedHeaderBytes=");
        stringBuilder.append(Arrays.toString(this.extendedHeaderBytes));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    static class Flags {
        static final int GARBLED = 1;
        static final int OLD_SECURED_NEW_ANSI_PAGE = 2;
        static final int VOLUME = 4;
        static final int ARJPROT = 8;
        static final int PATHSYM = 16;
        static final int BACKUP = 32;
        static final int SECURED = 64;
        static final int ALTNAME = 128;

        Flags() {
        }
    }
}

