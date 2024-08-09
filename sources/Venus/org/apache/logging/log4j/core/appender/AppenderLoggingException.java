/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import org.apache.logging.log4j.LoggingException;

public class AppenderLoggingException
extends LoggingException {
    private static final long serialVersionUID = 6545990597472958303L;

    public AppenderLoggingException(String string) {
        super(string);
    }

    public AppenderLoggingException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AppenderLoggingException(Throwable throwable) {
        super(throwable);
    }
}

