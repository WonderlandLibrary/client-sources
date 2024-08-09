/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.CompositeFilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentAndConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class DefaultCompositeFilterComponentBuilder
extends DefaultComponentAndConfigurationBuilder<CompositeFilterComponentBuilder>
implements CompositeFilterComponentBuilder {
    public DefaultCompositeFilterComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2) {
        super(defaultConfigurationBuilder, "Filters");
        this.addAttribute("onMatch", string);
        this.addAttribute("onMisMatch", string2);
    }

    @Override
    public CompositeFilterComponentBuilder add(FilterComponentBuilder filterComponentBuilder) {
        return (CompositeFilterComponentBuilder)this.addComponent(filterComponentBuilder);
    }

    @Override
    public ComponentBuilder add(FilterComponentBuilder filterComponentBuilder) {
        return this.add(filterComponentBuilder);
    }
}

