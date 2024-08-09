/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rewrite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="PropertiesRewritePolicy", category="Core", elementType="rewritePolicy", printObject=true)
public final class PropertiesRewritePolicy
implements RewritePolicy {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    private final Map<Property, Boolean> properties;
    private final Configuration config;

    private PropertiesRewritePolicy(Configuration configuration, List<Property> list) {
        this.config = configuration;
        this.properties = new HashMap<Property, Boolean>(list.size());
        for (Property property : list) {
            Boolean bl = property.getValue().contains("${");
            this.properties.put(property, bl);
        }
    }

    @Override
    public LogEvent rewrite(LogEvent logEvent) {
        HashMap<String, String> hashMap = new HashMap<String, String>(logEvent.getContextData().toMap());
        for (Map.Entry<Property, Boolean> entry : this.properties.entrySet()) {
            Property property = entry.getKey();
            hashMap.put(property.getName(), entry.getValue() != false ? this.config.getStrSubstitutor().replace(property.getValue()) : property.getValue());
        }
        Log4jLogEvent log4jLogEvent = new Log4jLogEvent.Builder(logEvent).setContextMap(hashMap).build();
        return log4jLogEvent;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" {");
        boolean bl = true;
        for (Map.Entry<Property, Boolean> entry : this.properties.entrySet()) {
            if (!bl) {
                stringBuilder.append(", ");
            }
            Property property = entry.getKey();
            stringBuilder.append(property.getName()).append('=').append(property.getValue());
            bl = false;
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @PluginFactory
    public static PropertiesRewritePolicy createPolicy(@PluginConfiguration Configuration configuration, @PluginElement(value="Properties") Property[] propertyArray) {
        if (propertyArray == null || propertyArray.length == 0) {
            LOGGER.error("Properties must be specified for the PropertiesRewritePolicy");
            return null;
        }
        List<Property> list = Arrays.asList(propertyArray);
        return new PropertiesRewritePolicy(configuration, list);
    }
}

