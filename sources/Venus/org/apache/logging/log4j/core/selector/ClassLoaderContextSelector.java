/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.selector;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.core.selector.ContextSelector;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.ReflectionUtil;

public class ClassLoaderContextSelector
implements ContextSelector {
    private static final AtomicReference<LoggerContext> DEFAULT_CONTEXT = new AtomicReference();
    protected static final StatusLogger LOGGER = StatusLogger.getLogger();
    protected static final ConcurrentMap<String, AtomicReference<WeakReference<LoggerContext>>> CONTEXT_MAP = new ConcurrentHashMap<String, AtomicReference<WeakReference<LoggerContext>>>();

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, boolean bl) {
        return this.getContext(string, classLoader, bl, null);
    }

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, boolean bl, URI uRI) {
        if (bl) {
            LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
            if (loggerContext != null) {
                return loggerContext;
            }
            return this.getDefault();
        }
        if (classLoader != null) {
            return this.locateContext(classLoader, uRI);
        }
        Class<?> clazz = ReflectionUtil.getCallerClass(string);
        if (clazz != null) {
            return this.locateContext(clazz.getClassLoader(), uRI);
        }
        LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        if (loggerContext != null) {
            return loggerContext;
        }
        return this.getDefault();
    }

    @Override
    public void removeContext(LoggerContext loggerContext) {
        for (Map.Entry entry : CONTEXT_MAP.entrySet()) {
            LoggerContext loggerContext2 = (LoggerContext)((WeakReference)((AtomicReference)entry.getValue()).get()).get();
            if (loggerContext2 != loggerContext) continue;
            CONTEXT_MAP.remove(entry.getKey());
        }
    }

    @Override
    public List<LoggerContext> getLoggerContexts() {
        ArrayList<LoggerContext> arrayList = new ArrayList<LoggerContext>();
        Collection collection = CONTEXT_MAP.values();
        for (AtomicReference atomicReference : collection) {
            LoggerContext loggerContext = (LoggerContext)((WeakReference)atomicReference.get()).get();
            if (loggerContext == null) continue;
            arrayList.add(loggerContext);
        }
        return Collections.unmodifiableList(arrayList);
    }

    private LoggerContext locateContext(ClassLoader classLoader, URI uRI) {
        ClassLoader classLoader2 = classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
        String string = this.toContextMapKey(classLoader2);
        AtomicReference atomicReference = (AtomicReference)CONTEXT_MAP.get(string);
        if (atomicReference == null) {
            Object object;
            Object object2;
            if (uRI == null) {
                for (object2 = classLoader2.getParent(); object2 != null; object2 = ((ClassLoader)object2).getParent()) {
                    LoggerContext loggerContext;
                    atomicReference = (AtomicReference)CONTEXT_MAP.get(this.toContextMapKey((ClassLoader)object2));
                    if (atomicReference == null || (loggerContext = (LoggerContext)((Reference)(object = (WeakReference)atomicReference.get())).get()) == null) continue;
                    return loggerContext;
                }
            }
            object2 = this.createContext(string, uRI);
            object = new AtomicReference<WeakReference<Object>>();
            ((AtomicReference)object).set(new WeakReference<Object>(object2));
            CONTEXT_MAP.putIfAbsent(string, (AtomicReference<WeakReference<LoggerContext>>)object);
            object2 = (LoggerContext)((WeakReference)((AtomicReference)CONTEXT_MAP.get(string)).get()).get();
            return object2;
        }
        WeakReference weakReference = (WeakReference)atomicReference.get();
        LoggerContext loggerContext = (LoggerContext)weakReference.get();
        if (loggerContext != null) {
            if (loggerContext.getConfigLocation() == null && uRI != null) {
                LOGGER.debug("Setting configuration to {}", (Object)uRI);
                loggerContext.setConfigLocation(uRI);
            } else if (loggerContext.getConfigLocation() != null && uRI != null && !loggerContext.getConfigLocation().equals(uRI)) {
                LOGGER.warn("locateContext called with URI {}. Existing LoggerContext has URI {}", (Object)uRI, (Object)loggerContext.getConfigLocation());
            }
            return loggerContext;
        }
        loggerContext = this.createContext(string, uRI);
        atomicReference.compareAndSet(weakReference, new WeakReference<LoggerContext>(loggerContext));
        return loggerContext;
    }

    protected LoggerContext createContext(String string, URI uRI) {
        return new LoggerContext(string, null, uRI);
    }

    protected String toContextMapKey(ClassLoader classLoader) {
        return Integer.toHexString(System.identityHashCode(classLoader));
    }

    protected LoggerContext getDefault() {
        LoggerContext loggerContext = DEFAULT_CONTEXT.get();
        if (loggerContext != null) {
            return loggerContext;
        }
        DEFAULT_CONTEXT.compareAndSet(null, this.createContext(this.defaultContextName(), null));
        return DEFAULT_CONTEXT.get();
    }

    protected String defaultContextName() {
        return "Default";
    }
}

