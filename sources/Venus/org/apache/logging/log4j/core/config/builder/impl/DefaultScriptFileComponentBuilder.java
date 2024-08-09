/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.impl;

import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.builder.api.ScriptFileComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultComponentAndConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class DefaultScriptFileComponentBuilder
extends DefaultComponentAndConfigurationBuilder<ScriptFileComponentBuilder>
implements ScriptFileComponentBuilder {
    public DefaultScriptFileComponentBuilder(DefaultConfigurationBuilder<? extends Configuration> defaultConfigurationBuilder, String string, String string2) {
        super(defaultConfigurationBuilder, string != null ? string : string2, "ScriptFile");
        this.addAttribute("path", string2);
    }

    @Override
    public DefaultScriptFileComponentBuilder addLanguage(String string) {
        this.addAttribute("language", string);
        return this;
    }

    @Override
    public DefaultScriptFileComponentBuilder addIsWatched(boolean bl) {
        this.addAttribute("isWatched", Boolean.toString(bl));
        return this;
    }

    @Override
    public DefaultScriptFileComponentBuilder addIsWatched(String string) {
        this.addAttribute("isWatched", string);
        return this;
    }

    @Override
    public DefaultScriptFileComponentBuilder addCharset(String string) {
        this.addAttribute("charset", string);
        return this;
    }

    @Override
    public ScriptFileComponentBuilder addCharset(String string) {
        return this.addCharset(string);
    }

    @Override
    public ScriptFileComponentBuilder addIsWatched(String string) {
        return this.addIsWatched(string);
    }

    @Override
    public ScriptFileComponentBuilder addIsWatched(boolean bl) {
        return this.addIsWatched(bl);
    }

    @Override
    public ScriptFileComponentBuilder addLanguage(String string) {
        return this.addLanguage(string);
    }
}

