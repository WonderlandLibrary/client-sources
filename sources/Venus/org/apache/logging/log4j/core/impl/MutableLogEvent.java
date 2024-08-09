/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MutableLogEvent
implements LogEvent,
ReusableMessage {
    private static final Message EMPTY = new SimpleMessage("");
    private int threadPriority;
    private long threadId;
    private long timeMillis;
    private long nanoTime;
    private short parameterCount;
    private boolean includeLocation;
    private boolean endOfBatch = false;
    private Level level;
    private String threadName;
    private String loggerName;
    private Message message;
    private StringBuilder messageText;
    private Object[] parameters;
    private Throwable thrown;
    private ThrowableProxy thrownProxy;
    private StringMap contextData = ContextDataFactory.createContextData();
    private Marker marker;
    private String loggerFqcn;
    private StackTraceElement source;
    private ThreadContext.ContextStack contextStack;
    transient boolean reserved = false;

    public MutableLogEvent() {
        this(new StringBuilder(Constants.INITIAL_REUSABLE_MESSAGE_SIZE), new Object[10]);
    }

    public MutableLogEvent(StringBuilder stringBuilder, Object[] objectArray) {
        this.messageText = stringBuilder;
        this.parameters = objectArray;
    }

    @Override
    public Log4jLogEvent toImmutable() {
        return this.createMemento();
    }

    public void initFrom(LogEvent logEvent) {
        this.loggerFqcn = logEvent.getLoggerFqcn();
        this.marker = logEvent.getMarker();
        this.level = logEvent.getLevel();
        this.loggerName = logEvent.getLoggerName();
        this.timeMillis = logEvent.getTimeMillis();
        this.thrown = logEvent.getThrown();
        this.thrownProxy = logEvent.getThrownProxy();
        this.contextData.putAll(logEvent.getContextData());
        this.contextStack = logEvent.getContextStack();
        this.source = logEvent.isIncludeLocation() ? logEvent.getSource() : null;
        this.threadId = logEvent.getThreadId();
        this.threadName = logEvent.getThreadName();
        this.threadPriority = logEvent.getThreadPriority();
        this.endOfBatch = logEvent.isEndOfBatch();
        this.includeLocation = logEvent.isIncludeLocation();
        this.nanoTime = logEvent.getNanoTime();
        this.setMessage(logEvent.getMessage());
    }

    public void clear() {
        this.loggerFqcn = null;
        this.marker = null;
        this.level = null;
        this.loggerName = null;
        this.message = null;
        this.thrown = null;
        this.thrownProxy = null;
        this.source = null;
        if (this.contextData != null) {
            if (this.contextData.isFrozen()) {
                this.contextData = null;
            } else {
                this.contextData.clear();
            }
        }
        this.contextStack = null;
        this.trimMessageText();
        if (this.parameters != null) {
            for (int i = 0; i < this.parameters.length; ++i) {
                this.parameters[i] = null;
            }
        }
    }

    private void trimMessageText() {
        if (this.messageText != null && this.messageText.length() > Constants.MAX_REUSABLE_MESSAGE_SIZE) {
            this.messageText.setLength(Constants.MAX_REUSABLE_MESSAGE_SIZE);
            this.messageText.trimToSize();
        }
    }

    @Override
    public String getLoggerFqcn() {
        return this.loggerFqcn;
    }

    public void setLoggerFqcn(String string) {
        this.loggerFqcn = string;
    }

    @Override
    public Marker getMarker() {
        return this.marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public Level getLevel() {
        if (this.level == null) {
            this.level = Level.OFF;
        }
        return this.level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String getLoggerName() {
        return this.loggerName;
    }

    public void setLoggerName(String string) {
        this.loggerName = string;
    }

    @Override
    public Message getMessage() {
        if (this.message == null) {
            return this.messageText == null ? EMPTY : this;
        }
        return this.message;
    }

    public void setMessage(Message message) {
        if (message instanceof ReusableMessage) {
            ReusableMessage reusableMessage = (ReusableMessage)message;
            reusableMessage.formatTo(this.getMessageTextForWriting());
            if (this.parameters != null) {
                this.parameters = reusableMessage.swapParameters(this.parameters);
                this.parameterCount = reusableMessage.getParameterCount();
            }
        } else {
            if (message != null && !this.canFormatMessageInBackground(message)) {
                message.getFormattedMessage();
            }
            this.message = message;
        }
    }

    private boolean canFormatMessageInBackground(Message message) {
        return Constants.FORMAT_MESSAGES_IN_BACKGROUND || message.getClass().isAnnotationPresent(AsynchronouslyFormattable.class);
    }

    private StringBuilder getMessageTextForWriting() {
        if (this.messageText == null) {
            this.messageText = new StringBuilder(Constants.INITIAL_REUSABLE_MESSAGE_SIZE);
        }
        this.messageText.setLength(0);
        return this.messageText;
    }

    @Override
    public String getFormattedMessage() {
        return this.messageText.toString();
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return this.parameters == null ? null : Arrays.copyOf(this.parameters, (int)this.parameterCount);
    }

    @Override
    public Throwable getThrowable() {
        return this.getThrown();
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        stringBuilder.append((CharSequence)this.messageText);
    }

    @Override
    public Object[] swapParameters(Object[] objectArray) {
        Object[] objectArray2 = this.parameters;
        this.parameters = objectArray;
        return objectArray2;
    }

    @Override
    public short getParameterCount() {
        return this.parameterCount;
    }

    @Override
    public Message memento() {
        if (this.message != null) {
            return this.message;
        }
        Object[] objectArray = this.parameters == null ? new Object[]{} : Arrays.copyOf(this.parameters, (int)this.parameterCount);
        return new ParameterizedMessage(this.messageText.toString(), objectArray);
    }

    @Override
    public Throwable getThrown() {
        return this.thrown;
    }

    public void setThrown(Throwable throwable) {
        this.thrown = throwable;
    }

    @Override
    public long getTimeMillis() {
        return this.timeMillis;
    }

    public void setTimeMillis(long l) {
        this.timeMillis = l;
    }

    @Override
    public ThrowableProxy getThrownProxy() {
        if (this.thrownProxy == null && this.thrown != null) {
            this.thrownProxy = new ThrowableProxy(this.thrown);
        }
        return this.thrownProxy;
    }

    @Override
    public StackTraceElement getSource() {
        if (this.source != null) {
            return this.source;
        }
        if (this.loggerFqcn == null || !this.includeLocation) {
            return null;
        }
        this.source = Log4jLogEvent.calcLocation(this.loggerFqcn);
        return this.source;
    }

    @Override
    public ReadOnlyStringMap getContextData() {
        return this.contextData;
    }

    @Override
    public Map<String, String> getContextMap() {
        return this.contextData.toMap();
    }

    public void setContextData(StringMap stringMap) {
        this.contextData = stringMap;
    }

    @Override
    public ThreadContext.ContextStack getContextStack() {
        return this.contextStack;
    }

    public void setContextStack(ThreadContext.ContextStack contextStack) {
        this.contextStack = contextStack;
    }

    @Override
    public long getThreadId() {
        return this.threadId;
    }

    public void setThreadId(long l) {
        this.threadId = l;
    }

    @Override
    public String getThreadName() {
        return this.threadName;
    }

    public void setThreadName(String string) {
        this.threadName = string;
    }

    @Override
    public int getThreadPriority() {
        return this.threadPriority;
    }

    public void setThreadPriority(int n) {
        this.threadPriority = n;
    }

    @Override
    public boolean isIncludeLocation() {
        return this.includeLocation;
    }

    @Override
    public void setIncludeLocation(boolean bl) {
        this.includeLocation = bl;
    }

    @Override
    public boolean isEndOfBatch() {
        return this.endOfBatch;
    }

    @Override
    public void setEndOfBatch(boolean bl) {
        this.endOfBatch = bl;
    }

    @Override
    public long getNanoTime() {
        return this.nanoTime;
    }

    public void setNanoTime(long l) {
        this.nanoTime = l;
    }

    protected Object writeReplace() {
        return new Log4jLogEvent.LogEventProxy(this, this.includeLocation);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    public Log4jLogEvent createMemento() {
        return Log4jLogEvent.deserialize(Log4jLogEvent.serialize(this, this.includeLocation));
    }

    public void initializeBuilder(Log4jLogEvent.Builder builder) {
        builder.setContextData(this.contextData).setContextStack(this.contextStack).setEndOfBatch(this.endOfBatch).setIncludeLocation(this.includeLocation).setLevel(this.getLevel()).setLoggerFqcn(this.loggerFqcn).setLoggerName(this.loggerName).setMarker(this.marker).setMessage(this.getNonNullImmutableMessage()).setNanoTime(this.nanoTime).setSource(this.source).setThreadId(this.threadId).setThreadName(this.threadName).setThreadPriority(this.threadPriority).setThrown(this.getThrown()).setThrownProxy(this.thrownProxy).setTimeMillis(this.timeMillis);
    }

    private Message getNonNullImmutableMessage() {
        return this.message != null ? this.message : new SimpleMessage(String.valueOf(this.messageText));
    }

    @Override
    public LogEvent toImmutable() {
        return this.toImmutable();
    }
}

