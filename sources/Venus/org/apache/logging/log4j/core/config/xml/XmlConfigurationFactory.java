/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.xml;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;

@Plugin(name="XmlConfigurationFactory", category="ConfigurationFactory")
@Order(value=5)
public class XmlConfigurationFactory
extends ConfigurationFactory {
    public static final String[] SUFFIXES = new String[]{".xml", "*"};

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        return new XmlConfiguration(loggerContext, configurationSource);
    }

    @Override
    public String[] getSupportedTypes() {
        return SUFFIXES;
    }
}

