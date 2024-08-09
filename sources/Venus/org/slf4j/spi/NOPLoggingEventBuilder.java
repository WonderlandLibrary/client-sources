/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.spi;

import java.util.function.Supplier;
import org.slf4j.Marker;
import org.slf4j.spi.LoggingEventBuilder;

public class NOPLoggingEventBuilder
implements LoggingEventBuilder {
    static final NOPLoggingEventBuilder SINGLETON = new NOPLoggingEventBuilder();

    private NOPLoggingEventBuilder() {
    }

    public static LoggingEventBuilder singleton() {
        return SINGLETON;
    }

    @Override
    public LoggingEventBuilder addMarker(Marker marker) {
        return NOPLoggingEventBuilder.singleton();
    }

    @Override
    public LoggingEventBuilder addArgument(Object object) {
        return NOPLoggingEventBuilder.singleton();
    }

    @Override
    public LoggingEventBuilder addArgument(Supplier<?> supplier) {
        return NOPLoggingEventBuilder.singleton();
    }

    @Override
    public LoggingEventBuilder addKeyValue(String string, Object object) {
        return NOPLoggingEventBuilder.singleton();
    }

    @Override
    public LoggingEventBuilder addKeyValue(String string, Supplier<Object> supplier) {
        return NOPLoggingEventBuilder.singleton();
    }

    @Override
    public LoggingEventBuilder setCause(Throwable throwable) {
        return NOPLoggingEventBuilder.singleton();
    }

    @Override
    public void log() {
    }

    @Override
    public LoggingEventBuilder setMessage(String string) {
        return this;
    }

    @Override
    public LoggingEventBuilder setMessage(Supplier<String> supplier) {
        return this;
    }

    @Override
    public void log(String string) {
    }

    @Override
    public void log(Supplier<String> supplier) {
    }

    @Override
    public void log(String string, Object object) {
    }

    @Override
    public void log(String string, Object object, Object object2) {
    }

    @Override
    public void log(String string, Object ... objectArray) {
    }
}

