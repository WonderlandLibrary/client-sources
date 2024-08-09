/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class DelegateFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = -8723373124984771318L;
    private final FilenameFilter filenameFilter;
    private final FileFilter fileFilter;

    public DelegateFileFilter(FilenameFilter filenameFilter) {
        if (filenameFilter == null) {
            throw new IllegalArgumentException("The FilenameFilter must not be null");
        }
        this.filenameFilter = filenameFilter;
        this.fileFilter = null;
    }

    public DelegateFileFilter(FileFilter fileFilter) {
        if (fileFilter == null) {
            throw new IllegalArgumentException("The FileFilter must not be null");
        }
        this.fileFilter = fileFilter;
        this.filenameFilter = null;
    }

    @Override
    public boolean accept(File file) {
        if (this.fileFilter != null) {
            return this.fileFilter.accept(file);
        }
        return super.accept(file);
    }

    @Override
    public boolean accept(File file, String string) {
        if (this.filenameFilter != null) {
            return this.filenameFilter.accept(file, string);
        }
        return super.accept(file, string);
    }

    @Override
    public String toString() {
        String string = this.fileFilter != null ? this.fileFilter.toString() : this.filenameFilter.toString();
        return super.toString() + "(" + string + ")";
    }
}

