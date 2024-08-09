/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationListener;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.NullConfiguration;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.jmx.Server;
import org.apache.logging.log4j.core.util.Cancellable;
import org.apache.logging.log4j.core.util.ExecutorServices;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.apache.logging.log4j.spi.Terminable;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LoggerContext
extends AbstractLifeCycle
implements org.apache.logging.log4j.spi.LoggerContext,
AutoCloseable,
Terminable,
ConfigurationListener {
    public static final String PROPERTY_CONFIG = "config";
    private static final Configuration NULL_CONFIGURATION;
    private final LoggerRegistry<Logger> loggerRegistry = new LoggerRegistry();
    private final CopyOnWriteArrayList<PropertyChangeListener> propertyChangeListeners = new CopyOnWriteArrayList();
    private volatile Configuration configuration = new DefaultConfiguration();
    private Object externalContext;
    private String contextName;
    private volatile URI configLocation;
    private Cancellable shutdownCallback;
    private final Lock configLock = new ReentrantLock();

    public LoggerContext(String string) {
        this(string, null, (URI)null);
    }

    public LoggerContext(String string, Object object) {
        this(string, object, (URI)null);
    }

    public LoggerContext(String string, Object object, URI uRI) {
        this.contextName = string;
        this.externalContext = object;
        this.configLocation = uRI;
    }

    public LoggerContext(String string, Object object, String string2) {
        this.contextName = string;
        this.externalContext = object;
        if (string2 != null) {
            URI uRI;
            try {
                uRI = new File(string2).toURI();
            } catch (Exception exception) {
                uRI = null;
            }
            this.configLocation = uRI;
        } else {
            this.configLocation = null;
        }
    }

    public static LoggerContext getContext() {
        return (LoggerContext)LogManager.getContext();
    }

    public static LoggerContext getContext(boolean bl) {
        return (LoggerContext)LogManager.getContext(bl);
    }

    public static LoggerContext getContext(ClassLoader classLoader, boolean bl, URI uRI) {
        return (LoggerContext)LogManager.getContext(classLoader, bl, uRI);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() {
        LOGGER.debug("Starting LoggerContext[name={}, {}]...", (Object)this.getName(), (Object)this);
        if (PropertiesUtil.getProperties().getBooleanProperty("log4j.LoggerContext.stacktrace.on.start", true)) {
            LOGGER.debug("Stack trace to locate invoker", (Throwable)new Exception("Not a real error, showing stack trace to locate invoker"));
        }
        if (this.configLock.tryLock()) {
            try {
                if (this.isInitialized() || this.isStopped()) {
                    this.setStarting();
                    this.reconfigure();
                    if (this.configuration.isShutdownHookEnabled()) {
                        this.setUpShutdownHook();
                    }
                    this.setStarted();
                }
            } finally {
                this.configLock.unlock();
            }
        }
        LOGGER.debug("LoggerContext[name={}, {}] started OK.", (Object)this.getName(), (Object)this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void start(Configuration configuration) {
        LOGGER.debug("Starting LoggerContext[name={}, {}] with configuration {}...", (Object)this.getName(), (Object)this, (Object)configuration);
        if (this.configLock.tryLock()) {
            try {
                if (this.isInitialized() || this.isStopped()) {
                    if (this.configuration.isShutdownHookEnabled()) {
                        this.setUpShutdownHook();
                    }
                    this.setStarted();
                }
            } finally {
                this.configLock.unlock();
            }
        }
        this.setConfiguration(configuration);
        LOGGER.debug("LoggerContext[name={}, {}] started OK with configuration {}.", (Object)this.getName(), (Object)this, (Object)configuration);
    }

    private void setUpShutdownHook() {
        LoggerContextFactory loggerContextFactory;
        if (this.shutdownCallback == null && (loggerContextFactory = LogManager.getFactory()) instanceof ShutdownCallbackRegistry) {
            LOGGER.debug(ShutdownCallbackRegistry.SHUTDOWN_HOOK_MARKER, "Shutdown hook enabled. Registering a new one.");
            try {
                long l = this.configuration.getShutdownTimeoutMillis();
                this.shutdownCallback = ((ShutdownCallbackRegistry)((Object)loggerContextFactory)).addShutdownCallback(new Runnable(this, l){
                    final long val$shutdownTimeoutMillis;
                    final LoggerContext this$0;
                    {
                        this.this$0 = loggerContext;
                        this.val$shutdownTimeoutMillis = l;
                    }

                    @Override
                    public void run() {
                        LoggerContext loggerContext = this.this$0;
                        AbstractLifeCycle.LOGGER.debug(ShutdownCallbackRegistry.SHUTDOWN_HOOK_MARKER, "Stopping LoggerContext[name={}, {}]", (Object)loggerContext.getName(), (Object)loggerContext);
                        loggerContext.stop(this.val$shutdownTimeoutMillis, TimeUnit.MILLISECONDS);
                    }

                    public String toString() {
                        return "Shutdown callback for LoggerContext[name=" + this.this$0.getName() + ']';
                    }
                });
            } catch (IllegalStateException illegalStateException) {
                throw new IllegalStateException("Unable to register Log4j shutdown hook because JVM is shutting down.", illegalStateException);
            } catch (SecurityException securityException) {
                LOGGER.error(ShutdownCallbackRegistry.SHUTDOWN_HOOK_MARKER, "Unable to register shutdown hook due to security restrictions", (Throwable)securityException);
            }
        }
    }

    @Override
    public void close() {
        this.stop();
    }

    @Override
    public void terminate() {
        this.stop();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        LOGGER.debug("Stopping LoggerContext[name={}, {}]...", (Object)this.getName(), (Object)this);
        this.configLock.lock();
        try {
            if (this.isStopped()) {
                boolean bl = true;
                return bl;
            }
            this.setStopping();
            try {
                Server.unregisterLoggerContext(this.getName());
            } catch (Exception | LinkageError throwable) {
                LOGGER.error("Unable to unregister MBeans", throwable);
            }
            if (this.shutdownCallback != null) {
                this.shutdownCallback.cancel();
                this.shutdownCallback = null;
            }
            Configuration configuration = this.configuration;
            this.configuration = NULL_CONFIGURATION;
            this.updateLoggers();
            if (configuration instanceof LifeCycle2) {
                ((LifeCycle2)((Object)configuration)).stop(l, timeUnit);
            } else {
                configuration.stop();
            }
            this.externalContext = null;
            LogManager.getFactory().removeContext(this);
        } finally {
            this.configLock.unlock();
            this.setStopped();
        }
        LOGGER.debug("Stopped LoggerContext[name={}, {}] with status {}", (Object)this.getName(), (Object)this, (Object)true);
        return false;
    }

    public String getName() {
        return this.contextName;
    }

    public Logger getRootLogger() {
        return this.getLogger("");
    }

    public void setName(String string) {
        this.contextName = Objects.requireNonNull(string);
    }

    public void setExternalContext(Object object) {
        this.externalContext = object;
    }

    @Override
    public Object getExternalContext() {
        return this.externalContext;
    }

    @Override
    public Logger getLogger(String string) {
        return this.getLogger(string, null);
    }

    public Collection<Logger> getLoggers() {
        return this.loggerRegistry.getLoggers();
    }

    @Override
    public Logger getLogger(String string, MessageFactory messageFactory) {
        Logger logger = this.loggerRegistry.getLogger(string, messageFactory);
        if (logger != null) {
            AbstractLogger.checkMessageFactory(logger, messageFactory);
            return logger;
        }
        logger = this.newInstance(this, string, messageFactory);
        this.loggerRegistry.putIfAbsent(string, messageFactory, logger);
        return this.loggerRegistry.getLogger(string, messageFactory);
    }

    @Override
    public boolean hasLogger(String string) {
        return this.loggerRegistry.hasLogger(string);
    }

    @Override
    public boolean hasLogger(String string, MessageFactory messageFactory) {
        return this.loggerRegistry.hasLogger(string, messageFactory);
    }

    @Override
    public boolean hasLogger(String string, Class<? extends MessageFactory> clazz) {
        return this.loggerRegistry.hasLogger(string, clazz);
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public void addFilter(Filter filter) {
        this.configuration.addFilter(filter);
    }

    public void removeFilter(Filter filter) {
        this.configuration.removeFilter(filter);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Configuration setConfiguration(Configuration configuration) {
        if (configuration == null) {
            LOGGER.error("No configuration found for context '{}'.", (Object)this.contextName);
            return this.configuration;
        }
        this.configLock.lock();
        try {
            Configuration configuration2 = this.configuration;
            configuration.addListener(this);
            ConcurrentMap concurrentMap = (ConcurrentMap)configuration.getComponent("ContextProperties");
            try {
                concurrentMap.putIfAbsent("hostName", NetUtils.getLocalHostname());
            } catch (Exception exception) {
                LOGGER.debug("Ignoring {}, setting hostName to 'unknown'", (Object)exception.toString());
                concurrentMap.putIfAbsent("hostName", "unknown");
            }
            concurrentMap.putIfAbsent("contextName", this.contextName);
            configuration.start();
            this.configuration = configuration;
            this.updateLoggers();
            if (configuration2 != null) {
                configuration2.removeListener(this);
                configuration2.stop();
            }
            this.firePropertyChangeEvent(new PropertyChangeEvent(this, PROPERTY_CONFIG, configuration2, configuration));
            try {
                Server.reregisterMBeansAfterReconfigure();
            } catch (Exception | LinkageError throwable) {
                LOGGER.error("Could not reconfigure JMX", throwable);
            }
            Log4jLogEvent.setNanoClock(this.configuration.getNanoClock());
            Configuration configuration3 = configuration2;
            return configuration3;
        } finally {
            this.configLock.unlock();
        }
    }

    private void firePropertyChangeEvent(PropertyChangeEvent propertyChangeEvent) {
        for (PropertyChangeListener propertyChangeListener : this.propertyChangeListeners) {
            propertyChangeListener.propertyChange(propertyChangeEvent);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.propertyChangeListeners.add(Objects.requireNonNull(propertyChangeListener, "listener"));
    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.propertyChangeListeners.remove(propertyChangeListener);
    }

    public URI getConfigLocation() {
        return this.configLocation;
    }

    public void setConfigLocation(URI uRI) {
        this.configLocation = uRI;
        this.reconfigure(uRI);
    }

    private void reconfigure(URI uRI) {
        ClassLoader classLoader = ClassLoader.class.isInstance(this.externalContext) ? (ClassLoader)this.externalContext : null;
        LOGGER.debug("Reconfiguration started for context[name={}] at URI {} ({}) with optional ClassLoader: {}", (Object)this.contextName, (Object)uRI, (Object)this, (Object)classLoader);
        Configuration configuration = ConfigurationFactory.getInstance().getConfiguration(this, this.contextName, uRI, classLoader);
        if (configuration == null) {
            LOGGER.error("Reconfiguration failed: No configuration found for '{}' at '{}' in '{}'", (Object)this.contextName, (Object)uRI, (Object)classLoader);
        } else {
            this.setConfiguration(configuration);
            String string = this.configuration == null ? "?" : String.valueOf(this.configuration.getConfigurationSource());
            LOGGER.debug("Reconfiguration complete for context[name={}] at URI {} ({}) with optional ClassLoader: {}", (Object)this.contextName, (Object)string, (Object)this, (Object)classLoader);
        }
    }

    public void reconfigure() {
        this.reconfigure(this.configLocation);
    }

    public void updateLoggers() {
        this.updateLoggers(this.configuration);
    }

    public void updateLoggers(Configuration configuration) {
        Configuration configuration2 = this.configuration;
        for (Logger logger : this.loggerRegistry.getLoggers()) {
            logger.updateConfiguration(configuration);
        }
        this.firePropertyChangeEvent(new PropertyChangeEvent(this, PROPERTY_CONFIG, configuration2, configuration));
    }

    @Override
    public synchronized void onChange(Reconfigurable reconfigurable) {
        LOGGER.debug("Reconfiguration started for context {} ({})", (Object)this.contextName, (Object)this);
        Configuration configuration = reconfigurable.reconfigure();
        if (configuration != null) {
            this.setConfiguration(configuration);
            LOGGER.debug("Reconfiguration completed for {} ({})", (Object)this.contextName, (Object)this);
        } else {
            LOGGER.debug("Reconfiguration failed for {} ({})", (Object)this.contextName, (Object)this);
        }
    }

    protected Logger newInstance(LoggerContext loggerContext, String string, MessageFactory messageFactory) {
        return new Logger(loggerContext, string, messageFactory);
    }

    @Override
    public ExtendedLogger getLogger(String string, MessageFactory messageFactory) {
        return this.getLogger(string, messageFactory);
    }

    @Override
    public ExtendedLogger getLogger(String string) {
        return this.getLogger(string);
    }

    static {
        try {
            LoaderUtil.loadClass(ExecutorServices.class.getName());
        } catch (Exception exception) {
            LOGGER.error("Failed to preload ExecutorServices class.", (Throwable)exception);
        }
        NULL_CONFIGURATION = new NullConfiguration();
    }
}

