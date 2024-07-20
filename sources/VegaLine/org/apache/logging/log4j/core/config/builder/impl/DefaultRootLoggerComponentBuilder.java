/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentAndConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

class DefaultRootLoggerComponentBuilder
extends DefaultComponentAndConfigurationBuilder<RootLoggerComponentBuilder>
implements RootLoggerComponentBuilder {
    public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String level) {
        super(builder, "", "Root");
        this.addAttribute("level", level);
    }

    public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String level, boolean includeLocation) {
        super(builder, "", "Root");
        this.addAttribute("level", level);
        this.addAttribute("includeLocation", includeLocation);
    }

    public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String level, String type2) {
        super(builder, "", type2);
        this.addAttribute("level", level);
    }

    public DefaultRootLoggerComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String level, String type2, boolean includeLocation) {
        super(builder, "", type2);
        this.addAttribute("level", level);
        this.addAttribute("includeLocation", includeLocation);
    }

    @Override
    public RootLoggerComponentBuilder add(AppenderRefComponentBuilder builder) {
        return (RootLoggerComponentBuilder)this.addComponent(builder);
    }

    @Override
    public RootLoggerComponentBuilder add(FilterComponentBuilder builder) {
        return (RootLoggerComponentBuilder)this.addComponent(builder);
    }
}

