/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.selector;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.core.selector.ContextSelector;

public class BasicContextSelector
implements ContextSelector {
    private static final LoggerContext CONTEXT = new LoggerContext("Default");

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, boolean bl) {
        LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        return loggerContext != null ? loggerContext : CONTEXT;
    }

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, boolean bl, URI uRI) {
        LoggerContext loggerContext = ContextAnchor.THREAD_CONTEXT.get();
        return loggerContext != null ? loggerContext : CONTEXT;
    }

    public LoggerContext locateContext(String string, String string2) {
        return CONTEXT;
    }

    @Override
    public void removeContext(LoggerContext loggerContext) {
    }

    @Override
    public List<LoggerContext> getLoggerContexts() {
        ArrayList<LoggerContext> arrayList = new ArrayList<LoggerContext>();
        arrayList.add(CONTEXT);
        return Collections.unmodifiableList(arrayList);
    }
}

