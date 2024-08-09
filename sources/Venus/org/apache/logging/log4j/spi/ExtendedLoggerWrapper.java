/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLogger;

public class ExtendedLoggerWrapper
extends AbstractLogger {
    private static final long serialVersionUID = 1L;
    protected final ExtendedLogger logger;

    public ExtendedLoggerWrapper(ExtendedLogger extendedLogger, String string, MessageFactory messageFactory) {
        super(string, messageFactory);
        this.logger = extendedLogger;
    }

    @Override
    public Level getLevel() {
        return this.logger.getLevel();
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable throwable) {
        return this.logger.isEnabled(level, marker, message, throwable);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
        return this.logger.isEnabled(level, marker, charSequence, throwable);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object object, Throwable throwable) {
        return this.logger.isEnabled(level, marker, object, throwable);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string) {
        return this.logger.isEnabled(level, marker, string);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object ... objectArray) {
        return this.logger.isEnabled(level, marker, string, objectArray);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object) {
        return this.logger.isEnabled(level, marker, string, object);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2) {
        return this.logger.isEnabled(level, marker, string, object, object2);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.logger.isEnabled(level, marker, string, object, object2, object3);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.logger.isEnabled(level, marker, string, object, object2, object3, object4);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.logger.isEnabled(level, marker, string, object, object2, object3, object4, object5);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.logger.isEnabled(level, marker, string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.logger.isEnabled(level, marker, string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.logger.isEnabled(level, marker, string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.logger.isEnabled(level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.logger.isEnabled(level, marker, string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Throwable throwable) {
        return this.logger.isEnabled(level, marker, string, throwable);
    }

    @Override
    public void logMessage(String string, Level level, Marker marker, Message message, Throwable throwable) {
        this.logger.logMessage(string, level, marker, message, throwable);
    }
}

