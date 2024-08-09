/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.regex.Pattern;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class RegexFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = 4269646126155225062L;
    private final Pattern pattern;

    public RegexFileFilter(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = Pattern.compile(string);
    }

    public RegexFileFilter(String string, IOCase iOCase) {
        if (string == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        int n = 0;
        if (iOCase != null && !iOCase.isCaseSensitive()) {
            n = 2;
        }
        this.pattern = Pattern.compile(string, n);
    }

    public RegexFileFilter(String string, int n) {
        if (string == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = Pattern.compile(string, n);
    }

    public RegexFileFilter(Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = pattern;
    }

    @Override
    public boolean accept(File file, String string) {
        return this.pattern.matcher(string).matches();
    }
}

