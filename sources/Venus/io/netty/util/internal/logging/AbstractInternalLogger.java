/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.logging;

import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ObjectStreamException;
import java.io.Serializable;

public abstract class AbstractInternalLogger
implements InternalLogger,
Serializable {
    private static final long serialVersionUID = -6382972526573193470L;
    private static final String EXCEPTION_MESSAGE = "Unexpected exception:";
    private final String name;

    protected AbstractInternalLogger(String string) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        this.name = string;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public boolean isEnabled(InternalLogLevel internalLogLevel) {
        switch (1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[internalLogLevel.ordinal()]) {
            case 1: {
                return this.isTraceEnabled();
            }
            case 2: {
                return this.isDebugEnabled();
            }
            case 3: {
                return this.isInfoEnabled();
            }
            case 4: {
                return this.isWarnEnabled();
            }
            case 5: {
                return this.isErrorEnabled();
            }
        }
        throw new Error();
    }

    @Override
    public void trace(Throwable throwable) {
        this.trace(EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void debug(Throwable throwable) {
        this.debug(EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void info(Throwable throwable) {
        this.info(EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void warn(Throwable throwable) {
        this.warn(EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void error(Throwable throwable) {
        this.error(EXCEPTION_MESSAGE, throwable);
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string, Throwable throwable) {
        switch (1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[internalLogLevel.ordinal()]) {
            case 1: {
                this.trace(string, throwable);
                break;
            }
            case 2: {
                this.debug(string, throwable);
                break;
            }
            case 3: {
                this.info(string, throwable);
                break;
            }
            case 4: {
                this.warn(string, throwable);
                break;
            }
            case 5: {
                this.error(string, throwable);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, Throwable throwable) {
        switch (1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[internalLogLevel.ordinal()]) {
            case 1: {
                this.trace(throwable);
                break;
            }
            case 2: {
                this.debug(throwable);
                break;
            }
            case 3: {
                this.info(throwable);
                break;
            }
            case 4: {
                this.warn(throwable);
                break;
            }
            case 5: {
                this.error(throwable);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string) {
        switch (1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[internalLogLevel.ordinal()]) {
            case 1: {
                this.trace(string);
                break;
            }
            case 2: {
                this.debug(string);
                break;
            }
            case 3: {
                this.info(string);
                break;
            }
            case 4: {
                this.warn(string);
                break;
            }
            case 5: {
                this.error(string);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string, Object object) {
        switch (1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[internalLogLevel.ordinal()]) {
            case 1: {
                this.trace(string, object);
                break;
            }
            case 2: {
                this.debug(string, object);
                break;
            }
            case 3: {
                this.info(string, object);
                break;
            }
            case 4: {
                this.warn(string, object);
                break;
            }
            case 5: {
                this.error(string, object);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string, Object object, Object object2) {
        switch (1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[internalLogLevel.ordinal()]) {
            case 1: {
                this.trace(string, object, object2);
                break;
            }
            case 2: {
                this.debug(string, object, object2);
                break;
            }
            case 3: {
                this.info(string, object, object2);
                break;
            }
            case 4: {
                this.warn(string, object, object2);
                break;
            }
            case 5: {
                this.error(string, object, object2);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    @Override
    public void log(InternalLogLevel internalLogLevel, String string, Object ... objectArray) {
        switch (1.$SwitchMap$io$netty$util$internal$logging$InternalLogLevel[internalLogLevel.ordinal()]) {
            case 1: {
                this.trace(string, objectArray);
                break;
            }
            case 2: {
                this.debug(string, objectArray);
                break;
            }
            case 3: {
                this.info(string, objectArray);
                break;
            }
            case 4: {
                this.warn(string, objectArray);
                break;
            }
            case 5: {
                this.error(string, objectArray);
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    protected Object readResolve() throws ObjectStreamException {
        return InternalLoggerFactory.getInstance(this.name());
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + this.name() + ')';
    }
}

