/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.ObjectStreamException;
import java.io.Serializable;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;

@Plugin(name="PatternMatch", category="Core", printObject=true)
public final class PatternMatch {
    private final String key;
    private final String pattern;

    public PatternMatch(String string, String string2) {
        this.key = string;
        this.pattern = string2;
    }

    public String getKey() {
        return this.key;
    }

    public String getPattern() {
        return this.pattern;
    }

    public String toString() {
        return this.key + '=' + this.pattern;
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.key == null ? 0 : this.key.hashCode());
        n2 = 31 * n2 + (this.pattern == null ? 0 : this.pattern.hashCode());
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        PatternMatch patternMatch = (PatternMatch)object;
        if (this.key == null ? patternMatch.key != null : !this.key.equals(patternMatch.key)) {
            return true;
        }
        return this.pattern == null ? patternMatch.pattern != null : !this.pattern.equals(patternMatch.pattern);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<PatternMatch>,
    Serializable {
        private static final long serialVersionUID = 1L;
        @PluginBuilderAttribute
        private String key;
        @PluginBuilderAttribute
        private String pattern;

        public Builder setKey(String string) {
            this.key = string;
            return this;
        }

        public Builder setPattern(String string) {
            this.pattern = string;
            return this;
        }

        @Override
        public PatternMatch build() {
            return new PatternMatch(this.key, this.pattern);
        }

        protected Object readResolve() throws ObjectStreamException {
            return new PatternMatch(this.key, this.pattern);
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

