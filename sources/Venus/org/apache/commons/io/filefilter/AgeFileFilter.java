/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class AgeFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = -2132740084016138541L;
    private final long cutoff;
    private final boolean acceptOlder;

    public AgeFileFilter(long l) {
        this(l, true);
    }

    public AgeFileFilter(long l, boolean bl) {
        this.acceptOlder = bl;
        this.cutoff = l;
    }

    public AgeFileFilter(Date date) {
        this(date, true);
    }

    public AgeFileFilter(Date date, boolean bl) {
        this(date.getTime(), bl);
    }

    public AgeFileFilter(File file) {
        this(file, true);
    }

    public AgeFileFilter(File file, boolean bl) {
        this(file.lastModified(), bl);
    }

    @Override
    public boolean accept(File file) {
        boolean bl = FileUtils.isFileNewer(file, this.cutoff);
        return this.acceptOlder ? !bl : bl;
    }

    @Override
    public String toString() {
        String string = this.acceptOlder ? "<=" : ">";
        return super.toString() + "(" + string + this.cutoff + ")";
    }
}

