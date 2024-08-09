/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.dump;

import java.io.IOException;

public class DumpArchiveException
extends IOException {
    private static final long serialVersionUID = 1L;

    public DumpArchiveException() {
    }

    public DumpArchiveException(String string) {
        super(string);
    }

    public DumpArchiveException(Throwable throwable) {
        this.initCause(throwable);
    }

    public DumpArchiveException(String string, Throwable throwable) {
        super(string);
        this.initCause(throwable);
    }
}

