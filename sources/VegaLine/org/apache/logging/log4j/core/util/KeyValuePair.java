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

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
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
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
        result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        KeyValuePair other = (KeyValuePair)obj;
        if (this.key == null ? other.key != null : !this.key.equals(other.key)) {
            return false;
        }
        return !(this.value == null ? other.value != null : !this.value.equals(other.value));
    }

    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<KeyValuePair> {
        @PluginBuilderAttribute
        private String key;
        @PluginBuilderAttribute
        private String value;

        public Builder setKey(String aKey) {
            this.key = aKey;
            return this;
        }

        public Builder setValue(String aValue) {
            this.value = aValue;
            return this;
        }

        @Override
        public KeyValuePair build() {
            return new KeyValuePair(this.key, this.value);
        }
    }
}

