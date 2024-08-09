/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.serialization;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.serialization.ClassNameMatcher;

final class WildcardClassNameMatcher
implements ClassNameMatcher {
    private final String pattern;

    public WildcardClassNameMatcher(String string) {
        this.pattern = string;
    }

    @Override
    public boolean matches(String string) {
        return FilenameUtils.wildcardMatch(string, this.pattern);
    }
}

