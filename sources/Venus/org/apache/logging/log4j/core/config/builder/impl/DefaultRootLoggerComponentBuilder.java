/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentAndConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class DefaultRootLoggerComponentBuilder
extends DefaultComponentAndConfigurationBuilder<RootLoggerComponentBuilder>
implements RootLoggerComponentBuilder {
    public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string) {
        super(defaultConfigurationBuilder, "", "Root");
        this.addAttribute("level", string);
    }

    public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, boolean bl) {
        super(defaultConfigurationBuilder, "", "Root");
        this.addAttribute("level", string);
        this.addAttribute("includeLocation", bl);
    }

    public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2) {
        super(defaultConfigurationBuilder, "", string2);
        this.addAttribute("level", string);
    }

    public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2, boolean bl) {
        super(defaultConfigurationBuilder, "", string2);
        this.addAttribute("level", string);
        this.addAttribute("includeLocation", bl);
    }

    @Override
    public RootLoggerComponentBuilder add(AppenderRefComponentBuilder appenderRefComponentBuilder) {
        return (RootLoggerComponentBuilder)this.addComponent(appenderRefComponentBuilder);
    }

    @Override
    public RootLoggerComponentBuilder add(FilterComponentBuilder filterComponentBuilder) {
        return (RootLoggerComponentBuilder)this.addComponent(filterComponentBuilder);
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

