/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginValue;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="property", category="Core", printObject=true)
public final class Property {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final String name;
    private final String value;
    private final boolean valueNeedsLookup;

    private Property(String string, String string2) {
        this.name = string;
        this.value = string2;
        this.valueNeedsLookup = string2 != null && string2.contains("${");
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return Objects.toString(this.value, "");
    }

    public boolean isValueNeedsLookup() {
        return this.valueNeedsLookup;
    }

    @PluginFactory
    public static Property createProperty(@PluginAttribute(value="name") String string, @PluginValue(value="value") String string2) {
        if (string == null) {
            LOGGER.error("Property name cannot be null");
        }
        return new Property(string, string2);
    }

    public String toString() {
        return this.name + '=' + this.getValue();
    }
}

