/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.AbstractFileComparator;
import org.apache.commons.io.comparator.ReverseComparator;

public class SizeFileComparator
extends AbstractFileComparator
implements Serializable {
    private static final long serialVersionUID = -1201561106411416190L;
    public static final Comparator<File> SIZE_COMPARATOR = new SizeFileComparator();
    public static final Comparator<File> SIZE_REVERSE = new ReverseComparator(SIZE_COMPARATOR);
    public static final Comparator<File> SIZE_SUMDIR_COMPARATOR = new SizeFileComparator(true);
    public static final Comparator<File> SIZE_SUMDIR_REVERSE = new ReverseComparator(SIZE_SUMDIR_COMPARATOR);
    private final boolean sumDirectoryContents;

    public SizeFileComparator() {
        this.sumDirectoryContents = false;
    }

    public SizeFileComparator(boolean bl) {
        this.sumDirectoryContents = bl;
    }

    @Override
    public int compare(File file, File file2) {
        long l = 0L;
        l = file.isDirectory() ? (this.sumDirectoryContents && file.exists() ? FileUtils.sizeOfDirectory(file) : 0L) : file.length();
        long l2 = 0L;
        l2 = file2.isDirectory() ? (this.sumDirectoryContents && file2.exists() ? FileUtils.sizeOfDirectory(file2) : 0L) : file2.length();
        long l3 = l - l2;
        if (l3 < 0L) {
            return 1;
        }
        if (l3 > 0L) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return super.toString() + "[sumDirectoryContents=" + this.sumDirectoryContents + "]";
    }

    public List sort(List list) {
        return super.sort(list);
    }

    @Override
    public File[] sort(File[] fileArray) {
        return super.sort(fileArray);
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((File)object, (File)object2);
    }
}

