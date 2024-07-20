/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

public class ConfigurationFactoryData {
    public final Configuration configuration;

    public ConfigurationFactoryData(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public LoggerContext getLoggerContext() {
        return this.configuration != null ? this.configuration.getLoggerContext() : null;
    }
}

