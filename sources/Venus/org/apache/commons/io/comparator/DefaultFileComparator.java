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

public class DefaultFileComparator
extends AbstractFileComparator
implements Serializable {
    private static final long serialVersionUID = 3260141861365313518L;
    public static final Comparator<File> DEFAULT_COMPARATOR = new DefaultFileComparator();
    public static final Comparator<File> DEFAULT_REVERSE = new ReverseComparator(DEFAULT_COMPARATOR);

    @Override
    public int compare(File file, File file2) {
        return file.compareTo(file2);
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

