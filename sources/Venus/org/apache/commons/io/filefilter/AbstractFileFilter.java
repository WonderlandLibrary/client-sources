/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import org.apache.commons.io.filefilter.IOFileFilter;

public abstract class AbstractFileFilter
implements IOFileFilter {
    @Override
    public boolean accept(File file) {
        return this.accept(file.getParentFile(), file.getName());
    }

    @Override
    public boolean accept(File file, String string) {
        return this.accept(new File(file, string));
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

