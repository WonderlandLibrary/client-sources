/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentAndConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class DefaultAppenderRefComponentBuilder
extends DefaultComponentAndConfigurationBuilder<AppenderRefComponentBuilder>
implements AppenderRefComponentBuilder {
    public DefaultAppenderRefComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string) {
        super(defaultConfigurationBuilder, "AppenderRef");
        this.addAttribute("ref", string);
    }

    @Override
    public AppenderRefComponentBuilder add(FilterComponentBuilder filterComponentBuilder) {
        return (AppenderRefComponentBuilder)this.addComponent(filterComponentBuilder);
    }

    @Override
    public ComponentBuilder add(FilterComponentBuilder filterComponentBuilder) {
        return this.add(filterComponentBuilder);
    }
}

