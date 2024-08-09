/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.comparator.AbstractFileComparator;

public class CompositeFileComparator
extends AbstractFileComparator
implements Serializable {
    private static final long serialVersionUID = -2224170307287243428L;
    private static final Comparator<?>[] NO_COMPARATORS = new Comparator[0];
    private final Comparator<File>[] delegates;

    public CompositeFileComparator(Comparator<File> ... comparatorArray) {
        if (comparatorArray == null) {
            this.delegates = NO_COMPARATORS;
        } else {
            this.delegates = new Comparator[comparatorArray.length];
            System.arraycopy(comparatorArray, 0, this.delegates, 0, comparatorArray.length);
        }
    }

    public CompositeFileComparator(Iterable<Comparator<File>> iterable) {
        if (iterable == null) {
            this.delegates = NO_COMPARATORS;
        } else {
            ArrayList<Comparator<File>> arrayList = new ArrayList<Comparator<File>>();
            for (Comparator<File> comparator : iterable) {
                arrayList.add(comparator);
            }
            this.delegates = arrayList.toArray(new Comparator[arrayList.size()]);
        }
    }

    @Override
    public int compare(File file, File file2) {
        Comparator<File> comparator;
        int n = 0;
        Comparator<File>[] comparatorArray = this.delegates;
        int n2 = comparatorArray.length;
        for (int i = 0; i < n2 && (n = (comparator = comparatorArray[i]).compare(file, file2)) == 0; ++i) {
        }
        return n;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append('{');
        for (int i = 0; i < this.delegates.length; ++i) {
            if (i > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.delegates[i]);
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
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

