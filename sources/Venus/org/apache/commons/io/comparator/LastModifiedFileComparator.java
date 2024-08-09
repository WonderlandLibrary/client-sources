/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.comparator.AbstractFileComparator;
import org.apache.commons.io.comparator.ReverseComparator;

public class LastModifiedFileComparator
extends AbstractFileComparator
implements Serializable {
    private static final long serialVersionUID = 7372168004395734046L;
    public static final Comparator<File> LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
    public static final Comparator<File> LASTMODIFIED_REVERSE = new ReverseComparator(LASTMODIFIED_COMPARATOR);

    @Override
    public int compare(File file, File file2) {
        long l = file.lastModified() - file2.lastModified();
        if (l < 0L) {
            return 1;
        }
        if (l > 0L) {
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return super.toString();
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

