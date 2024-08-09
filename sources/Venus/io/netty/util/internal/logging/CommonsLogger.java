/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.logging;

import io.netty.util.internal.logging.AbstractInternalLogger;
import io.netty.util.internal.logging.FormattingTuple;
import io.netty.util.internal.logging.MessageFormatter;
import org.apache.commons.logging.Log;

@Deprecated
class CommonsLogger
extends AbstractInternalLogger {
    private static final long serialVersionUID = 8647838678388394885L;
    private final transient Log logger;

    CommonsLogger(Log log2, String string) {
        super(string);
        if (log2 == null) {
            throw new NullPointerException("logger");
        }
        this.logger = log2;
    }

    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    @Override
    public void trace(String string) {
        this.logger.trace(string);
    }

    @Override
    public void trace(String string, Object object) {
        if (this.logger.isTraceEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object);
            this.logger.trace(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void trace(String string, Object object, Object object2) {
        if (this.logger.isTraceEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object, object2);
            this.logger.trace(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void trace(String string, Object ... objectArray) {
        if (this.logger.isTraceEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(string, objectArray);
            this.logger.trace(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void trace(String string, Throwable throwable) {
        this.logger.trace(string, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    @Override
    public void debug(String string) {
        this.logger.debug(string);
    }

    @Override
    public void debug(String string, Object object) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object);
            this.logger.debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void debug(String string, Object object, Object object2) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object, object2);
            this.logger.debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void debug(String string, Object ... objectArray) {
        if (this.logger.isDebugEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(string, objectArray);
            this.logger.debug(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void debug(String string, Throwable throwable) {
        this.logger.debug(string, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    @Override
    public void info(String string) {
        this.logger.info(string);
    }

    @Override
    public void info(String string, Object object) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object);
            this.logger.info(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void info(String string, Object object, Object object2) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object, object2);
            this.logger.info(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void info(String string, Object ... objectArray) {
        if (this.logger.isInfoEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(string, objectArray);
            this.logger.info(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void info(String string, Throwable throwable) {
        this.logger.info(string, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    @Override
    public void warn(String string) {
        this.logger.warn(string);
    }

    @Override
    public void warn(String string, Object object) {
        if (this.logger.isWarnEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object);
            this.logger.warn(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void warn(String string, Object object, Object object2) {
        if (this.logger.isWarnEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object, object2);
            this.logger.warn(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void warn(String string, Object ... objectArray) {
        if (this.logger.isWarnEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(string, objectArray);
            this.logger.warn(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void warn(String string, Throwable throwable) {
        this.logger.warn(string, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

    @Override
    public void error(String string) {
        this.logger.error(string);
    }

    @Override
    public void error(String string, Object object) {
        if (this.logger.isErrorEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object);
            this.logger.error(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void error(String string, Object object, Object object2) {
        if (this.logger.isErrorEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.format(string, object, object2);
            this.logger.error(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void error(String string, Object ... objectArray) {
        if (this.logger.isErrorEnabled()) {
            FormattingTuple formattingTuple = MessageFormatter.arrayFormat(string, objectArray);
            this.logger.error(formattingTuple.getMessage(), formattingTuple.getThrowable());
        }
    }

    @Override
    public void error(String string, Throwable throwable) {
        this.logger.error(string, throwable);
    }
}

