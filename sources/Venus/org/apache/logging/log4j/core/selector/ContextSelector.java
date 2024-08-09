/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.selector;

import java.net.URI;
import java.util.List;
import org.apache.logging.log4j.core.LoggerContext;

public interface ContextSelector {
    public LoggerContext getContext(String var1, ClassLoader var2, boolean var3);

    public LoggerContext getContext(String var1, ClassLoader var2, boolean var3, URI var4);

    public List<LoggerContext> getLoggerContexts();

    public void removeContext(LoggerContext var1);
}

