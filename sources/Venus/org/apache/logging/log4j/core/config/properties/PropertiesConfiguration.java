/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.properties;

import java.io.IOException;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.config.builder.api.Component;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationFactory;

public class PropertiesConfiguration
extends BuiltConfiguration
implements Reconfigurable {
    public PropertiesConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource, Component component) {
        super(loggerContext, configurationSource, component);
    }

    @Override
    public Configuration reconfigure() {
        try {
            ConfigurationSource configurationSource = this.getConfigurationSource().resetInputStream();
            if (configurationSource == null) {
                return null;
            }
            PropertiesConfigurationFactory propertiesConfigurationFactory = new PropertiesConfigurationFactory();
            PropertiesConfiguration propertiesConfiguration = propertiesConfigurationFactory.getConfiguration(this.getLoggerContext(), configurationSource);
            return propertiesConfiguration == null || propertiesConfiguration.getState() != LifeCycle.State.INITIALIZING ? null : propertiesConfiguration;
        } catch (IOException iOException) {
            LOGGER.error("Cannot locate file {}: {}", (Object)this.getConfigurationSource(), (Object)iOException);
            return null;
        }
    }
}

