/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;
import org.slf4j.helpers.SubstituteLogger;

public class SubstituteLoggingEvent
implements LoggingEvent {
    Level level;
    List<Marker> markers;
    String loggerName;
    SubstituteLogger logger;
    String threadName;
    String message;
    Object[] argArray;
    List<KeyValuePair> keyValuePairList;
    long timeStamp;
    Throwable throwable;

    @Override
    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public List<Marker> getMarkers() {
        return this.markers;
    }

    public void addMarker(Marker marker) {
        if (marker == null) {
            return;
        }
        if (this.markers == null) {
            this.markers = new ArrayList<Marker>(2);
        }
        this.markers.add(marker);
    }

    @Override
    public String getLoggerName() {
        return this.loggerName;
    }

    public void setLoggerName(String string) {
        this.loggerName = string;
    }

    public SubstituteLogger getLogger() {
        return this.logger;
    }

    public void setLogger(SubstituteLogger substituteLogger) {
        this.logger = substituteLogger;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String string) {
        this.message = string;
    }

    @Override
    public Object[] getArgumentArray() {
        return this.argArray;
    }

    public void setArgumentArray(Object[] objectArray) {
        this.argArray = objectArray;
    }

    @Override
    public List<Object> getArguments() {
        if (this.argArray == null) {
            return null;
        }
        return Arrays.asList(this.argArray);
    }

    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long l) {
        this.timeStamp = l;
    }

    @Override
    public String getThreadName() {
        return this.threadName;
    }

    public void setThreadName(String string) {
        this.threadName = string;
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        return this.keyValuePairList;
    }
}

