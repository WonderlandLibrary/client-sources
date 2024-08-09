/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.tar;

import java.io.IOException;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.archivers.tar.TarUtils;

public class TarArchiveSparseEntry
implements TarConstants {
    private final boolean isExtended;

    public TarArchiveSparseEntry(byte[] byArray) throws IOException {
        int n = 0;
        this.isExtended = TarUtils.parseBoolean(byArray, n += 504);
    }

    public boolean isExtended() {
        return this.isExtended;
    }
}

