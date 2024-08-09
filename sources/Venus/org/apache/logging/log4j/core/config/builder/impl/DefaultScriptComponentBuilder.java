/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.ScriptComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentAndConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

class DefaultScriptComponentBuilder
extends DefaultComponentAndConfigurationBuilder<ScriptComponentBuilder>
implements ScriptComponentBuilder {
    public DefaultScriptComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2, String string3) {
        super(defaultConfigurationBuilder, string, "Script");
        if (string2 != null) {
            this.addAttribute("language", string2);
        }
        if (string3 != null) {
            this.addAttribute("text", string3);
        }
    }
}

