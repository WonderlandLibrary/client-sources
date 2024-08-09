/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.ContextDataInjector;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.async.RingBufferLogEvent;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.core.impl.ContextDataInjectorFactory;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.ClockFactory;
import org.apache.logging.log4j.core.util.DummyNanoClock;
import org.apache.logging.log4j.core.util.NanoClock;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.message.TimestampMessage;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.StringMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Log4jLogEvent
implements LogEvent {
    private static final long serialVersionUID = -8393305700508709443L;
    private static final Clock CLOCK = ClockFactory.getClock();
    private static volatile NanoClock nanoClock = new DummyNanoClock();
    private static final ContextDataInjector CONTEXT_DATA_INJECTOR = ContextDataInjectorFactory.createInjector();
    private final String loggerFqcn;
    private final Marker marker;
    private final Level level;
    private final String loggerName;
    private Message message;
    private final long timeMillis;
    private final transient Throwable thrown;
    private ThrowableProxy thrownProxy;
    private final StringMap contextData;
    private final ThreadContext.ContextStack contextStack;
    private long threadId;
    private String threadName;
    private int threadPriority;
    private StackTraceElement source;
    private boolean includeLocation;
    private boolean endOfBatch = false;
    private final transient long nanoTime;

    public static Builder newBuilder() {
        return new Builder();
    }

    public Log4jLogEvent() {
        this("", null, "", null, null, null, null, null, null, 0L, null, 0, null, CLOCK.currentTimeMillis(), nanoClock.nanoTime());
    }

    @Deprecated
    public Log4jLogEvent(long l) {
        this("", null, "", null, null, null, null, null, null, 0L, null, 0, null, l, nanoClock.nanoTime());
    }

    @Deprecated
    public Log4jLogEvent(String string, Marker marker, String string2, Level level, Message message, Throwable throwable) {
        this(string, marker, string2, level, message, null, throwable);
    }

    public Log4jLogEvent(String string, Marker marker, String string2, Level level, Message message, List<Property> list, Throwable throwable) {
        this(string, marker, string2, level, message, throwable, null, Log4jLogEvent.createContextData(list), ThreadContext.getDepth() == 0 ? null : ThreadContext.cloneStack(), 0L, null, 0, null, message instanceof TimestampMessage ? ((TimestampMessage)((Object)message)).getTimestamp() : CLOCK.currentTimeMillis(), nanoClock.nanoTime());
    }

    @Deprecated
    public Log4jLogEvent(String string, Marker marker, String string2, Level level, Message message, Throwable throwable, Map<String, String> map, ThreadContext.ContextStack contextStack, String string3, StackTraceElement stackTraceElement, long l) {
        this(string, marker, string2, level, message, throwable, null, Log4jLogEvent.createContextData(map), contextStack, 0L, string3, 0, stackTraceElement, l, nanoClock.nanoTime());
    }

    @Deprecated
    public static Log4jLogEvent createEvent(String string, Marker marker, String string2, Level level, Message message, Throwable throwable, ThrowableProxy throwableProxy, Map<String, String> map, ThreadContext.ContextStack contextStack, String string3, StackTraceElement stackTraceElement, long l) {
        Log4jLogEvent log4jLogEvent = new Log4jLogEvent(string, marker, string2, level, message, throwable, throwableProxy, Log4jLogEvent.createContextData(map), contextStack, 0L, string3, 0, stackTraceElement, l, nanoClock.nanoTime());
        return log4jLogEvent;
    }

    private Log4jLogEvent(String string, Marker marker, String string2, Level level, Message message, Throwable throwable, ThrowableProxy throwableProxy, StringMap stringMap, ThreadContext.ContextStack contextStack, long l, String string3, int n, StackTraceElement stackTraceElement, long l2, long l3) {
        this.loggerName = string;
        this.marker = marker;
        this.loggerFqcn = string2;
        this.level = level == null ? Level.OFF : level;
        this.message = message;
        this.thrown = throwable;
        this.thrownProxy = throwableProxy;
        this.contextData = stringMap == null ? ContextDataFactory.createContextData() : stringMap;
        this.contextStack = contextStack == null ? ThreadContext.EMPTY_STACK : contextStack;
        this.timeMillis = message instanceof TimestampMessage ? ((TimestampMessage)((Object)message)).getTimestamp() : l2;
        this.threadId = l;
        this.threadName = string3;
        this.threadPriority = n;
        this.source = stackTraceElement;
        if (message != null && message instanceof LoggerNameAwareMessage) {
            ((LoggerNameAwareMessage)((Object)message)).setLoggerName(string);
        }
        this.nanoTime = l3;
    }

    private static StringMap createContextData(Map<String, String> map) {
        StringMap stringMap = ContextDataFactory.createContextData();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringMap.putValue(entry.getKey(), entry.getValue());
            }
        }
        return stringMap;
    }

    private static StringMap createContextData(List<Property> list) {
        StringMap stringMap = ContextDataFactory.createContextData();
        return CONTEXT_DATA_INJECTOR.injectContextData(list, stringMap);
    }

    public static NanoClock getNanoClock() {
        return nanoClock;
    }

    public static void setNanoClock(NanoClock nanoClock) {
        Log4jLogEvent.nanoClock = Objects.requireNonNull(nanoClock, "NanoClock must be non-null");
        StatusLogger.getLogger().trace("Using {} for nanosecond timestamps.", (Object)nanoClock.getClass().getSimpleName());
    }

    public Builder asBuilder() {
        return new Builder(this);
    }

    @Override
    public Log4jLogEvent toImmutable() {
        if (this.getMessage() instanceof ReusableMessage) {
            this.makeMessageImmutable();
        }
        return this;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public String getLoggerName() {
        return this.loggerName;
    }

    @Override
    public Message getMessage() {
        return this.message;
    }

    public void makeMessageImmutable() {
        this.message = new SimpleMessage(this.message.getFormattedMessage());
    }

    @Override
    public long getThreadId() {
        if (this.threadId == 0L) {
            this.threadId = Thread.currentThread().getId();
        }
        return this.threadId;
    }

    @Override
    public String getThreadName() {
        if (this.threadName == null) {
            this.threadName = Thread.currentThread().getName();
        }
        return this.threadName;
    }

    @Override
    public int getThreadPriority() {
        if (this.threadPriority == 0) {
            this.threadPriority = Thread.currentThread().getPriority();
        }
        return this.threadPriority;
    }

    @Override
    public long getTimeMillis() {
        return this.timeMillis;
    }

    @Override
    public Throwable getThrown() {
        return this.thrown;
    }

    @Override
    public ThrowableProxy getThrownProxy() {
        if (this.thrownProxy == null && this.thrown != null) {
            this.thrownProxy = new ThrowableProxy(this.thrown);
        }
        return this.thrownProxy;
    }

    @Override
    public Marker getMarker() {
        return this.marker;
    }

    @Override
    public String getLoggerFqcn() {
        return this.loggerFqcn;
    }

    @Override
    public ReadOnlyStringMap getContextData() {
        return this.contextData;
    }

    @Override
    public Map<String, String> getContextMap() {
        return this.contextData.toMap();
    }

    @Override
    public ThreadContext.ContextStack getContextStack() {
        return this.contextStack;
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

    public static StackTraceElement calcLocation(String string) {
        if (string == null) {
            return null;
        }
        StackTraceElement[] stackTraceElementArray = new Throwable().getStackTrace();
        StackTraceElement stackTraceElement = null;
        for (int i = stackTraceElementArray.length - 1; i > 0; --i) {
            String string2 = stackTraceElementArray[i].getClassName();
            if (string.equals(string2)) {
                return stackTraceElement;
            }
            stackTraceElement = stackTraceElementArray[i];
        }
        return null;
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

    protected Object writeReplace() {
        this.getThrownProxy();
        return new LogEventProxy(this, this.includeLocation);
    }

    public static Serializable serialize(LogEvent logEvent, boolean bl) {
        if (logEvent instanceof Log4jLogEvent) {
            logEvent.getThrownProxy();
            return new LogEventProxy((Log4jLogEvent)logEvent, bl);
        }
        return new LogEventProxy(logEvent, bl);
    }

    public static Serializable serialize(Log4jLogEvent log4jLogEvent, boolean bl) {
        log4jLogEvent.getThrownProxy();
        return new LogEventProxy(log4jLogEvent, bl);
    }

    public static boolean canDeserialize(Serializable serializable) {
        return serializable instanceof LogEventProxy;
    }

    public static Log4jLogEvent deserialize(Serializable serializable) {
        Objects.requireNonNull(serializable, "Event cannot be null");
        if (serializable instanceof LogEventProxy) {
            LogEventProxy logEventProxy = (LogEventProxy)serializable;
            Log4jLogEvent log4jLogEvent = new Log4jLogEvent(LogEventProxy.access$900(logEventProxy), LogEventProxy.access$1000(logEventProxy), LogEventProxy.access$1100(logEventProxy), LogEventProxy.access$1200(logEventProxy), LogEventProxy.access$1300(logEventProxy), LogEventProxy.access$1400(logEventProxy), LogEventProxy.access$1500(logEventProxy), LogEventProxy.access$1600(logEventProxy), LogEventProxy.access$1700(logEventProxy), LogEventProxy.access$1800(logEventProxy), LogEventProxy.access$1900(logEventProxy), LogEventProxy.access$2000(logEventProxy), LogEventProxy.access$2100(logEventProxy), LogEventProxy.access$2200(logEventProxy), LogEventProxy.access$2300(logEventProxy));
            log4jLogEvent.setEndOfBatch(LogEventProxy.access$2400(logEventProxy));
            log4jLogEvent.setIncludeLocation(LogEventProxy.access$2500(logEventProxy));
            return log4jLogEvent;
        }
        throw new IllegalArgumentException("Event is not a serialized LogEvent: " + serializable.toString());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    public LogEvent createMemento() {
        return Log4jLogEvent.createMemento(this);
    }

    public static LogEvent createMemento(LogEvent logEvent) {
        return new Builder(logEvent).build();
    }

    public static Log4jLogEvent createMemento(LogEvent logEvent, boolean bl) {
        return Log4jLogEvent.deserialize(Log4jLogEvent.serialize(logEvent, bl));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.loggerName.isEmpty() ? "root" : this.loggerName;
        stringBuilder.append("Logger=").append(string);
        stringBuilder.append(" Level=").append(this.level.name());
        stringBuilder.append(" Message=").append(this.message == null ? null : this.message.getFormattedMessage());
        return stringBuilder.toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        Log4jLogEvent log4jLogEvent = (Log4jLogEvent)object;
        if (this.endOfBatch != log4jLogEvent.endOfBatch) {
            return true;
        }
        if (this.includeLocation != log4jLogEvent.includeLocation) {
            return true;
        }
        if (this.timeMillis != log4jLogEvent.timeMillis) {
            return true;
        }
        if (this.nanoTime != log4jLogEvent.nanoTime) {
            return true;
        }
        if (this.loggerFqcn != null ? !this.loggerFqcn.equals(log4jLogEvent.loggerFqcn) : log4jLogEvent.loggerFqcn != null) {
            return true;
        }
        if (this.level != null ? !this.level.equals(log4jLogEvent.level) : log4jLogEvent.level != null) {
            return true;
        }
        if (this.source != null ? !this.source.equals(log4jLogEvent.source) : log4jLogEvent.source != null) {
            return true;
        }
        if (this.marker != null ? !this.marker.equals(log4jLogEvent.marker) : log4jLogEvent.marker != null) {
            return true;
        }
        if (this.contextData != null ? !this.contextData.equals(log4jLogEvent.contextData) : log4jLogEvent.contextData != null) {
            return true;
        }
        if (!this.message.equals(log4jLogEvent.message)) {
            return true;
        }
        if (!this.loggerName.equals(log4jLogEvent.loggerName)) {
            return true;
        }
        if (this.contextStack != null ? !this.contextStack.equals(log4jLogEvent.contextStack) : log4jLogEvent.contextStack != null) {
            return true;
        }
        if (this.threadId != log4jLogEvent.threadId) {
            return true;
        }
        if (this.threadName != null ? !this.threadName.equals(log4jLogEvent.threadName) : log4jLogEvent.threadName != null) {
            return true;
        }
        if (this.threadPriority != log4jLogEvent.threadPriority) {
            return true;
        }
        if (this.thrown != null ? !this.thrown.equals(log4jLogEvent.thrown) : log4jLogEvent.thrown != null) {
            return true;
        }
        return this.thrownProxy != null ? !this.thrownProxy.equals(log4jLogEvent.thrownProxy) : log4jLogEvent.thrownProxy != null;
    }

    public int hashCode() {
        int n = this.loggerFqcn != null ? this.loggerFqcn.hashCode() : 0;
        n = 31 * n + (this.marker != null ? this.marker.hashCode() : 0);
        n = 31 * n + (this.level != null ? this.level.hashCode() : 0);
        n = 31 * n + this.loggerName.hashCode();
        n = 31 * n + this.message.hashCode();
        n = 31 * n + (int)(this.timeMillis ^ this.timeMillis >>> 32);
        n = 31 * n + (int)(this.nanoTime ^ this.nanoTime >>> 32);
        n = 31 * n + (this.thrown != null ? this.thrown.hashCode() : 0);
        n = 31 * n + (this.thrownProxy != null ? this.thrownProxy.hashCode() : 0);
        n = 31 * n + (this.contextData != null ? this.contextData.hashCode() : 0);
        n = 31 * n + (this.contextStack != null ? this.contextStack.hashCode() : 0);
        n = 31 * n + (int)(this.threadId ^ this.threadId >>> 32);
        n = 31 * n + (this.threadName != null ? this.threadName.hashCode() : 0);
        n = 31 * n + (this.threadPriority ^ this.threadPriority >>> 32);
        n = 31 * n + (this.source != null ? this.source.hashCode() : 0);
        n = 31 * n + (this.includeLocation ? 1 : 0);
        n = 31 * n + (this.endOfBatch ? 1 : 0);
        return n;
    }

    @Override
    public LogEvent toImmutable() {
        return this.toImmutable();
    }

    static Clock access$000() {
        return CLOCK;
    }

    static StringMap access$100(List list) {
        return Log4jLogEvent.createContextData(list);
    }

    static StringMap access$200(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.contextData;
    }

    static ThrowableProxy access$300(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.thrownProxy;
    }

    static StackTraceElement access$400(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.source;
    }

    static long access$500(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.threadId;
    }

    static String access$600(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.threadName;
    }

    static int access$700(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.threadPriority;
    }

    Log4jLogEvent(String string, Marker marker, String string2, Level level, Message message, Throwable throwable, ThrowableProxy throwableProxy, StringMap stringMap, ThreadContext.ContextStack contextStack, long l, String string3, int n, StackTraceElement stackTraceElement, long l2, long l3, 1 var19_16) {
        this(string, marker, string2, level, message, throwable, throwableProxy, stringMap, contextStack, l, string3, n, stackTraceElement, l2, l3);
    }

    static String access$2600(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.loggerFqcn;
    }

    static Marker access$2700(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.marker;
    }

    static Level access$2800(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.level;
    }

    static String access$2900(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.loggerName;
    }

    static Message access$3000(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.message;
    }

    static long access$3100(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.timeMillis;
    }

    static Throwable access$3200(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.thrown;
    }

    static ThreadContext.ContextStack access$3300(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.contextStack;
    }

    static boolean access$3400(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.endOfBatch;
    }

    static long access$3500(Log4jLogEvent log4jLogEvent) {
        return log4jLogEvent.nanoTime;
    }

    static class 1 {
    }

    static class LogEventProxy
    implements Serializable {
        private static final long serialVersionUID = -8634075037355293699L;
        private final String loggerFQCN;
        private final Marker marker;
        private final Level level;
        private final String loggerName;
        private final transient Message message;
        private MarshalledObject<Message> marshalledMessage;
        private String messageString;
        private final long timeMillis;
        private final transient Throwable thrown;
        private final ThrowableProxy thrownProxy;
        private final StringMap contextData;
        private final ThreadContext.ContextStack contextStack;
        private final long threadId;
        private final String threadName;
        private final int threadPriority;
        private final StackTraceElement source;
        private final boolean isLocationRequired;
        private final boolean isEndOfBatch;
        private final transient long nanoTime;

        public LogEventProxy(Log4jLogEvent log4jLogEvent, boolean bl) {
            this.loggerFQCN = Log4jLogEvent.access$2600(log4jLogEvent);
            this.marker = Log4jLogEvent.access$2700(log4jLogEvent);
            this.level = Log4jLogEvent.access$2800(log4jLogEvent);
            this.loggerName = Log4jLogEvent.access$2900(log4jLogEvent);
            this.message = Log4jLogEvent.access$3000(log4jLogEvent) instanceof ReusableMessage ? LogEventProxy.memento((ReusableMessage)Log4jLogEvent.access$3000(log4jLogEvent)) : Log4jLogEvent.access$3000(log4jLogEvent);
            this.timeMillis = Log4jLogEvent.access$3100(log4jLogEvent);
            this.thrown = Log4jLogEvent.access$3200(log4jLogEvent);
            this.thrownProxy = Log4jLogEvent.access$300(log4jLogEvent);
            this.contextData = Log4jLogEvent.access$200(log4jLogEvent);
            this.contextStack = Log4jLogEvent.access$3300(log4jLogEvent);
            this.source = bl ? log4jLogEvent.getSource() : null;
            this.threadId = log4jLogEvent.getThreadId();
            this.threadName = log4jLogEvent.getThreadName();
            this.threadPriority = log4jLogEvent.getThreadPriority();
            this.isLocationRequired = bl;
            this.isEndOfBatch = Log4jLogEvent.access$3400(log4jLogEvent);
            this.nanoTime = Log4jLogEvent.access$3500(log4jLogEvent);
        }

        public LogEventProxy(LogEvent logEvent, boolean bl) {
            this.loggerFQCN = logEvent.getLoggerFqcn();
            this.marker = logEvent.getMarker();
            this.level = logEvent.getLevel();
            this.loggerName = logEvent.getLoggerName();
            Message message = logEvent.getMessage();
            this.message = message instanceof ReusableMessage ? LogEventProxy.memento((ReusableMessage)message) : message;
            this.timeMillis = logEvent.getTimeMillis();
            this.thrown = logEvent.getThrown();
            this.thrownProxy = logEvent.getThrownProxy();
            this.contextData = LogEventProxy.memento(logEvent.getContextData());
            this.contextStack = logEvent.getContextStack();
            this.source = bl ? logEvent.getSource() : null;
            this.threadId = logEvent.getThreadId();
            this.threadName = logEvent.getThreadName();
            this.threadPriority = logEvent.getThreadPriority();
            this.isLocationRequired = bl;
            this.isEndOfBatch = logEvent.isEndOfBatch();
            this.nanoTime = logEvent.getNanoTime();
        }

        private static Message memento(ReusableMessage reusableMessage) {
            return reusableMessage.memento();
        }

        private static StringMap memento(ReadOnlyStringMap readOnlyStringMap) {
            StringMap stringMap = ContextDataFactory.createContextData();
            stringMap.putAll(readOnlyStringMap);
            return stringMap;
        }

        private static MarshalledObject<Message> marshall(Message message) {
            try {
                return new MarshalledObject<Message>(message);
            } catch (Exception exception) {
                return null;
            }
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            this.messageString = this.message.getFormattedMessage();
            this.marshalledMessage = LogEventProxy.marshall(this.message);
            objectOutputStream.defaultWriteObject();
        }

        protected Object readResolve() {
            Log4jLogEvent log4jLogEvent = new Log4jLogEvent(this.loggerName, this.marker, this.loggerFQCN, this.level, this.message(), this.thrown, this.thrownProxy, this.contextData, this.contextStack, this.threadId, this.threadName, this.threadPriority, this.source, this.timeMillis, this.nanoTime, null);
            log4jLogEvent.setEndOfBatch(this.isEndOfBatch);
            log4jLogEvent.setIncludeLocation(this.isLocationRequired);
            return log4jLogEvent;
        }

        private Message message() {
            if (this.marshalledMessage != null) {
                try {
                    return this.marshalledMessage.get();
                } catch (Exception exception) {
                    // empty catch block
                }
            }
            return new SimpleMessage(this.messageString);
        }

        static String access$900(LogEventProxy logEventProxy) {
            return logEventProxy.loggerName;
        }

        static Marker access$1000(LogEventProxy logEventProxy) {
            return logEventProxy.marker;
        }

        static String access$1100(LogEventProxy logEventProxy) {
            return logEventProxy.loggerFQCN;
        }

        static Level access$1200(LogEventProxy logEventProxy) {
            return logEventProxy.level;
        }

        static Message access$1300(LogEventProxy logEventProxy) {
            return logEventProxy.message;
        }

        static Throwable access$1400(LogEventProxy logEventProxy) {
            return logEventProxy.thrown;
        }

        static ThrowableProxy access$1500(LogEventProxy logEventProxy) {
            return logEventProxy.thrownProxy;
        }

        static StringMap access$1600(LogEventProxy logEventProxy) {
            return logEventProxy.contextData;
        }

        static ThreadContext.ContextStack access$1700(LogEventProxy logEventProxy) {
            return logEventProxy.contextStack;
        }

        static long access$1800(LogEventProxy logEventProxy) {
            return logEventProxy.threadId;
        }

        static String access$1900(LogEventProxy logEventProxy) {
            return logEventProxy.threadName;
        }

        static int access$2000(LogEventProxy logEventProxy) {
            return logEventProxy.threadPriority;
        }

        static StackTraceElement access$2100(LogEventProxy logEventProxy) {
            return logEventProxy.source;
        }

        static long access$2200(LogEventProxy logEventProxy) {
            return logEventProxy.timeMillis;
        }

        static long access$2300(LogEventProxy logEventProxy) {
            return logEventProxy.nanoTime;
        }

        static boolean access$2400(LogEventProxy logEventProxy) {
            return logEventProxy.isEndOfBatch;
        }

        static boolean access$2500(LogEventProxy logEventProxy) {
            return logEventProxy.isLocationRequired;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<LogEvent> {
        private String loggerFqcn;
        private Marker marker;
        private Level level;
        private String loggerName;
        private Message message;
        private Throwable thrown;
        private long timeMillis = Log4jLogEvent.access$000().currentTimeMillis();
        private ThrowableProxy thrownProxy;
        private StringMap contextData = Log4jLogEvent.access$100(null);
        private ThreadContext.ContextStack contextStack = ThreadContext.getImmutableStack();
        private long threadId;
        private String threadName;
        private int threadPriority;
        private StackTraceElement source;
        private boolean includeLocation;
        private boolean endOfBatch = false;
        private long nanoTime;

        public Builder() {
        }

        public Builder(LogEvent logEvent) {
            Objects.requireNonNull(logEvent);
            if (logEvent instanceof RingBufferLogEvent) {
                ((RingBufferLogEvent)logEvent).initializeBuilder(this);
                return;
            }
            if (logEvent instanceof MutableLogEvent) {
                ((MutableLogEvent)logEvent).initializeBuilder(this);
                return;
            }
            this.loggerFqcn = logEvent.getLoggerFqcn();
            this.marker = logEvent.getMarker();
            this.level = logEvent.getLevel();
            this.loggerName = logEvent.getLoggerName();
            this.message = logEvent.getMessage();
            this.timeMillis = logEvent.getTimeMillis();
            this.thrown = logEvent.getThrown();
            this.contextStack = logEvent.getContextStack();
            this.includeLocation = logEvent.isIncludeLocation();
            this.endOfBatch = logEvent.isEndOfBatch();
            this.nanoTime = logEvent.getNanoTime();
            if (logEvent instanceof Log4jLogEvent) {
                Log4jLogEvent log4jLogEvent = (Log4jLogEvent)logEvent;
                this.contextData = Log4jLogEvent.access$200(log4jLogEvent);
                this.thrownProxy = Log4jLogEvent.access$300(log4jLogEvent);
                this.source = Log4jLogEvent.access$400(log4jLogEvent);
                this.threadId = Log4jLogEvent.access$500(log4jLogEvent);
                this.threadName = Log4jLogEvent.access$600(log4jLogEvent);
                this.threadPriority = Log4jLogEvent.access$700(log4jLogEvent);
            } else {
                if (logEvent.getContextData() instanceof StringMap) {
                    this.contextData = (StringMap)logEvent.getContextData();
                } else {
                    if (this.contextData.isFrozen()) {
                        this.contextData = ContextDataFactory.createContextData();
                    } else {
                        this.contextData.clear();
                    }
                    this.contextData.putAll(logEvent.getContextData());
                }
                this.thrownProxy = logEvent.getThrownProxy();
                this.source = logEvent.getSource();
                this.threadId = logEvent.getThreadId();
                this.threadName = logEvent.getThreadName();
                this.threadPriority = logEvent.getThreadPriority();
            }
        }

        public Builder setLevel(Level level) {
            this.level = level;
            return this;
        }

        public Builder setLoggerFqcn(String string) {
            this.loggerFqcn = string;
            return this;
        }

        public Builder setLoggerName(String string) {
            this.loggerName = string;
            return this;
        }

        public Builder setMarker(Marker marker) {
            this.marker = marker;
            return this;
        }

        public Builder setMessage(Message message) {
            this.message = message;
            return this;
        }

        public Builder setThrown(Throwable throwable) {
            this.thrown = throwable;
            return this;
        }

        public Builder setTimeMillis(long l) {
            this.timeMillis = l;
            return this;
        }

        public Builder setThrownProxy(ThrowableProxy throwableProxy) {
            this.thrownProxy = throwableProxy;
            return this;
        }

        @Deprecated
        public Builder setContextMap(Map<String, String> map) {
            this.contextData = ContextDataFactory.createContextData();
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    this.contextData.putValue(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public Builder setContextData(StringMap stringMap) {
            this.contextData = stringMap;
            return this;
        }

        public Builder setContextStack(ThreadContext.ContextStack contextStack) {
            this.contextStack = contextStack;
            return this;
        }

        public Builder setThreadId(long l) {
            this.threadId = l;
            return this;
        }

        public Builder setThreadName(String string) {
            this.threadName = string;
            return this;
        }

        public Builder setThreadPriority(int n) {
            this.threadPriority = n;
            return this;
        }

        public Builder setSource(StackTraceElement stackTraceElement) {
            this.source = stackTraceElement;
            return this;
        }

        public Builder setIncludeLocation(boolean bl) {
            this.includeLocation = bl;
            return this;
        }

        public Builder setEndOfBatch(boolean bl) {
            this.endOfBatch = bl;
            return this;
        }

        public Builder setNanoTime(long l) {
            this.nanoTime = l;
            return this;
        }

        @Override
        public Log4jLogEvent build() {
            Log4jLogEvent log4jLogEvent = new Log4jLogEvent(this.loggerName, this.marker, this.loggerFqcn, this.level, this.message, this.thrown, this.thrownProxy, this.contextData, this.contextStack, this.threadId, this.threadName, this.threadPriority, this.source, this.timeMillis, this.nanoTime, null);
            log4jLogEvent.setIncludeLocation(this.includeLocation);
            log4jLogEvent.setEndOfBatch(this.endOfBatch);
            return log4jLogEvent;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

