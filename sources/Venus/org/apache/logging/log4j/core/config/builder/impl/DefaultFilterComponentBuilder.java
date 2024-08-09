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
    public DefaultFilterComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2, String string3) {
        super(defaultConfigurationBuilder, string);
        this.addAttribute("onMatch", string2);
        this.addAttribute("onMisMatch", string3);
    }
}

