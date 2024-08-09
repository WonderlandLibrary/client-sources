/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class SuffixFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = -3389157631240246157L;
    private final String[] suffixes;
    private final IOCase caseSensitivity;

    public SuffixFileFilter(String string) {
        this(string, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(String string, IOCase iOCase) {
        if (string == null) {
            throw new IllegalArgumentException("The suffix must not be null");
        }
        this.suffixes = new String[]{string};
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    public SuffixFileFilter(String[] stringArray) {
        this(stringArray, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(String[] stringArray, IOCase iOCase) {
        if (stringArray == null) {
            throw new IllegalArgumentException("The array of suffixes must not be null");
        }
        this.suffixes = new String[stringArray.length];
        System.arraycopy(stringArray, 0, this.suffixes, 0, stringArray.length);
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    public SuffixFileFilter(List<String> list) {
        this(list, IOCase.SENSITIVE);
    }

    public SuffixFileFilter(List<String> list, IOCase iOCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of suffixes must not be null");
        }
        this.suffixes = list.toArray(new String[list.size()]);
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    @Override
    public boolean accept(File file) {
        String string = file.getName();
        for (String string2 : this.suffixes) {
            if (!this.caseSensitivity.checkEndsWith(string, string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean accept(File file, String string) {
        for (String string2 : this.suffixes) {
            if (!this.caseSensitivity.checkEndsWith(string, string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("(");
        if (this.suffixes != null) {
            for (int i = 0; i < this.suffixes.length; ++i) {
                if (i > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(this.suffixes[i]);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

