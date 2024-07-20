/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.ConfigurationSource;

public class DefaultConfiguration
extends AbstractConfiguration {
    public static final String DEFAULT_NAME = "Default";
    public static final String DEFAULT_LEVEL = "org.apache.logging.log4j.level";
    public static final String DEFAULT_PATTERN = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n";

    public DefaultConfiguration() {
        super(null, ConfigurationSource.NULL_SOURCE);
        this.setToDefault();
    }

    @Override
    protected void doConfigure() {
    }
}

