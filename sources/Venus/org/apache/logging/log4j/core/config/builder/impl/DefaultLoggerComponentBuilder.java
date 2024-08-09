/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentAndConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class DefaultLoggerComponentBuilder
extends DefaultComponentAndConfigurationBuilder<LoggerComponentBuilder>
implements LoggerComponentBuilder {
    public DefaultLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2) {
        super(defaultConfigurationBuilder, string, "Logger");
        this.addAttribute("level", string2);
    }

    public DefaultLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2, boolean bl) {
        super(defaultConfigurationBuilder, string, "Logger");
        this.addAttribute("level", string2);
        this.addAttribute("includeLocation", bl);
    }

    public DefaultLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2, String string3) {
        super(defaultConfigurationBuilder, string, string3);
        this.addAttribute("level", string2);
    }

    public DefaultLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2, String string3, boolean bl) {
        super(defaultConfigurationBuilder, string, string3);
        this.addAttribute("level", string2);
        this.addAttribute("includeLocation", bl);
    }

    @Override
    public LoggerComponentBuilder add(AppenderRefComponentBuilder appenderRefComponentBuilder) {
        return (LoggerComponentBuilder)this.addComponent(appenderRefComponentBuilder);
    }

    @Override
    public LoggerComponentBuilder add(FilterComponentBuilder filterComponentBuilder) {
        return (LoggerComponentBuilder)this.addComponent(filterComponentBuilder);
    }

    @Override
    public ComponentBuilder add(AppenderRefComponentBuilder appenderRefComponentBuilder) {
        return this.add(appenderRefComponentBuilder);
    }

    @Override
    public ComponentBuilder add(FilterComponentBuilder filterComponentBuilder) {
        return this.add(filterComponentBuilder);
    }
}

