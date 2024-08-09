/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

class DefaultComponentAndConfigurationBuilder<T extends ComponentBuilder<T>>
extends DefaultComponentBuilder<T, DefaultConfigurationBuilder<? extends Configuration>> {
    DefaultComponentAndConfigurationBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2, String string3) {
        super(defaultConfigurationBuilder, string, string2, string3);
    }

    DefaultComponentAndConfigurationBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2) {
        super(defaultConfigurationBuilder, string, string2);
    }

    public DefaultComponentAndConfigurationBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string) {
        super(defaultConfigurationBuilder, string);
    }
}

