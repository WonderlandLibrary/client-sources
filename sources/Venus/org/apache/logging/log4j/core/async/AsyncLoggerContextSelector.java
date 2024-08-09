/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.async;

import java.net.URI;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.async.AsyncLoggerContext;
import org.apache.logging.log4j.core.selector.ClassLoaderContextSelector;
import org.apache.logging.log4j.util.PropertiesUtil;

public class AsyncLoggerContextSelector
extends ClassLoaderContextSelector {
    public static boolean isSelected() {
        return AsyncLoggerContextSelector.class.getName().equals(PropertiesUtil.getProperties().getStringProperty("Log4jContextSelector"));
    }

    @Override
    protected LoggerContext createContext(String string, URI uRI) {
        return new AsyncLoggerContext(string, null, uRI);
    }

    @Override
    protected String toContextMapKey(ClassLoader classLoader) {
        return "AsyncContext@" + Integer.toHexString(System.identityHashCode(classLoader));
    }

    @Override
    protected String defaultContextName() {
        return "DefaultAsyncContext@" + Thread.currentThread().getName();
    }
}

