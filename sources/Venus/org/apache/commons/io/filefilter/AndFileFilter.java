/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.ConditionalFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class AndFileFilter
extends AbstractFileFilter
implements ConditionalFileFilter,
Serializable {
    private static final long serialVersionUID = 7215974688563965257L;
    private final List<IOFileFilter> fileFilters;

    public AndFileFilter() {
        this.fileFilters = new ArrayList<IOFileFilter>();
    }

    public AndFileFilter(List<IOFileFilter> list) {
        this.fileFilters = list == null ? new ArrayList<IOFileFilter>() : new ArrayList<IOFileFilter>(list);
    }

    public AndFileFilter(IOFileFilter iOFileFilter, IOFileFilter iOFileFilter2) {
        if (iOFileFilter == null || iOFileFilter2 == null) {
            throw new IllegalArgumentException("The filters must not be null");
        }
        this.fileFilters = new ArrayList<IOFileFilter>(2);
        this.addFileFilter(iOFileFilter);
        this.addFileFilter(iOFileFilter2);
    }

    @Override
    public void addFileFilter(IOFileFilter iOFileFilter) {
        this.fileFilters.add(iOFileFilter);
    }

    @Override
    public List<IOFileFilter> getFileFilters() {
        return Collections.unmodifiableList(this.fileFilters);
    }

    @Override
    public boolean removeFileFilter(IOFileFilter iOFileFilter) {
        return this.fileFilters.remove(iOFileFilter);
    }

    @Override
    public void setFileFilters(List<IOFileFilter> list) {
        this.fileFilters.clear();
        this.fileFilters.addAll(list);
    }

    @Override
    public boolean accept(File file) {
        if (this.fileFilters.isEmpty()) {
            return true;
        }
        for (IOFileFilter iOFileFilter : this.fileFilters) {
            if (iOFileFilter.accept(file)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean accept(File file, String string) {
        if (this.fileFilters.isEmpty()) {
            return true;
        }
        for (IOFileFilter iOFileFilter : this.fileFilters) {
            if (iOFileFilter.accept(file, string)) continue;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("(");
        if (this.fileFilters != null) {
            for (int i = 0; i < this.fileFilters.size(); ++i) {
                IOFileFilter iOFileFilter;
                if (i > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append((iOFileFilter = this.fileFilters.get(i)) == null ? "null" : iOFileFilter.toString());
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

