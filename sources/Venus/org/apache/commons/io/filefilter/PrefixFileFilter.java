/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class PrefixFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = 8533897440809599867L;
    private final String[] prefixes;
    private final IOCase caseSensitivity;

    public PrefixFileFilter(String string) {
        this(string, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(String string, IOCase iOCase) {
        if (string == null) {
            throw new IllegalArgumentException("The prefix must not be null");
        }
        this.prefixes = new String[]{string};
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    public PrefixFileFilter(String[] stringArray) {
        this(stringArray, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(String[] stringArray, IOCase iOCase) {
        if (stringArray == null) {
            throw new IllegalArgumentException("The array of prefixes must not be null");
        }
        this.prefixes = new String[stringArray.length];
        System.arraycopy(stringArray, 0, this.prefixes, 0, stringArray.length);
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    public PrefixFileFilter(List<String> list) {
        this(list, IOCase.SENSITIVE);
    }

    public PrefixFileFilter(List<String> list, IOCase iOCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of prefixes must not be null");
        }
        this.prefixes = list.toArray(new String[list.size()]);
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    @Override
    public boolean accept(File file) {
        String string = file.getName();
        for (String string2 : this.prefixes) {
            if (!this.caseSensitivity.checkStartsWith(string, string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean accept(File file, String string) {
        for (String string2 : this.prefixes) {
            if (!this.caseSensitivity.checkStartsWith(string, string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("(");
        if (this.prefixes != null) {
            for (int i = 0; i < this.prefixes.length; ++i) {
                if (i > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(this.prefixes[i]);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

