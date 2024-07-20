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
    public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
        return this.getContext(fqcn, loader, currentContext, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
        LoggerContext lc = ContextAnchor.THREAD_CONTEXT.get();
        if (lc != null) {
            return lc;
        }
        String loggingContextName = null;
        try (JndiManager jndiManager = JndiManager.getDefaultManager();){
            loggingContextName = (String)jndiManager.lookup("java:comp/env/log4j/context-name");
        }
        return loggingContextName == null ? CONTEXT : this.locateContext(loggingContextName, null, configLocation);
    }

    @Override
    public LoggerContext locateContext(String name, Object externalContext, URI configLocation) {
        if (name == null) {
            LOGGER.error("A context name is required to locate a LoggerContext");
            return null;
        }
        if (!CONTEXT_MAP.containsKey(name)) {
            LoggerContext ctx = new LoggerContext(name, externalContext, configLocation);
            CONTEXT_MAP.putIfAbsent(name, ctx);
        }
        return (LoggerContext)CONTEXT_MAP.get(name);
    }

    @Override
    public void removeContext(LoggerContext context) {
        for (Map.Entry entry : CONTEXT_MAP.entrySet()) {
            if (!((LoggerContext)entry.getValue()).equals(context)) continue;
            CONTEXT_MAP.remove(entry.getKey());
        }
    }

    @Override
    public LoggerContext removeContext(String name) {
        return (LoggerContext)CONTEXT_MAP.remove(name);
    }

    @Override
    public List<LoggerContext> getLoggerContexts() {
        ArrayList list = new ArrayList(CONTEXT_MAP.values());
        return Collections.unmodifiableList(list);
    }
}

