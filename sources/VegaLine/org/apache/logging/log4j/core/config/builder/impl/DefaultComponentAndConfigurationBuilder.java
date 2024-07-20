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
    DefaultComponentAndConfigurationBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String name, String type2, String value) {
        super(builder, name, type2, value);
    }

    DefaultComponentAndConfigurationBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String name, String type2) {
        super(builder, name, type2);
    }

    public DefaultComponentAndConfigurationBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String type2) {
        super(builder, type2);
    }
}

