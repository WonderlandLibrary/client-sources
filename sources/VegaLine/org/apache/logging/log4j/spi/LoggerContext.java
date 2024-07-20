/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.ExtendedLogger;

public interface LoggerContext {
    public Object getExternalContext();

    public ExtendedLogger getLogger(String var1);

    public ExtendedLogger getLogger(String var1, MessageFactory var2);

    public boolean hasLogger(String var1);

    public boolean hasLogger(String var1, MessageFactory var2);

    public boolean hasLogger(String var1, Class<? extends MessageFactory> var2);
}

