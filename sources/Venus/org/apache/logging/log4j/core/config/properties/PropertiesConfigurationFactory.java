/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.properties.PropertiesConfiguration;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Plugin(name="PropertiesConfigurationFactory", category="ConfigurationFactory")
@Order(value=8)
public class PropertiesConfigurationFactory
extends ConfigurationFactory {
    @Override
    protected String[] getSupportedTypes() {
        return new String[]{".properties"};
    }

    @Override
    public PropertiesConfiguration getConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        Properties properties = new Properties();
        try (InputStream inputStream = configurationSource.getInputStream();){
            properties.load(inputStream);
        } catch (IOException iOException) {
            throw new ConfigurationException("Unable to load " + configurationSource.toString(), iOException);
        }
        return new PropertiesConfigurationBuilder().setConfigurationSource(configurationSource).setRootProperties(properties).setLoggerContext(loggerContext).build();
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        return this.getConfiguration(loggerContext, configurationSource);
    }
}

