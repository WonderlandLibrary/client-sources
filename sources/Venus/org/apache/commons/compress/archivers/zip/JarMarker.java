/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipShort;

public final class JarMarker
implements ZipExtraField {
    private static final ZipShort ID = new ZipShort(51966);
    private static final ZipShort NULL = new ZipShort(0);
    private static final byte[] NO_BYTES = new byte[0];
    private static final JarMarker DEFAULT = new JarMarker();

    public static JarMarker getInstance() {
        return DEFAULT;
    }

    public ZipShort getHeaderId() {
        return ID;
    }

    public ZipShort getLocalFileDataLength() {
        return NULL;
    }

    public ZipShort getCentralDirectoryLength() {
        return NULL;
    }

    public byte[] getLocalFileDataData() {
        return NO_BYTES;
    }

    public byte[] getCentralDirectoryData() {
        return NO_BYTES;
    }

    public void parseFromLocalFileData(byte[] byArray, int n, int n2) throws ZipException {
        if (n2 != 0) {
            throw new ZipException("JarMarker doesn't expect any data");
        }
    }

    public void parseFromCentralDirectoryData(byte[] byArray, int n, int n2) throws ZipException {
        this.parseFromLocalFileData(byArray, n, n2);
    }
}

