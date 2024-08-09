/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class WildcardFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = -7426486598995782105L;
    private final String[] wildcards;
    private final IOCase caseSensitivity;

    public WildcardFileFilter(String string) {
        this(string, IOCase.SENSITIVE);
    }

    public WildcardFileFilter(String string, IOCase iOCase) {
        if (string == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.wildcards = new String[]{string};
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    public WildcardFileFilter(String[] stringArray) {
        this(stringArray, IOCase.SENSITIVE);
    }

    public WildcardFileFilter(String[] stringArray, IOCase iOCase) {
        if (stringArray == null) {
            throw new IllegalArgumentException("The wildcard array must not be null");
        }
        this.wildcards = new String[stringArray.length];
        System.arraycopy(stringArray, 0, this.wildcards, 0, stringArray.length);
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    public WildcardFileFilter(List<String> list) {
        this(list, IOCase.SENSITIVE);
    }

    public WildcardFileFilter(List<String> list, IOCase iOCase) {
        if (list == null) {
            throw new IllegalArgumentException("The wildcard list must not be null");
        }
        this.wildcards = list.toArray(new String[list.size()]);
        this.caseSensitivity = iOCase == null ? IOCase.SENSITIVE : iOCase;
    }

    @Override
    public boolean accept(File file, String string) {
        for (String string2 : this.wildcards) {
            if (!FilenameUtils.wildcardMatch(string, string2, this.caseSensitivity)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean accept(File file) {
        String string = file.getName();
        for (String string2 : this.wildcards) {
            if (!FilenameUtils.wildcardMatch(string, string2, this.caseSensitivity)) continue;
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("(");
        if (this.wildcards != null) {
            for (int i = 0; i < this.wildcards.length; ++i) {
                if (i > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(this.wildcards[i]);
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

