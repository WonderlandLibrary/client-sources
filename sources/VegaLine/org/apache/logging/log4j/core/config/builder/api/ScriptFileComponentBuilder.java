/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.api;

import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;

public interface ScriptFileComponentBuilder
extends ComponentBuilder<ScriptFileComponentBuilder> {
    public ScriptFileComponentBuilder addLanguage(String var1);

    public ScriptFileComponentBuilder addIsWatched(boolean var1);

    public ScriptFileComponentBuilder addIsWatched(String var1);

    public ScriptFileComponentBuilder addCharset(String var1);
}

