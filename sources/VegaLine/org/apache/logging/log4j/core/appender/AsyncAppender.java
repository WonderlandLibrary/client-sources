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

    private AsyncAppender(String name, Filter filter, AppenderRef[] appenderRefs, String errorRef, int queueSize, boolean blocking, boolean ignoreExceptions, long shutdownTimeout, Configuration config, boolean includeLocation, BlockingQueueFactory<LogEvent> blockingQueueFactory) {
        super(name, filter, null, ignoreExceptions);
        this.queue = blockingQueueFactory.create(queueSize);
        this.queueSize = queueSize;
        this.blocking = blocking;
        this.shutdownTimeout = shutdownTimeout;
        this.config = config;
        this.appenderRefs = appenderRefs;
        this.errorRef = errorRef;
        this.includeLocation = includeLocation;
    }

    @Override
    public void start() {
        Map<String, Appender> map = this.config.getAppenders();
        ArrayList<AppenderControl> appenders = new ArrayList<AppenderControl>();
        for (AppenderRef appenderRef : this.appenderRefs) {
            Appender appender = map.get(appenderRef.getRef());
            if (appender != null) {
                appenders.add(new AppenderControl(appender, appenderRef.getLevel(), appenderRef.getFilter()));
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
        if (appenders.size() > 0) {
            this.thread = new AsyncThread(appenders, this.queue);
            this.thread.setName("AsyncAppender-" + this.getName());
        } else if (this.errorRef == null) {
            throw new ConfigurationException("No appenders are available for AsyncAppender " + this.getName());
        }
        this.asyncQueueFullPolicy = AsyncQueueFullPolicyFactory.create();
        this.thread.start();
        super.start();
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(timeout, timeUnit, false);
        LOGGER.trace("AsyncAppender stopping. Queue still has {} events.", (Object)this.queue.size());
        this.thread.shutdown();
        try {
            this.thread.join(this.shutdownTimeout);
        } catch (InterruptedException ex) {
            LOGGER.warn("Interrupted while stopping AsyncAppender {}", (Object)this.getName());
        }
        LOGGER.trace("AsyncAppender stopped. Queue has {} events.", (Object)this.queue.size());
        if (DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy) > 0L) {
            LOGGER.trace("AsyncAppender: {} discarded {} events.", (Object)this.asyncQueueFullPolicy, (Object)DiscardingAsyncQueueFullPolicy.getDiscardCount(this.asyncQueueFullPolicy));
        }
        this.setStopped();
        return true;
    }

    @Override
    public void append(LogEvent logEvent) {
        Log4jLogEvent memento;
        if (!this.isStarted()) {
            throw new IllegalStateException("AsyncAppender " + this.getName() + " is not active");
        }
        if (!this.canFormatMessageInBackground(logEvent.getMessage())) {
            logEvent.getMessage().getFormattedMessage();
        }
        if (!this.transfer(memento = Log4jLogEvent.createMemento(logEvent, this.includeLocation))) {
            if (this.blocking) {
                EventRoute route = this.asyncQueueFullPolicy.getRoute(this.thread.getId(), memento.getLevel());
                route.logMessage(this, (LogEvent)memento);
            } else {
                this.error("Appender " + this.getName() + " is unable to write primary appenders. queue is full");
                this.logToErrorAppenderIfNecessary(false, memento);
            }
        }
    }

    private boolean canFormatMessageInBackground(Message message) {
        return Constants.FORMAT_MESSAGES_IN_BACKGROUND || message.getClass().isAnnotationPresent(AsynchronouslyFormattable.class);
    }

    private boolean transfer(LogEvent memento) {
        return this.queue instanceof TransferQueue ? ((TransferQueue)this.queue).tryTransfer(memento) : this.queue.offer(memento);
    }

    public void logMessageInCurrentThread(LogEvent logEvent) {
        logEvent.setEndOfBatch(this.queue.isEmpty());
        boolean appendSuccessful = this.thread.callAppenders(logEvent);
        this.logToErrorAppenderIfNecessary(appendSuccessful, logEvent);
    }

    public void logMessageInBackgroundThread(LogEvent logEvent) {
        try {
            this.queue.put(logEvent);
        } catch (InterruptedException e) {
            boolean appendSuccessful = this.handleInterruptedException(logEvent);
            this.logToErrorAppenderIfNecessary(appendSuccessful, logEvent);
        }
    }

    private boolean handleInterruptedException(LogEvent memento) {
        boolean appendSuccessful = this.queue.offer(memento);
        if (!appendSuccessful) {
            LOGGER.warn("Interrupted while waiting for a free slot in the AsyncAppender LogEvent-queue {}", (Object)this.getName());
        }
        Thread.currentThread().interrupt();
        return appendSuccessful;
    }

    private void logToErrorAppenderIfNecessary(boolean appendSuccessful, LogEvent logEvent) {
        if (!appendSuccessful && this.errorAppender != null) {
            this.errorAppender.callAppender(logEvent);
        }
    }

    @Deprecated
    public static AsyncAppender createAppender(AppenderRef[] appenderRefs, String errorRef, boolean blocking, long shutdownTimeout, int size, String name, boolean includeLocation, Filter filter, Configuration config, boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("No name provided for AsyncAppender");
            return null;
        }
        if (appenderRefs == null) {
            LOGGER.error("No appender references provided to AsyncAppender {}", (Object)name);
        }
        return new AsyncAppender(name, filter, appenderRefs, errorRef, size, blocking, ignoreExceptions, shutdownTimeout, config, includeLocation, new ArrayBlockingQueueFactory<LogEvent>());
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public String[] getAppenderRefStrings() {
        String[] result = new String[this.appenderRefs.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.appenderRefs[i].getRef();
        }
        return result;
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

    private class AsyncThread
    extends Log4jThread {
        private volatile boolean shutdown;
        private final List<AppenderControl> appenders;
        private final BlockingQueue<LogEvent> queue;

        public AsyncThread(List<AppenderControl> appenders, BlockingQueue<LogEvent> queue) {
            super("AsyncAppender-" + THREAD_SEQUENCE.getAndIncrement());
            this.shutdown = false;
            this.appenders = appenders;
            this.queue = queue;
            this.setDaemon(true);
        }

        @Override
        public void run() {
            while (!this.shutdown) {
                LogEvent event;
                try {
                    event = this.queue.take();
                    if (event == SHUTDOWN_LOG_EVENT) {
                        this.shutdown = true;
                        continue;
                    }
                } catch (InterruptedException ex) {
                    break;
                }
                event.setEndOfBatch(this.queue.isEmpty());
                boolean success = this.callAppenders(event);
                if (success || AsyncAppender.this.errorAppender == null) continue;
                try {
                    AsyncAppender.this.errorAppender.callAppender(event);
                } catch (Exception exception) {}
            }
            LOGGER.trace("AsyncAppender.AsyncThread shutting down. Processing remaining {} queue events.", (Object)this.queue.size());
            int count = 0;
            int ignored = 0;
            while (!this.queue.isEmpty()) {
                try {
                    LogEvent event = this.queue.take();
                    if (event instanceof Log4jLogEvent) {
                        Log4jLogEvent logEvent = (Log4jLogEvent)event;
                        logEvent.setEndOfBatch(this.queue.isEmpty());
                        this.callAppenders(logEvent);
                        ++count;
                        continue;
                    }
                    ++ignored;
                    LOGGER.trace("Ignoring event of class {}", (Object)event.getClass().getName());
                } catch (InterruptedException interruptedException) {}
            }
            LOGGER.trace("AsyncAppender.AsyncThread stopped. Queue has {} events remaining. Processed {} and ignored {} events since shutdown started.", (Object)this.queue.size(), (Object)count, (Object)ignored);
        }

        boolean callAppenders(LogEvent event) {
            boolean success = false;
            for (AppenderControl control : this.appenders) {
                try {
                    control.callAppender(event);
                    success = true;
                } catch (Exception exception) {}
            }
            return success;
        }

        public void shutdown() {
            this.shutdown = true;
            if (this.queue.isEmpty()) {
                this.queue.offer(SHUTDOWN_LOG_EVENT);
            }
            if (this.getState() == Thread.State.TIMED_WAITING || this.getState() == Thread.State.WAITING) {
                this.interrupt();
            }
        }
    }

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

        public Builder setAppenderRefs(AppenderRef[] appenderRefs) {
            this.appenderRefs = appenderRefs;
            return this;
        }

        public Builder setErrorRef(String errorRef) {
            this.errorRef = errorRef;
            return this;
        }

        public Builder setBlocking(boolean blocking) {
            this.blocking = blocking;
            return this;
        }

        public Builder setShutdownTimeout(long shutdownTimeout) {
            this.shutdownTimeout = shutdownTimeout;
            return this;
        }

        public Builder setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setIncludeLocation(boolean includeLocation) {
            this.includeLocation = includeLocation;
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

        public Builder setIgnoreExceptions(boolean ignoreExceptions) {
            this.ignoreExceptions = ignoreExceptions;
            return this;
        }

        public Builder setBlockingQueueFactory(BlockingQueueFactory<LogEvent> blockingQueueFactory) {
            this.blockingQueueFactory = blockingQueueFactory;
            return this;
        }

        @Override
        public AsyncAppender build() {
            return new AsyncAppender(this.name, this.filter, this.appenderRefs, this.errorRef, this.bufferSize, this.blocking, this.ignoreExceptions, this.shutdownTimeout, this.configuration, this.includeLocation, this.blockingQueueFactory);
        }
    }
}

