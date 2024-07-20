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
    public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
        if (currentContext) {
            LoggerContext ctx = ContextAnchor.THREAD_CONTEXT.get();
            if (ctx != null) {
                return ctx;
            }
            return this.getDefault();
        }
        if (loader instanceof BundleReference) {
            return BundleContextSelector.locateContext(((BundleReference)loader).getBundle(), configLocation);
        }
        Class<?> callerClass = ReflectionUtil.getCallerClass(fqcn);
        if (callerClass != null) {
            return BundleContextSelector.locateContext(FrameworkUtil.getBundle(callerClass), configLocation);
        }
        LoggerContext lc = ContextAnchor.THREAD_CONTEXT.get();
        return lc == null ? this.getDefault() : lc;
    }

    private static LoggerContext locateContext(Bundle bundle, URI configLocation) {
        String name = Objects.requireNonNull(bundle, "No Bundle provided").getSymbolicName();
        AtomicReference ref = (AtomicReference)CONTEXT_MAP.get(name);
        if (ref == null) {
            LoggerContext context = new LoggerContext(name, (Object)bundle, configLocation);
            CONTEXT_MAP.putIfAbsent(name, new AtomicReference<WeakReference<LoggerContext>>(new WeakReference<LoggerContext>(context)));
            return (LoggerContext)((WeakReference)((AtomicReference)CONTEXT_MAP.get(name)).get()).get();
        }
        WeakReference r = (WeakReference)ref.get();
        LoggerContext ctx = (LoggerContext)r.get();
        if (ctx == null) {
            LoggerContext context = new LoggerContext(name, (Object)bundle, configLocation);
            ref.compareAndSet(r, new WeakReference<LoggerContext>(context));
            return (LoggerContext)((WeakReference)ref.get()).get();
        }
        URI oldConfigLocation = ctx.getConfigLocation();
        if (oldConfigLocation == null && configLocation != null) {
            LOGGER.debug("Setting bundle ({}) configuration to {}", (Object)name, (Object)configLocation);
            ctx.setConfigLocation(configLocation);
        } else if (oldConfigLocation != null && configLocation != null && !configLocation.equals(oldConfigLocation)) {
            LOGGER.warn("locateContext called with URI [{}], but existing LoggerContext has URI [{}]", (Object)configLocation, (Object)oldConfigLocation);
        }
        return ctx;
    }
}

