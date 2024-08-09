/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleReference
 *  org.osgi.framework.FrameworkUtil
 */
package org.apache.logging.log4j.core.osgi;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.core.selector.ClassLoaderContextSelector;
import org.apache.logging.log4j.util.ReflectionUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;
import org.osgi.framework.FrameworkUtil;

public class BundleContextSelector
extends ClassLoaderContextSelector {
    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, boolean bl, URI uRI) {
        if (bl) {
            LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
            if (loggerContext != null) {
                return loggerContext;
            }
            return this.getDefault();
        }
        if (classLoader instanceof BundleReference) {
            return BundleContextSelector.locateContext(((BundleReference)classLoader).getBundle(), uRI);
        }
        Class<?> clazz = ReflectionUtil.getCallerClass(string);
        if (clazz != null) {
            return BundleContextSelector.locateContext(FrameworkUtil.getBundle(clazz), uRI);
        }
        LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        return loggerContext == null ? this.getDefault() : loggerContext;
    }

    private static LoggerContext locateContext(Bundle bundle, URI uRI) {
        String string = Objects.requireNonNull(bundle, "No Bundle provided").getSymbolicName();
        AtomicReference atomicReference = (AtomicReference)CONTEXT_MAP.get(string);
        if (atomicReference == null) {
            LoggerContext loggerContext = new LoggerContext(string, (Object)bundle, uRI);
            CONTEXT_MAP.putIfAbsent(string, new AtomicReference<WeakReference<LoggerContext>>(new WeakReference<LoggerContext>(loggerContext)));
            return (LoggerContext)((WeakReference)((AtomicReference)CONTEXT_MAP.get(string)).get()).get();
        }
        WeakReference weakReference = (WeakReference)atomicReference.get();
        LoggerContext loggerContext = (LoggerContext)weakReference.get();
        if (loggerContext == null) {
            LoggerContext loggerContext2 = new LoggerContext(string, (Object)bundle, uRI);
            atomicReference.compareAndSet(weakReference, new WeakReference<LoggerContext>(loggerContext2));
            return (LoggerContext)((WeakReference)atomicReference.get()).get();
        }
        URI uRI2 = loggerContext.getConfigLocation();
        if (uRI2 == null && uRI != null) {
            LOGGER.debug("Setting bundle ({}) configuration to {}", (Object)string, (Object)uRI);
            loggerContext.setConfigLocation(uRI);
        } else if (uRI2 != null && uRI != null && !uRI.equals(uRI2)) {
            LOGGER.warn("locateContext called with URI [{}], but existing LoggerContext has URI [{}]", (Object)uRI, (Object)uRI2);
        }
        return loggerContext;
    }
}

