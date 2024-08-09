/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class SizeFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = 7388077430788600069L;
    private final long size;
    private final boolean acceptLarger;

    public SizeFileFilter(long l) {
        this(l, true);
    }

    public SizeFileFilter(long l, boolean bl) {
        if (l < 0L) {
            throw new IllegalArgumentException("The size must be non-negative");
        }
        this.size = l;
        this.acceptLarger = bl;
    }

    @Override
    public boolean accept(File file) {
        boolean bl;
        boolean bl2 = bl = file.length() < this.size;
        return this.acceptLarger ? !bl : bl;
    }

    @Override
    public String toString() {
        String string = this.acceptLarger ? ">=" : "<";
        return super.toString() + "(" + string + this.size + ")";
    }
}

