/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.dataformat.yaml.YAMLFactory
 */
package org.apache.logging.log4j.core.config.yaml;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.json.JsonConfiguration;

public class YamlConfiguration
extends JsonConfiguration {
    public YamlConfiguration(LoggerContext loggerContext, ConfigurationSource configurationSource) {
        super(loggerContext, configurationSource);
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        return new ObjectMapper((JsonFactory)new YAMLFactory()).configure(JsonParser.Feature.ALLOW_COMMENTS, false);
    }

    @Override
    public Configuration reconfigure() {
        try {
            ConfigurationSource configurationSource = this.getConfigurationSource().resetInputStream();
            if (configurationSource == null) {
                return null;
            }
            return new YamlConfiguration(this.getLoggerContext(), configurationSource);
        } catch (IOException iOException) {
            LOGGER.error("Cannot locate file {}", (Object)this.getConfigurationSource(), (Object)iOException);
            return null;
        }
    }
}

