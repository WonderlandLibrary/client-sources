/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.HashMap;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.lookup.Interpolator;
import org.apache.logging.log4j.core.lookup.MapLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name="properties", category="Core", printObject=true)
public final class PropertiesPlugin {
    private PropertiesPlugin() {
    }

    @PluginFactory
    public static StrLookup configureSubstitutor(@PluginElement(value="Properties") Property[] propertyArray, @PluginConfiguration Configuration configuration) {
        if (propertyArray == null) {
            return new Interpolator(configuration.getProperties());
        }
        HashMap<String, String> hashMap = new HashMap<String, String>(configuration.getProperties());
        for (Property property : propertyArray) {
            hashMap.put(property.getName(), property.getValue());
        }
        return new Interpolator(new MapLookup(hashMap), configuration.getPluginPackages());
    }
}

