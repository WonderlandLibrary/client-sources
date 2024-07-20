/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.impl.ThreadContextDataInjector;
import org.apache.logging.log4j.spi.CopyOnWrite;
import org.apache.logging.log4j.spi.DefaultThreadContextMap;
import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public class ContextDataInjectorFactory {
    public static ContextDataInjector createInjector() {
        String className = PropertiesUtil.getProperties().getStringProperty("log4j2.ContextDataInjector");
        if (className == null) {
            return ContextDataInjectorFactory.createDefaultInjector();
        }
        try {
            Class<ContextDataInjector> cls = LoaderUtil.loadClass(className).asSubclass(ContextDataInjector.class);
            return cls.newInstance();
        } catch (Exception dynamicFailed) {
            ContextDataInjector result = ContextDataInjectorFactory.createDefaultInjector();
            StatusLogger.getLogger().warn("Could not create ContextDataInjector for '{}', using default {}: {}", (Object)className, (Object)result.getClass().getName(), (Object)dynamicFailed);
            return result;
        }
    }

    private static ContextDataInjector createDefaultInjector() {
        ReadOnlyThreadContextMap threadContextMap = ThreadContext.getThreadContextMap();
        if (threadContextMap instanceof DefaultThreadContextMap || threadContextMap == null) {
            return new ThreadContextDataInjector.ForDefaultThreadContextMap();
        }
        if (threadContextMap instanceof CopyOnWrite) {
            return new ThreadContextDataInjector.ForCopyOnWriteThreadContextMap();
        }
        return new ThreadContextDataInjector.ForGarbageFreeThreadContextMap();
    }
}

