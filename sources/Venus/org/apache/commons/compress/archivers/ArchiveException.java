/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers;

public class ArchiveException
extends Exception {
    private static final long serialVersionUID = 2772690708123267100L;

    public ArchiveException(String string) {
        super(string);
    }

    public ArchiveException(String string, Exception exception) {
        super(string);
        this.initCause(exception);
    }
}

