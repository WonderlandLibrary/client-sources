/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.apache.logging.log4j.spi.ExtendedLogger;

public final class EventLogger {
    public static final Marker EVENT_MARKER = MarkerManager.getMarker("EVENT");
    private static final String NAME = "EventLogger";
    private static final String FQCN = EventLogger.class.getName();
    private static final ExtendedLogger LOGGER = LogManager.getContext(false).getLogger("EventLogger");

    private EventLogger() {
    }

    public static void logEvent(StructuredDataMessage msg) {
        LOGGER.logIfEnabled(FQCN, Level.OFF, EVENT_MARKER, msg, null);
    }

    public static void logEvent(StructuredDataMessage msg, Level level) {
        LOGGER.logIfEnabled(FQCN, level, EVENT_MARKER, msg, null);
    }
}

