/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.visitors;

import java.lang.annotation.Annotation;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.PluginVisitorStrategy;
import org.apache.logging.log4j.core.config.plugins.visitors.PluginVisitor;
import org.apache.logging.log4j.status.StatusLogger;

public final class PluginVisitors {
    private static final Logger LOGGER = StatusLogger.getLogger();

    private PluginVisitors() {
    }

    public static PluginVisitor<? extends Annotation> findVisitor(Class<? extends Annotation> clazz) {
        PluginVisitorStrategy pluginVisitorStrategy = clazz.getAnnotation(PluginVisitorStrategy.class);
        if (pluginVisitorStrategy == null) {
            return null;
        }
        try {
            return pluginVisitorStrategy.value().newInstance();
        } catch (Exception exception) {
            LOGGER.error("Error loading PluginVisitor [{}] for annotation [{}].", (Object)pluginVisitorStrategy.value(), (Object)clazz, (Object)exception);
            return null;
        }
    }
}

