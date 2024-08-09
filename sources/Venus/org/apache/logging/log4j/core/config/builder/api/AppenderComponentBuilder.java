/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.api;

import org.apache.logging.log4j.core.config.builder.api.FilterableComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;

public interface AppenderComponentBuilder
extends FilterableComponentBuilder<AppenderComponentBuilder> {
    public AppenderComponentBuilder add(LayoutComponentBuilder var1);

    @Override
    public String getName();
}

