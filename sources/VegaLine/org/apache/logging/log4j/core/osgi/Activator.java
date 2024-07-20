/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleEvent
 *  org.osgi.framework.BundleListener
 *  org.osgi.framework.SynchronousBundleListener
 *  org.osgi.framework.wiring.BundleWiring
 */
package org.apache.logging.log4j.core.osgi;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.PluginRegistry;
import org.apache.logging.log4j.core.osgi.BundleContextSelector;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.SynchronousBundleListener;
import org.osgi.framework.wiring.BundleWiring;

public final class Activator
implements BundleActivator,
SynchronousBundleListener {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final AtomicReference<BundleContext> contextRef = new AtomicReference();

    public void start(BundleContext context) throws Exception {
        if (PropertiesUtil.getProperties().getStringProperty("Log4jContextSelector") == null) {
            System.setProperty("Log4jContextSelector", BundleContextSelector.class.getName());
        }
        if (this.contextRef.compareAndSet(null, context)) {
            context.addBundleListener((BundleListener)this);
            Activator.scanInstalledBundlesForPlugins(context);
        }
    }

    private static void scanInstalledBundlesForPlugins(BundleContext context) {
        Bundle[] bundles;
        for (Bundle bundle : bundles = context.getBundles()) {
            if (bundle.getState() != 32 || bundle.getBundleId() == 0L) continue;
            Activator.scanBundleForPlugins(bundle);
        }
    }

    private static void scanBundleForPlugins(Bundle bundle) {
        LOGGER.trace("Scanning bundle [{}] for plugins.", (Object)bundle.getSymbolicName());
        PluginRegistry.getInstance().loadFromBundle(bundle.getBundleId(), ((BundleWiring)bundle.adapt(BundleWiring.class)).getClassLoader());
    }

    private static void stopBundlePlugins(Bundle bundle) {
        LOGGER.trace("Stopping bundle [{}] plugins.", (Object)bundle.getSymbolicName());
        PluginRegistry.getInstance().clearBundlePlugins(bundle.getBundleId());
    }

    public void stop(BundleContext context) throws Exception {
        this.contextRef.compareAndSet(context, null);
        LogManager.shutdown();
    }

    public void bundleChanged(BundleEvent event) {
        switch (event.getType()) {
            case 2: {
                Activator.scanBundleForPlugins(event.getBundle());
                break;
            }
            case 256: {
                Activator.stopBundlePlugins(event.getBundle());
                break;
            }
        }
    }
}

