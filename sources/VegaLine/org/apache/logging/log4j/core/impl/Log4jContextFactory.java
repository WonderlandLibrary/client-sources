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

public class Log4jContextFactory
implements LoggerContextFactory,
ShutdownCallbackRegistry {
    private static final StatusLogger LOGGER = StatusLogger.getLogger();
    private static final boolean SHUTDOWN_HOOK_ENABLED = PropertiesUtil.getProperties().getBooleanProperty("log4j.shutdownHookEnabled", true);
    private final ContextSelector selector;
    private final ShutdownCallbackRegistry shutdownCallbackRegistry;

    public Log4jContextFactory() {
        this(Log4jContextFactory.createContextSelector(), Log4jContextFactory.createShutdownCallbackRegistry());
    }

    public Log4jContextFactory(ContextSelector selector) {
        this(selector, Log4jContextFactory.createShutdownCallbackRegistry());
    }

    public Log4jContextFactory(ShutdownCallbackRegistry shutdownCallbackRegistry) {
        this(Log4jContextFactory.createContextSelector(), shutdownCallbackRegistry);
    }

    public Log4jContextFactory(ContextSelector selector, ShutdownCallbackRegistry shutdownCallbackRegistry) {
        this.selector = Objects.requireNonNull(selector, "No ContextSelector provided");
        this.shutdownCallbackRegistry = Objects.requireNonNull(shutdownCallbackRegistry, "No ShutdownCallbackRegistry provided");
        LOGGER.debug("Using ShutdownCallbackRegistry {}", (Object)shutdownCallbackRegistry.getClass());
        this.initializeShutdownCallbackRegistry();
    }

    private static ContextSelector createContextSelector() {
        try {
            ContextSelector selector = LoaderUtil.newCheckedInstanceOfProperty("Log4jContextSelector", ContextSelector.class);
            if (selector != null) {
                return selector;
            }
        } catch (Exception e) {
            LOGGER.error("Unable to create custom ContextSelector. Falling back to default.", (Throwable)e);
        }
        return new ClassLoaderContextSelector();
    }

    private static ShutdownCallbackRegistry createShutdownCallbackRegistry() {
        try {
            ShutdownCallbackRegistry registry = LoaderUtil.newCheckedInstanceOfProperty("log4j.shutdownCallbackRegistry", ShutdownCallbackRegistry.class);
            if (registry != null) {
                return registry;
            }
        } catch (Exception e) {
            LOGGER.error("Unable to create custom ShutdownCallbackRegistry. Falling back to default.", (Throwable)e);
        }
        return new DefaultShutdownCallbackRegistry();
    }

    private void initializeShutdownCallbackRegistry() {
        if (SHUTDOWN_HOOK_ENABLED && this.shutdownCallbackRegistry instanceof LifeCycle) {
            try {
                ((LifeCycle)((Object)this.shutdownCallbackRegistry)).start();
            } catch (IllegalStateException e) {
                LOGGER.error("Cannot start ShutdownCallbackRegistry, already shutting down.");
                throw e;
            } catch (RuntimeException e) {
                LOGGER.error("There was an error starting the ShutdownCallbackRegistry.", (Throwable)e);
            }
        }
    }

    @Override
    public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext) {
        LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext);
        if (externalContext != null && ctx.getExternalContext() == null) {
            ctx.setExternalContext(externalContext);
        }
        if (ctx.getState() == LifeCycle.State.INITIALIZED) {
            ctx.start();
        }
        return ctx;
    }

    public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, ConfigurationSource source) {
        LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, null);
        if (externalContext != null && ctx.getExternalContext() == null) {
            ctx.setExternalContext(externalContext);
        }
        if (ctx.getState() == LifeCycle.State.INITIALIZED) {
            if (source != null) {
                ContextAnchor.THREAD_CONTEXT.set(ctx);
                Configuration config = ConfigurationFactory.getInstance().getConfiguration(ctx, source);
                LOGGER.debug("Starting LoggerContext[name={}] from configuration {}", (Object)ctx.getName(), (Object)source);
                ctx.start(config);
                ContextAnchor.THREAD_CONTEXT.remove();
            } else {
                ctx.start();
            }
        }
        return ctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, Configuration configuration) {
        LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, null);
        if (externalContext != null && ctx.getExternalContext() == null) {
            ctx.setExternalContext(externalContext);
        }
        if (ctx.getState() == LifeCycle.State.INITIALIZED) {
            ContextAnchor.THREAD_CONTEXT.set(ctx);
            try {
                ctx.start(configuration);
            } finally {
                ContextAnchor.THREAD_CONTEXT.remove();
            }
        }
        return ctx;
    }

    @Override
    public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, URI configLocation, String name) {
        LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, configLocation);
        if (externalContext != null && ctx.getExternalContext() == null) {
            ctx.setExternalContext(externalContext);
        }
        if (name != null) {
            ctx.setName(name);
        }
        if (ctx.getState() == LifeCycle.State.INITIALIZED) {
            if (configLocation != null || name != null) {
                ContextAnchor.THREAD_CONTEXT.set(ctx);
                Configuration config = ConfigurationFactory.getInstance().getConfiguration(ctx, name, configLocation);
                LOGGER.debug("Starting LoggerContext[name={}] from configuration at {}", (Object)ctx.getName(), (Object)configLocation);
                ctx.start(config);
                ContextAnchor.THREAD_CONTEXT.remove();
            } else {
                ctx.start();
            }
        }
        return ctx;
    }

    public LoggerContext getContext(String fqcn, ClassLoader loader, Object externalContext, boolean currentContext, List<URI> configLocations, String name) {
        LoggerContext ctx = this.selector.getContext(fqcn, loader, currentContext, null);
        if (externalContext != null && ctx.getExternalContext() == null) {
            ctx.setExternalContext(externalContext);
        }
        if (name != null) {
            ctx.setName(name);
        }
        if (ctx.getState() == LifeCycle.State.INITIALIZED) {
            if (configLocations != null && !configLocations.isEmpty()) {
                ContextAnchor.THREAD_CONTEXT.set(ctx);
                ArrayList<AbstractConfiguration> configurations = new ArrayList<AbstractConfiguration>(configLocations.size());
                for (URI configLocation : configLocations) {
                    Configuration currentReadConfiguration = ConfigurationFactory.getInstance().getConfiguration(ctx, name, configLocation);
                    if (currentReadConfiguration instanceof AbstractConfiguration) {
                        configurations.add((AbstractConfiguration)currentReadConfiguration);
                        continue;
                    }
                    LOGGER.error("Found configuration {}, which is not an AbstractConfiguration and can't be handled by CompositeConfiguration", (Object)configLocation);
                }
                CompositeConfiguration compositeConfiguration = new CompositeConfiguration(configurations);
                LOGGER.debug("Starting LoggerContext[name={}] from configurations at {}", (Object)ctx.getName(), (Object)configLocations);
                ctx.start(compositeConfiguration);
                ContextAnchor.THREAD_CONTEXT.remove();
            } else {
                ctx.start();
            }
        }
        return ctx;
    }

    public ContextSelector getSelector() {
        return this.selector;
    }

    public ShutdownCallbackRegistry getShutdownCallbackRegistry() {
        return this.shutdownCallbackRegistry;
    }

    @Override
    public void removeContext(org.apache.logging.log4j.spi.LoggerContext context) {
        if (context instanceof LoggerContext) {
            this.selector.removeContext((LoggerContext)context);
        }
    }

    @Override
    public Cancellable addShutdownCallback(Runnable callback) {
        return SHUTDOWN_HOOK_ENABLED ? this.shutdownCallbackRegistry.addShutdownCallback(callback) : null;
    }
}

