/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.event;

import java.util.List;
import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.Level;

public interface LoggingEvent {
    public Level getLevel();

    public String getLoggerName();

    public String getMessage();

    public List<Object> getArguments();

    public Object[] getArgumentArray();

    public List<Marker> getMarkers();

    public List<KeyValuePair> getKeyValuePairs();

    public Throwable getThrowable();

    public long getTimeStamp();

    public String getThreadName();

    default public String getCallerBoundary() {
        return null;
    }
}

