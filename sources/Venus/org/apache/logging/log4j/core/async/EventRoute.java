/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AsyncAppender;
import org.apache.logging.log4j.core.async.AsyncLogger;
import org.apache.logging.log4j.core.async.AsyncLoggerConfig;
import org.apache.logging.log4j.message.Message;

public enum EventRoute {
    ENQUEUE{

        @Override
        public void logMessage(AsyncLogger asyncLogger, String string, Level level, Marker marker, Message message, Throwable throwable) {
        }

        @Override
        public void logMessage(AsyncLoggerConfig asyncLoggerConfig, LogEvent logEvent) {
            asyncLoggerConfig.callAppendersInBackgroundThread(logEvent);
        }

        @Override
        public void logMessage(AsyncAppender asyncAppender, LogEvent logEvent) {
            asyncAppender.logMessageInBackgroundThread(logEvent);
        }
    }
    ,
    SYNCHRONOUS{

        @Override
        public void logMessage(AsyncLogger asyncLogger, String string, Level level, Marker marker, Message message, Throwable throwable) {
        }

        @Override
        public void logMessage(AsyncLoggerConfig asyncLoggerConfig, LogEvent logEvent) {
            asyncLoggerConfig.callAppendersInCurrentThread(logEvent);
        }

        @Override
        public void logMessage(AsyncAppender asyncAppender, LogEvent logEvent) {
            asyncAppender.logMessageInCurrentThread(logEvent);
        }
    }
    ,
    DISCARD{

        @Override
        public void logMessage(AsyncLogger asyncLogger, String string, Level level, Marker marker, Message message, Throwable throwable) {
        }

        @Override
        public void logMessage(AsyncLoggerConfig asyncLoggerConfig, LogEvent logEvent) {
        }

        @Override
        public void logMessage(AsyncAppender asyncAppender, LogEvent logEvent) {
        }
    };


    private EventRoute() {
    }

    public abstract void logMessage(AsyncLogger var1, String var2, Level var3, Marker var4, Message var5, Throwable var6);

    public abstract void logMessage(AsyncLoggerConfig var1, LogEvent var2);

    public abstract void logMessage(AsyncAppender var1, LogEvent var2);

    EventRoute(1 var3_3) {
        this();
    }
}

