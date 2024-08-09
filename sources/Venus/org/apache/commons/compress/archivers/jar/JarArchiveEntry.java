/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.jar;

import java.security.cert.Certificate;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

public class JarArchiveEntry
extends ZipArchiveEntry {
    private final Attributes manifestAttributes = null;
    private final Certificate[] certificates = null;

    public JarArchiveEntry(ZipEntry zipEntry) throws ZipException {
        super(zipEntry);
    }

    public JarArchiveEntry(String string) {
        super(string);
    }

    public JarArchiveEntry(ZipArchiveEntry zipArchiveEntry) throws ZipException {
        super(zipArchiveEntry);
    }

    public JarArchiveEntry(JarEntry jarEntry) throws ZipException {
        super(jarEntry);
    }

    @Deprecated
    public Attributes getManifestAttributes() {
        return this.manifestAttributes;
    }

    @Deprecated
    public Certificate[] getCertificates() {
        if (this.certificates != null) {
            Certificate[] certificateArray = new Certificate[this.certificates.length];
            System.arraycopy(this.certificates, 0, certificateArray, 0, certificateArray.length);
            return certificateArray;
        }
        return null;
    }
}

