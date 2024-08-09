/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.logging;

import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

class Log4J2Logger
extends ExtendedLoggerWrapper
implements InternalLogger {
    private static final long serialVersionUID = 5485418394879791397L;
    private static final String EXCEPTION_MESSAGE = "Unexpected exception:";

    Log4J2Logger(Logger logger) {
        super((ExtendedLogger)logger, logger.getName(), (MessageFactory)logger.getMessageFactory());
    }

    @Override
    public String name() {
        return this.getName();
    }

    @Override
    public void trace(Throwable throwable) {
        this.log(Level.TRACE, EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void debug(Throwable throwable) {
        this.log(Level.DEBUG, EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void info(Throwable throwable) {
        this.log(Level.INFO, EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void warn(Throwable throwable) {
        this.log(Level.WARN, EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void error(Throwable throwable) {
        this.log(Level.ERROR, EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public boolean isEnabled(InternalLogLevel internalLogLevel) {
        return this.isEnabled(this.toLevel(internalLogLevel));
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string) {
        this.log(this.toLevel(internalLogLevel), string);
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string, Object object) {
        this.log(this.toLevel(internalLogLevel), string, object);
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string, Object object, Object object2) {
        this.log(this.toLevel(internalLogLevel), string, object, object2);
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string, Object ... objectArray) {
        this.log(this.toLevel(internalLogLevel), string, objectArray);
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string, Throwable throwable) {
        this.log(this.toLevel(internalLogLevel), string, throwable);
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, Throwable throwable) {
        this.log(this.toLevel(internalLogLevel), EXCEPTION_MESSAGE, throwable);
    }

    protected Level toLevel(InternalLogLevel internalLogLevel) {
        switch (1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[internalLogLevel.ordinal()]) {
            case 1: {
                return Level.INFO;
            }
            case 2: {
                return Level.DEBUG;
            }
            case 3: {
                return Level.WARN;
            }
            case 4: {
                return Level.ERROR;
            }
            case 5: {
                return Level.TRACE;
            }
        }
        throw new Error();
    }
}

