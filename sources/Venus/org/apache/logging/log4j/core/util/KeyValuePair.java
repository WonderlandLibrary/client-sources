/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;

@Plugin(name="KeyValuePair", category="Core", printObject=true)
public final class KeyValuePair {
    private final String key;
    private final String value;

    public KeyValuePair(String string, String string2) {
        this.key = string;
        this.value = string2;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.key + '=' + this.value;
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.key == null ? 0 : this.key.hashCode());
        n2 = 31 * n2 + (this.value == null ? 0 : this.value.hashCode());
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
        KeyValuePair keyValuePair = (KeyValuePair)object;
        if (this.key == null ? keyValuePair.key != null : !this.key.equals(keyValuePair.key)) {
            return true;
        }
        return this.value == null ? keyValuePair.value != null : !this.value.equals(keyValuePair.value);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<KeyValuePair> {
        @PluginBuilderAttribute
        private String key;
        @PluginBuilderAttribute
        private String value;

        public Builder setKey(String string) {
            this.key = string;
            return this;
        }

        public Builder setValue(String string) {
            this.value = string;
            return this;
        }

        @Override
        public KeyValuePair build() {
            return new KeyValuePair(this.key, this.value);
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

