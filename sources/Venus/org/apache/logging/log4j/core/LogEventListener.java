/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core;

import java.util.EventListener;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.status.StatusLogger;

public class LogEventListener
implements EventListener {
    protected static final StatusLogger LOGGER = StatusLogger.getLogger();
    private final LoggerContext context = LoggerContext.getContext(false);

    protected LogEventListener() {
    }

    public void log(LogEvent logEvent) {
        if (logEvent == null) {
            return;
        }
        Logger logger = this.context.getLogger(logEvent.getLoggerName());
        if (logger.privateConfig.filter(logEvent.getLevel(), logEvent.getMarker(), logEvent.getMessage(), logEvent.getThrown())) {
            logger.privateConfig.logEvent(logEvent);
        }
    }
}

