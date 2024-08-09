/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.builder.api;

import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterableComponentBuilder;

public interface LoggableComponentBuilder<T extends ComponentBuilder<T>>
extends FilterableComponentBuilder<T> {
    public T add(AppenderRefComponentBuilder var1);
}

