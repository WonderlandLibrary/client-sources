/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.KeyValuePair;
import org.slf4j.event.Level;
import org.slf4j.event.LoggingEvent;

public class DefaultLoggingEvent
implements LoggingEvent {
    Logger logger;
    Level level;
    String message;
    List<Marker> markers;
    List<Object> arguments;
    List<KeyValuePair> keyValuePairs;
    Throwable throwable;
    String threadName;
    long timeStamp;
    String callerBoundary;

    public DefaultLoggingEvent(Level level, Logger logger) {
        this.logger = logger;
        this.level = level;
    }

    public void addMarker(Marker marker) {
        if (this.markers == null) {
            this.markers = new ArrayList<Marker>(2);
        }
        this.markers.add(marker);
    }

    @Override
    public List<Marker> getMarkers() {
        return this.markers;
    }

    public void addArgument(Object object) {
        this.getNonNullArguments().add(object);
    }

    public void addArguments(Object ... objectArray) {
        this.getNonNullArguments().addAll(Arrays.asList(objectArray));
    }

    private List<Object> getNonNullArguments() {
        if (this.arguments == null) {
            this.arguments = new ArrayList<Object>(3);
        }
        return this.arguments;
    }

    @Override
    public List<Object> getArguments() {
        return this.arguments;
    }

    @Override
    public Object[] getArgumentArray() {
        if (this.arguments == null) {
            return null;
        }
        return this.arguments.toArray();
    }

    public void addKeyValue(String string, Object object) {
        this.getNonnullKeyValuePairs().add(new KeyValuePair(string, object));
    }

    private List<KeyValuePair> getNonnullKeyValuePairs() {
        if (this.keyValuePairs == null) {
            this.keyValuePairs = new ArrayList<KeyValuePair>(4);
        }
        return this.keyValuePairs;
    }

    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        return this.keyValuePairs;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public String getLoggerName() {
        return this.logger.getName();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String string) {
        this.message = string;
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    @Override
    public String getThreadName() {
        return this.threadName;
    }

    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long l) {
        this.timeStamp = l;
    }

    public void setCallerBoundary(String string) {
        this.callerBoundary = string;
    }

    @Override
    public String getCallerBoundary() {
        return this.callerBoundary;
    }
}

