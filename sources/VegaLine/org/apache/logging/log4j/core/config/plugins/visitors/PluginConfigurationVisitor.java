/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.visitors;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.visitors.AbstractPluginVisitor;

public class PluginConfigurationVisitor
extends AbstractPluginVisitor<PluginConfiguration> {
    public PluginConfigurationVisitor() {
        super(PluginConfiguration.class);
    }

    @Override
    public Object visit(Configuration configuration, Node node, LogEvent event, StringBuilder log) {
        if (this.conversionType.isInstance(configuration)) {
            log.append("Configuration");
            if (configuration.getName() != null) {
                log.append('(').append(configuration.getName()).append(')');
            }
            return configuration;
        }
        LOGGER.warn("Variable annotated with @PluginConfiguration is not compatible with type {}.", (Object)configuration.getClass());
        return null;
    }
}

