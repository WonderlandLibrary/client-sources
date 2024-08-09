/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;

@Deprecated
public class WildcardFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = -5037645902506953517L;
    private final String[] wildcards;

    public WildcardFilter(String string) {
        if (string == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.wildcards = new String[]{string};
    }

    public WildcardFilter(String[] stringArray) {
        if (stringArray == null) {
            throw new IllegalArgumentException("The wildcard array must not be null");
        }
        this.wildcards = new String[stringArray.length];
        System.arraycopy(stringArray, 0, this.wildcards, 0, stringArray.length);
    }

    public WildcardFilter(List<String> list) {
        if (list == null) {
            throw new IllegalArgumentException("The wildcard list must not be null");
        }
        this.wildcards = list.toArray(new String[list.size()]);
    }

    @Override
    public boolean accept(File file, String string) {
        if (file != null && new File(file, string).isDirectory()) {
            return true;
        }
        for (String string2 : this.wildcards) {
            if (!FilenameUtils.wildcardMatch(string, string2)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        for (String string : this.wildcards) {
            if (!FilenameUtils.wildcardMatch(file.getName(), string)) continue;
            return false;
        }
        return true;
    }
}

