/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.selector;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.core.net.JndiManager;
import org.apache.logging.log4j.core.selector.NamedContextSelector;
import org.apache.logging.log4j.status.StatusLogger;

public class JndiContextSelector
implements NamedContextSelector {
    private static final LoggerContext CONTEXT = new LoggerContext("Default");
    private static final ConcurrentMap<String, LoggerContext> CONTEXT_MAP = new ConcurrentHashMap<String, LoggerContext>();
    private static final StatusLogger LOGGER = StatusLogger.getLogger();

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, boolean bl) {
        return this.getContext(string, classLoader, bl, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, boolean bl, URI uRI) {
        LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        if (loggerContext != null) {
            return loggerContext;
        }
        String string2 = null;
        try (JndiManager jndiManager = JndiManager.getDefaultManager();){
            string2 = (String)jndiManager.lookup("java:comp/env/log4j/context-name");
        }
        return string2 == null ? CONTEXT : this.locateContext(string2, null, uRI);
    }

    @Override
    public LoggerContext locateContext(String string, Object object, URI uRI) {
        if (string == null) {
            LOGGER.error("A context name is required to locate a LoggerContext");
            return null;
        }
        if (!CONTEXT_MAP.containsKey(string)) {
            LoggerContext loggerContext = new LoggerContext(string, object, uRI);
            CONTEXT_MAP.putIfAbsent(string, loggerContext);
        }
        return (LoggerContext)CONTEXT_MAP.get(string);
    }

    @Override
    public void removeContext(LoggerContext loggerContext) {
        for (Map.Entry entry : CONTEXT_MAP.entrySet()) {
            if (!((LoggerContext)entry.getValue()).equals(loggerContext)) continue;
            CONTEXT_MAP.remove(entry.getKey());
        }
    }

    @Override
    public LoggerContext removeContext(String string) {
        return (LoggerContext)CONTEXT_MAP.remove(string);
    }

    @Override
    public List<LoggerContext> getLoggerContexts() {
        ArrayList arrayList = new ArrayList(CONTEXT_MAP.values());
        return Collections.unmodifiableList(arrayList);
    }
}

