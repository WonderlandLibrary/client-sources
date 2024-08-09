/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.serialization;

import java.util.regex.Pattern;
import org.apache.commons.io.serialization.ClassNameMatcher;

final class RegexpClassNameMatcher
implements ClassNameMatcher {
    private final Pattern pattern;

    public RegexpClassNameMatcher(String string) {
        this(Pattern.compile(string));
    }

    public RegexpClassNameMatcher(Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Null pattern");
        }
        this.pattern = pattern;
    }

    @Override
    public boolean matches(String string) {
        return this.pattern.matcher(string).matches();
    }
}

