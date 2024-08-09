/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.simple;

import java.net.URI;
import org.apache.logging.log4j.simple.SimpleLoggerContext;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;

public class SimpleLoggerContextFactory
implements LoggerContextFactory {
    private static LoggerContext context = new SimpleLoggerContext();

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl) {
        return context;
    }

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl, URI uRI, String string2) {
        return context;
    }

    @Override
    public void removeContext(LoggerContext loggerContext) {
    }
}

