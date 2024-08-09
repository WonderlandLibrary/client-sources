/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.AbstractLogEvent;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.async.ArrayBlockingQueueFactory;
import org.apache.logging.log4j.core.async.AsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.AsyncQueueFullPolicyFactory;
import org.apache.logging.log4j.core.async.BlockingQueueFactory;
import org.apache.logging.log4j.core.async.DiscardingAsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.EventRoute;
import org.apache.logging.log4j.core.config.AppenderControl;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.Log4jThread;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.apache.logging.log4j.message.Message;

@Plugin(name="Async", category="Core", elementType="appender", printObject=true)
public final class AsyncAppender
extends AbstractAppender {
    private static final int DEFAULT_QUEUE_SIZE = 128;
    private static final LogEvent SHUTDOWN_LOG_EVENT = new AbstractLogEvent(){};
    private static final AtomicLong THREAD_SEQUENCE = new AtomicLong(1L);
    private final BlockingQueue<LogEvent> queue;
    private final int queueSize;
    private final boolean blocking;
    private final long shutdownTimeout;
    private final Configuration config;
    private final AppenderRef[] appenderRefs;
    private final String errorRef;
    private final boolean includeLocation;
    private AppenderControl errorAppender;
    private AsyncThread thread;
    private AsyncQueueFullPolicy asyncQueueFullPolicy;

    private AsyncAppender(String string, Filter filter, AppenderRef[] appenderRefArray, String string2, int n, boolean bl, boolean bl2, long l, Configuration configuration, boolean bl3, BlockingQueueFactory<LogEvent> blockingQueueFactory) {
        super(string, filter, null, bl2);
        this.queue = blockingQueueFactory.create(n);
        this.queueSize = n;
        this.blocking = bl;
        this.shutdownTimeout = l;
        this.config = configuration;
        this.appenderRefs = appenderRefArray;
        this.errorRef = string2;
        this.includeLocation = bl3;
    }

    @Override
    public void start() {
        Map<String, Appender> map = this.config.getAppenders();
        ArrayList<AppenderControl> arrayList = new ArrayList<AppenderControl>();
        for (AppenderRef appenderRef : this.appenderRefs) {
            Appender appender = map.get(appenderRef.getRef());
            if (appender != null) {
                arrayList.add(new AppenderControl(appender, appenderRef.getLevel(), appenderRef.getFilter()));
                continue;
            }
            LOGGER.error("No appender named {} was configured", (Object)appenderRef);
        }
        if (this.errorRef != null) {
            Appender appender = map.get(this.errorRef);
            if (appender != null) {
                this.errorAppender = new AppenderControl(appender, null, null);
            } else {
                LOGGER.error("Unable to set up error Appender. No appender named {} was configured", (Object)this.errorRef);
            }
        }
        if (arrayList.size() > 0) {
            this.thread = new AsyncThread(this, arrayList, this.queue);
            this.thread.setName("AsyncAppender-" + this.getName());
        } else if (this.errorRef == null) {
            throw new ConfigurationException("No appenders are available for AsyncAppender " + this.getName());
        }
        this.asyncQueueFullPolicy = AsyncQueueFullPolicyFactory.create();
        this.thread.start();
        super.start();
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(l, timeUnit, false);
        LOGGER.trace("AsyncAppender stopping. Queue still has {} events.", (Object)this.queue.size());
        this.thread.shutdown();
        try {
            this.thread.join(this.shutdownTimeout);
        } catch (InterruptedException interruptedException) {
            LOGGER.warn("Interrupted while stopping AsyncAppender {}", (Object)this.getName());
        }
        LOGGER.trace("AsyncAppender stopped. Queue has {} events.", (Object)this.queue.size());
        if (DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy) > 0L) {
            LOGGER.trace("AsyncAppender: {} discarded {} events.", (Object)this.asyncQueueFullPolicy, (Object)DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy));
        }
        this.setStopped();
        return false;
    }

    @Override
    public void append(LogEvent logEvent) {
        Log4jLogEvent log4jLogEvent;
        if (!this.isStarted()) {
            throw new IllegalStateException("AsyncAppender " + this.getName() + " is not active");
        }
        if (!this.canFormatMessageInBackground(logEvent.getMessage())) {
            logEvent.getMessage().getFormattedMessage();
        }
        if (!this.transfer(log4jLogEvent = Log4jLogEvent.createMemento(logEvent, this.includeLocation))) {
            if (this.blocking) {
                EventRoute eventRoute = this.asyncQueueFullPolicy.getRoute(this.thread.getId(), log4jLogEvent.getLevel());
                eventRoute.logMessage(this, (LogEvent)log4jLogEvent);
            } else {
                this.error("Appender " + this.getName() + " is unable to write primary appenders. queue is full");
                this.logToErrorAppenderIfNecessary(false, log4jLogEvent);
            }
        }
    }

    private boolean canFormatMessageInBackground(Message message) {
        return Constants.FORMAT_MESSAGES_IN_BACKGROUND || message.getClass().isAnnotationPresent(AsynchronouslyFormattable.class);
    }

    private boolean transfer(LogEvent logEvent) {
        return this.queue instanceof TransferQueue ? ((TransferQueue)this.queue).tryTransfer(logEvent) : this.queue.offer(logEvent);
    }

    public void logMessageInCurrentThread(LogEvent logEvent) {
        logEvent.setEndOfBatch(this.queue.isEmpty());
        boolean bl = this.thread.callAppenders(logEvent);
        this.logToErrorAppenderIfNecessary(bl, logEvent);
    }

    public void logMessageInBackgroundThread(LogEvent logEvent) {
        try {
            this.queue.put(logEvent);
        } catch (InterruptedException interruptedException) {
            boolean bl = this.handleInterruptedException(logEvent);
            this.logToErrorAppenderIfNecessary(bl, logEvent);
        }
    }

    private boolean handleInterruptedException(LogEvent logEvent) {
        boolean bl = this.queue.offer(logEvent);
        if (!bl) {
            LOGGER.warn("Interrupted while waiting for a free slot in the AsyncAppender LogEvent-queue {}", (Object)this.getName());
        }
        Thread.currentThread().interrupt();
        return bl;
    }

    private void logToErrorAppenderIfNecessary(boolean bl, LogEvent logEvent) {
        if (!bl && this.errorAppender != null) {
            this.errorAppender.callAppender(logEvent);
        }
    }

    @Deprecated
    public static AsyncAppender createAppender(AppenderRef[] appenderRefArray, String string, boolean bl, long l, int n, String string2, boolean bl2, Filter filter, Configuration configuration, boolean bl3) {
        if (string2 == null) {
            LOGGER.error("No name provided for AsyncAppender");
            return null;
        }
        if (appenderRefArray == null) {
            LOGGER.error("No appender references provided to AsyncAppender {}", (Object)string2);
        }
        return new AsyncAppender(string2, filter, appenderRefArray, string, n, bl, bl3, l, configuration, bl2, new ArrayBlockingQueueFactory<LogEvent>());
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public String[] getAppenderRefStrings() {
        String[] stringArray = new String[this.appenderRefs.length];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = this.appenderRefs[i].getRef();
        }
        return stringArray;
    }

    public boolean isIncludeLocation() {
        return this.includeLocation;
    }

    public boolean isBlocking() {
        return this.blocking;
    }

    public String getErrorRef() {
        return this.errorRef;
    }

    public int getQueueCapacity() {
        return this.queueSize;
    }

    public int getQueueRemainingCapacity() {
        return this.queue.remainingCapacity();
    }

    AsyncAppender(String string, Filter filter, AppenderRef[] appenderRefArray, String string2, int n, boolean bl, boolean bl2, long l, Configuration configuration, boolean bl3, BlockingQueueFactory blockingQueueFactory, 1 var13_12) {
        this(string, filter, appenderRefArray, string2, n, bl, bl2, l, configuration, bl3, blockingQueueFactory);
    }

    static AtomicLong access$100() {
        return THREAD_SEQUENCE;
    }

    static LogEvent access$200() {
        return SHUTDOWN_LOG_EVENT;
    }

    static AppenderControl access$300(AsyncAppender asyncAppender) {
        return asyncAppender.errorAppender;
    }

    static Logger access$400() {
        return LOGGER;
    }

    static Logger access$500() {
        return LOGGER;
    }

    static Logger access$600() {
        return LOGGER;
    }

    private class AsyncThread
    extends Log4jThread {
        private volatile boolean shutdown;
        private final List<AppenderControl> appenders;
        private final BlockingQueue<LogEvent> queue;
        final AsyncAppender this$0;

        public AsyncThread(AsyncAppender asyncAppender, List<AppenderControl> list, BlockingQueue<LogEvent> blockingQueue) {
            this.this$0 = asyncAppender;
            super("AsyncAppender-" + AsyncAppender.access$100().getAndIncrement());
            this.shutdown = false;
            this.appenders = list;
            this.queue = blockingQueue;
            this.setDaemon(false);
        }

        @Override
        public void run() {
            while (!this.shutdown) {
                LogEvent logEvent;
                try {
                    logEvent = this.queue.take();
                    if (logEvent == AsyncAppender.access$200()) {
                        this.shutdown = true;
                        continue;
                    }
                } catch (InterruptedException interruptedException) {
                    break;
                }
                logEvent.setEndOfBatch(this.queue.isEmpty());
                boolean bl = this.callAppenders(logEvent);
                if (bl || AsyncAppender.access$300(this.this$0) == null) continue;
                try {
                    AsyncAppender.access$300(this.this$0).callAppender(logEvent);
                } catch (Exception exception) {}
            }
            AsyncAppender.access$400().trace("AsyncAppender.AsyncThread shutting down. Processing remaining {} queue events.", (Object)this.queue.size());
            int n = 0;
            int n2 = 0;
            while (!this.queue.isEmpty()) {
                try {
                    LogEvent logEvent = this.queue.take();
                    if (logEvent instanceof Log4jLogEvent) {
                        Log4jLogEvent log4jLogEvent = (Log4jLogEvent)logEvent;
                        log4jLogEvent.setEndOfBatch(this.queue.isEmpty());
                        this.callAppenders(log4jLogEvent);
                        ++n;
                        continue;
                    }
                    ++n2;
                    AsyncAppender.access$500().trace("Ignoring event of class {}", (Object)logEvent.getClass().getName());
                } catch (InterruptedException interruptedException) {}
            }
            AsyncAppender.access$600().trace("AsyncAppender.AsyncThread stopped. Queue has {} events remaining. Processed {} and ignored {} events since shutdown started.", (Object)this.queue.size(), (Object)n, (Object)n2);
        }

        boolean callAppenders(LogEvent logEvent) {
            boolean bl = false;
            for (AppenderControl appenderControl : this.appenders) {
                try {
                    appenderControl.callAppender(logEvent);
                    bl = true;
                } catch (Exception exception) {}
            }
            return bl;
        }

        public void shutdown() {
            this.shutdown = true;
            if (this.queue.isEmpty()) {
                this.queue.offer(AsyncAppender.access$200());
            }
            if (this.getState() == Thread.State.TIMED_WAITING || this.getState() == Thread.State.WAITING) {
                this.interrupt();
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<AsyncAppender> {
        @PluginElement(value="AppenderRef")
        @Required(message="No appender references provided to AsyncAppender")
        private AppenderRef[] appenderRefs;
        @PluginBuilderAttribute
        @PluginAliases(value={"error-ref"})
        private String errorRef;
        @PluginBuilderAttribute
        private boolean blocking = true;
        @PluginBuilderAttribute
        private long shutdownTimeout = 0L;
        @PluginBuilderAttribute
        private int bufferSize = 128;
        @PluginBuilderAttribute
        @Required(message="No name provided for AsyncAppender")
        private String name;
        @PluginBuilderAttribute
        private boolean includeLocation = false;
        @PluginElement(value="Filter")
        private Filter filter;
        @PluginConfiguration
        private Configuration configuration;
        @PluginBuilderAttribute
        private boolean ignoreExceptions = true;
        @PluginElement(value="BlockingQueueFactory")
        private BlockingQueueFactory<LogEvent> blockingQueueFactory = new ArrayBlockingQueueFactory<LogEvent>();

        public Builder setAppenderRefs(AppenderRef[] appenderRefArray) {
            this.appenderRefs = appenderRefArray;
            return this;
        }

        public Builder setErrorRef(String string) {
            this.errorRef = string;
            return this;
        }

        public Builder setBlocking(boolean bl) {
            this.blocking = bl;
            return this;
        }

        public Builder setShutdownTimeout(long l) {
            this.shutdownTimeout = l;
            return this;
        }

        public Builder setBufferSize(int n) {
            this.bufferSize = n;
            return this;
        }

        public Builder setName(String string) {
            this.name = string;
            return this;
        }

        public Builder setIncludeLocation(boolean bl) {
            this.includeLocation = bl;
            return this;
        }

        public Builder setFilter(Filter filter) {
            this.filter = filter;
            return this;
        }

        public Builder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder setIgnoreExceptions(boolean bl) {
            this.ignoreExceptions = bl;
            return this;
        }

        public Builder setBlockingQueueFactory(BlockingQueueFactory<LogEvent> blockingQueueFactory) {
            this.blockingQueueFactory = blockingQueueFactory;
            return this;
        }

        @Override
        public AsyncAppender build() {
            return new AsyncAppender(this.name, this.filter, this.appenderRefs, this.errorRef, this.bufferSize, this.blocking, this.ignoreExceptions, this.shutdownTimeout, this.configuration, this.includeLocation, this.blockingQueueFactory, null);
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

