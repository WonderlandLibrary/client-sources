/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

abstract class AbstractFileComparator
implements Comparator<File> {
    AbstractFileComparator() {
    }

    public File[] sort(File ... fileArray) {
        if (fileArray != null) {
            Arrays.sort(fileArray, this);
        }
        return fileArray;
    }

    public List<File> sort(List<File> list) {
        if (list != null) {
            Collections.sort(list, this);
        }
        return list;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}

