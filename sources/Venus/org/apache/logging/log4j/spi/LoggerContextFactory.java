/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.net.URI;
import org.apache.logging.log4j.spi.LoggerContext;

public interface LoggerContextFactory {
    public LoggerContext getContext(String var1, ClassLoader var2, Object var3, boolean var4);

    public LoggerContext getContext(String var1, ClassLoader var2, Object var3, boolean var4, URI var5, String var6);

    public void removeContext(LoggerContext var1);
}

