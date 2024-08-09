/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.composite.CompositeConfiguration;
import org.apache.logging.log4j.core.impl.ContextAnchor;
import org.apache.logging.log4j.core.selector.ClassLoaderContextSelector;
import org.apache.logging.log4j.core.selector.ContextSelector;
import org.apache.logging.log4j.core.util.Cancellable;
import org.apache.logging.log4j.core.util.DefaultShutdownCallbackRegistry;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Log4jContextFactory
implements LoggerContextFactory,
ShutdownCallbackRegistry {
    private static final StatusLogger LOGGER = StatusLogger.getLogger();
    private static final boolean SHUTDOWN_HOOK_ENABLED = PropertiesUtil.getProperties().getBooleanProperty("log4j.shutdownHookEnabled", false);
    private final ContextSelector selector;
    private final ShutdownCallbackRegistry shutdownCallbackRegistry;

    public Log4jContextFactory() {
        this(Log4jContextFactory.createContextSelector(), Log4jContextFactory.createShutdownCallbackRegistry());
    }

    public Log4jContextFactory(ContextSelector contextSelector) {
        this(contextSelector, Log4jContextFactory.createShutdownCallbackRegistry());
    }

    public Log4jContextFactory(ShutdownCallbackRegistry shutdownCallbackRegistry) {
        this(Log4jContextFactory.createContextSelector(), shutdownCallbackRegistry);
    }

    public Log4jContextFactory(ContextSelector contextSelector, ShutdownCallbackRegistry shutdownCallbackRegistry) {
        this.selector = Objects.requireNonNull(contextSelector, "No ContextSelector provided");
        this.shutdownCallbackRegistry = Objects.requireNonNull(shutdownCallbackRegistry, "No ShutdownCallbackRegistry provided");
        LOGGER.debug("Using ShutdownCallbackRegistry {}", (Object)shutdownCallbackRegistry.getClass());
        this.initializeShutdownCallbackRegistry();
    }

    private static ContextSelector createContextSelector() {
        try {
            ContextSelector contextSelector = LoaderUtil.newCheckedInstanceOfProperty("Log4jContextSelector", ContextSelector.class);
            if (contextSelector != null) {
                return contextSelector;
            }
        } catch (Exception exception) {
            LOGGER.error("Unable to create custom ContextSelector. Falling back to default.", (Throwable)exception);
        }
        return new ClassLoaderContextSelector();
    }

    private static ShutdownCallbackRegistry createShutdownCallbackRegistry() {
        try {
            ShutdownCallbackRegistry shutdownCallbackRegistry = LoaderUtil.newCheckedInstanceOfProperty("log4j.shutdownCallbackRegistry", ShutdownCallbackRegistry.class);
            if (shutdownCallbackRegistry != null) {
                return shutdownCallbackRegistry;
            }
        } catch (Exception exception) {
            LOGGER.error("Unable to create custom ShutdownCallbackRegistry. Falling back to default.", (Throwable)exception);
        }
        return new DefaultShutdownCallbackRegistry();
    }

    private void initializeShutdownCallbackRegistry() {
        if (SHUTDOWN_HOOK_ENABLED && this.shutdownCallbackRegistry instanceof LifeCycle) {
            try {
                ((LifeCycle)((Object)this.shutdownCallbackRegistry)).start();
            } catch (IllegalStateException illegalStateException) {
                LOGGER.error("Cannot start ShutdownCallbackRegistry, already shutting down.");
                throw illegalStateException;
            } catch (RuntimeException runtimeException) {
                LOGGER.error("There was an error starting the ShutdownCallbackRegistry.", (Throwable)runtimeException);
            }
        }
    }

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl) {
        LoggerContext loggerContext = this.selector.getContext(string, classLoader, bl);
        if (object != null && loggerContext.getExternalContext() == null) {
            loggerContext.setExternalContext(object);
        }
        if (loggerContext.getState() == LifeCycle.State.INITIALIZED) {
            loggerContext.start();
        }
        return loggerContext;
    }

    public LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl, ConfigurationSource configurationSource) {
        LoggerContext loggerContext = this.selector.getContext(string, classLoader, bl, null);
        if (object != null && loggerContext.getExternalContext() == null) {
            loggerContext.setExternalContext(object);
        }
        if (loggerContext.getState() == LifeCycle.State.INITIALIZED) {
            if (configurationSource != null) {
                ContextAnchor.THREAD_CONTEXT.set(loggerContext);
                Configuration configuration = ConfigurationFactory.getInstance().getConfiguration(loggerContext, configurationSource);
                LOGGER.debug("Starting LoggerContext[name={}] from configuration {}", (Object)loggerContext.getName(), (Object)configurationSource);
                loggerContext.start(configuration);
                ContextAnchor.THREAD_CONTEXT.remove();
            } else {
                loggerContext.start();
            }
        }
        return loggerContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl, Configuration configuration) {
        LoggerContext loggerContext = this.selector.getContext(string, classLoader, bl, null);
        if (object != null && loggerContext.getExternalContext() == null) {
            loggerContext.setExternalContext(object);
        }
        if (loggerContext.getState() == LifeCycle.State.INITIALIZED) {
            ContextAnchor.THREAD_CONTEXT.set(loggerContext);
            try {
                loggerContext.start(configuration);
            } finally {
                ContextAnchor.THREAD_CONTEXT.remove();
            }
        }
        return loggerContext;
    }

    @Override
    public LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl, URI uRI, String string2) {
        LoggerContext loggerContext = this.selector.getContext(string, classLoader, bl, uRI);
        if (object != null && loggerContext.getExternalContext() == null) {
            loggerContext.setExternalContext(object);
        }
        if (string2 != null) {
            loggerContext.setName(string2);
        }
        if (loggerContext.getState() == LifeCycle.State.INITIALIZED) {
            if (uRI != null || string2 != null) {
                ContextAnchor.THREAD_CONTEXT.set(loggerContext);
                Configuration configuration = ConfigurationFactory.getInstance().getConfiguration(loggerContext, string2, uRI);
                LOGGER.debug("Starting LoggerContext[name={}] from configuration at {}", (Object)loggerContext.getName(), (Object)uRI);
                loggerContext.start(configuration);
                ContextAnchor.THREAD_CONTEXT.remove();
            } else {
                loggerContext.start();
            }
        }
        return loggerContext;
    }

    public LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl, List<URI> list, String string2) {
        LoggerContext loggerContext = this.selector.getContext(string, classLoader, bl, null);
        if (object != null && loggerContext.getExternalContext() == null) {
            loggerContext.setExternalContext(object);
        }
        if (string2 != null) {
            loggerContext.setName(string2);
        }
        if (loggerContext.getState() == LifeCycle.State.INITIALIZED) {
            if (list != null && !list.isEmpty()) {
                ContextAnchor.THREAD_CONTEXT.set(loggerContext);
                ArrayList<AbstractConfiguration> arrayList = new ArrayList<AbstractConfiguration>(list.size());
                for (URI uRI : list) {
                    Configuration configuration = ConfigurationFactory.getInstance().getConfiguration(loggerContext, string2, uRI);
                    if (configuration instanceof AbstractConfiguration) {
                        arrayList.add((AbstractConfiguration)configuration);
                        continue;
                    }
                    LOGGER.error("Found configuration {}, which is not an AbstractConfiguration and can't be handled by CompositeConfiguration", (Object)uRI);
                }
                CompositeConfiguration compositeConfiguration = new CompositeConfiguration(arrayList);
                LOGGER.debug("Starting LoggerContext[name={}] from configurations at {}", (Object)loggerContext.getName(), (Object)list);
                loggerContext.start(compositeConfiguration);
                ContextAnchor.THREAD_CONTEXT.remove();
            } else {
                loggerContext.start();
            }
        }
        return loggerContext;
    }

    public ContextSelector getSelector() {
        return this.selector;
    }

    public ShutdownCallbackRegistry getShutdownCallbackRegistry() {
        return this.shutdownCallbackRegistry;
    }

    @Override
    public void removeContext(org.apache.logging.log4j.spi.LoggerContext loggerContext) {
        if (loggerContext instanceof LoggerContext) {
            this.selector.removeContext((LoggerContext)loggerContext);
        }
    }

    @Override
    public Cancellable addShutdownCallback(Runnable runnable) {
        return SHUTDOWN_HOOK_ENABLED ? this.shutdownCallbackRegistry.addShutdownCallback(runnable) : null;
    }

    @Override
    public org.apache.logging.log4j.spi.LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl, URI uRI, String string2) {
        return this.getContext(string, classLoader, object, bl, uRI, string2);
    }

    @Override
    public org.apache.logging.log4j.spi.LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean bl) {
        return this.getContext(string, classLoader, object, bl);
    }
}

