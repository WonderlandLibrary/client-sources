/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class NameFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = 176844364689077340L;
    private final String[] names;
    private final IOCase caseSensitivity;

    public NameFileFilter(String string) {
        this(string, null);
    }

    public NameFileFilter(String string, IOCase iOCase) {
        if (string == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.names = new String[]{string};
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    public NameFileFilter(String[] stringArray) {
        this(stringArray, null);
    }

    public NameFileFilter(String[] stringArray, IOCase iOCase) {
        if (stringArray == null) {
            throw new IllegalArgumentException("The array of names must not be null");
        }
        this.names = new String[stringArray.length];
        System.arraycopy(stringArray, 0, this.names, 0, stringArray.length);
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    public NameFileFilter(List<String> list) {
        this(list, null);
    }

    public NameFileFilter(List<String> list, IOCase iOCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of names must not be null");
        }
        this.names = list.toArray(new String[list.size()]);
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    @Override
    public boolean accept(File file) {
        String string = file.getName();
        for (String string2 : this.names) {
            if (!this.caseSensitivity.checkEquals(string, string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean accept(File file, String string) {
        for (String string2 : this.names) {
            if (!this.caseSensitivity.checkEquals(string, string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("(");
        if (this.names != null) {
            for (int i = 0; i < this.names.length; ++i) {
                if (i > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(this.names[i]);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

