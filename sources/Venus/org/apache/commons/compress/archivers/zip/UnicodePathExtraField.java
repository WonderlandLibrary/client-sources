/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.AbstractUnicodeExtraField;
import org.apache.commons.compress.archivers.zip.ZipShort;

public class UnicodePathExtraField
extends AbstractUnicodeExtraField {
    public static final ZipShort UPATH_ID = new ZipShort(28789);

    public UnicodePathExtraField() {
    }

    public UnicodePathExtraField(String string, byte[] byArray, int n, int n2) {
        super(string, byArray, n, n2);
    }

    public UnicodePathExtraField(String string, byte[] byArray) {
        super(string, byArray);
    }

    public ZipShort getHeaderId() {
        return UPATH_ID;
    }
}

