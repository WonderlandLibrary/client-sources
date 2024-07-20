/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentAndConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

class DefaultFilterComponentBuilder
extends DefaultComponentAndConfigurationBuilder<FilterComponentBuilder>
implements FilterComponentBuilder {
    public DefaultFilterComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> builder, String type2, String onMatch, String onMisMatch) {
        super(builder, type2);
        this.addAttribute("onMatch", onMatch);
        this.addAttribute("onMisMatch", onMisMatch);
    }
}

