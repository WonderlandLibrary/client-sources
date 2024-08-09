/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.spi;

import java.util.function.Supplier;
import org.slf4j.Marker;
import org.slf4j.helpers.CheckReturnValue;

public interface LoggingEventBuilder {
    @CheckReturnValue
    public LoggingEventBuilder setCause(Throwable var1);

    @CheckReturnValue
    public LoggingEventBuilder addMarker(Marker var1);

    @CheckReturnValue
    public LoggingEventBuilder addArgument(Object var1);

    @CheckReturnValue
    public LoggingEventBuilder addArgument(Supplier<?> var1);

    @CheckReturnValue
    public LoggingEventBuilder addKeyValue(String var1, Object var2);

    @CheckReturnValue
    public LoggingEventBuilder addKeyValue(String var1, Supplier<Object> var2);

    @CheckReturnValue
    public LoggingEventBuilder setMessage(String var1);

    @CheckReturnValue
    public LoggingEventBuilder setMessage(Supplier<String> var1);

    public void log();

    public void log(String var1);

    public void log(String var1, Object var2);

    public void log(String var1, Object var2, Object var3);

    public void log(String var1, Object ... var2);

    public void log(Supplier<String> var1);
}

