/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.CommonMatcher;
import com.google.common.base.CommonPattern;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@GwtIncompatible
final class JdkPattern
extends CommonPattern
implements Serializable {
    private final Pattern pattern;
    private static final long serialVersionUID = 0L;

    JdkPattern(Pattern pattern) {
        this.pattern = Preconditions.checkNotNull(pattern);
    }

    @Override
    CommonMatcher matcher(CharSequence charSequence) {
        return new JdkMatcher(this.pattern.matcher(charSequence));
    }

    @Override
    String pattern() {
        return this.pattern.pattern();
    }

    @Override
    int flags() {
        return this.pattern.flags();
    }

    @Override
    public String toString() {
        return this.pattern.toString();
    }

    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JdkPattern)) {
            return true;
        }
        return this.pattern.equals(((JdkPattern)object).pattern);
    }

    private static final class JdkMatcher
    extends CommonMatcher {
        final Matcher matcher;

        JdkMatcher(Matcher matcher) {
            this.matcher = Preconditions.checkNotNull(matcher);
        }

        @Override
        boolean matches() {
            return this.matcher.matches();
        }

        @Override
        boolean find() {
            return this.matcher.find();
        }

        @Override
        boolean find(int n) {
            return this.matcher.find(n);
        }

        @Override
        String replaceAll(String string) {
            return this.matcher.replaceAll(string);
        }

        @Override
        int end() {
            return this.matcher.end();
        }

        @Override
        int start() {
            return this.matcher.start();
        }
    }
}

